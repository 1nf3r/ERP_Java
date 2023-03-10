package com.copernic.cat.erp.sales_4_us.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="id")
    private long userId;

    @NotEmpty
    @Column(nullable = false, length = 20)
    private String firstName;

    @NotEmpty
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotEmpty
    @Column(nullable = false, length = 9)
    private String dni;

    @NotEmpty
    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, length = 9)
    private int phone;

    @NotEmpty
    @Column(nullable = false, length = 200)
    private String password;

    @NotEmpty
    @Column(nullable = false, length = 60)
    private String address;

    @NotEmpty
    @Column(nullable = false, length = 10)
    String rol;

    @Column(length = 64)
    private String image;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Override
    public String toString(){
        return userId + " " + email +" " + password;
    }

}