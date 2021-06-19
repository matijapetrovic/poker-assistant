package bots.bongcloudbot;

import org.kie.api.definition.type.Role;

import com.biotools.meerkat.Action;

@Role(Role.Type.EVENT)
public class PlayerActionEvent {
	private String name;
	private Action action;
	private boolean preFlop;
	public PlayerActionEvent(String name, Action action, boolean preFlop) {
		super();
		this.name = name;
		this.action = action;
		this.preFlop = preFlop;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public boolean isPreFlop() {
		return preFlop;
	}
	public void setPreFlop(boolean preFlop) {
		this.preFlop = preFlop;
	}
	
	
}
