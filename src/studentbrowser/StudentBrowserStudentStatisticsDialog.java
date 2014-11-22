/*
 * StudentBrowserStudentStatisticsDialog.java
 *
 * Created on September 8, 2008, 5:35 PM
 */

package studentbrowser;

import java.awt.Graphics2D;

/**
 *
 * @author  GuestUser
 */
public class StudentBrowserStudentStatisticsDialog extends javax.swing.JDialog {
    
    private java.util.NavigableSet<StudentInfo2> studentsInformation;
    private final String gpasWeighed;
    private java.awt.image.BufferedImage generalGraphImage;
    private java.awt.image.BufferedImage specificGraphImage;
    private java.awt.Graphics2D generalGraphImageGraphics2D;
    private java.awt.Graphics2D specificGraphImageGraphics2D;
    private SaveGraphDialog saveGraphBox;
    private boolean summerIncluded;
    /**
     * changeGroups
     * -8 <= x < -7 is [0]
     * -7 <= x < -6 is [1]
     * -6 <= x < -5 is [2]
     * -5 <= x < -4 is [3]
     * -4 <= x < -3 is [4]
     * -3 <= x < -2 is [5]
     * -2 <= x < -1 is [6]
     * -1 <= x < 0 is [7]
     * 0 <= x < 1 is [8]
     * 1 <= x < 2 is [9]
     * 2 <= x < 3 is [10]
     * 3 <= x < 4 is [11]
     * 4 <= x < 5 is [12]
     * 6 <= x < 7 is [13]
     * 7 <= x <= 8 is [14]
     */
    private java.util.ArrayList<java.lang.Integer> changeGroups;
    private final int SECTIONS = 33;
    
    private java.util.ArrayList<java.lang.Integer> specificChangeGroups;

    
    /** Creates new form StudentBrowserStudentStatisticsDialog */
    public StudentBrowserStudentStatisticsDialog(java.awt.Frame parent, java.util.NavigableSet<StudentInfo2> studentsInfo) {
        super(parent);
        this.studentsInformation = studentsInfo;
        gpasWeighed = "with weighed grades";

        changeGroups = new java.util.ArrayList<java.lang.Integer>();
        specificChangeGroups = new java.util.ArrayList<java.lang.Integer>();
        for(int i = 0; i <= SECTIONS; i++)
        { changeGroups.add(i, 0); specificChangeGroups.add(i,0); }
        
        
        
        initComponents();
        
       
        
        summerIncluded = false;
        //Set up graphImage
        generalGraphImage = new java.awt.image.BufferedImage(
                generalLayoutPanel.getWidth(), generalLayoutPanel.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);
        //Set up graphImageGraphics2D
        generalGraphImageGraphics2D = generalGraphImage.createGraphics();
        //Fill in backround
        generalGraphImageGraphics2D.setColor(generalLayoutPanel.getBackground());
        generalGraphImageGraphics2D.fillRect(0, 0, getWidth(), getHeight());
        //reset color
        generalGraphImageGraphics2D.setColor(java.awt.Color.black);
        ((GeneralGraphPanel)generalGraphPanel).setPercentages();
        generalGraphPanel.repaint();
        
        specificGraphImage = new java.awt.image.BufferedImage(
                specificLayoutPanel.getWidth(), specificLayoutPanel.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);
        //Set up graphImageGraphics2D
        specificGraphImageGraphics2D = specificGraphImage.createGraphics();
        //Fill in backround
        specificGraphImageGraphics2D.setColor(specificLayoutPanel.getBackground());
        specificGraphImageGraphics2D.fillRect(0, 0, getWidth(), getHeight());
        //reset color
        specificGraphImageGraphics2D.setColor(java.awt.Color.black);
        ((SpecificGraphPanel)specificGraphPanel).setPercentages();
        specificGraphPanel.repaint();
        
        
        
        //Debug.line("GeneralGraphPanel Heigh,width:"+graphPanel.getHeight()+","+graphPanel.getWidth());

        //Sets the 50% marker in the middle
        
        //this.fiftyPercentLabel.setLocation(this.fiftyPercentLabel.getLocation().x, 
        //        this.graphPanel.getY() + 200);
        
        //this.generateGroups();
        
        //this.repaint();
        
        // Debugging for getLatestSemester and getSemesterBefore Methods
        /*Debug.line("Student: "+this.studentsInformation.first().getName()+".GetLatestSemester() = ");
        Debug.line(this.studentsInformation.first().getLatestSemester().type1.intValue() + " grade, ");
        Debug.line(" and " + this.studentsInformation.first().getLatestSemester().type2.toString());
        Debug.line(" Semester");
        
        Debug.line("Student: "+this.studentsInformation.first().getName()+".GetSemesterBefore() = ");
        Debug.line(this.studentsInformation.first().getSemesterBefore(
                this.studentsInformation.first().getLatestSemester().type1.intValue()
                , this.studentsInformation.first().getLatestSemester().type2).type1.intValue() + " grade, ");
        Debug.line(" and " + this.studentsInformation.first().getSemesterBefore(
                this.studentsInformation.first().getLatestSemester().type1.intValue(),
                this.studentsInformation.first().getLatestSemester().type2
                ).type2.toString());
        Debug.line(" Semester");*/
        
        //this.studentsInformation.first().getLatestSemester()
        
        //Debug.line("WIDTH: "+this.graphPanel.getBounds().width);
    }

