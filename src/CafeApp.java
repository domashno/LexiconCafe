import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericDeclaration;

public class CafeApp {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));       // using java.io.*
		System.out.println("Welcome! What is your name? ");
		String customerName = reader.readLine();
		System.out.println("Hi " + customerName + "! Here is our menu:");
		Product[] availableProducts = new Product().generateAvailableProducts();
		showMenu(availableProducts);
		System.out.println("Enter item number (1-5):");
		String productIndex = reader.readLine();
		System.out.println("How many? ");
		String productQuantity = reader.readLine();
		System.out.println("Loyalty member? (yes/no):");
		String loyalty = reader.readLine();
		showReceipt(customerName, availableProducts[Integer.parseInt(productIndex)-1],Integer.parseInt(productQuantity), loyalty.equals("yes"));
	}
	public static void showMenu(Product[] availableProducts){
		showMenuHeader();
		for (int i = 0; i < 5; i++){
			System.out.printf("%s%-16s %s\n", (i+1) + ". ", availableProducts[i].getName(), availableProducts[i].getPrice() + " SEK");

		}
		showMenuFooter();
	}
	public static void showReceipt(String customerName,Product product,int productQuantity,boolean loyalty){
		showMenuHeader();
		double subTotal=subTotal(product,productQuantity),discount=discount(subTotal,loyalty),vat=vat(subTotal,discount);
		System.out.printf("%-10s%s%s\n", "Customer", ": " ,customerName);
		System.out.printf("%-10s%s%s\n", "item", ": " , product.getName() + " x " + productQuantity);
		System.out.printf("%-10s%s%s\n", "Subtotal", ": " , subTotal + " SEK");
		if (discount > 0 ) {System.out.printf("%-10s%s%s\n", "Discount", ": " , -discount + " SEK");}
		System.out.printf("%-10s%s%s\n", "VAT", ": " , vat + " SEK");
		System.out.println("------------------------------");
		System.out.printf("%-10s%s%s\n", "TOTAL", "; " , (subTotal - discount + vat) + " SEK");
		showMenuFooterMessage(customerName);

	}

	public static void showMenuHeader(){
		System.out.println("==============================\n       Lexicon Cafe\n==============================");
	}
	public static void showMenuFooter(){
		System.out.println("==============================\n");
	}
	public static void showMenuFooterMessage(String customerName){
		System.out.println("==============================");
		System.out.printf("%"+ (30-((30-(customerName.length()+12)))/2)+"s\n","Thank you, "+ customerName +"!"); //((customerName.length()/2)+12)
		System.out.printf("%24s\n","See you next time.");//18char
		System.out.println("==============================");
	}
	public static double  subTotal(Product product,int productQuantity){
		return product.getPrice() * productQuantity;
	}
	public static double  discount(double subTotal,boolean loyalty){
		return loyalty ? subTotal * 0.15 : (subTotal > 150.00) ? subTotal * 0.10 : 0.00;
	}
	public static double  vat(double subtotal,double discount){
		return (subtotal - discount) * 0.12;
	}
}