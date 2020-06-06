package graph;

import java.io.Serializable;
import java.util.Objects;

public class Couple<K extends Cloneable, V extends Cloneable> implements Serializable, Cloneable {

    private K _key;
    private V _value;

    public Couple(K key, V value) {
        _key = key;
        _value = value;
    }

    public K getKey() { return  _key; }
    public V getValue() { return _value; }

    public void setKey(K _key) { this._key = _key; }
    public void setValue(V _value) { this._value = _value; }

    public Couple<K,V> clone() {
        return new Couple<>((K)_key.clone(), (V)_value.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couple<?, ?> couple = (Couple<?, ?>) o;
        return Objects.equals(_key, couple._key) &&
                Objects.equals(_value, couple._value);
    }

    @Override
    public String toString() {
        return "[Key \n: "+_key.toString()+"\n , Value : \n"+_value.toString()+"\n ]";
    }
}
