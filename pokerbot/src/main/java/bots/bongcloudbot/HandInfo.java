package bots.bongcloudbot;

import org.kie.api.definition.type.PropertyReactive;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;

@PropertyReactive
public class HandInfo {
	private Card card1;
	private Card card2;
	private Action action;
	
	public HandInfo(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
	}
	
	public Card getCard1() {
		return card1;
	}
	public void setCard1(Card card1) {
		this.card1 = card1;
	}
	public Card getCard2() {
		return card2;
	}
	public void setCard2(Card card2) {
		this.card2 = card2;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
}
