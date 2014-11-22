/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */

//setColumnModel(new ReorderingControlableTableColumnModel) 
public class ReorderingControlableTableColumnModel extends javax.swing.table.DefaultTableColumnModel
{
    private int maxMoveableRange, minMoveableRange;

    public ReorderingControlableTableColumnModel(int minMoveableRange, int maxMoveableRange) {
        setReorderingRange(minMoveableRange,maxMoveableRange);
    }
    public void setReorderingRange(int max, int min)
    {
        //if(max < 0) this.maxMoveableRange = tableColumns.size();
        this.maxMoveableRange = max;
        this.minMoveableRange = min;
    }

    public int getMaxMoveableRange() {
        return maxMoveableRange;
    }

    public int getMinMoveableRange() {
        return minMoveableRange;
    }
    
    
    @Override
    public void moveColumn(int columnIndex, int newIndex) {
        
        if(columnIndex >= minMoveableRange && (columnIndex <= maxMoveableRange || maxMoveableRange < 0) &&
                newIndex >= minMoveableRange && (newIndex <= maxMoveableRange || maxMoveableRange < 0))
            super.moveColumn(columnIndex, newIndex);
        
    }

   
    

}
