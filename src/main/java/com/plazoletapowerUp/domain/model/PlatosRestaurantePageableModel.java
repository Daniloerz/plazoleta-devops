package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatosRestaurantePageableModel {

    private Integer pagesAmount;
    List<PlatoRestauranteModel> platoRestauranteModelList;


}
