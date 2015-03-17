
public class gameboard {
	String[] Top1 = new String[5];
	String[] Mid1 = new String[5];
	String[] Bot1 = new String[5];
	public double heuristic;
	public String move;
	public int h;
	public int empty;
	public String prevmove;
	public gameboard(String[] a, String[] b, String[] c, double heur,int depth,String place,String prevplace,int e){
		assignvalue(Top1,a);
		assignvalue(Mid1,b);
		assignvalue(Bot1,c);
		heuristic=heur;
		move = place;
		empty=e;
		h=depth;
		prevmove = prevplace;
	}
	private boolean arrequal(String[] first,String[] other){
		for(int i=0;i<5;i++){
			if(!first[i].equals(other[i])){
				return false;
			}
				//System.out.println(first[i] + other[i]);
			
		}
		return true;
	}
	public void assignvalue(String[] initial, String[] other){
		for(int i=0;i<5;i++){
			initial[i] = other[i];
		}
	}
	public boolean equals(gameboard other){
		if (arrequal(this.Top1,other.Top1)&&arrequal(this.Mid1,other.Mid1)&&arrequal(this.Bot1,other.Bot1)){
			return true;
		}else{
			return false;
		}
	}
	public void drawboard(){
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Top1[i] + " | ");
		}
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Mid1[i] + " | ");
		}
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Bot1[i] + " | ");
		}
		System.out.print("\n____________________\n");
		System.out.print("\n\n");
	}
}
