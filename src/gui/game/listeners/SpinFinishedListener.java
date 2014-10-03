package gui.game.listeners;

import core.Bet;
import gui.game.GameCtrl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SpinFinishedListener implements EventHandler<ActionEvent> {
	private int result;
	private GameCtrl gameCtrl;

	public SpinFinishedListener(GameCtrl gameCtrl, int result) {
		this.gameCtrl = gameCtrl;
		this.result = result;
	}

	@Override
	public void handle(ActionEvent event) {
		
		for(Bet b: gameCtrl.bets){
				System.out.println((b.cameTrue() ? "True: " : "False: ") + b.winning);
		}
		
	}

}
