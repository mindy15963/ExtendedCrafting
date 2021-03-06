package com.blakebr0.extendedcrafting.container;

import com.blakebr0.cucumber.inventory.slot.OutputSlot;
import com.blakebr0.cucumber.inventory.slot.SingleSlot;
import com.blakebr0.extendedcrafting.tileentity.CompressorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.Function;

public class CompressorContainer extends Container {
	private final Function<PlayerEntity, Boolean> isUsableByPlayer;
	private final IIntArray data;
	private final BlockPos pos;

	private CompressorContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		this(type, id, playerInventory, p -> false, (new CompressorTileEntity()).getInventory(), new IntArray(10), buffer.readBlockPos());
	}

	private CompressorContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, IItemHandler inventory, IIntArray data, BlockPos pos) {
		super(type, id);
		this.isUsableByPlayer = isUsableByPlayer;
		this.data = data;
		this.pos = pos;

		this.addSlot(new OutputSlot(inventory, 0, 135, 48));
		this.addSlot(new SlotItemHandler(inventory, 1, 65, 48));
		this.addSlot(new SingleSlot(inventory, 2, 38, 48));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 112 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 170));
		}

		this.trackIntArray(data);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int slotNumber) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotNumber < 3) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				ItemStack inputStack = this.inventorySlots.get(1).getStack();
                if (inputStack.isEmpty() || (inputStack.isItemEqual(itemstack1) && inputStack.getCount() < inputStack.getMaxStackSize())) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slotNumber < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (slotNumber < 39) {
					if (!this.mergeItemStack(itemstack1, 3, 30, false)) {
						return ItemStack.EMPTY;
					}
				}
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return this.isUsableByPlayer.apply(player);
	}

	public static CompressorContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buffer) {
		return new CompressorContainer(ModContainerTypes.COMPRESSOR.get(), windowId, playerInventory, buffer);
	}

	public static CompressorContainer create(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, IItemHandler inventory, IIntArray data, BlockPos pos) {
		return new CompressorContainer(ModContainerTypes.COMPRESSOR.get(), windowId, playerInventory, isUsableByPlayer, inventory, data, pos);
	}

	public BlockPos getPos() {
		return this.pos;
	}

	public int getEnergyBarScaled(int pixels) {
		int i = this.getEnergyStored();
		int j = this.getMaxEnergyStored();
		return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
	}

	public int getMaterialBarScaled(int pixels) {
		int i = MathHelper.clamp(this.getMaterialCount(), 0, this.getMaterialsRequired());
		int j = this.getMaterialsRequired();
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	public int getProgressBarScaled(int pixels) {
		int i = this.getProgress();
		int j = this.getEnergyRequired();
		return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
	}

	public boolean isEjecting() {
		return this.data.get(2) > 0;
	}

	public boolean isLimitingInput() {
		return this.data.get(3) > 0;
	}

	public boolean hasRecipe() {
		return this.data.get(8) > 0;
	}

	public boolean hasMaterialStack() {
		return this.data.get(9) > 0;
	}

	public int getProgress() {
		return this.data.get(0);
	}

	public int getMaterialCount() {
		return this.data.get(1);
	}

	public int getEnergyStored() {
		return this.data.get(4);
	}

	public int getMaxEnergyStored() {
		return this.data.get(5);
	}

	public int getEnergyRequired() {
		return this.data.get(6);
	}

	public int getMaterialsRequired() {
		return this.data.get(7);
	}
}
