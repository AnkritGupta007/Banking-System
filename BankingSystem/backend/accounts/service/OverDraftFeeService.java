package accounts.service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;

import accounts.Account;
import accounts.AccountType;
import transactions.Transaction;
import transactions.TransactionType;

/**
 * Used to define overdraft limits for checking accounts.
 * 
 * @author ripke1tj
 *
 */
public final class OverDraftFeeService {

	/*
	 * The overdraft fee applied across all accounts.
	 */
	private static final BigDecimal OVER_DRAFT_FEE = new BigDecimal("35.00");

	private OverDraftFeeService() {
		throw new IllegalStateException("This class should not be instantiated.");
	}

	/**
	 * Applies the overdraft fee.
	 * 
	 * @return: A transaction.
	 * @throws FileNotFoundException: Thrown if a connection to the database could
	 *                                not be established.
	 */
	public static Transaction applyOverDraftFee(Account account) throws FileNotFoundException {
		account.setAccountBalance(account.getAccountBalance().subtract(OVER_DRAFT_FEE));
		return new Transaction(account.getAccountId(), LocalDate.now(), TransactionType.TRANSACTION_FEE, OVER_DRAFT_FEE,
				account.getAccountBalance());
	}

	/**
	 * Use to determine the overdraft fee limit.
	 * 
	 * @param type: The type of account.
	 * @return: The overdraft fee limit.
	 */
	public static BigDecimal getOverDraftFeeLimit(AccountType type) {
		BigDecimal overDraftFeeLimit = BigDecimal.ZERO;
		switch (type) {
		case STUDENT_CHECKING:
			overDraftFeeLimit = new BigDecimal("-500.00");
			break;
		case PERSONAL_CHECKING:
			overDraftFeeLimit = new BigDecimal("-1500.00");
			break;
		case BUSINESS_CHECKING:
			overDraftFeeLimit = new BigDecimal("-7500.00");
			break;
		default:
			return overDraftFeeLimit;
		}
		return overDraftFeeLimit;
	}

}
