package org.schar.whitelister.whitelist;

import org.json.*;

import java.util.*;

public class WhiteList {

    private final List<JSONObject> addedPlayers;
    private final List<String> failedPlayers;

    public WhiteList(Collection<String> playerNames) {
        this.addedPlayers = new ArrayList<>();
        this.failedPlayers = new ArrayList<>();

        for (String playerName : playerNames) {
            addPlayer(playerName);
        }
    }

    public void addPlayer(String playerName) {
        var playerJSON = getWhiteListReadyJSON(playerName);

        playerJSON.ifPresentOrElse(
                json -> this.addedPlayers.add(json),
                () -> this.failedPlayers.add(playerName)
        );
    }

    public List<JSONObject> getAddedPlayers() {
        return addedPlayers;
    }

    public List<String> getFailedPlayers() {
        return failedPlayers;
    }

    @Override
    public String toString() {
        return new JSONArray(addedPlayers).toString(4);
    }

    private Optional<JSONObject> getWhiteListReadyJSON(String playerName) {
        var playerJSON = UUIDProvider.getPlayerJSON(playerName);

        if (playerJSON.isPresent()) {
            String string = playerJSON.get().toString().replaceFirst("\"id\"", "\"uuid\"");
            return Optional.of(new JSONObject(string));
        }

        return Optional.empty();
    }


}
