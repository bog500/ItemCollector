package mc.itemcollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
import org.spigotmc.Metrics;

public class ItemCollector extends JavaPlugin implements Listener {

	SettingsManager settings = SettingsManager.getInstance();
	CollectionWriter writer;
	
	protected UpdateChecker updateChecker;

	private String messagePrefix = ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + ChatColor.GREEN + "ItemCollector"
			+ ChatColor.GRAY + "]" + ChatColor.RESET;

	private int minX = -100;
	private int minZ = -100;
	private int maxX = 100;
	private int maxZ = 100;

	private String worldName = "world";
	private String outputFile = "/itemcollector.json";

	private boolean generateOutputFile = false;
	
	private boolean collectCreatures = true;
	private boolean collectItems = true;

	private boolean announceInventoryItemsOnPlayerJoin = true;
	private boolean announceInventoryCreaturesOnPlayerJoin = true;

	private boolean announceNewItems = true;
	private boolean announceNewCreatures = true;

	private boolean updateItemsOnChestClosed = true;
	private boolean updateCreaturesOnCreatureFeed = true;
	private boolean updateCreaturesOnCreatureDamaged = false;

	private String announceInventoryItemsOnPlayerJoinMessage = ChatColor.DARK_RED + "Item collection: "
			+ ChatColor.DARK_GREEN + "<nbCollectedItems> / <nbTotalItems>";
	private String announceInventoryCreaturesOnPlayerJoinMessage = ChatColor.DARK_RED + "Creature collection: "
			+ ChatColor.DARK_GREEN + " <nbCollectedCreatures> / <nbTotalCreatures>";
	private String announceNewItemsMessage = ChatColor.DARK_RED + "New item in the collection: " + ChatColor.BOLD + ""
			+ ChatColor.GREEN + "<itemName>";
	private String announceNewCreaturesMessage = ChatColor.DARK_RED + "New Creature in the collection: " + ChatColor.BOLD
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
		
		callMetric();
		
		this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/itemcollector/files.rss");
		if(this.updateChecker.updateNeeded()) {
			this.getLogger().info(ChatColor.RED + "A new version is available: " + this.updateChecker.getVersion());
			this.getLogger().info(ChatColor.RED + "Download from: " + this.updateChecker.getLink());
		}else {
			this.getLogger().info(ChatColor.GREEN + "ItemCollector is up-to-date");
		}
		
		getSavedConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		writer = new CollectionWriter(outputFile, itemsToCollect, creaturesToCollect);
		
		try
		{
			refreshCollections();
		}
		catch(Exception ex) {
			Bukkit.getServer().getLogger().log(Level.SEVERE, ChatColor.RED + "ItemCollector encountered an error at startup!  Make sure the world and region are defined correctly in the configuration.", ex);
		}
		
