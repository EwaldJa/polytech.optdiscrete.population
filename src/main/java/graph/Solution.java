package graph;

import utils.ForEachWrapper;
import utils.FormatUtils;
import utils.RandUtils;

import java.io.Serializable;
import java.util.*;

/**
 * This class models a graph corresponding to the loaded dataset.
 * It is used in the project to find optimum solutions to the Capacitated Vehicule Routing Problem.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Node
 * @see DeliveryTour
 */
public class Solution implements Serializable, Cloneable {

    private Node _deposit;
    private List<Node> _clients;
    private List<DeliveryTour> _deliveryTours;

    /**
     * Only used when cloning
     */
    private Solution() {}

    /**
     * Constructs a Solution with randomly filled DeliveryTours (max 60% of their capacity)
     * @param deposit the origin Node of all deliveries, the deposit with the orders
     * @param clients all the Nodes instanciated
     */
    public Solution(Node deposit, List<Node> clients) {
        _deposit = deposit;
        _clients = clients;
        _deliveryTours = new ArrayList<>();

        Collections.shuffle(_clients);

        DeliveryTour dt = new DeliveryTour(_deposit);
        for(Node client:_clients) {
            if (client.getOrder() > dt.remainingSpaceInit()) {
                _deliveryTours.add(dt);
                dt = new DeliveryTour(_deposit); }
            dt.append(client);
        }
        _deliveryTours.add(dt);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Node list : \n\n").append(_deposit).append("\n");
        _clients.forEach(node -> sb.append(node).append("\n"));
        sb.append("\nDelivery tours:\n\n");
        _deliveryTours.parallelStream().forEach(dt -> sb.append(dt.toString()));
        return sb.toString();
    }

    /**
     * @return the list of all the DeliveryTours of this Solution
     */
    public List<DeliveryTour> getDeliveryTours() { return _deliveryTours; }

    /**
     * @return the Node corresponding to the starting point of all deliveries
     */
    public Node getDeposit() { return  _deposit; }


    /**
     * @return the total distance of this Solution : the sum of the distance of all of its DeliveryTours
     */
    public double getTotalDistance() {
        double sum = 0.0;
        for(DeliveryTour dt:_deliveryTours) { sum += dt.getTotalDistance(); }
        return sum;
    }

    /**
     * @return a clone of this Solution
     */
    public Solution clone() {
        Solution clone = new Solution();
        clone._deposit = this._deposit.clone();
        clone._clients = Collections.synchronizedList(new ArrayList<>());
        this._clients.parallelStream().forEachOrdered(node -> clone._clients.add(node.clone()));
        clone._deliveryTours = Collections.synchronizedList(new ArrayList<>());
        this._deliveryTours.parallelStream().forEachOrdered(dt -> clone._deliveryTours.add(dt.clone()));
        return clone;
    }

    /**
     * Generates a clone of this solution with one random atomical operation performed on it, so that it is a direct neighbour.
     * Possible operations are :
     *  - switching two randomly chosen Nodes inside a randomly chosen DeliveryTour of the Solution
     *  - switching two Nodes randomly chosen among two randomly chosen DeliveryTours of the Solution
     *  - removing a randomly chosen Node from a randomly chosen DeliveryTour, and putting it in another randomly chosen DeliveryTour
     * @return the generated direct neighbour
     */
    public Solution cloneRandom() {
        Solution clone = clone();
        int randomStyle = RandUtils.randInt(0, 3);
        switch (randomStyle) {
            default:
            case 0:
                int dt_intRndmSwap = RandUtils.randInt(0,clone._deliveryTours.size());
                while (clone._deliveryTours.get(dt_intRndmSwap).getNodes().size() <= 2) {
                    dt_intRndmSwap = RandUtils.randInt(0, clone._deliveryTours.size());
                }
                clone._deliveryTours.get(dt_intRndmSwap).internalSwapRandom();
                return clone;
            case 1:
                //les deux doivent etre de taille > 1
                int dt1_extSwapRand = RandUtils.randInt(0, clone._deliveryTours.size());
                while (clone._deliveryTours.get(dt1_extSwapRand).getNodes().size() <= 1) {
                    dt1_extSwapRand = RandUtils.randInt(0, clone._deliveryTours.size());
                }
                int dt2_extSwapRand = RandUtils.randInt(0, clone._deliveryTours.size(), dt1_extSwapRand);
                while (clone._deliveryTours.get(dt2_extSwapRand).getNodes().size() <= 1) {
                    dt2_extSwapRand = RandUtils.randInt(0, clone._deliveryTours.size(), dt1_extSwapRand);
                }
                clone._deliveryTours.get(dt1_extSwapRand).externalSwap(clone._deliveryTours.get(dt2_extSwapRand));
                return clone;
            case 2:
                //TODO: insérer possiblement au milieu
                //le deuxieme doit etre de taille > 1
                int dt1_chngTour = RandUtils.randInt(0, clone._deliveryTours.size());
                int dt2_chngTour = RandUtils.randInt(0, clone._deliveryTours.size(), dt1_chngTour);
                while (clone._deliveryTours.get(dt2_chngTour).getNodes().size() <= 1) {
                    dt2_chngTour = RandUtils.randInt(0, clone._deliveryTours.size(), dt1_chngTour);
                }
                clone._deliveryTours.get(dt1_chngTour).changeNodeTour(clone._deliveryTours.get(dt2_chngTour));
                return clone;
        }
    }

