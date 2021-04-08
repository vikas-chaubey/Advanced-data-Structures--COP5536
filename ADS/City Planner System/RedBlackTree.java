import java.util.ArrayList;

//this class implements the red black tree from scratch and it is used to hold the building records
public class RedBlackTree {
	
	// root of the tree.
	static Node rootNode; 
	
	// This is inner class which represent a single node in the red black tree
	static class Node {
		
		// this variable points to node data value.
		Building nodeData; 
		
		// this variable points to left child
		Node leftChild; 
		
		// this variable points to right child.
		Node rightChild; 
		
		// this variable points to parent node.
		Node parentNode; 
		
		// character value in the variable represent red or black color of the node.
		char nodeColor; 

		//inner class constructor
		public Node(Building data, char color) {
			this.nodeData = data;
			this.leftChild = null;
			this.rightChild = null;
			this.parentNode = null;
			this.nodeColor = color;
		}
	}
	
	
	//RedblackTree class no args constructor
	public RedBlackTree() {}

	// This function adds a new element to the red black tree
	public boolean addElement(Building buildingData) {
			Node node = createNewNode(buildingData);
			if (rootNode == null) {
				rootNode = node;
				rootNode.nodeColor = 'B';
				return true;
			}

			Node tempNode = rootNode;
			while (tempNode != null) {
				if (tempNode.nodeData.getBuildingNum() == buildingData.getBuildingNum()) {
					return false;
				}
				if (tempNode.nodeData.getBuildingNum() > buildingData.getBuildingNum()) {
					if (tempNode.leftChild.nodeData == null) {
						tempNode.leftChild = node;
						node.parentNode = tempNode;
						balanceRedBlackTreeAfterInsertion(node); // balance the tree.
						return true;
					}
					tempNode = tempNode.leftChild;
					continue;
				}
				if (tempNode.nodeData.getBuildingNum() < buildingData.getBuildingNum()) {
					if (tempNode.rightChild.nodeData == null) {
						tempNode.rightChild = node;
						node.parentNode = tempNode;
						balanceRedBlackTreeAfterInsertion(node); // balance the tree.
						return true;
					}
					tempNode = tempNode.rightChild;
				}
			}
			return true;
		}

	// This method removes an element from the red black tree
	public void remove(Building buildingData) {
		
			if (rootNode == null) {
				return;
			}

			// search for the given element in the tree.
			Node tempNode = rootNode;
			while (tempNode.nodeData != null) {
				if (tempNode.nodeData == buildingData) {
					break;
				}
				if (tempNode.nodeData.getBuildingNum() >= buildingData.getBuildingNum()) {
					tempNode = tempNode.leftChild;
					continue;
				}
				if (tempNode.nodeData.getBuildingNum() < buildingData.getBuildingNum()) {
					tempNode = tempNode.rightChild;
					continue;
				}
			}

			// if not found. then return.
			if (tempNode.nodeData == null) {
				return;
			}

			// find the next greater number than the given data.
			Node nextGreater = findNextBiggerNode(tempNode);

			// swap the data values of given node and next greater number.
			Building buildingObj = tempNode.nodeData;
			tempNode.nodeData = nextGreater.nodeData;
			nextGreater.nodeData = buildingObj;

			// remove the next node.
			Node parentNode = nextGreater.parentNode;
			if (parentNode == null) {
				if (nextGreater.leftChild.nodeData == null) {
					rootNode = null;
				} else {
					rootNode = nextGreater.leftChild;
					nextGreater.parentNode = null;
					rootNode.nodeColor = 'B';
				}
				return;
			}

			if (parentNode.rightChild == nextGreater) {
				parentNode.rightChild = nextGreater.leftChild;
			} else {
				parentNode.leftChild = nextGreater.leftChild;
			}
			nextGreater.leftChild.parentNode = parentNode;
			String color = Character.toString(nextGreater.leftChild.nodeColor) + Character.toString(nextGreater.nodeColor);
			balanceRedBlackTreeAfterDeletion(nextGreater.leftChild, color); // balance the tree.
		}

	// this function creates a new Node with building data and assign and left and right children.
	public Node createNewNode(Building data) {
		Node node = new Node(data, 'R');
		node.leftChild = new Node(null, 'B');
		node.rightChild = new Node(null, 'B');
		return node;
	}


