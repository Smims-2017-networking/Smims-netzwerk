package smims.networking.messages;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import smims.networking.model.Board;
import smims.networking.model.BoardDescriptor;
import smims.networking.model.Character;

public class BoardDeserializer implements JsonDeserializer<Board>{

	@Override
	public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext) throws JsonParseException {
		Collection<smims.networking.model.Character> charsOnBoard = new ArrayList<smims.networking.model.Character>();
		JsonArray asJsonArray = json.getAsJsonObject().get("charactersOnBoard").getAsJsonArray();
		for(JsonElement jsonE : asJsonArray) {
			charsOnBoard.add(jsonContext.deserialize(jsonE.getAsJsonObject(), Character.class));
		}
		return new Board(
				jsonContext.deserialize(json.getAsJsonObject().get("boardDescriptor"), BoardDescriptor.class),
				charsOnBoard,
				json.getAsJsonObject().get("boardSectionSize").getAsInt());
	}
}
