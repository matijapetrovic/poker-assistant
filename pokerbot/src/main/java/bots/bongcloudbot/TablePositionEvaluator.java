package bots.bongcloudbot;

import com.biotools.meerkat.Holdem;

public class TablePositionEvaluator {
	private int seat;
	private int numPlayers;
	
	public TablePositionEvaluator(int seat, int numPlayers) {
		this.seat = seat;
		this.numPlayers = numPlayers;
	}
	
	public static TablePosition evaluate(int seat, int numPlayers) {
		if (numPlayers == 2) {
			return evaluate2Seats(seat);
		}
		else if (numPlayers == 3) {
			return evaluate3Seats(seat);
		}
		return evaluate4PlusSeats( seat, numPlayers);
	}
	
	
	
	private static TablePosition evaluate2Seats(int seat) {
		switch (seat) {
	        case 0: {
	        	return TablePosition.BLINDS;
	        }
	        case 1: {
	        	return TablePosition.EARLY;
	        }
	        default: {
	            throw new Error();
	        }
	     }
	}
	
	private static TablePosition evaluate3Seats(int seat) {
		switch (seat) {
	        case 0: {
	        	return TablePosition.BLINDS;
	        }
	        case 1: {
	        	return TablePosition.EARLY;
	        }
	        case 2: {
	        	return TablePosition.LATE;
	        }
	        default: {
	            throw new Error();
	        }
		}
	}
	
	private static TablePosition evaluate4PlusSeats(int seat, int numPlayers) {
		int quarter = numPlayers / 4;
		int q = -1;
		for (int i = 1; i < 4; i++) {
			if (quarter*i > seat) {
				q = i;
				break;
			}
		}
		return TablePosition(q-1);
	}

	private static TablePosition TablePosition(int p) {
		switch (p) {
        case 0: {
        	return TablePosition.BLINDS;
        }
        case 1: {
        	return TablePosition.EARLY;
        }
        case 2: {
        	return TablePosition.LATE;
        }
        case 3: {
        	return TablePosition.LATE;
        }
        default: {
            throw new Error();
        }
     }
	}

	private static enum TablePosition {
		BLINDS, EARLY, MIDDLE, LATE
	}
}
