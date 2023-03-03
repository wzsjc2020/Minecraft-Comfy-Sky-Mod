package com.wzssoft.comfysky.block.hasNBT;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

/**
 * added since 17.0.3
 * modified at 17.0.4, 17.0.7
 *
 * @specialNote can grow on shoveled block
 * @reference extra rs craft mod bluestone block
 * @reference poppy block
 * @reference daylight sensor
 */
public class DewFlowerBlock extends FlowerBlock implements BlockEntityProvider {

    public DewFlowerBlock() {
        super(StatusEffects.SPEED, 4, Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offsetType(OffsetType.XZ));
    }

    /**
     * added since 17.0.4
     * @specialNote when you plant a dew_flower, nbt will syn lastInteractTime
     */
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof DewFlowerBlockEntity entity) {
            entity.synLastInteractTime(world.getTimeOfDay());
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        }
    }

    public int getDewAmount(World world, BlockState state, BlockPos pos) {

        DewFlowerBlockEntity entity = null;
        long lastInteractTime = 0L;
        int i = 0;
        if (world.getBlockEntity(pos) instanceof DewFlowerBlockEntity blockEntity) {
            entity = blockEntity;
            lastInteractTime = entity.lastInteractTime;
        }

        if (entity != null && isNewDay(lastInteractTime, world.getTimeOfDay()) && world.getDimension().hasSkyLight()) {

            long timeOfDay = world.getTimeOfDay() % 24000L;
            int rainBonus = world.isRaining() ? 1 : 0;

            if (timeOfDay < 3000L) {
                i = 2 + rainBonus;
            } else if (timeOfDay < 6000L) {
                i = 1 + rainBonus;
            }

            entity.synLastInteractTime(world.getTimeOfDay());
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        }
        return i;
    }

    private boolean isNewDay(long lastInteractTime, long currentTime) {
        if (currentTime < lastInteractTime) {
            return true;
        }

        return (currentTime / 24000L - lastInteractTime / 24000L) != 0;
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

