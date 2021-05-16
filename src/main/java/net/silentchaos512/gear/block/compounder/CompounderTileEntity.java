package net.silentchaos512.gear.block.compounder;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.material.IMaterial;
import net.silentchaos512.gear.api.material.IMaterialInstance;
import net.silentchaos512.gear.api.material.MaterialList;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.block.IDroppableInventory;
import net.silentchaos512.gear.crafting.recipe.compounder.CompoundingRecipe;
import net.silentchaos512.gear.gear.material.MaterialInstance;
import net.silentchaos512.gear.item.CompoundMaterialItem;
import net.silentchaos512.lib.tile.LockableSidedInventoryTileEntity;
import net.silentchaos512.lib.tile.SyncVariable;
import net.silentchaos512.lib.util.InventoryUtils;
import net.silentchaos512.lib.util.TimeUtils;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@SuppressWarnings("WeakerAccess")
public class CompounderTileEntity<R extends CompoundingRecipe> extends LockableSidedInventoryTileEntity implements ITickableTileEntity, IDroppableInventory {
    public static final int STANDARD_INPUT_SLOTS = 4;
    static final int WORK_TIME = TimeUtils.ticksFromSeconds(SilentGear.isDevBuild() ? 2 : 10);

    private final CompounderInfo<R> info;
    private final int[] allSlots;

    @SyncVariable(name = "Progress")
    private int progress = 0;
    @SyncVariable(name = "WorkEnabled")
    private boolean workEnabled = true;

    @SuppressWarnings("OverlyComplexAnonymousInnerClass") private final IIntArray fields = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return progress;
                case 1:
                    return workEnabled ? 1 : 0;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    progress = value;
                    break;
                case 1:
                    workEnabled = value != 0;
                    break;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public CompounderTileEntity(CompounderInfo<R> info) {
        super(info.getTileEntityType(), info.getInputSlotCount() + 2);
        this.info = info;
        this.allSlots = IntStream.range(0, this.items.size()).toArray();
    }

    protected IRecipeType<R> getRecipeType() {
        return this.info.getRecipeType();
    }

    @Nullable
    public R getRecipe() {
        if (world == null) return null;
        return world.getRecipeManager().getRecipe(getRecipeType(), this, world).orElse(null);
    }

    protected CompoundMaterialItem getOutputItem(MaterialList materials) {
        return this.info.getOutputItem();
    }

    protected ItemStack getWorkOutput(@Nullable R recipe, MaterialList materials) {
        if (recipe != null) {
            return recipe.getCraftingResult(this);
        }
        return getOutputItem(materials).create(materials);
    }

    public int getInputSlotCount() {
        return getSizeInventory() - 2;
    }

    public int getOutputSlotIndex() {
        return getSizeInventory() - 2;
    }

    public int getOutputHintSlotIndex() {
        return getSizeInventory() - 1;
    }

    public ItemStack getHintStack() {
        return getStackInSlot(getOutputHintSlotIndex());
    }

    public void encodeExtraData(PacketBuffer buffer) {
        buffer.writeByte(this.items.size());
        buffer.writeByte(this.fields.size());
    }

    private boolean areInputsEmpty() {
        for (int i = 0; i < this.getInputSlotCount(); ++i) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote || areInputsEmpty()) {
            // No point in doing anything on the client or when input slots are empty
            updateOutputHint(ItemStack.EMPTY);
            return;
        }

