import DS.CartList;
//import java.util.LinkedList;

public class Cart {
    CartList<CartItem> items = new CartList<>();

    public void addItem(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
    }

//    public void removeItem(int productId) {
//        items.removeIf(item -> item.getProduct().getProduct_Id() == productId);
//    }

    public boolean removeItem(int productId) {
        boolean flag=true;
        for(int i=0;i<items.size();i++) {
            CartItem cartItem = items.get(i);
            if(cartItem.getProduct().getProduct_Id()==productId) {
                items.remove(cartItem);
                return true;
            }
        }
        return false;
    }


    public void viewCart() {
        System.out.println("Cart Contents:");
        for (CartItem item : items) {
            Product product = item.getProduct();
            System.out.println("Product_Id: "+product.getProduct_Id()+", Product Name: " + product.getProductName() +
                    ", Quantity: " + item.getQuantity() +
                    ", Price: " + product.getRate());
        }
        System.out.println("Total Price: " + getTotal());
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getRate() * item.getQuantity();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }

    public CartList<CartItem> getItems() {
        return items;
    }
}
