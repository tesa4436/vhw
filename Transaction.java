import java.time.LocalDate;
import java.math.BigDecimal;

public class Transaction {

	private LocalDate date;
	private PackageSize size;
	private Provider provider;
	private BigDecimal price = BigDecimal.ZERO;
	private BigDecimal discount = BigDecimal.ZERO;
	private boolean isValid;
	private String transactionString;

	public Transaction(LocalDate date, PackageSize size, Provider provider) {
		this.date = date;
		this.size = size;
		this.provider = provider;
		this.isValid = true;
	}

	public Transaction(LocalDate date, PackageSize size, Provider provider, BigDecimal price, BigDecimal discount) {
		this.date = date;
		this.size = size;
		this.provider = provider;
		this.price = price;
		this.discount = discount;
		this.isValid = true;
	}

	public Transaction(String transactionString) {
		this.transactionString = transactionString;
		this.isValid = false;
	}

	public boolean isValid() {
		return this.isValid;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public String getTransactionString() {
		return this.transactionString;
	}

	public PackageSize getSize() {
		return this.size;
	}

	public Provider getProvider() {
		return this.provider;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

}
