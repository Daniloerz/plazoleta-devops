package com.plazoletapowerUp.infrastructure.out.jpa.adapter;

import com.plazoletapowerUp.domain.model.RestauranteModel;
import com.plazoletapowerUp.domain.model.RestaurantePageableModel;
import com.plazoletapowerUp.domain.spi.IRestaurantePersistencePort;
import com.plazoletapowerUp.infrastructure.exception.NoDataFoundException;
import com.plazoletapowerUp.infrastructure.out.jpa.entity.RestauranteEntity;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {

    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;

    @Override
    public RestauranteModel saveRestaurante(RestauranteModel restauranteModel) {
        RestauranteEntity restauranteEntity = restauranteRepository.save(restauranteEntityMapper.toEntity(restauranteModel));
        return restauranteEntityMapper.toRestauranteModel(restauranteEntity);
    }

    @Override
    public RestauranteModel findByIdPP(Integer id){
        Optional<RestauranteEntity> restauranteEntityOptional = restauranteRepository.findById(id);
        if (restauranteEntityOptional.isPresent()){
            RestauranteEntity restauranteEntity = restauranteEntityOptional.get();
            return restauranteEntityMapper.toRestauranteModel(restauranteEntity);
        } else {
            throw new NoDataFoundException("Restaurante no encontrado");
        }
    }

    @Override
    public RestaurantePageableModel findAllRestaurantesPP(Integer page) {
        Integer initPage = (page-1);
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(initPage,pageSize, Sort.by("nombre"));
        Page<RestauranteEntity> restauranteEntityPage = restauranteRepository.findAll(pageable);
        if(restauranteEntityPage.hasContent()){
            Integer pagesAmount = restauranteEntityPage.getTotalPages();
            List<RestauranteEntity> restauranteEntityList =restauranteEntityPage.getContent();
            List<RestauranteModel> restauranteModelList = restauranteEntityMapper.toRestauranteModelList(restauranteEntityList);

            return new RestaurantePageableModel(pagesAmount,
                    restauranteModelList.size(),
                    restauranteModelList);
        } else {
            throw new NoDataFoundException("Restaurantes no encontrados");
        }
    }
}