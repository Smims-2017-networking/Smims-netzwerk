package smims.networking.messages;

import java.lang.reflect.*;
import java.util.Collection;

import com.google.gson.*;

import smims.networking.model.Board;
import smims.networking.model.BoardDescriptor;

public class BoardDeserializer implements JsonDeserializer<Board>{

	@Override
	public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext) throws JsonParseException {
		return new Board(
				jsonContext.deserialize(json.getAsJsonObject().get("boardDescriptor"), BoardDescriptor.class),
				jsonContext.deserialize(json.getAsJsonObject().get("charactersOnBoard"), Collection.class),
				json.getAsJsonObject().get("boardSectionSize").getAsInt());
	}
}
