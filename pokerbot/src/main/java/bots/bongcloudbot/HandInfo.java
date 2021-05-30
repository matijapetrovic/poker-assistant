package bots.bongcloudbot;

import java.util.List;

import org.kie.api.definition.type.PropertyReactive;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.GameInfo;
import com.biotools.meerkat.Hand;
import com.biotools.meerkat.HandEvaluator;

@PropertyReactive
public class HandInfo {
	private Card card1;
	private Card card2;
	private Action action;
	private BongcloudAction preliminaryAction;
	

	private int numPlayers; 	  // number of players
	private int numActivePlayers; // number of players left in the hand (including us)
	private double toCall;        // amount of money bongcloud bot shoul call
	private int numOfRaises;
	private boolean sameSuit;
	private double potSize;		 // amount of money in game
	private double bankRoll;     // amount of money I have
	private Integer IR;

	private Double handRank;
	private boolean didRaise;

	private double handRange;
	private double winChance;
	private double profitRaise;
	private double profitCall;
	
	private List<PlayerDesc> playersInHand;
	private final int BEST_HANDS_SIZE = 25;
    private int[][] bestHands;
	
	public HandInfo(
			Card card1, 
			Card card2, 
			int seat, 
			GameInfo gameInfo, 
			List<PlayerDesc> playersInHand,
			boolean didRaise) {
		this.card1 = card1;
		this.card2 = card2;
		numPlayers = gameInfo.getNumPlayers();
		numActivePlayers = gameInfo.getNumActivePlayers();
		toCall = gameInfo.getAmountToCall(seat);
		numOfRaises = gameInfo.getNumRaises();
		potSize = gameInfo.getTotalPotSize();
		bankRoll = gameInfo.getBankRoll(seat);
		sameSuit = isSameSuit();
		
		handRank = null;
		this.didRaise = didRaise;
		
		this.playersInHand = playersInHand;
		this.setBestHands();
	}
	
	
	public void setHandRange() {
		this.handRange = 0.0;
		for (int i = 0; i < BEST_HANDS_SIZE; i++) {
            if (this.bestHands[i][0] == this.bestHands[i][1]) { //pocket pairs are 1 / 221
                handRange += (1.0 / 221.0);
            } else { // not a pocket pair is
                handRange += (8.0 / 663.0);
            }
        }
	}
	
	public void setWinChance() {
		this.winChance = 0.0;
		for (int j = 0; j < BEST_HANDS_SIZE; j++) {
            if (this.bestHands[j][0] == this.bestHands[j][1]) { //pocket pair is less likely
                winChance += ((1.0 / 221.0) / handRange) * this.handMatcher(this.card1, this.card2, j);
            } else { // not pair is more likely
                winChance += ((8.0 / 663.0) / handRange) * this.handMatcher(this.card1, this.card2, j);
            }
        }
	}
	
	public void setProfitRaiseAndCall() {
		this.profitRaise = (this.toCall + this.potSize) * this.winChance - this.toCall;
		this.profitCall = ((this.potSize + this.toCall) * this.winChance) - this.toCall;
	}
	
	private double handMatcher(Card c1, Card c2, int index) {
        boolean usPair = c1.getRank() == c2.getRank();
        int rank1 = c1.getRank(), rank2 = c2.getRank();
        if (rank1 < rank2) {
            int temp = rank1;
            rank1 = rank2;
            rank2 = temp;
        }
        int them1 = this.bestHands[index][0], them2 = this.bestHands[index][1];
        if (them1 < them2) {
            int temp = them1;
            them1 = them2;
            them2 = temp;
        }
        boolean themPair = this.bestHands[index][0] == this.bestHands[index][1];

        if (usPair && themPair) {
            if (rank1 > them1) {
                return 0.8;
            } else if (rank1 < them1) {
                return 0.2;
            } else {
                return 0.5;
            }
        } else if (usPair) {
            if (rank1 > them1 && rank1 > them2) {
                return 0.85;
            } else if ((rank1 > them1 && rank1 < them2) || (rank1 < them1 && rank1 > them2)) {
                return 0.7;
            } else if (rank1 < them1 && rank1 < them2) {
                return 0.55;
            } else if ((rank1 == them1 && rank1 > them2) || (rank1 == them2 && rank1 > them1)) {
                return 0.9;
            } else if ((rank1 == them1 && rank1 < them2) || (rank1 == them2 && rank1 < them1)) {
                return 0.7;
            }
        } else if (themPair) {
            if (them1 > rank1 && them1 > rank2) {
                return 0.15;
            } else if ((them1 > rank1 && them1 < rank2) || (them1 < rank1 && them1 > rank2)) {
                return 0.3;
            } else if (them1 < rank1 && them1 < rank2) {
                return 0.45;
            } else if ((them1 == rank1 && them1 > rank2) || (them1 == rank2 && them1 > rank1)) {
                return 0.1;
            } else if ((them1 == rank1 && them1 < rank2) || (them1 == rank2 && them1 < rank1)) {
                return 0.3;
            }
        } else { // no one has a pair
            if (rank2 > them1) {
                return 0.65;
            } else if (them2 > rank1) {
                return 0.35;
            } else if (rank1 > them1 && them1 > rank2 && rank2 > them2) {
                return 0.65;
            } else if (them1 > rank1 && rank1 > them2 && them2 > rank2) {
                return 0.35;
            } else if (rank1 > them1 && them2 > rank2) {
                return 0.6;
            } else if (them1 > rank1 && rank2 > them2) {
                return 0.4;
            } else if (rank2 == them1) {
                return 0.75;
            } else if (them2 == rank1) {
                return 0.25;
            }
        }
        return 0.5; //should never happen
    }
	
	
	public Double getHandRank() {
		return handRank;
	}

