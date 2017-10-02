public class DiceRoller {

	private int result;
	
	public DiceRoller() {}
	
	public void DiceRoll()
	{
		result = (int)(Math.random()*6);
	}
	
	public int getResult()
	{
		return result;
	}
}
