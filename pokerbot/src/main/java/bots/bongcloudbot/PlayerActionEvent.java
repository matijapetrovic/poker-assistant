package bots.bongcloudbot;

import org.kie.api.definition.type.Role;

import com.biotools.meerkat.Action;

@Role(Role.Type.EVENT)
public class PlayerActionEvent {
	private String name;
	private Action action;
	public PlayerActionEvent(String name, Action action) {
		super();
		this.name = name;
		this.action = action;
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
	
	
}
