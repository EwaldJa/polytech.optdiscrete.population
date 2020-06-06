package graph;

import utils.ForEachWrapper;
import utils.RandUtils;

import java.io.Serializable;
import java.util.*;

/**
 * This class establish the links between the Nodes of a DeliveryTour.
 * Deprecated, consider using NodeMapLight instead
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see NodeMapLight
 * @see DeliveryTour
 * @see Solution
 * @see Edge
 * @see Node
 */
@Deprecated
public class NodeMap implements Serializable {

    private Node _deposit, _lastNode;
    private int _totalOrders;
    private double _totalDistance;

    /*First Edge of Couple : incoming edge, and the second is the leaving edge (for instance fist one is between n1 and n2 and second one between n2 and n3)*/
    private FlexMap<Node, Couple<Edge, Edge>> _tour;

    private NodeMap(){}

    public NodeMap(Node deposit) {
        _deposit = deposit;
        _tour = new FlexMap<>();
        Edge initEdge = new Edge(_deposit, _deposit);
        _tour.put(_deposit, new Couple<>(initEdge, initEdge.clone()));
        _lastNode = _deposit;
        _totalDistance = 0.0;
    }

    /**
     * Adds the node to the delivery tour, at the last position, meaning that the next node following the edge after the one to add will be the origin node
     * @param n the node to add
     */
    public void put(Node n) {
        Edge last_with_n = _tour.get(_lastNode).getValue();
        last_with_n.changeN2(n); /*makes the node that was the last before be the one preceding the node we are adding*/
        Edge n_with_deposit = _tour.get(_deposit).getKey();
        n_with_deposit.changeN1(n); /*makes the departing edge of the newly added node leading to the origin node*/
        _tour.put(n, new Couple<>(last_with_n.clone(), n_with_deposit.clone()));
        //_totalDistance += _lastNode.getDistance(n) + _deposit.getDistance(n);
        _lastNode = n;
        _totalOrders += n.getOrder(); /*keeps tracking of capacity up to date*/
    }

    /**
     * @return a List of all the nodes of this delivery tour
     */
    public List<Node> getNodes() { return new ArrayList<>(_tour.keySet()); }

    /**
     * @return a List of all the edges of this delivery tour
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        _tour.values().parallelStream().forEachOrdered(cp -> edges.add(cp.getValue()));
        return edges;
    }


    /**
     * Swaps the two specified Nodes in this delivery tour. They both must be Nodes of this tour.
     * @param n one of the nodes to swap
     * @param m the other node to swap
     */
    public void internalSwapSafe(Node n, Node m) {
        Couple<Edge, Edge> n_1_edges = null;    //n-1
        Couple<Edge, Edge> n_edges = null;      //n
        Couple<Edge, Edge> n_2_edges = null;   //n+1
        Couple<Edge, Edge> m_1_edges = null;    //m-1
        Couple<Edge, Edge> m_edges = null;      //m
        Couple<Edge, Edge> m_2_edges = null;   //m+1
        Node n1,n2,m1,m2;

        try {
            if (n.equals(_lastNode)) {
                _lastNode = m; }
            else if (m.equals(_lastNode)) {
                _lastNode = n; }

            n_edges = _tour.get(n);
            n1 = n_edges.getKey().getN1();
            n2 = n_edges.getValue().getN2();
            n_1_edges = _tour.get(n1);
            n_2_edges = _tour.get(n2);

            m_edges = _tour.get(m);
            m1 = m_edges.getKey().getN1();
            m2 = m_edges.getValue().getN2();
            m_1_edges = _tour.get(m1);
            m_2_edges = _tour.get(m2);

            //_totalDistance -= n.getDistance(n1);
            //_totalDistance -= n.getDistance(n2);
            //_totalDistance -= m.getDistance(m1);
            //_totalDistance -= m.getDistance(m2);

            //_totalDistance += m.getDistance(n1);
            //_totalDistance += m.getDistance(n2);
            //_totalDistance += n.getDistance(m1);
            //_totalDistance += n.getDistance(m2);

            n_1_edges.getValue().changeN2(m);
            n_2_edges.getKey().changeN1(m);
            n_edges.getKey().changeN2(m);
            n_edges.getValue().changeN1(m);

            m_1_edges.getValue().changeN2(n);
            m_2_edges.getKey().changeN1(n);
            m_edges.getKey().changeN2(n);
            m_edges.getValue().changeN1(n);

            _tour.swapKeys(n, m);

        } catch (Exception e) {
            System.err.println("internalSwap");
            e.printStackTrace();
            System.err.println(this.toString());
        }
    }

    /**
     * Swaps two Nodes of this delivery tour chosen randomly among all
     */
    public void internalSwapRandom() {
        int indexN1 = RandUtils.randInt(1, _tour.size());
        int indexN2 = RandUtils.randInt(1, _tour.size());
        while (indexN1 == indexN2) {
            indexN2 = RandUtils.randInt(1, _tour.size());
        }
        List<Node> nodes = new ArrayList<>(_tour.keySet());
        internalSwapSafe(nodes.get(indexN1), nodes.get(indexN2));
    }

