package com.wzssoft.comfysky.utils;

import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.treasurehuntlib.block.TreasureHuntLibBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class ComfySkyConstant {

    public static ArrayList<Block> VANILLA_PLANT_LIST;
    public static ArrayList<Block> VANILLA_FLOWER_LIST;
    public static ArrayList<Block> GRASS_BLOCK_PLANT_SURVIVE_LIST;
    public static ArrayList<Block> GRASS_BLOCK_SEEDLING_SURVIVE_LIST;

    public static int MAX_BARREL_DISPLAY_INV;

    public static void initConstant() {

        VANILLA_PLANT_LIST = new ArrayList<>() {{
            add(Blocks.CARROTS);
            add(Blocks.POTATOES);
            add(Blocks.BEETROOTS);
            add(Blocks.WHEAT);

        }};

        VANILLA_FLOWER_LIST = new ArrayList<Block>() {{
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
        }};


        GRASS_BLOCK_PLANT_SURVIVE_LIST = new ArrayList<>() {{
            add(ComfySkyBlocks.DEW_FLOWER_BLOCK);
            addAll(VANILLA_FLOWER_LIST);
        }};

        GRASS_BLOCK_SEEDLING_SURVIVE_LIST = new ArrayList<>() {{
            add(TreasureHuntLibBlocks.FLOWER_SEEDLING_BLOCK);
            addAll(VANILLA_PLANT_LIST);
        }};
    }


    public static int ITEM_BAR_COLOR = MathHelper.packRgb(51, 204, 204);


    public static int getMaxBarrelDisplayInv() {
        if (MAX_BARREL_DISPLAY_INV == 0) {
            MAX_BARREL_DISPLAY_INV = 4;
        }
        return MAX_BARREL_DISPLAY_INV;
    }
}
