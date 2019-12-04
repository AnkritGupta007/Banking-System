package accounts;

import java.math.BigDecimal;

/**
 * Utility class for account.
 * 
 * @author ripke1tj
 *
 */
public final class AccountUtility {

	private AccountUtility() {
		throw new IllegalStateException("This class should not be instantiated.");
	}

	/**
	 * Validates the amount passed from the GUI is the correct form.
	 * 
	 * @param amount: The amount in the form #+.##
	 * @return A BigDecimal representation of the amount
	 * @throws IllegalArgumentException if the amount is not in a valid form
	 */
//	public static BigDecimal getValidAmount(String amount) throws IllegalArgumentException {
//		if (amount.matches("^\\d+\\.\\d{2}$")) {
//			return new BigDecimal(amount);
//		}
//		throw new IllegalArgumentException(amount + " should be in the form $(#+).##");
//	}
	
	public static BigDecimal getValidAmount(String amount)  {
		
			return new BigDecimal(amount);
		
	}
}
