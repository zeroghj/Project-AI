
public class Liste {

	NodeADT start;
	public NodeADT current;
	NodeADT prev;
	public Liste(gameboard game){
		start = new NodeADT(game);
		current = start;
		prev =null;
	}
	public Liste(){
		start = null;
		current = null;
		prev =null;
	}
	public void setbeginning(){
		current = start;
		prev=null;
	}
	public void addNode(gameboard game){
		if (start==null){
			start = new NodeADT(game);
			current = start;
		}else{
		gettoend();
		prev = current;
		current = new NodeADT(game);
		prev.setNext(current);
		}
	}
	public void remove(){
		if (current ==start){
			start = current.next;
			current = current.next;
		}else{
		current = current.next;
		prev.next = current;
		}
	}
	public void next(){
		if (current.next!=null){
		prev = current;
		current = current.next;
		}else{
			current=start;
			prev=null;
		}
		
	}
	public boolean reachend(){
		if (current.next==null){
			return true;
		}
		else
		return false;
	}
	public void gettoend(){
		if (!reachend()){
			next();
			gettoend();
		}
	}
	public boolean match(gameboard match){
		setbeginning();
		do{	
		if (current.game.equals(match)){
			return true;
		}else{
				next();
			}
		}while(!reachend());
		return false;
	}
}
