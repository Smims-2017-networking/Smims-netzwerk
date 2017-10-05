package smims.networking.messages;

import java.lang.reflect.Type;

import com.google.gson.*;

import smims.networking.model.BoardDescriptor;
import smims.networking.model.Position;
import smims.networking.model.Position.StartingPositionBuilder;

public class PositionDeserializer implements JsonDeserializer<Position> {

	@Override
	public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext)
			throws JsonParseException {
		BoardDescriptor boardDesc = jsonContext.deserialize(json.getAsJsonObject().get("boardDescriptor"), BoardDescriptor.class);
		StartingPositionBuilder spb = Position
				.on(boardDesc)
				.startingAt(json.getAsJsonObject().get("startingPosition").getAsInt());
		
		int pos = json.getAsJsonObject().get("startingPosition").getAsInt();
		if (pos == -1) {
			return spb.inBase();
		} else if (pos > boardDesc.getBoardSize()) {
			return spb.atHousePosition(pos - boardDesc.getBoardSize());
		} else {
			return spb.atPosition(pos);
		}
	}

}
