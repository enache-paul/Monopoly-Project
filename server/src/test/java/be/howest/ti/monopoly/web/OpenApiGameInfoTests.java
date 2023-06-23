package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OpenApiGameInfoTests extends OpenApiTestsBase {

    @Test
    void getGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public Game getGame(String gameId) {
                return new Game("test", 2, Collections.emptyList(), 1);
            }
        });
        get(
                testContext,
                "/games/game-id",
                "some-token",
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("test_1", response.bodyAsJsonObject().getString("id"));
                }
        );
    }

    @Test
    void getGameUnauthorized(final VertxTestContext testContext) {
        get(
                testContext,
                "/games/game-id",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void getDummyGame(final VertxTestContext testContext) {
        get(
                testContext,
                "/games/dummy",
                null,
                response -> assertNotYetImplemented(response, "getDummyGame")
        );
    }
}