    /**
     * Generates all the possible one-atomical-operation-far neighbour that are not in the tabuList, returns the best one
     * @param tabuList the List of all tabu Solutions
     * @return the best one-atomical-operation-far neighbour
     */
    public Solution getBestNeighbour(List<Solution> tabuList) {
        Solution bestNeighbour = null;
        Solution clone = null;
        double bestDistance = Double.POSITIVE_INFINITY;

        /*All internalSwap neighbours*/
        clone = clone();
        ForEachWrapper<Solution> cloneWrap = new ForEachWrapper<>(clone);
        ForEachWrapper<Solution> dtcloneWrap = new ForEachWrapper<>(clone());
        ForEachWrapper<Double> bestDistanceWrapper = new ForEachWrapper<>(bestDistance);
        ForEachWrapper<Solution> bestNeighbourWrapper = new ForEachWrapper<>(bestNeighbour);
        clone._deliveryTours.parallelStream().forEach(deliveryTour -> {
            if (deliveryTour.getNodesNb() > 1) {
                int dt_index = cloneWrap.value._deliveryTours.indexOf(deliveryTour);
                DeliveryTour dtClone = null;
                for(int i = 1; i < deliveryTour.getNodesNb() - 1; i++) {
                    for (int j = i + 1; j < deliveryTour.getNodesNb(); j++) {
                        dtClone = deliveryTour.clone();
                        dtClone.internalSwap(i,j);
                        Solution subclone = dtcloneWrap.value.clone();
                        subclone._deliveryTours.remove(dt_index);
                        subclone._deliveryTours.add(dt_index, dtClone);
                        if (!tabuList.contains(subclone) && subclone.getTotalDistance() < bestDistanceWrapper.value) {
                            bestDistanceWrapper.value = subclone.getTotalDistance();
                            bestNeighbourWrapper.value = subclone.clone(); } } } }
        });

        /*All externalSwap neighbours*/
        clone = clone();
        for(int i = 0; i < clone._deliveryTours.size() - 1; i++) {
            DeliveryTour dt_i = clone._deliveryTours.get(i);
            int i_remainingSpace = dt_i.remainingSpace();
            for(int j = i + 1; j < clone._deliveryTours.size(); j++) {
                DeliveryTour dt_j = clone._deliveryTours.get(j);
                int j_remainingSpace = dt_j.remainingSpace();
                for(int node_i_index = 1; node_i_index < dt_i.getNodesNb(); node_i_index++) {
                    Node node_i = dt_i.getNode(node_i_index);
                    for(int node_j_index = 1; node_j_index < dt_j.getNodesNb(); node_j_index++) {
                        Node node_j = dt_j.getNode(node_j_index);
                        if (node_j.getOrder() - node_i.getOrder() <= i_remainingSpace && node_i.getOrder() - node_j.getOrder() <= j_remainingSpace) {
                            Solution subclone = clone.clone();
                            subclone._deliveryTours.get(i).externalSwap(node_i_index, subclone._deliveryTours.get(j), node_j_index);
                            if (!tabuList.contains(subclone) && subclone.getTotalDistance() < bestDistance) {
                                bestNeighbour = subclone.clone();
                                bestDistance = subclone.getTotalDistance(); } } } } } }


        /*All changeTour neighbours*/
        clone = clone();
        for(int i = 0; i < clone._deliveryTours.size() - 1; i++) {
            DeliveryTour dt_i = clone._deliveryTours.get(i);
            for(int j = i + 1; j < clone._deliveryTours.size(); j++) {
                DeliveryTour dt_j = clone._deliveryTours.get(j);
                int j_remainingSpace = dt_j.remainingSpace();
                for(int node_i_index = 1; node_i_index < dt_i.getNodesNb(); node_i_index++) {
                    if (dt_i.getNode(node_i_index).getOrder() <= j_remainingSpace) {
                        for(int node_j_index = 1; node_j_index <= dt_j.getNodesNb(); node_j_index++) {
                            Solution subclone = clone.clone();
                            subclone._deliveryTours.get(j).changeNodeTour(subclone._deliveryTours.get(i), node_i_index, node_j_index);
                            if (!tabuList.contains(subclone) && subclone.getTotalDistance() < bestDistance) {
                                bestNeighbour = subclone.clone();
                                bestDistance = subclone.getTotalDistance();
                            }
                        }
                    }
                }
            }
        }

        return bestNeighbour;
    }

    /**
     * Removes empty DeliveryTours (with only the deposit in them)
     * @return the Solution without its empty DeliveryTour
     */
    public Solution finaliserSolution() {
        List<DeliveryTour> new_dts = new ArrayList<>();
        _deliveryTours.parallelStream().forEachOrdered(dt -> {if (!(FormatUtils.round(dt.getTotalDistance(), 1) == 0.0 && dt.getTotalOrders() == 0)){new_dts.add(dt);}});
        this._deliveryTours = new_dts;
        return this;
    }

    /**
     * Checks wether this Solution is better than the given one
     * @param other the other Solution to compare this one with
     * @return boolean, true if this solution is better, false if other Solution is better
     */
    public boolean isBetterThan(Solution other) {
        if (other == null) {
            return true; }
        else {
            return this.getTotalDistance() < other.getTotalDistance(); }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution other = (Solution) o;
        return Objects.equals(_deposit, other._deposit) &&
                _clients.equals(other._clients) &&
                _deliveryTours.equals(other._deliveryTours);
    }

}
