package com.example.producer;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

@Import(ReservationHttpConfiguration.class)
@SpringBootTest(properties = "server.port=0",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BaseClass {
    @MockBean
    private ReservationRepository repository;

    @LocalServerPort
    private int port;

    @Before
    public void before() {
        RestAssured.baseURI = "http://localhost:"+this.port;
        Mockito
                .when(repository.findAll())
                .thenReturn(Flux.just(new Reservation("1", "Jane")));


    }
}