
/*
 * This Software was made by Vincent Poulin-Rioux 6333540
 * COMP 472  ---  Project part 1
 * “I certify that this submission is my original work and meets the Faculty's Expectations of Originality
 */
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ai.project.test.Evaluator;

public class Main {
	
	public static void main(String[] argv){
		Scanner scan= new Scanner(System.in);
	System.out.println("welcome to the colour puzzle! \n Please enter the name of the file from which to load our puzzle from:");
	String gamepath = scan.nextLine();
	System.out.println("Please Enter how many puzzles are to be solved from the file");
	int tosolve = scan.nextInt();
	String[] games = new String[tosolve];
	String[] allresult= new String[tosolve*2];
	int totalmove = 0;
	int totaltime=0;
	Scanner in;
	try 
	{
		in = new Scanner(new FileReader(gamepath));
		for(int i=0;i<tosolve;i++){
			games[i] = in.nextLine();
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	game[] Puzzles = new game[tosolve];
	for(int i=0;i<tosolve;i++){
		Puzzles[i] = new game(games[i]);
	}
	for(int i=0;i<Puzzles.length;i++){
		allresult[2*i] = Puzzles[i].getresult();
		totalmove = totalmove + Puzzles[i].getresult().length();
		allresult[2*i+1] = Puzzles[i].gettimeresult()/1000000 + "ms";
		totaltime = (int) (totaltime + Puzzles[i].gettimeresult()/1000000);
	}
	System.out.println("All Games are completed, thank you for using this software\n");
	Puzzles[0].resultwriting(allresult,totalmove,totaltime);
	String[] evaluator = new String[2];
	evaluator[0] = gamepath;
	evaluator[1] = "result.txt";
	try {
		new Evaluator(evaluator);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.exit(0);
	}
}
