import java.util.ArrayList;
import java.util.Arrays;

public class TrainSystemManager 
{
	private TrainStation[] trainStations = new TrainStation[5];
	
	public TrainSystemManager() 
	{
		for (int t = 0; t <= 4; t++)
		{
			trainStations[t] = new TrainStation();
		}
	}
	
//	public synchronized void setTrainStations(ArrayList<ArrayList<PassengerArrival>> pA)
//	{
//		for (int i = 0; i <5; i++)
//		{
//			for (int j = 0; j<=pA.get(i).size()-1; j++)
//			{
//				int destination = pA.get(i).get(j).getDestinationTrainStation();
//				trainStations[i].setPassengerRequests(destination, pA.get(i).get(j).getNumPassengers());
//				trainStations[i].setTotalDestinationRequests(destination, trainStations[i].getTotalDestinationRequests(j) + pA.get(i).get(j).getNumPassengers());
//				
//			}
//		}
//	}

	public synchronized void setApproachingTrain(int currentTrainStation, int trainID)
	{
		trainStations[currentTrainStation].setApproachingTrain(trainID);
		
	}

	public synchronized int getPassengerRequests(int currentTrainStation, int i)
	{
		return trainStations[currentTrainStation].getPassengerRequests(i);
	}

	public synchronized void setPassengerRequests(int currentTrainStation, int i, int j)
	{
		trainStations[currentTrainStation].setPassengerRequests(i, trainStations[currentTrainStation].getPassengerRequests(i) +j);
		
	}

//	public synchronized int getPassengerRequests(int currentTrainStation, int i)
//	{
//		
//		return trainStations[currentTrainStation].getPassengerRequestsArray()[i];
//	}

	public synchronized void setArrivedPassengers(int i, int trainID,int numPassengers)
	{
		trainStations[i].setArrivedPassengers(trainID,
        trainStations[i].getArrivedPassengers()[trainID] + numPassengers);	
	}

	public synchronized void setTotalDestinationRequests(int i, int destinationTrainStation, int numPassengers)
	{
		trainStations[i].setTotalDestinationRequests(destinationTrainStation,
        trainStations[i].getTotalDestinationRequests(destinationTrainStation) + numPassengers);
		
	}

	public synchronized int checkStation(int trainID)
	{
		for (int i = 0; i < 5; ++i)
        {
            if (trainStations[i].getApproachingTrain() == -1)
            {
                for (int j = 0; j < 5; ++j)
                {
                    if (trainStations[i].getPassengerRequestsArray()[j] > 0)
                    {
                        setApproachingTrain(i, trainID);
                        return i;
                    }
                }
            }
        }
        return -1;
	}

	public TrainStation[] getTrainStation()
	{
		return trainStations;
	}
	
	@Override
	public String toString()
	{
		String printS = "";
		printS += "\n***Station State:\n";
		printS += String.format("| %9s | %9s | %9s | %9s | %9s |\n", "Station #", "Requests", "Exited", "Waiting", "Train ID");
		for (int i = 0; i < 5; i++)
		{
			int ttdr = 0;
			int tap = 0;
			int cpr = 0;
			for (int j = 0; j < 5; j++){
				ttdr += trainStations[i].getTotalDestinationRequests()[j];
				tap += trainStations[i].getArrivedPassengers()[j];
				cpr += trainStations[i].getPassengerRequestsArray()[j];
			}
			
			printS += String.format("| %9d | %9d | %9d | %9d | %9d |", i,ttdr,tap,cpr,trainStations[i].getApproachingTrain());
//			printS += "\nTrain station: " + i;
//			printS += "\nTotal number of passengers that requested a ride at the station: " + Arrays.toString(trainStations[i].getTotalDestinationRequests());
//			printS += "\nTotal number of passengers that exited train at the station: " + Arrays.toString(trainStations[i].getArrivedPassengers());
//			printS += "\nCurrent number of passengers requesting a ride at the station: " + Arrays.toString(trainStations[i].getPassengerRequestsArray());
//			printS += "\nCurrent train heading towards the train station: " + trainStations[i].getApproachingTrain();
			printS += "\n";
		}
		return printS;
	}	

}
