package genetics;

import graph.Couple;
import graph.Solution;
import utils.CustomDouble;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgo {

    private int POPULATION_SIZE, GENERATIONS_NB;
    private double PROBA_MUTATION;

    private List<Couple<Solution, CustomDouble>> _population;

    private Solution _bestSolution;


    public GeneticAlgo(Solution file, int populationSize, int generationsNb, double probaMutation) {
        this._population = new ArrayList<>();
        this.POPULATION_SIZE = populationSize;
        this.GENERATIONS_NB = generationsNb;
        this.PROBA_MUTATION = probaMutation;
        _population.add(new Couple<>(file, new CustomDouble(1/file.getTotalDistance())));
        Solution s;
        for(int i = 1; i < POPULATION_SIZE; i++) {
            s = new Solution(file.getDeposit(),file.getClients());
            _population.add(new Couple<>(s, new CustomDouble(1/s.getTotalDistance())));
        }
    }

    public Solution process() {
        return _bestSolution;
    }

    private void selectPopulation() {
        double sumInvDist = 0.0;
        List<Couple<Solution, CustomDouble>> newpop = new ArrayList<>();
        for(Couple<Solution, CustomDouble> couple:_population) {
            sumInvDist += couple.getValue().value;
        }
        for(int i = 0; i < POPULATION_SIZE; i++) {
            
        }
    }



}