        R recipe = getRecipe();
        if (recipe != null) {
            // Inputs match a custom recipe
            doWork(recipe, MaterialList.empty());
        } else {
            // No recipe, but we might be able to make a generic compound
            MaterialList materials = getInputs();
            if (!hasMultipleMaterials(materials) || !canCompoundMaterials(materials)) {
                // Not a valid combination
                stopWork(true);
                return;
            }
            doWork(null, materials);
        }
    }

    private void doWork(@Nullable R recipe, MaterialList materials) {
        assert world != null;

        ItemStack current = getStackInSlot(getOutputSlotIndex());
        ItemStack output = getWorkOutput(recipe, materials);

        updateOutputHint(output);

        if (!current.isEmpty()) {
            int newCount = current.getCount() + output.getCount();

            if (!InventoryUtils.canItemsStack(current, output) || newCount > output.getMaxStackSize()) {
                // Output items do not match or not enough room
                stopWork(false);
                return;
            }
        }

        if (workEnabled) {
            if (progress < WORK_TIME) {
                ++progress;
            }

            if (progress >= WORK_TIME && !world.isRemote) {
                finishWork(recipe, materials, current);
            }
        } else {
            stopWork(false);
        }
    }

    private void updateOutputHint(ItemStack hintStack) {
        setInventorySlotContents(getOutputHintSlotIndex(), hintStack);
    }

    private void stopWork(boolean clearHintItem) {
        progress = 0;

        if (clearHintItem) {
            setInventorySlotContents(getOutputHintSlotIndex(), ItemStack.EMPTY);
        }
    }

    private void finishWork(@Nullable R recipe, MaterialList materials, ItemStack current) {
        progress = 0;
        for (int i = 0; i < getInputSlotCount(); ++i) {
            decrStackSize(i, 1);
        }

        ItemStack output = getWorkOutput(recipe, materials);
        if (!current.isEmpty()) {
            current.grow(output.getCount());
        } else {
            setInventorySlotContents(getOutputSlotIndex(), output);
        }
    }

    private static boolean hasMultipleMaterials(List<IMaterialInstance> materials) {
        if (materials.size() < 2) {
            return false;
        }

        IMaterial first = materials.get(0).get();
        for (int i = 1; i < materials.size(); ++i) {
            if (materials.get(i).get() != first) {
                return true;
            }
        }

        return false;
    }

    private boolean canCompoundMaterials(Iterable<IMaterialInstance> materials) {
        Set<PartType> partTypes = new HashSet<>(PartType.getValues());
        for (IMaterialInstance material : materials) {
            if (!material.hasAnyCategory(this.info.getCategories())) {
                return false;
            }
            partTypes.removeIf(pt -> !material.getPartTypes().contains(pt));
        }
        return !partTypes.isEmpty();
    }

    private MaterialList getInputs() {
        boolean allEmpty = true;

        for (int i = 0; i < getInputSlotCount(); ++i) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                allEmpty = false;
                break;
            }
        }

        if (allEmpty) {
            return MaterialList.empty();
        }

        MaterialList ret = MaterialList.empty();

        for (int i = 0; i < getInputSlotCount(); ++i) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                MaterialInstance material = MaterialInstance.from(stack);
                if (material != null && material.get().isSimple()) {
                    ret.add(material);
                } else {
                    return MaterialList.empty();
                }
            }
        }

        return ret;
    }

    @Override
    public NonNullList<ItemStack> getItemsToDrop() {
        // Gets the items dropped when the block is broken. Excludes the "hint stack"
        NonNullList<ItemStack> ret = NonNullList.create();
        for (int i = 0; i < this.getSizeInventory() - 1; ++i) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                ret.add(stack);
            }
        }
        return ret;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return allSlots.clone();
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index < getInputSlotCount()/* && isSimpleMaterial(stack)*/;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index == getOutputSlotIndex();
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(Util.makeTranslationKey("container", this.info.getBlock().getRegistryName()));
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new CompounderContainer(this.info.getContainerType(),
                id,
                player,
                this,
                this.fields,
                this.info.getCategories());
    }

    @Override
    public void read(BlockState state, CompoundNBT tags) {
        super.read(state, tags);
        SyncVariable.Helper.readSyncVars(this, tags);
    }

    @Override
    public CompoundNBT write(CompoundNBT tags) {
        CompoundNBT compoundTag = super.write(tags);
        SyncVariable.Helper.writeSyncVars(this, compoundTag, SyncVariable.Type.WRITE);
        return compoundTag;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tags = super.getUpdateTag();
        SyncVariable.Helper.writeSyncVars(this, tags, SyncVariable.Type.PACKET);
        return tags;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        CompoundNBT tags = packet.getNbtCompound();
        SyncVariable.Helper.readSyncVars(this, tags);
    }
}
