package br.com.projeto.ecommerce.enums;

public enum StatusOrder {

    PENDING("Pedido foi criado, mas o pagamento ainda não foi confirmado."),
    PAID("Pagamento aprovado e confirmado."),
    PROCESSING("Pedido está sendo preparado para envio."),
    CANCELED("Pedido foi cancelado.");

    private final String description;

    StatusOrder(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
