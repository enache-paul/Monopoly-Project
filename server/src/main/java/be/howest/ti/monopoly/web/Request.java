package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.web.tokens.MonopolyUser;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;

import java.util.Objects;


/**
 * The Request class is responsible for translating information that is part of the
 * request into Java.
 *
 * For every piece of information that you need from the request, you should provide a method here.
 * You can find information in:
 * - the request path: params.pathParameter("some-param-name")
 * - the query-string: params.queryParameter("some-param-name")
 * Both return a `RequestParameter`, which can contain a string or an integer in our case.
 * The actual data can be retrieved using `getInteger()` or `getString()`, respectively.
 * You can check if it is an integer (or not) using `isNumber()`.
 *
 * Finally, some requests have a body. If present, the body will always be in the json format.
 * You can acces this body using: `params.body().getJsonObject()`.
 *
 * **TIP:** Make sure that al your methods have a unique name. For instance, there is a request
 * that consists of more than one "player name". You cannot use the method `getPlayerName()` for both,
 * you will need a second one with a different name.
 */
public class Request {

    private final RoutingContext ctx;
    private final RequestParameters params;
    private final MonopolyUser user;

    private Request(RoutingContext ctx) {
        this.ctx = ctx;
        this.params = ctx.get(ValidationHandler.REQUEST_CONTEXT_KEY);
        this.user = (MonopolyUser) ctx.user();
    }

    public static Request from(RoutingContext ctx) {
        return new Request(ctx);
    }

    public RoutingContext getRoutingContext() {
        return ctx;
    }

    public RequestParameters getRequestParameters() {
        return params;
    }

    public boolean isAuthorized(String expectedGameId, String expectedPlayerName) {
        return Objects.equals(expectedGameId, user.getGameId()) &&
                Objects.equals(expectedPlayerName, user.getPlayerName());
    }

    public boolean isTileName() {
        return params.pathParameter("tileId").isString();
    }

    public String getTileName() {
        return params.pathParameter("tileId").getString();
    }

    public int getTileIndex() {
        return params.pathParameter("tileId").getInteger();
    }

    public JsonObject getBodyJson() { return ctx.getBodyAsJson(); }

    public String getGameId() { return params.pathParameter("gameId").getString(); }

    public String getPropertyName() { return params.pathParameter("propertyName").getString(); }

    public String getPlayerName() { return params.pathParameter("playerName").getString(); }

    public int getNumberOfPlayers() { return  params.queryParameter("numberOfPlayers").getInteger();}

    public boolean getIsStarted() {return params.queryParameter("started").getBoolean();}

    public boolean isQueryEmpty() {
        try {
            params.queryParameter("numberOfPlayers").isNull();
        } catch (NullPointerException e) { return true; }

        //TODO: Find a solution instead of this monstrosity
        return false;
    }

    public String getPrefix() { return params.queryParameter("prefix").getString(); }
}
