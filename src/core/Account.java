package core;

import java.util.ArrayList;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin.Transaction;
import com.azazar.bitcoin.jsonrpcclient.BitcoinPaymentListener;


public class Account implements BitcoinPaymentListener{
	private double balance; 
	private String fundAddress;
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	
	public Account(String fundAddress){
		balance = 1.337;
		this.fundAddress = fundAddress;
		System.out.println(fundAddress);
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getAddress(){
		return fundAddress;
	}
	
	public String prettyPrintBalance(){
		return String.format("%1$,.4f", balance);
	}

	public void block(String blockHash) {
		return; 	/* Dont care about new blocks */
	}
	
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	 /* New transaction has arrived	or added confirmations */
	public void transaction(Transaction transaction) {
		for(Transaction tx : transactions)
			if(tx.txId().equals(transaction.txId())){	/* Adding confirmations */
				transactions.remove(tx);
				break;
			}
		
		transactions.add(transaction);
		
		double newBalance = 0.0;
		for(Transaction tx : transactions){
			if(tx.confirmations() >= 6)
				balance += tx.amount();
		}
		
		this.balance = newBalance;
	}
}
