package com.wzssoft.comfysky.item.common;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.comfysky.item.ComfySkyItemTags;
import com.wzssoft.comfysky.utils.ComfySkyConstant;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * added since 17.0.4
 * modified at 17.0.7
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

        if (world.isClient || player == null) {
            return ActionResult.PASS;
        }

        if (state.isOf(Blocks.BARREL)) {

            BlockState newState = ComfySkyBlocks.PACKED_BARREL_BLOCK.getDefaultState();
            if (!tryPack(world, pos)) {
                player.sendMessage(Text.translatable("pack.fail").formatted(Formatting.RED));
                return ActionResult.PASS;
            }
            world.setBlockState(pos, newState.with(Properties.FACING, state.get(Properties.FACING)).with(Properties.OPEN, false), Block.NOTIFY_LISTENERS);

            if (!player.getAbilities().creativeMode) {
                stack.damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    /**
     * @specialNote added since 17.0.7
     */
    private boolean tryPack(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof BarrelBlockEntity entity) {
            for (int i = 0; i < entity.size(); i++) {
                ItemStack stack = entity.getStack(i);
                if (stack.isIn(ComfySkyItemTags.BARREL_PACKING_BLACKLIST)) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ComfySkyConstant.ITEM_BAR_COLOR;
    }
}
