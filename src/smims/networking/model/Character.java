package smims.networking.model;

public class Character implements ICharacter {
	
	private Position position = new Position();
	private final Player player;
	
	public Character(Player player) {
		this.player = player; 
	}
	
	
	@Override
	public Position getCurrentPosition() {
		return position;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	private void setPosition(Position p) {
		position = p;
	}
	
	@Override
	public boolean isOnField(int pFieldNumber) {
		return getCurrentPosition().isOnField(pFieldNumber);
	}
	
	@Override
	public boolean isAtStartingPosition() {
		return getCurrentPosition().isAtStartingPosition();
	}
	
	@Override
	public void moveIntoHouse(int possibleNewDistanz) {
		setPosition(Position.movedIntoHouse(possibleNewDistanz));
	}
	
	@Override
	public void move(int possibleNewPosition, int possibleNewDistanz) {
		setPosition(getCurrentPosition().movedBy(possibleNewPosition, possibleNewDistanz));
	}
	
	@Override
	public void moveOutOfBase(int characterSpawnPosition) {
		setPosition(Position.spawnedAt(characterSpawnPosition));
	}
	
	@Override
	public void werdeGeschlagen() 
	{
		setPosition(Position.thrownOut());
	}
	
	@Override
	public boolean isInBase() {
		return getCurrentPosition().getStatus() == CharacterStatus.BASE;
	}
	
	/*public boolean canMove() {
		boolean re=true;
		if(position==0) {
			re=false;
		}
		return re;
	}*/
}
