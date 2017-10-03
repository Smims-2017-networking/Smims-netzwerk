package smims.networking.model;

public class Position {

	int distance = -1 ;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int pDistance) {
		this.distance = pDistance;
	}
			
	public String toString()
	{
		return ""+ distance;
	}
	
}
