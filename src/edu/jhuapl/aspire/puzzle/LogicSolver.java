package edu.jhuapl.aspire.puzzle;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Assertions;

public class LogicSolver {

	private void logicSolver() {
		Model model = new Model();
		IntVar x = model.intVar("x", -10,10);
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
	
	public static void main(String[] args) {
		

	}	

}
