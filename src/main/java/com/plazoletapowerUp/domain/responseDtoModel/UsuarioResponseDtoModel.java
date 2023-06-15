package com.plazoletapowerUp.domain.responseDtoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDtoModel {
    private Integer id;
    private String nombre;
    private String apellido;
    private String documento;
    private String celular;
    private String correo;
    private RoleResponseDtoModel Role;
}

