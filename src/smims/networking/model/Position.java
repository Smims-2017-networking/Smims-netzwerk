package smims.networking.model;

public class Position implements Comparable<Position> {
	
	private static final int NOT_ON_BOARD = -1;
	
	//Status eines Characters im Spiel: in der Base, auf dem Feld (Field) oder im Zielhaus (House)
	private final CharacterStatus status;
	
	//Absolute Position des Charakters auf dem Spielfeld: In der Basis und im Haus -1, auf dem Spielfeld hat das Startfeld des ersten Spielers den Wert 0, ab da wird aufwärts gezählt
	private final int index;
	
	//Distanz des Spielers von seinem Spawnfeld, ist auch innerhalb des Hauses gültig (Wenn das Spielfeld 40 Felder hat, startet der Spieler bei distance = 0, distance = 40 ist dann der erste Platz im Haus, 41 ist der zweite,...)
	private final int distanz;
	
	public int getDistanz() {
		return distanz;
	}

	public Position()
	{
		status = CharacterStatus.BASE;
		distanz = -1;
		index = NOT_ON_BOARD;
	}
	
	/**
	 * Copy constructor to support immutability.
	 * @param other The instance to clone
	 */
	public Position(Position other) {
		this(other.status, other.distanz, other.index);
	}
	
	public Position(CharacterStatus status, int distanz, int index) {
		this.status = status;
		this.distanz = distanz;
		this.index = index;
	}

	public CharacterStatus getStatus() {
		return status;
	}

	public int getIndex() {
		return index;
	}
	
	public boolean isOnField(int pFieldNumber) {
		return getIndex() == pFieldNumber
			&& getStatus() == CharacterStatus.FIELD;
	}
	
	public boolean isAtStartingPosition() {
		return getDistanz() == 0;
	}
	
	public static Position movedIntoHouse(int possibleNewDistanz) {
		return new Position(CharacterStatus.HOUSE, possibleNewDistanz, NOT_ON_BOARD);
	}
	
	public Position movedBy(int possibleNewPosition, int possibleNewDistanz) {
		return new Position(status, possibleNewDistanz, possibleNewPosition);
	}
	
	public static Position spawnedAt(int characterSpawnPosition) {
		return new Position(CharacterStatus.FIELD, 0, characterSpawnPosition);
	}
	
	public static Position thrownOut() {
		// TODO: The distance of -1 seems to be a bug, but I haven't tested this yet.
		return new Position(CharacterStatus.BASE, -1, NOT_ON_BOARD);
	}

	/**
	 * A Position is considered greater than another if it has a larger 
	 * absolute position or is in a higher status, where BASE is the lowest
	 * status and HOUSE is the highest.
	 */
	@Override
	public int compareTo(Position other) {
		int statusComparison = status.compareTo(other.status);
		return statusComparison == 0 ? index - other.index : statusComparison;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + distanz;
		result = prime * result + index;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (distanz != other.distanz) {
			return false;
		}
		if (index != other.index) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}
}
