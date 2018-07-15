import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;
import org.jgap.gp.*;
import org.jgap.gp.function.*;

public class Regression {

	public static Float[][] readTable(String tableFileName) throws FileNotFoundException {

		File tableFile = new File(tableFileName);
		Scanner sc = new Scanner(tableFile);
		sc.nextLine();
		sc.nextLine();
		List<Float> xFromFile = new ArrayList<Float>();
		List<Float> yFromFile = new ArrayList<Float>();
		while (sc.hasNext()) {
			xFromFile.add(sc.nextFloat());
			yFromFile.add(sc.nextFloat());
			sc.nextLine();
		}
		sc.close();
		return new Float[][] { xFromFile.toArray(new Float[xFromFile.size()]),
				yFromFile.toArray(new Float[yFromFile.size()]), };
	}

	public static void main(String[] args) throws InvalidConfigurationException, FileNotFoundException {
		GPConfiguration config = new GPConfiguration();

		// We use a delta fitness evaluator because we compute a defect rate,
		// not
		// a point score!
		// ----------------------------------------------------------------------
		config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		config.setMaxInitDepth(6);
		config.setPopulationSize(300);
		Float[][] xAndY = readTable(args[0]);
		config.setFitnessFunction(new RegressionFitnessFunction(xAndY[0], xAndY[1]));
		config.setMutationProb(0.05f);
		config.setReproductionProb(0.05f);
		GPGenotype gp = create(config);
		// Do 100 evolutions in a row.
		// ---------------------------
		gp.evolve(100);
		// Output best solution found.
		// ---------------------------
		gp.outputSolution(gp.getAllTimeBest());

	}

	public static GPGenotype create(GPConfiguration a_conf) throws InvalidConfigurationException {
		Class[] types = { CommandGene.FloatClass };
		Class[][] argTypes = { {} };
		// Define the commands and terminals the GP is allowed to use.
		// -----------------------------------------------------------
		CommandGene[][] nodeSets = {
				{ Variable.create(a_conf, "X", CommandGene.FloatClass), new Add(a_conf, CommandGene.FloatClass),
						new Subtract(a_conf, CommandGene.FloatClass), new Multiply(a_conf, CommandGene.FloatClass),
						new Divide(a_conf, CommandGene.FloatClass), new Pow(a_conf, CommandGene.FloatClass),
						// Use terminal with possible value from 2.0 to 10.0
						// decimal
						new Terminal(a_conf, CommandGene.FloatClass,-10.0d, 10.0d, true),} };
		// Create genotype with initial population.
		// Allow max. 100 nodes within one program.
		// ----------------------------------------
		return GPGenotype.randomInitialGenotype(a_conf, types, argTypes, nodeSets, 100, true);
	}

}
