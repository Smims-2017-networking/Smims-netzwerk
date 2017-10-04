package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board implements IBoard {
	private int PlayerCount;
	private static final int CharactersPerPlayer = 4;
	private static final int DistanceBetweenSpawns = 10;

	private final Collection<Character> charactersOnBoard;

	public Board(Collection<Player> players, int pPlayerCount) {
		PlayerCount = pPlayerCount;
		charactersOnBoard = new ArrayList<Character>();
		for (Player player: players) {
			for (int i = 0; i < CharactersPerPlayer; ++i) {
				charactersOnBoard.add(new Character(player));
			}
		}
	}

	/**
	 * Checht ob auf dem Feld ein Character steht
	 * @param pFieldNumber Nummer des zu ueberpruefenden Feldes, Durchnummeriert von dem Spawn mit der PlayerID 0 bis zu dem Feld vor dem Spawn von Player 0
	 * @return true wenn ein Character auf dem Feld ist
	 */
	@Override
	public boolean fieldOccupied(int pFieldNumber) {
		return charactersOnBoard.stream()
				.anyMatch((character) -> (character.getDistance() + character.getPlayer().getPlayerId() * DistanceBetweenSpawns) == pFieldNumber);
		
	}

	
	/**
	 * 
	 * @param pDistance   
	 * @param pPlayer
	 * @return Es wird der Character zurueckgegeben, der bei der die gesuchte Distanz zurueckgelegt und von dem Player ist.
	 */
	@Override
	public Character getCharacterAt(int pDistance, int pPlayerID) {
		return charactersOnBoard.stream()
				.filter((character) -> character.getPosition().getDistance() == pDistance && character.getPlayer().getPlayerId() == pPlayerID)
				.findAny()
				.orElse(null);
	}

	/**
	 * 
	 * @param pCharacter
	 * @param pDistance
	 * @return returns true, wenn an der Stelle die der Character nach dem bewegen
	 *         um pDistance auf dem gleichen Feld wie ein Character vom gleichen
	 *         Team ist.
	 */
	@Override
	public boolean characterWouldHitTeammate(Character pCharacter, int pDistance)
	{
		if(pCharacter.isInBase() && pDistance == 6)
		{
			return charactersOnBoard.stream()
				.anyMatch(character -> character.isAtStartingPosition() && character.getPlayer() == pCharacter.getPlayer());
		}
		else
		{
			return charactersOnBoard.stream()
				.anyMatch(character -> (pCharacter.getPosition().getDistance() + pDistance) == character.getPosition().getDistance() && character.getPlayer() == pCharacter.getPlayer());
		}
	}

	

	
	/**
	 * 
	 * @param character,
	 *            der �berpr�ft werden soll
	 * @param pDistance
	 *            Distanz, die der character sich bewegt
	 * @return true wenn er sich bewegen kann (kein Teammate im weg, Wenn in Basis
	 *         eine 6 gew�rfelt und falls er in das Haus geht ist das Haus lang
	 *         genug)
	 */
	@Override
	public boolean characterCanMove(Character character, int pDistance)
	{
		return 
				!characterWouldHitTeammate(character, pDistance)
				&& !characterWouldMoveInHouse(character, pDistance)
				&& characterCanExitBase(character, pDistance);
	}

	/**
	 * 
	 * @param character
	 * @param pDistance
	 * @return
	 */
	boolean characterWouldMoveInHouse(Character character, int pDistance) {
		return character.getPosition().getDistance() + pDistance > (DistanceBetweenSpawns * PlayerCount) + CharactersPerPlayer;
	}

	boolean characterCanEnterHouse(Character character, int pDistance) {
		return !characterWouldHitTeammate(character, pDistance) && character.getPosition().getDistance() + pDistance <= (DistanceBetweenSpawns * PlayerCount) + CharactersPerPlayer;
	}
	
	boolean characterCanExitBase(Character character, int pDistance) {
		return character.isInBase() && pDistance == 6 &&  !characterWouldHitTeammate(character, pDistance);
	}
	
	/**
	 * 
	 * @param pCharacter
	 * @return true wenn kein Character Auf dem Feld ist (bzw. Characters nur in Basis und im Haus)
	 */
	@Override
	public boolean playerHasCharactersOnBoard(IPlayer pPlayer)
	{
		return charactersOnBoard.stream()
		.filter((character) -> character.getPlayer()==pPlayer && characterInField(character))
		.count() == 0;
 	}
	
	/**
	 * 
	 * @param pCharacter
	 * @return true wenn der Character im Ziel Haus ist
	 */
	private boolean isCharacterInHouse(Character pCharacter)
	{
		return pCharacter.getPosition().getDistance() > (DistanceBetweenSpawns * PlayerCount);
	}
	
	/**
	 * 
	 * @param pCharacter
	 * @return	true wenn der Character nicht in der Basis und auch nicht im Haus ist. (im Feld)
	 */
	private boolean characterInField(Character pCharacter)
	{
		return !pCharacter.isInBase() && pCharacter.getPosition().getDistance() <= (DistanceBetweenSpawns * PlayerCount);
	}
	
	/**
	 * 
	 * @param pPlayerId
	 * @return	true, wenn alle im Haus sind
	 */
	@Override
	public boolean allCharactersInHouse(int pPlayerId) {
		return charactersOnBoard.stream()
				.filter((character) -> character.getPlayer().getPlayerId() == pPlayerId && !isCharacterInHouse(character)).count() == 0;	//keiner, der vor dem Haus ist
	}
	
	/**
	 * 
	 * @param character
	 *            Character, der bewegt werden soll
	 * @param distance
	 *            Distanz, die der Character bewegt werden soll
	 */
	@Override
	public void moveCharacter(Character pCharacter, int distance) throws MoveNotAllowedException{
		if(characterCanMove(pCharacter, distance))
		{
			int possibleNewDistance = pCharacter.getPosition().getDistance() + distance;
			if(pCharacter.isInBase())			//in Basis
			{
				pCharacter.moveOutOfBase();
			}
			else
			{
				pCharacter.moveForward(distance);
			}
			
			if(fieldOccupied(possibleNewDistance))						//Spieler schlagen, wenn vorhanden
			{
			
				charactersOnBoard.stream()
				.filter((character) -> (character.getDistance() + character.getPlayer().getPlayerId() * DistanceBetweenSpawns)% (DistanceBetweenSpawns * PlayerCount) == 
										(possibleNewDistance + character.getPlayer().getPlayerId() * DistanceBetweenSpawns) % (DistanceBetweenSpawns * PlayerCount)).findAny().get().werdeGeschlagen();
			}
		}
		else
		{
			throw new MoveNotAllowedException();
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
		
		return charactersByPlayer.keySet().stream()
				.filter((player) -> allCharactersInHouse(player.getPlayerId())				)
				.findAny()
				.orElse(null);
	}


}
