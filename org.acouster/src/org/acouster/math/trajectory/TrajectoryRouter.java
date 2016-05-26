package org.acouster.math.trajectory;

import org.acouster.math.MathConstants;
import org.acouster.math.RandDeviator;
import org.acouster.math.Vector2D;
import org.acouster.util.MathUtils;

public class TrajectoryRouter
{
    protected IObject2D target;
    protected IInterceptHelper obstacles;
    protected boolean targetReached = false;
    protected boolean isLastObs = false;
    
    protected Vector2D positionRef, deltaRef;
    protected double speed;
    protected double speed2_cos108;   //sqr(speed)*cos 108
    protected RandDeviator rDev;
    protected boolean turnLeft;
    protected Vector2D step, lastStep, tarPoint;  
    protected IObject2D lastObs = null;
    
    
    public TrajectoryRouter(IInterceptHelper obstacles, Vector2D position, double speed, double variator)
    {
    	this.positionRef = position;
        this.obstacles = obstacles;
        setSpeed(speed);
        
        step = new Vector2D();
        lastStep = new Vector2D();
        tarPoint = new Vector2D();
        
        rDev = new RandDeviator(variator, variator/2);
        turnLeft = MathUtils.randomInt(0, 2) == 0; 
    }
    
    protected boolean tryStep(double angle, Vector2D tmp, IObject2D obs)
    {
        if(turnLeft) step.rotate(-angle);
        else step.rotate(angle);
       
        tmp.setSum(step, positionRef);
        return obs.checkCollision(tmp);
    }
    
    public boolean tick()
    {
    	if (target == null)
    		return false;
    	if (targetReached)
    		return true;
    	
        double curDevAngle = rDev.tick();
        step.rotate(curDevAngle);
        
        Vector2D tmp = Vector2D.shared;
        tmp.setSum(step, positionRef);
        // check for collision with obstacles
        IObject2D obs = obstacles.checkCollision(tmp);
        if (obs == target)
        	obs = null;
        if(obs != null)
        {
            int i;
            // TODO: binary search for angle 
                        
            for(i=0; i<9; i++)
            {
                if(!tryStep(MathConstants.PI0125, tmp, obs)) break;
            }
            rDev.reset();
        }
        
        positionRef.set(tmp);
        lastStep.set(step);
        if (deltaRef != null)
        	deltaRef.set(step);
        step.setDiff(tarPoint, positionRef);
        step.normalaze(speed);
        if(obs != null && !isLastObs && !obs.equals(lastObs))                //check if angle more than 45
        {
            double stepDot = lastStep.dot(step);    // dot product ~ to cosine of the angle
            if(stepDot < speed2_cos108)              // angle > than 108 - change direction but only once
            {
                turnLeft = !turnLeft;    // change direction
                lastObs = obs;           // Remember me to avoid multiple changing
//              System.out.println(" Puk: " + turnLeft);
            }
            
            
        }
        isLastObs = obs != null;
        
        
        return (targetReached = (positionRef.getDistance(tarPoint) < speed));
    }

    public void setTarget(IObject2D target)
    {
        this.target = target;
        targetReached = false;
        if (target != null)
        {
        	target.getCenter(tarPoint);
            step.setDiff(tarPoint, positionRef);
            step.normalaze(speed);
        }
        
    }
    public Vector2D getCurPos()
    {
        return positionRef;
    }
    public IObject2D getTarget()
    {
		return target;
	}
    public void getLastStep(Vector2D fillMe)
    {
    	fillMe.set(lastStep);
    }
    
    public boolean isTargetReached()
    {
    	return targetReached;
    }

	public void setStepVector(Vector2D deltaRef)
	{
		this.deltaRef = deltaRef;
	}
	protected void setSpeed(double speed)
	{
	    this.speed = speed;
	    speed2_cos108 = speed*speed*MathConstants.cos108;
	}
   
}
