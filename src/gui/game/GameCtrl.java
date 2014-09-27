package gui.game;


import gui.MainGui;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
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
	public HashMap<Coord, Coord[]> coordToSelection = new HashMap<Coord, Coord[]>();
	private ArrayList<Bet> bets = new ArrayList<Bet>();
	private int[] chipAmounts = new int[]{1,2,3,4,5};
	private int currChip = -1;
	
	@FXML public GridPane grid;
	@FXML public ImageView wheel;
	@FXML public Circle ball;
	@FXML public Text balanceText;
	@FXML public ImageView whiteChip;
	@FXML public ImageView redChip;
	@FXML public ImageView greenChip;
	@FXML public ImageView blackChip;
	
	
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
		
		/* 1-12 selector */
		coordToSelection.put(new Coord(7,1), new Coord[]{
			numberToCoord[1],
			numberToCoord[2],
			numberToCoord[3],
			numberToCoord[4],
			numberToCoord[5],
			numberToCoord[6],
			numberToCoord[7],
			numberToCoord[8],
			numberToCoord[9],
			numberToCoord[10],
			numberToCoord[11],
			numberToCoord[12]
		});
		
		/* 13-24 selector */
		coordToSelection.put(new Coord(7,9), new Coord[]{
			numberToCoord[13],
			numberToCoord[14],
			numberToCoord[15],
			numberToCoord[16],
			numberToCoord[17],
			numberToCoord[18],
			numberToCoord[19],
			numberToCoord[20],
			numberToCoord[21],
			numberToCoord[22],
			numberToCoord[23],
			numberToCoord[24]
		});
		
		/* 25-36 selector */
		coordToSelection.put(new Coord(7,17), new Coord[]{
			numberToCoord[25],
			numberToCoord[26],
			numberToCoord[27],
			numberToCoord[28],
			numberToCoord[29],
			numberToCoord[30],
			numberToCoord[31],
			numberToCoord[32],
			numberToCoord[33],
			numberToCoord[34],
			numberToCoord[35],
			numberToCoord[36]
		});
		
		/* 1-18 selector */
		coordToSelection.put(new Coord(8,1), new Coord[]{
			numberToCoord[1],
			numberToCoord[2],
			numberToCoord[3],
			numberToCoord[4],
			numberToCoord[5],
			numberToCoord[6],
			numberToCoord[7],
			numberToCoord[8],
			numberToCoord[9],
			numberToCoord[10],
			numberToCoord[11],
			numberToCoord[12],
			numberToCoord[13],
			numberToCoord[14],
			numberToCoord[15],
			numberToCoord[16],
			numberToCoord[17],
			numberToCoord[18]
		});
		
		/* 19-36 selector */
		coordToSelection.put(new Coord(8,21), new Coord[]{
			numberToCoord[19],
			numberToCoord[20],
			numberToCoord[21],
			numberToCoord[22],
			numberToCoord[23],
			numberToCoord[24],
			numberToCoord[25],
			numberToCoord[26],
			numberToCoord[27],
			numberToCoord[28],
			numberToCoord[29],
			numberToCoord[30],
			numberToCoord[31],
			numberToCoord[32],
			numberToCoord[33],
			numberToCoord[34],
			numberToCoord[35],
			numberToCoord[36]
		});

		/* Parity selector */
		Coord[] evenCoords = new Coord[18];
		Coord[] oddCoords = new Coord[18];
		int eIdx = 0;
		int oIdx = 0;
		
		for(int i=1; i <= 36; i++){
			if(i %2 == 0)
				evenCoords[eIdx++] = numberToCoord[i];
			else
				oddCoords[oIdx++] = numberToCoord[i];
		}
		coordToSelection.put(new Coord(8,5), evenCoords);
		coordToSelection.put(new Coord(8,17), oddCoords);
		
		/* Red selector */
		coordToSelection.put(new Coord(8,9), new Coord[]{
			numberToCoord[1],
			numberToCoord[3],
			numberToCoord[5],
			numberToCoord[7],
			numberToCoord[9],
			numberToCoord[12],
			numberToCoord[14],
			numberToCoord[16],
			numberToCoord[18],
			numberToCoord[19],
			numberToCoord[21],
			numberToCoord[23],
			numberToCoord[25],
			numberToCoord[27],
			numberToCoord[30],
			numberToCoord[32],
			numberToCoord[34],
			numberToCoord[36]
		});
		
		/* Black selector */
		coordToSelection.put(new Coord(8,13), new Coord[]{
			numberToCoord[2],
			numberToCoord[4],
			numberToCoord[6],
			numberToCoord[8],
			numberToCoord[10],
			numberToCoord[11],
			numberToCoord[13],
			numberToCoord[15],
			numberToCoord[17],
			numberToCoord[20],
			numberToCoord[22],
			numberToCoord[24],
			numberToCoord[26],
			numberToCoord[28],
			numberToCoord[29],
			numberToCoord[31],
			numberToCoord[33],
			numberToCoord[35]
		});
		
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
			n.setOnMouseEntered(new EventSelectorEnter(this));
			n.setOnMouseExited(new EventSelectorExit(this));
			n.setOnMouseClicked(new EventSelectorClick(this));
		}
		
		balanceText.setText(String.format("%.8f฿", core.getCurrentPlayer().getAccount().getBalance()));
		
		EventHandler<MouseEvent> chipClick = new EventChipClick();
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


	public ArrayList<Bet> getBets() {
		return bets;
	}
}
