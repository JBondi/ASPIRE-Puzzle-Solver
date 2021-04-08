package edu.jhuapl.aspire.puzzle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolverTest {

	@Test
	public void simpleIntSolver() {
		Model model = new Model();
		IntVar x = model.intVar("x", 1,3);
		IntVar y = model.intVar("y", -10, 10, true);
		model.arithm(y, "<", 3).post();
		model.arithm(y, ">", 1).post();
		model.arithm(x, "=", y).post();
		model.arithm(x, "=", 2).post();
		Solution solution = new Solution(model);
		Solver solver = model.getSolver();
		solver.solve();
		solution.record();
		Assertions.assertEquals(solution.getIntVal(x), 2);
		Assertions.assertEquals(solution.getIntVal(y), 2);
	}
	
	@Test
	public void simpleIntSolver2() {
		// x+y <=10 but >3 and x-y=2
		Model model = new Model();
		IntVar ten = model.intVar(10);
		IntVar three = model.intVar(3);
		IntVar two = model.intVar(2);
		IntVar x = model.intVar("x", -10, 10, true);
		IntVar y = model.intVar("y", -10, 10, true);
		ten.ge(x.add(y)).post();
		three.lt(x.add(y)).post();
		two.eq(x.sub(y)).post();
		Solution solution = new Solution(model);
		Solver solver = model.getSolver();
		solver.solve();
		solution.record();
	}
	
	@Test
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
