package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OpenApiTurnManagementTests extends OpenApiTestsBase {

    @Test
    void rollDice(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public Game rollDice(String playerName, String gameId) {
                return new Game("test", 2, Collections.emptyList(),1);
            }
        });
        post(
                testContext,
                "/games/game-id/players/Alice/dice",
                "some-token",
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("test_1", response.bodyAsJsonObject().getString("id"));
                }
        );
    }

    @Test
    void rollDiceUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/dice",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void declareBankruptcy(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public String bankruptPlayer(String gameId, String playerName) {
                return "player has been bankrupt";
            }
        });
        post(
                testContext,
                "/games/game-id/players/Alice/bankruptcy",
                "some-token",
                response -> {
                    assertEquals(200, response.statusCode());
                }
        );
    }

    @Test
    void declareBankruptcyUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/bankruptcy",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
