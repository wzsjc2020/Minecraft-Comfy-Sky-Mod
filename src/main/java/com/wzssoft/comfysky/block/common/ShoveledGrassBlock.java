package com.wzssoft.comfysky.block.common;

import com.wzssoft.treasurehuntlib.block.common.AbstractShoveledBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


/**
 * added since 17.0.1
 * modified at 17.0.6
 *
 * @specialNote com.wzssoft.modid.block.common.ShoveledGrassBlock.java
 */
public class ShoveledGrassBlock extends AbstractShoveledBlock {

    public static final int MAX_MOISTURE = 5;
    public static final IntProperty MOISTURE = IntProperty.of("moisture", 0, MAX_MOISTURE);

    public ShoveledGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, 0));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(MOISTURE);
        if (this.isWaterNearBy(world, pos) || world.hasRain(pos.up())) {
            if (i < MAX_MOISTURE) {
                world.setBlockState(pos, state.with(MOISTURE, MAX_MOISTURE), Block.NOTIFY_LISTENERS);
            }
        } else if (i > 0) {
            world.setBlockState(pos, state.with(MOISTURE, i - 1), Block.NOTIFY_LISTENERS);
        } else {
            this.setToOriginalBlock(world, pos);
        }
    }

    private boolean isWaterNearBy(World world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-2, 0, -2), pos.add(2, 1, 2))) {
            if (!world.getFluidState(blockPos).isIn(FluidTags.WATER)) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

}


