package org.acouster.math.trajectory;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.acouster.math.Vector2D;

public class PandRHelper implements IInterceptHelper
{
    protected List<IObject2D> list;
    
    public PandRHelper()
    {
        list = Collections.synchronizedList(new Vector<IObject2D>());
    }
     
    public void addObstacle(IObject2D o)
    {
    	list.add(o);
    	// TODO: if Collections.synchronizedList still fails us
		//synchronized (list) { list.add(o); }
    }
    public void removeObstacle(IObject2D o)
    {
    	list.remove(o);
    	// TODO: if Collections.synchronizedList still fails us
    	//synchronized (list) { list.remove(o); }
    }
    /** returns first collision object. */
    public IObject2D checkCollision(Vector2D v)
    {
        List<IObject2D> list = getCandidates(v);
        synchronized (list)
        {
        	for(IObject2D cur:list)
            {
                if (cur.checkCollision(v))
                	return cur;
            }
		}
        return null;
    }
    
    @Override
    public List<IObject2D> getCandidates(Vector2D v)
    {
        return list;
    }
    
    public void clear()
    {
        list.clear();
    }
      
}
