package org.acouster.math;

import org.acouster.util.MathUtils;

/**
 * Increments/decrements <b>value</b> within bounds set by setLimits().
 * Changes increment speed when limits have been saturated.
 */
public class RandDeviator extends Integrator
{
    public static final class Constants 
    {
        public static final double DefDevRatio = 0.1;
        public static final double D2R = Math.PI/180;
        public static final double R2D = 180/Math.PI;
    }
    
    protected double speed;
    protected double dev_limit;
    protected double speed_limit;
    
    public RandDeviator(double limits, double dev_limit)
    {
        super();
        setLimits(-limits, limits);
        this.dev_limit = dev_limit;
        speed_limit = dev_limit * Constants.DefDevRatio;
        resetSpeed();
    }
    
    protected void resetSpeed()
    {
        speed = MathUtils.randomDouble(-speed_limit, speed_limit);
    }
    
    public double tick()
    {
        inc(speed);
        if(isSaturated()) resetSpeed();
        return getValue();
    }
    
    
}
