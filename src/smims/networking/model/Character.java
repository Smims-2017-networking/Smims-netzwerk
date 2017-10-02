package smims.networking.model;

public class Character implements ICharacter {
	
	private Position position = new Position();
	private final Player player;
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}
	
	@Override
	public boolean isOnField(int pFieldNumber) {
		return getPosition().isOnField(pFieldNumber);
	}
	
	@Override
	public boolean isAtStartingPosition() {
		return getPosition().isAtStartingPosition();
	}
	
	@Override
	public void moveIntoHouse(int possibleNewDistanz) {
		getPosition().moveIntoHouse(possibleNewDistanz);
	}
	
	@Override
	public void move(int possibleNewPosition, int possibleNewDistanz) {
		getPosition().move(possibleNewPosition, possibleNewDistanz);
	}
	
	@Override
	public void moveOutOfBase(int characterSpawnPosition) {
		getPosition().moveOutOfBase(characterSpawnPosition);
	}
	
	@Override
	public void werdeGeschlagen() 
	{
		getPosition().werdeGeschlagen();
	}
	
	@Override
	public boolean isInBase() {
		return getPosition().getStatus() == CharacterStatus.BASE;
	}
	
	/*public boolean canMove() {
		boolean re=true;
		if(position==0) {
			re=false;
		}
		return re;
	}*/
}