    public Graphics2D getGeneralGraphImageGraphics2D() {
        return generalGraphImageGraphics2D;
    }
    
    public Graphics2D getSpecificGraphImageGraphics2D() {
        return specificGraphImageGraphics2D;
    }
    
    public void writeImage(String filename, boolean general)
    {
        
        //Set Color to black
        if(general)
        {
            generalGraphImageGraphics2D.setColor(generalGraphPanel.getForeground());

            //Biger Border
            generalGraphImageGraphics2D.draw(new java.awt.geom.Rectangle2D.Double(10, 9, 400, 403));
            //Border
            //graphImageGraphics2D.draw(new java.awt.geom.Rectangle2D.Double(11, 10, 398, 401));
        }
        
        else
        {
            specificGraphImageGraphics2D.setColor(specificGraphPanel.getForeground());

            //Biger Border
            specificGraphImageGraphics2D.draw(new java.awt.geom.Rectangle2D.Double(10, 9, 400, 403));
            //Border
            //graphImageGraphics2D.draw(new java.awt.geom.Rectangle2D.Double(11, 10, 398, 401));
        }

        String[] correctSuffixes = javax.imageio.ImageIO.getWriterFileSuffixes();
        
        javax.swing.JFileChooser saveFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        javax.swing.filechooser.FileNameExtensionFilter imageFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files *.(jpg, bmp, jpeg, wbmp, png, gif)", correctSuffixes);
        saveFileDialog.setFileFilter(imageFilter);
        int dialogButtonOption = saveFileDialog.showSaveDialog(null);
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File saveFile = saveFileDialog.getSelectedFile();
            //saveFile.createNewFile()
            boolean suffixCorrect = false;
            String suffix = saveFile.getName().substring(saveFile.getName().lastIndexOf(".")+1);
                           
            for(int index = 0; index < correctSuffixes.length; index++)
                if(suffix.equals(correctSuffixes[index])) { suffixCorrect = true; break; }
                
            
            
            if(suffixCorrect)
            {
                try
                {
                    //javax.imageio.stream.ImageOutputStream imageOut = 
                    //        javax.imageio.ImageIO.createImageOutputStream(f);

                    if(general)
                        javax.imageio.ImageIO.write(generalGraphImage,suffix, saveFile);
                    else
                        javax.imageio.ImageIO.write(specificGraphImage,suffix, saveFile);
                }
                catch(java.io.IOException IOe)
                {
                    System.out.println("Error: writing Image");
                    System.out.println(IOe.getMessage());
                    IOe.printStackTrace();
                }
            }
        }
        
