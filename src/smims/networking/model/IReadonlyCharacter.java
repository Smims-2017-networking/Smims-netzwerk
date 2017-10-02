package smims.networking.model;

public interface IReadonlyCharacter {
	Position getPosition();
	Player getPlayer();
	boolean isOnField(int pFieldNumber);
	boolean isAtStartingPosition();
	boolean isInBase();
}
