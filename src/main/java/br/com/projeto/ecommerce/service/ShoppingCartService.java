package br.com.projeto.ecommerce.service;

import br.com.projeto.ecommerce.enums.PaymentType;
import br.com.projeto.ecommerce.enums.StatusCart;
import br.com.projeto.ecommerce.enums.StatusUser;
import br.com.projeto.ecommerce.models.CarItem;
import br.com.projeto.ecommerce.models.ShoppingCart;
import br.com.projeto.ecommerce.models.User;
import br.com.projeto.ecommerce.repository.ShoppingCartRepository;
import br.com.projeto.ecommerce.request.ShoppingCartRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserService userService, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional(rollbackFor = Exception.class)
    public ShoppingCart createCart(ShoppingCartRequest request) {
        try {

            ShoppingCart shoppingCart = request.getShoppingCart();
            User user = userService.findById(request.getUser().getId());

            if(!StatusUser.ACTIVE.equals(user.getStatusUser())) {
                throw new RuntimeException("Usuário não está ativo, fale com um administrador!");
            }

            shoppingCart.setStatusCart(StatusCart.PROCESSING); // Sempre que for feito o pedido, o status entra em "Processing"
            shoppingCart.setUser(user);

            BigDecimal totalPrice = BigDecimal.ZERO;
            List<CarItem> carItens = shoppingCart.getCarItens();
            for (CarItem item : carItens) {
                BigDecimal valorTotalItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
                totalPrice = totalPrice.add(valorTotalItem);
            }

            if(shoppingCart.getPaymentType().equals(PaymentType.PIX)) {
                BigDecimal discount = totalPrice.multiply(new BigDecimal("0.05"));
                BigDecimal priceFinalWithDiscount = totalPrice.subtract(discount);
                shoppingCart.setTotalPrice(priceFinalWithDiscount);
            }

            shoppingCartRepository.save(shoppingCart);

            //Todo -> sempre que salvar um novo pedido, enviar um email para o cliente informando que o pedido está em Processamento

            return shoppingCart;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao efetuar compra, tente novamente mais tarde.");
        }
    }

    public List<ShoppingCart> findAllShoppings() {
        try {
            return shoppingCartRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar os pedidos.");
        }
    }

    public ShoppingCart findById(Long idShopping) {
        if (idShopping == null) {
            throw new IllegalArgumentException("O id não pode ser null!");
        }

        try {
            return shoppingCartRepository.findById(idShopping)
                    .orElseThrow(() -> new RuntimeException(String.format("Pedido com ID %d não encontrado.", idShopping)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Erro ao buscar o pedido %d.", idShopping));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteShopping(Long idShopping) {
        try {
            shoppingCartRepository.findById(idShopping).ifPresentOrElse(
                    shoppingCart ->  {
                        shoppingCartRepository.deleteById(idShopping);
                    },
                    () -> {
                        throw new RuntimeException(String.format("Carrinho de compras com ID %d não encontrado.", idShopping));
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Erro ao buscar o pedido %d.", idShopping));
        }
    }
}
