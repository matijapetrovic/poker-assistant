package bots.bongcloudbot;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;
import com.biotools.meerkat.Holdem;
import com.biotools.meerkat.Player;
import com.biotools.meerkat.PlayerInfo;
import com.biotools.meerkat.util.Preferences;

public class BongcloudBot implements Player {
	private Card card1;
	private Card card2;
	private int seat;
	private Preferences preferences;
	private GameInfo gameInfo;
	private int eventCount;

	private PlayerStats playerStats;
	
	private KieSession rulesSession;
	private KieSession cepSession;
	private KieContainer kContainer;
	private KieServices ks;
	
	private boolean didRaise;
	
	public BongcloudBot() {
		ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
		cepSession = kContainer.newKieSession("ksession-cep");
		rulesSession = kContainer.newKieSession("ksession-rules");
		playerStats = new PlayerStats();
		eventCount = 0;
	}
	
	public Action preFlopAction() {
		return null;
	}
	
	private List<PlayerDesc> getActivePlayers() {
        List<PlayerDesc> list = new ArrayList<PlayerDesc>();
        int player = this.seat;
        player = gameInfo.nextActivePlayer(player);
        while (player != this.seat) {
            String playerName = gameInfo.getPlayerName(player);
            list.add(playerStats.getPlayerDesc(playerName));
            player = gameInfo.nextActivePlayer(player);
        }
        return list;
    }
	
	@Override
	public Action getAction() {
		HandInfo hi = new HandInfo(card1, card2, seat, gameInfo, getActivePlayers(), didRaise);
		rulesSession.setGlobal("gameInfo", gameInfo);
		rulesSession.insert(hi);
		
		if (gameInfo.getStage() == Holdem.PREFLOP) {
			rulesSession.insert(PlayerDesc.Tight.NEUTRAL);
			rulesSession.fireAllRules();
			didRaise = hi.isDidRaise();
			rulesSession.delete(rulesSession.getFactHandle(hi));
			return hi.getAction();
		}
		else {
			rulesSession.fireAllRules();
			rulesSession.delete(rulesSession.getFactHandle(hi));
			return hi.getAction() == null ? Action.callAction(gameInfo): hi.getAction();
		}
	}

	public Card getCard1() {
		return card1;
	}

	public void setCard1(Card card1) {
		this.card1 = card1;
	}

	public Card getCard2() {
		return card2;
	}

	public void setCard2(Card card2) {
		this.card2 = card2;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public KieSession getkSession() {
		return rulesSession;
	}

	public void setkSession(KieSession kSession) {
		this.rulesSession = kSession;
	}

	public KieContainer getkContainer() {
		return kContainer;
	}

	public void setkContainer(KieContainer kContainer) {
		this.kContainer = kContainer;
	}

	public KieServices getKs() {
		return ks;
	}

	public void setKs(KieServices ks) {
		this.ks = ks;
	}

	@Override
	public void holeCards(Card card1, Card card2, int seat) {
		this.card1 = card1;
		this.card2 = card2;
		this.seat = seat;
		this.didRaise = false;
	}

	@Override
	public void init(Preferences preferences) {
		this.preferences = preferences;
	}
	

	@Override
	public void gameStartEvent(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		List<PlayerDesc> playerDescriptions = new ArrayList<>();
		for (int seat = 0; seat < gameInfo.getNumSeats(); seat++) {
			playerDescriptions.add(new PlayerDesc(gameInfo.getPlayerName(seat)));
		}
		playerStats.setPlayerDescriptions(playerDescriptions);
	}
	
	
	@Override
	public void actionEvent(int seat, Action action) {
		PlayerInfo pi = gameInfo.getPlayer(seat);
		String name = pi.getName();
		// create event for player
		eventCount += 1;
		System.out.println("ACTION EVENT" + eventCount);
		this.cepSession.insert(new PlayerActionEvent(name, action));
	}

	@Override
	public void dealHoleCardsEvent() {
		
	}

	@Override
	public void gameOverEvent() {
		
	}

	@Override
	public void gameStateChanged() {
		
	}

	@Override
	public void showdownEvent(int seat, Card card1, Card card2) {
		
	}

	@Override
	public void stageEvent(int stage) {
		
	}

	@Override
	public void winEvent(int arg0, double arg1, String arg2) {
		
	}


}
