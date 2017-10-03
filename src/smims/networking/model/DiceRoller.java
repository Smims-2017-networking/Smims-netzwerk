package smims.networking.model;

import java.util.Random;

public class DiceRoller implements IDiceRoller {
	private static final int SidesOnADie = 6;
	private final Random random = new Random();
	private int result;
	
	public DiceRoller() {}
	
	@Override
	public void rollDice()
	{
		// nextInt returns an value x where 0 <= x < bound; the result must
		// be increased by one to conform to real-world die.
		result = random.nextInt(SidesOnADie) + 1;
	}
	
	@Override
	public int getResult()
	{
		return result;
	}
}
