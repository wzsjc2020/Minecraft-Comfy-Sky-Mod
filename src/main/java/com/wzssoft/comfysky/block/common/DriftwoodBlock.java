package com.wzssoft.comfysky.block.common;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * added since 17.0.6
 *
 * @specialNote for better performance, do not use block entity render
 * @specialNote can place above water source block
 * @reference pressure plate
 * @reference ScaffoldingBlock
 * @reference campfire
 * @reference lily pad
 */
public class DriftwoodBlock extends Block {

    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    public static final Box BOX = new Box(0.125, 0.20, 0.125, 0.875, 0.30, 0.875);

    public DriftwoodBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DOWN, false).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        FluidState fluidState = world.getFluidState(blockPos);
        FluidState fluidState2 = world.getFluidState(pos);

        return (fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.EMPTY) || isReachWorldBottom(world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(DOWN, false).with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            world.createAndScheduleBlockTick(new BlockPos(pos), this, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean hasCollision = isEntityOnCollision(world, pos);
        if (hasCollision) {
            if (state.get(DOWN)) return;
            world.setBlockState(pos, state.with(DOWN, true), Block.NOTIFY_LISTENERS);
            return;
        }
        world.setBlockState(pos, state.with(DOWN, false), Block.NOTIFY_LISTENERS);
    }

    protected boolean isEntityOnCollision(World world, BlockPos pos) {
        Box box = BOX.offset(pos);
        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, box);

        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity.canAvoidTraps()) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isReachWorldBottom(WorldView world, BlockPos pos) {
        return world.getBottomY() == pos.getY();
    }

    public static boolean isDown(BlockState state) {
        return state.get(DOWN);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN, FACING);
    }
}
