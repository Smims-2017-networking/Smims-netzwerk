package smims.networking.model;
import smims.networking.model.*;

public class Character  {
	
	private Position myPos;		//distance == -1, Wenn der Character in der Basis ist. Danach wird die gelaufende distanz gezaehlt
	
	// private Position position = new Position();
	private final Player player;
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	
	public Position getPosition() {
		return myPos;
	}



	public void setPosition(Position myPos) {
		this.myPos = myPos;
	}


	
	
	public int getDistance() {
		return myPos.getDistance();
	}

	
	public Player getPlayer() {
		return player;
	}

	private void setDistance(int pDistance) {
		myPos.setDistance(pDistance);
	}
	
	
	
	public boolean isAtStartingPosition() {
		return myPos.getDistance() == 0;
	}
	
	
	
	public void moveForward(int pWalkDistance) {
		myPos.setDistance(myPos.getDistance() + pWalkDistance);
	}
	
	
	public void moveOutOfBase() {
		myPos.setDistance(0);
	}
	
	
	public void werdeGeschlagen() 
	{
		myPos.setDistance(-1);
	}
	
	
	public boolean isInBase() {
		return myPos.getDistance() == -1;
	}
	
}
