package com.ironhack.iron_bank_project.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
    })
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    //add an email patter
    private String email;

    @NotBlank
    @Size(max = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "primaryOwner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> myAccounts;

    @OneToMany(mappedBy = "secondaryOwner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> myAccountsAsSeconday;

    private String passwordResetQuestion;

    private String passwordResetAnswer;




    public User(){}

    public User(String name, String email, String password, Role role, UserStatus status, String passwordResetQuestion, String passwordResetAnswer){
        setName(name);
        setEmail(email);
        setPassword(password);
        setRole(role);
        setStatus(status);
        setPasswordResetQuestion(passwordResetQuestion);
        setPasswordResetAnswer(passwordResetAnswer);
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }

    public String getPasswordResetQuestion() {
        return passwordResetQuestion;
    }

    public void setPasswordResetQuestion(String passwordResetQuestion) {
        this.passwordResetQuestion = passwordResetQuestion;
    }

    public String getPasswordResetAnswer() {
        return passwordResetAnswer;
    }

    public void setPasswordResetAnswer(String passwordResetAnswer) {
        this.passwordResetAnswer = passwordResetAnswer;
    }

    public List<Account> getMyAccounts(){
        return myAccounts;
    }

    @Override
    public String toString() {
        return "User{" +
                " name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
