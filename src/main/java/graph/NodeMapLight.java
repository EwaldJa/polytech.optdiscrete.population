package graph;

import utils.RandUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class establish the links between the Nodes of a DeliveryTour
 * It is a lighter version of NodeMap
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see DeliveryTour
 * @see Solution
 * @see Edge
 * @see Node
 * @see NodeMap
 */
public class NodeMapLight implements Serializable {

    private Node _deposit;
    private int _totalOrders;
    private double _totalDistance;

    /*The ordered List of all the Nodes*/
    private List<Node> _tour;

    private NodeMapLight(){}

    public NodeMapLight(Node deposit) {
        _deposit = deposit;
        _tour = new ArrayList<>();
        _tour.add(_deposit);
        _totalOrders = 0;
        _totalDistance = 0.0;
    }

    /**
     * Adds the node to the delivery tour, at the last position, meaning that the next node following the edge after the one to add will be the origin node
     * @param n the node to add
     */
    public void put(Node n) {
        Node n_1 = _tour.get(_tour.size() - 1);
        double n_1_to_n = n_1.getDistance(n);
        double n_1_to_deposit = n_1.getDistance(_deposit);
        double n_to_deposit = n.getDistance(_deposit);
        _totalDistance += (n_1_to_n + n_to_deposit - n_1_to_deposit);
        _totalOrders += n.getOrder();
        _tour.add(n);
    }

    /**
     * Adds the node to the delivery tour, at the index position, meaning that the next node following after the one to add will be the index+1 node, and the previous one index-1
     * @param index where you want to add the Node
     * @param n the Node to add
     */
    public void put(int index, Node n) {
        if (index < 1 || index > _tour.size()) { throw new IllegalArgumentException("Index is not greater or equal to 1 or is bigger than size : " + index); }
        Node n_A = _tour.get(index - 1);
        Node n_B = _tour.get(((index >= _tour.size())?0:index));

        double n_A_to_n_B = n_A.getDistance(n_B);
        double n_A_to_n = n_A.getDistance(n);
        double n_to_n_B = n.getDistance(n_B);
        _totalDistance += (n_A_to_n + n_to_n_B - n_A_to_n_B);
        _totalOrders += n.getOrder();
        _tour.add(index, n);
    }

    /**
     * @return a List of all the nodes of this delivery tour
     */
    public List<Node> getNodes() { return _tour; }

    /**
     * @return a List of all the edges of this delivery tour
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < _tour.size(); i++) {
            edges.add(new Edge(_tour.get(i-1),_tour.get(i)));
        }
        edges.add(new Edge(_tour.get(_tour.size() - 1),_deposit));
        return edges;
    }


    /**
     * Swaps the two specified Nodes in this delivery tour. They both must be Nodes of this tour.
     * @param n one of the nodes to swap
     * @param m the other node to swap
     */
    public void internalSwapSafe(Node n, Node m) {
        int index_n = _tour.indexOf(n);
        int index_m = _tour.indexOf(m);
        //System.err.println("    ------------");
        //System.err.println("    n: " + n.toString() + ", m : " + m.toString());
        //System.err.println("    n-1: " + _tour.get(index_n - 1).toString() + ", n+1 : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).toString());
        //System.err.println("    m-1: " + _tour.get(index_m - 1).toString() + ", m+1 : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).toString());
        //System.err.println("    n-1 to n: " + _tour.get(index_n - 1).getDistance(n) + ", n+1 to n : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n) + ", sum : " + (_tour.get(index_n - 1).getDistance(n)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n)));
        //System.err.println("    m-1 to m: " + _tour.get(index_m - 1).getDistance(m) + ", m+1 to m : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m) + ", sum : " + (_tour.get(index_m - 1).getDistance(m)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m)));
        //System.err.println("    n-1 to m: " + _tour.get(index_n - 1).getDistance(m) + ", n+1 to m : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m) + ", sum : " + (_tour.get(index_n - 1).getDistance(m)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m)));
        //System.err.println("    m-1 to n: " + _tour.get(index_m - 1).getDistance(n) + ", m+1 to n : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n) + ", sum : " + (_tour.get(index_m - 1).getDistance(n)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n)));
        //System.err.print("    total dist : " + _totalDistance);

        //_totalDistance -= (_tour.get(index_n - 1).getDistance(n) + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance -= (_tour.get(index_m - 1).getDistance(m) + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance += (_tour.get(index_n - 1).getDistance(m) + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance += (_tour.get(index_m - 1).getDistance(n) + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n));
        //System.err.println("    total dist : " + _totalDistance);

        Node n_A = _tour.get(index_n - 1);  //n-1
        Node n_B = _tour.get(((index_n + 1 >= _tour.size())?0:index_n + 1));    //n+1

        Node m_A = _tour.get(index_m - 1);  //m-1
        Node m_B = _tour.get(((index_m + 1 >= _tour.size())?0:index_m + 1));    //m+1

        //Distance to remove
        double n_A_to_n = n_A.getDistance(n);   //n-1 vers n
        double n_to_n_B = n.getDistance(n_B);   //n+1 vers n

        double m_A_to_m = m_A.getDistance(m);   //m-1 vers m
        double m_to_m_B = m.getDistance(m_B);   //m+1 vers m

        double dist_to_rem = n_A_to_n+n_to_n_B  +  m_A_to_m+m_to_m_B;




        _totalDistance = _totalDistance - dist_to_rem;


        if (index_m > index_n) { //n first
            _tour.remove(n);
            _tour.remove(m);
            _tour.add(index_n, m);
            _tour.add(index_m, n);
        }
        else {
            _tour.remove(m);
            _tour.remove(n);
            _tour.add(index_m, n);
            _tour.add(index_n, m);
        }


        n_A = _tour.get(index_n - 1);  //n-1
        n_B = _tour.get(((index_n + 1 >= _tour.size())?0:index_n + 1));    //n+1

        m_A = _tour.get(index_m - 1);  //m-1
        m_B = _tour.get(((index_m + 1 >= _tour.size())?0:index_m + 1));    //m+1

        //Distance to add
        double n_A_to_m = n_A.getDistance(m);   //n-1 vers m
        double m_to_n_B = m.getDistance(n_B);   //n+1 vers m

        double m_A_to_n = m_A.getDistance(n);   //m-1 vers n
        double n_to_m_B = n.getDistance(m_B);   //m+1 vers n

        double dist_to_add = n_A_to_m+m_to_n_B  +  m_A_to_n+n_to_m_B;

        _totalDistance = _totalDistance + dist_to_add;


        //System.err.print("    total dist : " + _totalDistance);
        //System.err.println("    total dist : " + _totalDistance);
        //System.err.println("    n-1 to m: " + _tour.get(index_n - 1).getDistance(m) + ", n+1 to m : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m) + ", sum : " + (_tour.get(index_n - 1).getDistance(m)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m)));
        //System.err.println("    m-1 to n: " + _tour.get(index_m - 1).getDistance(n) + ", m+1 to n : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n) + ", sum : " + (_tour.get(index_m - 1).getDistance(n)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n)));
        //System.err.println("    ------------");
    }

