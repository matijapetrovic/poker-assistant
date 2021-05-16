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
		
		private Tight(int level) {
			this.level = level;
		}
	};
	
	
	private Aggro aggressivenes;
	private Tight tightness;
	
	public PlayerDesc() {
		this.aggressivenes = Aggro.NEUTRAL;
		this.tightness = Tight.NEUTRAL;
	}
	
	public PlayerDesc(Aggro aggressivenes, Tight tightness) {
		super();
		this.aggressivenes = aggressivenes;
		this.tightness = tightness;
	}


	public Aggro getAggressivenes() {
		return aggressivenes;
	}
	public Tight getTightness() {
		return tightness;
	}
	public void setAggressivenes(Aggro aggressivenes) {
		this.aggressivenes = aggressivenes;
	}
	public void setTightness(Tight tightness) {
		this.tightness = tightness;
	}
}
