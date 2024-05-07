import java.util.*;

/*
 import that imports all classes from the java.util package.
 It's a convenient way to import multiple classes from the same package without explicitly listing each one.
*/


/*The interface Discountable declares a method calculateDiscount() without providing any implementation details. 
Interfaces  define a contract for classes that implement them. In this case, 
any class that implements the Discountable interface must provide an implementation for the calculateDiscount() method.*/


interface Discountable {
    double calculateDiscount();
}


abstract class Product {
    private String productId;
    private String name;
    private double price;
    private String brand;

    public Product(String productId, String name, double price, String brand) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public abstract void displayDetails();
                        
    public double calculateDiscount() {
        return 0;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

class Electronics extends Product implements Discountable {
    
    private int warrantyPeriod;
    private String powerSupply;

    public Electronics(String productId, String name, double price, String brand, int warrantyPeriod, String powerSupply) {
      //super refer to the superclass (or parent class) of the current class
     
        super(productId, name, price, brand);
        this.warrantyPeriod = warrantyPeriod;
        this.powerSupply = powerSupply;
    }

    @Override
    public void displayDetails() {
        System.out.println("Product ID: " + getProductId());
        System.out.println("Name: " + getName());
        System.out.println("Price: $" + getPrice());
        System.out.println("Brand: " + getBrand());
        System.out.println("Warranty Period: " + warrantyPeriod + " months");
        System.out.println("Power Supply: " + powerSupply);
    }

    @Override
    public double calculateDiscount() {
        // Apply 20% discount only for electronics
        return getPrice() * 0.20; 
    }
}

class Clothing extends Product implements Discountable {
    
    private String size;
    private String color;

    public Clothing(String productId, String name, double price, String brand, String size, String color) {
        super(productId, name, price, brand);
        this.size = size;
        this.color = color;
    }

    @Override
    public void displayDetails() {
        System.out.println("Product ID: " + getProductId());
        System.out.println("Name: " + getName());
        System.out.println("Price: $" + getPrice());
        System.out.println("Brand: " + getBrand());
        System.out.println("Size: " + size);
        System.out.println("Color: " + color);
    }

    @Override
    public double calculateDiscount() {
        return 0;
    }
}

abstract class User {
    private String userId;
    private String name;
    private String email;
    private List<Product> cart;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.cart = new ArrayList<>();
    }

    public abstract void addToCart(Product product);

    public abstract void removeFromCart(Product product);

    public abstract void checkout();

    public void viewCart() {
        System.out.println("Items in your cart:");
        for (Product item : cart) {
            item.displayDetails();
        }
    }

    public void clearCart() {
        cart.clear();
        System.out.println("Cart cleared.");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }
}


class Customer extends User {
    private String shippingAddress;
    private String paymentMethod;

    public Customer(String userId, String name, String email, String shippingAddress, String paymentMethod) {
        super(userId, name, email);
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void addToCart(Product product) {
        getCart().add(product);
        System.out.println(product.getName() + " added to cart.");
    }

    @Override
    public void removeFromCart(Product product) {
        getCart().remove(product);
        System.out.println(product.getName() + " removed from cart.");
    }

    @Override
    public void checkout() {
        if (getCart().isEmpty()) {
            System.out.println("Your cart is empty. Please add items to proceed to checkout.");
            return;
        }

        System.out.println("Items in your cart:");
        double totalAmount = 0;
        for (Product item : getCart()) {
            item.displayDetails();
            totalAmount += item.getPrice();
        }
        
        // Calculate total amount with discount applied
        double discount = 0;
        for (Product item : getCart()) {
            if (item instanceof Electronics) {
                discount += ((Discountable) item).calculateDiscount();
            }
        }
        double totalAmountAfterDiscount = totalAmount - discount;

        System.out.println("Total amount to pay (after discount): $" + totalAmountAfterDiscount);
        System.out.println("Shipping Address: " + shippingAddress);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment processed successfully. Thank you for your purchase!");
        getCart().clear();
    }

    public void buy(Product product) {
        addToCart(product);
        checkout();
    }

    public void placeOrder() {
        if (getCart().isEmpty()) {
            System.out.println("Your cart is empty. Please add items to place an order.");
            return;
        }
        Order order = new Order(getCart(), calculateTotalAmount(), shippingAddress, paymentMethod);
        System.out.println("Order placed successfully!");
        clearCart();
    }

    private double calculateTotalAmount() {
        double total = 0;
        for (Product product : getCart()) {
            total += product.getPrice();
        }
        return total;
    }
}


class Admin extends User {
    private String role;
    private String permissions;

    public Admin(String userId, String name, String email, String role, String permissions) {
        super(userId, name, email);
        this.role = role;
        this.permissions = permissions;
    }

    @Override
    public void addToCart(Product product) {
        System.out.println("Admin adding product to cart: " + product.getName());
        getCart().add(product);
    }

    @Override
    public void removeFromCart(Product product) {
        System.out.println("Admin removing product from cart: " + product.getName());
        getCart().remove(product);
    }

    @Override
    public void checkout() {
    }
}

class Order {
    private List<Product> products;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;

