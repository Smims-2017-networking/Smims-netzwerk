package smims.networking.model;

import java.util.*;
import java.util.stream.*;

import smims.networking.model.Position.StartingPositionBuilder;

public class Board implements IBoard {
	private final BoardDescriptor boardDescriptor;
	private final int boardSectionSize;
	private final Collection<Character> charactersOnBoard;

	/**
	 * 
	 * @param players
	 * @deprecated Use Board(BoardDescriptor, Collection<Character>, int)
	 */
	@Deprecated
	public Board(Collection<Player> players, int pPlayerCount) {
		final int CharactersPerPlayer = 4;
		final int HouseSize = 4;
		final int FieldsPerPlayer = 10;
		final int BoardSize = FieldsPerPlayer * pPlayerCount;

		boardDescriptor = new BoardDescriptor(HouseSize, BoardSize);
		boardSectionSize = FieldsPerPlayer;
		charactersOnBoard = new ArrayList<Character>();
		for (Player player : players) {
			for (int j = 0; j < CharactersPerPlayer; ++j) {
				charactersOnBoard.add(new Character(player, boardDescriptor, player.getPlayerId() * boardSectionSize));
			}
		}
	}

	// Likely to be used, if we start passing around players instead of playerIds.
	@SuppressWarnings("unused")
	private int getStartingPosition(Player player) {
		return getStartingPosition(player.getPlayerId());
	}

	private int getStartingPosition(int playerId) {
		return playerId * boardSectionSize;
	}

	@Override
	public int getBoardSectionSize() {
		return boardSectionSize;
	}

	@Override
	public BoardDescriptor getBoardDescriptor() {
		return boardDescriptor;
	}

	public Board(BoardDescriptor boardDescriptor, Collection<Character> charactersOnBoard, int playerCount) {
		this.boardDescriptor = boardDescriptor;
		this.boardSectionSize = boardDescriptor.getBoardSize() / playerCount;
		this.charactersOnBoard = charactersOnBoard;
	}

	/**
	 * 
	 * @param pDistance
	 * @param pPlayer
	 * @return Es wird der Character zurueckgegeben, der bei der die gesuchte
	 *         Distanz zurueckgelegt und von dem Player ist.
	 * 
	 */
	@Override
	public Character getCharacterAt(int pDistance, int pPlayerID) {
		Position givenPosition = Position.on(boardDescriptor).startingAt(getStartingPosition(pPlayerID))
				.atPosition(pDistance);

		return getCharacterAt(givenPosition);
	}

	@Override
	public Character getCharacterAt(Position givenPosition) {
		return getCharacterAtO(givenPosition).orElse(null);
	}
	
	public Optional<Character> getCharacterAtO(Position givenPosition) {
		return charactersOnBoard.stream().filter((character) -> character.getPosition().equals(givenPosition)).findAny();
	}

	@Override
	public boolean playerHasCharactersOnBoard(IPlayer pPlayer) {
		return charactersOnBoard.stream()
				.filter((character) -> character.getPlayer() == pPlayer && character.isOnField()).count() != 0;
	}

	/**
	 * 
	 * @param pPlayerId
	 * @return true, wenn alle im Haus sind
	 */
	@Override
	public boolean allCharactersInHouse(int pPlayerId) {
		return charactersOnBoard.stream()
				.filter((character) -> character.getPlayer().getPlayerId() == pPlayerId && !character.isInHouse())
				.count() == 0; // keiner, der vor dem Haus ist
	}

	/**
	 * 
	 * @param character
	 *            Character, der bewegt werden soll
	 * @param distance
	 *            Distanz, die der Character bewegt werden soll
	 */
	@Override
	public void moveCharacter(Character pCharacter, int distance) throws MoveNotAllowedException {
		if (couldLeaveBaseButDoesnt(pCharacter, distance)
				|| couldLeaveStartingPositionButDoesnt(pCharacter, distance))
			throw new MoveNotAllowedException();
		else {
			Position possibleNewPosition = calculatePositionAfterMove(pCharacter, distance);
			Optional<Character> characterAtTarget = findAnyCharacterAtPosition(possibleNewPosition);
			if (characterAtTarget.isPresent()) {
				if (characterAtTarget.get().isOfSamePlayerAs(pCharacter)) {
					throw new MoveNotAllowedException();
				} else {
					characterAtTarget.get().werdeGeschlagen();
				}
			}
			pCharacter.moveForward(distance);
		}
	}

