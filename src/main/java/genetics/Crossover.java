package genetics;

import graph.Node;
import graph.Solution;
import utils.RandUtils;

import java.util.ArrayList;
import java.util.List;

public final class Crossover {

    public static List<Solution> doCrossover(Solution parent1, Solution parent2) {
        List<Node> p1Nodes = parent1.getFlattenedOrderedClients(); List<Node> p2Nodes = parent2.getFlattenedOrderedClients();
        List<Node> c1Nodes = new ArrayList<>(), c2Nodes = new ArrayList<>();
        int index1 = RandUtils.randInt(0, p1Nodes.size());
        int index2 = RandUtils.randInt(1,p1Nodes.size(), index1);
        int first,second;
        if (index1 > index2) { first = index2; second = index1; }
        else { first = index1; second = index2; }

        /*CROSSOVER*/

        /*Add the mid sequence*/
        c1Nodes.addAll(p2Nodes.subList(first, second + 1));
        c2Nodes.addAll(p1Nodes.subList(first, second + 1));

        /*Temp var to avoid using getters several times*/
        Node p1Nodei, p2Nodei;

        /*Add first sequence without duplicate*/
        for(int i = first - 1; i >= 0; i--) {
            p1Nodei = p1Nodes.get(i);
            p2Nodei = p2Nodes.get(i);
            if (!c1Nodes.contains(p1Nodei)) {
                c1Nodes.add(0, p1Nodei);
            }
            if (!c2Nodes.contains(p2Nodei)) {
                c2Nodes.add(0, p2Nodei);
            }
        }

        /*Add last sequence without duplicate*/
        for(int i = 0; i < p1Nodes.size(); i++) {
            p1Nodei = p1Nodes.get(i);
            p2Nodei = p2Nodes.get(i);
            if (!c1Nodes.contains(p1Nodei)) {
                c1Nodes.add(p1Nodei);
            }
            if (!c2Nodes.contains(p2Nodei)) {
                c2Nodes.add(p2Nodei);
            }
        }

        /*Reconstructs the 2 childs*/
        List<Solution> childs = new ArrayList<>();
        childs.add(new Solution(c1Nodes, parent1.getDeposit()));
        childs.add(new Solution(c2Nodes, parent2.getDeposit()));
        return childs;
    }
}
