import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.DefaultGPFitnessEvaluator;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class Classifier {

	public static List<double[]> readData(String dataFileName) throws FileNotFoundException {
		File dataFile = new File(dataFileName);
		Scanner sc = new Scanner(dataFile);
		List<double[]> patients = new ArrayList<double[]>();
		while (sc.hasNext()) {
			String patient = sc.nextLine();
			String[] valueStrings = patient.split(",");
			double[] values = new double[valueStrings.length - 1];
			for (int i = 1; i < valueStrings.length; i++) {
				try {
					values[i - 1] = Double.valueOf(valueStrings[i]);
				} catch (NumberFormatException e) {
					if (valueStrings[i].equals("?")) {
						values[i - 1] = -1;
					} else {
						throw e;
					}
				}
			}
			patients.add(values);
		}
		sc.close();
		return patients;
	}

	public static void main(String[] args) throws InvalidConfigurationException, FileNotFoundException {
		GPConfiguration config = new GPConfiguration();
		config.setGPFitnessEvaluator(new DefaultGPFitnessEvaluator());
		config.setMaxInitDepth(6);
		config.setPopulationSize(150);
		List<double[]> trainingPatients = readData(args[0]);
		ClassifierFitnessFunction trainingFunction = new ClassifierFitnessFunction(trainingPatients);
		config.setFitnessFunction(trainingFunction);
		config.setMutationProb(0.05f);
		config.setReproductionProb(0.05f);
		GPGenotype gp = create(config);
		// Do 100 evolutions in a row.
		// ---------------------------
		gp.evolve(75);
		// Output best solution found.
		// ---------------------------
		IGPProgram best = gp.getAllTimeBest();
		gp.outputSolution(best);
		trainingFunction.printInfo = true;
		trainingFunction.evaluate(best);

		System.out.println("\nClassifing the Test Set");
		List<double[]> testPatients = readData(args[1]);
		ClassifierFitnessFunction testFunction = new ClassifierFitnessFunction(testPatients);
		testFunction.printInfo = true;
		double testFitness = testFunction.evaluate(best);
		System.out.printf("Fitness on test set: %f", testFitness);

	}

	private static GPGenotype create(GPConfiguration a_conf) throws InvalidConfigurationException {
		Class[] types = { CommandGene.DoubleClass };
		Class[][] argTypes = { {} };
		// Define the commands and terminals the GP is allowed to use.
		// -----------------------------------------------------------
		CommandGene[][] nodeSets = { { Variable.create(a_conf, "Clump_Thickness", CommandGene.DoubleClass),
				Variable.create(a_conf, "Uniformity_of_Cell_Size", CommandGene.DoubleClass),
				Variable.create(a_conf, "Uniformity_of_Cell_Shape", CommandGene.DoubleClass),
				Variable.create(a_conf, "Marginal_Adhesion", CommandGene.DoubleClass),
				Variable.create(a_conf, "Single_Epithelial_Cell_Size", CommandGene.DoubleClass),
				Variable.create(a_conf, "Bare_Nuclei", CommandGene.DoubleClass),
				Variable.create(a_conf, "Bland_Chromatin", CommandGene.DoubleClass),
				Variable.create(a_conf, "Normal_Nucleoli", CommandGene.DoubleClass),
				Variable.create(a_conf, "Mitoses", CommandGene.DoubleClass), new Add(a_conf, CommandGene.DoubleClass),
				new Subtract(a_conf, CommandGene.DoubleClass), new Multiply(a_conf, CommandGene.DoubleClass),
				new Divide(a_conf, CommandGene.DoubleClass), new Pow(a_conf, CommandGene.DoubleClass),
				new Abs(a_conf, CommandGene.DoubleClass), new Log(a_conf, CommandGene.DoubleClass),
				new Sine(a_conf, CommandGene.DoubleClass), new Exp(a_conf, CommandGene.DoubleClass),
				new Cosine(a_conf, CommandGene.DoubleClass),
				new Terminal(a_conf, CommandGene.DoubleClass, -10.0, 10.0, false),
				new Terminal(a_conf, CommandGene.DoubleClass, -10.0, 10.0, true), } };
		// Create genotype with initial population.
		// Allow max. 100 nodes within one program.
		// ----------------------------------------
		return GPGenotype.randomInitialGenotype(a_conf, types, argTypes, nodeSets, 100, true);
	}

}
