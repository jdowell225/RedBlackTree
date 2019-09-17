
/**
 * This class represents a node in a Red-Black tree. It has an integer and a NodeColor, as well as
 * pointers to a left node, a right node, and a parent node, as well as getters and setters for
 * all of those. It also has a function getColorString that returns its color as a string.
 */

public class RedBlackNode {
	private NodeColor color;
	private RedBlackNode left;
	private RedBlackNode right;
	private RedBlackNode parent;
	private int key;
	
	public RedBlackNode(int key, RedBlackNode left, RedBlackNode right, RedBlackNode parent){
		this.key = key;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = NodeColor.black;
	}

	/**
	 * This function exists for testing purposes.
	 * @return
	 */
	public String getColorString(){
		if (this.color == NodeColor.black){
			return "Black";
		} else {
			return "Red";
		}
	}
	
	public boolean legalPathsDownEstablish(){
		int count = 0;
		RedBlackNode checkNode = this;
		while(checkNode != null){
			if (checkNode.getColor() == NodeColor.black){
				count++;
			}
			checkNode = checkNode.getLeft();
		}
		if (legalPathsDownLeft(count, this) == false){
			return false;
		}
		if (legalPathsDownRight(count, this) == false){
			return false;
		}
		return true;
	}
	
	public boolean legalPathsDownLeft(int count, RedBlackNode checkNode){
		
		
		
		return true;
	}
	
	public boolean legalPathsDownRight(int count, RedBlackNode checkNode){
		
		
		return true;
	}
	
	public NodeColor getColor() {
		return color;
	}

	public void setColor(NodeColor color) {
		this.color = color;
	}

	public RedBlackNode getLeft() {
		return left;
	}

	public void setLeft(RedBlackNode left) {
		this.left = left;
	}

	public RedBlackNode getRight() {
		return right;
	}

	public void setRight(RedBlackNode right) {
		this.right = right;
	}

	public RedBlackNode getParent() {
		return parent;
	}

	public void setParent(RedBlackNode parent) {
		this.parent = parent;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
