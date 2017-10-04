package smims.networking.model;

public class BoardDescriptor {
	private final int houseSize;
	private final int baseSize;
	private final int boardSectionSize;
	private final int boardSize;
	
	public BoardDescriptor(int houseSize, int boardSectionSize, int boardSize, int baseSize) {
		this.houseSize = houseSize;
		this.boardSectionSize = boardSectionSize;
		this.boardSize = boardSize;
		this.baseSize = baseSize;
	}
	
	public int getHouseSize() {
		return houseSize;
	}
	
	public int getBoardSectionSize() {
		return boardSectionSize;
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boardSectionSize;
		result = prime * result + boardSize;
		result = prime * result + houseSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardDescriptor other = (BoardDescriptor) obj;
		if (boardSectionSize != other.boardSectionSize)
			return false;
		if (boardSize != other.boardSize)
			return false;
		if (houseSize != other.houseSize)
			return false;
		return true;
	}
}
