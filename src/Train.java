import java.util.ArrayList;
import java.util.*;

public class Train implements Runnable //TRAIN DOES NOT ACT ACCORDING TO TIME/ INTERACTS WITH pA Objs
{
	private int trainID; //is set in TrainStation through manager
	private int currentTrainStation; //set equal to train station it just left
	private int numPassengers; //current passengers on train; taken from PassengerArrival; interacts with TrainStation through Manager 
	private int totalLoadedPassengers; //Cumulative Loaded Passengers. Added through each loop as train processes move queue
	private int totalUnloadedPassengers; //cumulative unLoaded Passengers. Added through each loop as train processes movequeue
	private ArrayList<TrainEvent> moveQueue; // ArrayList of Train events that contain expected arrival time extracted from passenger arrival obj
	private int[] passengerDestinations;
	private TrainSystemManager manager;
	
	
	public Train(int trainId, TrainSystemManager manager)
	{
		this.manager = manager;
		this.trainID = trainId;
		this.currentTrainStation = 0;
		this.numPassengers = 0;
		this.totalUnloadedPassengers = 0;
		this.moveQueue = new ArrayList<TrainEvent>();
		this.passengerDestinations = new int[5];
		
		
	}
	
	public void run()// check move queue empty, take first element of movequeue; check current trainstation, load; else unloadload. if movequeue empty, create train events (look for requests, helper functions, 
	{
		while(!Thread.interrupted())
		{
			if (!moveQueue.isEmpty())
			{
				TrainEvent tEvent = moveQueue.get(0);	
				if (tEvent.getExpectedArrival() == SimClock.getTime())
				{
					currentTrainStation = tEvent.getDestination();
					if (numPassengers == 0)
					{
						if(goDown())//train goes down 
						{
							int num = 1;
					        for (int i = currentTrainStation - 1; i > -1; --i)
					        {
					            if (manager.getPassengerRequests(currentTrainStation, i) > 0)
					            {
					                if (i == currentTrainStation - 1)
					                {
					                    num++;
					                }
					                int numberOfPeople = manager.getPassengerRequests(currentTrainStation, i);
					                totalLoadedPassengers += numberOfPeople;
					                numPassengers += numberOfPeople;
					                manager.setPassengerRequests(currentTrainStation, i, -numberOfPeople);
					                passengerDestinations[i] = numberOfPeople;
					                createEvent(i, num);
					                num++;
					            }
					         
					        }
							
						}
						else //train goes up
						{
							int num = 1;
					        for (int i = currentTrainStation + 1; i < 5; ++i)
					        {
					            if (manager.getPassengerRequests(currentTrainStation, i) > 0)
					            {
					                if (currentTrainStation + 1 == i)
					                {
					                	num++;
					                }
					                	
					                int numberOfPeople = manager.getPassengerRequests(currentTrainStation, i);
					                totalLoadedPassengers += numberOfPeople;
					                numPassengers += numberOfPeople;
					                System.out.println();
					                manager.setPassengerRequests(currentTrainStation, i, -numberOfPeople);
					                passengerDestinations[i] = numberOfPeople;
					                createEvent(i, num);
					                num++; 
					            	}
					        }
						}
						manager.setApproachingTrain(currentTrainStation, -1);
					}
					
					else //unload passengers
					{
						int numberOfPeople = passengerDestinations[currentTrainStation];
				        setTotalUnloadedPassengers(numberOfPeople);
				        numPassengers -= numberOfPeople;
				        manager.setArrivedPassengers(currentTrainStation, trainID, numberOfPeople);
				        passengerDestinations[currentTrainStation] = 0;
					}
					moveQueue.remove(0);
				}
				
			}
			else
			{
				 int newStation = manager.checkStation(trainID);
	             if (newStation == -1){
	            	 continue;
	             }
	             createEvent(newStation, 0);  
			}
		}
	}

	public void createEvent(int destination, int num)
	{
		int expectedArrivalTime = (Math.abs(currentTrainStation - destination) * 5) + SimClock.getTime();
		TrainEvent tEvent = new TrainEvent();
		tEvent.setDestination(destination);
		tEvent.setExpectedArrival(expectedArrivalTime + (10*num));
		moveQueue.add(tEvent);	
	}


	public Boolean goDown()
	{
		boolean state = true;
        if (currentTrainStation == 0)
            state = false;
        for (int i = currentTrainStation + 1; i < 5; ++i)
        {
            if (manager.getPassengerRequests(currentTrainStation, i) > 0)
                state = false;
        }
        return state;
	}
	
	
//-----------HELPER METHODS --------------
	
	public int getTrainID() {
		return trainID;
	}

	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}

	public int getCurrentTrainStation() {
		return currentTrainStation;
	}

	public void setCurrentTrainStation(int currentTrainStation) {
		this.currentTrainStation = currentTrainStation;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	public int getTotalLoadedPassengers() {
		return this.totalLoadedPassengers;
	}

	public void setTotalLoadedPassengers(int totalLoadedPassengers) {
		this.totalLoadedPassengers += totalLoadedPassengers;
	}

	public int getTotalUnloadedPassengers() {
		return this.totalUnloadedPassengers;
	}

	public void setTotalUnloadedPassengers(int totalUnloadedPassengers) {
		this.totalUnloadedPassengers += totalUnloadedPassengers;
	}

	public int[] getPassengerDestinations() {
		return passengerDestinations;
	}

	public void setPassengerDestinations(int index, int passengers) {
		this.passengerDestinations[index] = passengers;
	}

	
}
