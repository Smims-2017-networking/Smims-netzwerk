package smims.networking.messages;

import static smims.networking.model.Protokoll.*;

import java.util.function.Consumer;

import Client.Client;
import smims.networking.model.*;
import smims.networking.model.Position.StartingPositionBuilder;

public class RemoteGame extends Client implements IGame {

	public RemoteGame(String serverIp, int serverPort) {
		super(serverIp, serverPort);
	}

	@Override
	public void rollDice(int playerID) throws NotYourTurnException, MoveNotAllowedException {
		sendMessage(CS_RollDice);
		throw new UnsupportedOperationException();
	}

	@Override
	public int getDiceResult() {
		sendMessage(CS_GetDiceResult);
		throw new UnsupportedOperationException();
	}

	@Override
	public IBoard getBoard() {
		sendMessage(CS_GetBoard);
		throw new UnsupportedOperationException();
	}

	@Override
	public TurnState getCurrentTurnState() {
		sendMessage(CS_GetTurnState);
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveCharacter(int playerID, Position characterPos)
			throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException {
		String sectionString;
		String distanceString;
		if (characterPos.isInBase()) {
			sectionString = Base;
			distanceString = "0";
		} else if (characterPos.isInHouse()) {
			sectionString = House;
			distanceString = characterPos.getHouseFieldNumber().get().toString();
		} else if (characterPos.isAtStartingPosition()){
			sectionString = Starting;
			distanceString = "0";
		} else {
			sectionString = Board;
			distanceString = characterPos.getFieldNumber().get().toString();
		}
 		
		sendMessage(CS_MoveCharacter+Splitter+sectionString+Splitter+distanceString);
		throw new UnsupportedOperationException();

	}

	@Override
	public int whoseTurn() {
		sendMessage(CS_WhoseTurn);
		throw new UnsupportedOperationException();
	}

	@Override
	public IPlayer getWinner() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRemainingRolls() {
		sendMessage(CS_GetThrowsLeft);
		throw new UnsupportedOperationException();
	}

	@Override
	public StartingPositionBuilder getStartingPositionBuilder(int playerId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void processMessage(String pMessage) {
		throw new UnsupportedOperationException();
		
	}
	
	private void sendMessage(String message) {
		send(message);
	}

	@Override
	public void registerTurnChangedCallback(Consumer<IPlayer> callback) {
		throw new UnsupportedOperationException();
		
	}

}
