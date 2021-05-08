package com.example.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PersistsPojoTest {

    @Autowired
    private ReservationRepository repository;

    @Test
    public void create() throws Exception {
        Reservation res = new Reservation(null, "Jane");
        Mono<Reservation> save = repository.save(res);
        StepVerifier.create(save)
                .expectNextMatches(r -> r.getId() != null && r.getName() == "Jane")
                .verifyComplete();
    }
}
