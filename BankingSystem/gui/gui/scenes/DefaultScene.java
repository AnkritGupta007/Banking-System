package gui.scenes;

import java.io.FileNotFoundException;
import java.text.ParseException;

import gui.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Used to represent the default scene.
 * 
 * @author ripke1tj
 *
 */
public class DefaultScene {

	/**
	 * Generates the Default scene for the Banking System, which is the home page.
	 * 
	 * @return The constructed default scene for the Banking System.
	 */
	public static Scene getScene() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setStyle("-fx-background-color: white;");
		grid.setMaxWidth(300);
		grid.setMaxHeight(300);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Image img = new Image("logo.png");
		grid.add(new ImageView(img), 0, 0);

		Text instructions = new Text("Enter an account or DL number");
		grid.add(instructions, 0, 1);

		TextField accountField = new TextField();
		grid.add(accountField, 0, 2);

		Button submitButton = new Button("Account Option");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(submitButton);
		grid.add(hbBtn, 0, 3);

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Main.changeScene(AccountActionScene.getScene(accountField.getText()));
			}
		});
		
		Button getAccountsBtn= new Button("Get Accounts");
		HBox getAccountsHBtn = new HBox(10);
		getAccountsHBtn.setAlignment(Pos.BOTTOM_CENTER);
		getAccountsHBtn.getChildren().add(getAccountsBtn);
		grid.add(getAccountsHBtn, 0, 4);

		getAccountsBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
					Main.changeScene(AccountScence.getScene(accountField.getText()));
				
			}
		});
		
		Button addNewCustomerButton = new Button("Add New Customer");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(addNewCustomerButton);
		grid.add(hbBtn, 0, 5);

		addNewCustomerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Main.changeScene(UserScene.getScene());
			}
		});
		

		Text notFederallyInsuredText = new Text("Not Federally insured by NCUA.");
		grid.add(notFederallyInsuredText, 0, 6);

		BorderPane bp = new BorderPane();
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(notFederallyInsuredText);
		bp.setCenter(grid);
		bp.setBottom(hb);
		grid.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

		Scene scene = new Scene(bp, 475, 550);
		scene.getStylesheets().add(LoginScene.class.getResource("/Login.css").toExternalForm());

		return scene;
	}

}
