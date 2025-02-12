package br.com.projeto.ecommerce.resource;

import br.com.projeto.ecommerce.models.ShoppingCart;
import br.com.projeto.ecommerce.models.User;
import br.com.projeto.ecommerce.request.ShoppingCartRequest;
import br.com.projeto.ecommerce.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartResource {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createShopping(@RequestBody ShoppingCartRequest request) {
        ShoppingCart sc = shoppingCartService.createCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(sc);
    }
}
