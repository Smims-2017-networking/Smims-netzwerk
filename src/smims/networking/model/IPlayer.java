package smims.networking.model;

public interface IPlayer {

	void makePlayerWantToStartGame();

	int getPlayerId();

	boolean wantsToStartGame();

}