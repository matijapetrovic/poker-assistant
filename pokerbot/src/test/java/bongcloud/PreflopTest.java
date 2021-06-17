package bongcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.Mockito;

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
	private static String kSessionName = "ksession-rules";
	KieSession kSession = null;
	static KieContainer kieContainer;
	
	private static GameInfo gameInfo;
	private static Card card1;
	private static Card card2;
	
//	private KieServices ks =KieServices.Factory.get();
//	private KieContainer kContainer =  ks.getKieClasspathContainer();
//	private KieSession kSession = kContainer.newKieSession("ksession-rules");



	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
		gameInfo = Mockito.mock(GameInfo.class);
		card1 = new Card("2c");
		card2 = new Card("10c");
		
		Mockito.when(gameInfo.getBigBlindSize()).thenReturn(10.0);
		Mockito.when(gameInfo.isPostFlop()).thenReturn(false);
		
	}

	@Test
	public void playstyleTight() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.insert(PlayerDesc.Tight.TIGHT);
	    int fired = kSession.fireAllRules();
	        
        assertThat(1, is(fired));
	}
	
	@Test
	public void playstyleNeutral() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.insert(PlayerDesc.Tight.NEUTRAL);
	    int fired = kSession.fireAllRules();
	    
        assertThat(1, is(fired));
	}
	
	@Test
	public void playstyleLoose() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.insert(PlayerDesc.Tight.LOOSE);
	    int fired = kSession.fireAllRules();
	    
        assertThat(1, is(fired));
	}
	
//	@Test
//	public void setPreliminaryAction() {
//		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
//		kSession.setGlobal("gameInfo", gameInfo);
//		HandInfo hi = new HandInfo();
//		hi.setCard1(card1);
//		hi.setCard2(card2);
//		
//		kSession.insert(new StartingHandMatrix());
//		kSession.insert(hi);
//	    int fired = kSession.fireAllRules();
//	    
//        assertThat(1, is(fired));
//        //assertNotNull(hi.getPreliminaryAction());
//	}
//	
	@Test
	public void preliminaryFoldAndToCallIsZero() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 0.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.FOLD);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void preliminaryFoldAndToCallIsNotZero() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.FOLD);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.foldAction(toCall).toString());
	}
	
	@Test
	public void preliminaryCallWhenFree() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 0.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void preliminaryCallWhenNoRaise() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setNumOfRaises(0);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void preliminaryCallWhenMoreThanOneRaise() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(5);
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 1.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.foldAction(toCall).toString());
	}
	
	@Test
	public void preliminaryCallWhenOneRaiseButTooBig() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 + 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.CALL);
		hi.setToCall(toCall);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    System.out.println(hi.getAction().toString());
	    System.out.println(hi.getAction().toString());
	    assertEquals(hi.getAction().toString(), Action.foldAction(toCall).toString());
	}
	
	@Test
	public void preliminaryCallWhenOnlyOneRaiseAndLooseField() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		kSession.setGlobal("gameInfo", gameInfo);
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
		hi.setNumOfRaises(1);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, bigBLindSize * 2.5).toString());
	}
	
	@Test
	public void preliminaryCallWhenOnlyOneRaiseAndTighField() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		kSession.setGlobal("gameInfo", gameInfo);
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
		hi.setNumOfRaises(1);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	
	@Test
	public void preliminaryRaiseWhenNoRaises() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(0);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setNumOfRaises(0);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, bigBLindSize * 4).toString());
	}
	
	@Test
	public void preliminaryRaiseWhenOneRaiseAndToCallIsSmallerThanBigBlindSize() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 - 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void preliminaryRaiseWhenOneRaiseAndToCallIsGreaterThanBigBlindSize() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 + 1;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setNumOfRaises(1);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.foldAction(toCall).toString());
	}
	
	@Test
	public void preliminaryRaiseWhen3PlusBet() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(5);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 5 + 1;
		HandInfo hi = new HandInfo();
	    hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setNumOfRaises(3);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.foldAction(toCall).toString());
	}
	
	@Test
	public void preliminaryRaiseWhenAggressivePlayers() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(2);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = bigBLindSize * 4 - 1;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(
				Arrays.asList(
						new PlayerDesc("player-desc1", Aggro.AGGRESSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc2", Aggro.AGGRESSIVE, Tight.TIGHT),
						new PlayerDesc("player-desc3", Aggro.AGGRESSIVE, Tight.TIGHT)));
		hi.setPreliminaryAction(BongcloudAction.RAISE);
		hi.setToCall(toCall);
		hi.setPlayersInHand(playersHand);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
		
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, toCall * 2).toString());
	}
	
	@Test
	public void preliminaryPremium() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		kSession.setGlobal("gameInfo", gameInfo);
		double bigBLindSize = gameInfo.getBigBlindSize();
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		hi.setPreliminaryAction(BongcloudAction.PREMIUM);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));
	    
	    assertThat(1, is(fired));
	    assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, bigBLindSize * 2).toString());
	}
}
