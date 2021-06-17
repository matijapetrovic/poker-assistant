package bongcloud.cep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.Action;

import bots.bongcloudbot.ChangeTightnessEvent;
import bots.bongcloudbot.KnowledgeSessionHelper;
import bots.bongcloudbot.PlayerActionEvent;

public class InsertDecreaseTightnessPreviousTest {
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
		List<PlayerActionEvent> events = new ArrayList<PlayerActionEvent>();
		for (int i = 0; i < 5; i++) {
			events.add(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		}
		kSession.insert(new ChangeTightnessEvent("p1", -1, events));
		
		kSession.insert(events.get(0));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));
		int fired = kSession.fireAllRules();
		assertEquals(0, fired);
		kSession.insert(new PlayerActionEvent("p1", Action.callAction(0.0), true));

		fired = kSession.fireAllRules();
		assertEquals(1, fired);;
	}
}
