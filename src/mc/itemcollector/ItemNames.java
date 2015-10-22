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
	ANVIL("anvil"), 
	APPLE("apple"), 
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
	BRICK("bricks"), 
	BRICK_STAIRS("brick stairs"), 
	BROWN_MUSHROOM("brown mushroom"), 
	BUCKET("bucket"), 
	BURNING_FURNACE("burning furnace"), 
	CACTUS("cactus"), 
	CAKE("cake"), 
	CAKE_BLOCK("cake"), 
	CARPET("carpet"), 
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
	COAL("coal"), 
	COAL_BLOCK("block of coal"), 
	COAL_ORE("coal ore"), 
	COBBLE_WALL("cobblestone wall"), 
	COBBLESTONE("cobblestone"), 
	COBBLESTONE_STAIRS("cobblestone stairs"), 
	COCOA("cocoa"), 
	COMMAND("command block"), 
	COMPASS("compass"), 
	COOKED_BEEF("steak"), 
	COOKED_CHICKEN("cooked chicken"), 
	COOKED_FISH("cooked fish"), 
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
	FENCE("fence"), 
	FENCE_GATE("fence gate"), 
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
	GOLD_PLATE("weighted pressure plate"), 
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
	IRON_PLATE("weighted pressure plate"), 
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
	LOG("wood log"), 
	LOG_1("wood log"), 
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
	MONSTER_EGGS("silverfish block"), 
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
	PAINTING("painting"), 
	PAPER("paper"), 
	PISTON_BASE("piston"), 
	PISTON_EXTENSION("piston extension"), 
	PISTON_MOVING_PIECE("moving piston"), 
	PISTON_STICK_BASE("sticky piston"), 
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
	//PUMPKIN_STEM("pumpkin stem"), 
	QUARTZ("quartz"), 
	QUARTZ_BLOCK("block of quartz"), 
	QUARTZ_ORE("quartz ore"), 
	QUARTZ_STAIRS("quartz stairs"), 
	RAILS("rail"), 
	RAW_BEEF("raw beef"), 
	RAW_CHICKEN("raw chicken"), 
	RAW_FISH("raw fish"), 
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
	RED_ROSE("red rose"), 
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
	SANDSTONE("sandstone"), 
	SANDSTONE_STAIRS("sandstone stairs"), 
	SAPLING("sapling"), 
	SEEDS("seeds"), 
	SHEARS("shears"), 
	SIGN("sign"), 
	SIGN_POST("sign"), 
	SKULL("skull"), 
	SKULL_ITEM("skull"), 
	SLIME_BLOCK("slime block"), 
	SMOOTH_BRICK("stone bricks"), 
	SMOOTH_STAIRS("stone bricks stairs"), 
	SNOW("snow"), 
	SNOW_BALL("snow ball"), 
	SNOW_BLOCK("snow block"), 
	SOIL("farmland"), 
	SOUL_SAND("soul sand"), 
	SPECKLED_MELON("glistering melon"), 
	SPIDER_EYE("spider eye"), 
	SPONGE("sponge"), 
	SPRUCE_WOOD_STAIRS("spruce wood stairs"), 
	STAINED_CLAY("stained clay"), 
	STAINED_GLASS("stained glass"), 
	STAINED_GLASS_PANE("stained glass pane"), 
	STATIONARY_LAVA("stationary lava"), 
	STEP("stone slab"), 
	STICK("stick"), 
	STONE("stone"), 
	STONE_AXE("stone axe"), 
	STONE_BUTTON("button"), 
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
	WOOD("wood planks"), 
	WOOD_AXE("wooden axe"), 
	WOOD_BUTTON("button"), 
	WOOD_DOOR("wooden door"), 
	WOOD_DOUBLE_STEP("double wood slab"), 
	WOOD_HOE("wooden hoe"), 
	WOOD_PICKAXE("wooden pickaxe"), 
	WOOD_PLATE("pressure plate"), 
	WOOD_SPADE("wooden shovel"), 
	WOOD_STAIRS("wood stairs"), 
	WOOD_STEP("wood slab"), 
	WOOD_SWORD("wooden sword"), 
	WOODEN_DOOR("wooden door"), 
	WOOL("wool"), 
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
	
	LOG__0("Oak wood planks"),
	LOG__1("Spruce wood planks"),
	LOG_1__2("Birch wood planks"),
	LOG_1__3("Jungle wood planks"),
	LOG_1__4("Acadia wood planks"),
	LOG_1__5("Dark oak Wood planks"),
	
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
		value = ItemNames.valueOf(bukkitName.toUpperCase()).firstUpperCased();
	}catch(Exception ex) {
		if(bukkitName != "" && bukkitName != null) {
			value = bukkitName;
		}else {
			value = key;
		}
	}
	return value.replace('_', ' ');
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
