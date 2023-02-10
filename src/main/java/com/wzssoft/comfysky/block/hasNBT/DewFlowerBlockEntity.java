package com.wzssoft.comfysky.block.hasNBT;

import com.wzssoft.comfysky.block.ComfySkyBlockEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * added since 17.0.3
 * modified at 17.0.5
 */
public class DewFlowerBlockEntity extends BlockEntity {

    public long lastInteractTime = 0L;

    public DewFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ComfySkyBlockEntityType.DEW_FLOWER_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void synLastInteractTime(long lastInteractTime) {
        this.lastInteractTime = lastInteractTime;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putLong("lastInteractTime", lastInteractTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        lastInteractTime = nbt.getLong("lastInteractTime");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
