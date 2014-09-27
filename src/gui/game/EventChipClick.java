package gui.game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EventChipClick  implements EventHandler<MouseEvent>{
	
	public void handle(MouseEvent event) {
		ImageView img =(ImageView)event.getSource();
		switch(img.getId()){
			case "whiteChip":
				System.out.println("white chip clicked");
				break;
			case "redChip":
				break;
			case "greenChip":
				break;
			case "blackChip":
				break;
		}
}

}
