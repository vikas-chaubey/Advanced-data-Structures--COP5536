import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class risingCity {

	private static long time = 0;
	private static int index = 0;
	private static Building selectedBuilding;

	//this method starts the construction of the risingCity
	private static void startConstructionOfCity(MinHeap minHeapObj, RedBlackTree redBlackTreeObj, List<String> timeList,List<String> operationList,BufferedWriter bufferedWriter) throws IOException {
		if ((index < timeList.size()) && time == Integer.parseInt(timeList.get(index))) {
			// doOperation
			if (operationList.get(index).contains("Insert")) {
				String buildingNo = operationList.get(index).substring(operationList.get(index).indexOf("(") + 1,
						operationList.get(index).indexOf(","));
				String totalTime = operationList.get(index).substring(operationList.get(index).indexOf(",") + 1,
						operationList.get(index).indexOf(")"));
				if (!insertBuildingRecord(minHeapObj, redBlackTreeObj, new Building(Integer.parseInt(buildingNo), 0, Integer.parseInt(totalTime)))) {
					return;
				}
			} else if (operationList.get(index).contains("Print")) {
				String range = operationList.get(index).substring(operationList.get(index).indexOf("(") + 1,
						operationList.get(index).indexOf(")"));
				if (range.contains(",")) {
					ArrayList<Building> list = RedBlackTree.doRangeSearchBetweenNodes(new ArrayList<>(), RedBlackTree.rootNode,
							Integer.parseInt(range.split(",")[0]), Integer.parseInt(range.split(",")[1]));
					if(list.isEmpty()) {
						bufferedWriter.write("(0,0,0)");
						bufferedWriter.newLine();
					}else {
						StringBuilder result = new StringBuilder();
						for (Building building : list) {
							result.append("(");
							result.append(building.getBuildingNum());
							result.append(",");
							result.append(building.getExecutedTime());
							result.append(",");
							result.append(building.getTotalTime());
							result.append(")");
							result.append(",");
						}
						bufferedWriter.write(result.substring(0, result.length() - 1));
						bufferedWriter.newLine();
					}
				} else {
					Building building = RedBlackTree.searchNode(RedBlackTree.rootNode, Integer.parseInt(range));
					if (building == null) {
						bufferedWriter.write("(0,0,0)");
						bufferedWriter.newLine();
					} else {
						bufferedWriter.write("(" + building.getBuildingNum() + "," + building.getExecutedTime() + ","
								+ building.getTotalTime() + ")");
						bufferedWriter.newLine();
					}
				}
			}
			index++;
		}

	}
	
	//this method inserts the building record in red black tree and min heap both
	public static boolean insertBuildingRecord(MinHeap minHeapObj, RedBlackTree rbtObj, Building buildingObj) {
		if (!rbtObj.addElement(buildingObj)) {
			return false;
		}
		minHeapObj.insertNode(buildingObj);
		return true;
	}

	// this method prints the building record
	private static void printBuildingRecord(Building building, long time, BufferedWriter bufferedWriter) throws IOException{
		bufferedWriter.write("(" + building.getBuildingNum() + "," + time + ")");
		bufferedWriter.newLine();
	}
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		boolean isMinHeapInitialized = false;
		MinHeap minHeap = new MinHeap(2000);
		RedBlackTree redBlackTreeObj = new RedBlackTree();
		
		//
		String fileName = args[0];
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		List<String> timeList = new ArrayList<>();
		List<String> operationList = new ArrayList<>();

		while ((line = reader.readLine()) != null) {
			timeList.add(line.split(":")[0]);
			operationList.add(line.split(":")[1]);
		}
		reader.close();

		int daysWorkedOnBuilding = 0;
		FileWriter fileWriter = new FileWriter("output_file.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		try {
			while (true) {

				if (isMinHeapInitialized && minHeap.getMinHeapSize() == 0 && index >= timeList.size()) {
					break;
				}

				if (!isMinHeapInitialized) {
					startConstructionOfCity(minHeap, redBlackTreeObj, timeList, operationList, bufferedWriter);
					isMinHeapInitialized = true;
					selectedBuilding = minHeap.getMinimumElement();
					daysWorkedOnBuilding = 0;
				} else {
					startConstructionOfCity(minHeap, redBlackTreeObj, timeList, operationList, bufferedWriter);
					if (minHeap.getMinHeapSize() == 1) {
						selectedBuilding = minHeap.getMinimumElement();
					}
				}
				time++;

				if (daysWorkedOnBuilding == 0) {
					minHeap.minHeapify();
					selectedBuilding = minHeap.getMinimumElement();
				}
				// Work on the building

				if (selectedBuilding != null) {
					daysWorkedOnBuilding++;
					selectedBuilding.setExecutedTime(selectedBuilding.getExecutedTime() + 1);
					if (selectedBuilding.getExecutedTime() == selectedBuilding.getTotalTime()) {
						printBuildingRecord(selectedBuilding, time, bufferedWriter);
						minHeap.removeMinimumElementNode();
						redBlackTreeObj.remove(selectedBuilding);
						minHeap.minHeapify();
						selectedBuilding = minHeap.getMinimumElement();
						daysWorkedOnBuilding = 0;
					}
				}

				if (daysWorkedOnBuilding == 5) {
					minHeap.minHeapify();
					selectedBuilding = minHeap.getMinimumElement();
					daysWorkedOnBuilding = 0;
				}
			}

		} catch (Exception e) {
			//do nothing
		}
		bufferedWriter.close();
		fileWriter.close();

	}


}
