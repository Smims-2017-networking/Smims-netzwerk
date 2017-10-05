package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import smims.networking.model.Position.StartingPositionBuilder;

public class Board implements IBoard {
	private final BoardDescriptor boardDescriptor;
	private final int boardSectionSize;
	private final Collection<Character> charactersOnBoard;

	/**
	 * 
	 * @param players
	 * @deprecated Use
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
		return charactersOnBoard.stream().filter((character) -> character.getPosition().equals(givenPosition)).findAny()
				.orElse(null);
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
		if (distance == 6 && !pCharacter.isInBase()
				&& getCharacterAt(pCharacter.getPosition().resetToStartingPosition()) == null && charactersOnBoard.stream()
					.anyMatch((character) -> character.getPlayer().getPlayerId() == pCharacter.getPlayer().getPlayerId() && character.isInBase()))
			throw new MoveNotAllowedException();
		else {
			Position possibleNewDistance = pCharacter.getPosition().movedBy(distance)
					.orElseThrow(() -> new MoveNotAllowedException());
			Optional<Character> characterAtTarget = getAllCharacters().stream()
					.filter(c -> c.getPosition().equals(possibleNewDistance)).findAny();
			characterAtTarget.ifPresent(c -> c.werdeGeschlagen());
			pCharacter.moveForward(distance);
		}
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
		Map<IPlayer, List<Character>> charactersByPlayer = getAllCharacters().stream()
				.collect(Collectors.groupingBy(Character::getPlayer));

		return charactersByPlayer.keySet().stream().filter((player) -> allCharactersInHouse(player.getPlayerId()))
				.findAny().orElse(null);
	}

	@Override
	public StartingPositionBuilder getStartingPositionBuilderFor(int playerId) {
		return Position.on(boardDescriptor).startingAt(getStartingPosition(playerId));
	}

}
