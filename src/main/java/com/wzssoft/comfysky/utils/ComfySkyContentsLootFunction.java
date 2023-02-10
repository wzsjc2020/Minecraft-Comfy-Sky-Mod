package com.wzssoft.comfysky.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.wzssoft.comfysky.ComfySkyMod;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;


/**
 * added since 17.0.4
 *
 * @specialNote see ConditionLoopFunction
 */
public class ComfySkyContentsLootFunction extends ConditionalLootFunction {

    private static final LootFunctionType TYPE = new LootFunctionType(new Serializer());

    protected ComfySkyContentsLootFunction(LootCondition[] conditions) {
        super(conditions);
    }

    public static void registerModContentsLootFunction() {
        Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(ComfySkyMod.MODID, "barrel_contents"), TYPE);
    }

    public static ConditionalLootFunction.Builder<?> builder() {
        return builder(ComfySkyContentsLootFunction::new);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {

        if (stack.isEmpty()) return stack;
        if (context.get(LootContextParameters.BLOCK_ENTITY) instanceof BarrelBlockEntity entity && !entity.isEmpty()) {

            //Mojang真行，全给我写成protected是吧 XD
            DefaultedList<ItemStack> inv = DefaultedList.ofSize(entity.size(), ItemStack.EMPTY);
            for (int i = 0; i < entity.size(); i++) {
                inv.set(i, entity.getStack(i));
            }

            NbtCompound nbtCompound = Inventories.writeNbt(new NbtCompound(), inv);
            BlockItem.setBlockEntityNbt(stack, BlockEntityType.BARREL, nbtCompound);
        }
        return stack;
    }

    @Override
    public LootFunctionType getType() {
        return TYPE;
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<ComfySkyContentsLootFunction> {
        @Override
        public ComfySkyContentsLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return new ComfySkyContentsLootFunction(conditions);
        }
    }
}
