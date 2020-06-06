package genetics;

import graph.Couple;
import graph.Solution;
import utils.CustomDouble;
import utils.FormatUtils;
import utils.RandUtils;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgo {

    private int POPULATION_SIZE, GENERATIONS_NB;
    private double PROBA_MUTATION;
    private boolean LOOK_FOR_LOCAL_BEST;

    private List<Couple<Solution, CustomDouble>> _population;

    private Solution _bestSolution;
    private double _bestDistance;


    public GeneticAlgo(Solution file, int populationSize, int generationsNb, double probaMutation, boolean lookForLocalBest) {
        this._population = new ArrayList<>();
        this.POPULATION_SIZE = populationSize;
        this.GENERATIONS_NB = generationsNb;
        this.PROBA_MUTATION = probaMutation;
        this.LOOK_FOR_LOCAL_BEST = lookForLocalBest;
        this._bestSolution = file;
        this._bestDistance = file.getTotalDistance();
        _population.add(new Couple<>(file, new CustomDouble(1/file.getTotalDistance())));
        Solution s;
        for(int i = 1; i < POPULATION_SIZE; i++) {
            s = new Solution(file.getDeposit(),file.getClients());
            _population.add(new Couple<>(s, new CustomDouble(1/s.getTotalDistance() + _population.get(i-1).getValue().value)));
        }
    }

    public Solution process() {
        for(int genIndex = 0; genIndex < GENERATIONS_NB; genIndex++) {
            selectPopulation();
            doCrossover();
            doMutation();
            updateValues();
            if (genIndex % 1 == 0) {
                System.out.println("Generation : " + genIndex + ", best distance : " + FormatUtils.round(_bestDistance, 2)); }
        }
        return _bestSolution;
    }

    /**
     * Roulette biaisee
     */
    private void selectPopulation() {
        List<Couple<Solution, CustomDouble>> newpop = new ArrayList<>();
        double randPop;
        double maxValue = _population.get(POPULATION_SIZE - 1).getValue().value;
        Couple<Solution, CustomDouble> precedingCouple, couple;
        for(int i = 0; i < POPULATION_SIZE; i++) {
            randPop = RandUtils.randDouble(0, maxValue);
            couple = _population.get(0);
            if (randPop < couple.getValue().value) {
                newpop.add(couple); }
            else {
                for(int index = 1; index < POPULATION_SIZE; index++) {
                    precedingCouple = _population.get(index-1);
                    couple = _population.get(index);
                    if (randPop >= precedingCouple.getValue().value && randPop < couple.getValue().value) {
                        newpop.add(couple);
                        break; } } }
        }
        _population = newpop;
    }

    private void doCrossover() {
        List<Couple<Solution, CustomDouble>> childs = new ArrayList<>();
        for(int i = 0; i < POPULATION_SIZE; i += 2) {
            Crossover.doCrossover(_population.get(i).getKey(), _population.get(i + 1).getKey()).forEach(solution -> childs.add(new Couple<>(solution, new CustomDouble(1/solution.getTotalDistance()))));
        }
        if (POPULATION_SIZE%2 == 1) {
            Crossover.doCrossover(_population.get(POPULATION_SIZE - 1).getKey(), _population.get(RandUtils.randInt(0, POPULATION_SIZE - 1)).getKey()).forEach(solution -> childs.add(new Couple<>(solution, new CustomDouble(1/solution.getTotalDistance()))));
        }
        if (LOOK_FOR_LOCAL_BEST) {
            for(Couple<Solution, CustomDouble> child:childs) {
                child.setKey(getLocalBest(child.getKey()));
                child.getValue().value = (1/child.getKey().getTotalDistance());
            }
        }
        _population = childs;
    }

    private void doMutation() {
        double dist;
        for(Couple<Solution, CustomDouble> couple:_population) {
            dist = couple.getKey().getTotalDistance();
            if(dist < _bestDistance) {
                _bestDistance = dist;
                _bestSolution = couple.getKey().clone();
            }
            if (RandUtils.randDouble(0, 1) < PROBA_MUTATION) {
                couple.setKey(couple.getKey().cloneRandom());
            }
        }
    }

    private void updateValues() {
        Couple<Solution, CustomDouble> couple;
        couple = _population.get(0);
        double dist = couple.getKey().getTotalDistance();
        if(dist < _bestDistance) {
            _bestDistance = dist;
            _bestSolution = couple.getKey().clone();
        }
        for(int i = 1; i < POPULATION_SIZE; i++) {
            couple = _population.get(i);
            dist = couple.getKey().getTotalDistance();
            if(dist < _bestDistance) {
                _bestDistance = dist;
                _bestSolution = couple.getKey().clone();
            }
            couple.getValue().value += _population.get(i - 1).getValue().value;
        }
    }

    private Solution getLocalBest(Solution s) {
        List<Solution> emptyTabu = new ArrayList<>();
        Solution bestNeighbour = s.getBestNeighbour(emptyTabu), lastNghb=s;
        double nghbDist = bestNeighbour.getTotalDistance(), lastDist = s.getTotalDistance();
        while (nghbDist < lastDist) {
            lastDist = nghbDist;
            lastNghb = bestNeighbour;
            bestNeighbour = bestNeighbour.getBestNeighbour(emptyTabu);
        }
        return lastNghb;
    }



}
