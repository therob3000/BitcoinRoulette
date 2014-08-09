package core;

import java.util.Arrays;
import java.util.HashSet;

public class ColorBet extends Bet {
	private boolean red;
	private HashSet<Integer> redNumbers = new HashSet<Integer>
		(Arrays.asList(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36));
	
	public ColorBet(boolean red, double amount, int payout) {
		super(amount, payout);
		this.red = red;
	}

	@Override
	public boolean cameTrue(int result) {
		return red == redNumbers.contains(result);
	}
}
