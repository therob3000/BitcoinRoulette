package gui.game.listeners;

import java.util.Arrays;
import java.util.List;

import gui.game.Coord;
import gui.game.GameCtrl;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
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
				url = "gui/images/blue_chip.png";
				break;
			case 3:
				url = "gui/images/green_chip.png";
				break;
			case 4:
				url = "gui/images/black_chip.png";
				break;
		}
		
		ImageView chip = new ImageView(new Image(url, 40.0, 40.0, true, false));
		chip.setMouseTransparent(true);
		chip.setX(event.getSceneX()-372);
		chip.setY(event.getSceneY()-22);
		gameCtrl.chipsOnBoard.getChildren().add(chip);
		gameCtrl.floatingChip.setVisible(false);
		
		Coord[] selection = gameCtrl.coordToSelection.get(coord);
		if(selection != null){
			
			double betAmount = gameCtrl.chipAmounts[gameCtrl.currChip];
			gameCtrl.currChip = -1;
			int payout = (36/selection.length) - 1;
			
			System.out.printf("Bet: %f Payout: %d to 1\n", betAmount, payout);
			
			List<Coord> winning = Arrays.asList(gameCtrl.coordToSelection.get(coord));
			gameCtrl.addBet(betAmount, payout, winning);
		}
		
		for(ImageView i : gameCtrl.chips){
			i.setEffect(new ColorAdjust());
		}
		
	}
}
