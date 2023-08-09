import java.util.List;
import java.math.BigDecimal;

public class DiscountLimitPerMonthRule implements Rule {
	public void apply(List<Transaction> transactions, List<Price> prices) {
		BigDecimal discountSum = BigDecimal.ZERO;
		BigDecimal previousSum = BigDecimal.ZERO;
		int previousMonth = transactions.get(0).getDate().getMonthValue();
		boolean skipToNextMonth = false;

		for (Transaction tr : transactions) {

			if (!tr.isValid()) {
				continue;
			}

			int month = tr.getDate().getMonthValue();

			if (skipToNextMonth && previousMonth == month) {
				/*
				 * this subroutine is executed when the discount limit has been exceeded.
				 * in that case we reset the original prices until we hit the next month in transaction list.
				 * we assume the dates are sorted in ascending order in the transaction list.
				 */
				tr.setPrice(tr.getPrice().add(tr.getDiscount()));
				tr.setDiscount(BigDecimal.ZERO);
				previousMonth = month;
				continue;
			} else if (skipToNextMonth && previousMonth != month) {
				skipToNextMonth = false; // reset the discount sum count after locating the next month
				discountSum = BigDecimal.ZERO;
				previousSum = BigDecimal.ZERO;
			}

			previousSum = discountSum;
			discountSum = previousMonth != month ? tr.getDiscount() : discountSum.add(tr.getDiscount());

			if (discountSum.compareTo(BigDecimal.TEN) > 0) {
				tr.setDiscount(BigDecimal.TEN.subtract(previousSum));

                                for (Price pr : prices) { //calculate the new price and discount
                                        if (pr.getProvider() == tr.getProvider() && pr.getSize() == tr.getSize()) {
                                                tr.setPrice(pr.getPrice());
                                        }
                                }

				tr.setPrice(tr.getPrice().subtract(tr.getDiscount()));
				skipToNextMonth = true;
			}

			previousMonth = month;
		}
	}
}
