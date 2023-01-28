package com.wzssoft.comfysky.item;

import com.wzssoft.comfysky.ComfySkyMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ComfySkyItems {

    public static final Item CRUSHED_COAL = registerItem("crushed_coal", new Item(new FabricItemSettings().group(ComfySkyItemGroups.ExtraCraft).rarity(Rarity.COMMON)));
    public static final Item CRUSHED_IRON_ORE = registerItem("crushed_iron_ore", new Item(new FabricItemSettings().group(ComfySkyItemGroups.ExtraCraft).rarity(Rarity.COMMON)));
    public static final Item CRUSHED_STONE = registerItem("crushed_stone", new Item(new FabricItemSettings().group(ComfySkyItemGroups.ExtraCraft).rarity(Rarity.COMMON)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ComfySkyMod.MODID, name), item);
    }

    public static void registerModItem() {
        //当执行这一行命令时，会自动执行一遍空参构造
    }
}
