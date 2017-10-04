package smims.networking.model;

import java.util.Optional;

public class Position implements Comparable<Position> {
	private static final int IN_BASE = -1;
	private final BoardDescriptor boardDescriptor;
	private final int movedDistance;
	private final int startingPosition;
	
	public static BoardPositionBuilder on(BoardDescriptor boardDescriptor) {
		return new BoardPositionBuilder(boardDescriptor);
	}
	
	private Position(BoardDescriptor boardDescriptor, int distance, int startingPosition) {
		this.boardDescriptor = boardDescriptor;
		this.movedDistance = distance;
		this.startingPosition = startingPosition;
	}

	public String toString()
	{
		return ""+ getFieldNumber();
	}

	public Optional<Integer> getFieldNumber() {
		if (isOnField()) {
			return Optional.of(startingPosition + movedDistance);
		} else {
			return Optional.empty();
		}
	}

	public Optional<Position> movedBy(int steps) {
		if (isInBase()) {
			if (canLeaveBaseWith(steps)) {
				return Optional.of(new Position(boardDescriptor, 0, startingPosition));
			} else {
				return Optional.empty();
			}
		} else {
			Position movedPosition = new Position(boardDescriptor, movedDistance + steps, startingPosition);
			if (movedPosition.isValid()) {
				return Optional.of(movedPosition);
			} else {
				return Optional.empty();
			}
		}
	}
	
	public Position resetToStartingPosition() {
		return new Position(boardDescriptor, 0, startingPosition);
	}
	
	public Position resetToBase() {
		return new Position(boardDescriptor, IN_BASE, startingPosition);
	}
	
	private boolean canLeaveBaseWith(int steps) {
		return steps == 6;
	}

	private boolean isValid() {
		return
				(movedDistance >= IN_BASE)
				&& (movedDistance - boardDescriptor.getBoardSize() < boardDescriptor.getHouseSize())
				&& startingPosition >= 0
				&& startingPosition < boardDescriptor.getBoardSize();
	}

	public boolean isInHouse() {
		int housePosition = movedDistance - boardDescriptor.getBoardSize();
		return housePosition >= 0 && housePosition < boardDescriptor.getHouseSize();
	}

	public boolean isInBase() {
		return movedDistance == IN_BASE;
	}

	public boolean isAtStartingPosition() {
		return movedDistance == 0;
	}

	public boolean isOnField() {
		return !isInBase() && !isInHouse();
	}

	public Optional<Integer> getHouseFieldNumber() {
		if (isInHouse()) {
			return Optional.of(movedDistance - boardDescriptor.getBoardSize());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardDescriptor == null) ? 0 : boardDescriptor.hashCode());
		
		// This implementation generates the same hashCodes for objects that
		// are in any house or base. This is permitted by the hashCode
		// contract, so it's not worth fixing, unless proven otherwise.
		result = prime * result + this.getFieldNumber().orElse(-1);
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
		Position other = (Position) obj;
		
		// Must be on the same board.
		if (!this.boardDescriptor.equals(other.boardDescriptor))
			return false;
		
		// Must be on the same field; this doesn't catch house and base positions.
		if (this.getFieldNumber().equals(other.getFieldNumber()))
		{
			// If both are on the same (existing) field, they are equal.
			if (this.getFieldNumber().isPresent())
				return true;
			else {
				// Both characters must be either in the house or base, and
				// those are only equal iff they have the same startingPosition.
				if (this.startingPosition == other.startingPosition) {
					// Base positions are always equal
					if (this.isInBase() && other.isInBase()) {
						return true;
					} else {
						return this.getHouseFieldNumber() == other.getHouseFieldNumber();
					}
				}
				else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Position other) {
		if (this.isInBase()) {
			if (other.isInBase())
				return 0;
			else
				return -1;
		} else if (this.isOnField()) {
			if (other.isInHouse()) {
				return 1;
			} else if (other.isOnField()) {
				return this.getFieldNumber().get().compareTo(other.getFieldNumber().get());
			} else {
				return -1;
			}
		} else {
			if (other.isInHouse()) {
				return this.getHouseFieldNumber().get().compareTo(other.getHouseFieldNumber().get());
			} else {
				return 1;
			}
		}
	}
	
	public static class BoardPositionBuilder {

		private final BoardDescriptor boardDescriptor;

		public BoardPositionBuilder(BoardDescriptor boardDescriptor) {
			this.boardDescriptor = boardDescriptor;
		}
		
		public StartingPositionBuilder startingAt(int startingPosition) {
			return new StartingPositionBuilder(boardDescriptor, startingPosition);
		}
	}

	public static class StartingPositionBuilder {
		private final int startingPosition;
		private final BoardDescriptor boardDescriptor;

		public StartingPositionBuilder(BoardDescriptor boardDescriptor, int startingPosition) {
			this.boardDescriptor = boardDescriptor;
			this.startingPosition = startingPosition;
		}
		
		public Position inBase() {
			return new Position(boardDescriptor, IN_BASE, startingPosition);
		}
		
		public Position atStartingPosition() {
			return new Position(boardDescriptor, 0, startingPosition);
		}
		
		public Position atPosition(int position) {
			if (position < 0 || position > boardDescriptor.getBoardSize()) {
				throw new IllegalArgumentException();
			}
			
			int distance = position - startingPosition;
			if (distance < 0) {
				distance += boardDescriptor.getBoardSize();
			}
			
			return new Position(boardDescriptor, distance, startingPosition);
		}
		
		public Position atHousePosition(int position) {
			if (position < 0 || position > boardDescriptor.getBoardSize()) {
				throw new IllegalArgumentException();
			}
			
			return new Position(boardDescriptor, position + boardDescriptor.getBoardSize(), startingPosition);
		}

	}
}
