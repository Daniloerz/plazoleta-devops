package com.plazoletapowerUp.infrastructure.configuration;

import com.plazoletapowerUp.domain.api.IPedidosServicePort;
import com.plazoletapowerUp.domain.api.IPlatosServicePort;
import com.plazoletapowerUp.domain.api.IRestauranteEmpleadoServicePort;
import com.plazoletapowerUp.domain.api.IRestauranteServicePort;
import com.plazoletapowerUp.domain.client.ITwilioClientPort;
import com.plazoletapowerUp.domain.client.IUsuarioClientPort;
import com.plazoletapowerUp.domain.spi.*;
import com.plazoletapowerUp.domain.usecase.PedidosUseCase;
import com.plazoletapowerUp.domain.usecase.PlatosUseCase;
import com.plazoletapowerUp.domain.usecase.RestauranteEmpleadoUseCase;
import com.plazoletapowerUp.domain.usecase.RestauranteUseCase;
import com.plazoletapowerUp.infrastructure.out.feing.adapter.TwilioClientAdapter;
import com.plazoletapowerUp.infrastructure.out.feing.adapter.UsuarioClientAdapter;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IMessageRequestMapper;
import com.plazoletapowerUp.infrastructure.out.feing.mapper.IUsuarioResponseMapper;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.ITwilioRestClient;
import com.plazoletapowerUp.infrastructure.out.feing.restClient.IUsuarioRestClient;
import com.plazoletapowerUp.infrastructure.out.jpa.adapter.*;
import com.plazoletapowerUp.infrastructure.out.jpa.mapper.*;
import com.plazoletapowerUp.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatosRepository platosRepository;
    private final IPlatosEntityMapper platosEntityMapper;
    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;
    private final IPedidosRepository pedidosRepository;
    private final IPedidosPlatosRepository pedidosPlatosRepository;
    private final IUsuarioRestClient usuarioRestClient;
    private final IUsuarioResponseMapper usuarioResponseMapper;
    private final IRestauranteEmpleadoRepository restauranteEmpleadoRepository;
    private final IRestauranteEmpleadoEntityMapper restauranteEmpleadoEntityMapper;
    private final IPedidosEntityMapper pedidosEntityMapper;
    private final ITwilioRestClient twilioRestClient;
    private final IMessageRequestMapper messageRequestMapper;


    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(restauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IUsuarioClientPort usuarioClientPort() {
        return new UsuarioClientAdapter(usuarioRestClient, usuarioResponseMapper);
    }

    @Bean
    public ITwilioClientPort twilioClientPort() {
        return new TwilioClientAdapter(twilioRestClient, messageRequestMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort(), usuarioClientPort());
    }

    @Bean
    public IPlatosPersistencePort platosPersistencePort() {
        return new PlatosJpaAdapter(platosRepository, platosEntityMapper);
    }

    @Bean
    public ICategoriaPersistencePort categoriaPersistencePort() {
        return new CategoriaJpaAdapter(categoriaRepository, categoriaEntityMapper);
    }

    @Bean
    public IPlatosServicePort platosServicePort() {
        return new PlatosUseCase(platosPersistencePort(),
                categoriaPersistencePort(),
                restaurantePersistencePort(), usuarioClientPort());
    }

    @Bean
    public IPedidosPersistencePort pedidosPersistencePort() {
        return new PedidosJpaAdapter(pedidosRepository,
                pedidosPlatosRepository, platosRepository, pedidosEntityMapper);
    }

    @Bean
    public IPedidosServicePort pedidosServicePort() {

        return new PedidosUseCase(pedidosPersistencePort(),
                restaurantePersistencePort(), restauranteEmpleadoPersistencePort(),
                twilioClientPort(), usuarioClientPort(), platosPersistencePort());
    }

    @Bean
    public IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort() {
        return new RestauranteEmpleadoJpaAdapter(restauranteEmpleadoEntityMapper,
                restauranteEmpleadoRepository);
    }

    @Bean
    public IRestauranteEmpleadoServicePort restauranteEmpleadoServicePort() {

        return new RestauranteEmpleadoUseCase(restauranteEmpleadoPersistencePort(),
                usuarioClientPort(), restaurantePersistencePort());
    }

}