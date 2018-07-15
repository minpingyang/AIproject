import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class RegressionFitnessFunction extends GPFitnessFunction {

	private Float[] x;
	private Float[] y;

	public RegressionFitnessFunction(Float[] x, Float[] y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	protected double evaluate(IGPProgram ind) {
		double error = 0.0f;
		Object[] noargs = new Object[0];
		// Evaluate function for input numbers 0 to 20.
		// --------------------------------------------
		for (int i = 0; i < 20; i++) {
			// Provide the variable X with the input number.
			// -------------------------------------------------------------
			CommandGene var = ind.getNodeSets()[0][0];
			if(var instanceof Variable){
				Variable v = (Variable) var;
				v.set(x[i]);
			}else {
				throw new RuntimeException("First node in node set was not a Variable");
			}
			try {
				// Execute the GP program representing the function to be evolved.
				// As in method create(), the return type is declared as float (see
				// declaration of array "types").
				// ----------------------------------------------------------------
				double result = ind.execute_float(0, noargs);
				// Sum up the error between actual and expected result to get a defect
				// rate.
				// -------------------------------------------------------------------
				double mod = 1.0;
				if (x[i] >= -0.5 && x[i] < 0.5) mod = 4*Math.pow((x[i]+0.5), 2.0) + 1.0;
				if (x[i] >= 0.5 && x[i] <= 1.5) mod = 4*Math.pow((x[i]-1.5), 2.0) + 1.0;
				error += Math.abs(result - y[i])*mod;
				// If the error is too high, stop evlauation and return worst error
				// possible.
				// ----------------------------------------------------------------
				if (Double.isInfinite(error)) {
					return Double.MAX_VALUE;
				}
			} catch (ArithmeticException ex) {
				// This should not happen, some illegal operation was executed.
				// ------------------------------------------------------------
				System.out.println("x = " + x[i].floatValue());
				System.out.println(ind);
				throw ex;
			}
		}
		if(!Double.isNaN(error)){
			error = (double)Math.round(error * 1000d) / 1000d;
			error = error * 100000;
			error = error + ind.getChromosome(0).getDepth(0);
		}
		return error;
	}

}
