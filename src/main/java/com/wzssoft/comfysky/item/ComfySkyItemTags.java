package com.wzssoft.comfysky.item;

import com.wzssoft.comfysky.ComfySkyMod;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ComfySkyItemTags {


    private static TagKey<Item> of(String name) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(ComfySkyMod.MODID, name));
    }

    public static void registerModItemTags() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
