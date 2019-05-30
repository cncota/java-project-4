
public class TrainStation //INTERACTS WITH PASSENGER OBJ
{
	private int[] totalDestinationRequests; //cumulative passengers per station 
	private int[] arrivedPassengers; //arrived cumulative passengers - update 
	private int[] passengerRequests; //current passengers that want to travel to ith station; passengers increment every set simulated second; set station to 0 if passengers picked up
	private int approachingTrain; // set to approaching trainID, -1 if none
	
	public TrainStation()
	{
		this.totalDestinationRequests = new int[5];
		this.arrivedPassengers = new int[5];
		this.passengerRequests = new int[5];
		approachingTrain = -1;
	}
//----------------------HELPER METHODS---------------------------
	public int getTotalDestinationRequests(int index) {
		return totalDestinationRequests[index];
		
	}
	public int[] getTotalDestinationRequests() {
		return totalDestinationRequests;
	}
	public void setTotalDestinationRequests(int index, int requests) {
		this.totalDestinationRequests[index]= requests;
	}
	public int[] getArrivedPassengers() {
		return arrivedPassengers;
	}
	public void setArrivedPassengers(int index, int passengers) {
		this.arrivedPassengers[index] = passengers;
	}
	public int getPassengerRequests(int index){
		return passengerRequests[index];
	}
	public int[] getPassengerRequestsArray(){
		return passengerRequests;
	}
	public void setPassengerRequests(int index, int passengers) 
	{
		this.passengerRequests[index] = passengers;
	}
	public int getApproachingTrain() {
		return approachingTrain;
	}
	public void setApproachingTrain(int approachingTrain) {
		this.approachingTrain = approachingTrain;
	}
	
	public void printPassengerRequests()
	{
		for (int i = 0; i <= 4; i++)
		{
			System.out.println("Station:" + i + "requests: " + passengerRequests[i]);
		}
	}
}
