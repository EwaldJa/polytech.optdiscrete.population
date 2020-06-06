package algo;

import graph.Solution;
import utils.MathUtils;
import utils.RandUtils;

/**
 * This class is used to perform Simulated Annealing algorithm
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class SimulatedAnnealing extends Algorithm {

    private double _temperature, _coolingFactor;
    private int _neighbourNb;

    /**
     * Constructs an instance of the algorithm with the specified parameters
     * @param temperature the initial temperature
     * @param coolingFactor the cooling factor
     * @param neighbourNb the number of random neighbour to generate for each temperature change
     */
    public SimulatedAnnealing(double temperature, double coolingFactor, int neighbourNb) {
        this._temperature = temperature;
        this._coolingFactor = coolingFactor;
        this._neighbourNb = neighbourNb;
    }

    /**
     * returns the result of the algorithm on the given Solution
     * @param current the Solution to process
     */
    @Override
    public Solution processCurrent(Solution current) {
        Solution bestSolution = current.clone();
        double bestDistance = bestSolution.getTotalDistance();
        Solution baseSolution = current.clone();
        double baseDistance = baseSolution.getTotalDistance();
        for(double t = _temperature; t > 1; t *= _coolingFactor) {
            for(int i = 0; i < _neighbourNb; i++) {
                Solution rndmNeighbour = baseSolution.cloneRandom();
                double rndmDist = rndmNeighbour.getTotalDistance();

                if (rndmDist < baseDistance) {
                    baseSolution = rndmNeighbour;
                    baseDistance = rndmDist;
                    if (rndmDist < bestDistance) {
                        bestSolution = rndmNeighbour;
                        bestDistance = rndmDist;
                    } }
                else if (RandUtils.randDouble(0,1) <= MathUtils.exp(baseDistance, rndmDist, t)) {
                    baseSolution = rndmNeighbour;
                    baseDistance = rndmDist;
                }
            }
            System.out.println("Temperature : " + t + ", bestDistance : " + bestDistance + ", baseDistance : " + baseDistance);
        }
        return bestSolution;
    }
}
