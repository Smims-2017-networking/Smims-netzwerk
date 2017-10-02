package smims.networking.model;

public class Position {
	private CharacterStatus status;
	private int index;
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
		index = 0;
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
