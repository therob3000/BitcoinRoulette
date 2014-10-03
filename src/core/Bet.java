package core;

import java.util.HashSet;

public class Bet {
	public Player player;
	private int payout;
	private double amount;
	public int resultOfSpin = -1;
	public HashSet<Integer> winning = new HashSet<Integer>();

	public Bet(double amount, int payout, HashSet<Integer> winning){
		this.payout = payout;
		this.amount = amount;
		this.winning = winning;
	}
	
	public int getPayout(){
		return payout;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public boolean cameTrue(){
		return winning.contains(resultOfSpin);
	}
	
}

