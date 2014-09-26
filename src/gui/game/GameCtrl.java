package gui.game;


import java.util.HashMap;

import gui.MainGui;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import core.Core;

public class GameCtrl {
	private Core core;
	private MainGui mainGui;
	public Coord[] numberToCoord = new Coord[37];
	public HashMap<Coord, Coord[]> coordToSelection = new HashMap<Coord, Coord[]>();
	@FXML public GridPane grid;
	@FXML public ImageView wheel;
	@FXML public Circle ball;
	
	public GameCtrl(Core core, MainGui mainGui){
		this.core = core;
		this.mainGui = mainGui;
		
		/* 1st row */
		int num = 3;
		for(int i=1; i <= 23; i+=2){
			numberToCoord[num] = new Coord(1,i);
			num += 3;
		}
		
		/* 2nd row */
		num = 2;
		for(int i=1; i <= 23; i+=2){
			numberToCoord[num] = new Coord(3,i);
			num += 3;
		}
		
		/* 3rd row */
		num = 1;
		for(int i=1; i <= 23; i+=2){
			numberToCoord[num] = new Coord(5,i);
			num += 3;
		}
		
		coordToSelection.put(new Coord(1,1), new Coord[]{numberToCoord[3]});
		coordToSelection.put(new Coord(2,8), new Coord[]{numberToCoord[11],numberToCoord[12],numberToCoord[14],numberToCoord[15]});
	}
	
	public void initialize(){
		
		for(Node n : grid.getChildren()){
			n.setOnMouseEntered(new EventNumberEnter(this));
			n.setOnMouseExited(new EventNumberExit(this));
		}
	}
	
	public Node getPaneFromCoord(int row, int col){
		for(Node n : grid.getChildren()){
			if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == col)
				return n;
		}
		return null;
	}

	 private Path createCirclePath(double centerX, double centerY, double radiusX, double radiusY) {
	        ArcTo arcTo = new ArcTo();
	        arcTo.setX(centerX - radiusX + 1);
	        arcTo.setY(centerY - radiusY);
	        arcTo.setSweepFlag(false);
	        arcTo.setLargeArcFlag(true);
	        arcTo.setRadiusX(radiusX);
	        arcTo.setRadiusY(radiusY);
	        
	        Path path = new Path();
		    path.getElements().add(new MoveTo(centerX - radiusX, centerY - radiusY));
		    path.getElements().add(arcTo);
	        path.getElements().add(new ClosePath());
	        
	        return path;
	    }

	 public void spin(ActionEvent e){
		 int cycles = 20;
		 PathTransition spin = new PathTransition(Duration.millis(10000).divide(cycles), createCirclePath(115, 115, 110, 110), ball);
		 spin.setCycleCount(cycles);
		 spin.setInterpolator(Interpolator.LINEAR);
		 spin.play();
		 
		 int deg = 360*5 + (int)(Math.random() * 360);	//Between 5 and 6 spins
		 RotateTransition rt = new RotateTransition(Duration.millis(10000), wheel);
		 rt.setByAngle(deg);
		 rt.play();
	}
	 

	public void showAccountScene(){
		mainGui.getStage().setScene(mainGui.getAccountScene());
	}
}
