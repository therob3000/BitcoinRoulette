package gui.game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EventSelectorEnter implements EventHandler<Event>{

	private GameCtrl gameCtrl;
	
	public EventSelectorEnter(GameCtrl gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	@Override
	public void handle(Event event) {
		Pane p = (Pane)event.getSource();
		int row = GridPane.getRowIndex(p);
		int col = GridPane.getColumnIndex(p);
		
		Coord[] selection = gameCtrl.coordToSelection.get(new Coord(row, col));
		if(selection != null){
			for(Coord c : selection){
				gameCtrl.getPaneFromCoord(c.row, c.col).setOpacity(0.5);
			}
		}
	}
}
