package edu.jhuapl.aspire.puzzle;

import static org.junit.jupiter.api.Assertions.*;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolverTest {

	@Test
	void simpleIntSolver() {
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

}
