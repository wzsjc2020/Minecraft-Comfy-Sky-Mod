package com.wzssoft.comfysky.utils;

import com.wzssoft.comfysky.item.ComfySkyItems;
import com.wzssoft.treasurehuntlib.enchantment.TreasureHuntLibEnchantments;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

/**
 * added since 17.0.1
 *
 * @specialNote see all conditions at LootCondition.java
 * @specialNote this is only an example, do not copy this loot table to your own mod
 */
public class ComfySkyLootTableModifier {

    public static final Identifier GRASS_BLOCK_ID = new Identifier("minecraft", "blocks/grass_block");
    public static final Identifier RED_SAND_ID = new Identifier("minecraft", "blocks/red_sand");

    public static void registerModModifyLootTable() {

        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {

            if (GRASS_BLOCK_ID.equals(id)) {
                LootTable.Builder table = LootTable.builder();
                LootCondition.Builder match_enchantment_silktouch = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.exactly(1))));
                LootCondition.Builder match_enchantment_treasure = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(TreasureHuntLibEnchantments.TREASURE, NumberRange.IntRange.exactly(1))));
                LootCondition.Builder explosion = SurvivesExplosionLootCondition.builder();

                LootPool.Builder iron_ore_builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(AlternativeEntry.builder(

                                ItemEntry.builder(Items.GRASS_BLOCK).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(match_enchantment_silktouch),
                                GroupEntry.create(
                                        ItemEntry.builder(ComfySkyItems.CRUSHED_IRON_ORE).weight(1).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))),
                                        ItemEntry.builder(Items.AIR).weight(20)
                                ).conditionally(match_enchantment_treasure),
                                ItemEntry.builder(Items.DIRT).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(explosion)));

                table.pool(iron_ore_builder);

                LootPool.Builder coal_builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(AlternativeEntry.builder(

                                ItemEntry.builder(Items.AIR).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(match_enchantment_silktouch),
                                GroupEntry.create(
                                        ItemEntry.builder(ComfySkyItems.CRUSHED_COAL).weight(1).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))),
                                        ItemEntry.builder(Items.AIR).weight(20)
                                ).conditionally(match_enchantment_treasure),
                                ItemEntry.builder(Items.AIR).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(explosion)));

                table.pool(coal_builder);

                LootPool.Builder stone_builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(AlternativeEntry.builder(

                                ItemEntry.builder(Items.AIR).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(match_enchantment_silktouch),
                                GroupEntry.create(
                                        ItemEntry.builder(ComfySkyItems.CRUSHED_STONE).weight(1).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))),
                                        ItemEntry.builder(Items.AIR).weight(20)
                                ).conditionally(match_enchantment_treasure),
                                ItemEntry.builder(Items.AIR).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(explosion)));

                table.pool(stone_builder);
                return table.build();
            }

            if(RED_SAND_ID.equals(id)){
                LootTable.Builder table = LootTable.builder();
                LootCondition.Builder match_enchantment_treasure = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(TreasureHuntLibEnchantments.TREASURE, NumberRange.IntRange.exactly(1))));
                LootCondition.Builder explosion = SurvivesExplosionLootCondition.builder();

                LootPool.Builder gold_ore_builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(AlternativeEntry.builder(
                                GroupEntry.create(
                                        ItemEntry.builder(ComfySkyItems.CRUSHED_GOLD_ORE).weight(1).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))),
                                        ItemEntry.builder(Items.AIR).weight(20)
                                ).conditionally(match_enchantment_treasure),
                                ItemEntry.builder(Items.RED_SAND).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f))).conditionally(explosion)));

                table.pool(gold_ore_builder);
                return table.build();
            }


            return original;
        });
    }
}
