package br.com.euliro.livraria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordUpdateDTO {

    @NotBlank(message = "A senha antiga é obrigatória")
    private String oldPassword;

    @NotBlank
    @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres")
    private String newPassword;

    public PasswordUpdateDTO(){
    }   
    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}