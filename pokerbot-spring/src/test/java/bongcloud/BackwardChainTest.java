package bongcloud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.mockito.Mockito;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;

import bots.bongcloudbot.BongcloudAction;
import bots.bongcloudbot.HandEval;
import bots.bongcloudbot.HandInfo;
import bots.bongcloudbot.KnowledgeSessionHelper;

public class BackwardChainTest {
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
    public void goAllIn() {
        kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
        gameInfo = Mockito.mock(GameInfo.class);

        int seat = 1;

        Mockito.when(gameInfo.isPreFlop()).thenReturn(true);
        Mockito.when(gameInfo.getBankRoll(seat)).thenReturn(0.4);

        kSession.setGlobal("gameInfo", gameInfo);
        kSession.setGlobal("phase", 1);
        HandEval handEval = Mockito.mock(HandEval.class);

        HandInfo hi = new HandInfo();
        hi.setSeat(seat);
        hi.setPreliminaryAction(BongcloudAction.RERAISE);


        kSession.insert(hi);
        int fired = kSession.fireAllRules();
        kSession.delete(kSession.getFactHandle(hi));

        assertEquals(1, fired);
        assertEquals(Action.raiseAction(gameInfo, gameInfo.getBankRoll(seat)).toString(), hi.getAction().toString());
    }

    @Test
    public void goAllInNotPreflop() {
        kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
        gameInfo = Mockito.mock(GameInfo.class);

        int seat = 1;

        Mockito.when(gameInfo.isPreFlop()).thenReturn(false);
        Mockito.when(gameInfo.getBankRoll(seat)).thenReturn(0.4);

        kSession.setGlobal("gameInfo", gameInfo);
        kSession.setGlobal("phase", 1);

        HandInfo hi = new HandInfo();
        hi.setSeat(seat);
        hi.setPreliminaryAction(BongcloudAction.RERAISE);


        kSession.insert(hi);
        int fired = kSession.fireAllRules();
        kSession.delete(kSession.getFactHandle(hi));

        assertEquals(0, fired);
        assertNull(hi.getAction());
    }

    @Test
    public void goAllInNotLowBankroll() {
        kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
        gameInfo = Mockito.mock(GameInfo.class);

        int seat = 1;

        Mockito.when(gameInfo.isPreFlop()).thenReturn(true);
        Mockito.when(gameInfo.getBankRoll(seat)).thenReturn(0.6);

        kSession.setGlobal("gameInfo", gameInfo);
        kSession.setGlobal("phase", 1);

        HandInfo hi = new HandInfo();
        hi.setSeat(seat);
        hi.setPreliminaryAction(BongcloudAction.RERAISE);


        kSession.insert(hi);
        int fired = kSession.fireAllRules();
        kSession.delete(kSession.getFactHandle(hi));

        assertEquals(0, fired);
        assertNull(hi.getAction());
    }

    @Test
    public void goAllInNotStrongHand() {
        kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, kSessionName);
        gameInfo = Mockito.mock(GameInfo.class);

        int seat = 1;

        Mockito.when(gameInfo.isPreFlop()).thenReturn(true);
        Mockito.when(gameInfo.getBankRoll(seat)).thenReturn(0.4);

        kSession.setGlobal("gameInfo", gameInfo);
        kSession.setGlobal("phase", 1);

        HandInfo hi = new HandInfo();
        hi.setSeat(seat);
        hi.setPreliminaryAction(BongcloudAction.LIMP);


        kSession.insert(hi);
        int fired = kSession.fireAllRules();
        kSession.delete(kSession.getFactHandle(hi));

        assertEquals(0, fired);
        assertNull(hi.getAction());
    }
}
