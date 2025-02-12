package br.com.projeto.ecommerce.enums;

public enum StatusUser {
    ACTIVE(1L, "active"),
    DISABLED(2L, "disabled"),
    BLOCKED(3L, "blocked");

    private final Long id;
    private final String descricao;

    StatusUser(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

}
