import java.util.Set;


public class Part {
	private String type;
	private int num;
	private int numRemaining;
	private Set<Player> assigned;
	
	public Part(String s) {
		type = s;
		assigned = null;
	}
	
	public String type() {
		return type;
	}
	
	public int quantity() {
		return num;
	}
	
	public Set<Player> player() {
		return assigned;
	}
	
	public void addPlayer(Player player) {
		assigned.add(player);
	}
	

}
