package gui;
	
import gui.account.AccountCtrl;
import gui.game.GameCtrl;
import core.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;


public class MainGui extends Application {
	private Scene gameScene;
	private Scene accountScene;
	private Stage stage;
	
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setResizable(false);
		Core core = new Core();
		core.addPlayer();
		
		/* Game Screen */
		FXMLLoader gameFxml = new FXMLLoader(getClass().getResource("game/Game.fxml"));
		gameFxml.setController(new GameCtrl(core, this));	//TODO not make another core
		gameScene = new Scene(gameFxml.load());
		Node n = null;
		
		/* Account Screen */
	    FXMLLoader accountFxml = new FXMLLoader(getClass().getResource("account/Account.fxml"));
	    accountFxml.setController(new AccountCtrl(core, this));
	    accountScene = new Scene(accountFxml.load());
	    
	    stage.setWidth(1024);
		stage.setHeight(700);
	    stage.setTitle("Bitcoin Roulette");
	    stage.setScene(gameScene);
	    stage.show();
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Scene getGameScene(){
		return gameScene;
	}
	
	public Scene getAccountScene(){
		return accountScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
