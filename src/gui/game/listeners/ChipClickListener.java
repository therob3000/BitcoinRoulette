package gui.game.listeners;

import gui.game.GameCtrl;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ChipClickListener  implements EventHandler<MouseEvent>{
	private GameCtrl gameCtrl;
	
	public ChipClickListener(GameCtrl gameCtrl){
		this.gameCtrl = gameCtrl;
	}
	
	public void handle(MouseEvent event) {
		ImageView img =(ImageView)event.getSource();
		switch(img.getId()){
			case "whiteChip":
				gameCtrl.currChip = 0;
				break;
			case "redChip":
				gameCtrl.currChip = 1;
				break;
			case "greenChip":
				gameCtrl.currChip = 2;
				break;
			case "blackChip":
				gameCtrl.currChip = 3;
				break;
		}
}

}
