import java.util.List;

import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class ClassifierFitnessFunction extends GPFitnessFunction {

	private List<double[]> patients;
	protected boolean printInfo = false;

	public ClassifierFitnessFunction(List<double[]> patients) {
		this.patients = patients;
	}

	@Override
	protected double evaluate(IGPProgram ind) {
		double numCorrect= 0;
		int numBenign = 0;
		int numMalignant = 0;
		int numMisBenign = 0;
		int numMisMalignant = 0;
		Object[] noargs = new Object[0];
		for (double[] p : patients) {
			for (int i = 0; i < p.length - 1; i++){
				CommandGene var = ind.getNodeSets()[0][i];
				if(var instanceof Variable){
					Variable v = (Variable) var;
					v.set(p[i]);
				}else {
					throw new RuntimeException("First nodes in node set are not Variables");
				}
			}
			double result = ind.execute_double(0, noargs);
			if(p[p.length-1] == 2.0){
				numBenign++;
				if(result < 0) numCorrect++;
				else numMisBenign++;
			}
			if(p[p.length-1] == 4.0){
				numMalignant++;
				if(result > 0) numCorrect++;
				else numMisMalignant++;
			}
		}
		if(printInfo){
			System.out.printf("Correct Classifications: %.0f / %d \n", numCorrect, patients.size());
			System.out.printf("Misclassified benign tumors: %d / %d \n", numMisBenign, numBenign);
			System.out.printf("Misclassified malignant tumors: %d / %d \n", numMisMalignant, numMalignant);
		}
		return numCorrect / patients.size();
	}

}
