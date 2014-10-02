package gui.game;


import gui.MainGui;
import gui.game.listeners.ChipClickListener;
import gui.game.listeners.SelectorClickListener;
import gui.game.listeners.SelectorEnterListener;
import gui.game.listeners.SelectorExitListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import core.Bet;
import core.Core;

public class GameCtrl {
	private Core core;
	private MainGui mainGui;
	public Coord[] numberToCoord = new Coord[37];
	public HashMap<Coord, Integer> coordToNumber = new HashMap<Coord, Integer>();
	public HashMap<Coord, Coord[]> coordToSelection = new HashMap<Coord, Coord[]>();
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	private int[] chipAmounts = new int[]{1,2,3,4,5};
	public int currChip = -1;
	
	@FXML public GridPane grid;
	@FXML public ImageView wheel;
	@FXML public Circle ball;
	@FXML public Text balanceText;
	@FXML public ImageView whiteChip;
	@FXML public ImageView redChip;
	@FXML public ImageView greenChip;
	@FXML public ImageView blackChip;
	@FXML public AnchorPane boardPane;
	
	public GameCtrl(Core core, MainGui mainGui){
		this.core = core;
		this.mainGui = mainGui;
	}
	
	/* Called after scene graph loaded */
	public void initialize(){
		
		
		/* Get coordinate for each number */
		int num = 0;
		int row = 5;
		int col = 1;
		while(++num <= 36){
			Coord c = new Coord(row, col);
			numberToCoord[num] = c;
			coordToNumber.put(c, num);
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
		
		/* Two selectors (vertical) */
		for(int r : new int[]{2,4}){
			for(int c = 1; c <= 23; c+=2){
				coordToSelection.put(new Coord(r, c), new Coord[]{new Coord(r-1, c), new Coord(r+1, c)});
			}
		}
		
		/* Two selectors (horizontal) */
		for(int r : new int[]{1,3,5}){
			for(int c = 2; c <= 22; c+=2){
				coordToSelection.put(new Coord(r, c), new Coord[]{new Coord(r, c-1), new Coord(r, c+1)});
			}
		}
		
		/* 1-12 selector */
		Coord[] ray = new Coord[12];
		for(int i=1; i <= 12; i++){
			ray[i-1] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(7,1), ray);
		
		/* 13-24 selector */
		ray = new Coord[12];
		for(int i=13; i <= 24; i++){
			ray[i-13] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(7,9), ray);
		
		/* 25-36 selector */
		ray = new Coord[12];
		for(int i=25; i <= 36; i++){
			ray[i-25] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(7,17), ray);
		
		/* 1-18 selector */
		ray = new Coord[18];
		for(int i=1; i <= 18; i++){
			ray[i-1] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(8,1), ray);
		
		/* 19-36 selector */
		ray = new Coord[18];
		for(int i=19; i <= 36; i++){
			ray[i-19] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(8,21), ray);

		/* Parity selector */
		Coord[] evenCoords = new Coord[18];
		Coord[] oddCoords = new Coord[18];
		int eIdx = 0;
		int oIdx = 0;
		
		for(int i=1; i <= 36; i++){
			if(i % 2 == 0)
				evenCoords[eIdx++] = numberToCoord[i];
			else
				oddCoords[oIdx++] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(8,5), evenCoords);
		coordToSelection.put(new Coord(8,17), oddCoords);
		
		/* Red/Black selector */
		List<Integer> redNums = Arrays.asList(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36);
		Coord [] reds = new Coord[18];
		Coord [] blacks = new Coord[18];
		int redIdx = 0;
		int blackIdx = 0;
		
		for(int i=1; i <= 36; i++){
			if(redNums.contains(i))
				reds[redIdx++] = numberToCoord[i];
			else
				blacks[blackIdx++] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(8,9), reds);
		coordToSelection.put(new Coord(8,13), blacks);
		
		/* '1st','2nd','3rd' column selectors */
		Coord[] first = new Coord[12];
		Coord[] second = new Coord[12];
		Coord[] third = new Coord[12];
		int firstIdx=0 ,secondIdx=0 ,thirdIdx = 0;
		for(int i=1; i <= 36; i++){
			if(i % 3 == 0){
				third[thirdIdx++] = numberToCoord[i];
			} else if (i % 3 == 2) {
				second[secondIdx++] = numberToCoord[i];
			} else {
				first[firstIdx++] = numberToCoord[i];
			}
		}
		coordToSelection.put(new Coord(5,24), first);
		coordToSelection.put(new Coord(3,24), second);
		coordToSelection.put(new Coord(1,24), third);
		
		for(Node n : grid.getChildren()){
			n.setOnMouseEntered(new SelectorEnterListener(this));
			n.setOnMouseExited(new SelectorExitListener(this));
			n.setOnMouseClicked(new SelectorClickListener(this));
		}
		
		balanceText.setText(String.format("%.8f฿", core.getCurrentPlayer().getAccount().getBalance()));
		
		EventHandler<MouseEvent> chipClick = new ChipClickListener(this);
		whiteChip.setOnMouseClicked(chipClick);
		redChip.setOnMouseClicked(chipClick);
		greenChip.setOnMouseClicked(chipClick);
		blackChip.setOnMouseClicked(chipClick);
	}
	
	public Node getPaneFromCoord(int row, int col){
		for(Node n : grid.getChildren()){
			if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == col)
				return n;
		}
		return null;
	}

	 private Path createCirclePath(int cycles) {
		 	double radiusX = 110;
		 	double radiusY = 110;
	        
	        double [] xs = new double[]{-45, -75, -101, -110, -102, -72, -47, 0, 47, 80, 105, 115, 108, 80, 50, 0};
	        double [] ys = new double[]{13, 35, 70, 110, 155, 197, 215, 228, 218, 198, 165, 110, 75, 33, 12, 0};
	        
	        Path path = new Path();
	        path.getElements().add(new MoveTo(0,0));
	        
	        int numIncrements = xs.length;
	        int iterations = cycles * numIncrements + (int)(Math.random() * numIncrements);
	        System.out.println(iterations % cycles);
	       
	        for(int i=0; i < iterations; i++){
	        	double x = xs[i % numIncrements];
	        	double y = ys[i % numIncrements];
	        	path.getElements().add(new ArcTo(radiusX, radiusY, 0, x, y, false, false));
	        }
	        
	        return path;
	    }

	 public void spin(ActionEvent e){
		
		 long durationMillis = 12000;
		 Path p = createCirclePath(16);
		 PathTransition spin = new PathTransition(Duration.millis(durationMillis), p, ball);
		 spin.setInterpolator(Interpolator.LINEAR);
		 spin.play();
		 
		
		 /* Spin Wheel*/
		 int deg = 360*3 + (int)(Math.random() * 360);	//Between 3 and 4 spins
		 RotateTransition rt = new RotateTransition(Duration.millis(durationMillis), wheel);
		 rt.setByAngle(deg);
		 rt.play();

	 }
	 

	public void showAccountScene(){
		Scanner cin = new Scanner(System.in);
		String[] r = cin.nextLine().split(" ");
		ball.setCenterX(Integer.parseInt(r[0]));
		ball.setCenterY(Integer.parseInt(r[1]));
		return;
//		mainGui.getStage().setScene(mainGui.getAccountScene());
	}


	public ArrayList<Bet> getBets() {
		return bets;
	}
}
