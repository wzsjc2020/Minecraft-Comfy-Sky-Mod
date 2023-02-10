package com.wzssoft.comfysky.item.common;

import com.wzssoft.comfysky.block.hasNBT.DewFlowerBlock;
import com.wzssoft.comfysky.utils.ComfySkyConstant;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

/**
 * added since 17.0.3
 *
 * @reference bottle
 * @reference cauldron behevour
 * @specialNote max damage = 50
 */
public class DewBottleItem extends Item {
    public DewBottleItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        if (playerEntity == null || world.isClient) {
            return ActionResult.PASS;
        }

        if (state.getBlock() instanceof DewFlowerBlock block) {

            int dews = block.getDewAmount(world, state, pos);
            int damage = 0;
            int i = itemStack.getDamage() - dews;       //calculate the bottle is full or not
            if (i > 0) {
                damage = i;
            }
            itemStack.setDamage(damage);                //set damage(also amount of dews)
            return ActionResult.SUCCESS;

        } else if (state.getBlock() instanceof CauldronBlock block) {

            if (isFull(itemStack)) {
                playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
                playerEntity.incrementStat(Stats.USE_CAULDRON);
                world.setBlockState(pos, Blocks.WATER_CAULDRON.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                return ActionResult.SUCCESS;
            }

        } else if (state.getBlock() instanceof LeveledCauldronBlock block) {

            if (isFull(itemStack) && !block.isFull(state)) {
                playerEntity.setStackInHand(context.getHand(), ItemUsage.exchangeStack(itemStack, playerEntity, new ItemStack(Items.GLASS_BOTTLE)));
                playerEntity.incrementStat(Stats.USE_CAULDRON);
                world.setBlockState(pos, (BlockState) state.cycle(LeveledCauldronBlock.LEVEL));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private boolean isFull(ItemStack itemStack) {
        return itemStack.getDamage() == 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ComfySkyConstant.ITEM_BAR_COLOR;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}



