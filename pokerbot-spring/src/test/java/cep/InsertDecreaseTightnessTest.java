package cep;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.Action;

import bots.bongcloudbot.KnowledgeSessionHelper;
import bots.bongcloudbot.PlayerActionEvent;

public class InsertDecreaseTightnessTest {
	private static String sessionName = "ksession-cep";
	KieSession kSession = null;
	static KieContainer kieContainer;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}
	
	
	@Test
	public void insertDecreaseTightnessEvent() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, sessionName);
		int fired;
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		fired = kSession.fireAllRules();
		assertEquals(0, fired);
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		fired = kSession.fireAllRules();
		assertEquals(1, fired);
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		fired = kSession.fireAllRules();
		assertEquals(0, fired);
	}
	

}
