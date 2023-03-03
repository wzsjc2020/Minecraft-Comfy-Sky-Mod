package com.wzssoft.comfysky.mixin;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.comfysky.utils.ComfySkyConstant;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BarrelBlock.class)
public class BarrelBlockMixin extends Block {


    public BarrelBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onStateReplaced", at = @At("HEAD"), cancellable = true)
    public void injectOnStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo callbackInfo) {

        if(newState.isOf(ComfySkyBlocks.PACKED_BARREL_BLOCK)){
            callbackInfo.cancel();
            return;
        }
        return;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {

        //当有nbt时添加Tooltip
        var nbt = BlockItem.getBlockEntityNbt(stack);
        if (nbt == null) return;

        DefaultedList<ItemStack> inv = DefaultedList.ofSize(27, ItemStack.EMPTY);
        Inventories.readNbt(nbt, inv);

        /**
         * @specialNote following code is copied from Shculker Box Block
         */
        int i = 0;
        int j = 0;
        for (ItemStack itemStack : inv) {
            if (itemStack.isEmpty()) continue;
            ++j;
            if (i > ComfySkyConstant.MAX_BARREL_DISPLAY_INV) continue;
            ++i;
            MutableText mutableText = itemStack.getName().copy();
            mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
            tooltip.add(mutableText);
        }
        if (j - i > 0) {
            tooltip.add(Text.translatable("container.minecraft.barrel.more", j - i).formatted(Formatting.ITALIC));
        }
    }
}