	// this method balances the red black tree in case of any insertion
	public static void balanceRedBlackTreeAfterInsertion(Node node) {

			// check if given node is root node then assign it black color
			if (node.parentNode == null) {
				rootNode = node;
				rootNode.nodeColor = 'B';
				return;
			}

			// check if node's parent color is black then return tree is balanced
			if (node.parentNode.nodeColor == 'B') {
				return;
			}

			// fetch the node's parent's sibling node.
			Node siblingNode = null;
			if (node.parentNode.parentNode.leftChild == node.parentNode) {
				siblingNode = node.parentNode.parentNode.rightChild;
			} else {
				siblingNode = node.parentNode.parentNode.leftChild;
			}

			// check if the sibling node color is red.
			if (siblingNode.nodeColor == 'R') {
				node.parentNode.nodeColor = 'B';
				siblingNode.nodeColor = 'B';
				node.parentNode.parentNode.nodeColor = 'R';
				balanceRedBlackTreeAfterInsertion(node.parentNode.parentNode);
				return;
			}

			// check if the sibling color is black.
			else {
				if (node.parentNode.leftChild == node && node.parentNode.parentNode.leftChild == node.parentNode) {
					doRightRotation(node.parentNode);
					balanceRedBlackTreeAfterInsertion(node.parentNode);
					return;
				}
				if (node.parentNode.rightChild == node && node.parentNode.parentNode.rightChild == node.parentNode) {
					doLeftRotation(node.parentNode);
					balanceRedBlackTreeAfterInsertion(node.parentNode);
					return;
				}
				if (node.parentNode.rightChild == node && node.parentNode.parentNode.leftChild == node.parentNode) {
					doLeftRotation(node);
					doRightRotation(node);
					balanceRedBlackTreeAfterInsertion(node);
					return;
				}
				if (node.parentNode.leftChild == node && node.parentNode.parentNode.rightChild == node.parentNode) {
					doRightRotation(node);
					doLeftRotation(node);
					balanceRedBlackTreeAfterInsertion(node);
					return;
				}
			}
		}
	
	// this function balances the red black tree in case if any node is deleted
	private static void balanceRedBlackTreeAfterDeletion(Node node, String color) {


		// if the node is Red-Black.
		if (node.parentNode == null || color.equals("BR") || color.equals("RB")) {
			node.nodeColor = 'B';
			return;
		}

		Node parentNode = node.parentNode;

		// fetch the sibling node of the given node.
		Node siblingNode = null;
		if (parentNode.leftChild == node) {
			siblingNode = parentNode.rightChild;
		} else {
			siblingNode = parentNode.leftChild;
		}

		Node siblingLeftChild = siblingNode.leftChild; // sibling's left node.
		Node siblingRightChild = siblingNode.rightChild; // siblings right node.

		if(siblingLeftChild==null && siblingRightChild==null) {
			return;
		}
		
		// check if sibling, its left child and right child all are black.
		if (siblingNode.nodeColor == 'B' && siblingLeftChild.nodeColor == 'B' && siblingRightChild.nodeColor == 'B') {
			siblingNode.nodeColor = 'R';
			node.nodeColor = 'B';
			String c = Character.toString(parentNode.nodeColor) + Character.toString('B');
			balanceRedBlackTreeAfterDeletion(parentNode, c);
			return;
		}

		// check if sibling is red then do the left rotation else do right rotation to balance tree.
		if (siblingNode.nodeColor == 'R') {
			if (parentNode.rightChild == siblingNode) {
				doLeftRotation(siblingNode);
			} else {
				doRightRotation(siblingNode);
			}
			balanceRedBlackTreeAfterDeletion(node, color);
			return;
		}

		// check if sibling is red but one of its children are red then change node color
		if(siblingLeftChild==null) {
			return;
		}
		if (parentNode.leftChild == siblingNode) {
			if (siblingLeftChild.nodeColor == 'R') {
				doRightRotation(siblingNode);
				siblingLeftChild.nodeColor = 'B';
			} else {
				doLeftRotation(siblingRightChild);
				doRightRotation(siblingRightChild);
				siblingRightChild.leftChild.nodeColor = 'B';
			}
			return;
		} else {
			if (siblingRightChild.nodeColor == 'R') {
				doLeftRotation(siblingNode);
				siblingRightChild.nodeColor = 'B';
			} else {
				doRightRotation(siblingLeftChild);
				doLeftRotation(siblingLeftChild);
				siblingLeftChild.rightChild.nodeColor = 'B';
			}
			return;
		}
	}

