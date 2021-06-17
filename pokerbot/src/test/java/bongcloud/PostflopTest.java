package bongcloud;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.Mockito;

import com.biotools.meerkat.GameInfo;
import com.biotools.meerkat.HandEvaluator;
import com.biotools.meerkat.Holdem;

import bots.bongcloudbot.KnowledgeSessionHelper;
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
		gameInfo = Mockito.mock(GameInfo.class);
	}
	
	
//	@Test
//	public void calculateHandRank() {
//		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
//		Mockito.when(gameInfo.isPostFlop()).thenReturn(false);
//		kSession.setGlobal("gameInfo", gameInfo);
//		kSession.setGlobal("phase", 1);
//		HandInfo hi = new HandInfo();
//		
//		kSession.insert(hi);
//	    int fired = kSession.fireAllRules();
//	    kSession.delete(kSession.getFactHandle(hi));
//		
//	    assertThat(1, is(fired));
//
//	}
	
	@Test
	public void cbetIfRaisedAndNoOneRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
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
        
        assertThat(1, is(fired));
        assertEquals(hi.getAction().toString(), Action.betAction(gameInfo.getTotalPotSize() * 0.5 + (0.1 * hi.getNumPlayers())).toString());
	}

	@Test
	public void raiseIfSomeoneElseCbetsAndNoCallers() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
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

        assertThat(1, is(fired));
        assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, toCall*2).toString());
	}
	

	
	@Test
	public void veryStrongHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.9);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

        assertThat(1, is(fired));
        assertEquals(hi.getAction().toString(), Action.raiseAction(toCall, toCall*2).toString());
	}
	
	@Test
	public void goodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.80);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

        assertThat(1, is(fired));
        assertEquals(hi.getAction().toString(), Action.callAction(toCall).toString());
	}
	
	@Test
	public void notSoGoodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
		HandInfo hi = new HandInfo();
		kSession.setGlobal("gameInfo", gameInfo);
		kSession.setGlobal("phase", 1);
		double toCall = 10.0;
		hi.setToCall(toCall);
		hi.setHandRank(0.7);
		
		kSession.insert(hi);
	    int fired = kSession.fireAllRules();
	    kSession.delete(kSession.getFactHandle(hi));

        assertThat(1, is(fired));
        assertEquals(hi.getAction().toString(), Action.checkOrFoldAction(toCall).toString());
	}
}
