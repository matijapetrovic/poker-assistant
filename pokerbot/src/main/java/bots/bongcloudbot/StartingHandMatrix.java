package bots.bongcloudbot;

import bots.smarterbot.Action;

public class StartingHandMatrix {

	
	 private Action[][] matrix;

    public StartingHandMatrix() {
        matrix = new Action[13][13];
    }

	
	public void setDefaultMatrix() {

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                matrix[i][j] = Action.FOLD;
                matrix[j][i] = Action.FOLD;
            }
        }

        matrix[8][10] = Action.FOLD;
        matrix[10][8] = Action.FOLD;
        matrix[8][11] = Action.FOLD;
        matrix[11][8] = Action.FOLD;

        matrix[0][0] = Action.CALL; //pocket 2s - 6s
        matrix[1][1] = Action.CALL;
        matrix[2][2] = Action.CALL;
        matrix[3][3] = Action.CALL;
        matrix[4][4] = Action.CALL;

        matrix[8][9] = Action.CALL; //10J suited
        matrix[9][10] = Action.CALL;
        matrix[9][11] = Action.CALL;

        matrix[9][8] = Action.CALL; //J10 off suit
        matrix[10][9] = Action.CALL;
        matrix[11][9] = Action.CALL;

        matrix[5][5] = Action.CALL; //pocket 7s-9s
        matrix[6][6] = Action.CALL;
        matrix[7][7] = Action.CALL;

        matrix[8][12] = Action.CALL;
        matrix[9][12] = Action.CALL;
        matrix[10][11] = Action.CALL;

        matrix[12][8] = Action.CALL;
        matrix[12][9] = Action.CALL;
        matrix[11][10] = Action.CALL;

        matrix[8][8] = Action.RAISE; //10s, Js
        matrix[9][9] = Action.RAISE;

        matrix[10][12] = Action.RAISE; //AQ suited
        matrix[11][12] = Action.RAISE; //AK suited

        matrix[12][10] = Action.RAISE; //AQ
        matrix[12][11] = Action.RAISE; //AK

        matrix[10][10] = Action.RAISE; //QQ
        matrix[11][11] = Action.RAISE; //KK

        matrix[12][12] = Action.RAISE; //AA
    }
}
