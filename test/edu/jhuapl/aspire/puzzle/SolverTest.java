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
		System.out.println(solution.getIntVal(x));
		System.out.println(solution.getIntVal(y));
	}

}
