package smims.networking.messages;

import java.lang.reflect.Type;

import com.google.gson.*;

import smims.networking.model.Player;

public class PlayerDeserializer implements JsonDeserializer<Player>{

	@Override
	public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext) throws JsonParseException {
		Player player = new Player(json.getAsJsonObject().get("playerId").getAsInt());
		if (json.getAsJsonObject().get("wantsToStartGame").getAsBoolean()) {
			player.makePlayerWantToStartGame();
		}
		return player;
	}
	
}
