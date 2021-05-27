package bots.bongcloudbot;

import java.util.HashMap;
import java.util.Map;

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
	
	// game attributes we need
	private Map<String, PlayerDesc> playerDescriptions;
	

	private KieSession kSession;
	private KieContainer kContainer;
	private KieServices ks;
	
	public BongcloudBot() {
		ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
		kSession = kContainer.newKieSession("ksession-rules");
		
		playerDescriptions = new HashMap<String, PlayerDesc>();
	}
	
	public Action preFlopAction() {
		return null;
	}
	
	@Override
	public Action getAction() {
		HandInfo hi = new HandInfo(card1, card2, seat, gameInfo);
		hi.evaluateHand();
		hi.setMatrixAction(card1, card2);
		kSession.insert(hi);
		
		Action action = Action.callAction(gameInfo.getAmountToCall(seat));
		kSession.fireAllRules();
		action = hi.getAction();
		//		switch (gameInfo.getStage()) {
//	        case Holdem.PREFLOP: {
//	        	kSession.fireAllRules();
//				action = hi.getPreliminaryAction();
//	        }
//	        break;
//	        case Holdem.FLOP: {
//	        	kSession.fireAllRules();
//				action = hi.getPreliminaryAction();
//	        }
//	        break;
//	        case Holdem.TURN: {
//	        	kSession.fireAllRules();
//				action = hi.getPreliminaryAction();
//	        }
//	        break;
//	        case Holdem.RIVER: {
//	        	kSession.fireAllRules();
//				action = hi.getPreliminaryAction();
//	        }
//	        break;
//	        default: {
//	            throw new Error();
//	        }
//	    }
		return action;
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

	public Map<String, PlayerDesc> getPlayerDescriptions() {
		return playerDescriptions;
	}

	public void setPlayerDescriptions(Map<String, PlayerDesc> playerDescriptions) {
		this.playerDescriptions = playerDescriptions;
	}

	public KieSession getkSession() {
		return kSession;
	}

	public void setkSession(KieSession kSession) {
		this.kSession = kSession;
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
	}

	@Override
	public void init(Preferences preferences) {
		this.preferences = preferences;
	}
	

	@Override
	public void gameStartEvent(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		for (int seat = 0; seat < gameInfo.getNumSeats(); seat++) {
			playerDescriptions.put(gameInfo.getPlayerName(seat), new PlayerDesc());
		}
	}
	
	
	
	@Override
	public void actionEvent(int seat, Action action) {
		PlayerInfo pi = gameInfo.getPlayer(seat);
		String name = pi.getName();
		// create event for player
		this.kSession.insert(new PlayerActionEvent(name, action));
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
