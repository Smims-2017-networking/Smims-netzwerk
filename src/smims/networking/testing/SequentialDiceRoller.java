package smims.networking.testing;

import java.util.Iterator;
import java.util.stream.IntStream;

import smims.networking.model.IDiceRoller;

public class SequentialDiceRoller implements IDiceRoller {
	private final Iterator<Integer> rolls;
	private int lastRoll;
	
	public SequentialDiceRoller(IntStream rolls) {
		if (rolls == null) {
			throw new IllegalArgumentException();
		}
		
		this.rolls = rolls.iterator();
	}

	@Override
	public int getResult() {
		return lastRoll;
	}

	@Override
	public void rollDice() {
		lastRoll = rolls.next();
	}
}
