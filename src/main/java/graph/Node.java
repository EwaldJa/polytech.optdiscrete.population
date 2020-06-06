package graph;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a node of a graph corresponding to the loaded dataset.
 * It models a customer, with his position, and his order.
 * The transport depot is a node with empty order.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Solution
 * @see Node
 * @see DeliveryTour
 */
public class Node implements Serializable, Cloneable {

    private int _id, _xPos, _yPos, _order;

    public Node(int id, int xPos, int yPos, int order) {
        _id = id;
        _xPos = xPos;
        _yPos = yPos;
        _order = order;
    }

    public double getDistance(Node other) {
        return Math.sqrt( Math.pow((this._xPos - other._xPos), 2.0) + Math.pow((this._yPos - other._yPos), 2.0) );
    }

    public int getId() { return _id; }

    public int getXPos() { return _xPos; }

    public int getYPos() { return _yPos; }

    public int getOrder() { return _order; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return _id == other._id &&
                _xPos == other._xPos &&
                _yPos == other._yPos &&
                _order == other._order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _xPos, _yPos, _order);
    }

    @Override
    public String toString() {
        return "[Node #" + _id + " x:" + _xPos + " y:" + _yPos + " q:" + _order + "]";
    }

    public Node clone() { return new Node(_id, _xPos, _yPos, _order); }
}
