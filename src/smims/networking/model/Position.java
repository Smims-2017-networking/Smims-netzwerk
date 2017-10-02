package smims.networking.model;

public class Position implements Comparable<Position> {

	private CharacterStatus status;		//Status eines Characters im Spiel: in der Base, auf dem Feld (Field) oder im Zielhaus (House)
	private int index;					//Absolute Position des Charakters auf dem Spielfeld: In der Basis und im Haus -1, auf dem Spielfeld hat das Startfeld des ersten Spielers den Wert 0, ab da wird aufwärts gezählt
	private int distanz;				//Distanz des Spielers von seinem Spawnfeld, ist auch innerhalb des Hauses gültig (Wenn das Spielfeld 40 Felder hat, startet der Spieler bei distance = 0, distance = 40 ist dann der erste Platz im Haus, 41 ist der zweite,...)
	
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
