package bots.bongcloudbot;

import org.kie.api.definition.type.PropertyReactive;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;

@PropertyReactive
public class HandInfo {
	private Card card1;
	private Card card2;
	private Action action;
	public Action preliminaryAction;
	
	private CardEvaluator handEvaluator;
	
	private int numPlayers; 	  // number of players
	private int numActivePlayers; // number of players left in the hand (including us)
	private double toCall;        // amount of money bongcloud bot shoul call
	private int numOfRaises;
	private boolean sameSuit;
	private double potSize;		 // amount of money in game
	private double bankRoll;     // amount of money I have
	private Integer IR;
	public CardsStrength cardsStrength;
	
	

	public HandInfo(Card card1, Card card2, int seat, GameInfo gameInfo) {
		this.card1 = card1;
		this.card2 = card2;
		numPlayers = gameInfo.getNumPlayers();
		numActivePlayers = gameInfo.getNumActivePlayers();
		toCall = gameInfo.getAmountToCall(seat);
		numOfRaises = gameInfo.getNumRaises();
		potSize = gameInfo.getTotalPotSize();
		bankRoll = gameInfo.getBankRoll(seat);
		sameSuit = isSameSuit();
		
		handEvaluator = new CardEvaluator(card1, card2);
	}
	
	private boolean isSameSuit() {
		return card1.getSuit() == card2.getSuit();
	}
	
	public void evaluateHand() {
		cardsStrength =  handEvaluator.evaluateHand();
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
	
	public CardEvaluator getHandEvaluator() {
		return handEvaluator;
	}

	public void setHandEvaluator(CardEvaluator handEvaluator) {
		this.handEvaluator = handEvaluator;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public int getNumActivePlayers() {
		return numActivePlayers;
	}

	public void setNumActivePlayers(int numActivePlayers) {
		this.numActivePlayers = numActivePlayers;
	}

	public double getToCall() {
		return toCall;
	}

	public void setToCall(double toCall) {
		this.toCall = toCall;
	}

	public int getNumOfRaises() {
		return numOfRaises;
	}

	public void setNumOfRaises(int numOfRaises) {
		this.numOfRaises = numOfRaises;
	}

	public double getPotSize() {
		return potSize;
	}

	public void setPotSize(double potSize) {
		this.potSize = potSize;
	}

	public double getBankRoll() {
		return bankRoll;
	}

	public void setBankRoll(double bankRoll) {
		this.bankRoll = bankRoll;
	}

	public CardsStrength getCardsStrength() {
		return cardsStrength;
	}

	public void setCardsStrength(CardsStrength cardsStrength) {
		this.cardsStrength = cardsStrength;
	}

	public Action getPreliminaryAction() {
		return preliminaryAction;
	}

	public void setPreliminaryAction(Action preliminaryAction) {
		this.preliminaryAction = preliminaryAction;
	}

	public void setSameSuit(boolean sameSuit) {
		this.sameSuit = sameSuit;
	}
	
}