        /*image manipulation dialog
        javax.swing.JFrame mainFrame = StudentBrowserApp.getApplication().getMainFrame();
               
        //creates dialog
        saveGraphBox = new SaveGraphDialog(mainFrame, graphImage);
        //saveGraphBox.setLocationByPlatform(false);
        
        saveGraphBox.setLocation(getLocation().x+20,getLocation().y+20);
        //saveGraphBox.setLocation(20,20);
        //saveGraphBox.setLocationRelativeTo(this);
        saveGraphBox.setVisible(true);
        //StudentBrowserApp.getApplication().show(saveGraphBox);
        */
        /* Simple file save
        java.io.File f = new java.io.File(filename);
        try
        {
            
            //javax.imageio.stream.ImageOutputStream imageOut = 
            //        javax.imageio.ImageIO.createImageOutputStream(f);
            
            javax.imageio.ImageIO.write(graphImage,"jpg", f);
        }
        catch(java.io.IOException IOe)
        {
            System.out.println("Error: writing Image");
            IOe.printStackTrace();
        }
        */
    }
    
    
    public double getPercentage(int index, boolean specific)
    {
        double total = 0;
        if(specific)
        {
            for(Integer i : specificChangeGroups)
            {
                total += i.intValue();
            }
            if(total == 0) total++;
            total = specificChangeGroups.get(index)/total;
        }
        else
        {
            for(Integer i : changeGroups)
            {
                total += i.intValue();
            }
            if(total == 0) total++;
            total = changeGroups.get(index)/total;
        }
        
        return total;
    }
    
    public void generateSpecificGroups()
    {   
        ClassType specificSubject = ClassType.ENGLISH;
        /*
        if(subjectComboBox != null)
        {
            Debug.line("subjectComboBox.getSelectedIndex(): "+subjectComboBox.getSelectedIndex());
            if(subjectComboBox.getSelectedIndex() == 0) specificSubject = ClassType.ENGLISH;

            else if(subjectComboBox.getSelectedIndex() == 1) specificSubject = ClassType.MATH;

            else if(subjectComboBox.getSelectedIndex() == 2) specificSubject = ClassType.SCIENCE;

            else if(subjectComboBox.getSelectedIndex() == 3) specificSubject = ClassType.HISTORY;

            else if(subjectComboBox.getSelectedIndex() == 4) specificSubject = ClassType.FOREIGN_LANGUAGE;

            else if(subjectComboBox.getSelectedIndex() == 5) specificSubject = ClassType.OTHER;

            else if(subjectComboBox.getSelectedIndex() == 6) specificSubject = ClassType.PLACEHOLDER;
        }
        */
        if(subjectComboBox != null)
        {
            try
            {
                specificSubject = ClassType.generateClassType(subjectComboBox.getSelectedIndex());
            }
            catch(java.lang.IndexOutOfBoundsException e)
            { System.out.println(e.getMessage()); e.printStackTrace(); }
        }
        
        for(int i = 0; i <= SECTIONS; i++)
            specificChangeGroups.set(i, 0);
        //final int semesterGap = 1;
        float change = 0;
        //Debug.line("isSelected:"+includeSummerRadioButton.isSelected());
        for(StudentInfo2 s : this.studentsInformation)
        {
            
            Pair<Integer,Semester> latest = new Pair<Integer,Semester>();
            Pair<Integer,Semester> earlier = new Pair<Integer,Semester>();
            
            latest = s.getLatestSemester(summerIncluded);
            earlier = s.getSemesterANumberBefore(this.toComboBox.getSelectedIndex()+1, summerIncluded);
            
            
            if(latest.type1.intValue() != -1 && earlier.type1.intValue() != -1)
            {
                
                change = s.getGPAFor(latest.type1, latest.type2, gpasWeighed, specificSubject) -
                        s.getGPAFor(earlier.type1, earlier.type2, gpasWeighed, specificSubject);
                
                if(Float.isNaN(change)) change = 0;
                /*
                Debug.line("Name:"+s.getName());
                Debug.line("Subject: "+ specificSubject.toString());
                //Debug.line("latest.grade:"+latest.type1+",latest.semester:"+latest.type2.toString());
                Debug.line("latestGPA="+s.getGPAFor(latest.type1, latest.type2, gpasWeighed));
                //Debug.line("prev.grade:"+prev.type1+",prev.semester:"+prev.type2.toString());
                Debug.line("prevGPA="+s.getGPAFor(earlier.type1, earlier.type2, gpasWeighed));
                Debug.line("Change:"+change);
                Debug.line("(change+4)*4:"+(int)((change+4)*4));
                */
                
                specificChangeGroups.set((int)((change+4)*4), specificChangeGroups.get((int)((change+4)*4))+1); 
            }
            
        }
        
    }
    
    public void generateGroups()
    {
        //NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //SOMETHING SEEEMS TO BE WRONG WITH INSTANCES WHARE THE CHANGE IN GRADE IS 0.0
        //NOT GETTING ADDED TO THE GRAPH AT ALL
        for(int i = 0; i <= SECTIONS; i++)
            changeGroups.set(i, 0);
        //final int semesterGap = 1;
        float change = 0;
        
        Debug.line("SummerIncluded?: "+summerIncluded);
        
        //Debug.line("isSelected:"+includeSummerRadioButton.isSelected());
        for(StudentInfo2 s : this.studentsInformation)
        {
            /*
            latest = s.getLatestSemester();
            prev = s.getSemesterBefore(s.getLatestSemester().type1.intValue(), 
                    s.getLatestSemester().type2);
            */
            Pair<Integer,Semester> latest = new Pair<Integer,Semester>();
            Pair<Integer,Semester> earlier = new Pair<Integer,Semester>();
            /*
            latest = s.getLatestSemester(includeSummerRadioButton.isSelected());
            prev = s.getSemesterBefore(s.getLatestSemester(includeSummerRadioButton.isSelected()).type1.intValue(), 
                    s.getLatestSemester(includeSummerRadioButton.isSelected()).type2,includeSummerRadioButton.isSelected());
            */
            
            latest = s.getLatestSemester(summerIncluded);
            earlier = s.getSemesterANumberBefore(this.toComboBox.getSelectedIndex()+1, summerIncluded);
            /*
            prev = s.getSemesterBefore(s.getLatestSemester(summerIncluded).type1.intValue(), 
                    s.getLatestSemester(summerIncluded).type2, summerIncluded);
            */
            
            if(latest.type1.intValue() != -1 && earlier.type1.intValue() != -1)
            {
                change = s.getGPAFor(latest.type1, latest.type2, gpasWeighed) -
                        s.getGPAFor(earlier.type1, earlier.type2, gpasWeighed);
                //if(change == 8) change=change-0.1F;
                               
                
                Debug.line("Name:"+s.getName());
                Debug.line("Change:"+change);
                /*
                //Debug.line("latest.grade:"+latest.type1+",latest.semester:"+latest.type2.toString());
                Debug.line("latestGPA="+s.getGPAFor(latest.type1, latest.type2, gpasWeighed));
                //Debug.line("prev.grade:"+prev.type1+",prev.semester:"+prev.type2.toString());
                Debug.line("prevGPA="+s.getGPAFor(prev.type1, prev.type2, gpasWeighed));
                Debug.line("Change:"+change);
                Debug.line("(change+4)*4:"+(int)((change+4)*4));
                */
                //change = +.15F;
                changeGroups.set((int)((change+4)*4), changeGroups.get((int)((change+4)*4))+1); 
            }
            
            
            
            
            //Float getGPAFor(int grade, Semester semester, String weighed)
            /*change = s.getGPAFor(s.getLatestSemester().type1, 
                    s.getLatestSemester().type2, gpasWeighed).floatValue()-
            s.getGPAFor(s.getSemesterBefore(s.getLatestSemester().type1.intValue(), 
                    s.getLatestSemester().type2).type1,
                    s.getSemesterBefore(s.getLatestSemester().type1.intValue(), 
                    s.getLatestSemester().type2).type2, gpasWeighed).floatValue();*/
            
        }
        
        //for(int i = 0; i < changeGroups.size(); i++)
        //    Debug.line("index "+i+":"+changeGroups.get(i));
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        averageChangePanel = new javax.swing.JPanel();
        toComboBox = new javax.swing.JComboBox();
        compareToLabel = new javax.swing.JLabel();
        oneHundredPercentLabel = new javax.swing.JLabel();
        fiftyPercentLabel = new javax.swing.JLabel();
        zeroPercentLabel = new javax.swing.JLabel();
        saveGraphButton = new javax.swing.JButton();
        generalLayoutPanel = new studentbrowser.GraphLayoutPanel(this);
        generalGraphPanel = new studentbrowser.GeneralGraphPanel(this);
        includeSummerRadioButton = new javax.swing.JRadioButton();
        specificLayoutPanel = new studentbrowser.GraphLayoutPanel(this);
        specificGraphPanel = new SpecificGraphPanel(this);
        subjectComboBox = new javax.swing.JComboBox();
        generalGraphLabel = new javax.swing.JLabel();
        specificGraphLabel = new javax.swing.JLabel();
        saveSpecificGraphButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(studentbrowser.StudentBrowserApp.class).getContext().getResourceMap(StudentBrowserStudentStatisticsDialog.class);
        averageChangePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("averageChangePanel.border.title"))); // NOI18N
        averageChangePanel.setName("averageChangePanel"); // NOI18N

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "One Semester Ago", "Two Semesters Ago", "Three Semesters Ago", "Four Semesters Ago", "Five Semesters Ago", "Six Semesters Ago" }));
        toComboBox.setName("toComboBox"); // NOI18N
        toComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toComboBoxActionPerformed(evt);
            }
        });

        compareToLabel.setText(resourceMap.getString("compareToLabel.text")); // NOI18N
        compareToLabel.setName("compareToLabel"); // NOI18N

        oneHundredPercentLabel.setText(resourceMap.getString("oneHundredPercentLabel.text")); // NOI18N
        oneHundredPercentLabel.setName("oneHundredPercentLabel"); // NOI18N

        fiftyPercentLabel.setText(resourceMap.getString("fiftyPercentLabel.text")); // NOI18N
        fiftyPercentLabel.setName("fiftyPercentLabel"); // NOI18N

        zeroPercentLabel.setText(resourceMap.getString("zeroPercentLabel.text")); // NOI18N
        zeroPercentLabel.setName("zeroPercentLabel"); // NOI18N

        saveGraphButton.setText(resourceMap.getString("saveGraphButton.text")); // NOI18N
        saveGraphButton.setName("saveGraphButton"); // NOI18N
        saveGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGraphButtonActionPerformed(evt);
            }
        });

        generalLayoutPanel.setName("generalLayoutPanel"); // NOI18N

        generalGraphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        generalGraphPanel.setName("generalGraphPanel"); // NOI18N

        javax.swing.GroupLayout generalGraphPanelLayout = new javax.swing.GroupLayout(generalGraphPanel);
        generalGraphPanel.setLayout(generalGraphPanelLayout);
        generalGraphPanelLayout.setHorizontalGroup(
            generalGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
        generalGraphPanelLayout.setVerticalGroup(
            generalGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout generalLayoutPanelLayout = new javax.swing.GroupLayout(generalLayoutPanel);
        generalLayoutPanel.setLayout(generalLayoutPanelLayout);
        generalLayoutPanelLayout.setHorizontalGroup(
            generalLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
            .addGroup(generalLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(generalLayoutPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(generalGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );
        generalLayoutPanelLayout.setVerticalGroup(
            generalLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
            .addGroup(generalLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(generalLayoutPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(generalGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(110, Short.MAX_VALUE)))
        );

        includeSummerRadioButton.setText(resourceMap.getString("includeSummerRadioButton.text")); // NOI18N
        includeSummerRadioButton.setName("includeSummerRadioButton"); // NOI18N
        includeSummerRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeSummerRadioButtonActionPerformed(evt);
            }
        });

        specificLayoutPanel.setName("specificLayoutPanel"); // NOI18N

        specificGraphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        specificGraphPanel.setName("specificGraphPanel"); // NOI18N

        javax.swing.GroupLayout specificGraphPanelLayout = new javax.swing.GroupLayout(specificGraphPanel);
        specificGraphPanel.setLayout(specificGraphPanelLayout);
        specificGraphPanelLayout.setHorizontalGroup(
            specificGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
        specificGraphPanelLayout.setVerticalGroup(
            specificGraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout specificLayoutPanelLayout = new javax.swing.GroupLayout(specificLayoutPanel);
        specificLayoutPanel.setLayout(specificLayoutPanelLayout);
        specificLayoutPanelLayout.setHorizontalGroup(
            specificLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specificLayoutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(specificGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        specificLayoutPanelLayout.setVerticalGroup(
            specificLayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specificLayoutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(specificGraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        subjectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "English", "Math", "Science", "History", "Foreign Language" }));
        subjectComboBox.setSelectedIndex(0);
        subjectComboBox.setName("subjectComboBox"); // NOI18N
        subjectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectComboBoxActionPerformed(evt);
            }
        });

        generalGraphLabel.setText(resourceMap.getString("generalGraphLabel.text")); // NOI18N
        generalGraphLabel.setName("generalGraphLabel"); // NOI18N

        specificGraphLabel.setText(resourceMap.getString("specificGraphLabel.text")); // NOI18N
        specificGraphLabel.setName("specificGraphLabel"); // NOI18N

        saveSpecificGraphButton.setText(resourceMap.getString("saveSpecificGraphButton.text")); // NOI18N
        saveSpecificGraphButton.setName("saveSpecificGraphButton"); // NOI18N
        saveSpecificGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSpecificGraphButtonActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1st progress report", "2nd progress report", "3rd progress report", "final grade" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout averageChangePanelLayout = new javax.swing.GroupLayout(averageChangePanel);
        averageChangePanel.setLayout(averageChangePanelLayout);
        averageChangePanelLayout.setHorizontalGroup(
            averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(averageChangePanelLayout.createSequentialGroup()
                .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(averageChangePanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(oneHundredPercentLabel)
                            .addComponent(zeroPercentLabel)
                            .addComponent(fiftyPercentLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generalLayoutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(averageChangePanelLayout.createSequentialGroup()
                                .addComponent(generalGraphLabel)
                                .addGap(18, 18, 18)
                                .addComponent(saveGraphButton)))
                        .addGap(35, 35, 35)
                        .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, averageChangePanelLayout.createSequentialGroup()
                                .addComponent(specificGraphLabel)
                                .addGap(18, 18, 18)
                                .addComponent(saveSpecificGraphButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(subjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(specificLayoutPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(averageChangePanelLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(compareToLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(85, 85, 85)
                        .addComponent(includeSummerRadioButton)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        averageChangePanelLayout.setVerticalGroup(
            averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, averageChangePanelLayout.createSequentialGroup()
                .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generalGraphLabel)
                    .addComponent(specificGraphLabel)
                    .addComponent(saveGraphButton)
                    .addComponent(saveSpecificGraphButton)
                    .addComponent(subjectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(averageChangePanelLayout.createSequentialGroup()
                        .addComponent(oneHundredPercentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                        .addComponent(fiftyPercentLabel)
                        .addGap(183, 183, 183)
                        .addComponent(zeroPercentLabel)
                        .addGap(159, 159, 159))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, averageChangePanelLayout.createSequentialGroup()
                        .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(specificLayoutPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(generalLayoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(averageChangePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(includeSummerRadioButton)
                            .addComponent(compareToLabel)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(21, 21, 21))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(averageChangePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(averageChangePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void saveGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGraphButtonActionPerformed
    this.writeImage("testImage.jpg", true);
    
}//GEN-LAST:event_saveGraphButtonActionPerformed

private void includeSummerRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeSummerRadioButtonActionPerformed
    summerIncluded = includeSummerRadioButton.isSelected();
    //this.initComponents();
    //this.generateGroups();
    
    //NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    generalGraphImage = new java.awt.image.BufferedImage(
                generalLayoutPanel.getWidth(), generalLayoutPanel.getHeight(), java.awt.image.BufferedImage.TYPE_INT_RGB);
        //Set up graphImageGraphics2D
        generalGraphImageGraphics2D = generalGraphImage.createGraphics();
        //Fill in backround
        generalGraphImageGraphics2D.setColor(generalLayoutPanel.getBackground());
        generalGraphImageGraphics2D.fillRect(0, 0, getWidth(), getHeight());
        //reset color
        generalGraphImageGraphics2D.setColor(java.awt.Color.black);
        ((GeneralGraphPanel)generalGraphPanel).setPercentages();
        generalGraphPanel.repaint();
        
        generalLayoutPanel.repaint();
    //NOTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //The code above MUST be duplicated for the specific graph panel
    
    //((GeneralGraphPanel)this.generalGraphPanel).setPercentages();
    ((SpecificGraphPanel)this.specificGraphPanel).setPercentages();
    
    //generalGraphPanel.repaint();
    specificGraphPanel.repaint();
    //((GeneralGraphPanel)graphPanel).paintImmediately(0, 0, graphPanel.getWidth(), graphPanel.getHeight());
}//GEN-LAST:event_includeSummerRadioButtonActionPerformed

private void toComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toComboBoxActionPerformed
    ((GeneralGraphPanel)this.generalGraphPanel).setPercentages();
    ((SpecificGraphPanel)this.specificGraphPanel).setPercentages();
    generalGraphPanel.repaint();
    specificGraphPanel.repaint();
}//GEN-LAST:event_toComboBoxActionPerformed

private void subjectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectComboBoxActionPerformed
    //generateSpecificGroups();
    ((SpecificGraphPanel)this.specificGraphPanel).setPercentages();
    specificGraphLabel.setText(subjectComboBox.getSelectedItem()+" Grade Change");
    specificGraphPanel.repaint();
}//GEN-LAST:event_subjectComboBoxActionPerformed

private void saveSpecificGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSpecificGraphButtonActionPerformed
    this.writeImage("testImage.jpg", false);
}//GEN-LAST:event_saveSpecificGraphButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StudentBrowserStudentStatisticsDialog dialog = new StudentBrowserStudentStatisticsDialog(new javax.swing.JFrame(), null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel averageChangePanel;
    private javax.swing.JLabel compareToLabel;
    private javax.swing.JLabel fiftyPercentLabel;
    private javax.swing.JLabel generalGraphLabel;
    private javax.swing.JPanel generalGraphPanel;
    private javax.swing.JPanel generalLayoutPanel;
    private javax.swing.JRadioButton includeSummerRadioButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel oneHundredPercentLabel;
    private javax.swing.JButton saveGraphButton;
    private javax.swing.JButton saveSpecificGraphButton;
    private javax.swing.JLabel specificGraphLabel;
    private javax.swing.JPanel specificGraphPanel;
    private javax.swing.JPanel specificLayoutPanel;
    private javax.swing.JComboBox subjectComboBox;
    private javax.swing.JComboBox toComboBox;
    private javax.swing.JLabel zeroPercentLabel;
    // End of variables declaration//GEN-END:variables

}
