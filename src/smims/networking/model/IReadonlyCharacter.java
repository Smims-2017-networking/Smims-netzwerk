package smims.networking.model;

public interface IReadonlyCharacter {
	Position getCurrentPosition();
	Player getPlayer();
	boolean isOnField(int pFieldNumber);
	boolean isAtStartingPosition();
	boolean isInBase();
}
