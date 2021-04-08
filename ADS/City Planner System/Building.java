//this class holds Building records
public class Building {
	//this variable gives building  number
	private int buildingNum;
	
	//this variable gives number of work days spent on the building
	private int executed_time;
	
	//this variable gives the total number of days required to complete the building construction
	private int total_time;
	
	//this variable is the reference pointer to the corresponding same building record in the red black tree
	private Building redBlackTreePointer;
	
	
	
	//No-args constructor
	public Building() {
	}
	
	
	
	//constructor
	public Building(int buildingNum, int executedTime, int totalTime) {
		this.buildingNum = buildingNum;
		this.executed_time = executedTime;
		this.total_time = totalTime;
	}
	
	

	
	//getter method for building number variable
	public int getBuildingNum() {
		return buildingNum;
	}
	
	//setter method for the building number variable
	public void setBuildingNum(int buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	
	
	
	//getter method for executed_time variable
	public int getExecutedTime() {
		return executed_time;
	}
	
	//setter method for executed_time variable
	public void setExecutedTime(int executed_time) {
		this.executed_time = executed_time;
	}
	
	
	
	
	//getter method for redBlackTreePointer variable
	public Building getRedBlackTreePointer() {
		return redBlackTreePointer;
	}

	//setter method for redBlackTreePointer variable
	public void setRedBlackTreePointer(Building redBlackTreePointer) {
		this.redBlackTreePointer = redBlackTreePointer;
	}
	
	
	
	
	//getter method for the total_time variable
	public int getTotalTime() {
		return total_time;
	}
	
	//setter method for total_time variable
	public void setTotalTime(int total_time) {
		this.total_time = total_time;
	}

	
	
	
	@Override
	public String toString() {
		return "Buliding Number is =" + buildingNum + ", Execution Time is=" + executed_time + ", Total Time is=" + total_time;
	}
}
