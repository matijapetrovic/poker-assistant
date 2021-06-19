package bots.bongcloudbot;

import java.util.List;


public class PlayerStats {
	private List<PlayerDesc> playerDescriptions;

	public PlayerStats() {
		
	}
	
	public PlayerStats(List<PlayerDesc> playerDescriptions) {
		super();
		this.playerDescriptions = playerDescriptions;
	}

	public List<PlayerDesc> getPlayerDescriptions() {
		return playerDescriptions;
	}

	public void setPlayerDescriptions(List<PlayerDesc> playerDescriptions) {
		this.playerDescriptions = playerDescriptions;
	}
	
	public PlayerDesc getPlayerDesc(String playerName)  {
		return playerDescriptions
				.stream()
				.filter(playerDesc -> playerDesc.getPlayerName().equalsIgnoreCase(playerName))
				.findAny()
				.orElseThrow();
	}
	
	
}
