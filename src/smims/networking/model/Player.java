package smims.networking.model;

public class Player {
	private boolean wantsToStartGame = false;

	int PlayerID;
	
	Player(int pPlayerID)
	{
		this.PlayerID = pPlayerID;
	}
	
	public void makePlayerWantToStartGame() {
		wantsToStartGame = true;
	}
	
	public int getPlayerId() {
		
		return 0;
	}

	public int charactersInBase() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void moveCharacters(int x) {
		// TODO Auto-generated method stub
		
	}

	public int numberOfMovableCharacters() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean wantsToStartGame() {
		// TODO Auto-generated method stub
		return false;
	}

	public void greet() {
		// TODO Auto-generated method stub
		
	}

	public int chooseCharacter() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void signOff() {
		// TODO Auto-generated method stub
		
	}	
}
