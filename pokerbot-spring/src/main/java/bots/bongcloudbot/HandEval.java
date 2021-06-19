package bots.bongcloudbot;

import com.biotools.meerkat.Card;
import com.biotools.meerkat.Hand;
import com.biotools.meerkat.HandEvaluator;

public class HandEval {

	public Double evalHand(Card c1, Card c2, Hand h, int numPlayers) {
		return HandEvaluator.handRank(c1, c2, h, numPlayers);
	}
}
