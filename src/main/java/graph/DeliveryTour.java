package graph;

import utils.FormatUtils;

import java.io.Serializable;
import java.util.List;

/**
 * This class models a delivery tour, with a subset of the Nodes of a Solution
 * See NodeMapLight for the documentation
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see NodeMapLight
 * @see Solution
 * @see Edge
 * @see Node
 */
public class DeliveryTour implements Serializable, Cloneable {

    public static final int MAX_CAPACITY = 100, MAX_CAPACITY_INIT = 60;

    private NodeMapLight _tour;

    private DeliveryTour() {}

    public DeliveryTour(Node deposit) {
        _tour = new NodeMapLight(deposit);
    }

    public DeliveryTour append(Node n) {
        _tour.put(n);
        return this;
    }

    public int remainingSpace() { return MAX_CAPACITY - _tour.getTotalOrders(); }

    public int remainingSpaceInit() { return MAX_CAPACITY_INIT - _tour.getTotalOrders(); }

    public int getTotalOrders() { return _tour.getTotalOrders(); }

    public List<Node> getNodes() { return _tour.getNodes(); }

    public int getNodesNb() { return _tour.getSize(); }

    public Node getNode(int index) { return _tour.getNode(index); }

    public List<Edge> getEdges() { return _tour.getEdges(); }

    public void internalSwapRandom() { _tour.internalSwapRandom(); }

    public void externalSwap(DeliveryTour other) { _tour.externalSwap(other._tour); }

    public void changeNodeTour(DeliveryTour other) { _tour.changeNodeTour(other._tour); }

    public void internalSwap(int index1, int index2) { _tour.internalSwap(index1, index2); }

    public void externalSwap(int localIndex, DeliveryTour other, int otherIndex) { _tour.externalSwap(localIndex, other._tour, otherIndex); }

    public void changeNodeTour(DeliveryTour other, int otherindex, int localindex) { _tour.changeNodeTour(other._tour, otherindex, localindex); }

    public double getTotalDistance() { return _tour.getTotalDistance(); }

    @Override
    public String toString() {
        return "Remaining capacity : " + remainingSpace() + "\nDistance : " + FormatUtils.round(_tour.getTotalDistance(),2) + "\nCalculated distance : " + FormatUtils.round(_tour.calculateTotalDistance(),2) + "\n" + _tour.toString() + "\n\n";
    }

    public DeliveryTour clone() {
        DeliveryTour clone = new DeliveryTour();
        clone._tour = this._tour.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryTour that = (DeliveryTour) o;
        return _tour.equals(that._tour);
    }
}
