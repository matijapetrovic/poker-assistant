package bongcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import bots.bongcloudbot.KnowledgeSessionHelper;
import bots.bongcloudbot.PlayerDesc.Tight;
import bots.bongcloudbot.PlayerDesc.Aggro;
import bots.bongcloudbot.PlayerDesc;
import bots.bongcloudbot.HandInfo;
import bots.bongcloudbot.BongcloudAction;
import bots.bongcloudbot.StartingHandMatrix;
import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;
import bots.bongcloudbot.BongcloudAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreflopTest {
	KieSession kSession = null;
	static KieContainer kieContainer;
	private GameInfo gameInfo;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Test
	public void playstyleTight() {
		KieContainer kContainer =  KnowledgeSessionHelper.createRuleBase();
		KieSession kSession =  kContainer.newKieSession("test-rules");
		PlayerDesc playerDesc = new PlayerDesc("player-desc", Aggro.PASSIVE, Tight.TIGHT);
		kSession.insert(playerDesc);
	    int fired = kSession.fireAllRules();
	        
        assertThat(1, is(fired));
        assertThat(playerDesc.getTightness(), is(Tight.TIGHT));
	}
	
	@Test
	public void playstyleNeutral() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		PlayerDesc playerDesc = new PlayerDesc("player-desc", Aggro.PASSIVE, Tight.NEUTRAL);
		kSession.insert(playerDesc);
	    int fired = kSession.fireAllRules();
	        
        assertThat(1, is(fired));
        assertThat(playerDesc.getTightness(), is(Tight.NEUTRAL));
	}
	
	@Test
	public void playstyleLoose() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		PlayerDesc playerDesc = new PlayerDesc("player-desc", Aggro.PASSIVE, Tight.LOOSE);
		kSession.insert(playerDesc);
	    int fired = kSession.fireAllRules();
	        
        assertThat(1, is(fired));
        assertThat(playerDesc.getTightness(), is(Tight.LOOSE));
	}
	
	
	@Test
	public void setPreliminaryAction() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		StartingHandMatrix matrix = new StartingHandMatrix();
		Card card1 = new Card("2c");
		Card card2 = new Card("5c");
		HandInfo hi = new HandInfo(card1, card2, 1, gameInfo, new ArrayList<PlayerDesc>(), true);
		kSession.insert(matrix);
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	        
        assertThat(1, is(fired));
        assertNotNull(hi.getPreliminaryAction());
	}
	
	@Test
	public void preliminaryFoldAndToCallIsZero() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double toCall = 0.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.FOLD);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void preliminaryFoldAndToCallIsNotZero() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.FOLD);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void preliminaryCallWhenFree() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void preliminaryCallWhenNoRaise() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(0);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void preliminaryCallWhenMoreThanOneRaise() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(5);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void preliminaryCallWhenOneRaiseButTooBig() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 + 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.foldAction(toCall)));
	}
	
	@Test
	public void preliminaryCallWhenOnlyOneRaiseAndLooseField() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 - 1;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(
				Arrays.asList(
						new PlayerDesc("player-desc1", Aggro.PASSIVE, Tight.LOOSE),
						new PlayerDesc("player-desc2", Aggro.PASSIVE, Tight.LOOSE),
						new PlayerDesc("player-desc3", Aggro.PASSIVE, Tight.LOOSE)));
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(1);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, bigBLindSize * 2.5)));
	}
	
	@Test
	public void preliminaryCallWhenOnlyOneRaiseAndTighField() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 - 1;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(
				Arrays.asList(
						new PlayerDesc("player-desc1", Aggro.PASSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc2", Aggro.PASSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc3", Aggro.PASSIVE, Tight.TIGHT)));
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(1);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	
	@Test
	public void preliminaryRaiseWhenNoRaises() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(0);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, bigBLindSize * 4)));
	}
	
	@Test
	public void preliminaryRaiseWhenOneRaiseAndToCallIsSmallerThanBigBlindSize() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 - 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, bigBLindSize * 4)));
	}
	
	@Test
	public void preliminaryRaiseWhenOneRaiseAndToCallIsGreaterThanBigBlindSize() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 + 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, bigBLindSize * 4)));
	}
	
	@Test
	public void preliminaryRaiseWhen3PlusBet() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 + 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(3);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.foldAction(toCall)));
	}
	
	@Test
	public void preliminaryRaiseWhenAggressivePlayers() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 - 1;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(
				Arrays.asList(
						new PlayerDesc("player-desc1", Aggro.AGGRESSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc2", Aggro.AGGRESSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc3", Aggro.AGGRESSIVE, Tight.TIGHT)));
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		hi.setNumOfRaises(2);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, toCall * 2)));
	}
	
	@Test
	public void preliminaryPremium() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setAction(null);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertThat(hi.getAction(), is(Action.raiseAction(toCall, bigBLindSize * 2)));
	}
}
