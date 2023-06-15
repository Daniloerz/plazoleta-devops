package com.plazoletapowerUp.infrastructure.out.feing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {
    private Integer id;
    private String nombre;
    private String apellido;
    private String documento;
    private String celular;
    private String correo;
    private RoleResponseDto Role;
}
