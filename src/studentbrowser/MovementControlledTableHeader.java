/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableColumnModel;
//GOT TO CREATE DefaultTableColumnModel
/**
 * NOT USED, ReorderingControlableTableColumnModel does what this class was suppose to.
 * @author GuestUser
 */
/*
MovementControlledTableHeader tableHeader = 
        new MovementControlledTableHeader(studentInformationTable.getColumnModel(),2,5);
tableHeader.setTable(studentInformationTable);
studentInformationTable.setTableHeader(tableHeader);
*/
public class MovementControlledTableHeader extends javax.swing.table.JTableHeader
{
    
    private int maxMoveableRange, minMoveableRange;
    

    @Override
    public void columnMoved(TableColumnModelEvent e) 
    {
        int fromIndex = e.getFromIndex();
        Debug.line("minMoveable,maxMoveable: "+minMoveableRange+","+maxMoveableRange);
        Debug.line("getFromIndex():"+fromIndex);
        //super.columnMoved(e);
        
        
        if(minMoveableRange <= fromIndex && maxMoveableRange >= fromIndex)
        {   
            
            Debug.line("RAN!!!!!!!!!!!!!!");    
            this.setReorderingAllowed(false); 
            super.columnMoved(e); 
        }
        /*
        if(maxMoveableRange < 0 || 
                (minMoveableRange <= e.getFromIndex() && maxMoveableRange <= e.getFromIndex()))
            super.columnMoved(e);
         */
    }
    
  
    
    public MovementControlledTableHeader(TableColumnModel cm, int minMoveableRange, int maxMoveableRange) 
    {
        super(cm);
        setMaxMoveableRange(maxMoveableRange);
        setMinMoveableRange(minMoveableRange);
    }
    

    public MovementControlledTableHeader(int maxMoveableRange, int minMoveableRange) 
    {
        super();
        setMaxMoveableRange(maxMoveableRange);
        setMinMoveableRange(minMoveableRange);
    }
    
    public MovementControlledTableHeader() 
    {
        super();
        setMaxMoveableRange(0);
        setMinMoveableRange(-1);
    }

    public int getMaxMoveableRange() {
        return maxMoveableRange;
    }
    
    public int getMinMoveableRange() {
        return minMoveableRange;
    }

    public void setMaxMoveableRange(int maxMoveableRange) 
    {
        this.maxMoveableRange = maxMoveableRange;
    }
    
    public void setMinMoveableRange(int minMoveableRange) {
        this.minMoveableRange = minMoveableRange;
    }
}
