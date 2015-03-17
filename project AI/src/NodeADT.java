
public class NodeADT {

	NodeADT next;
	public gameboard game;
	public NodeADT(gameboard board){
		next=null;
		game = board;
		
	}
	public void setNext(NodeADT bob){
		next = bob;
	}
}
