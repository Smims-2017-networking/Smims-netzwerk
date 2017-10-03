package smims.networking.model;

public class DiceRoller {

	private int result;
	
	public DiceRoller() {}
	
	public void rollDice()
	{
		result = (int)(Math.random()*6);
	}
	
	public int getResult()
	{
		return result;
	}
}
