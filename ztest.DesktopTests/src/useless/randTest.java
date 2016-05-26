package useless;

import org.acouster.util.MathUtils;

public class randTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        int arr[] = {0,0,0,0};
        for(int i=0; i!=500; i++)
        {
           int r = MathUtils.randomInt(0, 3);
           arr[r]++;
          //  System.out.println("" + i + " : " + r);
        }
        System.out.println("" + arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3]);

    }

}
