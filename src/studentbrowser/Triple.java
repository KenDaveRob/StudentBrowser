/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */
public class Triple<X,Y,Z>
{
    public X type1;
    public Y type2;
    public Z type3;
    
    public Triple(X type1, Y type2, Z type3)
    {
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
    }
    
    public Triple(Pair<X,Y> pair)
    {
        this.type1 = pair.type1;
        this.type2 = pair.type2;
    }
    
    public Triple() {}
}
