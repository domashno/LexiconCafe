public class Product {
	public String name;
	public double price;
	public int quantity;
	public Product(){
	}
	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}
	public Product[] generateAvailableProducts() {
		return new Product[] {
				new Product("Espresso", 25.00),
				new Product("Cappuccino", 35.00),
				new Product("Latte", 40.00),
				new Product("Croissant", 30.00),
				new Product("Sandwich", 55.00)
		};
	}
	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
}
