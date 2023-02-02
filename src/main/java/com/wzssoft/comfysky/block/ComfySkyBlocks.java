package com.wzssoft.comfysky.block;

import com.wzssoft.comfysky.ComfySkyMod;
import com.wzssoft.comfysky.block.hasNBT.DewFlowerBlock;
import com.wzssoft.comfysky.block.common.ShoveledGrassBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ComfySkyBlocks {

    public static final Block SHOVELED_GRASS_BLOCK = registerBlock("shoveled_grass_block",
            new ShoveledGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(0.6f).sounds(BlockSoundGroup.GRASS)));

    public static final Block DEW_FLOWER_BLOCK = registerBlock("dew_flower",new DewFlowerBlock());


    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(ComfySkyMod.MODID, name), block);
    }

    public static void registerModBlock() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
