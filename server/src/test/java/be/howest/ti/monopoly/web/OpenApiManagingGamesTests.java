package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.implementation.Tile;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class OpenApiManagingGamesTests extends OpenApiTestsBase {

    @Test
    void getGames(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<Game> getGames() {
                return List.of(new Game("group39", 10,
                        List.of(new Tile("Baltic", 10, "street")),
                        0));
            }
        });
        get(
                testContext,
                "/games",
                null,
                response -> {
                    assertEquals(1, response.bodyAsJsonArray().size());
                }
        );
    }

    @Test
    void getGamesWithAllParams(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted) {
                return List.of(new Game("group39", 10,
                        List.of(new Tile("Baltic", 10, "street")),
                        0));
            }
        });
        get(
                testContext,
                "/games?started=true&prefix=azerty&numberOfPlayers=3",
                null,
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getGamesWithInvalidStartedType(final VertxTestContext testContext) {
        get(
                testContext,
                "/games?started=not-a-boolean",
                null,
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void getGamesWithInvalidNumberType(final VertxTestContext testContext) {
        get(
                testContext,
                "/games?numberOfPlayers=not-a-number",
                null,
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGameWithEmptyBody(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject(),
                response -> {
                    assertEquals(403, response.statusCode());
                });
    }

    @Test
    void createGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<Game> createGame(String prefix, int numberOfPlayers) {
                return List.of(new Game("group39", 10,
                        List.of(new Tile("Baltic", 10, "street")),
                        0));
            }
        });
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("prefix", "group39")
                        .put("numberOfPlayers", 10),
                response -> {
                    assertTrue(response.bodyAsJsonArray()
                            .getJsonObject(0)
                            .getString("id")
                            .matches("group39_.*"));
                    System.out.println(response.bodyAsJsonArray().getJsonObject(0).getString("gameId"));
                    assertEquals(10,
                            (response.bodyAsJsonArray().getJsonObject(0).getInteger("maxPlayers"))
                    );
                }
        );
    }

    @Test
    void createGamePrefixTooLong(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("prefix", "aaaaaaaaaaa"),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGameInvalidSymbol(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("prefix", "a-a"), // spaces, ...
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGameTooManyPlayers(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("numberOfPlayers", 11),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGameTooFewPlayers(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("numberOfPlayers", 1),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGamePlayersNotANumber(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                new JsonObject()
                        .put("numberOfPlayers", "two"),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void createGameWithoutBody(final VertxTestContext testContext) {
        post(
                testContext,
                "/games",
                null,
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void joinGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public String joinGame(String playerName, String gameId) {
                return "Test";
            }
        });
        post(
                testContext,
                "/games/game-id/players",
                null,
                new JsonObject()
                        .put("playerName", "Alice"),
                response -> assertOkResponse(response)
                //TODO: Create a proper test
        );
    }

    @Test
    void joinGamePlayerNameTooLong(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players",
                null,
                new JsonObject()
                        .put("playerName", "aaaaaaaaaaaaaaaa"),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void joinGamePlayerNameTooShort(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players",
                null,
                new JsonObject()
                        .put("playerName", ""),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void joinGamePlayerNameInvalidPatterns(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players",
                null,
                new JsonObject()
                        .put("playerName", "a-a"), // spaces, ...
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void joinGameWithoutBody(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players",
                null,
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void joinGameWithEmptyBody(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players",
                null,
                new JsonObject(),
                response -> assertErrorResponse(response, 400)
        );
    }

    @Test
    void clearGameList(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games",
                "some-token",
                response -> assertNotYetImplemented(response, "clearGameList")
        );
    }

    @Test
    void clearGameListUnauthorized(final VertxTestContext testContext) {
        delete(
                testContext,
                "/games",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

}