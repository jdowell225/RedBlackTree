 import static org.junit.Assert.*;

import org.junit.Test;

public class RedBlackTest {

	//	@Test
	//	public void test() {
	//		fail("Not yet implemented");
	//	}


	/**
	 * This tests to ensure that an element
	 * can be found in a tree with only one node
	 */
	@Test
	public void searchOneTest(){
		RedBlackTree testTree = new RedBlackTree(0);

		try {
			RedBlackNode testNode = testTree.search(0, testTree.getRoot());
		} catch (KeyNotFoundException e) {
			// TODO Auto-generated catch block
			fail("0 not found");
		}

	}

	/**
	 * This tests to ensure that ten elements can all
	 * be found in a tree with those ten elements
	 */
	@Test
	public void searchSevenTest(){
		RedBlackTree testTree = new RedBlackTree(4);
		testTree.insert(1);
		testTree.insert(2);
		testTree.insert(3);
		testTree.insert(5);
		testTree.insert(6);
		testTree.insert(7);

		for(int count = 1; count <= 7; count++){
			try {
				RedBlackNode testNode = testTree.search(count, testTree.getRoot());
			} catch (KeyNotFoundException e) {
				// TODO Auto-generated catch block
				fail("Failed to find "+count);
			}
		}
	}

	/**
	 * This tests to ensure that a tree with five elements
	 * inserted into it one at a time will uphold the
	 * characteristics of a red-black tree
	 */
	@Test
	public void insertFixupTest(){
		RedBlackTree testTree = new RedBlackTree(4);
		testTree.insert(1);
		testTree.insert(2);
		testTree.insert(3);
		testTree.insert(5);

		/*
		 * We know that the root of this tree should be 2, a black node, with a left child of
		 * 1, a black node with no children, and a right child of 4, a black node with two children.
		 * Black has a left child of 3, a red node, and a right child of 5, also a red node.
		 */
		if (testTree.getRoot().getKey() != 2){
			fail("2 out of place");
		} else if (testTree.getRoot().getLeft().getKey() != 1){
			fail("1 out of place");
		} else if (testTree.getRoot().getRight().getKey() != 4){
			fail("4 out of place");
		} else if (testTree.getRoot().getRight().getLeft().getKey() != 3){
			fail("3 out of place");
		} else if (testTree.getRoot().getRight().getRight().getKey() != 5){
			fail("5 out of place");
		}

		if(testTree.getRoot().getColor() != NodeColor.black){
			fail("Root is red");
		} else if (testTree.getRoot().getLeft().getColor() != NodeColor.black){
			fail("1 is red");
		} else if (testTree.getRoot().getRight().getColor() != NodeColor.black){
			fail("4 is red");
		} else if (testTree.getRoot().getRight().getLeft().getColor() != NodeColor.red){
			fail("3 is black");
		} else if (testTree.getRoot().getRight().getRight().getColor() != NodeColor.red){
			fail("5 is black");
		}
	}

	/**
	 * This tests to ensure that a five-element tree deleted
	 * one at a time will uphold the characteristics of a red-
	 * black tree
	 */
	@Test
	public void deleteFixupTest(){
		RedBlackTree testTree = new RedBlackTree(4);
		testTree.insert(1);
		testTree.insert(2);
		testTree.insert(3);
		testTree.insert(5);

		for(int count = 5; count > 0; count--){
			System.out.println(""+count);
			testTree.delete(count);
			if (testTree.getRoot().getColor() == NodeColor.red){
				fail("Root node is red");
			}
			
			for(int counter = count-1; counter > 0; counter--){
				RedBlackNode testNode = null;
				try {
					testNode = testTree.search(counter, testTree.getRoot());
				} catch (KeyNotFoundException e) {
					fail("could not find node that should be in tree "+counter);
				}
				if (testNode.getColor() == NodeColor.red){
					if (testNode.getLeft().getColor() == NodeColor.red){
						fail("Left child of red node "+counter+" is red");
					} else if (testNode.getRight().getColor() == NodeColor.red){
						fail("Left child of red node" +counter+" is red");
					}
				}
				
			}
			
		}
	}



}
