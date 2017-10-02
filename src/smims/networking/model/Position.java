package smims.networking.model;

public class Position implements Comparable<Position> {
	//Status eines Characters im Spiel: in der Base, auf dem Feld (Field) oder im Zielhaus (House)
	private CharacterStatus status;
	
	//Absolute Position des Charakters auf dem Spielfeld: In der Basis und im Haus -1, auf dem Spielfeld hat das Startfeld des ersten Spielers den Wert 0, ab da wird aufwärts gezählt
	private int index;
	
	//Distanz des Spielers von seinem Spawnfeld, ist auch innerhalb des Hauses gültig (Wenn das Spielfeld 40 Felder hat, startet der Spieler bei distance = 0, distance = 40 ist dann der erste Platz im Haus, 41 ist der zweite,...)
	private int distanz;
	
	public int getDistanz() {
		return distanz;
	}

	public void setDistanz(int distanz) {
		this.distanz = distanz;
	}

	public Position()
	{
		status = CharacterStatus.BASE;
		distanz = 0;
		index = -1;
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

	public void setStatus(CharacterStatus status) {
		this.status = status;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isOnField(int pFieldNumber) {
		return getIndex() == pFieldNumber
			&& getStatus() == CharacterStatus.FIELD;
	}
	
	public boolean isAtStartingPosition() {
		return getDistanz() == 0;
	}
	
	public void moveIntoHouse(int possibleNewDistanz) {
		setDistanz(possibleNewDistanz);
		setStatus(CharacterStatus.HOUSE);
		setIndex(-1);
	}
	
	public void move(int possibleNewPosition, int possibleNewDistanz) {
		setDistanz(possibleNewDistanz);
		setIndex(possibleNewPosition);
	}
	
	public void moveOutOfBase(int characterSpawnPosition) {
		setStatus(CharacterStatus.FIELD);
		setDistanz(0);
		setIndex(characterSpawnPosition);
	}
	
	public void werdeGeschlagen() {
		setIndex(-1);
		setStatus(CharacterStatus.BASE);
		setDistanz(-1);
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
