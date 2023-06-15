package com.plazoletapowerUp.application.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatosRequestPriceDto {
    @JsonIgnore
    private Integer id;
    private Integer precio;

}

