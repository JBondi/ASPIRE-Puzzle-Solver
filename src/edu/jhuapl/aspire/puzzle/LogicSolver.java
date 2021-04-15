package edu.jhuapl.aspire.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Assertions;

public class LogicSolver {
	
	public void textSolver() {
		Map<String, String[]> puzzleClues = new HashMap<>();
		puzzleClues.put("Name", new String[] {"Hannah", "James", "Xavier", "Wolfgang"});
		puzzleClues.put("Pet", new String[] { "Cat", "Dog", "Marmot", "Fish"});
		puzzleClues.put("Color", new String[] { "Red", "Green", "Black", "Blue" });
		Map<String, IntVar> variables = new HashMap<>();
		Model model = new Model();
		for(String VarName: puzzleClues.keySet()) {
			for(String nameName: puzzleClues.get(VarName)) {
				IntVar var = model.intVar(nameName, 1, puzzleClues.get(VarName).length, true );
				variables.put(nameName, var);
			}
		}
		
		//making sure that ex hannah does not equal wolfgang
		for(Map.Entry<String, String[]> z : puzzleClues.entrySet()){
			String[] zValue = z.getValue();
			for (int i=0; i< zValue.length; i++) {
				for(int j=0; j< zValue.length; j++) {
					if(i != j) {
						variables.get(zValue[i]).ne(variables.get(zValue[j])).post();
					}
				}
			}
		}
		
		 //variables.get("Hannah").ne(variables.get("Wolfgang"));
		
		//now adding clues
		variables.get("Hannah").eq(variables.get("Red")).post();
		variables.get("Dog").ne(variables.get("Black")).post();
		variables.get("Wolfgang").ne(variables.get("Cat")).post();
		variables.get("Xavier").eq(variables.get("Marmot")).post();
		variables.get("Dog").eq(variables.get("Green")).post();
		
		//now solving the puzzle
		Solution solution = new Solution(model);
		Solver solver = model.getSolver();
		solver.solve();
		solution.record();
		
		System.out.println(solution.getIntVal(variables.get("Hannah")));
		System.out.println(solution.getIntVal(variables.get("Red")));
		
		Map<Integer, List<String>> solutionMap = new HashMap<>();
		for(int i=1; i<= 4; i++) {
			solutionMap.put(i, new ArrayList<>());
		}
		for(String VarName: puzzleClues.keySet()) {
			for(String nameName: puzzleClues.get(VarName)) {
				int solutionInt = solution.getIntVal(variables.get(nameName));
				solutionMap.get(solutionInt).add(nameName);
			}
		}
		System.out.println(solutionMap);
	}	

}
