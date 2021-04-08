//This class implements a Min-Heap from scratch to hold building records
public class MinHeap {
	
	//variable to point at the first element of heap which is root 
	public static final int FrontElement = 1;
	
	//array variable using which heap is implemented
	private Building[] minHeapArray;
	
	//variable which gives current size of minheap
	private int minHeapSize;
	
	//variable which defines the max size of minheap
	private int minHeapMaxSize;

	
	// Min Heap constructor
	public MinHeap(int minHeapMaxSize) {
		this.minHeapMaxSize = minHeapMaxSize;
		this.minHeapSize = 0;
		minHeapArray = new Building[this.minHeapMaxSize + 1];
		minHeapArray[0] = new Building(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
	
	//returns the heap size of min heap
	public int getMinHeapSize() {
		return minHeapSize;
	}

	//this method returns the root element which is minimum element in min heap
	public Building getMinimumElement() {
		return minHeapArray[FrontElement];
	}
	
	
	//this method deletes and returns the minimum element node
	public Building removeMinimumElementNode() {
		Building popped = minHeapArray[FrontElement];
		minHeapArray[FrontElement] = minHeapArray[minHeapSize];
		minHeapArray[minHeapSize--] = null;
		minHeapify();
		return popped;
	}

	//this method returns the right child node of the node which is currently at position 
	private int getRightChild(int positionIndex) {
		return (2 * positionIndex) + 1;
	}
	
	//this method returns the left child node of the node which is currently at position 
	private int getLeftChild(int positionIndex) {
			return (2 * positionIndex);
	}
	
	//this method returns the parent node of the node which is currently at position 
	private int getParentNode(int positionIndex) {
			return positionIndex / 2;
	}
	
	
	//this function returns true if the passed node is a leaf
	private boolean isLeafNode(int positionIndex) {
		if (2 * positionIndex > minHeapSize && 2 * positionIndex + 1 > minHeapSize && positionIndex <= minHeapSize) {
			return true;
		}
		return false;
	}

	// this function swaps two nodes of the heap
	private void swapNodes(int firstNode, int secondNode) {
		Building temp;
		temp = minHeapArray[firstNode];
		minHeapArray[firstNode] = minHeapArray[secondNode];
		minHeapArray[secondNode] = temp;
	}
	
	// this function inserts a  new node in heap
	public void insertNode(Building element) {
		if (minHeapSize >= minHeapMaxSize) {
			return;
		}
		minHeapArray[++minHeapSize] = element;
		int current = minHeapSize;
	}

	//this function min-heapifies the whole heap
	public void minHeapify() {
		for (int pos = (minHeapSize / 2); pos >= 1; pos--) {
			minHeapifyNode(pos);
		}
	}
		
		
	// this function min heapify the node at given position
	public void minHeapifyNode(int positionIndex) {

		
		//check if the node is non leaf node and it is greater than any of its child
		if (!isLeafNode(positionIndex)) {

			if (minHeapArray[getLeftChild(positionIndex)]!=null && (minHeapArray[positionIndex].getExecutedTime() > minHeapArray[getLeftChild(positionIndex)].getExecutedTime())
					|| (minHeapArray[getRightChild(positionIndex)]!=null && (minHeapArray[positionIndex].getExecutedTime() > minHeapArray[getRightChild(positionIndex)].getExecutedTime()))) {

				//swap with the left child and min heapify the left child
				if(minHeapArray[getLeftChild(positionIndex)]==null) {
					swapNodes(positionIndex, getRightChild(positionIndex));
					minHeapifyNode(getRightChild(positionIndex));
				}else if (minHeapArray[getRightChild(positionIndex)]==null) {
					swapNodes(positionIndex, getLeftChild(positionIndex));
					minHeapifyNode(getLeftChild(positionIndex));
				}else {
					if (minHeapArray[getLeftChild(positionIndex)].getExecutedTime() < minHeapArray[getRightChild(positionIndex)].getExecutedTime()) {
						swapNodes(positionIndex, getLeftChild(positionIndex));
						minHeapifyNode(getLeftChild(positionIndex));
					}
					
					//swap with the left child and min heapify the right child
					else if (minHeapArray[getLeftChild(positionIndex)].getExecutedTime() > minHeapArray[getRightChild(positionIndex)].getExecutedTime()) {
						swapNodes(positionIndex, getRightChild(positionIndex));
						minHeapifyNode(getRightChild(positionIndex));
					}else {
						int minPos = Math.min(minHeapArray[getLeftChild(positionIndex)].getBuildingNum(), minHeapArray[getRightChild(positionIndex)].getBuildingNum());
						if(minHeapArray[getLeftChild(positionIndex)].getBuildingNum() == minPos) {
							swapNodes(positionIndex, getLeftChild(positionIndex));
							minHeapifyNode(getLeftChild(positionIndex));
						}else {
							swapNodes(positionIndex, getRightChild(positionIndex));
							minHeapifyNode(getRightChild(positionIndex));
						}
					}
				}
			} else {
				if (minHeapArray[getLeftChild(positionIndex)]!=null && minHeapArray[positionIndex].getExecutedTime() == minHeapArray[getLeftChild(positionIndex)].getExecutedTime()) {
					if (minHeapArray[positionIndex].getBuildingNum() > minHeapArray[getLeftChild(positionIndex)].getBuildingNum()) {
						swapNodes(positionIndex, getLeftChild(positionIndex));
						minHeapifyNode(getLeftChild(positionIndex));
					}
				}

				if (minHeapArray[getRightChild(positionIndex)]!=null && minHeapArray[positionIndex].getExecutedTime() == minHeapArray[getRightChild(positionIndex)].getExecutedTime()) {
					if (minHeapArray[positionIndex].getBuildingNum() > minHeapArray[getRightChild(positionIndex)].getBuildingNum()) {
						swapNodes(positionIndex, getRightChild(positionIndex));
						minHeapifyNode(getRightChild(positionIndex));
					}
				}
			}
		}
	}

	

	// this function prints the elements of the heap
	public void printHeapElements() {
		for (int i = 1; i <= minHeapSize / 2; i++) {
			System.out.println(" PARENT : " + minHeapArray[i] + " LEFT CHILD : " + minHeapArray[2 * i] + " RIGHT CHILD :" + minHeapArray[2 * i + 1]);
		}
	}

}