		writer.setItemsToCollect(itemsToCollect);
		writer.setCreaturesToCollect(creaturesToCollect);
		writer.WriteFile(itemsCollected, creaturesCollected);
	}

	@Override
	public void onDisable() {
		saveNewConfig();
		Bukkit.getServer().getLogger().info("ItemCollector Disabled!");
	}
	
	private void callMetric() {
		try
		{
			Metrics metrics = new Metrics();
			metrics.start();
		}catch(Exception ex) {
			// failed
		}
	}

	private void saveNewConfig() {
		FileConfiguration config = getConfig();
		config.set("minX", minX);
		config.set("minZ", minZ);
		config.set("maxX", maxX);
		config.set("maxZ", maxZ);

		config.set("worldName", worldName);
		config.set("outputFile", outputFile);

		config.set("collectCreatures", collectCreatures);
		config.set("collectItems", collectItems);
		
		config.set("generateOutputFile", generateOutputFile);

		config.set("announceInventoryItemsOnPlayerJoin", announceInventoryItemsOnPlayerJoin);
		config.set("announceInventoryCreaturesOnPlayerJoin", announceInventoryCreaturesOnPlayerJoin);

		config.set("announceNewItems", announceNewItems);
		config.set("announceNewCreatures", announceNewCreatures);

		config.set("updateItemsOnChestClosed", updateItemsOnChestClosed);
		config.set("updateCreaturesOnCreatureFeed", updateCreaturesOnCreatureFeed);
		config.set("updateCreaturesOnCreatureDamaged", updateCreaturesOnCreatureDamaged);

		config.set("messagePrefix", messagePrefix);
		config.set("announceInventoryItemsOnPlayerJoinMessage", announceInventoryItemsOnPlayerJoinMessage);
		config.set("announceInventoryCreaturesOnPlayerJoinMessage", announceInventoryCreaturesOnPlayerJoinMessage);
		config.set("announceNewItemsMessage", announceNewItemsMessage);
		config.set("announceNewCreaturesMessage", announceNewCreaturesMessage);

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
		
		config.addDefault("outputFile", outputFile);		
		config.addDefault("generateOutputFile", generateOutputFile);

		config.addDefault("collectCreatures", collectCreatures);
		config.addDefault("collectItems", collectItems);

		config.addDefault("announceInventoryItemsOnPlayerJoin", announceInventoryItemsOnPlayerJoin);
		config.addDefault("announceInventoryCreaturesOnPlayerJoin", announceInventoryCreaturesOnPlayerJoin);

		config.addDefault("announceNewItems", announceNewItems);
		config.addDefault("announceNewCreatures", announceNewCreatures);

		config.addDefault("updateItemsOnChestClosed", updateItemsOnChestClosed);
		config.addDefault("updateCreaturesOnCreatureFeed", updateCreaturesOnCreatureFeed);
		config.addDefault("updateCreaturesOnCreatureDamaged", updateCreaturesOnCreatureDamaged);

		config.addDefault("messagePrefix", messagePrefix);
		config.addDefault("announceInventoryItemsOnPlayerJoinMessage", announceInventoryItemsOnPlayerJoinMessage);
		config.addDefault("announceInventoryCreaturesOnPlayerJoinMessage", announceInventoryCreaturesOnPlayerJoinMessage);
		config.addDefault("announceNewItemsMessage", announceNewItemsMessage);
		config.addDefault("announceNewCreaturesMessage", announceNewCreaturesMessage);

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
		outputFile = config.getString("outputFile");		

		collectCreatures = config.getBoolean("collectCreatures");
		collectItems = config.getBoolean("collectItems");
		generateOutputFile = config.getBoolean("generateOutputFile");

		announceInventoryItemsOnPlayerJoin = config.getBoolean("announceInventoryItemsOnPlayerJoin");
		announceInventoryCreaturesOnPlayerJoin = config.getBoolean("announceInventoryCreaturesOnPlayerJoin");

		announceNewItems = config.getBoolean("announceNewItems");
		announceNewCreatures = config.getBoolean("announceNewCreatures");

		updateItemsOnChestClosed = config.getBoolean("updateItemsOnChestClosed");
		updateCreaturesOnCreatureFeed = config.getBoolean("updateCreaturesOnCreatureFeed");
		updateCreaturesOnCreatureDamaged = config.getBoolean("updateCreaturesOnCreatureDamaged");

		messagePrefix = config.getString("messagePrefix");
		announceInventoryItemsOnPlayerJoinMessage = config.getString("announceInventoryItemsOnPlayerJoinMessage");
		announceInventoryCreaturesOnPlayerJoinMessage = config.getString("announceInventoryCreaturesOnPlayerJoinMessage");
		announceNewItemsMessage = config.getString("announceNewItemsMessage");
		announceNewCreaturesMessage = config.getString("announceNewCreaturesMessage");

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
		if (announceInventoryCreaturesOnPlayerJoin && collectCreatures) {
			String msg = getMessage(announceInventoryCreaturesOnPlayerJoinMessage, "");
			player.sendMessage(msg);
		}
		if (announceInventoryItemsOnPlayerJoin && collectItems) {
			String msg = getMessage(announceInventoryItemsOnPlayerJoinMessage, "");
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
		if(updateCreaturesOnCreatureDamaged) {
			Entity entity = e.getEntity();
			if (entity == null)
				return;

			if (entity instanceof Creature) {
				Creature a = (Creature) entity;
				boolean isNew = addCreature(a, true);
				if(isNew) {
					updateAllSigns();
				}
			}
		}
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

	private boolean addCreature(Creature Creature, Boolean announceIfNew) {
		if (checkLocation(Creature.getLocation())) {
			String creatureName = getCreatureName(Creature).toLowerCase().replace(" ", "_");
			if (creaturesCollected.contains(creatureName) == false) {
				if (creaturesToCollect.contains(creatureName)) {
					creaturesCollected.add(creatureName);
					if (announceNewCreatures && announceIfNew) {
						String displayName = ItemNames.getCreatureDisplayName(creatureName);
						String msg = getMessage(announceNewCreaturesMessage, displayName);
						broadcastMessage(msg);
					}
				}
				return true;
			}
		}
		return false;
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
		if(generateOutputFile) {
			writer.WriteFile(itemsCollected, creaturesCollected);
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
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.list.creatures")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
				listCreatures(sender, listType);
				return true;
			} else if (args[1].equalsIgnoreCase("items")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (!player.hasPermission("itemcollector.list.items")) {
						sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
						return true;
					}
				}
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
			if (!player.hasPermission("itemcollector.count")) {
				sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
				return true;
			}
		}
		if (collectCreatures) {
			String msg = getMessage(announceInventoryCreaturesOnPlayerJoinMessage, "");
			sender.sendMessage(msg);
		}
		if (collectItems) {
			String msg = getMessage(announceInventoryItemsOnPlayerJoinMessage, "");
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
					writer.setCreaturesToCollect(creaturesToCollect);
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
					writer.setItemsToCollect(itemsToCollect);
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
					writer.setCreaturesToCollect(creaturesToCollect);
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
					writer.setItemsToCollect(itemsToCollect);
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
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector.set.option")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			boolean value = false;
			try  {
				value = Boolean.getBoolean(args[3]);
			}catch(Exception ex) {
				
			}
			switch(args[2].toLowerCase()) {
			case "generateoutputfile":
				generateOutputFile = value;
				break;
			case "collectcreatures":
				collectCreatures = value;
				break;
				
			case "collectitems":
				collectItems = value;
				break;
				
			case "announceinventoryitemsonplayerjoin":
				announceInventoryItemsOnPlayerJoin = value;
				break;
				
			case "announceinventorycreaturesonplayerjoin":
				announceInventoryCreaturesOnPlayerJoin = value;
				break;
				
			case "announcenewitems":
				announceNewItems = value;
				break;
				
			case "announcenewcreatures":
				announceNewCreatures = value;
				break;
				
			case "updateitemsonchestclosed":
				updateItemsOnChestClosed = value;
				break;
				
			case "updatecreaturesoncreaturefeed":
				updateCreaturesOnCreatureFeed = value;
				
			case "updateCreaturesOnCreatureDamaged":
				updateCreaturesOnCreatureDamaged = value;
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
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector.set.world")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			worldName = args[2];
			sender.sendMessage(messagePrefix + "World set to " + ChatColor.GREEN + worldName);
			saveNewConfig();
			refreshItems(null);
			refreshCreatures();
			return true;
		}
		else if (args.length == 4 && args[1].equalsIgnoreCase("outputfile")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector.set.outputfile")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			outputFile = args[2];
			sender.sendMessage(messagePrefix + "Outputfile set to " + ChatColor.GREEN + outputFile);
			saveNewConfig();
			refreshItems(null);
			refreshCreatures();
			return true;
		}
		else if (args.length == 4 && args[1].equalsIgnoreCase("message")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("itemcollector.set.message")) {
					sender.sendMessage(messagePrefix + ChatColor.RED + "You are not permitted to do this!");
					return true;
				}
			}
			String newMsg = args[3];
			switch(args[2].toLowerCase()) {
			case "messagePrefix":
				messagePrefix = newMsg;
				break;
				
			case "announceinventoryitemsonplayerjoinmessage":
				announceInventoryItemsOnPlayerJoinMessage = newMsg;
				break;
				
			case "announceinventorycreaturesonplayerjoinmessage":
				announceInventoryCreaturesOnPlayerJoinMessage = newMsg;
				break;
				
			case "announcenewitemsmessage":
				announceNewItemsMessage = newMsg;
				break;
				
			case "announcenewcreaturesmessage":
				announceNewCreaturesMessage = newMsg;
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

		int minXChunk = minX / 16;
		int maxXChunk = maxX / 16;
		int minZChunk = minZ / 16;
		int maxZChunk = maxZ / 16;

		chests.clear();
		signsCreatures.clear();
		signsItems.clear();

		for (int x = minXChunk; x <= maxXChunk; x++) {
			for (int z = minZChunk; z <= maxZChunk; z++) {
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

			int minXChunk = minX / 16;
			int maxXChunk = maxX / 16;
			int minZChunk = minZ / 16;
			int maxZChunk = maxZ / 16;

			for (int x = minXChunk; x <= maxXChunk; x++) {
				for (int z = minZChunk; z <= maxZChunk; z++) {
					world.loadChunk(x, z);
					Chunk chunk = world.getChunkAt(x, z);

					for (Entity entity : chunk.getEntities()) {
						if (entity instanceof Creature) {
							Creature creature = (Creature) entity;
							addCreature(creature, false);
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
							if (announceNewItems && sameLocation(chest, newChest) && !oldItems.contains(itemId)) {
								String displayName = ItemNames.getBlockDisplayName(itemId);
								broadcastMessage(getMessage(announceNewItemsMessage, displayName));
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

	
	private boolean sameLocation(Object inv1, Object inv2) {
		
		if (inv1 == null || inv2 == null) {
			return false;
		}
		
		Location loc1 = null;
		Location loc2 = null;
		
		if(inv1 instanceof BlockState) {
			BlockState block = (BlockState)inv1;
			loc1 = block.getLocation();
		}else if(inv1 instanceof DoubleChest) {
			DoubleChest doublechest = (DoubleChest)inv1;
			loc1 = doublechest.getLocation();
		}
		
		if(inv2 instanceof BlockState) {
			BlockState block = (BlockState)inv2;
			loc2 = block.getLocation();
		}else if(inv2 instanceof DoubleChest) {
			DoubleChest doublechest = (DoubleChest)inv2;
			loc2 = doublechest.getLocation();
		}
		
		if(loc1 == null || loc2 == null)
			return false;
				
		return (Math.floor(loc1.getX()) == Math.floor(loc2.getX()) || Math.ceil(loc1.getX()) == Math.ceil(loc2.getX()))
				&&
				(Math.floor(loc1.getY()) == Math.floor(loc2.getY()) || Math.ceil(loc1.getY()) == Math.ceil(loc2.getY()))
				&&
				(Math.floor(loc1.getZ()) == Math.floor(loc2.getZ()) || Math.ceil(loc1.getZ()) == Math.ceil(loc2.getZ()));
		
	}
}
