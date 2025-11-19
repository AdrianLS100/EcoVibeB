package com.upc.ecovibeb.entities;

import com.upc.ecovibeb.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID; // (Para el código de invitación)

@Entity
@Table(name = "familias")
@Getter
@Setter
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 10)
    private String codigoInvitacion;

    @OneToOne
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id")
    private User admin;

    @OneToMany(mappedBy = "familia")
    private List<User> miembros;

    public Familia() {
        this.codigoInvitacion = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}