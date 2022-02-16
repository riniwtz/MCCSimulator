package data.blocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class Blocks {
	HashMap<String, String> blockMap = new HashMap<>();

	public Blocks() {
		try {
			BufferedReader blocksIDList = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Blocks.class.getResourceAsStream("/blocks"))));
			String blockID;
			while (((blockID = blocksIDList.readLine()) != null)) {
				blockMap.put(blockID, getConvertItemIDToItemName(blockID));
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private String getConvertItemIDToItemName(String itemID) {
		String[] itemName = itemID.split("_");
		StringBuilder itemIDBuilder = new StringBuilder();
		for (String i : itemName) {
			itemIDBuilder.append(toUpperCaseFirstChar(i)).append(" ");
		}
		return itemIDBuilder.substring(0, itemIDBuilder.length() - 1);
	}

	private static String toUpperCaseFirstChar(String text) {
		return (text.substring(0, 1).toUpperCase()) + (text.substring(1).toLowerCase());
	}

	public HashMap<String, String> getBlockMap() {
		return blockMap;
	}

	// value
	public boolean exists(String blockID) {
		if (blockID.startsWith("minecraft:"))
			blockID = blockID.substring(blockID.indexOf(':') + 1);
		String[] blockIDCollection = blockMap.keySet().toArray(new String[0]);
		for (String b : blockIDCollection) {
			if (blockID.equals(b))
				return true;
		}
		return false;
	}
}
