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
		
	}
	
	
	public void initialize(){
		
		/* Get coordinate for each number */
		int num = 0;
		int row = 5;
		int col = 1;
		while(++num <= 36){
			numberToCoord[num] = new Coord(row, col);
			if(row == 1){
				col += 2;
				row = 5;
			} else {
				row -= 2;
			}
		}
		
		/* Each number selects itself */
		for(Coord c : numberToCoord){
			coordToSelection.put(c, new Coord[]{c});
		}
		
		/* Top and bottom selectors for whole column */
		for(col = 1; col <=23; col+=2){
			Coord[] coords = new Coord[]{
					new Coord(1,col),
					new Coord(3,col),
					new Coord(5,col)
			};
			coordToSelection.put(new Coord(0,col), coords);
			coordToSelection.put(new Coord(6,col), coords);
		}
		
		/* Top and bottom selectors for double column */
		for(col = 2; col <=22; col+=2){
			Coord[] coords = new Coord[]{
					new Coord(1,col-1),
					new Coord(1,col+1),
					new Coord(3,col-1),
					new Coord(3,col+1),
					new Coord(5,col-1),
					new Coord(5,col+1)
				};
			coordToSelection.put(new Coord(0,col), coords);
			coordToSelection.put(new Coord(6,col), coords);
		}
		
		/* Four selectors */
		for(int r : new int[]{2,4}){
			for(int c = 2; c <= 22; c+=2){
				coordToSelection.put(new Coord(r, c), new Coord[]{new Coord(r-1, c-1), new Coord(r-1, c+1), new Coord(r+1,c+1), new Coord(r+1,c-1)});
			}
		}
		
		/* Two selectors */
		for(int r : new int[]{2,4}){
			for(int c = 1; c <= 23; c+=2){
				coordToSelection.put(new Coord(r, c), new Coord[]{new Coord(r-1, c), new Coord(r+1, c)});
			}
		}
		
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
