package smims.networking.messages;

import java.lang.reflect.Type;

import com.google.gson.*;

import smims.networking.model.BoardDescriptor;

public class BoardDescriptorDeserializer implements JsonDeserializer<BoardDescriptor> {

	@Override
	public BoardDescriptor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext)
			throws JsonParseException {
		return new BoardDescriptor(json.getAsJsonObject().get("houseSize").getAsInt(),
				json.getAsJsonObject().get("boardSize").getAsInt());
	}

}
