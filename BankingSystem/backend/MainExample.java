import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import accounts.Account;
import accounts.AccountFactory;
import accounts.AccountType;
import accounts.service.AccountService;
import accounts.service.InterestGainingService;
import accounts.service.MonthlyFeeService;
import dao.implementations.AccountDAOImpl;
import dao.implementations.TransactionDAOImpl;
import dao.implementations.UserDAOImpl;
import dao.interfaces.AccountDAO;
import dao.interfaces.TransactionDAO;
import dao.interfaces.UserDAO;
import transactions.Transaction;
import users.User;

public class MainExample {

	public static void main(String[] args) throws IOException, ParseException {

		User user = new User("R123", "Taylor", "James", "Ripke", "Lecturer", "06/14/1995");
		Account account = AccountFactory.getAccount(AccountType.PERSONAL_CHECKING, user, true);

		UserDAO userDAO = new UserDAOImpl();
		userDAO.addUser(user);

		AccountDAO accountDAO = new AccountDAOImpl(userDAO);
		accountDAO.addAccount(account);

		TransactionDAO transactionDAO = new TransactionDAOImpl();
		AccountService accountService = new AccountService(accountDAO, transactionDAO);

		accountService.deposit(account, "50.00");
		System.out.println(account.getAccountBalance());

		accountService.withdraw(account, "75.00");
		System.out.println(account.getAccountBalance());

		MonthlyFeeService monthlyFeeService = new MonthlyFeeService(transactionDAO, accountDAO);
		monthlyFeeService.applyMonthlyFee();

		account = accountDAO.getAccount("0");
		System.out.println(account.getAccountBalance());

		List<Transaction> transactions = transactionDAO.getTransactions("0", LocalDate.now(), LocalDate.now());
		for (Transaction t : transactions) {
			System.out.println(t);
		}

		account = AccountFactory.getAccount(AccountType.PERSONAL_SAVINGS, user, true);
		accountDAO.addAccount(account);
		accountService.deposit(account, "50.00");

		InterestGainingService interestGainingService = new InterestGainingService(transactionDAO, accountDAO);
		interestGainingService.applyCompoundInterest();

		account = accountDAO.getAccount("1");
		System.out.println(account.getAccountBalance());
	}

}
