import java.util.ArrayList;

public class Order {
	String customerName;
	ArrayList<Product> products= new ArrayList<Product>();
	boolean membership;
	double subTotal;
	double discount;
	double vat;
	Order(String customerName,ArrayList<Product> products,boolean membership){
		this.customerName=customerName;
		this.products=products;
		this.membership=membership;
		this.setSubTotal();
		this.setDiscount();
		this.setVat();
	}
	public void printReceipt(){
		printOrderHeader();
		System.out.printf("%-10s%s%s\n", "Customer", ": " ,customerName);
		for (int i = 0; i < products.size(); i++) {
			System.out.printf("%-10s%s%s\n", "item", ": " , products.get(i).getName() + " x " + products.get(i).getQuantity());
		}
		System.out.printf("%-10s%s%s\n", "Subtotal", ": " , subTotal + " SEK");
		if (discount > 0 ) {System.out.printf("%-10s%s%s\n", "Discount", ": " , -discount + " SEK");}
		System.out.printf("%-10s%s%s\n", "VAT", ": " , vat + " SEK");
		System.out.println("------------------------------");
		System.out.printf("%-10s%s%s\n", "TOTAL", "; " , (subTotal - discount + vat) + " SEK");
		printMenuFooterMessage();
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
		for (Product product : products) {
			this.subTotal += product.getPrice() * product.quantity;
		}
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
	public double getTotal() {
		return subTotal - discount + vat;
	}
}
