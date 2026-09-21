package com.odontologia.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private String token;
    private String tipo;
    private String username;
    private String rol;

    public JwtResponse(String token, String username, String rol) {
        this.token = token;
        this.tipo = "Bearer";
        this.username = username;
        this.rol = rol;
    }
}
