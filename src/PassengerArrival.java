
public class PassengerArrival 
{
	private int numPassengers; 
	private int destinationTrainStation;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	
	public PassengerArrival()
	{
        this.numPassengers = 0;
        this.destinationTrainStation = 0;
        this.timePeriod = 0;
        this.expectedTimeOfArrival = 0;
	}
	
	public int getNumPassengers() {
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}
	public int getDestinationTrainStation() {
		return destinationTrainStation;
	}
	public void setDestinationTrainStation(int destinationTrainStation) {
		this.destinationTrainStation = destinationTrainStation;
	}
	public int getExpectedTimeOfArrival() {
		return expectedTimeOfArrival;
	}
	public void setExpectedTimeOfArrival(int expectedTimeOfArrival) {
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	public int getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}
}
