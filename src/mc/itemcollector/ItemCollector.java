package mc.itemcollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemCollector extends JavaPlugin implements Listener {
	
	SettingsManager settings = SettingsManager.getInstance();
	
	private String messagePrefix = ChatColor.ITALIC + "" + ChatColor.GRAY + "[" + ChatColor.GREEN + "ItemCollector" + ChatColor.GRAY + "]" + ChatColor.RESET;
	
	private int minX = -100;
	private int minZ = -100;
	private int maxX = 100;
	private int maxZ = 100;
	
	private boolean collectAnimals = true;
	private boolean collectItems = true;
	
	private boolean annonceInventoryItemsOnPlayerJoin = true;
	private boolean annonceInventoryAnimalsOnPlayerJoin = true;
	
	private boolean annonceNewItems = true;
	private boolean annonceNewAnimals = true;
	
	private boolean updateItemsOnChestClosed = true;
	private boolean updateAnimalsOnAnimalFeed = true;
	
	private String annonceInventoryItemsOnPlayerJoinMessage = ChatColor.DARK_RED + "Item collection: " + ChatColor.DARK_GREEN + "<nbCollectedItems> / <nbTotalItems>";
	private String annonceInventoryAnimalsOnPlayerJoinMessage = ChatColor.DARK_RED + "Animal collection: " + ChatColor.DARK_GREEN + " <nbCollectedAnimals> / <nbTotalAnimals>";
	private String annonceNewItemsMessage = ChatColor.DARK_RED + "New item in the collection: " + ChatColor.BOLD + "" + ChatColor.GREEN + "<itemName>";
	private String annonceNewAnimalsMessage = ChatColor.DARK_RED + "New animal in the collection: " + ChatColor.BOLD + "" + ChatColor.GREEN + "<animalName>";
	
	private List<Map<?, ?>> itemsCollection;
	private List<Map<?, ?>> animalsCollection;
	
	private HashSet<String> itemsCollected = new HashSet<String>();
	private HashSet<String> animalsCollected = new HashSet<String>();
	
	private HashSet<String> itemsToCollect = new HashSet<String>();
	private HashSet<String> animalsToCollect = new HashSet<String>();
	
	private List<Sign> signsItems = new ArrayList<Sign>();
	private List<Sign> signsAnimals = new ArrayList<Sign>();
	private List<Chest> chests = new ArrayList<Chest>();
		
	@Override
	public void onEnable() {
		settings.setup(this);
		
		// Bukkit.getServer().broadcastMessage
		Bukkit.getServer().getLogger().info("ItemCollector Enabled!");
		//setDefaultConfig();
		getSavedConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getChestsAndSigns();
		refreshCollections();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getLogger().info("ItemCollector Disabled!");
	}
	
	
	@SuppressWarnings("unchecked")
	private void setDefaultConfig() {
	    FileConfiguration config = getConfig();
	    config.addDefault("minX", minX);
	    config.addDefault("minZ", minZ);
	    config.addDefault("maxX", maxX);
	    config.addDefault("maxZ", maxZ);
	    
	    config.addDefault("collectAnimals", collectAnimals);
	    config.addDefault("collectItems", collectItems);
	    	    
	    config.addDefault("annonceInventoryItemsOnPlayerJoin", annonceInventoryItemsOnPlayerJoin);
	    config.addDefault("annonceInventoryAnimalsOnPlayerJoin", annonceInventoryAnimalsOnPlayerJoin);
	    
	    config.addDefault("annonceNewItems", annonceNewItems);
	    config.addDefault("annonceNewAnimals", annonceNewAnimals);
	    
	    config.addDefault("updateItemsOnChestClosed", updateItemsOnChestClosed);
	    config.addDefault("updateAnimalsOnAnimalFeed", updateAnimalsOnAnimalFeed);
	    
	    config.addDefault("messagePrefix", messagePrefix);
	    config.addDefault("annonceInventoryItemsOnPlayerJoinMessage", annonceInventoryItemsOnPlayerJoinMessage);
	    config.addDefault("annonceInventoryAnimalsOnPlayerJoinMessage", annonceInventoryAnimalsOnPlayerJoinMessage);
	    config.addDefault("annonceNewItemsMessage", annonceNewItemsMessage);
	    config.addDefault("annonceNewAnimalsMessage", annonceNewAnimalsMessage);
	    
	    HashMap<String,Boolean> items = new HashMap<String,Boolean>();
	    
	    items.put("1", true);
	    items.put("1:1", true);
	    items.put("1:2", true);
	    
	    HashMap<String,Boolean> animals = new HashMap<String,Boolean>();
	    
	    animals.put("Cow", true);
	    animals.put("Pig", true);
	    animals.put("Rabbit", true);
	    
	    itemsCollection = new ArrayList();
	    animalsCollection = new ArrayList();
	    
	    itemsCollection.add(items);
	    animalsCollection.add(animals);
	    
	    config.addDefault("itemsCollection", itemsCollection);
	    config.addDefault("animalsCollection", animalsCollection);
	   
	    config.options().copyDefaults(true);
	    saveConfig();
	}
	
	private void getSavedConfig() {
	    FileConfiguration config = getConfig();
	    minX = config.getInt("minX");
	    minZ = config.getInt("minZ");
	    maxX = config.getInt("maxX");
	    maxZ = config.getInt("maxZ");
	    
	    collectAnimals = config.getBoolean("collectAnimals");
	    collectItems = config.getBoolean("collectItems");
	    
	    annonceInventoryItemsOnPlayerJoin = config.getBoolean("annonceInventoryItemsOnPlayerJoin");
	    annonceInventoryAnimalsOnPlayerJoin = config.getBoolean("annonceInventoryAnimalsOnPlayerJoin");
	    	
	    annonceNewItems = config.getBoolean("annonceNewItems");
	    annonceNewAnimals = config.getBoolean("annonceNewAnimals");
		
	    updateItemsOnChestClosed = config.getBoolean("updateItemsOnChestClosed");
	    updateAnimalsOnAnimalFeed = config.getBoolean("updateAnimalsOnAnimalFeed");
    
	    messagePrefix = config.getString("messagePrefix");
	    annonceInventoryItemsOnPlayerJoinMessage = config.getString("annonceInventoryItemsOnPlayerJoinMessage");
	    annonceInventoryAnimalsOnPlayerJoinMessage = config.getString("annonceInventoryAnimalsOnPlayerJoinMessage");
	    annonceNewItemsMessage = config.getString("annonceNewItemsMessage");
	    annonceNewAnimalsMessage = config.getString("annonceNewAnimalsMessage");

	    itemsCollection = config.getMapList("itemsCollection");
	    animalsCollection = config.getMapList("animalsCollection");
	    
	    for (Map.Entry<?, ?> entry : itemsCollection.get(0).entrySet()) {
	    	String key = entry.getKey().toString().toLowerCase();
	    	if(Boolean.parseBoolean(entry.getValue().toString())) {
	    		if(!itemsToCollect.contains(key)) {
	    			itemsToCollect.add(key);
	    		}
	    	}
	    }
	    
	    for (Map.Entry<?, ?> entry : animalsCollection.get(0).entrySet()) {
	    	String key = entry.getKey().toString().toLowerCase();
	    	if(Boolean.parseBoolean(entry.getValue().toString())) {
	    		if(!animalsToCollect.contains(key)) {
	    			animalsToCollect.add(key);
	    		}
	    	}
	    }
	    	    	    	   
	    config.options().copyDefaults(true);
	    saveConfig();
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
            Player p = e.getPlayer();
            p.sendMessage(ChatColor.GREEN + "Welcome to the server!");
    }
	
	@EventHandler
	public void invClose(InventoryCloseEvent event) {
		// Cancel method if not a chest
		if(!(event.getInventory().getHolder() instanceof Chest)) return;
		Chest c = (Chest)event.getInventory().getHolder();
		// do something
	}
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
            if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
            if (e.getClickedBlock().getState() instanceof Animals) {
            	Animals a = (Animals)e.getClickedBlock().getState();
            	addAnimal(a);
            }
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                updateSign(sign);
            }
    }

    private void addAnimal(Animals animal) {
    	if (checkLocation(animal.getLocation())) {
			String animalName = animal.getName();
			if (animal instanceof Horse) {
				Horse h = (Horse) animal;
				animalName = h.getVariant().name();
			}
			animalName = animalName.toLowerCase();

			if (animalsCollected.contains(animalName) == false) {
				if(animalsToCollect.contains(animalName)) {
					animalsCollected.add(animalName);
				}
			}
		}
	}

	@EventHandler
    public void onSignChange(SignChangeEvent e) {
    	Sign s = (Sign) e.getBlock().getState();
    	updateSign(s);
    }
    
    private boolean checkLocation(Location block) {
    	if (block.getX() >= minX && block.getX() <= maxX && block.getZ() >= minZ && block.getZ() <= maxZ) {
    		return true;
    	}
    	return false;
    }
    
    
	private void updateSign(Sign sign) {
		if (checkLocation(sign.getLocation())) {
			int nbCollected = 0;
			int nbCollection = 0;
			if (sign.getLine(0).equalsIgnoreCase("[Animals]")) {
				if (signsAnimals.contains(sign) == false) {
					signsAnimals.add(sign);
				}
				sign.setLine(0, ChatColor.DARK_BLUE + "[Animals]");
				nbCollected = itemsCollected.size();
				nbCollection = itemsToCollect.size();
			} else if (sign.getLine(0).equalsIgnoreCase("[Items]")) {
				if (signsItems.contains(sign) == false) {
					signsItems.add(sign);
				}
				sign.setLine(0, ChatColor.DARK_BLUE + "[Items]");
				nbCollected = animalsCollected.size();
				nbCollection = animalsToCollect.size();
			}
			sign.setLine(1, ChatColor.DARK_BLUE + "==========");
			sign.setLine(2, ChatColor.RED + " " + nbCollected + "/" + nbCollection);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("itemcollector")) {
				sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
				return true;
			}

			if (cmd.getName().equalsIgnoreCase("itemcollector")) {
				player.sendMessage(ChatColor.AQUA + "It Works from player too !! " + ChatColor.RED + player.getName());

				getConfig().set("message", "new message");
				saveConfig();
			}
		}
		else {
			// console command
			if (cmd.getName().equalsIgnoreCase("itemcollector")) {
				if(args.length == 1 && args[0].equalsIgnoreCase("count")) {
					if(collectAnimals) {
						int nbCollected = animalsCollected.size();
						int nbCollection = animalsToCollect.size();
						sender.sendMessage(ChatColor.DARK_GREEN + "Animals: " + ChatColor.RED + " " + nbCollected + "/" + nbCollection);
					}	
					if(collectItems) {
						int nbCollected = itemsCollected.size();
						int nbCollection = itemsToCollect.size();
						sender.sendMessage(ChatColor.DARK_GREEN + "Items: " + ChatColor.RED + " " + nbCollected + "/" + nbCollection);
					}	
					if(!collectAnimals && !collectItems) {
						sender.sendMessage(ChatColor.RED + "Nothing to collect.  Turn ON items collection or animals collection");
					}
				}
			}
			return true;
		}

		return true;

	}

	private void getChestsAndSigns() {
		World world = getServer().getWorld("world");

		int minXChunck = minX / 16;
		int maxXChunck = maxX / 16;
		int minZChunck = minZ / 16;
		int maxZChunck = maxZ / 16;

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
							if (sign.getLine(0).equalsIgnoreCase("[Animals]")) {
								signsAnimals.add(sign);
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
		refreshAnimals();
		refreshItems();	
	}
	
	private void refreshAnimals() {
		if (collectAnimals) {
			animalsCollected.clear();
			
			World world = getServer().getWorld("world");

			int minXChunck = minX / 16;
			int maxXChunck = maxX / 16;
			int minZChunck = minZ / 16;
			int maxZChunck = maxZ / 16;
			
			for (int x = minXChunck; x <= maxXChunck; x++) {
				for (int z = minZChunck; z <= maxZChunck; z++) {
					world.loadChunk(x, z);
					Chunk chunk = world.getChunkAt(x, z);

					for (Entity entity : chunk.getEntities()) {
						if(entity instanceof Animals) {
							Animals a = (Animals)entity;
							addAnimal(a);
						}
					}
				}
			}
		}
	}
	
	private void refreshItems() {
		if (collectItems) {
			itemsCollected.clear();
			for(Chest chest : chests) {
				for (ItemStack items : chest.getInventory().getContents()) {
					if (items != null) {
						int id = items.getTypeId();
						
						Material mat = items.getType();
						short variant = items.getDurability();

						String bukkitName = mat.name();										
						
						String displayName = ItemNames.valueOf(bukkitName).toString();
						
						if(items.getData() instanceof Wool) {
							Wool w = (Wool)items.getData();
							String variantPrefix = w.getColor().name();
						}
						
					}
				}
			}
		}
	}
}
