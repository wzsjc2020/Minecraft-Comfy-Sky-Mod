package com.wzssoft.comfysky.api;

import net.minecraft.util.math.Vec3i;

/**
 * added since 17.0.7
 *
 * @reference direction
 * @specialNote pattern
 * <p>
 * 7  -  0  -  1
 * ----------------
 * 6 -      -  2
 * ----------------
 * 5 -  4   -  3
 */


public enum AmbiguousDirection {

    NORTH(0, "north", new Vec3i(0, 0, -1)),
    NORTH_EAST(1, "north_east", new Vec3i(1, 0, -1)),
    EAST(2, "east", new Vec3i(1, 0, 0)),
    SOUTH_EAST(3, "south_east", new Vec3i(1, 0, 1)),
    SOUTH(4, "south", new Vec3i(0, 0, 1)),
    SOUTH_WEST(5, "south_west", new Vec3i(-1, 0, 1)),
    WEST(6, "west", new Vec3i(-1, 0, 0)),
    NORTH_WEST(7, "north_west", new Vec3i(-1, 0, -1));

    private final int id;
    private final String name;
    private final Vec3i vector;

    AmbiguousDirection(int id, String name, Vec3i vector) {
        this.id = id;
        this.name = name;
        this.vector = vector;
    }

    public static AmbiguousDirection[] VALUES = AmbiguousDirection.values();

    public static AmbiguousDirection fromHorizontal(int identifier) {
        return VALUES[identifier];
    }

    public static int getIdentifier(AmbiguousDirection ambiguousDirection) {
        return ambiguousDirection.id;
    }

    public static String getName(AmbiguousDirection ambiguousDirection) {
        return ambiguousDirection.name;
    }

    public static Vec3i getVector(AmbiguousDirection ambiguousDirection) {
        return ambiguousDirection.vector;
    }

}