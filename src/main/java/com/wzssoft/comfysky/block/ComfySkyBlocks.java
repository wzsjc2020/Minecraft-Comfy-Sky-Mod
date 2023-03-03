package com.wzssoft.comfysky.block;

import com.wzssoft.comfysky.ComfySkyMod;
import com.wzssoft.comfysky.block.common.DriftwoodBlock;
import com.wzssoft.comfysky.block.common.ShoveledSandBlock;
import com.wzssoft.comfysky.block.hasNBT.DewFlowerBlock;
import com.wzssoft.comfysky.block.common.ShoveledGrassBlock;
import com.wzssoft.comfysky.block.hasNBT.PackedBarrelBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ComfySkyBlocks {

    public static final Block SHOVELED_GRASS_BLOCK = registerBlock("shoveled_grass_block",
            new ShoveledGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(0.6f).sounds(BlockSoundGroup.GRASS)));

    public static final Block DEW_FLOWER_BLOCK = registerBlock("dew_flower",new DewFlowerBlock());

    public static final Block PACKED_BARREL_BLOCK = registerBlock("packed_barrel",
            new PackedBarrelBlock(FabricBlockSettings.of(Material.WOOD).strength(2.5f).sounds(BlockSoundGroup.WOOD)));

    public static final Block DRIFTWOOD_BLOCK = registerBlock("driftwood",
            new DriftwoodBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()));

    public static final Block SHOVELED_RED_SAND_BLOCK = registerBlock("shoveled_red_sand", new ShoveledSandBlock(11098145));

    public static final Block DRIFT_BAMBOO_BLOCK = registerBlock("drift_bamboo",
            new DriftwoodBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(ComfySkyMod.MODID, name), block);
    }

    public static void registerModBlock() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
