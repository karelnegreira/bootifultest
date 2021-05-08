package com.example.consumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8080)
public class ConsumerApplicationTests {

	@Autowired
	private ReservationClient client;

	@Test
	public void contextLoads() {

		var json = "[ {  \"id\": \"1\"  ,  \"reservationName\": \"Jane\"  }  ]";

		WireMock
				.stubFor(WireMock
						.get(WireMock
								.urlEqualTo("/reservations"))
				.willReturn(WireMock
						.aResponse()
						.withBody(json)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
						.withStatus(HttpStatus.OK.value())));

		Flux<Reservation> reservations = client.getAllReservations();

		StepVerifier
				.create(reservations)
				.expectNextMatches(r -> r.getId() != null && r.getReservationName().equalsIgnoreCase("Jane"))
				.verifyComplete();
	}

}