	public void setHandRank(Hand h) {
		this.handRank = HandEvaluator.handRank(card1, card2, h, numPlayers);
	}

	public  bots.bongcloudbot.BongcloudAction  getPreliminaryAction() {
		return preliminaryAction;
	}

	public void setPreliminaryAction(BongcloudAction preliminaryAction) {
		this.preliminaryAction = preliminaryAction;
	}

	
	public boolean isDidRaise() {
		return didRaise;
	}


	public void setDidRaise(boolean didRaise) {
		this.didRaise = didRaise;
	}


	private boolean isSameSuit() {
		return card1.getSuit() == card2.getSuit();
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
		if (action.isRaise())
			this.didRaise = true;
		this.action = action;
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
	
	public void setSameSuit(boolean sameSuit) {
		this.sameSuit = sameSuit;
	}

	public Integer getIR() {
		return IR;
	}

	public void setIR(Integer iR) {
		IR = iR;
	}
	public List<PlayerDesc> getPlayersInHand() {
		return playersInHand;
	}

	public void setPlayersInHand(List<PlayerDesc> playersInHand) {
		this.playersInHand = playersInHand;
	}
	
	public double getProfitRaise() {
		return profitRaise;
	}


	public void setProfitRaise(double profitRaise) {
		this.profitRaise = profitRaise;
	}


	public double getProfitCall() {
		return profitCall;
	}


	public void setProfitCall(double profitCall) {
		this.profitCall = profitCall;
	}
	
	public double getHandRange() {
		return handRange;
	}

	public void setHandRange(double handRange) {
		this.handRange = handRange;
	}
	
	public double getWinChance() {
		return winChance;
	}


	public void setWinChance(double winChance) {
		this.winChance = winChance;
	}

	public void setBestHands() {
		 bestHands = new int[BEST_HANDS_SIZE][2];
	        //66, A6, K8, Q8, J8, T8
	        bestHands[0][0] = 12;
	        bestHands[0][1] = 12;//AA
	        bestHands[1][0] = 11;
	        bestHands[1][1] = 11;//KK
	        bestHands[2][0] = 12;
	        bestHands[2][1] = 11;//AK
	        bestHands[3][0] = 10;
	        bestHands[3][1] = 10;//QQ
	        bestHands[4][0] = 9;
	        bestHands[4][1] = 9;//JJ
	        bestHands[5][0] = 12;
	        bestHands[5][1] = 10;//AQ
	        bestHands[6][0] = 8;
	        bestHands[6][1] = 8;//TT
	        bestHands[7][0] = 12;
	        bestHands[7][1] = 9;//AJ
	        bestHands[8][0] = 7;
	        bestHands[8][1] = 7;//99
	        bestHands[9][0] = 11;
	        bestHands[9][1] = 10;//KQ
	        bestHands[10][0] = 6;
	        bestHands[10][1] = 6;//88
	        bestHands[11][0] = 12;
	        bestHands[11][1] = 8;//AT
	        bestHands[12][0] = 11;
	        bestHands[12][1] = 9;//KJ
	        bestHands[13][0] = 10;
	        bestHands[13][1] = 9;//QJ
	        bestHands[14][0] = 11;
	        bestHands[14][1] = 8;//KT
	        bestHands[15][0] = 12;
	        bestHands[15][1] = 7;//A9
	        bestHands[16][0] = 9;
	        bestHands[16][1] = 8;//JT
	        bestHands[17][0] = 10;
	        bestHands[17][1] = 8;//QT
	        bestHands[18][0] = 8;
	        bestHands[18][1] = 7;//T9
	        bestHands[19][0] = 11;
	        bestHands[19][1] = 7;//K9
	        bestHands[20][0] = 12;
	        bestHands[20][1] = 6;//A8
	        bestHands[21][0] = 5;
	        bestHands[21][1] = 5;//77
	        bestHands[22][0] = 10;
	        bestHands[22][1] = 7;//Q9
	        bestHands[23][0] = 9;
	        bestHands[23][1] = 7;//J9
	        bestHands[24][0] = 12;
	        bestHands[24][1] = 5;//A7
	}
	
}
