package com.wzssoft.comfysky.utils;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.treasurehuntlib.block.TreasureHuntLibBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class ComfySkyConstant {

    private static ArrayList<Block> SMALL_FLOWERS;

    public static void initConstant() {

        SMALL_FLOWERS = new ArrayList<Block>() {{
            add(Blocks.DANDELION);               //蒲公英
            add(Blocks.POPPY);                   //罂粟
            add(Blocks.BLUE_ORCHID);             //兰花
            add(Blocks.ALLIUM);                  //绒球葱
            add(Blocks.AZURE_BLUET);             //蓝花美耳草
            add(Blocks.RED_TULIP);               //红色郁金香
            add(Blocks.ORANGE_TULIP);            //橙色郁金香
            add(Blocks.WHITE_TULIP);             //白色郁金香
            add(Blocks.PINK_TULIP);              //粉色郁金香
            add(Blocks.OXEYE_DAISY);             //茜草花
            add(Blocks.CORNFLOWER);              //矢车菊
            add(Blocks.LILY_OF_THE_VALLEY);      //铃兰花
            add(ComfySkyBlocks.DEW_FLOWER_BLOCK);//露水花
        }};
    }

    public static int ITEM_BAR_COLOR = MathHelper.packRgb(51, 204, 204);

    public static ArrayList<Block> getSmallFlowerList() {
        if (SMALL_FLOWERS == null) {
            SMALL_FLOWERS = new ArrayList<Block>() {{
                add(Blocks.DANDELION);               //蒲公英
                add(Blocks.POPPY);                   //罂粟
                add(Blocks.BLUE_ORCHID);             //兰花
                add(Blocks.ALLIUM);                  //绒球葱
                add(Blocks.AZURE_BLUET);             //蓝花美耳草
                add(Blocks.RED_TULIP);               //红色郁金香
                add(Blocks.ORANGE_TULIP);            //橙色郁金香
                add(Blocks.WHITE_TULIP);             //白色郁金香
                add(Blocks.PINK_TULIP);              //粉色郁金香
                add(Blocks.OXEYE_DAISY);             //茜草花
                add(Blocks.CORNFLOWER);              //矢车菊
                add(Blocks.LILY_OF_THE_VALLEY);      //铃兰花
                add(ComfySkyBlocks.DEW_FLOWER_BLOCK);//露水花
            }};
        }
        return SMALL_FLOWERS;
    }

    public static final int  MAX_BARREL_DISPLAY_INV = 4;
}
