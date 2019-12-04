package gui.scenes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.ParseException;

import accounts.Account;
import accounts.AccountFactory;
import accounts.AccountStatus;
import accounts.AccountType;
import accounts.service.AccountService;
import dao.implementations.AccountDAOImpl;
import dao.implementations.TransactionDAOImpl;
import dao.implementations.UserDAOImpl;
import dao.interfaces.AccountDAO;
import dao.interfaces.TransactionDAO;
import dao.interfaces.UserDAO;
import gui.main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import users.User;

public class AccountActionScene {
	static Account acc=null;
	public static Scene getScene(String accountNumber)   {
		UserDAO userDAO = new UserDAOImpl();
		try {
			userDAO.addUser(null);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		AccountDAO accountDAO = new AccountDAOImpl(userDAO);
		TransactionDAO transactionDAO = new TransactionDAOImpl();
		AccountService accountService = new AccountService(accountDAO, transactionDAO);
		
		try {
			acc = accountDAO.getAccount(accountNumber);
		} catch ( Exception e2) {
			// TODO Auto-generated catch block
			Alert alert=new Alert(AlertType.ERROR,"Account Not Found \nPlease check Account Number",ButtonType.OK);
			alert.showAndWait();
		}
		
				
				
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Perform Account Action");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label amount = new Label("Amount");
		grid.add(amount, 0, 1);

		TextField amountField = new TextField();
		grid.add(amountField, 0,2,1,1);
		
		
		Label accountBalance = new Label("Account Balance");
		grid.add(accountBalance, 1, 1);
		
		Label accountBalanceField=new Label();
		if(acc!=null) {
		 accountBalanceField.setText(acc.getAccountBalance().setScale(2,RoundingMode.CEILING).toPlainString());
		}
		grid.add(accountBalanceField, 1,2,1,1);


		Button withdrawBtn = new Button("Withdraw");
		HBox withdrawhBtn = new HBox(10);
		withdrawhBtn.setAlignment(Pos.BOTTOM_RIGHT);
		withdrawhBtn.getChildren().add(withdrawBtn);
		grid.add(withdrawhBtn, 1, 4);

		withdrawBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
					boolean result=false;
					if(acc!=null) {
						try {
							result = accountService.withdraw(acc, amountField.getText());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(result) {
						accountBalanceField.setText(acc.getAccountBalance().setScale(2,RoundingMode.CEILING).toPlainString());
						Alert alert=new Alert(AlertType.CONFIRMATION,"Successful",ButtonType.OK);
						alert.showAndWait();
						amountField.setText("");
					}
					else {
						Alert alert=new Alert(AlertType.ERROR,"Unsuccessfull",ButtonType.OK);
						alert.showAndWait();
					}
				
			}
		});
		
		Button depositButton = new Button("Deposit");
		HBox depositHButton = new HBox(10);
		depositHButton.setAlignment(Pos.BOTTOM_RIGHT);
		depositHButton.getChildren().add(depositButton);
		grid.add(depositHButton, 2, 4);

		depositButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					boolean result=accountService.deposit(acc, amountField.getText());
					if(result) {
						Alert alert=new Alert(AlertType.CONFIRMATION,"Successful",ButtonType.OK);
						alert.showAndWait();
						accountBalanceField.setText(acc.getAccountBalance().setScale(2,RoundingMode.CEILING).toPlainString());
						amountField.setText("");
					}
					else {
						Alert alert=new Alert(AlertType.ERROR,"Unsuccessfull",ButtonType.OK);
						alert.showAndWait();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		Button btn = new Button("Close Account");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn,3 , 4);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				boolean result;
				try {
					result=accountService.removeAccount(accountNumber);
					if(result) {
						Alert alert=new Alert(AlertType.CONFIRMATION,"Account Closed",ButtonType.OK);
						alert.showAndWait();
						accountBalanceField.setText("0.00");
					}
					else {
						Alert alert=new Alert(AlertType.ERROR,"Account Closed",ButtonType.OK);
						alert.showAndWait();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Alert alert=new Alert(AlertType.CONFIRMATION,"Account Closed",ButtonType.OK);
				alert.showAndWait();
			}
		});
		
		Button returnButton = new Button("Close");
		HBox returnHButton = new HBox(10);
		returnHButton.setAlignment(Pos.BOTTOM_RIGHT);
		returnHButton.getChildren().add(returnButton);
		grid.add(returnHButton,3 , 5);

		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Main.changeScene(DefaultScene.getScene());
			}
		});

		Scene scene = new Scene(grid, 475, 275);
		scene.getStylesheets().add(LoginScene.class.getResource("/Login.css").toExternalForm());

		return scene;
	}
}
