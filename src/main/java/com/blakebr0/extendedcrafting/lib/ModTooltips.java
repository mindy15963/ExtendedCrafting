package com.blakebr0.extendedcrafting.lib;

import com.blakebr0.cucumber.lib.Tooltip;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.ModList;

public class ModTooltips {
    public static final Tooltip EMPTY = new Tooltip("tooltip.extendedcrafting.empty");
    public static final Tooltip EJECT = new Tooltip("tooltip.extendedcrafting.eject");
    public static final Tooltip EJECTING = new Tooltip("tooltip.extendedcrafting.ejecting");
    public static final Tooltip LIMITED_INPUT = new Tooltip("tooltip.extendedcrafting.limited_input");
    public static final Tooltip UNLIMITED_INPUT = new Tooltip("tooltip.extendedcrafting.unlimited_input");
    public static final Tooltip TIER = new Tooltip("tooltip.extendedcrafting.tier");
    public static final Tooltip CRAFTING = new Tooltip("tooltip.extendedcrafting.crafting");
    public static final Tooltip MODE = new Tooltip("tooltip.extendedcrafting.mode");
    public static final Tooltip CHANGED_MODE = new Tooltip("tooltip.extendedcrafting.changed_mode");
    public static final Tooltip NUM_ITEMS = new Tooltip("tooltip.extendedcrafting.num_items");
    public static final Tooltip REQUIRES_TABLE = new Tooltip("tooltip.extendedcrafting.requires_table");
    public static final Tooltip ITEMS_REQUIRED = new Tooltip("tooltip.extendedcrafting.items_required");
    public static final Tooltip SINGULARITY_ID = new Tooltip("tooltip.extendedcrafting.singularity_id");
    public static final Tooltip ADDED_BY = new Tooltip("tooltip.extendedcrafting.added_by");

    public static ITextComponent getAddedByTooltip(String modid) {
        String name = ModList.get().getModFileById(modid).getMods().get(0).getDisplayName();
        return ADDED_BY.args(name).build();
    }
}
