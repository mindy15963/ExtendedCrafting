package com.blakebr0.extendedcrafting.tileentity;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import com.blakebr0.cucumber.lib.Localizable;
import com.blakebr0.cucumber.tileentity.BaseInventoryTileEntity;
import com.blakebr0.extendedcrafting.container.AdvancedTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;

public class AdvancedTableTileEntity extends BaseInventoryTileEntity implements INamedContainerProvider {
    private final BaseItemStackHandler inventory = new BaseItemStackHandler(25);

    public AdvancedTableTileEntity() {
        super(ModTileEntities.ADVANCED_TABLE.get());
    }

    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ITextComponent getDisplayName() {
        return Localizable.of("container.extendedcrafting.advanced_table").build();
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        return AdvancedTableContainer.create(windowId, playerInventory, this::isUsableByPlayer, this.inventory);
    }
}
