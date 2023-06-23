package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OpenApiBuyingStreetTests extends OpenApiTestsBase {

    @Test
    void buyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public String buyProperty(String gameId, String playerName) {
                return "This is a test.";
            }
        });
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                "some-token",
                response -> {
                    assertEquals(200, response.statusCode());
                }
        );
    }

    @Test
    void buyPropertyUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void dontBuyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public String dontBuyProperty(String gameId, String playerName) {
                return "This is a test.";
            }
        });
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                "some-token",
                response -> assertEquals(200, response.statusCode())
        );
    }

    @Test
    void dontBuyPropertyUnauthorized(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
