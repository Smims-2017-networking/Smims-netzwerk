package smims.networking.model;

public interface ICharacter extends IReadonlyCharacter {
	void setPosition(Position p);

	void moveIntoHouse(int possibleNewDistanz);

	void move(int possibleNewPosition, int possibelNewDistanz);

	void moveOutOfBase(int characterSpawnPosition);

	void werdeGeschlagen();
}
