import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.GenericDeclaration;
import java.util.ArrayList;
import java.util.List;

public class CafeApp {
	public static void main(String[] args) throws IOException {
		String customerName="";
		int customersServed=0;
		double totalRevenue=0.00;
		int productIndex=0;
		int productQuantity=0;
		boolean loyalty=false;
		List<Order> orders = new ArrayList<Order>();
		do {
			ArrayList<Product> selectedProducts = new ArrayList<Product>();
			boolean customerNameError=false;
			boolean menuItemsIndexInvalid = true;
			boolean menuItemquantityInvalid = true;
			boolean menuloyaltyInvalid = true;
			boolean menuMultiselect = true;
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


				do {
					try {
						System.out.println("Enter item number (1-"+ availableProducts.length +", or 0 to finish):");
						productIndex = (Integer.parseInt(reader.readLine())-1);
						if ((productIndex+1) > availableProducts.length){
							throw new NumberFormatException();
						}
						if (productIndex <=0){menuMultiselect = false;}
						menuItemsIndexInvalid=false;
					}catch(NumberFormatException e) {
						System.out.println("(Error) Please enter a number between 1-"+ availableProducts.length +" : ");
					} catch(IOException e) {
						e.printStackTrace();
					}
				} while(menuItemsIndexInvalid);
				do {
					if (!menuMultiselect){break;};
					try {
						System.out.println("How many? ");
						productQuantity = Integer.parseInt(reader.readLine());
						if ((productQuantity) <=0){throw new NumberFormatException();}
						availableProducts[productIndex].setQuantity(productQuantity);
						menuItemquantityInvalid = false;
					}catch(NumberFormatException e) {
						System.out.println("Please enter a valid number");
					} catch(IOException e) {
						e.printStackTrace();
					}
					selectedProducts.add(availableProducts[productIndex]);
				} while(menuItemquantityInvalid);
			} while(menuMultiselect);
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
			Order  order=new Order(customerName, selectedProducts, loyalty);
			order.printReceipt();
			orders.add(order);
			totalRevenue +=order.getTotal();
			customersServed++;
		} while(!customerName.equalsIgnoreCase("done"));
		showEndOfDayReport(customersServed, totalRevenue);

	}
	public static void showMenu(Product[] availableProducts){
		showMenuHeader();
		for (int i = 0; i < availableProducts.length; i++){
			System.out.printf("%s%-16s %s\n", (i+1) + ". ", availableProducts[i].getName(), availableProducts[i].getPrice() + " SEK");

		}
		showMenuFooter();
	}
	public static void showMenuHeader(){
		System.out.println("==============================\n       Lexicon Cafe\n==============================");
	}
	public static void showMenuFooter(){
		System.out.println("==============================\n");
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