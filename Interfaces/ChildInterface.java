package Interfaces;

import Network.Adult;

public interface ChildInterface {

	public void configureParents(Adult parentOne, Adult parentTwo);
	public Adult getParentOne();
	public Adult getParentTwo();
	
}