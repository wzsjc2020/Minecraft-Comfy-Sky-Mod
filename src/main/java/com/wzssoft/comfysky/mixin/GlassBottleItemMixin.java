package com.wzssoft.comfysky.mixin;

import com.wzssoft.comfysky.block.hasNBT.DewFlowerBlock;
import com.wzssoft.comfysky.item.ComfySkyItems;
import com.wzssoft.comfysky.utils.ComfySkyMixinHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/**
 * added since 17.0.3
 * modified at 17.0.4
 * @specialNote strong override
 */

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin extends Item {


    public GlassBottleItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        List<AreaEffectCloudEntity> list = world.getEntitiesByClass(AreaEffectCloudEntity.class, user.getBoundingBox().expand(2.0), entity -> entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragonEntity);
        ItemStack itemStack = user.getStackInHand(hand);
        if (!list.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = list.get(0);
            areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5f);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            world.emitGameEvent((Entity) user, GameEvent.FLUID_PICKUP, user.getPos());
            return TypedActionResult.success(ComfySkyMixinHelper.fill(itemStack, user, new ItemStack(Items.DRAGON_BREATH)), world.isClient());
        }

        BlockHitResult hitResult = ComfySkyMixinHelper.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (((HitResult) hitResult).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }

        if (((HitResult) hitResult).getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);

            if (!world.canPlayerModifyAt(user, blockPos)) {
                return TypedActionResult.pass(itemStack);
            }
            if (blockState.getBlock() instanceof DewFlowerBlock block) {
                int dews = block.getDewAmount(world, blockState, blockPos);
                ItemStack dew_bottle = new ItemStack(ComfySkyItems.DEW_BOTTLE);
                dew_bottle.setDamage(dew_bottle.getMaxDamage() - dews);
                return TypedActionResult.success(ComfySkyMixinHelper.fill(itemStack, user, dew_bottle), world.isClient());
            }

            if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                world.emitGameEvent((Entity) user, GameEvent.FLUID_PICKUP, blockPos);
                return TypedActionResult.success(ComfySkyMixinHelper.fill(itemStack, user, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER)), world.isClient());
            }
        }
        return TypedActionResult.pass(itemStack);
    }
}