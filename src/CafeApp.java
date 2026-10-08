import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericDeclaration;

public class CafeApp {
	public static void main(String[] args) throws IOException {
		String customerName="";
		int customersServed=0;
		double totalRevenue=0.00;
		boolean customerNameError=false;
		boolean menuItemsIndexInvalid = true;
		boolean menuItemquantityInvalid = true;
		boolean menuloyaltyInvalid = true;
		int productIndex=0;
		int productQuantity=0;
		boolean loyalty=false;
		do {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			do {
				System.out.println("Welcome! What is your name? ");
				try {
						customerName = reader.readLine();
						if (customerName.isEmpty()) {
							customerNameError = true;
							throw new Exception("Customer name can't be empty");
						}else{customerNameError=false;}
					} catch (Exception e) {
						System.out.println(e.getMessage());
				}

			}while (customerNameError);
			if (customerName.equalsIgnoreCase("done")){break;}
			System.out.println("Hi " + customerName + "! Here is our menu:");
			Product[] availableProducts = new Product().generateAvailableProducts();
			showMenu(availableProducts);
			do {
				try {
					System.out.println("Enter item number (1-"+ availableProducts.length +"):");
					productIndex = (Integer.parseInt(reader.readLine())-1);
					if ((productIndex+1) > availableProducts.length){
						throw new NumberFormatException();
					}
					menuItemsIndexInvalid = false;
				}catch(NumberFormatException e) {
					System.out.println("(Error) Please enter a number between 1-"+ availableProducts.length +" : ");
				} catch(IOException e) {
					e.printStackTrace();
				}
			} while(menuItemsIndexInvalid);
			do {
				try {
					System.out.println("How many? ");
					productQuantity = (Integer.parseInt(reader.readLine())-1);
					menuItemquantityInvalid = false;
				}catch(NumberFormatException e) {
					System.out.println("Please enter a valid number");
				} catch(IOException e) {
					e.printStackTrace();
				}
			} while(menuItemquantityInvalid);
			do {
				try {
					System.out.println("Loyalty member? (yes/no):");
					switch (reader.readLine().toLowerCase()) {
						case ("yes") -> {loyalty = true;menuloyaltyInvalid = false;}
						case ("no") -> {loyalty = false;menuloyaltyInvalid = false;}
						default -> throw new IOException("Invalid input");
					}
				} catch(IOException e) {
					System.out.println("Please enter yes or no");
				}
			} while(menuloyaltyInvalid);

			totalRevenue += showReceipt(customerName, availableProducts[productIndex],productQuantity, loyalty);
			customersServed++;
		} while(!customerName.equalsIgnoreCase("done"));
		showEndOfDayReport(customersServed, totalRevenue);

	}
	public static void showMenu(Product[] availableProducts){
		showMenuHeader();
		for (int i = 0; i < 5; i++){
			System.out.printf("%s%-16s %s\n", (i+1) + ". ", availableProducts[i].getName(), availableProducts[i].getPrice() + " SEK");

		}
		showMenuFooter();
	}
	public static double showReceipt(String customerName,Product product,int productQuantity,boolean loyalty){
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
		return subTotal - discount + vat;
	}

	public static void showMenuHeader(){
		System.out.println("==============================\n       Lexicon Cafe\n==============================");
	}
	public static void showMenuFooter(){
		System.out.println("==============================\n");
	}
	public static void showMenuFooterMessage(String customerName){
		System.out.println("==============================");
		System.out.printf("%"+ (30-((30-(customerName.length()+12)))/2)+"s\n","Thank you, "+ customerName +"!");
		System.out.printf("%24s\n","See you next time.");
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
	public static void showEndOfDayReport(int customersServed,double totalRevenue){
		System.out.println("==============================");
		System.out.printf("%24s\n","END OF DAY REPORT");
		System.out.println("==============================");
		System.out.printf("%-17s%s%s\n", "Customers served", ": " ,customersServed);
		System.out.printf("%-17s%s%s\n", "Total revenue", ": " , totalRevenue + " SEK");
		System.out.println("==============================");
	}
}