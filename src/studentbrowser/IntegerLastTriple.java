/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */


public class IntegerLastTriple<X,Y> extends Pair<X,Y>
{
    public Integer integer;

    public IntegerLastTriple() 
    {
    }
    
    public IntegerLastTriple(X type1, Y type2) {
        super(type1, type2);
        this.integer = new Integer(0);
    }

    public IntegerLastTriple(X type1, Y type2, Integer integer) {
        super(type1, type2);
        this.integer = integer;
    }

    
}
