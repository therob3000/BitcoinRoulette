package core;

import java.util.HashSet;

public class NumberBet extends Bet{
	private HashSet<Integer> numbers;
	
	public NumberBet(HashSet<Integer> numbers, double amount, int payout){
		super(amount, payout);
		this.numbers = numbers;
	}

	@Override
	public boolean cameTrue(int result) {
		return numbers.contains(result);
	}
}
