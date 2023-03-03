package com.wzssoft.comfysky.mixin;

import com.wzssoft.comfysky.utils.ComfySkyConstant;
import com.wzssoft.treasurehuntlib.block.hiden.FlowerSeedlingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

/**
 * added since 17.0.6
 * modified at 17.0.7
 */

@Mixin(FlowerSeedlingBlock.class)
public class FlowerSeedlingBlockMixin {

    @Inject(method = "mature", at = @At("HEAD"), cancellable = true)
    public void injectMature(World world, BlockState state, BlockPos pos, CallbackInfo callbackInfo) {
        ArrayList<Block> matureFlowers = ComfySkyConstant.getSmallFlowerList();
        if (matureFlowers != null && !matureFlowers.isEmpty()) {
            Block flowerBlock = matureFlowers.get(world.random.nextInt(matureFlowers.size()));
            world.setBlockState(pos, flowerBlock.getDefaultState(), Block.NOTIFY_LISTENERS);

            //creative placement
            flowerBlock.onPlaced(world, pos, state, null, ItemStack.EMPTY);
        }
        callbackInfo.cancel();
    }
}
