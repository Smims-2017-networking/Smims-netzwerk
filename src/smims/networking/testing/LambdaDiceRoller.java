package smims.networking.testing;

import java.util.function.Function;

import smims.networking.model.IDiceRoller;

public class LambdaDiceRoller implements IDiceRoller {
	private final Function<Integer, Integer> numberGenerator;
	private int lastRoll;
	
	public LambdaDiceRoller(Function<Integer, Integer> numberGenerator) {
		if (numberGenerator == null) {
			throw new IllegalArgumentException();
		}
		
		this.numberGenerator = numberGenerator;
	}
	
	@Override
	public int getResult() {
		return lastRoll;
	}
	
	@Override
	public void rollDice() {
		lastRoll = numberGenerator.apply(lastRoll);
		
	}
}
