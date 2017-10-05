package smims.networking.model;
import java.util.Optional;

public class Character  {
	
	private Position myPos;	
	private final IPlayer player;
	
	public Character(IPlayer defaultPlayer, BoardDescriptor boardDescriptor, int startingPosition) {
		this.player = defaultPlayer; 
		myPos = Position.on(boardDescriptor).startingAt(startingPosition).inBase();
	}

	public IPlayer getPlayer() {
		return player;
	}
	
	public Position getPosition() {
		return myPos;
	}

	public Optional<Integer> getFieldNumber() {
		return myPos.getFieldNumber();
	}
	
	public Optional<Integer> getHouseFieldNumber() {
		return myPos.getHouseFieldNumber();
	}
	
	
	public boolean isAtStartingPosition() {
		return myPos.isAtStartingPosition();
	}
	
	public void moveForward(int pWalkDistance) throws MoveNotAllowedException {
		myPos = myPos.movedBy(pWalkDistance).orElseThrow(() -> new MoveNotAllowedException());
	}
	
	public void werdeGeschlagen() 
	{
		myPos = myPos.resetToBase();
	}
	
	public boolean isInBase() {
		return myPos.isInBase();
	}

	public boolean isOnField() {
		return myPos.isOnField();
	}

	public boolean isInHouse() {
		return myPos.isInHouse();
	}
	
	public boolean isOfSamePlayerAs(Character other) {
		return this.player.equals(other.player);
	}
	
	public boolean isAtPosition(Position otherPosition) {
		return myPos.equals(otherPosition);
	}
	
}
