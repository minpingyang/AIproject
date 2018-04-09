package code;

public class Node {
	private Node leftChild;
	private Node rightChild;
	private String name;
	private double currentImpurity;

	public Node getLeftChild() {
		return leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public String getName() {
		return name;
	}

	public double getCurrentImpurity() {
		return currentImpurity;
	}

	public Node(String name) {
		this.name = name;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public void setCurrentImpurity(double currentImpurity) {
		this.currentImpurity = currentImpurity;
	}

}
