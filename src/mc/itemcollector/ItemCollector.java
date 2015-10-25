package mc.itemcollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemCollector extends JavaPlugin implements Listener {

	SettingsManager settings = SettingsManager.getInstance();

	private String messagePrefix = ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + ChatColor.GREEN + "ItemCollector"
			+ ChatColor.GRAY + "]" + ChatColor.RESET;

	private int minX = -100;
	private int minZ = -100;
	private int maxX = 100;
	private int maxZ = 100;

	private String worldName = "world";

	private boolean collectCreatures = true;
	private boolean collectItems = true;

	private boolean annonceInventoryItemsOnPlayerJoin = true;
	private boolean annonceInventoryCreaturesOnPlayerJoin = true;

	private boolean annonceNewItems = true;
	private boolean annonceNewCreatures = true;

	private boolean updateItemsOnChestClosed = true;
	private boolean updateCreaturesOnCreatureFeed = true;

	private String annonceInventoryItemsOnPlayerJoinMessage = ChatColor.DARK_RED + "Item collection: "
			+ ChatColor.DARK_GREEN + "<nbCollectedItems> / <nbTotalItems>";
	private String annonceInventoryCreaturesOnPlayerJoinMessage = ChatColor.DARK_RED + "Creature collection: "
			+ ChatColor.DARK_GREEN + " <nbCollectedCreatures> / <nbTotalCreatures>";
	private String annonceNewItemsMessage = ChatColor.DARK_RED + "New item in the collection: " + ChatColor.BOLD + ""
			+ ChatColor.GREEN + "<itemName>";
	private String annonceNewCreaturesMessage = ChatColor.DARK_RED + "New Creature in the collection: " + ChatColor.BOLD
			+ "" + ChatColor.GREEN + "<creatureName>";

	private List<Map<?, ?>> itemsCollection;
	private List<Map<?, ?>> creaturesCollection;

	private HashSet<String> itemsCollected = new HashSet<String>();
	private HashSet<String> creaturesCollected = new HashSet<String>();

	private HashSet<String> itemsToCollect = new HashSet<String>();
	private HashSet<String> creaturesToCollect = new HashSet<String>();

	private List<Sign> signsItems = new ArrayList<Sign>();
	private List<Sign> signsCreatures = new ArrayList<Sign>();
	private List<InventoryHolder> chests = new ArrayList<InventoryHolder>();

	@Override
	public void onEnable() {
		settings.setup(this);
		Bukkit.getServer().getLogger().info("ItemCollector Enabled!");
		// setDefaultConfig();
		getSavedConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		refreshCollections();
	}

	@Override
	public void onDisable() {
		saveNewConfig();
		Bukkit.getServer().getLogger().info("ItemCollector Disabled!");
	}

	private void saveNewConfig() {
		FileConfiguration config = getConfig();
		config.set("minX", minX);
		config.set("minZ", minZ);
		config.set("maxX", maxX);
		config.set("maxZ", maxZ);

		config.set("worldName", worldName);

		config.set("collectCreatures", collectCreatures);
		config.set("collectItems", collectItems);

		config.set("annonceInventoryItemsOnPlayerJoin", annonceInventoryItemsOnPlayerJoin);
		config.set("annonceInventoryCreaturesOnPlayerJoin", annonceInventoryCreaturesOnPlayerJoin);

		config.set("annonceNewItems", annonceNewItems);
		config.set("annonceNewCreatures", annonceNewCreatures);

		config.set("updateItemsOnChestClosed", updateItemsOnChestClosed);
		config.set("updateCreaturesOnCreatureFeed", updateCreaturesOnCreatureFeed);

		config.set("messagePrefix", messagePrefix);
		config.set("annonceInventoryItemsOnPlayerJoinMessage", annonceInventoryItemsOnPlayerJoinMessage);
		config.set("annonceInventoryCreaturesOnPlayerJoinMessage", annonceInventoryCreaturesOnPlayerJoinMessage);
		config.set("annonceNewItemsMessage", annonceNewItemsMessage);
		config.set("annonceNewCreaturesMessage", annonceNewCreaturesMessage);

		HashMap<String, Boolean> items = new HashMap<String, Boolean>();

		for (String s : itemsToCollect) {
			items.put(s, true);
		}
		for (Map.Entry<?, ?> entry : itemsCollection.get(0).entrySet()) {
			String key = entry.getKey().toString().toLowerCase();
			if (!items.containsKey(key)) {
				items.put(key, false);
			}
		}

		HashMap<String, Boolean> Creatures = new HashMap<String, Boolean>();

		for (String s : creaturesToCollect) {
			Creatures.put(s, true);
		}
		for (Map.Entry<?, ?> entry : creaturesCollection.get(0).entrySet()) {
			String key = entry.getKey().toString().toLowerCase();
			if (!Creatures.containsKey(key)) {
				Creatures.put(key, false);
			}
		}

		itemsCollection = new ArrayList();
		creaturesCollection = new ArrayList();

		itemsCollection.add(items);
		creaturesCollection.add(Creatures);

		config.set("itemsCollection", itemsCollection);
		config.set("creaturesCollection", creaturesCollection);

		config.options().copyDefaults(true);
		saveConfig();
	}

	@SuppressWarnings("unchecked")
	private void setDefaultConfig() {
		FileConfiguration config = getConfig();
		config.addDefault("minX", minX);
		config.addDefault("minZ", minZ);
		config.addDefault("maxX", maxX);
		config.addDefault("maxZ", maxZ);

		config.addDefault("worldName", worldName);

		config.addDefault("collectCreatures", collectCreatures);
		config.addDefault("collectItems", collectItems);

		config.addDefault("annonceInventoryItemsOnPlayerJoin", annonceInventoryItemsOnPlayerJoin);
		config.addDefault("annonceInventoryCreaturesOnPlayerJoin", annonceInventoryCreaturesOnPlayerJoin);

		config.addDefault("annonceNewItems", annonceNewItems);
		config.addDefault("annonceNewCreatures", annonceNewCreatures);

		config.addDefault("updateItemsOnChestClosed", updateItemsOnChestClosed);
		config.addDefault("updateCreaturesOnCreatureFeed", updateCreaturesOnCreatureFeed);

		config.addDefault("messagePrefix", messagePrefix);
		config.addDefault("annonceInventoryItemsOnPlayerJoinMessage", annonceInventoryItemsOnPlayerJoinMessage);
		config.addDefault("annonceInventoryCreaturesOnPlayerJoinMessage", annonceInventoryCreaturesOnPlayerJoinMessage);
		config.addDefault("annonceNewItemsMessage", annonceNewItemsMessage);
		config.addDefault("annonceNewCreaturesMessage", annonceNewCreaturesMessage);

		HashMap<String, Boolean> items = new HashMap<String, Boolean>();

		items.put("1", true);
		items.put("1:1", true);
		items.put("1:2", true);

		HashMap<String, Boolean> Creatures = new HashMap<String, Boolean>();

		Creatures.put("Cow", true);
		Creatures.put("Pig", true);
		Creatures.put("Rabbit", true);

		itemsCollection = new ArrayList();
		creaturesCollection = new ArrayList();

		itemsCollection.add(items);
		creaturesCollection.add(Creatures);

		config.addDefault("itemsCollection", itemsCollection);
		config.addDefault("creaturesCollection", creaturesCollection);

		config.options().copyDefaults(true);
		saveConfig();
	}

	private void getSavedConfig() {
		FileConfiguration config = getConfig();
		minX = config.getInt("minX");
		minZ = config.getInt("minZ");
		maxX = config.getInt("maxX");
		maxZ = config.getInt("maxZ");

		worldName = config.getString("worldName");

		collectCreatures = config.getBoolean("collectCreatures");
		collectItems = config.getBoolean("collectItems");

		annonceInventoryItemsOnPlayerJoin = config.getBoolean("annonceInventoryItemsOnPlayerJoin");
		annonceInventoryCreaturesOnPlayerJoin = config.getBoolean("annonceInventoryCreaturesOnPlayerJoin");

		annonceNewItems = config.getBoolean("annonceNewItems");
		annonceNewCreatures = config.getBoolean("annonceNewCreatures");

		updateItemsOnChestClosed = config.getBoolean("updateItemsOnChestClosed");
		updateCreaturesOnCreatureFeed = config.getBoolean("updateCreaturesOnCreatureFeed");

		messagePrefix = config.getString("messagePrefix");
		annonceInventoryItemsOnPlayerJoinMessage = config.getString("annonceInventoryItemsOnPlayerJoinMessage");
		annonceInventoryCreaturesOnPlayerJoinMessage = config.getString("annonceInventoryCreaturesOnPlayerJoinMessage");
		annonceNewItemsMessage = config.getString("annonceNewItemsMessage");
		annonceNewCreaturesMessage = config.getString("annonceNewCreaturesMessage");

		itemsCollection = config.getMapList("itemsCollection");
		creaturesCollection = config.getMapList("creaturesCollection");

		for (Map.Entry<?, ?> entry : itemsCollection.get(0).entrySet()) {
			String key = entry.getKey().toString().toLowerCase();
			if (Boolean.parseBoolean(entry.getValue().toString())) {
				if (!itemsToCollect.contains(key)) {
					itemsToCollect.add(key);
				}
			}
		}

		for (Map.Entry<?, ?> entry : creaturesCollection.get(0).entrySet()) {
			String key = entry.getKey().toString().toLowerCase();
			if (Boolean.parseBoolean(entry.getValue().toString())) {
				if (!creaturesToCollect.contains(key)) {
					creaturesToCollect.add(key);
				}
			}
		}

		config.options().copyDefaults(true);
		saveConfig();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (annonceInventoryCreaturesOnPlayerJoin && collectCreatures) {
			String msg = getMessage(annonceInventoryCreaturesOnPlayerJoinMessage, "");
			player.sendMessage(msg);
		}
		if (annonceInventoryItemsOnPlayerJoin && collectItems) {
			String msg = getMessage(annonceInventoryItemsOnPlayerJoinMessage, "");
			player.sendMessage(msg);
		}

	}

	@EventHandler
	public void invClose(InventoryCloseEvent event) {
		if (updateItemsOnChestClosed) {
			
			if ((event.getInventory().getHolder() instanceof Chest)) {
				Chest chest = (Chest) event.getInventory().getHolder();
				if (checkLocation(chest.getLocation())) {
					if (!chests.contains(chest)) {
						chests.add(chest);
					}
					broadcastMessage("good chest");
					refreshItems(chest);
				}
			}
			
			if ((event.getInventory().getHolder() instanceof DoubleChest)) {
				DoubleChest chest = (DoubleChest) event.getInventory().getHolder();
				if (checkLocation(chest.getLocation())) {
					if (!chests.contains(chest)) {
						chests.add(chest);
					}
					refreshItems(chest);
				}
			}
		}
	}

	@EventHandler
	public void onSignChanged(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("[Items]") || e.getLine(0).equalsIgnoreCase("[Creatures]")) {
			Sign sign = (Sign) e.getBlock().getState();
			sign.setLine(0, e.getLine(0));
			sign.update();
			Player player = e.getPlayer();
			updateSign(sign, player);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		// recount ?
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (updateCreaturesOnCreatureFeed) {
			Entity entity = e.getRightClicked();
			if (entity == null)
				return;

			if (entity instanceof Creature) {
				Creature a = (Creature) entity;
				addCreature(a, true);
				updateAllSigns();
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;

		if (e.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			Player player = e.getPlayer();
			if (checkLocation(sign.getLocation())) {
				if (sign.getLine(0).equalsIgnoreCase("[Creatures]")) {
					refreshCreatures();
				}else if (sign.getLine(0).equalsIgnoreCase("[Items]")) {
					refreshItems(null);
				}
			}
		}
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		Sign s = (Sign) e.getBlock().getState();
		Player player = e.getPlayer();
		updateSign(s, player);
	}

	private void addCreature(Creature Creature, Boolean annonceIfNew) {
		if (checkLocation(Creature.getLocation())) {
			String CreatureName = getCreatureName(Creature);
			if (creaturesCollected.contains(CreatureName) == false) {
				if (creaturesToCollect.contains(CreatureName)) {
					creaturesCollected.add(CreatureName);
					if (annonceNewCreatures && annonceIfNew) {
						String displayName = ItemNames.getCreatureDisplayName(CreatureName);
						String msg = getMessage(annonceNewCreaturesMessage, displayName);
						broadcastMessage(msg);
					}
				}
			}
		}
	}

	private String getCreatureName(Creature creature) {
		String creatureName = creature.getName();
		if (creature instanceof Horse) {
			Horse h = (Horse) creature;
			creatureName = h.getVariant().name();
		}
		creatureName = creatureName.toLowerCase();
		return creatureName;
	}

	private boolean checkLocation(Location block) {
		if (block.getX() >= minX && block.getX() <= maxX && block.getZ() >= minZ && block.getZ() <= maxZ) {
			return true;
		}
		return false;
	}

	private void updateAllSigns() {
		if (collectItems) {
			for (Sign sign : signsItems) {
				updateSign(sign, null);
			}
		}
		if (collectCreatures) {
			for (Sign sign : signsCreatures) {
				updateSign(sign, null);
			}
		}
	}

	private void updateSign(Sign sign, Player player) {

		if (checkLocation(sign.getLocation())) {
			int nbCollected = 0;
			int nbCollection = 0;
			boolean updateSign = false;
			if (sign.getLine(0).equalsIgnoreCase("[Creatures]")) {
				if (signsCreatures.contains(sign) == false) {
					signsCreatures.add(sign);
				}
				nbCollected = creaturesCollected.size();
				nbCollection = creaturesToCollect.size();
				updateSign = true;
				
			} else if (sign.getLine(0).equalsIgnoreCase("[Items]")) {
				if (signsItems.contains(sign) == false) {
					signsItems.add(sign);
				}
				nbCollected = itemsCollected.size();
				nbCollection = itemsToCollect.size();
				updateSign = true;
			}
			if (updateSign) {
				sign.setLine(1, ChatColor.DARK_BLUE + "==========");
				sign.setLine(2, ChatColor.RED + " " + nbCollected + "/" + nbCollection);
				sign.setLine(3, "");
				sign.update();
				if(player != null) {
					player.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Collection updated");
				}
			}
		}
	}

	private boolean listCommand(CommandSender sender, String[] args) {
		if(args.length == 3) {
			ListType listType = ListType.getListType(args[2]);
			if (args[1].equalsIgnoreCase("creatures")) {
				listCreatures(sender, listType);
				return true;
			} else if (args[1].equalsIgnoreCase("items")) {
				listItems(sender, listType);
				return true;
			}
		}
		return false;
	}
	
	private boolean refreshCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("itemcollector.refresh")) {
				sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
				return true;
			}
		}
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("creatures")) {
				refreshCreatures();
				sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Creatures refreshed");
				return true;
			} else if (args[1].equalsIgnoreCase("items")) {
				refreshItems(null);
				sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Items refreshed");
				return true;
			}
		}
		return false;
	}

	private boolean countCommand(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("itemcollector.remove.count")) {
				sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
				return false;
			}
		}
		if (collectCreatures) {
			String msg = getMessage(annonceInventoryCreaturesOnPlayerJoinMessage, "");
			sender.sendMessage(msg);
		}
		if (collectItems) {
			String msg = getMessage(annonceInventoryItemsOnPlayerJoinMessage, "");
			sender.sendMessage(msg);
		}
		if (!collectCreatures && !collectItems) {
			sender.sendMessage(messagePrefix + ChatColor.RED
					+ "Nothing to collect.  Turn ON Items-collection or Creatures-collection");
		}
		return true;
	}
	
	private boolean addCommand(CommandSender sender, String[] args) {
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("creature")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.add.creature")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
				if (creaturesToCollect.contains(args[2])) {
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "This Creature is already collected");
				} else {
					creaturesToCollect.add(args[2]);
					saveNewConfig();
					refreshCreatures();
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Creature added");
				}
				return true;
			} else if (args[1].equalsIgnoreCase("item")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.add.item")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
				if (itemsToCollect.contains(args[2])) {
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "This item is already collected");
				} else {
					itemsToCollect.add(args[2]);
					saveNewConfig();
					refreshItems(null);
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Item added");
				}
				return true;
			} 
		}
		return false;
	}
	
	private boolean removeCommand(CommandSender sender, String[] args) {
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("creature")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.remove.creature")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
				if (creaturesToCollect.contains(args[2])) {
					creaturesToCollect.remove(args[2]);
					saveNewConfig();
					refreshCreatures();
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Creature removed");
				} else {
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "This Creature not currently collected");
				}
				return true;
			} else if (args[1].equalsIgnoreCase("item")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.remove.item")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
				if (itemsToCollect.contains(args[2])) {
					itemsToCollect.remove(args[2]);
					saveNewConfig();
					refreshItems(null);
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Item removed");
				} else {
					sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "This item not currently collected");
				}
				return true;
			}
		}
		return false;
	}
	
	private boolean setCommand(CommandSender sender, String[] args) {
		if (args.length == 6 && args[1].equalsIgnoreCase("region")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector.set.region")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			minX = Integer.parseInt(args[2]);
			maxX = Integer.parseInt(args[3]);
			minZ = Integer.parseInt(args[4]);
			maxZ = Integer.parseInt(args[5]);
			saveNewConfig();
			refreshItems(null);
			refreshCreatures();
			sender.sendMessage(messagePrefix + ChatColor.DARK_GREEN + "Region modified");
			return true;
		} 
		else if (args.length == 4 && args[1].equalsIgnoreCase("option")) {
			boolean value = false;
			try  {
				value = Boolean.getBoolean(args[3]);
			}catch(Exception ex) {
				
			}
			switch(args[2].toLowerCase()) {
			case "collectcreatures":
				collectCreatures = value;
				break;
				
			case "collectitems":
				collectItems = value;
				break;
				
			case "annonceinventoryitemsonplayerjoin":
				annonceInventoryItemsOnPlayerJoin = value;
				break;
				
			case "annonceinventorycreaturesonplayerjoin":
				annonceInventoryCreaturesOnPlayerJoin = value;
				break;
				
			case "annoncenewitems":
				annonceNewItems = value;
				break;
				
			case "annoncenewcreatures":
				annonceNewCreatures = value;
				break;
				
			case "updateitemsonchestclosed":
				updateItemsOnChestClosed = value;
				break;
				
			case "updatecreaturesoncreaturefeed":
				updateCreaturesOnCreatureFeed = value;
				break;
				
			default:
				sender.sendMessage(messagePrefix + ChatColor.RED + "Invalid parameter '" + args[2].toLowerCase() + "'");
				return false;
			}
			sender.sendMessage(messagePrefix + ChatColor.DARK_PURPLE  + args[2] + ChatColor.GRAY + " set to " + ChatColor.GREEN + Boolean.toString(value));
			saveNewConfig();
			return true;
		}
		else if (args.length == 4 && args[1].equalsIgnoreCase("world")) {
			worldName = args[2];
			sender.sendMessage(messagePrefix + "World set to " + ChatColor.GREEN + worldName);
			saveNewConfig();
			refreshItems(null);
			refreshCreatures();
			return true;
		}
		else if (args.length == 4 && args[1].equalsIgnoreCase("message")) {
			String newMsg = args[3];
			switch(args[2].toLowerCase()) {
			case "messagePrefix":
				messagePrefix = newMsg;
				break;
				
			case "annonceinventoryitemsonplayerjoinmessage":
				annonceInventoryItemsOnPlayerJoinMessage = newMsg;
				break;
				
			case "annonceinventorycreaturesonplayerjoinmessage":
				annonceInventoryCreaturesOnPlayerJoinMessage = newMsg;
				break;
				
			case "annoncenewitemsmessage":
				annonceNewItemsMessage = newMsg;
				break;
				
			case "annoncenewcreaturesmessage":
				annonceNewCreaturesMessage = newMsg;
				break;
								
			default:
				sender.sendMessage(messagePrefix + ChatColor.RED + "Invalid parameter '" + args[2].toLowerCase() + "'");
				return false;
			}
			sender.sendMessage(messagePrefix + ChatColor.DARK_PURPLE  + args[2] + ChatColor.GRAY + " was " + ChatColor.GREEN + "changed");
			saveNewConfig();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (cmd.getName().equalsIgnoreCase("itemcollector")) {

			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("list")) {
					return listCommand(sender, args);
				}else if(args[0].equalsIgnoreCase("refresh")) {
					return refreshCommand(sender, args);
				}else if(args[0].equalsIgnoreCase("count")) {
					return countCommand(sender, args);
				}else if(args[0].equalsIgnoreCase("add")) {
					return addCommand(sender, args);
				}else if(args[0].equalsIgnoreCase("remove")) {
					return removeCommand(sender, args);
				}else if(args[0].equalsIgnoreCase("set")) {
					return setCommand(sender, args);
				}
			}
		}
		return false;
	}

	private void getChestsAndSigns() {
		World world = getServer().getWorld(worldName);

		int minXChunck = minX / 16;
		int maxXChunck = maxX / 16;
		int minZChunck = minZ / 16;
		int maxZChunck = maxZ / 16;

		chests.clear();
		signsCreatures.clear();
		signsItems.clear();

		for (int x = minXChunck; x <= maxXChunck; x++) {
			for (int z = minZChunck; z <= maxZChunck; z++) {
				world.loadChunk(x, z);
				Chunk chunk = world.getChunkAt(x, z);

				for (BlockState block : chunk.getTileEntities()) {
					if (checkLocation(block.getLocation())) {
						if (block instanceof Chest) {
							Chest chest = (Chest) block;
							chests.add(chest);
						}
						if (block instanceof Sign) {
							Sign sign = (Sign) block;
							if (sign.getLine(0).equalsIgnoreCase("[Creatures]")) {
								signsCreatures.add(sign);
							}
							if (sign.getLine(0).equalsIgnoreCase("[Items]")) {
								signsItems.add(sign);
							}
						}
					}
				}
			}
		}
	}

	private void refreshCollections() {
		refreshCreatures();
		refreshItems(null);
	}

	private void refreshCreatures() {
		if (collectCreatures) {
			getChestsAndSigns();
			creaturesCollected.clear();

			World world = getServer().getWorld(worldName);

			int minXChunck = minX / 16;
			int maxXChunck = maxX / 16;
			int minZChunck = minZ / 16;
			int maxZChunck = maxZ / 16;

			for (int x = minXChunck; x <= maxXChunck; x++) {
				for (int z = minZChunck; z <= maxZChunck; z++) {
					world.loadChunk(x, z);
					Chunk chunk = world.getChunkAt(x, z);

					for (Entity entity : chunk.getEntities()) {
						if (entity instanceof Creature) {
							Creature Creature = (Creature) entity;
							addCreature(Creature, false);
						}
					}
				}
			}
			updateAllSigns();
		}
	}

	private void refreshItems(InventoryHolder newChest) {
		if (collectItems) {
			getChestsAndSigns();
			HashSet<String> oldItems = new HashSet<String>();
			oldItems.addAll(itemsCollected);
			itemsCollected.clear();
			for (InventoryHolder chest : chests) {

				for (ItemStack items : chest.getInventory().getContents()) {
					if (items != null) {
						int id = items.getTypeId();

						short variant = items.getDurability();

						String itemId = id + "";
						if (variant != 0) {
							itemId = id + ":" + variant;
						}

						if (itemsToCollect.contains(itemId)) {
							if (annonceNewItems && sameLocation(chest, newChest) && !oldItems.contains(itemId)) {
								String displayName = ItemNames.getBlockDisplayName(itemId);
								broadcastMessage(getMessage(annonceNewItemsMessage, displayName));
								oldItems.add(itemId);
							}
							itemsCollected.add(itemId);
						}
					}
				}

			}
			updateAllSigns();
		}
	}

	private void listCreatures(CommandSender sender, ListType listType) {
		List<String> messages = new ArrayList<String>();
		for (String s : creaturesToCollect) {
			String displayName = ItemNames.getCreatureDisplayName(s);
			if ((listType == ListType.ALL || listType == ListType.COLLECTED) && creaturesCollected.contains(s)) {
				messages.add(ChatColor.GREEN + displayName);
			} else if ((listType == ListType.ALL || listType == ListType.MISSING) && !creaturesCollected.contains(s)) {
				messages.add(ChatColor.RED + displayName);
			}
		}
		Collections.sort(messages);
		for(String msg : messages) {
			sender.sendMessage(msg);
		}
	}

	private void listItems(CommandSender sender, ListType listType) {
		List<String> messages = new ArrayList<String>();
		for (String s : itemsToCollect) {
			String displayName = ItemNames.getBlockDisplayName(s);
			if ((listType == ListType.ALL || listType == ListType.COLLECTED) && itemsCollected.contains(s)) {
				messages.add(ChatColor.GREEN + displayName);
			} else if ((listType == ListType.ALL || listType == ListType.MISSING) && !itemsCollected.contains(s)) {
				messages.add(ChatColor.RED + displayName);
			}
		}
		Collections.sort(messages);
		for(String msg : messages) {
			sender.sendMessage(msg);
		}
	}

	private String getMessage(String message, String displayName) {
		return messagePrefix + message.replace("<itemName>", displayName).replace("<creatureName>", displayName)
				.replace("<nbCollectedItems>", itemsCollected.size() + "")
				.replace("<nbTotalItems>", itemsToCollect.size() + "")
				.replace("<nbCollectedCreatures>", creaturesCollected.size() + "")
				.replace("<nbTotalCreatures>", creaturesToCollect.size() + "");
	}

	private void broadcastMessage(String msg) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendMessage(msg);
		}
	}

	private boolean sameLocation(BlockState block1, BlockState block2) {
		if (block1 == null || block2 == null) {
			return false;
		}
		return block1.getX() == block2.getX() && block1.getY() == block2.getY() && block1.getZ() == block2.getZ();
	}
	
	private boolean sameLocation(InventoryHolder inv1, InventoryHolder inv2) {
		if(inv1 instanceof BlockState && inv2 instanceof BlockState) {
			BlockState block1 = (BlockState)inv1;
			BlockState block2 = (BlockState)inv2;
			return sameLocation(block1, block2);
		}
		return false;
	}
}
