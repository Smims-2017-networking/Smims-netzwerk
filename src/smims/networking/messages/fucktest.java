package smims.networking.messages;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class fucktest {

	private class deserColl implements JsonDeserializer<Collection<Integer>>{

		@Override
		public Collection<Integer> deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	@Test
	public void test() {
		GsonBuilder gsonB = new GsonBuilder();
		Gson gson = gsonB.create();
		JsonElement gsonElement = gson.toJsonTree(Arrays.asList(new Integer[] {5,3,1,2}));
		for (JsonElement json : gsonElement.getAsJsonArray()) {
			System.out.println(json.getAsString());
		}
		assertTrue(true);
	}

}
