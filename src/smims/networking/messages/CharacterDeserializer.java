package smims.networking.messages;

import java.lang.reflect.Type;

import com.google.gson.*;

import smims.networking.model.*;
import smims.networking.model.Character;

public class CharacterDeserializer implements JsonDeserializer<Character> {

	@Override
	public Character deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext)
			throws JsonParseException {
		Position pos = jsonContext.deserialize(json.getAsJsonObject().get("position"), Position.class);
		return new Character(jsonContext.deserialize(json.getAsJsonObject().get("player"), Player.class),
				pos.getBoardDescriptor(),
				pos.getStartingPosition());
	}

}
