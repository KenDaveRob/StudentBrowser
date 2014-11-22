/*
 * SpecificGraphPanel.java
 *
 * Created on December 16, 2008, 2:23 PM
 */

package studentbrowser;

import java.awt.Graphics;


/**
 *
 * @author  Coordinator
 */
public class SpecificGraphPanel extends javax.swing.JPanel {

    /** Creates new form SpecificGraphPanel */
    private java.util.ArrayList<Double> percentages;
    private StudentBrowserStudentStatisticsDialog parent;
    private final int imageOffsetX = 10;
    private final int imageOffsetY = 11;
    
    /** Creates new form GeneralGraphPanel */
    public SpecificGraphPanel(StudentBrowserStudentStatisticsDialog parent) 
    {
        initComponents();    
        this.parent = parent;
        //percentages = new java.util.ArrayList<Double>();
        setPercentages();
    }
    
    public void setPercentages()
    {
        parent.generateSpecificGroups();
        
        percentages = new java.util.ArrayList<Double>();
        //percentages.ensureCapacity(60);
        for(int i = 0; i <= 32; i++)
            percentages.add(i, new Double(0.0));
        
        for(int index = 0; index < percentages.size(); index++)
            percentages.set(index, parent.getPercentage(index, true));
        
    }
    
    
    //CREATE NEW CONSTRUCTOR TO TAKE IN STATS so that repaint doesn't need to be called
    


    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        java.awt.Graphics2D g2D = (java.awt.Graphics2D)g;
        java.awt.Graphics2D imageG2D = parent.getSpecificGraphImageGraphics2D();
        //java.awt.geom.Line2D.Double testLine = new java.awt.geom.Line2D.Double(0, 10, 12.5, 10);
        //g2D.draw(testLine);
        //g.setFont(new java.awt.Font("Small", java.awt.Font.PLAIN, 10));
        //g.drawString("-8", 200, 200);
        
        //java.awt.geom.Line2D.Double line;
        double index = 0;
        
        for(Double d : percentages)
        {
            //Debug.line("PaintComponent()RAN!!!!!!!!!!!!!!!!!!!!");
            double height = d.doubleValue()*400;
            double yValue = 400-height;
            if(yValue == 0) 
            {
                yValue += 1;
                //height++;
            }
            
            //else if(tempval != 400)
            {
                //if(height != 0)
                    g2D.fill(new java.awt.geom.Rectangle2D.Double(index, yValue, 12.12, height+1));
                    g2D.setColor(java.awt.Color.red);
                    g2D.draw(new java.awt.geom.Rectangle2D.Double(index, yValue, 12.12, height+1));
                    g2D.setColor(java.awt.Color.black);
                    
                    imageG2D.fill(new java.awt.geom.Rectangle2D.Double(
                            index+imageOffsetX, yValue+imageOffsetY, 12.12, height+1));
                    imageG2D.setColor(java.awt.Color.red);
                    imageG2D.draw(new java.awt.geom.Rectangle2D.Double(
                            index+imageOffsetX, yValue+imageOffsetY, 12.12, height+1));
                    imageG2D.setColor(java.awt.Color.black);
                    
                    //Invisi. line
                    //g2D.draw(new java.awt.geom.Line2D.Double(index, 400, index, 390));
                    
                
            }
            index += 12.12;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
