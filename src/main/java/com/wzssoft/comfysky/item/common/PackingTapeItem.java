package com.wzssoft.comfysky.item.common;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.comfysky.utils.ComfySkyConstant;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * added since 17.0.4
 *
 * @specialNote damage = 5
 * @reference shear item
 */
public class PackingTapeItem extends Item {

    public PackingTapeItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();

        if (player == null || world.isClient) {
            return ActionResult.PASS;
        }

        if (state.isOf(Blocks.BARREL)) {
            BlockState newState = ComfySkyBlocks.PACKED_BARREL_BLOCK.getDefaultState();
            world.setBlockState(pos, newState.with(Properties.FACING, state.get(Properties.FACING)).with(Properties.OPEN, false), Block.NOTIFY_LISTENERS);

            if (!player.getAbilities().creativeMode) {
                stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(context.getHand()));
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ComfySkyConstant.ITEM_BAR_COLOR;
    }
}
