package org.acouster.util;

import java.util.Vector;

public class Binner
{
    protected int number;
    protected int low;
    protected int high;
    protected int diff; 
    protected int[] bin;
    
    public Binner(int number, int low, int high)
    {
        this.number = number;
        this.low = low;
        this.high = high;
        diff = high - low;
        bin = new int[number];
        reset();
    }
    
    public void reset()
    {
        for(Integer cur : bin) cur = 0;
    }
    
    public boolean add(int num)
    {
        int index;
        if(num < low || num > high) return false;
        index = ((num-low) * number) /diff;
        bin[index]++;
        return true;
    }
    public int getBinValue(int num)
    {
        int index;
        if(num < low || num > high) return -1;
        index = ((num-low) * number) /diff;
        return bin[index];
    }
}
