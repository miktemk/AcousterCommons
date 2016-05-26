package org.acouster.math;

/**
 * Increments <b>value</b> via inc() within bounds set by setLimits
 */
public class Integrator
{
    protected double value, initValue;
    protected double max;
    protected double min;
    
    public Integrator()
    {
        this(0.0);
    }
    public Integrator(double initValue)
    {
        value = initValue;
        this.initValue = initValue;
        setLimits(-Double.MAX_VALUE, Double.MAX_VALUE);
    }
    
    public void setLimits(double min, double max)
    {
        this.max = max;
        this.min = min;
        enforceLimits();
    }
    protected void enforceLimits()
    {
        if(value > max) value = max;
        else if(value < min) value = min;
    }
    public double inc(double increment)
    {
        value += increment;
        enforceLimits();
        return value;
    }
    public boolean isSaturated()
    {
        return value == max || value == min;
    }
    
    public double getValue()
    {
        return value;
    }
    public void setValue(double value)
    {
        this.value = value;
        enforceLimits();
    }
    public double getMax()
    {
        return max;
    }
    public double getMin()
    {
        return min;
    }
    public void reset()
    {
        setValue(initValue);
    }
     

}
