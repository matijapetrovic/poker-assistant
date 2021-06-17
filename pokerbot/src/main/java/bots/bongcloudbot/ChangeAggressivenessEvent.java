package bots.bongcloudbot;

import java.util.List;

public class ChangeAggressivenessEvent {
	private String name;
	private int change;
	private List<PlayerActionEvent> events;
	private boolean changed;
	
	public ChangeAggressivenessEvent(String name, int change, List<PlayerActionEvent> events) {
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
	
	public boolean intersect(List<PlayerActionEvent> events) {
		for (PlayerActionEvent event: events) {
			if (this.events.contains(event)) return true;
		}
		return false;
	}
}
