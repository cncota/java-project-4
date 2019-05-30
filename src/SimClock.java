
public class SimClock 
{
	private static int simTime;
	
	public SimClock()
	{
		simTime = 0;
	}
	
	public static void tick()
	{
		simTime ++;
	}

	public static int getTime()
	{
		return simTime;
	}
}
