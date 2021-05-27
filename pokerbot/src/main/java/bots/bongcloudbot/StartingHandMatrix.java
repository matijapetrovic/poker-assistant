package bots.bongcloudbot;

import com.biotools.meerkat.Card;


public class StartingHandMatrix {

	
	 private BongcloudAction[][] matrix;

    public StartingHandMatrix() {
        matrix = new BongcloudAction[13][13];
    }

	
	public void setDefaultMatrix() {

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                matrix[i][j] = BongcloudAction.FOLD;
                matrix[j][i] = BongcloudAction.FOLD;
            }
        }

        matrix[8][10] = BongcloudAction.FOLD;
        matrix[10][8] = BongcloudAction.FOLD;
        matrix[8][11] = BongcloudAction.FOLD;
        matrix[11][8] = BongcloudAction.FOLD;

        matrix[0][0] = BongcloudAction.CALL; //pocket 2s - 6s
        matrix[1][1] = BongcloudAction.CALL;
        matrix[2][2] = BongcloudAction.CALL;
        matrix[3][3] = BongcloudAction.CALL;
        matrix[4][4] = BongcloudAction.CALL;

        matrix[8][9] = BongcloudAction.CALL; //10J suited
        matrix[9][10] = BongcloudAction.CALL;
        matrix[9][11] = BongcloudAction.CALL;

        matrix[9][8] = BongcloudAction.CALL; //J10 off suit
        matrix[10][9] = BongcloudAction.CALL;
        matrix[11][9] = BongcloudAction.CALL;

        matrix[5][5] = BongcloudAction.CALL; //pocket 7s-9s
        matrix[6][6] = BongcloudAction.CALL;
        matrix[7][7] = BongcloudAction.CALL;

        matrix[8][12] = BongcloudAction.CALL;
        matrix[9][12] = BongcloudAction.CALL;
        matrix[10][11] = BongcloudAction.CALL;

        matrix[12][8] = BongcloudAction.CALL;
        matrix[12][9] = BongcloudAction.CALL;
        matrix[11][10] = BongcloudAction.CALL;

        matrix[8][8] = BongcloudAction.RAISE; //10s, Js
        matrix[9][9] = BongcloudAction.RAISE;

        matrix[10][12] = BongcloudAction.RAISE; //AQ suited
        matrix[11][12] = BongcloudAction.RAISE; //AK suited

        matrix[12][10] = BongcloudAction.RAISE; //AQ
        matrix[12][11] = BongcloudAction.RAISE; //AK

        matrix[10][10] = BongcloudAction.RAISE; //QQ
        matrix[11][11] = BongcloudAction.RAISE; //KK

        matrix[12][12] = BongcloudAction.RAISE; //AA
    }
	
	public BongcloudAction getFlopAction(Card c1, Card c2) {
        if (c1.getRank() <= c2.getRank()) {
            if (c1.getSuit() == c2.getSuit()) {
                return matrix[c1.getRank()][c2.getRank()];
            } else {
                return matrix[c2.getRank()][c1.getRank()];
            }
        } else {
            if (c1.getSuit() == c2.getSuit()) {
                return matrix[c2.getRank()][c1.getRank()];
            } else {
                return matrix[c1.getRank()][c2.getRank()];
            }
        }
    }

    public void setFlopAction(Card c1, Card c2, BongcloudAction action) {
        if (c1.getRank() <= c2.getRank()) {
            if (c1.getSuit() == c2.getSuit()) {
                matrix[c1.getRank()][c2.getRank()] = action;
            } else {
                matrix[c2.getRank()][c1.getRank()] = action;
            }
        } else {
            if (c1.getSuit() == c2.getSuit()) {
                matrix[c2.getRank()][c1.getRank()] = action;
            } else {
                matrix[c1.getRank()][c2.getRank()] = action;
            }
        }
    }
}
