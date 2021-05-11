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
	
	private Model model = new Model();
	private Map<String, String[]> puzzleData;
	private Map<String, IntVar> variables = new HashMap<>();
	
	public LogicSolver(Map<String, String[]> puzzleData) {
		this.puzzleData = puzzleData;
		for(String VarName: puzzleData.keySet()) {
			for(String nameName: puzzleData.get(VarName)) {
				IntVar var = model.intVar(nameName, 1, puzzleData.get(VarName).length, true );
				variables.put(nameName, var);
			}
		}
		
		//making sure that ex hannah does not equal wolfgang
		for(Map.Entry<String, String[]> z : puzzleData.entrySet()){
			String[] zValue = z.getValue();
			for (int i=0; i< zValue.length; i++) {
				for(int j=0; j< zValue.length; j++) {
					if(i != j) {
						variables.get(zValue[i]).ne(variables.get(zValue[j])).post();
					}
				}
			}
		}
		
	}
	
	public void addAssociations(String name1, String name2, boolean isAssociated) {
		if(isAssociated) {
			variables.get(name1).eq(variables.get(name2)).post();
		}
		else {
			variables.get(name1).ne(variables.get(name2)).post();
		}
			}
	
	public void solvePuzzle() {
		Solution solution = new Solution(model);
		Solver solver = model.getSolver();
		solver.solve();
		solution.record();
		
		Map<Integer, List<String>> solutionMap = new HashMap<>();
		for(int i=1; i<= puzzleData.keySet().size(); i++) {
			solutionMap.put(i, new ArrayList<>());
		}
		for(String VarName: puzzleData.keySet()) {
			System.out.printf("%-20s", VarName);
			for(String nameName: puzzleData.get(VarName)) {
				int solutionInt = solution.getIntVal(variables.get(nameName));
				solutionMap.get(solutionInt).add(nameName);
			}
		}
		System.out.println();
		for(Map.Entry<Integer, List<String>> entry: solutionMap.entrySet()) {
			for(String value : entry.getValue()) {
				System.out.printf("%-20s", value);
			}
			System.out.println();
		}
	}
}