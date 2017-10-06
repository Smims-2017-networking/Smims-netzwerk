package smims.networking.messages;

import java.lang.reflect.Type;

import com.google.gson.*;

import smims.networking.model.*;
import smims.networking.model.Character;
import smims.networking.model.Position;
import smims.networking.model.Player;

public class CharacterDeserializer implements JsonDeserializer<Character> {

	@Override
	public Character deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext)
			throws JsonParseException {
		Position pos = jsonContext.deserialize(json.getAsJsonObject().get("myPos"), Position.class);
		Player myPlayer = jsonContext.deserialize(json.getAsJsonObject().get("player"), Player.class);
		
		Character tempCharacter = new Character(myPlayer,
				pos.getBoardDescriptor(),
				pos.getStartingPosition());
		
		tempCharacter.setPosition(pos);
		
		return tempCharacter;
	}

}