	// this method  performs Right Rotation.
	private static void doRightRotation(Node node) {
		Node parentNode = node.parentNode;
		Node rightChild = node.rightChild;
		node.rightChild = parentNode;
		parentNode.leftChild = rightChild;
		if (rightChild != null) {
			rightChild.parentNode = parentNode;
		}
		char colorIdentifier = parentNode.nodeColor;
		parentNode.nodeColor = node.nodeColor;
		node.nodeColor = colorIdentifier;
		Node grandParent = parentNode.parentNode;
		parentNode.parentNode = node;
		node.parentNode = grandParent;

		if (grandParent == null) {
			rootNode = node;
			return;
		} else {
			if (grandParent.leftChild == parentNode) {
				grandParent.leftChild = node;
			} else {
				grandParent.rightChild = node;
			}
		}
	}
	
	// this method performs the Left Rotation.
	private static void doLeftRotation(Node node) {

		Node parentNode = node.parentNode;
		Node leftChild = node.leftChild;
		node.leftChild = parentNode;
		parentNode.rightChild = leftChild;
		if (leftChild != null) {
			leftChild.parentNode = parentNode;
		}
		char colorIdentifier = parentNode.nodeColor;
		parentNode.nodeColor = node.nodeColor;
		node.nodeColor = colorIdentifier;
		Node grandParent = parentNode.parentNode;
		parentNode.parentNode = node;
		node.parentNode = grandParent;

		if (grandParent == null) {
			rootNode = node;
			return;
		} else {
			if (grandParent.leftChild == parentNode) {
				grandParent.leftChild = node;
			} else {
				grandParent.rightChild = node;
			}
		}
	}

	//this method searches whether the given node is present in the tree if its present then it returns the node data
	public static Building searchNode(Node node, int buildingNumber) {
		if(node == null || node.nodeData == null) {
			return null;
		}
		if(node.nodeData.getBuildingNum()==buildingNumber) {
			return node.nodeData;
		}else if(node.nodeData.getBuildingNum()<buildingNumber) {
			return searchNode(node.rightChild, buildingNumber);
		}else if(node.nodeData.getBuildingNum()>buildingNumber) {
			return searchNode(node.leftChild, buildingNumber);
		}
		return null;
	}
	
	//this method performs the range search between two nodes and returns a list of nodes which lie between two given nodes
	public static ArrayList<Building> doRangeSearchBetweenNodes(ArrayList<Building> list, Node node, int startBuilding, int endBuilding){
		if(node==null || node.nodeData == null) {
			return list;
		}
		if(isMiddle(node.nodeData.getBuildingNum(), startBuilding, endBuilding)) {
			doRangeSearchBetweenNodes(list, node.leftChild, startBuilding, endBuilding);
			list.add(node.nodeData);
			doRangeSearchBetweenNodes(list, node.rightChild, startBuilding, endBuilding);
		}else if(node.nodeData.getBuildingNum() >= startBuilding) {
			doRangeSearchBetweenNodes(list, node.leftChild, startBuilding, endBuilding);
		}else if(node.nodeData.getBuildingNum() <= startBuilding) {
			doRangeSearchBetweenNodes(list, node.rightChild, startBuilding, endBuilding);
		}
		return list;
	}
	
	// this method displays the elements of the red black tree.
	public static void displayTreeElements() {
		if (rootNode == null) {
			System.out.println("The Tree is Empty");
			return;
		}

		System.out.print("Tree's PreOrder Traversal : ");
		preOrderTraversal(rootNode);
		System.out.println();
	}

	// this method performs the PreOrder Traversal of the red black tree.
	private static void preOrderTraversal(Node node) {
		if (node.nodeData == null) {
			return;
		}
		System.out.print(node.nodeData + "-" + node.nodeColor + " ");
		preOrderTraversal(node.leftChild);
		preOrderTraversal(node.rightChild);
	}

	// this method finds  next greater element than the given node.
	private static Node findNextBiggerNode(Node node) {
		Node nextNode = node.rightChild;
		if (nextNode.nodeData == null) {
			return node;
		}
		while (nextNode.leftChild.nodeData != null) {
			nextNode = nextNode.leftChild;
		}
		return nextNode;
	}
	
	//this method checks whether a given node lies in mid of two given nodes
	private static boolean isMiddle(int buildingNum, int startBuilding, int endBuilding) {
		if(buildingNum >= startBuilding && buildingNum<=endBuilding)
			return true;
		return false;
	}

}
