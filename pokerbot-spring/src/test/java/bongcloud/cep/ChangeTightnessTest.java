package bongcloud.cep;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biotools.meerkat.GameInfo;

import bots.bongcloudbot.ChangeTightnessEvent;
import bots.bongcloudbot.KnowledgeSessionHelper;
import bots.bongcloudbot.PlayerDesc;

public class ChangeTightnessTest {
	private static String sessionName = "ksession-cep";
	KieSession kSession = null;
	static KieContainer kieContainer;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}
	
	
	@Test
	public void increaseTightnessEvent() {
		kSession = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, sessionName);
		kSession.insert(new PlayerDesc("p1"));
		kSession.insert(new ChangeTightnessEvent("p1", 1, null));

		int fired = kSession.fireAllRules();
		assertThat(1, is(fired));
	}
}
