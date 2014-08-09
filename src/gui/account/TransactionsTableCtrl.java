package gui.account;

import com.azazar.bitcoin.jsonrpcclient.Bitcoin.Transaction;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import core.Account;
import core.Core;


public class TransactionsTableCtrl {
	private Core core;
	@FXML private TableView<TransactionRow> tableView;
	
	public TransactionsTableCtrl(Core core){
		this.core = core;
	}

	public void refresh(ActionEvent e){
		Account acct = core.getCurrentPlayer().getAccount();
		ObservableList<TransactionRow> rows = tableView.getItems();
		rows.clear();
		
		for(Transaction t : acct.getTransactions()){
			rows.add(new TransactionRow(t.address(), t.amount(), 
					t.confirmations() >= 6 ? "Confirmed" : t.confirmations() + " confirmations"));
		}
		
		
	}
}
