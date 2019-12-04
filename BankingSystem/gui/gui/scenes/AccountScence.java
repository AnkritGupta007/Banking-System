
package gui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;


//import DAOS.UserDAO;
//import GUI.main.Main;
//import GUI.user.User;
import accounts.Account;
import dao.implementations.AccountDAOImpl;
import gui.main.Main;

public class AccountScence {
	private static  AccountDAOImpl userDao= new AccountDAOImpl(null);
	@SuppressWarnings("unchecked")
	public static Scene getScene(String driversLicense) {
		
		//Create a table view instance
		@SuppressWarnings("rawtypes")
		TableView tableView=new TableView();
		// define a series of columns to be displayed on the table
		TableColumn<String, Account> column1=new TableColumn<>("Account Number");


		//this will automatically extract the data from a POJO(Plain Odd java Object)
		column1.setCellValueFactory(new PropertyValueFactory<>("accountId"));

		TableColumn<String, Account> column2=new TableColumn<>("Account Type");
		column2.setCellValueFactory(new PropertyValueFactory<>("accountType"));

		TableColumn<String, Account> column3=new TableColumn<>("Account Balance");
		column3.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		List<Account> accounts=null;
		try {
			try {
				accounts=userDao.getUserAccounts(driversLicense);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Account acc:accounts) {
				tableView.getItems().add(acc);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Alert alert=new Alert(AlertType.ERROR,"errors reading user file",ButtonType.OK);
			alert.showAndWait();
		}
		tableView.setPlaceholder(new Label("No rows to display"));

		tableView.setOnMouseClicked((MouseEvent event)->{
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2) {
				Main.changeScene(AccountActionScene.getScene(((Account)tableView.getSelectionModel().getSelectedItem()).getAccountId()));			}
		});






		VBox vbox=new VBox(tableView);
		Scene scene=new Scene(vbox,475,275);
		return scene;
	}


}