    /**
     * Swaps two Nodes of this delivery tour chosen randomly among all
     */
    public void internalSwapRandom() {
        int size = _tour.size();
        int indexN = RandUtils.randInt(1, size);
        int indexM = RandUtils.randInt(1, size);
        while (indexN == indexM) {
            indexM = RandUtils.randInt(1, size);
        }
        internalSwapSafe(_tour.get(indexN), _tour.get(indexM));
    }

    /**
     * Swaps the nodes specified by the two indexes
     * @param index1
     * @param index2
     */
    public void internalSwap(int index1, int index2) {
        internalSwapSafe(_tour.get(index1), _tour.get(index2));
    }

    /**
     * Swaps two Nodes chosen randomly, one among other's, one among this instance's
     * @param other another NodeMap
     */
    public void externalSwap(NodeMapLight other) {
        Node otherNode = other._tour.get(RandUtils.randInt(1, other._tour.size()));
        Node localNode = this._tour.get(RandUtils.randInt(1, this._tour.size()));
        if ((otherNode.getOrder() - localNode.getOrder()) > this.getRemainingSpace() ||
                (localNode.getOrder() - otherNode.getOrder()) > other.getRemainingSpace()) { return; }
        else {
            this.replaceNodeSafe(localNode, otherNode);
            other.replaceNodeSafe(otherNode, localNode); }
    }

    /**
     * Swaps two Nodes, one chosen among other's Nodes, one among this instance's Nodes
     * @param other another NodeMapLight
     * @param localIndex the index of the Node to swap on this NodeMapLight
     * @param otherIndex the index of the Node to swap on other NodeMapLight
     */
    public void externalSwap(int localIndex, NodeMapLight other, int otherIndex) {
        Node otherNode = other._tour.get(otherIndex);
        Node localNode = this._tour.get(localIndex);
        this.replaceNodeSafe(localNode, otherNode);
        other.replaceNodeSafe(otherNode, localNode);
    }


    /**
     * Replaces old_n by new_n in the nodes of the delivery tour
     * @param old_n the Node to be replace
     * @param new_n the Node replacing
     */
    private void replaceNodeSafe(Node old_n, Node new_n) {
        int index_old = _tour.indexOf(old_n);
        Node n_1 = _tour.get(index_old - 1); Node n_2 = _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1);
        double n_1_to_old = n_1.getDistance(old_n); double n_2_to_old = n_2.getDistance(old_n);
        double n_1_to_new = n_1.getDistance(new_n); double n_2_to_new = n_2.getDistance(new_n);

