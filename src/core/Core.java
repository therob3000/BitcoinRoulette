package core;

import java.net.MalformedURLException;
import java.util.ArrayList;

import com.azazar.bitcoin.jsonrpcclient.BitcoinException;
import com.azazar.bitcoin.jsonrpcclient.BitcoinJSONRPCClient;

public class Core {
	
	private BitcoinJSONRPCClient bitcoin;
	private ArrayList<Player> players;
	private BitcoinListener bitcoinListener;

	public Core(){
		
//		Auth.authenticate();
//		
//		try {
//			bitcoin = new BitcoinJSONRPCClient("http://localhost:"+ 18332);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			return;
//		}
//		
//		
//		bitcoinListener = new BitcoinListener(bitcoin);
//		new Thread(bitcoinListener).start();
		players = new ArrayList<Player>();
	}
	
	public Player getCurrentPlayer(){
		return players.get(0);
	}

	public BitcoinJSONRPCClient getBitcoin(){
		return bitcoin;
	}

	public void addPlayer() throws BitcoinException {
//		String address = bitcoin.getNewAddress("roulette");
		String address = "ASdfasdf";
		Account account = new Account(address);
		players.add(new Player(account));
//		bitcoinListener.addListener(address,account);
	}
}