    public Order(List<Product> products, double totalAmount, String shippingAddress, String paymentMethod) {
        this.products = products;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

class UserAuthentication {
    private Map<String, String> credentials;

    public UserAuthentication() {
        
        //HashMap in Java is a data structure that implements the Map interface, 
        //providing key-value mappings. It stores elements as key-value pairs, where each key is unique.
        credentials = new HashMap<>();
        credentials.put("admin", "adminpassword");
    }

    public boolean authenticate(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}




public class Main {
    
    private static void addProduct(List<Product> products, Scanner scanner) {
        System.out.println("Enter product type (1. Electronics / 2. Clothing): ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter product ID: ");
        String productId = scanner.nextLine();

        System.out.println("Enter product name: ");
        String name = scanner.nextLine();

        System.out.println("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter product brand: ");
        String brand = scanner.nextLine();

        if (typeChoice == 1) {
            System.out.println("Enter warranty period (in months): ");
            int warrantyPeriod = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            System.out.println("Enter power supply: ");
            String powerSupply = scanner.nextLine();

            products.add(new Electronics(productId, name, price, brand, warrantyPeriod, powerSupply));
            System.out.println("Electronics product added successfully.");
        } else if (typeChoice == 2) {
            System.out.println("Enter size: ");
            String size = scanner.nextLine();

            System.out.println("Enter color: ");
            String color = scanner.nextLine();

            products.add(new Clothing(productId, name, price, brand, size, color));
            System.out.println("Clothing product added successfully.");
        } else {
            System.out.println("Invalid product type choice.");
        }
    }

    private static void removeProduct(List<Product> products, Scanner scanner) {
        System.out.println("Enter the name of the product to remove: ");
        String productNameToRemove = scanner.nextLine();
        boolean found = false;
                                                                   //i++
        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if (product.getName().equalsIgnoreCase(productNameToRemove)) {
                iterator.remove();
                System.out.println(productNameToRemove + " removed from menu.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product not found in menu.");
        }
    }

///1 2 3 4 5 
//size =5
//0 1 2
    private static void displayProducts(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i).getName() + " - $" + products.get(i).getPrice());
        }
    }

    private static void searchProducts(List<Product> products, Scanner scanner) {
        System.out.println("Search by (1. Name / 2. Brand): ");
        int searchBy = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.println("Enter search keyword: ");
        String keyword = scanner.nextLine().toLowerCase();// Skirt /sKirt ->skirt

        List<Product> searchResults = new ArrayList<>();

        for (Product product : products) {
            if (searchBy == 1 && product.getName().toLowerCase().contains(keyword)) {
                searchResults.add(product);
            } else if (searchBy == 2 && product.getBrand().toLowerCase().contains(keyword)) {
                searchResults.add(product);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No products found matching the search criteria.");
        } else {
            System.out.println("Search Results:");
            for (Product product : searchResults) {
                product.displayDetails();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Welcome to the Online Shopping System!");
            System.out.println("Are you an Admin or a Customer? (Enter 'admin' or 'customer'): ");
            String role = scanner.nextLine().toLowerCase();
            User user = null;

            if (role.equals("admin")) {
                user = new Admin("A001", "Admin User", "admin@example.com", "Admin", "All");
            } else if (role.equals("customer")) {
                
            
                user = new Customer("C001", "Raseel Fadhi", "Raseel@example.com", "123 Street, Saudi", "Credit Card");
            } else {
                System.out.println("Invalid role selection. Please choose 'admin' or 'customer'.");
                return;
            }

            List<Product> products = new ArrayList<>();
            products.add(new Electronics("E1001", "Laptop", 1200.0, "Dell", 12, "AC Adapter"));
            products.add(new Electronics("E1002", "Smartphone", 800.0, "Samsung", 6, "Charger"));
            products.add(new Clothing("C1001", "T-Shirt", 20.0, "Nike", "M", "Blue"));
            products.add(new Clothing("C1002", "Jeans", 50.0, "Levi's", "32", "Black"));

            while (true) {
                if (user instanceof Admin) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Add Product");
                    System.out.println("2. Remove Product");
                    System.out.println("3. View Products");
                    System.out.println("4. Search Products");
                    System.out.println("0. Exit");
                    System.out.println("Enter your choice: ");
                    int adminChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    switch (adminChoice) {
                        case 1:
                            addProduct(products, scanner);
                            break;
                        case 2:
                            removeProduct(products, scanner);
                            break;
                        case 3:
                            displayProducts(products);
                            break;
                        case 4:
                            searchProducts(products, scanner);
                            break;
                        case 0:
                            System.out.println("Exiting Admin Menu.");
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } else {
                    System.out.println("\nAvailable Products:");
                    displayProducts(products);
                    System.out.println("0. Exit");
                    System.out.println("Enter product number to add to cart (0 to exit): ");
                    int choice = scanner.nextInt();
                    if (choice == 0) {
                        break;
                    } else if (choice < 1 || choice > products.size()) {
                        System.out.println("Invalid choice. Please select again.");
                        continue;
                    }
                    Product selectedProduct = products.get(choice - 1);
                    ((Customer) user).addToCart(selectedProduct);
                   
                }
            }

            if (user instanceof Customer) {
                ((Customer) user).checkout();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid value.");
            scanner.nextLine(); // Consume the invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
}