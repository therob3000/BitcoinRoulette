package gui.game;

import gui.Main;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
	private Main main;
	@FXML public ImageView wheel;
	@FXML public ImageView board;
	@FXML public Circle ball;
	@FXML public Group group;
	@FXML public GridPane gridPane;
	
	public GameCtrl(Core core, Main main){
		this.core = core;
		this.main = main;
	}
	
	public void initialize(){
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
		main.getStage().setScene(main.getAccountScene());
	}
}
