import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class TrainSimulation 
{
	private TrainSystemManager manager;
	private Train[] trains;
	private int totalSimTime;
	private int simSecondRate;
	private ArrayList<ArrayList<PassengerArrival>> passengerArrivalObjs;
	
	public TrainSimulation()
	{
		manager = new TrainSystemManager();
		trains = new Train[5];
		for (int t = 0; t < 5; t ++)
		{
			trains[t] = new Train(t, manager);
		}
	}
	
	
	public void start() throws FileNotFoundException
	{
		configExtract();
		printPassengerArrivalState();
		try
		{
			Thread thread0 = new Thread(trains[0]);
			thread0.start();
			Thread thread1 = new Thread(trains[1]);
			thread1.start();
			Thread thread2 = new Thread(trains[2]);
			thread2.start();
			Thread thread3 = new Thread(trains[3]);
			thread3.start();
			Thread thread4 = new Thread(trains[4]);
			thread4.start();
			while ((SimClock.getTime() < totalSimTime) && !Thread.interrupted())
			{
				Thread.sleep(simSecondRate);
				SimClock.tick();
				for (int i = 0; i < 5; i++)
				{
					for (PassengerArrival pA: passengerArrivalObjs.get(i))
					{
						int time = SimClock.getTime();
							if (pA.getExpectedTimeOfArrival() == time)
							{
								pA.setExpectedTimeOfArrival(pA.getTimePeriod() + time);
			                    manager.setPassengerRequests(i, pA.getDestinationTrainStation(),
			                            pA.getNumPassengers());
			                    manager.setTotalDestinationRequests(i,
			                            pA.getDestinationTrainStation(),
			                            pA.getNumPassengers());
							}
						}
					}
				
				
				if (SimClock.getTime() % 50 == 0)
				{
                    printTrainState();
				}
			}
				
			thread0.interrupt();
			thread1.interrupt();
			thread2.interrupt();
			thread3.interrupt();
			thread4.interrupt();
		}
		
		catch (InterruptedException e)
		{
			System.out.print("Exception: Thread Interrupted.");	
		}

	}
	
	public void printTrainState()
	{
		String printS = "";
		printS += "\n ---------------------------------------------------------";
		printS += "\nTime elapsed :" + SimClock.getTime();
		
		printS += manager.toString();
		printS += "\n***Train State:\n";
		printS += String.format("| %9s | %9s | %9s | %9s |\n", "Train ID", "Loaded", "Unloaded", "Current");
		for (int j = 0; j<5; j++)
		{
			printS += String.format("| %9d | %9d | %9d | %9d |\n", j,trains[j].getTotalLoadedPassengers(),trains[j].getTotalUnloadedPassengers(),trains[j].getNumPassengers());
				//printS += "Train: " + j;
				//printS += "\nTotal number of passengers loaded: "+trains[j].getTotalLoadedPassengers();
				//printS += "\nTotal number of passengers unloaded: "+trains[j].getTotalUnloadedPassengers();
				//printS += "\nCurrent number of passengers in train: "+trains[j].getNumPassengers();
				//printS += "\n";		
		}
		
		printS += "\n ---------------------------------------------------------";
		System.out.println(printS);
	}
	
	public void printPassengerArrivalState()
	{
		System.out.println("The simulation will be "+this.totalSimTime+" simulated seconds long.");
		System.out.println("Each simulated second will be "+this.simSecondRate+" milliseconds in real-time.");
		for (int i = 0; i < 5; i++)
		{
			String printS = "Train station "+ i;
			for (int j = 0; j<5; j++)
			{
				String stationAccro = "";
				if (j < this.passengerArrivalObjs.get(i).size()&& this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation() == 1)
				{
					stationAccro = "st";
				}
				if (j < this.passengerArrivalObjs.get(i).size()&& this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation() == 2)
				{
					stationAccro = "nd";
				}
				if (j < this.passengerArrivalObjs.get(i).size()&& this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation() == 3)
				{
					stationAccro = "rd";
				}
				if (j < this.passengerArrivalObjs.get(i).size()&& this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation() == 4 || j < this.passengerArrivalObjs.get(i).size()&& this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation() == 0)
				{
					stationAccro = "th";
				}
				
				
				if (j < this.passengerArrivalObjs.get(i).size() && j == 0)
				{
					printS += (" will have "+this.passengerArrivalObjs.get(i).get(j).getNumPassengers() +" people requesting to go to the "+this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation()+stationAccro+" train station every " +this.passengerArrivalObjs.get(i).get(j).getExpectedTimeOfArrival()+" simulated seconds");
				}
				if (j < this.passengerArrivalObjs.get(i).size() && j > 0 && j != this.passengerArrivalObjs.get(i).size()-1)
				{
					printS += (", "+this.passengerArrivalObjs.get(i).get(j).getNumPassengers() +" people requesting to go to the "+this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation()+stationAccro+" train station every " +this.passengerArrivalObjs.get(i).get(j).getExpectedTimeOfArrival()+" simulated seconds");
				}
				
				if (j < this.passengerArrivalObjs.get(i).size() && j == this.passengerArrivalObjs.get(i).size()-1 && j != 0)
				{
					printS += (" and "+this.passengerArrivalObjs.get(i).get(j).getNumPassengers() +" people requesting to go to the "+this.passengerArrivalObjs.get(i).get(j).getDestinationTrainStation()+stationAccro+" train station every " +this.passengerArrivalObjs.get(i).get(j).getExpectedTimeOfArrival()+" simulated seconds");
				}	
			}
			printS += ".";
			System.out.println(printS);
		}
	}

	//---------------------------------------------HELPER METHODS--------------------------------------------------------//
	public void configExtract() throws FileNotFoundException
	{
		passengerArrivalObjs = new ArrayList<ArrayList<PassengerArrival>>();
		File configFile = new File("TrainConfig.txt");
		Scanner in = null;
		try
		{
			in = new Scanner(configFile);
		}
		catch(FileNotFoundException e)
		{
			
		}
		totalSimTime = in.nextInt();
		simSecondRate = in.nextInt();
		in.nextLine();
		for (int i = 0; i < 5; i++)
		{
			String line = in.nextLine();
			String[] nums = line.toString().split(";");
			ArrayList<PassengerArrival> innerArray = new ArrayList<PassengerArrival>();
			for (int j = 0; j < 5; j++)
			{
				if (j < nums.length){
					String[] num = nums[j].toString().split(" ");
					PassengerArrival p = new PassengerArrival();
					p.setNumPassengers(Integer.parseInt(num[0]));
					p.setDestinationTrainStation(Integer.parseInt(num[1]));
					p.setExpectedTimeOfArrival(Integer.parseInt(num[2]));
					p.setTimePeriod(Integer.parseInt(num[2]));
					innerArray.add(j, p);
				}
			}
			passengerArrivalObjs.add(i, innerArray);
		}
	}
}
