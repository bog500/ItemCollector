package mc.itemcollector;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemNames {
	ACACIA_STAIRS("acacia wood stairs"), 
	ACTIVATOR_RAIL("activator rail"), 
	AIR("air"), 
	APPLE("apple"), 
	ARMOR_STAND("armor stand"), 
	ARROW("arrow"), 
	BAKED_POTATO("baked potato"), 
	BEACON("beacon"), 
	BED("bed"), 
	BED_BLOCK("bed block"), 
	BEDROCK("bedrock"), 
	BIRCH_WOOD_STAIRS("birch wood stairs"), 
	BLAZE_POWDER("blaze powder"), 
	BLAZE_ROD("blaze rod"), 
	BOAT("boat"), 
	BONE("bone"), 
	BOOK("book"), 
	BOOK_AND_QUILL("book and quill"), 
	BOOKSHELF("bookshelf"), 
	BOW("bow"), 
	BOWL("bowl"), 
	BREAD("bread"), 
	BREWING_STAND("brewing stand"), 
	BREWING_STAND_ITEM("brewing stand"), 
	BRICK("bricks"), 
	BRICK_STAIRS("brick stairs"), 
	BROWN_MUSHROOM("brown mushroom"), 
	BUCKET("bucket"), 
	BURNING_FURNACE("burning furnace"), 
	CACTUS("cactus"), 
	CAKE("cake"), 
	CAKE_BLOCK("cake"), 
	CARROT("carrot"), 
	CARROT_ITEM("carrot"), 
	CARROT_STICK("carrot on a stick"), 
	CAULDRON("cauldron"), 
	CAULDRON_ITEM("cauldron"), 
	CHAINMAIL_BOOTS("chainmail boots"), 
	CHAINMAIL_CHESTPLATE("chainmail chestplate"), 
	CHAINMAIL_HELMET("chainmail helmet"), 
	CHAINMAIL_LEGGINGS("chainmail leggings"), 
	CHEST("chest"), 
	CLAY("clay"), 
	CLAY_BALL("clay"), 
	CLAY_BRICK("brick"), 
	COAL_BLOCK("block of coal"), 
	COAL_ORE("coal ore"), 
	COBBLESTONE("cobblestone"), 
	COBBLESTONE_STAIRS("cobblestone stairs"), 
	COCOA("cocoa"), 
	COMMAND("command block"), 
	COMPASS("compass"), 
	COOKED_BEEF("steak"), 
	COOKED_CHICKEN("cooked chicken"), 
	COOKED_MUTTON("cooked mutton"), 
	COOKED_RABBIT("cooked rabbit"), 
	COOKIE("cookie"), 
	CROPS("crops"), 
	DARK_OAK_STAIRS("dark oak wood stairs"), 
	DAYLIGHT_DETECTOR("daylight sensor"), 
	DEAD_BUSH("dead bush"), 
	DETECTOR_RAIL("detector rail"), 
	DIAMOND("diamond"), 
	DIAMOND_AXE("diamond axe"), 
	DIAMOND_BARDING("diamond horse armor"), 
	DIAMOND_BLOCK("diamond block"), 
	DIAMOND_BOOTS("diamond boots"), 
	DIAMOND_CHESTPLATE("diamond chestplate"), 
	DIAMOND_HELMET("diamond helmet"), 
	DIAMOND_HOE("diamond hoe"), 
	DIAMOND_LEGGINGS("diamond leggings"), 
	DIAMOND_ORE("diamond ore"), 
	DIAMOND_PICKAXE("diamond pickaxe"), 
	DIAMOND_SPADE("diamond shovel"), 
	DIAMOND_SWORD("diamond sword"), 
	DIODE("redstone repeater"), 
	DIODE_BLOCK_OFF("redstone repeater"), 
	DIODE_BLOCK_ON("redstone repeater"), 
	DIRT("dirt"), 
	DISPENSER("dispenser"), 
	DOULBE_PLANT("double plant"), 
	DOUBLE_STEP("double slab"), 
	DRAGON_EGG("dragon egg"), 
	DROPPER("dropper"), 
	EGG("egg"), 
	EMERALD("emerald"), 
	EMERALD_BLOCK("emerald block"), 
	EMERALD_ORE("emerald ore"), 
	EMPTY_MAP("empty map"), 
	ENCHANTED_BOOK("enchanted book"), 
	ENCHANTMENT_TABLE("enchantment table"), 
	ENDER_CHEST("ender chest"), 
	ENDER_PEARL("ender pearl"), 
	ENDER_PORTAL("ender portal"), 
	ENDER_PORTAL_FRAME("ender protal frame"), 
	ENDER_STONE("ender stone"), 
	EXP_BOTTLE("bottle o' enchanting"), 
	EXPLOSIVE_MINECART("minecart with tnt"), 
	EYE_OF_ENDER("eye of ender"), 
	FEATHER("feather"), 
	FERMENTED_SPIDER_EYE("fermented spider eye"), 
	FIRE("fire"), 
	FIREBALL("fireball"), 
	FIREWORK("firework"), 
	FIREWORK_CHARGE("firework charge"), 
	FISHING_ROD("fishing rod"), 
	FLINT("flint"), 
	FLINT_AND_STEEL("flint and steel"), 
	FLOWER_POT("flower pot"), 
	FLOWER_POT_ITEM("flower pot"), 
	FURNACE("furnace"), 
	GHAST_TEAR("ghast tear"), 
	GLASS("glass"), 
	GLASS_BOTTLE("glass bottle"), 
	GLOWING_REDSTONE_ORE("redstone ore"), 
	GLOWSTONE("glowstone"), 
	GLOWSTONE_DUST("glowstone dust"), 
	GOLD_AXE("gold axe"), 
	GOLD_BARDING("gold horse armor"), 
	GOLD_BLOCK("gold block"), 
	GOLD_BOOTS("gold boots"), 
	GOLD_CHESTPLATE("gold chestplate"), 
	GOLD_HELMET("gold helmet"), 
	GOLD_HOE("gold hoe"), 
	GOLD_INGOT("gold ingot"), 
	GOLD_LEGGINGS("gold leggings"), 
	GOLD_NUGGET("gold nugget"), 
	GOLD_ORE("gold ore"), 
	GOLD_PICKAXE("gold pickaxe"), 
	GOLD_PLATE("weighted pressure plate (ligth)"), 
	GOLD_RECORD("music disc - 13"), 
	GOLD_SPADE("gold shovel"), 
	GOLD_SWORD("gold sword"), 
	GOLDEN_APPLE("golden apple"), 
	GOLDEN_CARROT("golden carrot"), 
	GRASS("grass"), 
	GRAVEL("gravel"), 
	GREEN_RECORD("music disc - cat"), 
	GRILLED_PORK("cooked porkchop"), 
	HARD_CLAY("hardened clay"), 
	HAY_BLOCK("hay bale"), 
	HOPPER("hopper"), 
	HOPPER_MINECART("minecart with hopper"), 
	HUGE_MUSHROOM_1("huge mushroom"), 
	HUGE_MUSHROOM_2("huge mushroom"), 
	ICE("ice"), 
	INK_SACK("ink sack"), 
	IRON_AXE("iron axe"), 
	IRON_BARDING("iron horse armor"), 
	IRON_BLOCK("iron block"), 
	IRON_BOOTS("iron boots"), 
	IRON_CHESTPLATE("iron chestplate"), 
	IRON_DOOR("iron door"), 
	IRON_DOOR_BLOCK("iron door block"), 
	IRON_FENCE("iron fence"), 
	IRON_HELMET("iron helmet"), 
	IRON_HOE("iron hoe"), 
	IRON_INGOT("iron ingot"), 
	IRON_LEGGINGS("iron leggings"), 
	IRON_ORE("iron ore"), 
	IRON_PICKAXE("iron pickaxe"), 
	IRON_PLATE("weighted pressure plate (heavy)"), 
	IRON_SPADE("iron shovel"), 
	IRON_SWORD("iron sword"), 
	ITEM_FRAME("item frame"), 
	JACK_O_LANTERN("jack-o-lantern"), 
	JUKEBOX("jukebox"), 
	JUNGLE_WOOD_STAIRS("jungle wood stairs"), 
	LADDER("ladder"), 
	LAPIS_BLOCK("lapis lazuli block"), 
	LAPIS_ORE("lapis lazuli ore"), 
	LAVA("lava"), 
	LAVA_BUCKET("lava bucket"), 
	LEASH("lead"), 
	LEATHER("leather"), 
	LEATHER_BOOTS("leather boots"), 
	LEATHER_CHESTPLATE("leather tunic"), 
	LEATHER_HELMET("leather cap"), 
	LEATHER_LEGGINGS("leather pants"), 
	LEAVES("leaves"), 
	LEAVES_2("leaves"), 
	LEVER("lever"), 
	LOCKED_CHEST("locked chest"), 
	LONG_GRASS("shrub"), 
	MAGMA_CREAM("magma cream"), 
	MAP("map"), 
	MELON("melon"), 
	MELON_BLOCK("melon"), 
	MELON_SEEDS("melon seeds"), 
	MELON_STEM("melon stem"), 
	MILK_BUCKET("milk bucket"), 
	MINECART("minecart"), 
	MOB_SPAWNER("mob spawner"), 
	MONSTER_EGG("mob egg"), 
	MOSSY_COBBLESTONE("mossy cobblestone"), 
	MUSHROOM_SOUP("mushroom soup"), 
	MYCEL("mycelium"), 
	NAME_TAG("name tag"), 
	NETHER_BRICK("nether brick"), 
	NETHER_BRICK_ITEM("nether brick"), 
	NETHER_BRICK_STAIRS("nether brick stairs"), 
	NETHER_FENCE("nether brick fence"), 
	NETHER_STALK("nether wart"), 
	NETHER_STAR("nether star"), 
	NETHER_WARTS("nether wart"), 
	NETHERRACK("netherrack"), 
	NOTE_BLOCK("note block"), 
	OBSIDIAN("obsidian"), 
	PACKED_ICE("packed ice"), 
	PAINTING("painting"), 
	PAPER("paper"), 
	PISTON_BASE("piston"), 
	PISTON_EXTENSION("piston extension"), 
	PISTON_MOVING_PIECE("moving piston"), 
	PISTON_STICK_BASE("sticky piston"), 
	PISTON_STICKY_BASE("sticky piston"), 
	POISONOUS_POTATO("poisonous potato"), 
	PORK("raw porkchop"), 
	PORTAL("portal"), 
	POTATO("potato"), 
	POTATO_ITEM("potato"), 
	POTION("potion"), 
	POWERED_MINECART("minecart with furnace"), 
	POWERED_RAIL("powered rail"), 
	PUMPKIN("pumpkin"), 
	PUMPKIN_PIE("pumpkin pie"), 
	PUMPKIN_SEEDS("pumpkin seeds"), 
	PUMPKIN_STEM("pumpkin stem"), 
	QUARTZ("quartz"), 
	QUARTZ_ORE("quartz ore"), 
	QUARTZ_STAIRS("quartz stairs"), 
	RAILS("rail"), 
	RABBIT_FOOT("rabbit foot"),
	RABBIT_HIDE("rabbit lether"),
	RABBIT_STEW("rabbit stew"),
	RAW_BEEF("raw beef"), 
	RAW_CHICKEN("raw chicken"), 
	RAW_MUTTON("raw mutton"), 
	RAW_RABBIT("raw rabbit"), 
	RECORD_10("music disc - ward"), 
	RECORD_11("music disc - 11"), 
	RECORD_12("music disc - wait"), 
	RECORD_3("music disc - blocks"), 
	RECORD_4("music disc - chirp"), 
	RECORD_5("music disc - far"), 
	RECORD_6("music disc - mall"), 
	RECORD_7("music disc - mellohi"), 
	RECORD_8("mucis disc - stal"), 
	RECORD_9("music disc - strad"), 
	RED_MUSHROOM("red mushroom"), 
	REDSTONE("redstone"), 
	REDSTONE_BLOCK("block of redstone"), 
	REDSTONE_COMPARATOR("redstone comparator"), 
	REDSTONE_COMPARATOR_OFF("redstone comparator"), 
	REDSTONE_COMPARATOR_ON("redstone comparator"), 
	REDSTONE_LAMP_OFF("redstone lamp"), 
	REDSTONE_LAMP_ON("redstone lamp"), 
	REDSTONE_ORE("redstone ore"), 
	REDSTONE_TORCH_OFF("redstone torch"), 
	REDSTONE_TORCH_ON("redstone torch"), 
	REDSTONE_WIRE("redstone"), 
	ROTTEN_FLESH("rotten flesh"), 
	SADDLE("saddle"), 
	SAND("sand"), 
	SANDSTONE_STAIRS("sandstone stairs"), 
	SAPLING("sapling"), 
	SEA_LANTERN("sea lantern"), 
	SEEDS("seeds"), 
	SHEARS("shears"), 
	SIGN("sign"), 
	SIGN_POST("sign"), 
	SKULL("skull"), 
	SLIME_BALL("slime ball"), 
	SLIME_BLOCK("slime block"), 
	SMOOTH_STAIRS("stone bricks stairs"), 
	SNOW("snow"), 
	SNOW_BALL("snow ball"), 
	SNOW_BLOCK("snow block"), 
	SOIL("farmland"), 
	SOUL_SAND("soul sand"), 
	SPECKLED_MELON("glistering melon"), 
	SPIDER_EYE("spider eye"), 
	SPRUCE_WOOD_STAIRS("spruce wood stairs"), 
	STATIONARY_LAVA("stationary lava"), 
	STEP("stone slab"), 
	STICK("stick"), 
	STONE("stone"), 
	STONE_AXE("stone axe"), 
	STONE_BUTTON("stone button"), 
	STONE_HOE("stone hoe"), 
	STONE_PICKAXE("stone pickaxe"), 
	STONE_PLATE("pressure plate"), 
	STONE_SPADE("stone shovel"), 
	STONE_SWORD("stone sword"), 
	STORAGE_MINECART("minecart with chest"), 
	STRING("string"), 
	SUGAR("sugar"), 
	SUGAR_CANE("sugar cane"), 
	SUGAR_CANE_BLOCK("sugar cane"), 
	SULPHUR("gunpowder"), 
	THIN_GLASS("glass pane"), 
	TNT("tnt"), 
	TORCH("torch"), 
	TRAP_DOOR("trapdoor"), 
	TRAPPED_CHEST("trapped chest"), 
	TRIPWIRE("tripwire"), 
	TRIPWIRE_HOOK("tripwire hook"), 
	VINE("vines"), 
	WALL_SIGN("sign"), 
	WATCH("clock"), 
	WATER("water"), 
	WATER_BUCKET("water bucket"), 
	WATER_LILY("lily pad"), 
	WEB("cobweb"), 
	WHEAT("wheat"), 
	WOOD_AXE("wooden axe"), 
	WOOD_BUTTON("wooden button"), 
	WOOD_DOUBLE_STEP("double wood slab"), 
	WOOD_HOE("wooden hoe"), 
	WOOD_PICKAXE("wooden pickaxe"), 
	WOOD_PLATE("pressure plate"), 
	WOOD_SPADE("wooden shovel"), 
	WOOD_STAIRS("wood stairs"), 
	WOOD_SWORD("wooden sword"), 
	WOODEN_DOOR("wooden door"), 
	WORKBENCH("crafting table"), 
	WRITTEN_BOOK("written book"), 
	YELLOW_FLOWER("dandelion"),

	//Variant
	
	STONE__0("stone"),
	STONE__1("granite"),
	STONE__2("polished granite"),
	STONE__3("diorite"),
	STONE__4("polish diorite"),
	STONE__5("andesite"),
	STONE__6("polished andesite"),
	
	DIRT__0("dirt"),
	DIRT__1("coarse dirt"),
	DIRT__2("podzol"),
	
	SAPLING__0("pak sapling"),
	SAPLING__1("spruce Sapling"),
	SAPLING__2("birch sapling"),
	SAPLING__3("jungle sapling"),
	SAPLING__4("acadia sapling"),
	SAPLING__5("dark Oak sapling"),
	
	DOUBLE_STEP__0("oak wood planks"),
	DOUBLE_STEP__1("spruce wood planks"),
	DOUBLE_STEP__2("birch wood planks"),
	DOUBLE_STEP__3("jungle wood planks"),
	DOUBLE_STEP__4("acadia wood planks"),
	DOUBLE_STEP__5("dark oak Wood planks"),
	
	LOG("Oak wood log"),
	LOG__0("Oak wood log"),
	LOG__1("Spruce wood log"),
	LOG__2("Birch wood log"),
	LOG__3("Jungle wood log"),
	LOG__4("Acadia wood log"),
	LOG__5("Dark oak wood log"),
	LOG_2("Acadia wood log"),
	LOG_2__0("Acadia wood log"),
	LOG_2__1("Dark oak Wood log"),
	
	WOOD("Oak wood planks"),
	WOOD__0("Oak wood planks"),
	WOOD__1("Spruce wood planks"),
	WOOD__2("Birch wood planks"),
	WOOD__3("Jungle wood planks"),
	WOOD__4("Acadia wood planks"),
	WOOD__5("Dark oak wood planks"),
	
	WOOD_STEP("Oak wood slab"),
	WOOD_STEP__0("Oak wood slab"),
	WOOD_STEP__1("Spruce wood slab"),
	WOOD_STEP__2("Birch wood slab"),
	WOOD_STEP__3("Jungle wood slab"),
	WOOD_STEP__4("Acadia wood slab"),
	WOOD_STEP__5("Dark oak wood slab"),
	
	FENCE("oak fence"), 
	FENCE_GATE("oak fence gate"),
	WOOD_DOOR("oak wooden door"), 
	
	SPRUCE_FENCE("spruce fence"), 
	SPRUCE_FENCE_GATE("spruce fence gate"),
	SPRUCE_DOOR_ITEM("spruce door"), 
	
	ACACIA_FENCE("acacia fence"), 
	ACACIA_FENCE_GATE("acacia fence gate"), 
	ACACIA_DOOR_ITEM("acacia door"), 
	
	JUNGLE_FENCE("jungle fence"), 
	JUNGLE_FENCE_GATE("jungle fence gate"), 
	JUNGLE_DOOR_ITEM("jungle door"), 
	
	BIRCH_FENCE("birch fence"), 
	BIRCH_FENCE_GATE("birch fence gate"), 
	BIRCH_DOOR_ITEM("birch door"), 
	 
	MONSTER_EGGS("monster eggs (stone)"),
	MONSTER_EGGS__0("monster eggs (stone)"),
	MONSTER_EGGS__1("monster eggs (cobblestone)"),
	MONSTER_EGGS__2("monster eggs (stone bricks)"),
	MONSTER_EGGS__3("monster eggs (mossy stone bricks)"),
	MONSTER_EGGS__4("monster eggs (cracked stone bricks)"),
	MONSTER_EGGS__5("monster eggs (chiseled stone bricks)"),
	
	SAND__0("sand"),
	SAND__1("red sand"),
	
	LEAVES__0("oak leaves"),
	LEAVES__1("spruce leaves"),
	LEAVES__2("birch leaves"),
	LEAVES__3("jungle leaves"),
	LEAVES_2__0("acadia leaves"),
	LEAVES_2__1("dark oak leaves"),
	
	STEP__0("stone slab"),
	STEP__1("sandstone slab"),
	STEP__2("wooden slab"),
	STEP__3("cobblestone slab"),
	STEP__4("brick slab"),
	STEP__5("stone brick slab"),
	STEP__6("nether brick slab"),
	STEP__7("quartz brick slab"),
	
	RED_SANDSTONE("red sandstone"),
	RED_SANDSTONE__0("red sandstone"),
	RED_SANDSTONE__1("red sandstone chiseled"),
	RED_SANDSTONE__2("red sandstone smooth"),
	RED_SANDSTONE_STAIRS("red sandstone stairs"),
	STONE_SLAB2("red sandstone slab"),
	
	SANDSTONE("sandstone"), 
	SANDSTONE__0("sandstone"), 
	SANDSTONE__1("sandstone chiseled"), 
	SANDSTONE__2("sandstone smooth"), 
	
	SMOOTH_BRICK("stone bricks"), 
	SMOOTH_BRICK__0("stone bricks"),
	SMOOTH_BRICK__1("mossy stone bricks"),
	SMOOTH_BRICK__2("cracked stone bricks"), 
	SMOOTH_BRICK__3("chiseled stone bricks"), 
	
	ANVIL("anvil"), 
	ANVIL__0("anvil"), 
	ANVIL__1("anvil slightly damaged"), 
	ANVIL__2("anvil very damaged"), 
	
	STAINED_GLASS("stained glass (white)"),
	STAINED_GLASS__0("stained glass (white)"),
	STAINED_GLASS__1("stained glass (orange)"),
	STAINED_GLASS__2("stained glass (magenta)"),
	STAINED_GLASS__3("stained glass (light blue)"),
	STAINED_GLASS__4("stained glass (yellow)"),
	STAINED_GLASS__5("stained glass (lime)"),
	STAINED_GLASS__6("stained glass (pink)"),
	STAINED_GLASS__7("stained glass (grey)"),
	STAINED_GLASS__8("stained glass (light grey)"),
	STAINED_GLASS__9("stained glass (cyan)"),
	STAINED_GLASS__10("stained glass (purple)"),
	STAINED_GLASS__11("stained glass (blue)"),
	STAINED_GLASS__12("stained glass (brown)"),
	STAINED_GLASS__13("stained glass (green)"),
	STAINED_GLASS__14("stained glass (red)"),
	STAINED_GLASS__15("stained glass (black)"),
	
	STAINED_GLASS_PANE("stained glass pane (white)"),
	STAINED_GLASS_PANE__0("stained glass pane (white)"),
	STAINED_GLASS_PANE__1("stained glass pane (orange)"),
	STAINED_GLASS_PANE__2("stained glass pane (magenta)"),
	STAINED_GLASS_PANE__3("stained glass pane (light blue)"),
	STAINED_GLASS_PANE__4("stained glass pane (yellow)"),
	STAINED_GLASS_PANE__5("stained glass pane (lime)"),
	STAINED_GLASS_PANE__6("stained glass pane (pink)"),
	STAINED_GLASS_PANE__7("stained glass pane (grey)"),
	STAINED_GLASS_PANE__8("stained glass pane (light grey)"),
	STAINED_GLASS_PANE__9("stained glass pane (cyan)"),
	STAINED_GLASS_PANE__10("stained glass pane (purple)"),
	STAINED_GLASS_PANE__11("stained glass pane (blue)"),
	STAINED_GLASS_PANE__12("stained glass pane (brown)"),
	STAINED_GLASS_PANE__13("stained glass pane (green)"),
	STAINED_GLASS_PANE__14("stained glass pane (red)"),
	STAINED_GLASS_PANE__15("stained glass pane (black)"),
	
	WOOL("wool (white)"),
	WOOL__0("wool (white)"),
	WOOL__1("wool (orange)"),
	WOOL__2("wool (magenta)"),
	WOOL__3("wool (light blue)"),
	WOOL__4("wool (yellow)"),
	WOOL__5("wool (lime)"),
	WOOL__6("wool (pink)"),
	WOOL__7("wool (grey)"),
	WOOL__8("wool (light grey)"),
	WOOL__9("wool (cyan)"),
	WOOL__10("wool (purple)"),
	WOOL__11("wool (blue)"),
	WOOL__12("wool (brown)"),
	WOOL__13("wool (green)"),
	WOOL__14("wool (red)"),
	WOOL__15("wool (black)"),
	
	CARPET("carpet (white)"),
	CARPET__0("carpet (white)"),
	CARPET__1("carpet (orange)"),
	CARPET__2("carpet (magenta)"),
	CARPET__3("carpet (light blue)"),
	CARPET__4("carpet (yellow)"),
	CARPET__5("carpet (lime)"),
	CARPET__6("carpet (pink)"),
	CARPET__7("carpet (grey)"),
	CARPET__8("carpet (light grey)"),
	CARPET__9("carpet (cyan)"),
	CARPET__10("carpet (purple)"),
	CARPET__11("carpet (blue)"),
	CARPET__12("carpet (brown)"),
	CARPET__13("carpet (green)"),
	CARPET__14("carpet (red)"),
	CARPET__15("carpet (black)"),
	
	STAINED_CLAY("stained clay (white)"),
	STAINED_CLAY__0("stained clay (white)"),
	STAINED_CLAY__1("stained clay (orange)"),
	STAINED_CLAY__2("stained clay (magenta)"),
	STAINED_CLAY__3("stained clay (light blue)"),
	STAINED_CLAY__4("stained clay (yellow)"),
	STAINED_CLAY__5("stained clay (lime)"),
	STAINED_CLAY__6("stained clay (pink)"),
	STAINED_CLAY__7("stained clay (grey)"),
	STAINED_CLAY__8("stained clay (light grey)"),
	STAINED_CLAY__9("stained clay (cyan)"),
	STAINED_CLAY__10("stained clay (purple)"),
	STAINED_CLAY__11("stained clay (blue)"),
	STAINED_CLAY__12("stained clay (brown)"),
	STAINED_CLAY__13("stained clay (green)"),
	STAINED_CLAY__14("stained clay (red)"),
	STAINED_CLAY__15("stained clay (black)"),
	
	BANNER("banner (white)"),
	BANNER__0("banner (white)"),
	BANNER__1("banner (orange)"),
	BANNER__2("banner (magenta)"),
	BANNER__3("banner (light blue)"),
	BANNER__4("banner (yellow)"),
	BANNER__5("banner (lime)"),
	BANNER__6("banner (pink)"),
	BANNER__7("banner (grey)"),
	BANNER__8("banner (light grey)"),
	BANNER__9("banner (cyan)"),
	BANNER__10("banner (purple)"),
	BANNER__11("banner (blue)"),
	BANNER__12("banner (brown)"),
	BANNER__13("banner (green)"),
	BANNER__14("banner (red)"),
	BANNER__15("banner (black)"),
	
	RED_ROSE("poppy"), 
	RED_ROSE__0("poppy"), 
	RED_ROSE__1("blue orchid"), 
	RED_ROSE__2("allium"), 
	RED_ROSE__3("azure bluet"), 
	RED_ROSE__4("red tulip"), 
	RED_ROSE__5("orange tulip"), 
	RED_ROSE__6("white tulip"), 
	RED_ROSE__7("pink tulip"), 
	RED_ROSE__8("oxeye daisy"), 
	
	DOUBLE_PLANT("sunflower"),
	DOUBLE_PLANT__0("sunflower"),
	DOUBLE_PLANT__1("lilac"),
	DOUBLE_PLANT__2("double tall grass"),
	DOUBLE_PLANT__3("large fern"),
	DOUBLE_PLANT__4("rose bush"),
	DOUBLE_PLANT__5("peony"),
	
	COAL("coal"), 
	COAL__0("coal"), 
	COAL__1("charcoal"), 
	
	PRISMARINE("prismarine"),
	PRISMARINE__0("prismarine"),
	PRISMARINE__1("prismarine bricks"),
	PRISMARINE__2("dark prismarine"),
	PRISMARINE_CRYSTALS("prismarine crystals"),
	PRISMARINE_SHARD("prismarine shard"),
	
	SKULL_ITEM("skeleton skull"), 
	SKULL_ITEM__0("skeleton skull"), 
	SKULL_ITEM__1("whither skull"), 
	SKULL_ITEM__2("zombie skull"), 
	SKULL_ITEM__3("steve skull"), 
	SKULL_ITEM__4("creeper skull"), 
	
	QUARTZ_BLOCK("block of quartz"), 
	QUARTZ_BLOCK__0("block of quartz"), 
	QUARTZ_BLOCK__1("chiseled block of quartz"), 
	QUARTZ_BLOCK__2("pillar block of quartz"), 
	
	COBBLE_WALL("cobblestone wall"), 
	COBBLE_WALL__0("cobblestone wall"), 
	COBBLE_WALL__1("mossy cobblestone wall"), 
	
	RAW_FISH("raw fish"), 
	RAW_FISH__0("raw fish"), 
	RAW_FISH__1("raw salmon"), 
	RAW_FISH__2("raw clownfish"), 
	RAW_FISH__3("raw pufferfish"), 
	
	COOKED_FISH("cooked fish"), 
	COOKED_FISH__0("cooked fish"), 
	COOKED_FISH__1("cooked salmon"), 
	COOKED_FISH__2("cooked clownfish"), 
	COOKED_FISH__3("cooked pufferfish"), 
	
	SPONGE("sponge"), 
	SPONGE__0("sponge"), 
	SPONGE__1("wet sponge"),
	
	// ######## ENTITIES ########
	
	SNOWMAN("snow golem"),
	OCELOT("ocelot"),
	IRON_GOLEM("iron golem"),
	RABBIT("rabbit"),
	VILLAGER("villager"),
	COW("cow"),
	CHICKEN("chicken"),
	WOLF("wolf"),
	MUSHROOM_COW("mooshroom"),
	SHEEP("sheep"),
	PIG("pig"),
	DONKEY("donkey"),
	HORSE("horse"),
	MULE("mule"),
	SKELETON_HORSE("skeleton horse"),
	UNDEAD_HORSE("undead horse"),
	;


private final String name;

private ItemNames(final String name) {
	this.name = name;
}

@Override
public String toString() {
	return name;
}

private static final Map<String, ItemNames> lookup = new HashMap<String, ItemNames>();

public static String getCreatureDisplayName(String key) {
	String value = "";
	try {
		value = ItemNames.valueOf(key.toUpperCase()).firstUpperCased();
	}catch(Exception ex) {
		value = key;
	}
	return value.replace('_', ' ');
}

public static String getBlockDisplayName(String key) {
	String bukkitName = "";
	String value = "";
	try {
		
		String[] splitted = key.split(":");
		int id = Integer.parseInt(splitted[0]);
		int variant = -1;
		ItemStack is;
			
		if(splitted.length == 2) {
			variant = Integer.parseInt(splitted[1]);
			is = new ItemStack(id, 1, (short)variant);
			Material mat = is.getType();
			bukkitName = mat.name() + "__" + variant;	
		}else {
			is = new ItemStack(id, 1);
			Material mat = is.getType();
			bukkitName = mat.name();	
		}
		/* change name because an entity use the same name */
		if(bukkitName.equalsIgnoreCase("rabbit")) {
			bukkitName = "RAW_RABBIT";
		}
		value = ItemNames.valueOf(bukkitName.toUpperCase()).firstUpperCased();
	}catch(Exception ex) {
		if(bukkitName != "" && bukkitName != null) {
			value = bukkitName;
		}else {
			value = key;
		}
	}
	return value.replace('_', ' ') + " [" + key + "]";
}

// Returns the Material name from the given block name
public static String getMaterialName(String fromBlockName) {
	for (ItemNames n : values()) {
		lookup.put(n.toString(), n);
	}
	String result = lookup.get(fromBlockName).name();
	return result;
}

// Returns the item name with the first letter uppercased (Example: pressure
// plate -> Pressure plate)
public String firstUpperCased() {
	char first = Character.toUpperCase(name.charAt(0));
	return first + name.substring(1);
}

// Returns the item name with all the words with the first letter uppercased
// (Example: pressure plate -> Pressure Plate)
public String firstAllUpperCased() {
	return WordUtils.capitalizeFully(name);
}

// Returns the item name with all the letters uppercased (Example: pressure
// plate -> PRESSURE PLATE)
	public String allUpperCased() {
		return name.toUpperCase();
	}

}
