package br.com.projeto.ecommerce.models;

import br.com.projeto.ecommerce.enums.PaymentType;
import br.com.projeto.ecommerce.enums.StatusCart;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa o carrinho de compras
 **/

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarItem> carItens = new ArrayList<>(); // itens do carrinhho

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private StatusCart statusCart;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CarItem> getCarItens() {
        return carItens;
    }

    public void setCarItens(List<CarItem> carItens) {
        this.carItens = carItens;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StatusCart getStatusCart() {
        return statusCart;
    }

    public void setStatusCart(StatusCart statusCart) {
        this.statusCart = statusCart;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
