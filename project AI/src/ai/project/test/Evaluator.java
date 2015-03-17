package ai.project.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Evaluator {
	private ResourceBundle resourceBundle;
	
	private List<String> configurations;
	private List<String> solutions;
	private int totalMoves;
	private int totalTime;
	
	public Evaluator(String[] args) throws IOException {
		String bundleQualifierName = "ai.project.test.ErrorMsg";
		resourceBundle = ResourceBundle.getBundle(bundleQualifierName, new Locale("en"));
		System.out.println(resourceBundle.getString("msg.welcom"));
		if (args.length != 2){
			System.out.println(resourceBundle.getString("msg.usage"));
			return;
		}
		
		configurations = readLines(args[0]);
		solutions = readLines(args[1]);
		for (int i = 0; i < configurations.size(); ++i){
			System.out.println(resourceBundle.getObject("msg.solution.testing") + "" + i);
			if (checkConfigurationFormat(configurations.get(i)) && 
					checkSolutionFormat(solutions.get(2 * i), solutions.get(2 * i + 1)) &&
					check(configurations.get(i), solutions.get(2 * i))){
				System.out.println(resourceBundle.getObject("msg.solution.ok"));
			} else
				System.out.println(resourceBundle.getObject("msg.solution.error"));
		}
		
		if (!solutions.get(configurations.size() * 2).equals("" + totalMoves))
			System.out.println("msg.solution.err.totalMove" + totalMoves);
		if (!solutions.get(configurations.size() * 2 + 1).equals("" + totalTime + "ms"))
			System.out.println("msg.solution.err.totalTime" + totalTime);
	}
	
	private boolean check(String configuration, String solution) {
		String positions = "ABCDEFGHIJKLMNO";
		configuration = configuration.replace(" ", "");
		char[] maze = configuration.toCharArray();
		int empty = configuration.indexOf('e');
		for (int i = 0; i < solution.length(); ++i){
			int newPos = positions.indexOf(solution.charAt(i));
			if (!swqp(empty, newPos, maze)){
				System.out.println(resourceBundle.getObject("msg.solution.err.notpossiblemove") + "" + i);
				return false;
			} 
			empty = newPos;	
		}
		
		if (!checkIsGoal(maze)){
			System.out.println(resourceBundle.getObject("msg.solution.err.notgoal") + new String(maze));
			return false;
		}
			
		return true;
	}

	private boolean checkIsGoal(char[] maze) {
		for (int i = 0; i < 5; ++i){
			if (maze[i] != maze[ 10 + i])
				return false;
		}
		return true;
	}

	private boolean swqp(int empty, int newPos, char[] maze) {
		int distance = Math.abs(empty - newPos);
		if (distance == 1 || distance == 5){
			char c = maze[empty];
			maze[empty] = maze[newPos];
			maze[newPos] = c;
			return true;
		}
			
		return false;
	}

	private boolean checkSolutionFormat(String solution, String times) {
		totalMoves += solution.length();
		if (!solution.matches("[A-Z]+")){
			System.out.println(resourceBundle.getObject("msg.solution.err.invalidchar") + solution);
			return false;
		}
		if (!times.matches("\\d+ms")){
			System.out.println(resourceBundle.getObject("msg.solution.err.timeformat") + times);
			return false;
		}
		totalTime += Integer.parseInt(times.substring(0, times.length() - 2));
		
		return true;
	}

	private boolean checkConfigurationFormat(String strCofiguration) {
		if (strCofiguration.length() != 29){
			System.out.println(resourceBundle.getObject("msg.configuration.err.length") + "" + strCofiguration.length());
			return false;
		}
		for (int i = 0; i < 14; ++i){
			if (strCofiguration.charAt(2 * i) == ' ' || strCofiguration.charAt(2 * i + 1) != ' '){
				System.out.println(resourceBundle.getObject("msg.configuration.err.format") + "" + i + " > " + strCofiguration);
				return false;
			}
		}
		if (strCofiguration.charAt(28) == ' '){
			System.out.println(resourceBundle.getObject("msg.configuration.err.format") + "" + 28 + " > " + strCofiguration);
			return false;
		}
		return true;
	}

	public List<String> readLines(String filePath) throws IOException{
		List<String> lines = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = br.readLine()) != null){
			lines.add(line);
		}
		
		br.close();
		return lines;
	}

	public static void main(String[] args) throws IOException {
		new Evaluator(args);
	}
}
