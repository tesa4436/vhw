import java.math.BigDecimal;

public class Price {
	private Provider provider;
	private PackageSize size;
	private BigDecimal price;
	private BigDecimal discount;

	public Price(Provider provider, PackageSize size, BigDecimal price) {
		this.provider = provider;
		this.size = size;
		this.price = price;
	}

	public Provider getProvider() {
		return this.provider;
	}

	public PackageSize getSize() {
		return this.size;
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
