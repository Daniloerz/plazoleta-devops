package com.plazoletapowerUp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantePageableModel {

    private Integer pagesAmount;
    private Integer pageSize;
    private List<RestauranteModel> restauranteModelList;

}
