package utils;

/**
 * This class allows to use variables in lambdas defined out of lambdas
 * Usage : instanciate a new ForEachWrapper by passing the var you want to use in the constructor, and the use your forEachWrapper._value like a normal variable
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class ForEachWrapper<K> {
    public K value;

    public ForEachWrapper(K val) { this.value = val; }
}
