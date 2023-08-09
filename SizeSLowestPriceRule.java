import java.util.List;
import java.math.BigDecimal;

public class SizeSLowestPriceRule implements Rule {

	public void apply(List<Transaction> transactions, List<Price> prices) {
		for (Transaction tr : transactions) {

			if (!tr.isValid()) {
				continue;
			}

			if (tr.getSize() == PackageSize.S) {
				BigDecimal newPrice = tr.getPrice();

				for (Price pr : prices) {
					if (pr.getSize() == PackageSize.S && pr.getPrice().compareTo(newPrice) < 0) {
						newPrice = pr.getPrice();
					}
				}

				tr.setDiscount(tr.getPrice().subtract(newPrice));
				tr.setPrice(newPrice);
			}
		}
	}
}
