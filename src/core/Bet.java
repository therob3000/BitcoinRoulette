package core;

import java.util.HashSet;

public class Bet {
	private int payout;
	private double amount;
	private HashSet<Integer> numbers = new HashSet<Integer>();

	public Bet(double amount, int payout){
		this.payout = payout;
		this.amount = amount;
	}
	
	public int getPayout(){
		return payout;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public boolean cameTrue(int result) {
		return numbers.contains(result);
	}
	
}