    /**
     * Swaps two Nodes chosen randomly, one among other's, one among this instance's
     * @param other another NodeMap
     */
    public void externalSwapRandom(NodeMap other) {
        Node otherNode = new ArrayList<>(other._tour.keySet()).get(RandUtils.randInt(1, other._tour.size()));
        Node localNode = new ArrayList<>(this._tour.keySet()).get(RandUtils.randInt(1, this._tour.size()));
        if (localNode.getOrder() - otherNode.getOrder() > (DeliveryTour.MAX_CAPACITY - this.getTotalOrders()) ||
                otherNode.getOrder() - localNode.getOrder() > (DeliveryTour.MAX_CAPACITY - other.getTotalOrders())) { return; }
        else {
            this.replaceNodeSafe(localNode, otherNode);
            this._totalOrders += otherNode.getOrder() - localNode.getOrder();
            other.replaceNodeSafe(otherNode, localNode);
            other._totalOrders += localNode.getOrder() - otherNode.getOrder(); }
    }


    /**
     * Replaces old_n by new_n in the nodes of the delivery tour
     * @param old_n the Node to be replace
     * @param new_n the Node replacing
     */
    private void replaceNodeSafe(Node old_n, Node new_n) {
        Couple<Edge, Edge> old_1_edges = null;  //n-1
        Couple<Edge, Edge> old_edges = null;
        Couple<Edge, Edge> old_2_edges = null;  //n+1
        Node n1,n2;
        try {
            if (old_n.equals(_lastNode)) {
                _lastNode = new_n; }
            old_edges = _tour.get(old_n);
            n1 = old_edges.getKey().getN1();
            n2 = old_edges.getValue().getN2();
            old_1_edges = _tour.get(n1);
            old_2_edges = _tour.get(n2);

            //_totalDistance -= old_n.getDistance(n1);
            //_totalDistance -= old_n.getDistance(n2);
            //_totalDistance += new_n.getDistance(n1);
            //_totalDistance += new_n.getDistance(n2);

            old_1_edges.getValue().changeN2(new_n);
            old_2_edges.getKey().changeN1(new_n);
            old_edges.getKey().changeN2(new_n);
            old_edges.getValue().changeN1(new_n);

            _tour.replaceKey(old_n, new_n);
        }catch (Exception e) {
            System.err.println("replaceNode");
            e.printStackTrace();
            System.err.println(this.toString());
            System.err.println(old_edges.toString());
            System.err.println(old_edges.getKey().toString());
            System.err.println(old_edges.getValue().toString());
            System.err.println(old_n);
            System.err.println(new_n);
        }
    }

    /**
     * Removes a Node from other and puts it in current instance
     * @param other another NodeMap
     */
    public void changeNodeTour(NodeMap other) {
        Node otherNode = null;
        try {
            otherNode = new ArrayList<>(other._tour.keySet()).get(RandUtils.randInt(1, other._tour.size()));
            if (otherNode.getOrder() > (DeliveryTour.MAX_CAPACITY - this.getTotalOrders())) { return; }
            else {
                this.put(otherNode);
                other.removeSafe(otherNode); }
        } catch (Exception e) {
            System.err.println("changeNodeTour");
            e.printStackTrace();
            System.err.println(this.toString());
            System.err.println(other.toString());
            System.err.println(otherNode);
        }
    }


    /**
     * Removes the specified Node from the delivery tour
     * @param n the Node to delete
     */
    public void removeSafe(Node n) {
        Couple<Edge, Edge> n_1_edges = null;    //n-1
        Couple<Edge, Edge> n_edges = null;
        Couple<Edge, Edge> n_2_edges = null;    //n+1
        Node n1,n2;
        try {
            n_edges = _tour.get(n);
            n1 = n_edges.getKey().getN1();
            n2 = n_edges.getValue().getN2();
            n_1_edges = _tour.get(n1);
            n_2_edges = _tour.get(n2);


            //_totalDistance -= n.getDistance(n1);
            //_totalDistance -= n.getDistance(n2);

            if (n.equals(_lastNode)) {
                _lastNode = n1;
            }

            n_1_edges.getValue().changeN2(n2);
            n_2_edges.getKey().changeN1(n1);

            _totalOrders -= n.getOrder();
            _tour.remove(n);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(this.toString());
        }
    }

    /**
     * @return the sum of the orders of all the client of this delivery tour
     */
    public int getTotalOrders() { return _totalOrders; }

    public double getTotalDistance() {
        //return _totalDistance;
        return calculateTotalDistance();
    }

    public double calculateTotalDistance() {
        ForEachWrapper<Double> sum = new ForEachWrapper<>(0.0);
        getEdges().parallelStream().forEachOrdered(edge -> sum.value+=(edge.getDist()));
        return sum.value;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        getEdges().parallelStream().forEachOrdered(edge -> sb.append(edge.toString()).append("\n"));
        return sb.toString();
    }

    @Override
    protected NodeMap clone() {
        NodeMap clone = new NodeMap();
        clone._totalOrders = this._totalOrders;
        clone._totalDistance = this._totalDistance;
        clone._deposit = this._deposit.clone();
        clone._lastNode = this._lastNode.clone();
        clone._tour = this._tour.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeMap nodeMap = (NodeMap) o;
        return _totalOrders == nodeMap._totalOrders &&
                Objects.equals(_deposit, nodeMap._deposit) &&
                Objects.equals(_lastNode, nodeMap._lastNode) &&
                Objects.equals(_tour, nodeMap._tour);
    }

}
