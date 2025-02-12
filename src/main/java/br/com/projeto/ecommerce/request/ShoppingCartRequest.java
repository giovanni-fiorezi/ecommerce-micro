package br.com.projeto.ecommerce.request;

import br.com.projeto.ecommerce.models.ShoppingCart;
import br.com.projeto.ecommerce.models.User;

public class ShoppingCartRequest {

    private ShoppingCart shoppingCart;
    private User user;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
