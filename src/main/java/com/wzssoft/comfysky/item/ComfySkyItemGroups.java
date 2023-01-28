package com.wzssoft.comfysky.item;

import com.wzssoft.comfysky.ComfySkyMod;
import com.wzssoft.treasurehuntlib.item.TreasureHuntLibBlockItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ComfySkyItemGroups {

    public static final ItemGroup ExtraCraft = FabricItemGroupBuilder.build(
            new Identifier(ComfySkyMod.MODID, "tab"), () -> new ItemStack(TreasureHuntLibBlockItems.FLOWER_SEEDS_BLOCK_ITEM));
}
