package com.wzssoft.comfysky.block.hasNBT;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * added since 17.0.3
 *
 * @specialNote can grow on shoveled block
 * @reference poppy block
 * @reference daylight sensor
 */
public class DewFlowerBlock extends FlowerBlock implements BlockEntityProvider {

    public DewFlowerBlock() {
        super(StatusEffects.SPEED, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(OffsetType.XZ));
    }


    public int getDewAmount(World world, BlockState state, BlockPos pos) {

        DewFlowerBlockEntity entity = null;
        long lastInteractTime = 0L;
        if (world.getBlockEntity(pos) instanceof DewFlowerBlockEntity blockEntity) {
            entity = blockEntity;
            lastInteractTime = entity.lastInteractTime;
        }

        if (entity != null && isNewDay(lastInteractTime, world.getTimeOfDay()) && world.getDimension().hasSkyLight()) {

            long timeOfDay = world.getTimeOfDay() % 24000L;
            int rainBonus = world.isRaining() ? 1 : 0;

            if (timeOfDay < 3000L) {

                entity.synLastInteractTime(world.getTimeOfDay());
                return 2 + rainBonus;

            } else if (timeOfDay < 6000L) {

                entity.synLastInteractTime(world.getTimeOfDay());
                return 1 + rainBonus;
            }
        }
        return 0;
    }


    private boolean isNewDay(long lastInteractTime, long currentTime) {
        if (currentTime < lastInteractTime) {
            return true;
        }

        return (currentTime - lastInteractTime) / 24000L != 0;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DewFlowerBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}

