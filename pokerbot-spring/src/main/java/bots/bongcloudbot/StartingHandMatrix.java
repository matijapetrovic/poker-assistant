package bots.bongcloudbot;

import com.biotools.meerkat.Card;

public class StartingHandMatrix {

	
	 private BongcloudAction[][] matrix;

    public StartingHandMatrix() {
        matrix = new BongcloudAction[13][13];
    }

	
    /*Note that ascending order card represents suited. Not ascending order is offsuit*/
    public void setTightMatrix() {

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

        matrix[0][0] = BongcloudAction.LIMP; //pocket 2s - 6s
        matrix[1][1] = BongcloudAction.LIMP;
        matrix[2][2] = BongcloudAction.LIMP;
        matrix[3][3] = BongcloudAction.LIMP;
        matrix[4][4] = BongcloudAction.LIMP;

        matrix[8][9] = BongcloudAction.LIMP; //10J suited
        matrix[9][10] = BongcloudAction.LIMP;
        matrix[9][11] = BongcloudAction.LIMP;

        matrix[9][8] = BongcloudAction.LIMP; //J10 off suit
        matrix[10][9] = BongcloudAction.LIMP;
        matrix[11][9] = BongcloudAction.LIMP;

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

        matrix[10][10] = BongcloudAction.RERAISE; //QQ
        matrix[11][11] = BongcloudAction.RERAISE; //KK

        matrix[12][12] = BongcloudAction.PREMIUM; //AA
    }

