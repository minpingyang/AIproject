package code;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DT {
	private Node root;
	private List<Attributes> attributeList;
	private Attributes parent_Attribute;

	public DT(List<Attributes> attributes) {
		this.attributeList = attributes;
		buildTree();
	}

	
	public Map<String, Double> findName_Purity(boolean isRoot,boolean isLeft) {
		List<Double> allImpurity = new ArrayList<>();
		for (Attributes attributes2 : attributeList) {
			if(isRoot) {
				allImpurity.add(calculateImpurity(attributes2));
			}else {
				allImpurity.add(findChildPurity(parent_Attribute, attributes2, isLeft));
			}
			
		}

		int index = allImpurity.indexOf(Collections.min(allImpurity));
		String attName = attributeList.get(index).getName();
		Double impurity = allImpurity.get(index);
		deleteAttr(attName);
		Map<String, Double> attMap = new HashMap<>();
		attMap.put(attName, impurity);
		return attMap;
		// System.out.println(attributes.get(index).getName());
	}

	public void deleteAttr(String name) {
		for (Attributes attributes1 : attributeList) {
			if (attributes1.getName().equals(name)) {
				parent_Attribute=attributes1;
				attributeList.remove(attributes1);
				break;
			}
		}
	}

	public void buildTree() {
		root = setNode(true, false);
		Node left_node=setNode(false, true);
		Node right_node=setNode(false, false);
		
		System.out.print(right_node.getName()+": "+right_node.getCurrentImpurity());
//		System.out.print(left_node.getName()+": "+left_node.getCurrentImpurity());
//		System.out.print(root.getName()+": "+root.getCurrentImpurity());
		
	}
//	public void setChild(boolean isLeft) {
//		findName_Purity(false,isLeft);
//		
//	}
	public Node setNode(boolean isRoot, boolean isLeft) {
		Map<String, Double> attMap = findName_Purity(isRoot,isLeft);
		Node node = new Node("Initialise");
		for (String name : attMap.keySet()) {
			node= new Node(name);
			node.setCurrentImpurity(attMap.get(name));
		}
		return node;

	}
	public double findChildPurity(Attributes att_pa, Attributes att_child,boolean isLeft) {
		double m_tr = 0;
		double n_tr = 0;
		double m_fa = 0;
		double n_fa = 0;
		double count_tr = 0;
		double count_fa = 0;
		double weight=0;
		for (int i = 0; i < att_pa.getBooList().size(); i++) {
			boolean b_pa = att_pa.getBooList().get(i);
			boolean b_child = att_child.getBooList().get(i);
			String status = att_child.getStatus_List().get(i);
			if(!isLeft) {
				b_pa=!b_pa;
			}
			if (b_pa) {
				if(b_child) {
					if (status.equals(DataCollection.kindsOfStatus[0])) {
						m_tr++;
					} else {
						n_tr++;
					}
					count_tr++;
				}else {
					if (status.equals(DataCollection.kindsOfStatus[0])) {
						m_fa++;
					} else {
						n_fa++;
					}
					count_fa++;
				}
				weight++;
			}
			
			
		}
		double tr_impurity = 2 * m_tr * n_tr / Math.pow(m_tr + n_tr, 2);
		double fa_impurity = 2 * m_fa * n_fa / Math.pow(m_fa + n_fa, 2);
		return ((tr_impurity * count_tr) + (fa_impurity * count_fa)) / weight;
		
	}

	public double calculateImpurity(Attributes attribute) {
		double m_tr = 0;
		double n_tr = 0;
		double m_fa = 0;
		double n_fa = 0;
		double count_tr = 0;
		double count_fa = 0;
		for (int i = 0; i < attribute.getBooList().size(); i++) {
			boolean b = attribute.getBooList().get(i);
			String status = attribute.getStatus_List().get(i);
			if (b) {
				if (status.equals(DataCollection.kindsOfStatus[0])) {
					m_tr++;
				} else {
					n_tr++;
				}
				count_tr++;
			} else {
				if (status.equals(DataCollection.kindsOfStatus[0])) {
					m_fa++;
				} else {
					n_fa++;
				}
				count_fa++;
			}
		}
		double tr_impurity = 2 * m_tr * n_tr / Math.pow(m_tr + n_tr, 2);
		double fa_impurity = 2 * m_fa * n_fa / Math.pow(m_fa + n_fa, 2);
		return ((tr_impurity * count_tr) + (fa_impurity * count_fa)) / attribute.getBooList().size();

	}

}
