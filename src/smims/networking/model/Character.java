package smims.networking.model;

public class Character implements ICharacter {
	
	private Position position;
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
		return getPosition().getIndex() == pFieldNumber
			&& getPosition().getStatus() == CharacterStatus.FIELD;
	}
	
	@Override
	public boolean isAtStartingPosition() {
		return getPosition().getDistanz() == 0;
	}
	
	@Override
	public void moveIntoHouse(int possibleNewDistanz) {
		getPosition().setDistanz(possibleNewDistanz);
		getPosition().setStatus(CharacterStatus.HOUSE);
		getPosition().setIndex(-1);
	}
	
	@Override
	public void move(int possibleNewPosition, int possibleNewDistanz) {
		getPosition().setDistanz(possibleNewDistanz);
		getPosition().setIndex(possibleNewPosition);
	}
	
	@Override
	public void moveOutOfBase(int characterSpawnPosition) {
		getPosition().setStatus(CharacterStatus.FIELD);
		getPosition().setDistanz(0);
		getPosition().setIndex(characterSpawnPosition);
	}
	
	@Override
	public void werdeGeschlagen() 
	{
		getPosition().setIndex(-1);
		getPosition().setStatus(CharacterStatus.BASE);
		getPosition().setDistanz(-1);
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
