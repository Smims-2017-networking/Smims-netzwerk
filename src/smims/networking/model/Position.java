package smims.networking.model;

public class Position {
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
}
