package gui;
	
import java.io.IOException;

import gui.account.TransactionsTableCtrl;
import gui.game.GameCtrl;
import core.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	public void start(Stage stage) throws Exception {
		foo();
		if(1==1)return;
		Core core = new Core();
		core.addPlayer();
		stage.setTitle("Bitcoin Roulette Account");
	    FXMLLoader l = new FXMLLoader(getClass().getResource("account/TransactionsTable.fxml"));
	    l.setController(new TransactionsTableCtrl(core));
	    Scene myScene = new Scene(l.load());
	    stage.setScene(myScene);
	    stage.show();
	}
	 
	public static void main(String[] args) {
		launch(args);
	}
	
	public void foo() throws IOException{
		Stage gameStage = new Stage();
		gameStage.setTitle("Bitcoin Roulette");
		FXMLLoader l = new FXMLLoader(getClass().getResource("game/Game.fxml"));
		l.setController(new GameCtrl(new Core()));	//TODO not make another core
		Scene myScene = new Scene(l.load());
		gameStage.setScene(myScene);
		gameStage.show();
	}
}
