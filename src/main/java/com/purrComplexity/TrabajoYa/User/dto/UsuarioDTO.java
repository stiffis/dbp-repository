package com.purrComplexity.TrabajoYa.User.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {
    private Long id;
    private String username;
    private String nombre;
    private List<String> roles;

    public UsuarioDTO(Long id, String username, String nombre, List<String> roles) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.roles = roles;
    }

}
