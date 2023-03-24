# 如何制作一个宝藏方块V17.2.2



## 下载Example Mod

**重要**：如果你已经有自己的项目，则可以忽略这一步

[wzsjc2020/fabric-example-mod-1.19.2-prefab (github.com)](https://github.com/wzsjc2020/fabric-example-mod-1.19.2-prefab)

复制此仓库内所有的文件到本地



## 添加依赖包

```
build.gradle
```

```java
repositories {
	maven {
		url "https://maven.pkg.github.com/wzsjc2020/treasure-hunt-lib"
            credentials {
                username = "wzsjc2020"
                password = "see github 'how to use'"
            }
	}
}

dependencies {
	modImplementation ("com.wzssoft:treasurehuntlib:${"1.19.2-17.2.2"}")
}
```



## 添加第一个方块

### 创建方块

```
com.wzssoft.modid.block.common.ShoveledGrassBlock.java
```

```java
package com.wzssoft.modid.block.common;

import com.wzssoft.treasurehuntlib.block.common.AbstractShoveledBlock;

/**
 * added since 17.0.1
 */
public class ShoveledGrassBlock extends AbstractShoveledBlock {


    public ShoveledGrassBlock(Settings settings) {
        super(settings);
    }
}
```



### 注册方块物品

这个教程我们以制作一个可以挖掘的草方块为例

```
com.wzssoft.modid.block.MODIDBlocks.java
```

```java
public static final Block SHOVELED_GRASS_BLOCK = registerBlock("shoveled_grass_block",
        new ShoveledGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(0.6f).sounds(BlockSoundGroup.GRASS)));
```

```
com.wzssoft.modid.item.MODIDBlockItems.java
```

```java
public static final Item SHOVELED_GRASS_BLOCK_ITEM = registerBlockItem("shoveled_grass_block",
        new BlockItem(MODIDBlocks.SHOVELED_GRASS_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS).rarity(Rarity.COMMON)));
```

这个方块将会被添加在建筑方块里



### 设置方块渲染

略

### 设置方块模型

略，即使不添加也不会报错只会变成紫黑块

查看Fabirc Wiki设置方块模型教程[tutorial:blockstate [Fabric Wiki\] (fabricmc.net)](https://fabricmc.net/wiki/tutorial:blockstate)



## 让方块可以用挖掘铲挖掘

### 创建宝藏方块数据

概念：宝藏方块指的是可以用挖掘铲交互的方块，挖掘过的方块和可挖掘方块统称宝藏方块

重要：此章节较17.1.x 教程有较大的改变，需要特别注意

```
com.wzssoft.modid.registry.MODIDTreasureBlocks.java
```

```java
package com.wzssoft.modid.registry;

import com.wzssoft.modid.block.MODIDBlocks;
import com.wzssoft.treasurehuntlib.data.TreasureBlockData;
import net.minecraft.block.Blocks;

/**
 * com.wzssoft.modid.registry.MODIDTreasureBlocks.java
 */
public class MODIDTreasureBlocks {

    public static final TreasureBlockData GRASS_BLOCK = new TreasureBlockData(Blocks.GRASS_BLOCK);
    
    public static final TreasureBlockData SHOVELED_GRASS_BLOCK = new TreasureBlockData(MODIDBlocks.SHOVELED_GRASS_BLOCK);
    
}
```

我们需要写两个TreasureBlockData，之后我们会在此基础上进行拓展



### 注册宝藏方块

```
com.wzssoft.modid.MODIDMod.java
```

```java
package com.wzssoft.modid;

...

public class MODIDMod implements ModInitializer {

    public static final String MODID = "modid";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {

        ...
            
 //注册宝藏方块
 TreasureHuntLibRegistry.registerTreasureBlock(MODIDTreasureBlocks.GRASS_BLOCK);
 TreasureHuntLibRegistry.registerTreasureBlock(MODIDTreasureBlocks.SHOVELED_GRASS_BLOCK);
    }
}
```

此时我们进入游戏后发现使用挖掘铲挖掘草方块并没有任何效果，是因为我们还没有对刚刚注册的TreasureBlockData进行设置



### 修改可挖掘方块数据

我们可以看到TreasureBlockData里面有很多的接口

```
com.wzssoft.modid.registry.MODIDTreasureBlocks.java
```

```java
//当前是否可以使用挖掘铲挖掘
.isShovelable()

//在挖掘前
.foreShovel()

//挖掘后
.postShovel()

//挖掘后转换的方块状态
.shoveledState()
```

以上这4个接口是我们注册 **可挖掘方块** 时使用的，你可能还注意到其他几个方法，但是我们就挖掘草方块而言，暂时用不到那些接口。



```java
public static final TreasureBlockData GRASS_BLOCK = new TreasureBlockData(Blocks.GRASS_BLOCK)


        //当前是否可以使用挖掘铲挖掘     ->默认写法，判断是否超出世界边界，工具是否有耐久等...
        .isShovelable(TreasureHuntLibTreasureBlocks::isShovelable)

        //在挖掘前      ->我们一般不用这个方法
        //.foreShovel() 

        //挖掘后       ->默认写法，扣除工具一点耐久
        .postShovel(TreasureHuntLibTreasureBlocks::postShovel)

        //挖掘后转换的方块状态    ->这边我们需要传入挖掘过的草方块的方块状态
        .shoveledState((ShovelableState, world, pos, player) -> MODIDBlocks.SHOVELED_GRASS_BLOCK.getDefaultState())

        ;
```

这样我们就完成了对最基础的可挖掘方块数据的修改



### 修改挖掘过的方块数据

```java
//是否可以被挖掘
.isShoveled()

//是否可以掉落物品
.dropLoots()

//掉落物列表
.loots()

//挖掘前的状态
.shovelableState()
```

TreasureBlockData还剩下一些接口是给挖掘过的方块准备的



```java
public static final TreasureBlockData SHOVELED_GRASS_BLOCK = new TreasureBlockData(MODIDBlocks.SHOVELED_GRASS_BLOCK)

        //是否可以被挖掘       ->默认可以被挖掘
        .isShoveled((shovelableState, shoveledState, world, pos, player) -> true)

        //是否可以掉落物品      ->暂时不用写，在添加自定义掉落物章节会仔细讲解
        //.dropLoots()

        //掉落物列表         ->暂时不用写，在添加自定义掉落物章节会仔细讲解
        //.loots()           

        //挖掘前的状态        ->这边我们需要传入草方块的方块状态
        .shovelableState((shoveledState, world, pos, player) -> Blocks.GRASS_BLOCK.getDefaultState())
        
        ;
```

现在进入游戏，你可以看到用挖掘铲挖掘草方块时，草方块转换成了我们模组中添加的挖掘过的草方块

你可以看到草方块在挖掘后直接被破坏，这是因为我们没有给草方块添加mineable/digging_shovel标签



## 为方块添加更多的功能

### 添加可挖掘方块标签

可挖掘方块标签即mineable/digging_shovel标签，添加此标签可以让玩家以正常挖掘速度的12倍挖掘方块，同时挖掘后不会破坏此方块，所有的可挖掘方块必须添加此方块标签

标签位于 src\main\resources\data\treasurehuntlib\tags\blocks\mineable

```json
{
  "replace": false,
  "values": [
    "minecraft:grass_block"
  ]
}
```



### 添加自定义掉落物

重要：添加自定义掉落物与v17.1.7教程中完全不同，需要特别注意

我们之前在修改挖掘过方块数据时，曾经了解过可以使用 droploots() 和 loots() 两个接口来实现自定义掉落物生成

```java
//是否可以掉落物品
.dropLoots()

//掉落物列表
.loots()
```



```
com.wzssoft.modid.registry.MODIDTreasureBlocks.java
```

```java
public static final TreasureBlockData SHOVELED_GRASS_BLOCK = new TreasureBlockData(MODIDBlocks.SHOVELED_GRASS_BLOCK)

        //是否可以被挖掘       ->默认可以被挖掘
        .isShoveled((shovelableState, shoveledState, world, pos, player) -> true)

        //是否可以掉落物品      ->默认可以掉落物品
        .dropLoots((shovelableState, shoveledState, world, pos, player) -> true)

        //掉落物列表         ->这里可以随便填入一个掉落物列表
        .loots((shovelableState, shoveledState, world, pos, player) -> {

            //随便用了一个Treasure Hunt Lib中自带方块的 掉落物列表
            Identifier LOOTS = ShoveledStoneBlock.LOOTS;
            return loots(world,LOOTS);
        })

        //挖掘前的状态        ->这边我们需要传入草方块的方块状态
        .shovelableState((shoveledState, world, pos, player) -> Blocks.GRASS_BLOCK.getDefaultState())

        ;

protected static List<ItemStack> loots(World world, Identifier identifier) {
    LootContext lootContext = (new LootContext.Builder((ServerWorld) world)).random(world.random).build(LootContextTypes.EMPTY);
    LootTable lootTable = ((ServerWorld) world).getServer().getLootManager().getTable(identifier);
    return lootTable.generateLoot(lootContext);
}
```

如果正确的话，它会掉落钻石，绿宝石，铁和黄金（仅供测试使用，请不要在你的模组中使用）



### 添加随机刻

重要：如果想让这个物品具有随机刻，可以重写方法中的 hasRandomTicks（）或者在注册方块时添加

```java
FabricBlockSettings.of(Material.SOLID_ORGANIC).strength(0.6f).sounds(BlockSoundGroup.GRASS).ticksRandomly()
```

```
com.wzssoft.modid.block.common.ShoveledGrassBlock.java
```

```java
public class ShoveledGrassBlock extends AbstractShoveledBlock {

    public ShoveledGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.setToOriginalBlock(world, pos);
    }
}
```

现在这个方块在一个随机刻后会还原成被挖掘之前的样子



## 其他更多功能

如果想实现在此方块上种自定义植物，请查看下一篇笔记

