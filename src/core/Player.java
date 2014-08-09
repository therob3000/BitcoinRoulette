package core;

import java.util.Stack;

public class Player {
	private Account account;
	private Stack<Bet> bets;
	
	public Player(Account account){
		this.account = account;
		bets = new Stack<Bet>();
	}

	public Account getAccount() {
		return account;
	}

	public Stack<Bet> getBets() {
		return bets;
	}
}
