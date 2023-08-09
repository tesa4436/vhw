import java.util.List;
import java.math.BigDecimal;

public class ThirdLLPTransactionFreeOncePerMonthRule implements Rule {
	public void apply(List<Transaction> transactions, List<Price> prices) {
		int count = 0;
		int previousMonth = transactions.get(0).getDate().getMonthValue();

		for (Transaction tr : transactions) {

			if (!tr.isValid()) {
				continue;
			}

			if (tr.getSize() != PackageSize.L || tr.getProvider() != Provider.LP) {
				continue;
			}

			int month = tr.getDate().getMonthValue();
			count = previousMonth != month ? 1 : count + 1;

			if (tr.getSize() == PackageSize.L && tr.getProvider() == Provider.LP && count == 3) {
				tr.setDiscount(tr.getPrice());
				tr.setPrice(BigDecimal.ZERO);
			}

			previousMonth = month;
		}
	}
}
