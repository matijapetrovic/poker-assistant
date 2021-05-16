package bots.bongcloudbot;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;
import com.biotools.meerkat.Player;
import com.biotools.meerkat.PlayerInfo;
import com.biotools.meerkat.util.Preferences;

public class BongcloudBot implements Player {
	private Card card1;
	private Card card2;
	private int seat;
	private Preferences preferences;
	private GameInfo gameInfo;
	
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
	
	@Override
	public Action getAction() {
		
		if (gameInfo.isPreFlop()) {
			HandInfo hi = new HandInfo(card1, card2);
			kSession.insert(hi);
			kSession.fireAllRules();
			return hi.getAction();
		}
		return Action.callAction(gameInfo.getAmountToCall(seat));
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
