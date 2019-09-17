
/**
 * This class represents a data structure called a Red-Black Tree, that can contain
 * any number of integers. It has a root node and a null node that is the parent of the root and the child of all leaf nodes
 * in the tree. It contains methods to insert a key, delete a key, search for a key, and various helper functions
 * for those larger functions. All Red-Black Trees must fulfill a certain number of requirements, those being:
 * Every node is either Red or Black, the root is Black, every leaf is Black (leaves are technically the nil node),
 * if a node is Red, then both its children are black, and for each node, all simple paths from the node to descendant
 * leaves contain the same number of black nodes. All functions in the tree are made to ensure that these requirements
 * are fulfilled.
 * @author Jacob Dowell
 *
 */

public class RedBlackTree {
	private RedBlackNode root;
	private RedBlackNode nil = null;
	
	public RedBlackTree (int rootKey){
		this.root = new RedBlackNode(rootKey, this.nil, this.nil, this.nil);
	}
	
	/**
	 * Insert takes in an integer to be inserted, finds the proper place in the tree to insert it, and
	 * then calls insertFixup to ensure that nodes remain the proper color.
	 * @param key
	 */
	public void insert(int key){
		RedBlackNode z = new RedBlackNode(key, this.nil, this.nil, this.nil); //the node to be inserted
		RedBlackNode y = this.nil;
		RedBlackNode x = this.root;
		
		/*
		 * We start by looking for the node that will be the parent of our new node z
		 */
		while(x != this.nil){
			y = x;
			if (z.getKey() < x.getKey()){
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		
		/*
		 * We put z into the proper place
		 */
		z.setParent(y);
		if (y == this.nil){
			this.root = z;
		} else {
			if (z.getKey() < y.getKey()){
				y.setLeft(z);
			} else {
				y.setRight(z);
			}
		}
		/*
		 * We set z's children to be nil, as it is a leaf, set the color to be red, and then
		 * call insertFixup to ensure that the tree retains the requirements
		 */
		z.setLeft(this.nil);
		z.setRight(this.nil);
		z.setColor(NodeColor.red);
		insertFixup(z);
	}
	
	/**
	 * LeftRotate and RightRotate are helper methods for the two fixup methods used for
	 * insert and delete. They assume that x.right or y.left are not nil, and they switch 
	 * the positions and children of x and y, rotating either right or left, depending
	 * on the method.
	 * @param x
	 */
	public void leftRotate(RedBlackNode x){
		RedBlackNode y = x.getRight();
		x.setRight(y.getLeft());
		
		if (y.getLeft() != this.nil){
			y.getLeft().setParent(x);
		}
		
		y.setParent(x.getParent());
		
		if (x.getParent() == this.nil){
			this.root = y;
		} else if (x == x.getParent().getLeft()){
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		
		y.setLeft(x);
		x.setParent(y);
	}
	
	public void rightRotate(RedBlackNode y){
		RedBlackNode x = y.getLeft();
		y.setLeft(x.getRight());
		
		if (x.getRight() != this.nil){
			x.getRight().setParent(y);
		}
		
		x.setParent(y.getParent());
		
		if (y.getParent() == this.nil){
			this.root = x;
		} else if (y == y.getParent().getRight()){
			y.getParent().setRight(x);
		} else {
			y.getParent().setLeft(x);
		}
		
		x.setRight(y);
		y.setParent(x);
	}
	
	/**
	 * InsertFixup is a method that goes through a tree that has just inserted a new
	 * red node, and alters the tree such that it continues to fulfill the requirements
	 * established for a Red-Black tree.
	 * @param z
	 */
	public void insertFixup(RedBlackNode z){
		while(z.getParent() != this.nil && z.getParent().getColor() == NodeColor.red){ //If z's parent is black, then we do nothing. We only need to fixup if it is red
			
			/*
			 * This section of code runs if z's parent is z's grandparent's left child. The other section of code
			 * runs if it is the right child, and is the same but with Left and Right reversed
			 */
			if (z.getParent() == z.getParent().getParent().getLeft()){
				
				RedBlackNode y = z.getParent().getParent().getRight(); //z's parent's sibling
				
				/*
				 * if y is red, then we set z's parent's color to black, change y's color to black,
				 * set z's grandparent's color to red, and replace z with its grandparent
				 */
				if (y != this.nil && y.getColor() == NodeColor.red){
					z.getParent().setColor(NodeColor.black);
					y.setColor(NodeColor.black);
					z.getParent().getParent().setColor(NodeColor.red);
					z = z.getParent().getParent();
				} else {
					/*
					 * otherwise, if z is the right child, we set z to be its parent, and call leftRotate on z
					 */
					if (z == z.getParent().getRight()){
						z = z.getParent();
						leftRotate(z);
					}
					/*
					 * We then set z's parent to be black, z's grandparent to be red, and call rightRotate on
					 * z's grandparent
					 */
					z.getParent().setColor(NodeColor.black);
					z.getParent().getParent().setColor(NodeColor.red);
					rightRotate(z.getParent().getParent());
				}
				
			} else { //same but with Left and Right reversed
				
				RedBlackNode y = z.getParent().getParent().getLeft();
				
				if (y != this.nil && y.getColor() == NodeColor.red){
					z.getParent().setColor(NodeColor.black);
					y.setColor(NodeColor.black);
					z.getParent().getParent().setColor(NodeColor.red);
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getLeft()){
						z = z.getParent();
						rightRotate(z);
					}
					z.getParent().setColor(NodeColor.black);
					z.getParent().getParent().setColor(NodeColor.red);
					leftRotate(z.getParent().getParent());
				}
				
			}
		}
		this.root.setColor(NodeColor.black); //we need to ensure that the root is black
	}
	
	/**
	 * Transplant is a helper method for delete. It replaces the subtree
	 * rooted at u with the subtree rooted at v.
	 * @param u
	 * @param v
	 */
	public void transplant(RedBlackNode u, RedBlackNode v){
		if (u.getParent() == this.nil){
			this.root = v;
		} else if (u == u.getParent().getLeft()){
			u.getParent().setLeft(v);
		} else{
			u.getParent().setRight(v);
		}
		
		if(v != this.nil){
			v.getParent().setParent(u.getParent());
		}
	}
	
	/**
	 * TreeMinimum is a helper function for delete. It finds the minimum node in tree 
	 * rooted at x.
	 * @param x
	 * @return
	 */
	public RedBlackNode treeMinimum(RedBlackNode x){
		while (x.getLeft() != this.nil){
			x = x.getLeft();
		}
		return x;
	}
	
	/**
	 * The delete method takes in an integer, finds the node that contains that integer, if one
	 * exists, and removes it from the tree. 
	 * @param deleteKey
	 */
	public void delete(int deleteKey){
		RedBlackNode z; //the node to be deleted
		try {
			z = search(deleteKey, this.root);
		} catch (KeyNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		RedBlackNode y = z;
		NodeColor yOrigin = y.getColor();
		RedBlackNode x;
		
		if (z.getLeft() == this.nil){
			x = z.getRight();
			transplant(z, z.getRight());
		} else if (z.getRight() == this.nil){
			x = z.getLeft();
			transplant(z, z.getLeft());
		} else {
			y = treeMinimum(z.getRight());
			yOrigin = y.getColor();
			x = y.getRight();
			if (y.getParent() == z){
				x.setParent(y);
			} else {
				transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			y.setColor(z.getColor());
		}
		
		if (yOrigin == NodeColor.black){
			deleteFixup(x);
		}
	}
	
	public void deleteFixup(RedBlackNode x){
		while(x != this.root && x.getColor() == NodeColor.black){
			if (x == x.getParent().getLeft()){
				
				RedBlackNode w = x.getParent().getRight();
				
				if (w.getColor() == NodeColor.red){
					w.setColor(NodeColor.black);
					x.getParent().setColor(NodeColor.red);
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				
				if (w.getLeft().getColor() == NodeColor.black && w.getRight().getColor() == NodeColor.black){
					w.setColor(NodeColor.red);
					x = x.getParent();
				} else {
					if (w.getRight().getColor() == NodeColor.black){
						w.getLeft().setColor(NodeColor.black);
						w.setColor(NodeColor.red);
						rightRotate(w);
						w = x.getParent().getRight();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(NodeColor.black);
					w.getRight().setColor(NodeColor.black);
					leftRotate(x.getParent());
					x = this.root;
				}
				
			} else {
				
				RedBlackNode w = x.getParent().getLeft();
				
				if (w.getColor() == NodeColor.red){
					w.setColor(NodeColor.black);
					x.getParent().setColor(NodeColor.red);
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				
				if (w.getRight().getColor() == NodeColor.black && w.getLeft().getColor() == NodeColor.black){
					w.setColor(NodeColor.red);
					x = x.getParent();
				} else {
					if (w.getLeft().getColor() == NodeColor.black){
						w.getRight().setColor(NodeColor.black);
						w.setColor(NodeColor.red);
						leftRotate(w);
						w = x.getParent().getLeft();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(NodeColor.black);
					w.getLeft().setColor(NodeColor.black);
					rightRotate(x.getParent());
					x = this.root;
				}
				
			}
		}
		x.setColor(NodeColor.black);
	}
	
	/**
	 * Search takes in an integer to be searched for and a node
	 * to search, then recursively looks through the tree until
	 * it either finds the node that contains the integer or
	 * it runs out of tree to search.
	 * @param searchKey
	 * @param searchNode
	 * @return
	 * @throws KeyNotFoundException
	 */
	public RedBlackNode search(int searchKey, RedBlackNode searchNode) throws KeyNotFoundException{
		int nodeKey = searchNode.getKey();
		
		if (searchKey == nodeKey){
			return searchNode;
		} else {
			if (searchKey < nodeKey){
				if (searchNode.getLeft() != this.nil){
					return search(searchKey, searchNode.getLeft());
				} else {
					throw new KeyNotFoundException();
				}
			} else if (searchKey > nodeKey){
				if (searchNode.getRight() != this.nil){
					return search(searchKey, searchNode.getRight());
				} else {
					throw new KeyNotFoundException();
				}
			}
		}
		return null;
	}
	
	/**
	 * A getter method for the root
	 */
	public RedBlackNode getRoot(){
		return this.root;
	}
}
