package com.wzssoft.comfysky.block;

import com.wzssoft.comfysky.ComfySkyMod;
import com.wzssoft.comfysky.block.hasNBT.DewFlowerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ComfySkyBlockEntityType {

    public static final BlockEntityType<DewFlowerBlockEntity> DEW_FLOWER_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ComfySkyMod.MODID, "dew_flower"), FabricBlockEntityTypeBuilder.create(DewFlowerBlockEntity::new, ComfySkyBlocks.DEW_FLOWER_BLOCK).build(null));


    public static void registerModBlockEntityType() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
