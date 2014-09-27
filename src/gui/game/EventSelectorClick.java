package gui.game;

import core.Bet;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EventSelectorClick implements EventHandler<Event>{

	private GameCtrl gameCtrl;
	
	public EventSelectorClick(GameCtrl gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	@Override
	public void handle(Event event) {
		Pane p = (Pane)event.getSource();
		int row = GridPane.getRowIndex(p);
		int col = GridPane.getColumnIndex(p);
		
		Coord[] selection = gameCtrl.coordToSelection.get(new Coord(row, col));
		if(selection != null){
			gameCtrl.getBets().add(new Bet(12.34, 4));
		}
	}
}
