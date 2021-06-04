package bots.bongcloudbot;

import java.util.List;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class ChangeTightnessEvent {
	private String name;
	private int change;
	private List<PlayerActionEvent> events;
	private boolean changed;
	
	public ChangeTightnessEvent(String name, int change, List<PlayerActionEvent> events) {
		this.name = name;
		this.change = change;
		this.events = events;
		this.changed = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public List<PlayerActionEvent> getEvents() {
		return events;
	}

	public void setEvents(List<PlayerActionEvent> events) {
		this.events = events;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	
	
}
