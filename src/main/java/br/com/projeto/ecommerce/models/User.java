package br.com.projeto.ecommerce.models;

import br.com.projeto.ecommerce.enums.StatusUser;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="login", nullable = false)
    private String login;

    @Column(name="senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private StatusUser statusUser;

    public User() {
    }

    public User(String id, String name, String lastName, String email, String login, String senha, StatusUser statusUser) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.statusUser = statusUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusUser statusUser) {
        this.statusUser = statusUser;
    }
}
