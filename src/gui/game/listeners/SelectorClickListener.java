package gui.game.listeners;

import java.util.Arrays;

import gui.game.Coord;
import gui.game.GameCtrl;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import core.Bet;

public class SelectorClickListener implements EventHandler<MouseEvent>{

	private GameCtrl gameCtrl;
	private Coord[] offBoard = new Coord[]{	new Coord(7,0),
	                                     	new Coord(8,0),
	                                     	new Coord(7,24),
	                                     	new Coord(8,24),
										};
	
	public SelectorClickListener(GameCtrl gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
	
	@Override
	public void handle(MouseEvent event) {
		Pane p = (Pane)event.getSource();
		int row = GridPane.getRowIndex(p);
		int col = GridPane.getColumnIndex(p);
		Coord coord = new Coord(row, col);
		
		if(Arrays.asList(offBoard).contains(coord))
			return;
		
		String url = "";
		switch(gameCtrl.currChip){
			case -1:	/* No chip selected */
				return;
			case 0:
				url = "gui/images/white_chip.png";
				break;
			case 1:
				url = "gui/images/red_chip.png";
				break;
			case 2:
				url = "gui/images/green_chip.png";
				break;
			case 3:
				url = "gui/images/black_chip.png";
				break;
		}
		
		ImageView chip = new ImageView(new Image(url, 40.0, 40.0, true, false));
		gameCtrl.boardPane.getChildren().add(chip);
		chip.setX(event.getSceneX()-372);
		chip.setY(event.getSceneY()-22);
		
		Coord[] selection = gameCtrl.coordToSelection.get(coord);
		if(selection != null){
			gameCtrl.getBets().add(new Bet(12.34, 4));
		}
	}
}
