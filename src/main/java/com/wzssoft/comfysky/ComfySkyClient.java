package com.wzssoft.comfysky;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

public class ComfySkyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        //shoveled grass block
        BlockRenderLayerMap.INSTANCE.putBlock(ComfySkyBlocks.SHOVELED_GRASS_BLOCK, RenderLayer.getCutout());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x5C9854, ComfySkyBlocks.SHOVELED_GRASS_BLOCK);
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> world.getColor(pos, BiomeColors.GRASS_COLOR)), ComfySkyBlocks.SHOVELED_GRASS_BLOCK);

        //dew flower
        BlockRenderLayerMap.INSTANCE.putBlock(ComfySkyBlocks.DEW_FLOWER_BLOCK, RenderLayer.getCutout());


    }
}
