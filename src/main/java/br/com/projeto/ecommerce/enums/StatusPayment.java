package br.com.projeto.ecommerce.enums;

public enum StatusPayment {
    CANCEL("cancel"),
    PENDING("pending"),
    FAILED("failed"),
    PAID("paid");

    private final String description;

    StatusPayment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
