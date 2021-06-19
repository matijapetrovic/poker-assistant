package bots.bongcloudbot;

public class PlayerDesc {
	public enum Aggro { 
		PASSIVE(-1), NEUTRAL(0), AGGRESSIVE(1); 
		private int level;
		
		public int getLevel() {
			return level;
		}

		private Aggro(int level) {
			this.level = level;
		}
	};
	public enum Tight { 
		LOOSE(-1), NEUTRAL(0), TIGHT(1);
	
		private int level;
		
		public int getLevel() {
			return level;
		}

		public void setLevel(int level) { this.level = level; }

		private Tight(int level) {
			this.level = level;
		}
	};
	
	
	private Aggro aggressiveness;
	private Tight tightness;
	private String playerName;
	
	public PlayerDesc(String playerName) {
		this.playerName = playerName;
		this.aggressiveness = Aggro.NEUTRAL;
		this.tightness = Tight.NEUTRAL;
	}
	
	public PlayerDesc(String playerName, Aggro aggressivenes, Tight tightness) {
		super();
		this.playerName = playerName;
		this.aggressiveness = aggressivenes;
		this.tightness = tightness;
	}


	public Aggro getAggressiveness() {
		return aggressiveness;
	}
	public Tight getTightness() {
		return tightness;
	}
	public void setAggressiveness(Aggro aggressiveness) {
		this.aggressiveness = aggressiveness;
	}
	public void setTightness(Tight tightness) {
		this.tightness = tightness;
	}
	public void updateTightness(int change) {
		int level = this.tightness.level + change;
		level = Math.max(level, -1);
		level = Math.min(level, 1);
		this.tightness = Tight.values()[level + 1];
	}
	public void updateAggressiveness(int change) {
		int level = this.aggressiveness.level + change;
		level = Math.max(level, -1);
		level = Math.min(level, 1);
		this.aggressiveness = Aggro.values()[level + 1];
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
}
