package com.wzssoft.comfysky.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ComfySkyMixinHelper {

    public static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float i = MathHelper.sin(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float j = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float k = MathHelper.sin(-f * ((float)Math.PI / 180));
        float l = i * j;
        float m = k;
        float n = h * j;
        double d = 5.0;
        Vec3d vec3d2 = vec3d.add((double)l * 5.0, (double)m * 5.0, (double)n * 5.0);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
    }

    public static ItemStack fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        player.incrementStat(Stats.USED.getOrCreateStat(Items.GLASS_BOTTLE));
        return ItemUsage.exchangeStack(stack, player, outputStack);
    }
}
