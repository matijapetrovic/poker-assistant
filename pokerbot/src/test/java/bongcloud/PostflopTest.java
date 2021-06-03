package bongcloud;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.GameInfo;

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
	KieSession kSession = null;
	static KieContainer kieContainer;
	private GameInfo gameInfo;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}
	
	
	@Test
	public void calculateHandRank() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		HandInfo hi = new HandInfo();
		// Treba dodati da je gameInfo.isPostFlop() == true
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertNotNull(hi.getHandRank());
	}
	
	@Test
	public void cbetIfRaisedAndNoOneRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		HandInfo hi = new HandInfo();
		hi.setNumActivePlayers(2);
		hi.setDidRaise(true);
		hi.setNumOfRaises(0);
		hi.setAction(null);
		// treba dodati gameInfo.getStage() == Holdem.FLOP
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertThat(hi.getAction(), is(Action.betAction(gameInfo.getTotalPotSize() * 0.5 + (0.1 * hi.getNumActivePlayers()))));
	}

	@Test
	public void raiseIfSomeoneElseCbetsAndNoCallers() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 10.0;
		HandInfo hi = new HandInfo();
		List<PlayerDesc> playersHand = new ArrayList<>(Arrays.asList());
		hi.setPlayersInHand(playersHand);
		hi.setNumActivePlayers(2);
		hi.setDidRaise(true);
		hi.setNumOfRaises(0);
		hi.setAction(null);
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertThat(hi.getAction(), is(Action.raiseAction(toCall, toCall*2)));
	}
	

	
	@Test
	public void veryStrongHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 10.0;
		HandInfo hi = new HandInfo();

		hi.setAction(null);
		// treba ubaciti handRank na preko 0.85 u hi
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertThat(hi.getAction(), is(Action.raiseAction(toCall, toCall*2)));
	}
	
	@Test
	public void goodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 10.0;
		HandInfo hi = new HandInfo();

		hi.setAction(null);
		// treba ubaciti handRank na preko 0.75 u hi
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
	
	@Test
	public void notSoGoodHandAndRaised() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "test-session");
		kSession.setGlobal("gameInfo", gameInfo);
		double toCall = 10.0;
		HandInfo hi = new HandInfo();

		hi.setAction(null);
		// treba ubaciti handRank na manje od 0.75 u hi
		int fired = kSession.fireAllRules();
        
        assertThat(1, is(fired));
        assertThat(hi.getAction(), is(Action.callAction(toCall)));
	}
}
