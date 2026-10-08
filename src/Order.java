public class Order {
	String customerName;
	Product product;
	int quantity;
	boolean membership;
	double subTotal;
	double discount;
	double vat;
	Order(String customerName,Product product,int quantity,boolean membership){
		this.customerName=customerName;
		this.product=product;
		this.quantity=quantity;
		this.membership=membership;
		this.setSubTotal();
		this.setDiscount();
		this.setVat();
	}
	public double printReceipt(){
		printOrderHeader();
		System.out.printf("%-10s%s%s\n", "Customer", ": " ,customerName);
		System.out.printf("%-10s%s%s\n", "item", ": " , product.getName() + " x " + quantity);
		System.out.printf("%-10s%s%s\n", "Subtotal", ": " , subTotal + " SEK");
		if (discount > 0 ) {System.out.printf("%-10s%s%s\n", "Discount", ": " , -discount + " SEK");}
		System.out.printf("%-10s%s%s\n", "VAT", ": " , vat + " SEK");
		System.out.println("------------------------------");
		System.out.printf("%-10s%s%s\n", "TOTAL", "; " , (subTotal - discount + vat) + " SEK");
		printMenuFooterMessage();
		return subTotal - discount + vat;
	}
	public void printOrderHeader(){
		System.out.println("==============================\n       Lexicon Cafe\n==============================");
	}
	public void printOrderFooter(){
		System.out.println("==============================\n");
	}
	public void printMenuFooterMessage(){
		System.out.println("==============================");
		System.out.printf("%"+ (30-((30-(customerName.length()+12)))/2)+"s\n","Thank you, "+ customerName +"!");
		System.out.printf("%24s\n","See you next time.");
		System.out.println("==============================");
	}
	public void setSubTotal() {
		this.subTotal = this.product.getPrice() * this.quantity;
	}

	public void setDiscount(){
		this.discount= membership ? subTotal * 0.15 : (subTotal > 150.00) ? subTotal * 0.10 : 0.00;
	}
	public void setVat(){
		this.vat= (subTotal - discount) * 0.12;
	}
	public double getVat() {
		return this.vat ;
	}
}
