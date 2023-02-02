package com.wzssoft.comfysky.item;

import com.wzssoft.comfysky.ComfySkyMod;
import com.wzssoft.comfysky.block.ComfySkyBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ComfySkyBlockItems {

    public static final Item SHOVELED_GRASS_BLOCK_ITEM = registerBlockItem("shoveled_grass_block",
            new BlockItem(ComfySkyBlocks.SHOVELED_GRASS_BLOCK, new FabricItemSettings().group(ComfySkyItemGroups.ExtraCraft).rarity(Rarity.COMMON)));

    public static final Item DEW_FLOWER_BLOCK_ITEM = registerBlockItem("dew_flower",
            new AliasedBlockItem(ComfySkyBlocks.DEW_FLOWER_BLOCK, new FabricItemSettings().group(ComfySkyItemGroups.ExtraCraft).rarity(Rarity.COMMON)));


    public static Item registerBlockItem(String name, BlockItem blockitem) {
        return Registry.register(Registry.ITEM, new Identifier(ComfySkyMod.MODID, name), blockitem);
    }


    public static void registerModBlockItems() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