    /*Note that ascending order card represents suited. Not ascending order is offsuit*/
    public void setNeutralMatrix() {
      
      /* Initialize to LIMP, since this table has lots of limps */
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                matrix[i][j] = BongcloudAction.LIMP;
                matrix[j][i] = BongcloudAction.LIMP;
            }
        }

        matrix[0][0] = BongcloudAction.CALL;      //pocket 2s - 7s
        matrix[1][1] = BongcloudAction.CALL;
        matrix[2][2] = BongcloudAction.CALL;
        matrix[3][3] = BongcloudAction.CALL;
        matrix[4][4] = BongcloudAction.CALL;
        matrix[5][5] = BongcloudAction.CALL;

        matrix[6][6] = BongcloudAction.RAISE;     //pocket 8s - 9s
        matrix[7][7] = BongcloudAction.RAISE;

        matrix[8][8] = BongcloudAction.RERAISE;   //pocket 10s - Qs
        matrix[9][9] = BongcloudAction.RERAISE;
        matrix[10][10] = BongcloudAction.RERAISE;

        matrix[11][11] = BongcloudAction.PREMIUM; //pocket Ks - As
        matrix[12][12] = BongcloudAction.PREMIUM;

        matrix[1][0] = BongcloudAction.FOLD;      //fold 2 + (K - 3) unsuited
        matrix[2][0] = BongcloudAction.FOLD;
        matrix[3][0] = BongcloudAction.FOLD;
        matrix[4][0] = BongcloudAction.FOLD;
        matrix[5][0] = BongcloudAction.FOLD;
        matrix[6][0] = BongcloudAction.FOLD;
        matrix[7][0] = BongcloudAction.FOLD;
        matrix[8][0] = BongcloudAction.FOLD;
        matrix[9][0] = BongcloudAction.FOLD;
        matrix[10][0] = BongcloudAction.FOLD;
        matrix[11][0] = BongcloudAction.FOLD;

        matrix[4][1] = BongcloudAction.FOLD;      //fold 3 + (6 - K) unsuited
        matrix[5][1] = BongcloudAction.FOLD;
        matrix[6][1] = BongcloudAction.FOLD;
        matrix[7][1] = BongcloudAction.FOLD;
        matrix[8][1] = BongcloudAction.FOLD;
        matrix[9][1] = BongcloudAction.FOLD;
        matrix[10][1] = BongcloudAction.FOLD;
        matrix[11][1] = BongcloudAction.FOLD;

        matrix[5][2] = BongcloudAction.FOLD;      //fold 4 + (7 - K) unsuited
        matrix[6][2] = BongcloudAction.FOLD;
        matrix[7][2] = BongcloudAction.FOLD;
        matrix[8][2] = BongcloudAction.FOLD;
        matrix[9][2] = BongcloudAction.FOLD;
        matrix[10][2] = BongcloudAction.FOLD;
        matrix[11][2] = BongcloudAction.FOLD;

        matrix[6][3] = BongcloudAction.FOLD;      //fold 5 + (8 - K) unsuited
        matrix[7][3] = BongcloudAction.FOLD;
        matrix[8][3] = BongcloudAction.FOLD;
        matrix[9][3] = BongcloudAction.FOLD;
        matrix[10][3] = BongcloudAction.FOLD;
        matrix[11][3] = BongcloudAction.FOLD;

        matrix[7][4] = BongcloudAction.FOLD;      //fold 6 + (9 - K) unsuited
        matrix[8][4] = BongcloudAction.FOLD;
        matrix[9][4] = BongcloudAction.FOLD;
        matrix[10][4] = BongcloudAction.FOLD;
        matrix[11][4] = BongcloudAction.FOLD;

        matrix[8][5] = BongcloudAction.FOLD;      //fold 7 + (10 - K) unsuited
        matrix[9][5] = BongcloudAction.FOLD;
        matrix[10][5] = BongcloudAction.FOLD;
        matrix[11][5] = BongcloudAction.FOLD;
      
      /* Calls */
        matrix[1][2] = BongcloudAction.CALL;      // 2 3 suited
        matrix[2][3] = BongcloudAction.CALL;      // 4 5 suited
        matrix[2][4] = BongcloudAction.CALL;      // 4 6 suited
        matrix[2][5] = BongcloudAction.CALL;      // 4 7 suited
        matrix[3][6] = BongcloudAction.CALL;      // 5 8 suited
        matrix[4][7] = BongcloudAction.CALL;      // 6 9 suited
        matrix[5][8] = BongcloudAction.CALL;      // 7 T suited
        matrix[5][9] = BongcloudAction.CALL;      // 7 J suited
        matrix[6][10] = BongcloudAction.CALL;     // 8 Q suited
        matrix[6][11] = BongcloudAction.CALL;     // 8 K suited
        matrix[7][11] = BongcloudAction.CALL;     // 9 K suited
        matrix[7][12] = BongcloudAction.CALL;     // 9 A suited
        matrix[7][6] = BongcloudAction.CALL;      // 9 8 unsuited
        matrix[9][7] = BongcloudAction.CALL;      // J 9 unsuited
      
      /* Raises */
        matrix[3][4] = BongcloudAction.RAISE;     // 5 6 suited
        matrix[3][5] = BongcloudAction.RAISE;     // 5 7 suited
        matrix[4][5] = BongcloudAction.RAISE;     // 6 7 suited
        matrix[4][6] = BongcloudAction.RAISE;     // 6 8 suited
        matrix[5][6] = BongcloudAction.RAISE;     // 7 8 suited
        matrix[5][7] = BongcloudAction.RAISE;     // 7 9 suited
        matrix[6][7] = BongcloudAction.RAISE;     // 8 9 suited
        matrix[6][8] = BongcloudAction.RAISE;     // 8 T suited
        matrix[6][9] = BongcloudAction.RAISE;     // 8 J suited
        matrix[7][9] = BongcloudAction.RAISE;     // 9 J suited
        matrix[7][10] = BongcloudAction.RAISE;    // 9 Q suited
        matrix[8][10] = BongcloudAction.RAISE;    // T Q suited
        matrix[8][11] = BongcloudAction.RAISE;    // T K suited
        matrix[9][11] = BongcloudAction.RAISE;    // J K suited
        matrix[9][12] = BongcloudAction.RAISE;    // J A suited
        matrix[8][7] = BongcloudAction.RAISE;     // T 9 unsuited
        matrix[9][8] = BongcloudAction.RAISE;     // J T unsuited
        matrix[10][8] = BongcloudAction.RAISE;    // Q T unsuited
        matrix[11][8] = BongcloudAction.RAISE;    // K T unsuited
        matrix[12][8] = BongcloudAction.RAISE;    // A T unsuited
        matrix[10][9] = BongcloudAction.RAISE;    // Q J unsuited
        matrix[11][9] = BongcloudAction.RAISE;    // K J unsuited
        matrix[12][9] = BongcloudAction.RAISE;    // A J unsuited
      
      /* Re-Raises */
        matrix[7][8] = BongcloudAction.RERAISE;   // 9 T suited
        matrix[8][9] = BongcloudAction.RERAISE;   // T J suited
        matrix[9][10] = BongcloudAction.RERAISE;  // J Q suited
        matrix[10][11] = BongcloudAction.RERAISE; // Q K suited
        matrix[9][12] = BongcloudAction.RERAISE;  // J A suited
        matrix[10][12] = BongcloudAction.RERAISE; // Q A suited
        matrix[11][10] = BongcloudAction.RERAISE; // K Q unsuited
        matrix[12][10] = BongcloudAction.RERAISE; // A Q unsuited
        matrix[12][11] = BongcloudAction.RERAISE; // A K unsuited
      
      /* Premium */
        matrix[11][12] = BongcloudAction.PREMIUM; // A K suited

    }

    public void setLooseMatrix() {
      /* Initialize to LIMP, since this table has lots of limps */
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                matrix[i][j] = BongcloudAction.FOLD;
                matrix[j][i] = BongcloudAction.FOLD;
            }
        }

        matrix[0][0] = BongcloudAction.RAISE;     //pocket 2s - 7s
        matrix[1][1] = BongcloudAction.RAISE;
        matrix[2][2] = BongcloudAction.RAISE;
        matrix[3][3] = BongcloudAction.RAISE;
        matrix[4][4] = BongcloudAction.RAISE;
        matrix[5][5] = BongcloudAction.RAISE;

        matrix[6][6] = BongcloudAction.RERAISE;   //pocket 8s - Qs
        matrix[7][7] = BongcloudAction.RERAISE;
        matrix[8][8] = BongcloudAction.RERAISE;
        matrix[9][9] = BongcloudAction.RERAISE;
        matrix[10][10] = BongcloudAction.RERAISE;

        matrix[11][11] = BongcloudAction.PREMIUM; //pocket Ks - As
        matrix[12][12] = BongcloudAction.PREMIUM;
      
      /* Limps */
        matrix[0][1] = BongcloudAction.LIMP;      // 2 4 suited
        matrix[0][2] = BongcloudAction.LIMP;      // 2 4 suited
        matrix[1][3] = BongcloudAction.LIMP;      // 3 5 suited
        matrix[1][4] = BongcloudAction.LIMP;      // 3 6 suited
        matrix[2][5] = BongcloudAction.LIMP;      // 4 7 suited
        matrix[3][5] = BongcloudAction.LIMP;      // 5 7 suited
        matrix[3][6] = BongcloudAction.LIMP;      // 5 8 suited
        matrix[4][7] = BongcloudAction.LIMP;      // 6 9 suited
        matrix[4][12] = BongcloudAction.LIMP;     // 6 A suited
        matrix[5][12] = BongcloudAction.LIMP;     // 7 A suited
        matrix[6][12] = BongcloudAction.LIMP;     // 8 A suited
        matrix[5][8] = BongcloudAction.LIMP;      // 7 T suited
        matrix[6][9] = BongcloudAction.LIMP;      // 8 J suited
        matrix[8][6] = BongcloudAction.LIMP;      // T 8 unsuited
        matrix[9][6] = BongcloudAction.LIMP;      // J 8 unsuited
        matrix[10][6] = BongcloudAction.LIMP;     // Q 8 unsuited
        matrix[11][6] = BongcloudAction.LIMP;     // K 8 unsuited
        matrix[10][7] = BongcloudAction.LIMP;     // Q 9 unsuited
        matrix[11][7] = BongcloudAction.LIMP;     // K 9 unsuited
        matrix[12][0] = BongcloudAction.LIMP;     // A 2 unsuited
        matrix[12][1] = BongcloudAction.LIMP;     // A 3 unsuited
        matrix[12][2] = BongcloudAction.LIMP;     // A 4 unsuited
        matrix[12][3] = BongcloudAction.LIMP;     // A 5 unsuited
      
      
      /* Calls */
        matrix[0][12] = BongcloudAction.CALL;      // 2 A suited
        matrix[2][12] = BongcloudAction.CALL;      // 3 A suited
        matrix[3][12] = BongcloudAction.CALL;      // 4 A suited
        matrix[4][12] = BongcloudAction.CALL;      // 5 A suited

        matrix[1][2] = BongcloudAction.CALL;      // 2 3 suited
        matrix[2][3] = BongcloudAction.CALL;      // 4 5 suited
        matrix[2][4] = BongcloudAction.CALL;      // 4 6 suited
        matrix[3][4] = BongcloudAction.CALL;      // 5 6 suited
        matrix[4][5] = BongcloudAction.CALL;      // 6 7 suited
        matrix[5][6] = BongcloudAction.CALL;      // 7 8 suited
        matrix[5][9] = BongcloudAction.CALL;      // 7 J suited
        matrix[6][7] = BongcloudAction.CALL;      // 8 9 suited
        matrix[6][10] = BongcloudAction.CALL;     // 8 Q suited
        matrix[6][11] = BongcloudAction.CALL;     // 8 K suited
        matrix[7][10] = BongcloudAction.CALL;     // 9 Q suited
        matrix[7][11] = BongcloudAction.CALL;     // 9 K suited
        matrix[7][12] = BongcloudAction.CALL;     // 9 A suited
        matrix[7][6] = BongcloudAction.CALL;      // 9 8 unsuited
        matrix[9][7] = BongcloudAction.CALL;      // J 9 unsuited
        matrix[12][6] = BongcloudAction.CALL;      // A 8 unsuited
        matrix[12][7] = BongcloudAction.CALL;      // A 9 unsuited
      
      /* Raises */
        matrix[3][5] = BongcloudAction.RAISE;     // 5 7 suited
        matrix[4][6] = BongcloudAction.RAISE;     // 6 8 suited
        matrix[5][7] = BongcloudAction.RAISE;     // 7 9 suited
        matrix[6][8] = BongcloudAction.RAISE;     // 8 T suited
        matrix[7][8] = BongcloudAction.RAISE;     // 9 T suited
        matrix[7][9] = BongcloudAction.RAISE;     // 9 J suited
        matrix[8][10] = BongcloudAction.RAISE;    // T Q suited
        matrix[8][11] = BongcloudAction.RAISE;    // T K suited
        matrix[9][11] = BongcloudAction.RAISE;    // J K suited
        matrix[9][12] = BongcloudAction.RAISE;    // J A suited
        matrix[8][7] = BongcloudAction.RAISE;     // T 9 unsuited
        matrix[9][8] = BongcloudAction.RAISE;     // J T unsuited
        matrix[10][8] = BongcloudAction.RAISE;    // Q T unsuited
        matrix[11][8] = BongcloudAction.RAISE;    // K T unsuited
        matrix[12][8] = BongcloudAction.RAISE;    // A T unsuited
        matrix[10][9] = BongcloudAction.RAISE;    // Q J unsuited
        matrix[11][9] = BongcloudAction.RAISE;    // K J unsuited
        matrix[12][9] = BongcloudAction.RAISE;    // A J unsuited
      
      /* Re-Raises */
        matrix[8][9] = BongcloudAction.RERAISE;   // T J suited
        matrix[9][10] = BongcloudAction.RERAISE;  // J Q suited
        matrix[9][11] = BongcloudAction.RERAISE;  // J K suited
        matrix[9][11] = BongcloudAction.RERAISE;  // A Q unsuited
        matrix[9][12] = BongcloudAction.RERAISE;  // J A suited
        matrix[11][10] = BongcloudAction.RERAISE; // K Q unsuited
        matrix[12][10] = BongcloudAction.RERAISE; // A Q unsuited
      
      /* Premium */
        matrix[11][12] = BongcloudAction.PREMIUM; // K A suited
        matrix[12][11] = BongcloudAction.PREMIUM; // A K unsuited
        matrix[10][12] = BongcloudAction.PREMIUM; // Q A suited
    }

	
	public BongcloudAction getFlopAction(Card c1, Card c2) {
		BongcloudAction action;
        if (c1.getRank() <= c2.getRank()) {
            if (c1.getSuit() == c2.getSuit()) {
            	action =  matrix[c1.getRank()][c2.getRank()];
            } else {
                action = matrix[c2.getRank()][c1.getRank()];
            }
        } else {
            if (c1.getSuit() == c2.getSuit()) {
            	action =  matrix[c2.getRank()][c1.getRank()];
            } else {
            	action =  matrix[c1.getRank()][c2.getRank()];
            }
        }
        
        if (action == BongcloudAction.LIMP)
        	return BongcloudAction.CALL;
        if (action == BongcloudAction.RERAISE)
        	return BongcloudAction.RAISE;
        return action;
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
