/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import java.util.*;

/**
 *
 * @author GuestUser
 */
public class SortedArrayList<T> extends ArrayList<T> 
{
    Comparator comparator;
   
    public SortedArrayList(Comparator comparator) 
    {
        super();
        this.comparator = comparator;
    }
    
    //Needs to check for comparability
   
    

    @Override
    public boolean add(T type) {
        return super.add(type);
        /*if(comparator != null)
        {
            for(int i = 0; i < this.size(); i++)
            {
                if(comparator.compare(this.get(i), type) >= 0)
                {
                    super.add(i, type);
                    return true;
                }
            }

            super.add(0, type);
            return true;
        }
        
        return false;*/
        
    }
    

}
