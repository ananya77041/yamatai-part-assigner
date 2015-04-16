import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Player implements Comparable<Player> {
	private String name;
	private int year;
	private List<String> preferredSongs;
	private Set<String> assignedParts;
	private Set<String> assignedSongs;
	
	public Player(String s, int y, String[] pref) {
		name = s;
		year = y;
		preferredSongs = new ArrayList<String>();
		assignedParts = new HashSet<String>();
		assignedSongs = new HashSet<String>();
		for (int i=0; i < pref.length; i++) {
			preferredSongs.add(pref[i]);
		}
	}
	
	public String name() {
		return name;
	}
	
	public int year() {
		return year;
	}
	
	// number of preferences
	public int numPreferences() {
		return preferredSongs.size();
	}
	
	// array of song/part preferences
	public String[] songParts() {
		String[] songs = new String[preferredSongs.size()];
		for (int i = 0; i < preferredSongs.size(); i++) {
			songs[i] = preferredSongs.get(i);
		}
		return songs;
	}
	
	// song at rank i
	public String song(int i) {
		return preferredSongs.get(i).split(" ")[0];
	}
	
	// part at rank i
	public String part(int i) {
		String[] split = preferredSongs.get(i).split(" ");
		if (split.length > 1) return split[1];
		else return "any";
	}
	
	// rank of song
	public int getRank(String song) {
		return preferredSongs.indexOf(song)+1;
	}
	
	// assign a part to a player
	public void assignPart(String part) {
		assignedParts.add(part);
	}
	
	// assign a song to a player
	public void assignSong(String song) {
		assignedSongs.add(song);
	}
	
	// player's assigned parts
	public Iterable<String> assignedParts() {
		return assignedParts;
	}
	
	// player's assigned songs
	public Set<String> assignedSongs() {
		return assignedSongs;
	}
	
	// number of assigned parts
	public int numAssigned() {
		return assignedParts.size();
	}
	
	// string representation of a player's preferences
	@Override
	public String toString() {
		String player = name() + ", Year: " + year() + "\nPreferences:\n";
		for (String s : songParts()) player += getRank(s) + ". " + s + "\n";
		return player;
	}
	
	public String assignmentsToString() {
		String r = name() + ":\n";
		for (String s : assignedParts) r += s + "\n";
		return r;
	}
	
	// testing
	public static void main(String[] args) {
		In in = new In(args[0]);
		int numPlayers = in.readInt();
//		StdOut.println(numPlayers);
		Player[] players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			String name = in.readString();
//			StdOut.println(name);
			int year = in.readInt();
//			StdOut.println(year);
			int numPrefs = in.readInt();
//			StdOut.println(numPrefs);
			in.readLine();
			String[] prefs = new String[numPrefs];
			for (int j = 0; j < numPrefs; j++) {
				String part = in.readLine();
				prefs[j] = part;
//				StdOut.println(prefs[j][0] + " " + prefs[j][1]);
			}
			players[i] = new Player(name, year, prefs);
		}
		
		for (Player p : players) StdOut.println(p.toString());
	}

	@Override
	public int compareTo(Player p) {
		if (p.year() < this.year()) return 1;
		if (p.year() > this.year()) return -1;
		return 0;
	}

}
