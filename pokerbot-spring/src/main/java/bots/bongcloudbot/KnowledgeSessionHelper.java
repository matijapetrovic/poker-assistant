package bots.bongcloudbot;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {
	
	public static KieContainer createRuleBase() {
		KieServices ks = KieServices.Factory.get();
		return ks.getKieClasspathContainer();
	}
	
	public static StatelessKieSession getStatlessKnowledgeSession(KieContainer kieContainer, String sessionName) {
		StatelessKieSession kSession = kieContainer.newStatelessKieSession(sessionName);
		return kSession;
	}
	
	public static KieSession getStatefulKnowledgeSession(KieContainer kieContainer, String sessionName) {
		KieSession kSession = kieContainer.newKieSession(sessionName);
		return kSession;
	}


}
