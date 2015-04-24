import java.util.Arrays;
import java.util.HashMap;


public class PartAssigner {
	private HashMap<String, Song> songs;
	private Player[] players;

	// assigns parts
	public PartAssigner(String songsFilename, String playersFilename) {
		readSongs(songsFilename);
		readPlayers(playersFilename);
		assign();
	}

	// reads songs and parts from file
	private void readSongs(String filename) {
		In in = new In(filename);
		int numSongs = in.readInt();
		songs = new HashMap<String, Song>();
		for (int i = 0; i < numSongs; i++) {
			String name = in.readString();
			int numParts = in.readInt();
			String[] parts = new String[numParts];
			int[] quantities = new int[numParts];
			for (int j = 0; j < numParts; j++) {
				parts[j] = in.readString(); quantities[j] = in.readInt();
			}
			songs.put(name, new Song(name, parts, quantities));
		}
	}

	// reads players and preferences from file
	private void readPlayers(String filename) {
		In in = new In(filename);
		int numPlayers = in.readInt();
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			String name = in.readString();
			int year = in.readInt();
			int numPrefs = in.readInt();
			in.readLine();
			String[] prefs = new String[numPrefs];
			for (int j = 0; j < numPrefs; j++) {
				String part = in.readLine();
				prefs[j] = part;
			}
			players[i] = new Player(name, year, prefs);
		}
		Arrays.sort(players);
	}

	// logic for assigning parts
	private void assign() {
		// assign based on player preferences
		for (Player p : players) {
			for (int i = 0; i < p.numPreferences(); i++) {
				String song = p.song(i); String part = p.part(i);
				if (!p.assignedSongs().contains(song)) {
					Song current = songs.get(song);

					if (part.equals("any")) {
						part = current.highestQuantity();
					}

					if (current.quantity(part) > 0) {
						current.assignPlayer(part, p.name());
						songs.put(song, current);
						p.assignPart(song + " " + part);
						p.assignSong(song);
					}
				}
			}
		}
		
		// fill in empty spots in songs
		for (String song : songs.keySet()) {
			for (String part : songs.get(song).parts()) {
				Song current = songs.get(song);
				while (current.quantity(part) != 0) {
					for (Player p : players) {
						if (!p.assignedSongs().contains(song)) {
							current.assignPlayer(part, p.name());
							songs.put(song, current);
							p.assignPart(song + " " + part);
							p.assignSong(song);
						}
					}
				}
			}
		}
	}

	// all songs
	public Iterable<Song> songs() {
		return songs.values();
	}

	// all players
	public Player[] players() {
		return players;
	}

	// executable
	public static void main(String[] args) {
		PartAssigner assigner = new PartAssigner(args[0], args[1]);
		for (Song s : assigner.songs()) StdOut.println(s.toString());
		for (Player p : assigner.players()) StdOut.println(p.assignmentsToString());
	}

}
