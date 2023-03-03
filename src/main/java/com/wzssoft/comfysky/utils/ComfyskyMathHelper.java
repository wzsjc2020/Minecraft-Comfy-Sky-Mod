package com.wzssoft.comfysky.utils;

import com.wzssoft.comfysky.api.AmbiguousDirection;
import net.minecraft.util.math.MathHelper;

/**
 * added since 17.0.7
 */
public class ComfyskyMathHelper {


    /**
     * added since 17.0.7
     *
     * @specialNote get direction from player's facing
     * @specialNote player facing is opposite to world direction,so you need add extra 180 degrees
     * @specialNote silly method, but it works fine
     */
    public static AmbiguousDirection fromRotation(double rotation) {
        return AmbiguousDirection.fromHorizontal(MathHelper.floor(rotation / 45.0 + 12.5) % 8);
    }
}
