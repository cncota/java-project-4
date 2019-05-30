
public class TrainEvent 
{
	private int destination; //destination station
	private int expectedArrival; //time that train will sleep; = expected arrival + load time (10) + stations passed (abs(destination-origin)*5) + unload(10) 

	public TrainEvent()
	{
		destination = 0;
		expectedArrival = 0;
	}
	

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getExpectedArrival() {
		return expectedArrival;
	}

	public void setExpectedArrival(int expectedArrival) {
		this.expectedArrival = expectedArrival;
	}
	
}
