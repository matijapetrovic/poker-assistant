package bongcloud;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.Mockito;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;
import com.biotools.meerkat.Hand;
import com.biotools.meerkat.Holdem;

import bots.bongcloudbot.HandEval;
import bots.bongcloudbot.HandInfo;
import bots.bongcloudbot.KnowledgeSessionHelper;
import bots.bongcloudbot.PlayerDesc;

public class PostflopTest {
	private static String kSessionName = "ksession-rules";
	KieSession kSession = null;
	static KieContainer kieContainer;
	
	private static GameInfo gameInfo;
	private static Card card1;
	private static Card card2;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}
	
	
	@Test
	public void calculateHandRank() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		Mockito.when(gameInfo.isPostFlop()).thenReturn(true);
		Hand h = new Hand();
		h.addCard(new Card("7c"));
		h.addCard(new Card("8c"));
		h.addCard(new Card("9c"));
		Card c1 = new Card("5c");
		Card c2 = new Card("6c");
		Mockito.when(gameInfo.getBoard()).thenReturn(h);
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		HandEval handEval = Mockito.mock(HandEval.class);
		Mockito.when(handEval.evalHand(c1, c2, h, 0)).thenReturn(0.9);
		HandInfo hi = new HandInfo(c1, c2, handEval);
		hi.setAction(Action.callAction(0.0));
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);

	}
	
	@Test
	public void cbetIfRaisedAndNoOneRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(0);
		Mockito.when(gameInfo.getStage()).thenReturn(Holdem.FLOP);
		Mockito.when(gameInfo.getTotalPotSize()).thenReturn(20.0);
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		HandInfo hi = new HandInfo();
		hi.setNumPlayers(2);
		hi.setDidRaise(true);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);
        assertEquals(hi.getAction().toString(), Action.betAction(gameInfo.getTotalPotSize() * 0.5 + (0.1 * hi.getNumPlayers())).toString());
	}

	@Test
	public void raiseIfSomeoneElseCbetsAndNoCallers() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		Mockito.when(gameInfo.getNumRaises()).thenReturn(1);
		Mockito.when(gameInfo.getStage()).thenReturn(Holdem.FLOP);
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(Arrays.asList());
		hi.setPlayersInHand(playersHand);
		hi.setNumActivePlayers(2);
		hi.setDidRaise(true);
		hi.setToCall(toCall);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);
        assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, toCall*2).toString());
	}
	

	
	@Test
	public void veryStrongHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.9);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);
        assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, toCall*2).toString());
	}
	
	@Test
	public void goodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.80);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);
        assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void notSoGoodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		gameInfo = Mockito.mock(GameInfo.class);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.7);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

		assertEquals(1, fired);
        assertEquals(hi.getAction().toString(), Action.checkOrFoldAction(toCall).toString());
	}
}
