package com.wzssoft.comfysky.block.common;

import com.wzssoft.treasurehuntlib.block.common.ImplementedShoveledBlock;
import com.wzssoft.treasurehuntlib.utils.MixinDataHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;


/**
 * added since 17.0.6
 *
 * @reference falling block
 */
public class ShoveledSandBlock extends SandBlock implements ImplementedShoveledBlock {

    public ShoveledSandBlock(int color) {
        super(color, FabricBlockSettings.of(Material.AGGREGATE, MapColor.ORANGE).strength(0.5f).sounds(BlockSoundGroup.SAND));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.setToOriginalBlock(world, pos);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {

        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
        world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            this.setToOriginalBlock(world, pos);
            return;
        }
        if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
            return;
        }
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        this.configureFallingBlockEntity(fallingBlockEntity);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.getMaterial().isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof PistonExtensionBlock;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (!this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return getOriginalBlockState(ctx.getWorld(), ctx.getBlockPos());
        }
        return super.getPlacementState(ctx);
    }

    public void setToOriginalBlock(World world, BlockPos pos) {
        world.setBlockState(pos, getOriginalBlockState(world, pos), NOTIFY_LISTENERS);
    }

    private BlockState getOriginalBlockState(World world, BlockPos pos) {
        return MixinDataHelper.getReversedTreasureEnchantmentEffectBlockMap().get(this).getDefaultState();
    }

}