	private boolean couldLeaveStartingPositionButDoesnt(Character pCharacter, int distance) {
		return  anyCharacterOfSamePlayerIsInBase(pCharacter)
				&& anyCharacterOfSamePlayerIsInStartingPosition(pCharacter)
				&& canMoveByDistance(pCharacter, distance)
				&& !pCharacter.isAtStartingPosition();
	}

	private boolean anyCharacterOfSamePlayerIsInStartingPosition(Character pCharacter) {
		return getAllCharacters().stream()
				.anyMatch(c -> c.isOfSamePlayerAs(pCharacter) && c.isAtStartingPosition());
	}

	private Optional<Character> findAnyCharacterAtPosition(Position possibleNewPosition) {
		return getAllCharacters().stream()
				.filter(c -> c.isAtPosition(possibleNewPosition)).findAny();
	}

	private Position calculatePositionAfterMove(Character pCharacter, int distance) throws MoveNotAllowedException {
		return pCharacter.getPosition().movedBy(distance)
				.orElseThrow(() -> new MoveNotAllowedException());
	}

	private boolean couldLeaveBaseButDoesnt(Character characterToMove, int distance) {
		return couldMoveCharacterOutOfBase(characterToMove, distance)
				&& anyCharacterOfSamePlayerIsInBase(characterToMove)
				&& !startingPositionIsOccupiedBySamePlayer(characterToMove)
				&& !characterToMove.isInBase();
	}

	private boolean anyCharacterOfSamePlayerIsInBase(Character pCharacter) {
		return charactersOnBoard.stream()
			.anyMatch((character) -> character.isOfSamePlayerAs(pCharacter) && character.isInBase());
	}

	private boolean startingPositionIsOccupiedBySamePlayer(Character pCharacter) {
		return positionIsOccupiedByTeammateOf(pCharacter, pCharacter.getPosition().resetToStartingPosition());
	}

	private boolean couldMoveCharacterOutOfBase(Character pCharacter, int distance) {
		return distance == 6 && !pCharacter.isInBase();
	}

	/**
	 * returns all Characters in an ArrayList<IReadonlyCharacter>
	 */
	@Override
	public Collection<Character> getAllCharacters() {
		Collection<Character> characters = new ArrayList<Character>();
		characters.addAll(charactersOnBoard);
		return characters;
	}

	@Override
	public IPlayer getWinner() {
		// HACK This is a pretty ugly way of checking which house is full; I
		// think modeling each house would be better.
		Map<IPlayer, java.util.List<Character>> charactersByPlayer = getAllCharacters().stream()
				.collect(Collectors.groupingBy(Character::getPlayer));

		return charactersByPlayer.keySet().stream().filter((player) -> allCharactersInHouse(player.getPlayerId()))
				.findAny().orElse(null);
	}

	@Override
	public StartingPositionBuilder getStartingPositionBuilderFor(int playerId) {
		return Position.on(boardDescriptor).startingAt(getStartingPosition(playerId));
	}

	@Override
	public boolean canMoveByDistance(Character character, int distance) {
		Optional<Position> newPosition = character.getPosition().movedBy(distance);
		return newPosition.isPresent() && !positionIsOccupiedByTeammateOf(character, newPosition.get());
	}

	private boolean positionIsOccupiedByTeammateOf(Character character, Position newPosition) {
		return getCharacterAtO(newPosition)
			.map(c -> c.isOfSamePlayerAs(character) && c != character)
			.orElse(false);
	}
}