        _totalDistance = _totalDistance + ( (n_1_to_new + n_2_to_new) - (n_1_to_old + n_2_to_old) );
        _totalOrders += (new_n.getOrder() - old_n.getOrder());
        _tour.remove(old_n);
        _tour.add(index_old, new_n);

        //System.err.println("    old_n: " + old_n.toString() + ", new_n : " + new_n.toString());
        //System.err.println("    n-1: " + _tour.get(index_old - 1).toString() + ", n+1 : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).toString());
        //System.err.println("    n-1 to old_n: " + _tour.get(index_old - 1).getDistance(old_n) + ", n+1 to old_n : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(old_n));
        //System.err.println("    n-1 to new_n: " + _tour.get(index_old - 1).getDistance(new_n) + ", n+1 to new_n : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(new_n));
        //System.err.print("    total dist : " + _totalDistance);
        //System.err.print("    total dist : " + _totalDistance);
        //System.err.println("    total dist : " + _totalDistance);
    }

    /**
     * Removes a Node from other and puts it in current instance
     * @param other another NodeMap
     */
    public void changeNodeTour(NodeMapLight other) {
        Node otherNode = other._tour.get(RandUtils.randInt(1, other._tour.size()));
        if (otherNode.getOrder() > this.getRemainingSpace()) { return; }
        else {
            this.put(RandUtils.randInt(1, this._tour.size() + 1), otherNode);
            other.removeSafe(otherNode); }
    }

    /**
     * Removes a Node from other NodeMapLight and puts it in current instance
     * @param other another NodeMapLight
     * @param otherindex index of the Node to remove on other NodeMapLight
     * @param localindex index where to add the Node (from 1 to this._tour.size())
     */
    public void changeNodeTour(NodeMapLight other, int otherindex, int localindex) {
        Node otherNode = other._tour.get(otherindex);
        this.put(localindex, otherNode);
        other.removeSafe(otherNode);
    }


    /**
     * Removes the specified Node from the delivery tour
     * @param n the Node to delete
     */
    public void removeSafe(Node n) {
        int index_n = _tour.indexOf(n);
        Node n_1 = _tour.get(index_n - 1);
        Node n_2 = _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1);
        double n_1_to_n = n_1.getDistance(n);
        double n_to_n_2 = n.getDistance(n_2);
        double n_1_to_n_2 = n_1.getDistance(n_2);
        _totalDistance = _totalDistance - (n_1_to_n + n_to_n_2) + n_1_to_n_2;
        _totalOrders -= n.getOrder();
        _tour.remove(n);
    }

    /**
     * @return the sum of the orders of all the client of this delivery tour
     */
    public int getTotalOrders() { return _totalOrders; }


    /**
     * @return remaining space as of the capacity defined in DeliveryTour
     * @see DeliveryTour#MAX_CAPACITY
     */
    public int getRemainingSpace() { return DeliveryTour.MAX_CAPACITY - _totalOrders; }

    /**
     * @return the total distance between all the clients of this delivery tour that is kept up to date during programm running
     */
    public double getTotalDistance() {
        return _totalDistance;
    }

    /**
     * @return the total distance by recalculating it
     */
    public double calculateTotalDistance() {
        double dist = 0.0;
        for (int i = 1; i < _tour.size(); i++) {
            dist += _tour.get(i-1).getDistance(_tour.get(i));
        }
        dist += _tour.get(_tour.size() - 1).getDistance(_deposit);
        return dist;
    }

    public Node getDeposit() { return _deposit; }

    public Node getNode(int index) { return _tour.get(index); }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < _tour.size(); i++) {
            sb.append(_tour.get(i-1).toString()).append(" -> ").append(_tour.get(i).toString()).append("\n");
        }
        sb.append(_tour.get(_tour.size()-1).toString()).append(" -> ").append(_deposit.toString()).append("\n");
        return sb.toString();
    }

    @Override
    protected NodeMapLight clone() {
        NodeMapLight clone = new NodeMapLight();
        clone._totalOrders = this._totalOrders;
        clone._totalDistance = this._totalDistance;
        clone._deposit = this._deposit.clone();
        clone._tour = new ArrayList<>();
        this._tour.parallelStream().forEachOrdered(node -> clone._tour.add(node.clone()));
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeMapLight nodeMap = (NodeMapLight) o;
        return _totalOrders == nodeMap._totalOrders &&
                _totalDistance == nodeMap._totalDistance &&
                Objects.equals(_deposit, nodeMap._deposit) &&
                Objects.equals(_tour, nodeMap._tour);
    }

    public int getSize() {
        return _tour.size();
    }
}
