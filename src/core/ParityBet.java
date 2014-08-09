package core;

public class ParityBet extends Bet {
	private boolean even;
	
	public ParityBet(boolean even, double amount, int payout) {
		super(amount, payout);
		this.even = even;
	}

	@Override
	public boolean cameTrue(int result) {
		return even == (result % 2 == 0);
	}
}
