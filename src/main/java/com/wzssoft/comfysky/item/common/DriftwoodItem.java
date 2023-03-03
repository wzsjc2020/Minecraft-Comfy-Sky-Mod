package com.wzssoft.comfysky.item.common;

import com.wzssoft.comfysky.block.common.DriftwoodBlock;
import com.wzssoft.comfysky.api.AmbiguousDirection;
import com.wzssoft.comfysky.utils.ComfyskyMathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

/**
 * added since 17.0.6
 * modified at 17.0.7
 *
 * @specialNote items can place at water and block
 * @reference PlaceableOnWaterItem
 * @reference ScaffoldingItem
 */
public class DriftwoodItem extends BlockItem {
    public static int MAX_DRIFTWOOD_EXTEND_RADIUS = 5;

    public DriftwoodItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    //可以放置在水上和世界最低点
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult blockHitResult = Item.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.withBlockPos(blockHitResult.getBlockPos().up());

        if(blockHitResult.getType() == HitResult.Type.MISS){
            return super.use(world, user, hand);
        }

        ActionResult actionResult;
        if (world.getBlockState(blockHitResult.getBlockPos()).isOf(this.getBlock()) && blockHitResult.getSide() == Direction.UP) {
            AmbiguousDirection direction = ComfyskyMathHelper.fromRotation(user.getYaw());
            BlockPos pos = tryExtendBlock(world, blockHitResult, direction);
            blockHitResult = blockHitResult2 = blockHitResult.withBlockPos(pos);
        }

        if (DriftwoodBlock.isReachWorldBottom(world, blockHitResult.getBlockPos())) {
            actionResult = super.useOnBlock(new ItemUsageContext(user, hand, blockHitResult));
        } else {
            actionResult = super.useOnBlock(new ItemUsageContext(user, hand, blockHitResult2));
        }

        //这一串代码意思就是消耗手上的物品
        return new TypedActionResult<ItemStack>(actionResult, user.getStackInHand(hand));
    }


    private BlockPos tryExtendBlock(World world, BlockHitResult blockHitResult, AmbiguousDirection direction) {

        BlockPos hitPos = blockHitResult.getBlockPos();
        BlockPos.Mutable mutable = hitPos.mutableCopy().move(AmbiguousDirection.getVector(direction));

        for (int i = 0; i < MAX_DRIFTWOOD_EXTEND_RADIUS; i++) {

            //检查有没有超越世界边界
            if (!world.isClient && !world.isInBuildLimit(mutable)) {
                break;
            }

            //检查位置上是否为空气，自动跳过浮木方块，遇到障碍停止
            if (world.getBlockState(mutable).isAir()) {
                return mutable;
            } else if (world.getBlockState(mutable).isOf(this.getBlock())) {
                mutable.move(AmbiguousDirection.getVector(direction));
            } else {
                break;
            }
        }
        return hitPos;
    }
}

