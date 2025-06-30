package com.purrComplexity.TrabajoYa.User.dto;

import com.purrComplexity.TrabajoYa.User.Role;

public class SignupRequest {
    private String username;
    private String password;
    private Role role;
    private String correo;

    public SignupRequest() {
    }

    public SignupRequest(String username, String password, Role role, String correo) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
