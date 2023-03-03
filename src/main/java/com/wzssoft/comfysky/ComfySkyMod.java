package com.wzssoft.comfysky;

import com.wzssoft.comfysky.block.ComfySkyBlockEntityType;
import com.wzssoft.comfysky.block.ComfySkyBlocks;
import com.wzssoft.comfysky.block.common.ShoveledGrassBlock;
import com.wzssoft.comfysky.item.ComfySkyBlockItems;
import com.wzssoft.comfysky.item.ComfySkyItemTags;
import com.wzssoft.comfysky.item.ComfySkyItems;
import com.wzssoft.comfysky.utils.ComfySkyConstant;
import com.wzssoft.comfysky.utils.ComfySkyContentsLootFunction;
import com.wzssoft.comfysky.utils.ComfySkyLootTableModifier;
import com.wzssoft.treasurehuntlib.TreasureHuntLibClient;
import com.wzssoft.treasurehuntlib.TreasureHuntLibRegister;
import com.wzssoft.treasurehuntlib.block.TreasureHuntLibBlocks;
import com.wzssoft.treasurehuntlib.block.hiden.FlowerSeedlingBlock;
import com.wzssoft.treasurehuntlib.utils.TreasureHuntLibConstant;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComfySkyMod implements ModInitializer {

    public static final String MODID = "comfysky";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {

        ComfySkyBlockEntityType.registerModBlockEntityType();
        ComfySkyItems.registerModItem();
        ComfySkyBlocks.registerModBlock();
        ComfySkyBlockItems.registerModBlockItems();
        ComfySkyItemTags.registerModItemTags();
        ComfySkyLootTableModifier.registerModModifyLootTable();
        ComfySkyContentsLootFunction.registerModContentsLootFunction();
        ComfySkyConstant.initConstant();

        //register treasure enchantment effect block
        TreasureHuntLibRegister.registerTreasureEnchantmentEffectBlock(Blocks.GRASS_BLOCK, ComfySkyBlocks.SHOVELED_GRASS_BLOCK);
        TreasureHuntLibRegister.registerTreasureEnchantmentEffectBlock(Blocks.RED_SAND, ComfySkyBlocks.SHOVELED_RED_SAND_BLOCK);

        //register farmland
        TreasureHuntLibRegister.registerFarmlandProperty(ComfySkyBlocks.SHOVELED_GRASS_BLOCK, ShoveledGrassBlock.MOISTURE);
    }
}
