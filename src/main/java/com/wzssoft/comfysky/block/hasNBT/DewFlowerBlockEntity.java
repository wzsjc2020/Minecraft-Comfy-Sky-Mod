package com.wzssoft.comfysky.block.hasNBT;

import com.wzssoft.comfysky.block.ComfySkyBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

/**
 * added since 17.0.3
 */
public class DewFlowerBlockEntity extends BlockEntity {

    public long lastInteractTime;

    public DewFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ComfySkyBlockEntityType.DEW_FLOWER_BLOCK_ENTITY_TYPE, pos, state);
    }


    public void synLastInteractTime(long lastInteractTime) {
        this.lastInteractTime = lastInteractTime;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putLong("long lastInteractTime", lastInteractTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        lastInteractTime = nbt.getLong("lastInteractTime");
    }

}
