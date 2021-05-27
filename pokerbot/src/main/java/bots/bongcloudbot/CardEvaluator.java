package bots.bongcloudbot;

import com.biotools.meerkat.Card;


public class CardEvaluator {
	private static int card1Value;
	private static int card2Value;
	
	private final static int STRONG_TRESHOLD = 20;
	private final static int MEDIUM_TRESHOLD = 10;
	
	public CardsStrength cardsStrength;

	
	public CardEvaluator(Card card1, Card card2) {
		card1Value = card1.getRank();
		card2Value = card2.getRank();
	}
	
	public CardsStrength evaluateHand() {
		int cardsValueSum = card1Value + card2Value;
		if (cardsValueSum >= STRONG_TRESHOLD) {
			cardsStrength = CardsStrength.STRONG;
		}
		else if (cardsValueSum > MEDIUM_TRESHOLD && cardsValueSum < STRONG_TRESHOLD) {
			cardsStrength = CardsStrength.MEDIUM;
		}
		else {
			cardsStrength =  CardsStrength.WEAK;
		}
		return cardsStrength;
	}
	
}


