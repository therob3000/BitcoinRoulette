package core;

public abstract class Bet {
	private int payout;
	private double amount;

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
	
	public abstract boolean cameTrue(int result);
	
}
