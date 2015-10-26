package mc.itemcollector;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CollectionWriter {
	
	private HashSet<String> itemsToCollect = new HashSet<String>();
	private HashSet<String> creaturesToCollect = new HashSet<String>();
	
	private String outputFile;
	
	public CollectionWriter(String outputfile, HashSet<String> itemsToCollect, HashSet<String> creaturesToCollect) {
		this.outputFile = outputfile;
	}
	
	public void setOutputfile(String outputfile) {
		this.outputFile = outputfile;
	}
	
	public void setItemsToCollect(HashSet<String> itemsToCollect) {
		this.itemsToCollect = itemsToCollect;
	}
	
	public void setCreaturesToCollect(HashSet<String> creaturesToCollect) {
		this.creaturesToCollect = creaturesToCollect;
	}
	

	public void WriteFile(HashSet<String> itemsCollected, HashSet<String> creaturesCollected) {
		
		try {
			JSONArray jsonItems = getJSONArray(itemsCollected, itemsToCollect, true);
			JSONArray jsonCreatures = getJSONArray(creaturesCollected, creaturesToCollect, false);
			
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
			writer.print(getData(itemsCollected, creaturesCollected));
			writer.close();
		}catch(Exception ex) {
			Bukkit.getServer().getLogger().log(Level.SEVERE, ChatColor.RED + "Enable to write to" + outputFile + " " + ex.getMessage(), ex);
			ex.printStackTrace();
		}
	}
	
	private JSONObject getData(HashSet<String> itemsCollected, HashSet<String> creaturesCollected) {
		JSONObject result = new JSONObject();
		result.put("items", getJSONArray(itemsCollected, itemsToCollect, true));
		result.put("creatures", getJSONArray(creaturesCollected, creaturesToCollect, false));
		return result;
	}
	
	private JSONArray getJSONArray(HashSet<String> collected, HashSet<String> toCollect, Boolean isItem) {
		if(toCollect == null) {
			toCollect = new HashSet<String>();
		}
		JSONArray result = new JSONArray();
		for(String tc : toCollect) {
			JSONObject out = new JSONObject();
			out.put("id", tc);
			if(isItem) {
				out.put("name", ItemNames.getBlockDisplayName(tc));
			} else {
				out.put("name", ItemNames.getCreatureDisplayName(tc));
			}
			if(collected.contains(tc)) {
				out.put("collected", true);
			}else {
				out.put("collected", false);
			}
			result.add(out);
		}
		
		return result;
	}
}
