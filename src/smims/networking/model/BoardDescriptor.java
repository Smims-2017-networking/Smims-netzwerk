package smims.networking.model;

public class BoardDescriptor {
	private final int houseSize;
	private final int boardSize;
	
	public BoardDescriptor(int houseSize, int boardSize) {
		this.houseSize = houseSize;
		this.boardSize = boardSize;
	}
	
	public int getHouseSize() {
		return houseSize;
	}
		
	public int getBoardSize() {
		return boardSize;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (boardSize != other.boardSize)
			return false;
		if (houseSize != other.houseSize)
			return false;
		return true;
	}
}
