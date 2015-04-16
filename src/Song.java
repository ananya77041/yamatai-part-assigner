import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Song {
	private String songName;
	private Map<String, Integer> partList;
	private Map<String, HashSet<String>> partAssignments;
	private int numParts;
	
	// constructor
	public Song(String name, String[] parts, int[] quantities) {
		songName = name;
		numParts = parts.length;
		partList = new HashMap<String, Integer>();
		partAssignments = new HashMap<String, HashSet<String>>();
		for (int i = 0; i < parts.length; i++) {
			partList.put(parts[i], quantities[i]);
		}
	}
	
	// name of song
	public String name() {
		return songName;
	}
	
	// list of parts
	public Iterable<String> parts() {
		return partList.keySet();
	}
	
	// number of parts
	public int numParts() {
		return numParts;
	}
	
	// quantity of a particular part
	public int quantity(String part) {
		return partList.get(part);
	}
	
	// part with highest quantity
	public String highestQuantity() {
		int highest = 0;
		String highestQuantity = "";
		for (String part : partList.keySet()) {
			if (partList.get(part) > highest) {
				highestQuantity = part;
				highest = partList.get(part);
			}
		}
		return highestQuantity;
	}
	
	// players for particular part
	public Iterable<String> players(String part) {
		if (partAssignments.containsKey(part)) return partAssignments.get(part);
		else return null;
	}
	
	// assign player to part and decrease available quantity
	public void assignPlayer(String part, String player) {
		HashSet<String> players = new HashSet<String>();
		if (partAssignments.containsKey(part)) {
			players = partAssignments.get(part);
		}
		players.add(player);
		partAssignments.put(part, players);
		partList.put(part, partList.get(part) - 1);
	}
	
	// string representation of song
	@Override
	public String toString() {
		String s = name() + " has " + numParts() + " parts.\n";
		for (String part : parts()) {
			String s2 = "";
			Iterable<String> players = players(part);
			if (players != null) for (String p : players(part)) s2 += p + " ";
			s += quantity(part) + " " + part + ": " + s2 + "\n";
		}
		return s;
	}
	
	// testing
	public static void main(String[] args) {
		In in = new In(args[0]);
		int numSongs = in.readInt();
		Song[] songs = new Song[numSongs];
		for (int i = 0; i < numSongs; i++) {
			String name = in.readString();
			int numParts = in.readInt();
			String[] parts = new String[numParts];
			int[] quantities = new int[numParts];
			for (int j = 0; j < numParts; j++) {
				parts[j] = in.readString(); quantities[j] = in.readInt();
			}
			songs[i] = new Song(name, parts, quantities);
		}
		for (Song s : songs) StdOut.println(s.toString());
	}

}
