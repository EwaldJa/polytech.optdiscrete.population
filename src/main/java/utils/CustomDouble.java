package utils;

public class CustomDouble implements graph.Cloneable{

    public double value;

    public CustomDouble(double val) {
        this.value = val;
    }

    @Override
    public CustomDouble clone() {
        return new CustomDouble(value);
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
