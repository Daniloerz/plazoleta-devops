package com.plazoletapowerUp.infrastructure.out.jpa.repository;

import com.plazoletapowerUp.infrastructure.out.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity,Integer> {

}
