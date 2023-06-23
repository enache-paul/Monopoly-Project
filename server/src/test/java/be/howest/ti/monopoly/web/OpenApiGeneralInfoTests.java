package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Chance;
import be.howest.ti.monopoly.logic.implementation.Tile;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OpenApiGeneralInfoTests extends OpenApiTestsBase {

    @Test
    void getInfo(final VertxTestContext testContext) {
        String versionForTest = "test-version";
        service.setDelegate(new ServiceAdapter() {
            @Override
            public String getVersion() {
                return versionForTest;
            }
        });

        get(
                testContext,
                "/",
                null,
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("monopoly", response.bodyAsJsonObject().getString("name"));
                    assertEquals(versionForTest, response.bodyAsJsonObject().getString("version"));
                }
        );
    }

    @Test
    void getTiles(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<Tile> getTiles() {
                return Collections.emptyList();
            }
        });
        get(
                testContext,
                "/tiles",
                null,
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getTileByName(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public Tile getTile(String name) {
                return new Tile("Test", 0, "street");
            }
        });
        get(
                testContext,
                "/tiles/Test",
                null,
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("Test", response.bodyAsJsonObject().getString("name"));
                }
        );
    }

    @Test
    void getTileById(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public Tile getTile(int position) {
                return new Tile("Test", 0, "street");
            }
        });
        get(
                testContext,
                "/tiles/0",
                null,
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals(0, response.bodyAsJsonObject().getInteger("position"));
                }
        );
    }


    @Test
    void getChance(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<String> getChance() {
                return List.of(
                        "Advance to Boardwalk",
                        "Advance to Go (Collect $200)"
                );
            }
        });
        get(
                testContext,
                "/chance",
                null,
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("Advance to Boardwalk", response.bodyAsJsonArray().getString(0));
                }
        );
    }


    @Test
    void getCommunityChest(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter(){
            @Override
            public List<String> getCommunityChestCard() {
                return List.of(
                        "Advance to Go (Collect $200)",
                        "Bank error in your favor, collect $200",
                        "Doctor's fee. Pay $50",
                        "From sale of stock you get $50",
                        "Get out of Jail Free",
                        "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
                        "Holiday fund matures. Collect $20",
                        "Income tax refund. Collect $200",
                        "It is your birthday. Collect $10 from each player",
                        "Life insurance matures. Collect $100",
                        "Pay hospital fees of $100",
                        "Pay school fees of $50",
                        "Receive $25 consultancy fee",
                        "You are assessed for street repair. $40 per house. $115 per hotel",
                        "You have won second prize in a beauty contest. Collect $10",
                        "You inherit $100"
                );
            }
        });
        get(
                testContext,
                "/community-chest",
                null,
                response -> {
                    assertEquals(200, response.statusCode());
                    assertEquals("Advance to Go (Collect $200)", response.bodyAsJsonArray().getString(0));
                }
        );
    }

}
