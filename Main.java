import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Main {

	public static void main(String[] args) {
		var reader = new TransactionReader();
		var prices = new ArrayList<Price>();
		var rules = new ArrayList<Rule>();

		prices.add(new Price(Provider.LP, PackageSize.S, new BigDecimal(1.50)));
		prices.add(new Price(Provider.LP, PackageSize.M, new BigDecimal(4.90)));
		prices.add(new Price(Provider.LP, PackageSize.L, new BigDecimal(6.90)));
		prices.add(new Price(Provider.MR, PackageSize.S, new BigDecimal(2.00)));
		prices.add(new Price(Provider.MR, PackageSize.M, new BigDecimal(3.00)));
		prices.add(new Price(Provider.MR, PackageSize.L, new BigDecimal(4.00)));

		rules.add(new SizeSLowestPriceRule());
		rules.add(new ThirdLLPTransactionFreeOncePerMonthRule()); //add rules in this specific order, which is important
		rules.add(new DiscountLimitPerMonthRule());

		try {
			List<Transaction> transactions = reader.read("input.txt");
			for (Transaction tr : transactions) {

				if (!tr.isValid()) { // is valid when parsed successfully
					continue;
				}

				for (Price pr : prices) {
					if (pr.getProvider() == tr.getProvider() && pr.getSize() == tr.getSize()) {
						tr.setPrice(pr.getPrice());
					} // set the initial prices according to the price data
				}
			}

			/*
			 * we apply the rules from a list to the transaction list, one rule by one.
			 * the rule classes must implement the Rule interface.
			 * this allows rather easy addition of new rules and modification of existing ones.
			 * also, order of rules in the rule list is important. this approach, instead of applying the
			 * rule to each of the transactions, makes more sense because we need not just the
			 * current transaction during processing, but the other transactions as well.
			 */

			for (Rule rl : rules) { 
				rl.apply(transactions, prices);
			}

			for (Transaction tr : transactions) { // output formatted output to stdout
				var twoDecimals = new DecimalFormat("#0.00");
				if (tr.isValid() && tr.getDiscount().compareTo(BigDecimal.ZERO) != 0) {
					System.out.println(tr.getDate() + " " + tr.getSize() + " " + tr.getProvider() + " " + twoDecimals.format(tr.getPrice()) + " " + twoDecimals.format(tr.getDiscount()));
				} else if (tr.isValid() && tr.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
					System.out.println(tr.getDate() + " " + tr.getSize() + " " + tr.getProvider() + " " + twoDecimals.format(tr.getPrice()) + " -");
				} else if (!tr.isValid()) {
					System.out.println(tr.getTransactionString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
