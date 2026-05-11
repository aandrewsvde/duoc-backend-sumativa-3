package com.duoc.LearningPlatform.model.dto;

import com.duoc.LearningPlatform.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO para actualización parcial de usuario.
 * Todos los campos son opcionales: solo se actualizan los que lleguen con valor.
 */
public class UserUpdateDTO {

    private String name;

    @Email(message = "Formato de email inválido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private Role role;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
