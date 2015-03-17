import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class game {
	String[] Top = new String[5];
	String[] Mid = new String[5];
	String[] Bot = new String[5];
	int empty=0;
	List moves = new ArrayList();
	String currentmove="";
	String prevmove="";
	long time;
	boolean win;
	String answer;
	int h;
	int test=10;
	long answertime;
	Liste open;
	Liste closed;
	gameboard current;
	int[] colours = new int[6];
	double[] initialweight = new double[6];
	int totalcolors;
	//instantiate a game
	public game(String gameset){	
		totalcolors=0;
		Top = getLine(gameset,0);
		Mid = getLine(gameset,1);
		Bot = getLine(gameset,2);
		findempty();
		setcolours();
		win=false;
		h=0;
		answer=null;
		answertime=0;
		open = new Liste(new gameboard(Top,Mid,Bot,0,0,"","",empty));
		closed = new Liste();
		prevmove="";
		initiate();
		searchloop();
	}
	private void searchloop(){
		while (!win){
			nextmove();
			if(!win){
			createnewopen();
			}
		}
		saveresult();
	}
	public void	initiate(){
		time=System.nanoTime();
	}
	private String[] getLine(String gameset,int pos){
		gameset = gameset.replaceAll(" ", "");
		String[] split = gameset.split("");
		String[] Line = new String[5];
		switch (pos){
		case 0:
			System.arraycopy(split,1,Line,0,5);
			break;
		case 1:
			System.arraycopy(split,6,Line,0,5);
			break;
		case 2:
			System.arraycopy(split,11,Line,0,5);
			break;
		}
		return Line;	
	}
	private void findempty(){
		for(int i=0;i<5;i++){
			if (Top[i].equals("e")){
				empty=20+i;
			}
			if (Mid[i].equals("e")){
				empty=10+i;
			}
			if (Bot[i].equals("e")){
				empty=i;
			}
		}
	}
	private gameboard getMax(){
		double max=-1;
		gameboard keep=null;
		open.setbeginning();
		do{
			if ((open.current.game.heuristic + open.current.game.h/5)>max){
				keep = open.current.game;
				max = open.current.game.heuristic + open.current.game.h/5;
				if (open.current.game.heuristic==1000000){
					win=true;
				}
			}
			open.next();
		}while (!open.reachend());
		return keep;
		
	}
	private void nextmove(){
		if ((open.start!=null)){
		gameboard best = getMax();
		if (open.match(best)){
			if (best.h!=this.h+1){
				findempty();
			
			}	
		open.remove();
		closed.addNode(best);
		setgameboard(best);
		h=best.h;
		prevmove = best.prevmove;
		}else{
			System.out.println("Error, Can't find a match");
			System.exit(0);
		}
		}else{
			System.out.println("Error, Open List is Empty");
			System.exit(0);
		}
		
	}
	private void setgameboard(gameboard current){
		current.assignvalue(this.Top,current.Top1);
		current.assignvalue(this.Mid,current.Mid1);
		current.assignvalue(this.Bot,current.Bot1);
		this.h = current.h;
		this.empty = current.empty;
		this.prevmove = current.prevmove;
	}
	private void setcolours(){
		for(int i=0;i<6;i++){
			colours[i]=0;
		}
		findcolour("r",0);
		findcolour("w",1);
		findcolour("b",2);
		findcolour("y",3);
		findcolour("p",4);
		findcolour("g",5);
		for(int i=0;i<6;i++){
			initialweight[i] = (double)colours[i]/(double)totalcolors;
		}
		
	}
	private double getheuristics(){
		double heuristic=0;
		if (winning()){
			heuristic=1000000;
		}else{
		for(int i=0;i<5;i++){
			if (Top[i].equals(Bot[i])){
				heuristic++;
			}else{
				heuristic+=(getweight(Top[i])+getweight(Bot[i]));
			}
		}
		}
		return heuristic;
	}
	private double getweight(String letter){
		if (letter.equals("r")){
			return initialweight[0];
		}else if (letter.equals("w")){
			return initialweight[1];
		}else if (letter.equals("b")){
			return initialweight[2];
		}else if (letter.equals("y")){
			return initialweight[3];
		}else if (letter.equals("p")){
			return initialweight[4];
		}else if (letter.equals("g")){
			return initialweight[5];
		}else {
			return 0;
		}
	}
	private void createnewopen(){
		current = new gameboard(Top,Mid,Bot,0,h,"",prevmove,empty);
		if (moveright()){
			if (!closed.match(new gameboard(Top,Mid,Bot,0,h+1,currentmove,"",0))){
		open.addNode(new gameboard(Top,Mid,Bot,getheuristics(),h+1,currentmove,prevmove + currentmove,empty));
			}
		setgameboard(current);
		}
		if (moveleft()){
			if (!closed.match(new gameboard(Top,Mid,Bot,0,h+1,currentmove,"",0))){
		open.addNode(new gameboard(Top,Mid,Bot,getheuristics(),h+1,currentmove,prevmove + currentmove,empty));
			}
		setgameboard(current);
		}
		if (moveup()){
			if (!closed.match(new gameboard(Top,Mid,Bot,0,h+1,currentmove,"",0))){
		open.addNode(new gameboard(Top,Mid,Bot,getheuristics(),h+1,currentmove,prevmove + currentmove,empty));
			}
		setgameboard(current);
		}
		if (movedown()){
			if (!closed.match(new gameboard(Top,Mid,Bot,0,h+1,currentmove,"",0))){
		open.addNode(new gameboard(Top,Mid,Bot,getheuristics(),h+1,currentmove,prevmove + currentmove,empty));
			}
		setgameboard(current);
		}
	}
	private boolean loopfinder(){
		if (closed.match(new gameboard(Top,Mid,Bot,0,0,"","",0))){
			return true;
		}else{
			return false;
		}
	}
	private void findcolour(String colour, int colpos){
		for(int i=0;i<5;i++){
			if (Top[i].equals(colour)){
				colours[colpos]++;
				totalcolors++;
			}
			if (Mid[i].equals(colour)){
				colours[colpos]++;
				totalcolors++;
			}
			if (Bot[i].equals(colour)){
				colours[colpos]++;
				totalcolors++;
			}
		}
	}
	public boolean moveright(){

		if (empty>20){
			switch (empty-20){
			case 1:
				this.Top[1]=this.Top[0];
				this.Top[0]="e";
				writemove("A");
				break;
			case 2:
				Top[2]=Top[1];
				Top[1]="e";
				writemove("B");
				break;
			case 3:
				Top[3]=Top[2];
				Top[2]="e";
				writemove("C");
				break;
			case 4:
				Top[4]=Top[3];
				Top[3]="e";
				writemove("D");
				break;
			
			}
			empty--;
			return true;
		}
		else if (empty>10&&empty<20){
			switch (empty-10){
			case 1:
				Mid[1]=Mid[0];
				Mid[0]="e";
				writemove("F");
				break;
			case 2:
				Mid[2]=Mid[1];
				Mid[1]="e";
				writemove("G");
				break;
			case 3:
				Mid[3]=Mid[2];
				Mid[2]="e";
				writemove("H");
				break;
			case 4:
				Mid[4]=Mid[3];
				Mid[3]="e";
				writemove("I");
				break;
			
			}
			empty--;
			return true;
		}
		else if(empty>0&&empty<10){
			switch (empty){
			case 1:
				Bot[1]=Bot[0];
				Bot[0]="e";
				writemove("K");
				break;
			case 2:
				Bot[2]=Bot[1];
				Bot[1]="e";
				writemove("L");
				break;
			case 3:
				Bot[3]=Bot[2];
				Bot[2]="e";
				writemove("M");
				break;
			case 4:
				Bot[4]=Bot[3];
				Bot[3]="e";
				writemove("N");
				break;
			
			}
			empty--;

			return true;
		}else{
			return false;
		}
	}
	public boolean moveleft(){
		if (empty>=20&&empty<24){
			switch (empty-20){
			case 0:
				Top[0]=Top[1];
				Top[1]="e";
				writemove("B");
				break;
			case 1:
				Top[1]=Top[2];
				Top[2]="e";
				writemove("C");
				break;
			case 2:
				Top[2]=Top[3];
				Top[3]="e";
				writemove("D");
				break;
			case 3:
				Top[3]=Top[4];
				Top[4]="e";
				writemove("E");
				break;
			
			}
			empty++;
			return true;
		}
		else if (empty>=10&&empty<14){
			switch (empty-10){
			case 0:
				Mid[0]=Mid[1];
				Mid[1]="e";
				writemove("G");
				break;
			case 1:
				Mid[1]=Mid[2];
				Mid[2]="e";
				writemove("H");
				break;
			case 2:
				Mid[2]=Mid[3];
				Mid[3]="e";
				writemove("I");
				break;
			case 3:
				Mid[3]=Mid[4];
				Mid[4]="e";
				writemove("J");
				break;
			
			}
			empty++;
			return true;
		}
		else if(empty>=0&&empty<4){
			switch (empty){
			case 0:
				Bot[0]=Bot[1];
				Bot[1]="e";
				writemove("L");
				break;
			case 1:
				Bot[1]=Bot[2];
				Bot[2]="e";
				writemove("M");
				break;
			case 2:
				Bot[2]=Bot[3];
				Bot[3]="e";
				writemove("N");
				break;
			case 3:
				Bot[3]=Bot[4];
				Bot[4]="e";
				writemove("O");
				break;
			
			}
			empty++;
			return true;
			
		}else{
			return false;
		}
	}
	public boolean moveup(){
		if (empty>=20){
			switch (empty-20){
			case 0:
				Top[0]=Mid[0];
				Mid[0]="e";
				writemove("F");
				break;
			case 1:
				Top[1]=Mid[1];
				Mid[1]="e";
				writemove("G");
				break;
			case 2:
				Top[2]=Mid[2];
				Mid[2]="e";
				writemove("H");
				break;
			case 3:
				Top[3]=Mid[3];
				Mid[3]="e";
				writemove("I");
				break;
			case 4:
				Top[4]=Mid[4];
				Mid[4]="e";
				writemove("J");
				break;
			}
			empty+=-10;
		}
		else if (empty>=10){
			switch (empty-10){
			case 0:
				Mid[0]=Bot[0];
				Bot[0]="e";
				writemove("K");
				break;
			case 1:
				Mid[1]=Bot[1];
				Bot[1]="e";
				writemove("L");
				break;
			case 2:
				Mid[2]=Bot[2];
				Bot[2]="e";
				writemove("M");
				break;
			case 3:
				Mid[3]=Bot[3];
				Bot[3]="e";
				writemove("N");
				break;
			case 4:
				Mid[4]=Bot[4];
				Bot[4]="e";
				writemove("O");
				break;
			}
			empty+=-10;
		}else{
			return false;
		}
		return true;
	
	}
	public boolean movedown(){
		if (empty>=20){
			return false;
		}
		else if (empty>=10){
			switch (empty-10){
			case 0:
				Mid[0]=Top[0];
				Top[0]="e";
				writemove("A");
				break;
			case 1:
				Mid[1]=Top[1];
				Top[1]="e";
				writemove("B");
				break;
			case 2:
				Mid[2]=Top[2];
				Top[2]="e";
				writemove("C");
				break;
			case 3:
				Mid[3]=Top[3];
				Top[3]="e";
				writemove("D");
				break;
			case 4:
				Mid[4]=Top[4];
				Top[4]="e";
				writemove("E");
				break;
			}
			empty+=10;
		}
		else {
			switch (empty){
			case 0:
				Bot[0]=Mid[0];
				Mid[0]="e";
				writemove("F");
				break;
			case 1:
				Bot[1]=Mid[1];
				Mid[1]="e";
				writemove("G");
				break;
			case 2:
				Bot[2]=Mid[2];
				Mid[2]="e";
				writemove("H");
				break;
			case 3:
				Bot[3]=Mid[3];
				Mid[3]="e";
				writemove("I");
				break;
			case 4:
				Bot[4]=Mid[4];
				Mid[4]="e";
				writemove("J");
				break;
			}
			empty+=10;
		}
		return true;
	
	}
	private boolean winning(){
		for(int i=0;i<5;i++){
			if(!(Top[i].equals(Bot[i]))){
				return false;
			}
		}
		return true;
	}
	private void writemove(String letter){
		currentmove=letter;
	}
	public String getresult(){
		return answer;
	}
	public long gettimeresult(){
		return answertime;
	}
	private void saveresult(){
		answer = prevmove;
		answer = answer.replaceAll("\\[", "");
		answer = answer.replaceAll("\\]", "");
		answer = answer.replaceAll(",", "");
		answer = answer.replaceAll(" ", "");
		answertime =  (System.nanoTime()-time);
	}
	public void resultwriting(String[] allanswer,int totalmove,int totaltime){
		BufferedWriter result=null;
		 try {
			result = new BufferedWriter(new FileWriter("result.txt"));
			for(int i=0;i<allanswer.length/2;i++){
				result.write(allanswer[2*i]);
				result.newLine();
				result.write(allanswer[2*i+1]);
				result.newLine();
			}
			result.write(String.valueOf(totalmove));
			result.newLine();
			result.write(String.valueOf(totaltime) + "ms");
			result.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
                // Close the writer regardless of what happens...
                result.close();
            } catch (Exception e) {
            }
		}
		 
	}
	public void drawboard(){
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Top[i] + " | ");
		}
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Mid[i] + " | ");
		}
		System.out.print("\n____________________\n|");
		for (int i=0;i<5;i++){
			System.out.print(Bot[i] + " | ");
		}
		System.out.print("\n____________________\n");
		if (winning()){
			saveresult();
		}
		System.out.print("\n\n");
	}
	private void createmovelist(){
		closed.setbeginning();
		System.out.println(closed.current.game.move);
		do{
			closed.next();
			System.out.print(closed.current.game.move);
		}while(closed.current.next!=null);
		//saveresult();
	}

}
