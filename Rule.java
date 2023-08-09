import java.util.List;

public interface Rule {
	public void apply(List<Transaction> transactions, List<Price> prices);
}
