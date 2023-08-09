import java.time.LocalDate;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tests {

	public static void main(String[] args) {
		var prices = new ArrayList<Price>();	

                prices.add(new Price(Provider.LP, PackageSize.S, new BigDecimal(1.50)));
                prices.add(new Price(Provider.LP, PackageSize.M, new BigDecimal(4.90)));
                prices.add(new Price(Provider.LP, PackageSize.L, new BigDecimal(6.90)));
                prices.add(new Price(Provider.MR, PackageSize.S, new BigDecimal(2.00)));
                prices.add(new Price(Provider.MR, PackageSize.M, new BigDecimal(3.00)));
                prices.add(new Price(Provider.MR, PackageSize.L, new BigDecimal(4.00)));

		testThirdTransactionRule(getTransactionsForThird(), prices);
		testDiscountLimitRule(getTransactionsForLimit(), prices);
		testLowestPriceForSPackages(getTransactionsForLowestPrice(), prices);
	}

	public static void testThirdTransactionRule(ArrayList<Transaction> transactions, ArrayList<Price> prices) {
		var rule = new ThirdLLPTransactionFreeOncePerMonthRule();
		rule.apply(transactions, prices);

		assert transactions.get(8).getPrice().compareTo(BigDecimal.ZERO) == 0;
		assert transactions.get(transactions.size() - 1).getPrice().compareTo(BigDecimal.ZERO) == 0;
	}

	public static void testDiscountLimitRule(ArrayList<Transaction> transactions, ArrayList<Price> prices) {
		var rule = new DiscountLimitPerMonthRule();
		rule.apply(transactions, prices);

		assert transactions.get(17).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(
			new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP)) == 0;
	}

	public static void testLowestPriceForSPackages(ArrayList<Transaction> transactions, ArrayList<Price> prices) {
		var rule = new SizeSLowestPriceRule();

                for (Transaction tr : transactions) {
			for (Price pr : prices) {
				if (pr.getProvider() == tr.getProvider() && pr.getSize() == tr.getSize()) {
					tr.setPrice(pr.getPrice());
                                }
                        }
                }

		rule.apply(transactions, prices);

		assert transactions.get(0).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(1).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(4).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(10).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(11).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(15).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(17).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
		assert transactions.get(19).getDiscount().setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal(0.50).setScale(2, RoundingMode.HALF_UP)) == 0;
	}

	public static ArrayList<Transaction> getTransactionsForThird() {
		var transactions = new ArrayList<Transaction>();	
		transactions.add(new Transaction(LocalDate.of(2015, 02, 1), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 2), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 3), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 5), PackageSize.S, Provider.LP, new BigDecimal(1.50), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 7), PackageSize.L, Provider.MR, new BigDecimal(4.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 8), PackageSize.M, Provider.MR, new BigDecimal(3.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 9), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 11), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 12), PackageSize.M, Provider.MR, new BigDecimal(3.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 13), PackageSize.M, Provider.LP, new BigDecimal(4.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 15), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.S, Provider.MR, new BigDecimal(2.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 24), PackageSize.L, Provider.LP, new BigDecimal(6.90), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));

		return transactions;
	}

	public static ArrayList<Transaction> getTransactionsForLimit() {
		var transactions = new ArrayList<Transaction>();	
		transactions.add(new Transaction(LocalDate.of(2015, 02, 1), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 2), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 3), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 5), PackageSize.S, Provider.LP, new BigDecimal(1.50), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 7), PackageSize.L, Provider.MR, new BigDecimal(4.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 8), PackageSize.M, Provider.MR, new BigDecimal(3.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 9), PackageSize.L, Provider.LP, BigDecimal.ZERO, new BigDecimal(6.90)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 11), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 12), PackageSize.M, Provider.MR, new BigDecimal(3.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 13), PackageSize.M, Provider.LP, new BigDecimal(4.00), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 15), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.L, Provider.LP, new BigDecimal(6.90), BigDecimal.ZERO));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 24), PackageSize.L, Provider.LP, new BigDecimal(6.90), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.S, Provider.MR, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.L, Provider.LP, new BigDecimal(1.50), new BigDecimal(0.50)));

		return transactions;
	}

	public static ArrayList<Transaction> getTransactionsForLowestPrice() {
		var transactions = new ArrayList<Transaction>();	
		transactions.add(new Transaction(LocalDate.of(2015, 02, 1), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 2), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 3), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 5), PackageSize.S, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 6), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 7), PackageSize.L, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 8), PackageSize.M, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 9), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 10), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 11), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 12), PackageSize.M, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 13), PackageSize.M, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 15), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 17), PackageSize.S, Provider.MR));
		transactions.add(new Transaction(LocalDate.of(2015, 02, 24), PackageSize.L, Provider.LP));
		transactions.add(new Transaction(LocalDate.of(2015, 03, 01), PackageSize.S, Provider.MR));

		return transactions;
	}
}
