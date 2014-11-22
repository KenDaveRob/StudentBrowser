/*
 * StudentBrowserView.java
 */


/*
  ArrayList<String> rowStrings = new ArrayList<String>();
        for(StudentInfo s : studentsInformation)
        {
        for(int l = 0; l < ((Enum)s.getStudentGrade()).ordinal()+1;l++)
            {
            
                if(s.getClasses().hasGradeList(l+9))
                {
                    //ClassList test = s.getClasses().getGradeList(l+9);
                    if(s.getClasses().getGradeList(l+9).presentDuring(Semester.FALL))
                    {
                        rowStrings.add((l+9)+"th Grade Fall");
                    }
                    if(s.getClasses().getGradeList(l+9).presentDuring(Semester.SPRING))
                    {
                        rowStrings.add((l+9)+"th Grade Spring");
                    }
                    if(s.getClasses().getGradeList(l+9).presentDuring(Semester.SUMMER))
                    {
                        rowStrings.add((l+9)+"th Grade Summer");
                    }
                }
            }
        }
            
            for(String e : rowStrings)
                Debug.line(e);

*/
package studentbrowser;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.*;
import java.text.*;




/**
 * The application's main frame.
 * 
 * NOTE:
 * Create a selection around the grade letter(s) for progress report grades to speed up imputting
 */
public class StudentBrowserView extends FrameView {
    public StudentBrowserView(SingleFrameApplication app) {
        super(app);
        defaultCellEditor = new javax.swing.DefaultCellEditor(new javax.swing.JTextField());
        currentFile = ((StudentBrowserApp)app).getInitialFile();
        studentsInformation = ((StudentBrowserApp)app).getStudentList();
        progressGradeCombo = new javax.swing.JComboBox();
        
        progressGradeCombo.addItem("Final Grade");
        progressGradeCombo.addItem("1st Progress");
        progressGradeCombo.addItem("2nd Progress");
        progressGradeCombo.addItem("3rd Progress");
        
        progressGradeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentInformationTable.repaint();
            }
        });
        
        //Debug.line("Icon:"+this.getFrame().getIconImage().toString());
        //java.awt.Toolkit kit = java.awt.Toolkit.getDefaultToolkit();
        //this.getFrame().setIconImage(kit.getImage("logo.jpg"));
        //System.out.println(studentList.first().toString());
        //ArrayList<Pair<String,ArrayList<Pair<String,Float>>

        //System.out.println("Inside Frame FIRST STUDENT:"+studentsInformation.first().getName());
        //studentsInformation.first().printClassLists();
        //studentsInformation.first().setStudentGrade(StudentGrade.FRESHMAN);
        //Debug.line("((StudentInfo)(studentList.getSelectedValue())).getName() method:"+((StudentInfo)(studentList.getSelectedValue())).getName());
        //studentsInformation.first().generateClassPlaceholder(9, Semester.SUMMER);
        initComponents();
        
        
        javax.swing.table.TableColumnModel columnModel = studentInformationTable.getColumnModel();
        javax.swing.table.TableColumn firstColumn = columnModel.getColumn(1);
        firstColumn.setCellEditor(new javax.swing.DefaultCellEditor(progressGradeCombo));
        
        
        
        saveSummerColumns();
        
        this.resetListGPAAverage();
        
        gradeComboBox.setSelectedIndex(studentsInformation.first().getStudentGrade().ordinal());
        genderComboBox.setSelectedIndex(studentsInformation.first().getGender().ordinal());
        mathCahseeStatusComboBox.setSelectedIndex(studentsInformation.first().getMathCahseeStatus().ordinal());
        englishCahseeStatusComboBox.setSelectedIndex(studentsInformation.first().getEnglishCahseeStatus().ordinal());
        
        this.generateAddSemestersMenuItems();
        this.newStudentNamesAdded = 0;
        
        
        //org.netbeans.modules.form.editors2.JTableHeaderEditor$FormTableHeader@d789af
        //this.studentInformationTable.getTableHeader().setReorderingAllowed(true);
      
        //javax.swing.JMenu temp = this.addSemestersMenu;
        //this.studentInformationTablePopupMenu.add(temp);
        
        //javax.swing.JMenu temp = new javax.swing.JMenu("temp");
        //this.studentInformationTablePopupMenu.add(temp);
        //this.studentInformationTablePopupMenu.ad
        //this.studentInformationTablePopupMenu.add(this.addSemestersMenu);
        
        //this.addSemesterMenu.add(new javax.swing.JMenuItem("INSERTED"));
        
        //((StudentInformationTableModel)(this.studentInformationTable.getModel())).setExtraRows(3);
        //Debug.line("RESIZE MODE:"+gpaTable.getAutoResizeMode());
        //gpaTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        //NumberFormat test =NumberFormat.getIntegerInstance();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);
        gradeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeComboBoxChanged(evt);
            }
        });
        
        
        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }
    
    private void saveSummerColumns()
    {
        summerCols.clear();
        for(int index = 1; index <= 12; index++)
        {
            if((index-1) % 3 == 2)
                summerCols.add(gpaTable.getColumnModel().getColumn(index));
        }
    }
    
    public void resetListGPAAverage()
    {
        float totalAverageGPA = 0;
        float totalStudents = 0;
        
        for(StudentInfo2 s : studentsInformation)
        {
            totalAverageGPA += s.getGPA("with weighed grades");
            totalStudents++;
        }
        
        totalAverageGPA /= totalStudents;
        //Debug.line("totalStudents: "+totalStudents+", totalAverageGPA");
        
        this.averageWeighedGPALabel.setText("Average Weighed GPA = " + Float.toString(totalAverageGPA));
        
        totalAverageGPA = 0;
        totalStudents = 0;
        
        for(StudentInfo2 s : studentsInformation)
        {
            totalAverageGPA += s.getGPA("with out weighed grades");
            totalStudents++;
        }
        
        totalAverageGPA /= totalStudents;
        //Debug.line("totalStudents: "+totalStudents+", totalAverageGPA");
        
        this.averageGPALabel.setText("Average GPA = " + Float.toString(totalAverageGPA));
        
    }
    
    

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = StudentBrowserApp.getApplication().getMainFrame();
            aboutBox = new StudentBrowserAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        StudentBrowserApp.getApplication().show(aboutBox);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainTabbedPane = new javax.swing.JTabbedPane();
        studentListingPanel = new javax.swing.JPanel();
        studentListScrollPane = new javax.swing.JScrollPane();
        studentList = new javax.swing.JList();
        studentInformationTableScrollPane = new javax.swing.JScrollPane();
        studentInformationTable = new javax.swing.JTable();
        studentInformationPanel = new javax.swing.JPanel();
        gradeComboBox = new javax.swing.JComboBox();
        nameTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        gradeLabel = new javax.swing.JLabel();
        dateOfBirthLabel = new javax.swing.JLabel();
        dateOfBirthTextField = new javax.swing.JTextField();
        genderLabel = new javax.swing.JLabel();
        genderComboBox = new javax.swing.JComboBox();
        mathCahseeStatusLabel = new javax.swing.JLabel();
        mathCahseeStatusComboBox = new javax.swing.JComboBox();
        englishCahseeStatusComboBox = new javax.swing.JComboBox();
        englishCahseeStatusLabel = new javax.swing.JLabel();
        notesPanel = new javax.swing.JPanel();
        notesScrollPane = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();
        //Removed because it wasnt working correctlyj
        /*
        ProgressReportLabel = new javax.swing.JLabel();
        allProgressReportComboBox = new javax.swing.JComboBox();
        */
        gradePointAveragePanel = new javax.swing.JPanel();
        gpaTableScrollPane = new javax.swing.JScrollPane();
        gpaTable = new javax.swing.JTable();
        infoPanel = new javax.swing.JPanel();
        averageGPALabel = new javax.swing.JLabel();
        averageWeighedGPALabel = new javax.swing.JLabel();
        showSummersCheckBox = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        loadStudentNamesMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        addStudentMenuItem = new javax.swing.JMenuItem();
        removeStudentMenuItem = new javax.swing.JMenuItem();
        addSemestersMenu = new javax.swing.JMenu();
        viewMenu = new javax.swing.JMenu();
        studentStatisticsMenuItem = new javax.swing.JMenuItem();
        tableMenu = new javax.swing.JMenu();
        createStudentsTablesMenuItem = new javax.swing.JMenuItem();
        createGPAsTableMenuItem = new javax.swing.JMenuItem();
        createYearlyGPAsTableMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        studentListPopupMenu = new javax.swing.JPopupMenu();
        addStudentPopupMenuItem = new javax.swing.JMenuItem();
        removeStudentPopupMenuItem = new javax.swing.JMenuItem();
        studentInformationTablePopupMenu = new javax.swing.JPopupMenu();
        addSemestersPopupMenu = new javax.swing.JMenu();

        mainPanel.setName("mainPanel"); // NOI18N

        mainTabbedPane.setName("mainTabbedPane"); // NOI18N
        mainTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainTabbedPaneStateChanged(evt);
            }
        });

        studentListingPanel.setName("studentListingPanel"); // NOI18N

        studentListScrollPane.setName("studentListScrollPane"); // NOI18N

        studentList.setModel(new StudentListModel(studentsInformation));
        studentList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        studentList.setName("studentList");
        studentList.setSelectedIndex(0);
        studentList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                studentListMousePressed(evt);
            }
        });
        studentList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                studentListValueChanged(evt);
            }
        });
        studentList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                studentListKeyPressed(evt);
            }
        });
        studentListScrollPane.setViewportView(studentList);

        studentInformationTableScrollPane.setName("studentInformationTableScrollPane"); // NOI18N
        studentInformationTableScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                studentInformationTableAreaMousePressed(evt);
            }
        });

        studentInformationTable.setColumnModel(new ReorderingControlableTableColumnModel(-1,1));
        studentInformationTable.setModel(new StudentInformationTableModel(studentsInformation.first(),8,this));
        studentInformationTable.setCellEditor(defaultCellEditor);
        studentInformationTable.setCellSelectionEnabled(true);
        studentInformationTable.setName("studentInformationTable"); // NOI18N
        studentInformationTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                studentInformationTableAreaMousePressed(evt);
            }
        });
        studentInformationTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                studentInformationTableKeyPressed(evt);
            }
        });
        studentInformationTableScrollPane.setViewportView(studentInformationTable);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(studentbrowser.StudentBrowserApp.class).getContext().getResourceMap(StudentBrowserView.class);
        studentInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("studentInformationPanel.border.title"))); // NOI18N
        studentInformationPanel.setName("studentInformationPanel"); // NOI18N

        gradeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Freshman", "Sophomore", "Junior", "Senior" }));
        gradeComboBox.setName("gradeComboBox"); // NOI18N
        gradeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradeComboBoxChanged(evt);
            }
        });

        nameTextField.setText(((StudentInfo2)(studentList.getSelectedValue())).getName());
        nameTextField.setMinimumSize(new java.awt.Dimension(72, 20));
        nameTextField.setName("nameTextField"); // NOI18N
        nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nameTextFieldKeyPressed(evt);
            }
        });

        nameLabel.setText(resourceMap.getString("nameLabel.text")); // NOI18N
        nameLabel.setName("nameLabel"); // NOI18N

        gradeLabel.setText(resourceMap.getString("gradeLabel.text")); // NOI18N
        gradeLabel.setName("gradeLabel"); // NOI18N

        dateOfBirthLabel.setText(resourceMap.getString("dateOfBirthLabel.text")); // NOI18N
        dateOfBirthLabel.setName("dateOfBirthLabel"); // NOI18N

        dateOfBirthTextField.setText((((StudentInfo2)(studentList.getSelectedValue())).getStringDateOfBirth()));
        dateOfBirthTextField.setName("dateOfBirthTextField"); // NOI18N
        dateOfBirthTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dateOfBirthTextFieldFocusGained(evt);
            }
        });
        dateOfBirthTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfBirthTextFieldKeyPressed(evt);
            }
        });

        genderLabel.setText(resourceMap.getString("genderLabel.text")); // NOI18N
        genderLabel.setName("genderLabel"); // NOI18N

        genderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        genderComboBox.setName("genderComboBox"); // NOI18N
        genderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genderComboBoxChanged(evt);
            }
        });

        mathCahseeStatusLabel.setText(resourceMap.getString("mathCahseeStatusLabel.text")); // NOI18N
        mathCahseeStatusLabel.setName("mathCahseeStatusLabel"); // NOI18N

        mathCahseeStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Passed", "Not Taken", "Not Passed" }));
        mathCahseeStatusComboBox.setName("mathCahseeStatusComboBox"); // NOI18N
        mathCahseeStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mathCahseeStatusComboBoxChanged(evt);
            }
        });

        englishCahseeStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Passed", "Not Taken", "Not Passed" }));
        englishCahseeStatusComboBox.setName("englishCahseeStatusComboBox"); // NOI18N
        englishCahseeStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                englishCahseeStatusComboBoxChanged(evt);
            }
        });

        englishCahseeStatusLabel.setText(resourceMap.getString("englishCahseeStatusLabel.text")); // NOI18N
        englishCahseeStatusLabel.setName("englishCahseeStatusLabel"); // NOI18N

        javax.swing.GroupLayout studentInformationPanelLayout = new javax.swing.GroupLayout(studentInformationPanel);
        studentInformationPanel.setLayout(studentInformationPanelLayout);
        studentInformationPanelLayout.setHorizontalGroup(
            studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentInformationPanelLayout.createSequentialGroup()
                            .addComponent(nameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, studentInformationPanelLayout.createSequentialGroup()
                            .addComponent(dateOfBirthLabel)
                            .addGap(18, 18, 18)
                            .addComponent(dateOfBirthTextField)))
                    .addGroup(studentInformationPanelLayout.createSequentialGroup()
                        .addComponent(genderLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(gradeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gradeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentInformationPanelLayout.createSequentialGroup()
                        .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mathCahseeStatusLabel)
                            .addComponent(englishCahseeStatusLabel))
                        .addGap(18, 18, 18)
                        .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(englishCahseeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mathCahseeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        studentInformationPanelLayout.setVerticalGroup(
            studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInformationPanelLayout.createSequentialGroup()
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateOfBirthLabel)
                    .addComponent(dateOfBirthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderLabel)
                    .addComponent(genderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gradeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gradeLabel))
                .addGap(18, 18, 18)
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mathCahseeStatusLabel)
                    .addComponent(mathCahseeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(studentInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(englishCahseeStatusLabel)
                    .addComponent(englishCahseeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        notesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("notesPanel.border.title"))); // NOI18N
        notesPanel.setName("notesPanel"); // NOI18N

        notesScrollPane.setName("notesScrollPane"); // NOI18N

        notesTextArea.setColumns(20);
        notesTextArea.setDocument(((StudentInfo2)(studentList.getSelectedValue())).getNotes());
        notesTextArea.setLineWrap(true);
        notesTextArea.setRows(5);
        notesTextArea.setName("notesTextArea"); // NOI18N
        notesTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                notesTextAreaFocusLost(evt);
            }
        });
        notesScrollPane.setViewportView(notesTextArea);

        javax.swing.GroupLayout notesPanelLayout = new javax.swing.GroupLayout(notesPanel);
        notesPanel.setLayout(notesPanelLayout);
        notesPanelLayout.setHorizontalGroup(
            notesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        notesPanelLayout.setVerticalGroup(
            notesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
        //Removed because it wasnt working properly
        /*
        ProgressReportLabel.setText(resourceMap.getString("ProgressReportLabel.text")); // NOI18N
        ProgressReportLabel.setName("ProgressReportLabel"); // NOI18N

        allProgressReportComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Final Grade", "1st Progress", "2nd Progress", "3rd Progress" }));
        allProgressReportComboBox.setName("allProgressReportComboBox"); // NOI18N
        allProgressReportComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allProgressReportComboBoxActionPerformed(evt);
            }
        });
        */

        javax.swing.GroupLayout studentListingPanelLayout = new javax.swing.GroupLayout(studentListingPanel);
        studentListingPanel.setLayout(studentListingPanelLayout);
        studentListingPanelLayout.setHorizontalGroup(
            studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentListingPanelLayout.createSequentialGroup()
                .addComponent(studentListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    //Removed because it wasnt working properly
                    /*
                    .addGroup(studentListingPanelLayout.createSequentialGroup()
                        .addComponent(ProgressReportLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(allProgressReportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    */
                    .addGroup(studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(studentListingPanelLayout.createSequentialGroup()
                            .addComponent(studentInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(notesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addContainerGap())
                        .addComponent(studentInformationTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE))))
        );
        studentListingPanelLayout.setVerticalGroup(
            studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(studentListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
            .addGroup(studentListingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(studentInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                //Removed because it wasn't working properly
                /*
                .addGroup(studentListingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProgressReportLabel)
                    .addComponent(allProgressReportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                */
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studentInformationTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab(resourceMap.getString("studentListingPanel.TabConstraints.tabTitle"), studentListingPanel); // NOI18N

        gradePointAveragePanel.setName("gradePointAveragePanel"); // NOI18N

        gpaTableScrollPane.setName("gpaTableScrollPane"); // NOI18N

        gpaTable.setModel(new GPATableModel(studentsInformation));
        gpaTable.setCellSelectionEnabled(true);
        gpaTable.setName("gpaTable"); // NOI18N
        gpaTable.getTableHeader().setReorderingAllowed(false);
        gpaTableScrollPane.setViewportView(gpaTable);

        infoPanel.setName("infoPanel"); // NOI18N

        averageGPALabel.setText(resourceMap.getString("averageGPALabel.text")); // NOI18N
        averageGPALabel.setName("averageGPALabel"); // NOI18N

        averageWeighedGPALabel.setText(resourceMap.getString("averageWeighedGPALabel.text")); // NOI18N
        averageWeighedGPALabel.setName("averageWeighedGPALabel"); // NOI18N

        showSummersCheckBox.setSelected(true);
        showSummersCheckBox.setText(resourceMap.getString("showSummersCheckBox.text")); // NOI18N
        showSummersCheckBox.setName("showSummersCheckBox"); // NOI18N
        showSummersCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showSummersCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(averageWeighedGPALabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 471, Short.MAX_VALUE)
                .addComponent(showSummersCheckBox)
                .addGap(18, 18, 18)
                .addComponent(averageGPALabel)
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(averageWeighedGPALabel)
                    .addComponent(averageGPALabel)
                    .addComponent(showSummersCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout gradePointAveragePanelLayout = new javax.swing.GroupLayout(gradePointAveragePanel);
        gradePointAveragePanel.setLayout(gradePointAveragePanelLayout);
        gradePointAveragePanelLayout.setHorizontalGroup(
            gradePointAveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(gpaTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
        );
        gradePointAveragePanelLayout.setVerticalGroup(
            gradePointAveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gradePointAveragePanelLayout.createSequentialGroup()
                .addComponent(gpaTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainTabbedPane.addTab(resourceMap.getString("gradePointAveragePanel.TabConstraints.tabTitle"), gradePointAveragePanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(studentbrowser.StudentBrowserApp.class).getContext().getActionMap(StudentBrowserView.class, this);
        newMenuItem.setAction(actionMap.get("createNewStudentsInfoFileAction")); // NOI18N
        newMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        newMenuItem.setName("newMenuItem"); // NOI18N
        fileMenu.add(newMenuItem);

        openMenuItem.setAction(actionMap.get("openStudentsInfoFileAction")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setAction(actionMap.get("saveStudentsInfoFileAction")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setAction(actionMap.get("saveAsStudentsInfoFileAction")); // NOI18N
        saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        loadStudentNamesMenuItem.setAction(actionMap.get("loadStudentNames")); // NOI18N
        loadStudentNamesMenuItem.setText(resourceMap.getString("loadStudentNamesMenuItem.text")); // NOI18N
        loadStudentNamesMenuItem.setName("loadStudentNamesMenuItem"); // NOI18N
        fileMenu.add(loadStudentNamesMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        addStudentMenuItem.setAction(actionMap.get("addStudentAction")); // NOI18N
        addStudentMenuItem.setText(resourceMap.getString("addStudentMenuItem.text")); // NOI18N
        addStudentMenuItem.setName("addStudentMenuItem"); // NOI18N
        editMenu.add(addStudentMenuItem);

        removeStudentMenuItem.setAction(actionMap.get("removeStudentAction")); // NOI18N
        removeStudentMenuItem.setText(resourceMap.getString("removeStudentMenuItem.text")); // NOI18N
        removeStudentMenuItem.setName("removeStudentMenuItem"); // NOI18N
        editMenu.add(removeStudentMenuItem);

        addSemestersMenu.setText(resourceMap.getString("addSemestersMenu.text")); // NOI18N
        addSemestersMenu.setName("addSemestersMenu"); // NOI18N
        editMenu.add(addSemestersMenu);

        menuBar.add(editMenu);

        viewMenu.setText(resourceMap.getString("viewMenu.text")); // NOI18N
        viewMenu.setName("viewMenu"); // NOI18N

        studentStatisticsMenuItem.setAction(actionMap.get("showStudentStatisticsDialog")); // NOI18N
        studentStatisticsMenuItem.setText(resourceMap.getString("studentStatisticsMenuItem.text")); // NOI18N
        studentStatisticsMenuItem.setName("studentStatisticsMenuItem"); // NOI18N
        viewMenu.add(studentStatisticsMenuItem);

        menuBar.add(viewMenu);

        tableMenu.setText(resourceMap.getString("tableMenu.text")); // NOI18N
        tableMenu.setName("tableMenu"); // NOI18N

        createStudentsTablesMenuItem.setAction(actionMap.get("createStudentsTablesFileAction")); // NOI18N
        createStudentsTablesMenuItem.setName("createStudentsTablesMenuItem"); // NOI18N
        tableMenu.add(createStudentsTablesMenuItem);

        createGPAsTableMenuItem.setAction(actionMap.get("createGPAsTableFileAction")); // NOI18N
        createGPAsTableMenuItem.setName("createGPAsTableMenuItem"); // NOI18N
        tableMenu.add(createGPAsTableMenuItem);

        createYearlyGPAsTableMenuItem.setName("createYearlyGPAsTableMenuItem"); // NOI18N
        tableMenu.add(createYearlyGPAsTableMenuItem);

        menuBar.add(tableMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 694, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        studentListPopupMenu.setName("studentListPopupMenu"); // NOI18N

        addStudentPopupMenuItem.setAction(actionMap.get("addStudentAction")); // NOI18N
        addStudentPopupMenuItem.setName("addStudentPopupMenuItem"); // NOI18N
        studentListPopupMenu.add(addStudentPopupMenuItem);

        removeStudentPopupMenuItem.setAction(actionMap.get("removeStudentAction")); // NOI18N
        removeStudentPopupMenuItem.setName("removeStudentPopupMenuItem"); // NOI18N
        studentListPopupMenu.add(removeStudentPopupMenuItem);

        studentInformationTablePopupMenu.setName("studentInformationTablePopupMenu"); // NOI18N

        addSemestersPopupMenu.setText(resourceMap.getString("addSemestersPopupMenu.text")); // NOI18N
        addSemestersPopupMenu.setName("addSemestersPopupMenu"); // NOI18N
        studentInformationTablePopupMenu.add(addSemestersPopupMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

private void studentListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentListMousePressed
    if(evt.getButton() == evt.BUTTON3)
    {
        studentListPopupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
    }
        
}//GEN-LAST:event_studentListMousePressed

private void studentListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_studentListValueChanged

    
    if(this.studentList.getSelectedIndex() == -1) this.studentList.setSelectedIndex(0);
    
    //Make sure the list isn't empty
    if(((StudentInfo2)(this.studentList.getSelectedValue())) != null) 
    {
        
        //Set new name of current student assuming a change has been made
        //Removing and readding with new name is the only way to resort the tree
        StudentInfo2 temp = ((StudentInformationTableModel)(studentInformationTable.getModel())).
                getStudentInformation();
        if(!temp.getName().equals(this.nameTextField.getText()))
        {
            //Debug.line("TEMP ADDED!!!!!!!!!!!!!!");
            //Debug.line("temp.getName:"+temp.getName()+", nametextfield:"+this.nameTextField.getText());
            this.studentsInformation.remove(temp);
            temp.setName(this.nameTextField.getText());
            studentsInformation.add(temp);
            this.studentList.repaint();
        }
        /*
        ((StudentInformationTableModel)(studentInformationTable.getModel())).
                getStudentInformation().setName(nameTextField.getText());
        */

        /**
         * Set the student notes document to the document inside the notes text area 
         * assuming a change has been made.
         * Note: this if statement would increase efficency if I could ensure the equals method works
         */

        //if(!((StudentInformationTableModel)(studentInformationTable.getModel())).
        //        getStudentInformation().getNotes().equals(notesTextArea.getDocument()))
        //{
            //((StudentInformationTableModel)(studentInformationTable.getModel())).
                    //getStudentInformation().setNotes(notesTextArea.getDocument());
        //}


        //Set new DOB of current student DOBInputMARK
        //Should add a focuslost listener for the date field to check that it matches before this point
        String date = dateOfBirthTextField.getText();
        if(temp.getStringDateOfBirth() != date)
        {
            String infoStrings[] = date.split("/");
            //Check all three parts are present
            if(infoStrings.length == 3)
            {
                //If they are then make sure their the right size
                java.util.ArrayList<Integer> infoInts = new java.util.ArrayList<Integer>();
                try
                {
                    infoInts.add(Integer.parseInt(infoStrings[0]));
                    infoInts.add(Integer.parseInt(infoStrings[1]));
                    infoInts.add(Integer.parseInt(infoStrings[2]));
                }
                catch(java.lang.NumberFormatException NFE)
                {
                    System.out.println("ERROR: Decoding date string into numbers.");
                    System.out.println(NFE.getMessage());
                    NFE.printStackTrace();
                }
                if((infoInts.get(0) <= 12 && infoInts.get(0) >= 1) && 
                        (infoInts.get(1) <= 32 && infoInts.get(1) >= 1) &&
                        (infoInts.get(2) <= 99 && infoInts.get(2) >= 0))
                {
                    if(infoInts.get(2) < 30) infoInts.set(2, infoInts.get(2)+2000);
                    else infoInts.set(2, infoInts.get(2)+ 1900);

                    ((StudentInformationTableModel)(studentInformationTable.getModel())).
                    getStudentInformation().setDOB(new java.util.GregorianCalendar(
                            infoInts.get(2).intValue(), infoInts.get(0).intValue()-1, 
                            infoInts.get(1).intValue()));
                }
            }
        }




        //Deleate all PLACEHOLDER classes from current student unless its a new entry
        if(((StudentInfo2)(this.studentList.getSelectedValue())).onlyHasClassPlaceHolders() == false)
            ((StudentInfo2)(this.studentList.getSelectedValue())).deleateAllClassPlaceHolder();



        //Set text field to new name from list
        nameTextField.setText(((StudentInfo2)(studentList.getSelectedValue())).getName());

        //DOBInputMARK
        //Set DOB field to new DOB from list
        dateOfBirthTextField.setText(((StudentInfo2)(studentList.getSelectedValue())).getStringDateOfBirth());


        //Set notes text area to new document from list
        notesTextArea.setDocument(((StudentInfo2)studentList.getSelectedValue()).getNotes());
        //notesTextArea.setDocument(((StudentInformationTableModel)(studentInformationTable.getModel())).
        //        getStudentInformation().getNotes());



        //Set new student for table and repaint it
        ((StudentInformationTableModel)(studentInformationTable.getModel())).setStudentInformation((StudentInfo2)studentList.getSelectedValue());
        studentInformationTable.repaint();

        //Set the combo box to the grade of the new student
        gradeComboBox.setSelectedIndex(((StudentInformationTableModel)(studentInformationTable.getModel())).getStudentInformation().getStudentGrade().ordinal());
        genderComboBox.setSelectedIndex(((StudentInformationTableModel)(studentInformationTable.getModel())).getStudentInformation().getGender().ordinal());
        mathCahseeStatusComboBox.setSelectedIndex(((StudentInformationTableModel)(studentInformationTable.getModel())).getStudentInformation().getMathCahseeStatus().ordinal());
        englishCahseeStatusComboBox.setSelectedIndex(((StudentInformationTableModel)(studentInformationTable.getModel())).getStudentInformation().getEnglishCahseeStatus().ordinal());
        
        //Regenerate possible add semester menu items
        this.generateAddSemestersMenuItems();
    }
    //if it is empty
    else
    {
        Debug.line("is empty");
        this.addSemestersMenu.removeAll();
        this.addSemestersPopupMenu.removeAll();
        this.removeStudentMenuItem.setEnabled(false);
        this.removeStudentPopupMenuItem.setEnabled(false);
    }
    /*
    Debug.line("New studentsInformation");
        for(StudentInfo e : studentsInformation)
            {
                System.out.println("Student:"+e.getName());
                e.printClassLists();
            }
    */
    //StudentInformationTableModel test = new StudentInformationTableModel(studentsInformation.first(), 6);
    //
    
}//GEN-LAST:event_studentListValueChanged

private void studentInformationTableAreaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentInformationTableAreaMousePressed
    if(evt.getButton() == evt.BUTTON3)
    {
        this.studentInformationTablePopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        //studentListPopupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
    }
    //studentInformationTable.getTableHeader()
    //else if(evt.getButton() == evt.BUTTON1 && studentInformationTable.getColumnModel().get)
}//GEN-LAST:event_studentInformationTableAreaMousePressed

private void notesTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_notesTextAreaFocusLost
            ((StudentInformationTableModel)(studentInformationTable.getModel())).
                getStudentInformation().setNotes(notesTextArea.getDocument());
}//GEN-LAST:event_notesTextAreaFocusLost

private void gradeComboBoxChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradeComboBoxChanged
    
    //This code uses the Models own methods for adding extra rows, 
    //which is very confusing and since I now have a better way to handle it
    //using the PLACEHOLDER ClassType it should be rewritten at some point.
    
    //studentsGradeIndex+9 = Students Actual Grade Number
    final int studentsGradeIndex  = ((StudentInformationTableModel)(studentInformationTable.getModel())).
            getStudentInformation().getStudentGrade().ordinal();
    
    //The extra grades that the tableModel uses to generate blank rows
    final int extraGradeLevel = ((StudentInformationTableModel)(studentInformationTable.getModel())).getExtraGradeLevel();
    
    //newGradeIndex+9 == The desired new grade level
    final int newGradeIndex = Enum.valueOf(StudentGrade.class, ((String)gradeComboBox.getSelectedItem()).
            toUpperCase()).ordinal();
    
        
    //Looks for the most recent grade that the student has taken a class and
    //compares it to the newGradeIndex or if the extra blank rows generated by
    //the extraGradeLevel is greater than the newGradeIndex.
    if(studentsGradeIndex < newGradeIndex || ((extraGradeLevel-9 > newGradeIndex)  && 
            (studentsGradeIndex == newGradeIndex)))
    {
        //This has the effect of being able to both add rows if the newGradeIndex is greater
        //or deleate rows if both the newGradeIndex is less than the previous extraGradeLevel
        //NOTE: this CANNOT deleate rows that have classes (Placeholder or otherwise) in that row
        ((StudentInformationTableModel)(studentInformationTable.getModel())).setExtraGrades(newGradeIndex+9);
    }
    
    else if(studentsGradeIndex != newGradeIndex)
    {
        //Debug.line("studentsGradeIndex: "+studentsGradeIndex+" newGradeIndex: "+newGradeIndex);
        int response = javax.swing.JOptionPane.showConfirmDialog(this.getFrame(), 
                "Warning: Class information will be lost. Continue anyway?", "Warning", 
                javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
        if(response == javax.swing.JOptionPane.YES_OPTION)
        {
            
            for(int i = ((StudentInformationTableModel)(studentInformationTable.getModel())).
                    getStudentInformation().getClasses().getSize(); i < newGradeIndex; i--)
            {
                java.util.Iterator<ClassInfo> it = ((StudentInformationTableModel)(studentInformationTable.getModel())).getStudentInformation().getClasses().getFromIndex(i).iterator();
                
                while(it.hasNext())
                    it.remove();
            }
            if(newGradeIndex == 0)
                ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setStudentGrade(StudentGrade.FRESHMAN);
            if(newGradeIndex == 1)
                ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setStudentGrade(StudentGrade.SOPHOMORE);
            if(newGradeIndex == 2)
                ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setStudentGrade(StudentGrade.JUNIOR);
            ((StudentInformationTableModel)this.studentInformationTable.getModel()).setStudentInformation((StudentInfo2)this.studentList.getSelectedValue());
        }
        else
            this.gradeComboBox.setSelectedIndex(extraGradeLevel-9);
    }
    
    
    this.generateAddSemestersMenuItems();
    
}//GEN-LAST:event_gradeComboBoxChanged

private void mainTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainTabbedPaneStateChanged
    if(mainTabbedPane.getSelectedComponent() == gradePointAveragePanel)
    {
        ((GPATableModel)(gpaTable.getModel())).generateGPAs();
        gpaTable.repaint();
    }
}//GEN-LAST:event_mainTabbedPaneStateChanged

private void nameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameTextFieldKeyPressed
    //Debug.line("keyCode:"+evt.getKeyCode());
    //Should be enter
    if(evt.getKeyCode()== evt.VK_ENTER)
    {
        if(this.studentsInformation.contains(new StudentInfo2(this.nameTextField.getText())) == false)
        {
            
            StudentInfo2 temp = ((StudentInformationTableModel)(studentInformationTable.getModel())).
                getStudentInformation();
            if(temp.getName() != this.nameTextField.getText())
            {
                this.studentsInformation.remove(temp);
                temp.setName(this.nameTextField.getText());
                studentsInformation.add(temp);
                this.studentList.setSelectedValue(temp, true);
                this.studentList.repaint();
            }
                    
                
        }
        else
        {
            this.nameTextField.setText(((StudentInfo2)this.studentList.getSelectedValue()).getName());
        }
        this.nameTextField.transferFocus();
    }
    else if(evt.VK_UP == evt.getKeyCode())
        this.studentList.setSelectedIndex(this.studentList.getSelectedIndex()-1);
    else if(evt.VK_DOWN == evt.getKeyCode())//GEN-LAST:event_nameTextFieldKeyPressed
        this.studentList.setSelectedIndex(this.studentList.getSelectedIndex()+1);
    //Not needed
    //else if(evt.VK_A == evt.getKeyCode() && evt.isAltDown())
    //    this.nameTextField.selectAll();
}                                        

private void studentListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_studentListKeyPressed
    if(evt.VK_DELETE == evt.getKeyCode() && !this.studentList.isSelectionEmpty())
        this.removeStudentAction();
    else if(evt.isControlDown() && evt.getKeyCode() == evt.VK_A)
        this.addStudentAction();
}//GEN-LAST:event_studentListKeyPressed

private void studentInformationTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_studentInformationTableKeyPressed
    /*
    int selectedRowNumber = studentInformationTable.getSelectedRow();
    if(evt.getKeyCode() == evt.VK_CONTROL && selectedRowNumber != 0)
    { 
        javax.swing.table.TableColumn selectedColumn = 
                studentInformationTable.getColumnModel().getColumn
                (studentInformationTable.getSelectedColumn());
        
        //gpaTable.getColumnModel().getColumn(index)
        studentInformationTable.getColumnModel().getColumn(busyIconIndex)
    }
    */
    //studentInformationTable.
    int selectedCol = studentInformationTable.getSelectedColumn();    
    int selectedRow = studentInformationTable.getSelectedRow();
    if(evt.getKeyCode() == evt.VK_CONTROL && selectedRow != 0)
    { 
        String cellAbove = ((java.lang.StringBuilder)studentInformationTable.getValueAt(selectedRow-1, selectedCol)).toString();
        if(cellAbove.length() > 0)
        {
            studentInformationTable.setValueAt(cellAbove, selectedRow, selectedCol);
            //studentInformationTable.getModel().setValueAt(
            //       cellAbove, selectedRow, selectedCol);
        }
    }
    //Debug.line("ALMOST RAN F1 Code");
}//GEN-LAST:event_studentInformationTableKeyPressed

private void showSummersCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showSummersCheckBoxActionPerformed
    if(!showSummersCheckBox.isSelected())
        for(javax.swing.table.TableColumn col : summerCols) gpaTable.removeColumn(col);
    
    else
    {
        //gpaTable.addColumn(summerCols.get(0));
        //gpaTable.moveColumn(9, 3);
        
        int index = 0;
        for(javax.swing.table.TableColumn col : summerCols) 
        {
            //Debug.line("From: "+(13-(4-index))+", To: "+(3+(index*3)));
            gpaTable.addColumn(col);
            gpaTable.moveColumn(13-(4-index), 3+(index*3));
            index++;
        }
        
    }
         
}//GEN-LAST:event_showSummersCheckBoxActionPerformed

private void dateOfBirthTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfBirthTextFieldKeyPressed
    //DOBInputMARK
    if(evt.getKeyCode() == evt.VK_ENTER)
        dateOfBirthTextField.transferFocus();
}//GEN-LAST:event_dateOfBirthTextFieldKeyPressed

private void genderComboBoxChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderComboBoxChanged
    Gender newGender;
    if(genderComboBox.getSelectedIndex() == 0) newGender = Gender.MALE;
    else newGender = Gender.FEMALE;
    
    ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setGender(newGender);
}//GEN-LAST:event_genderComboBoxChanged

private void mathCahseeStatusComboBoxChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mathCahseeStatusComboBoxChanged
    CahseeStatus newStatus;
    if(mathCahseeStatusComboBox.getSelectedIndex() == 0) newStatus = CahseeStatus.PASSED;
    else if(mathCahseeStatusComboBox.getSelectedIndex() == 1) newStatus = CahseeStatus.NOT_TAKEN;
    else newStatus = CahseeStatus.NOT_PASSED;
    ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setMathCahseeStatus(newStatus);
}//GEN-LAST:event_mathCahseeStatusComboBoxChanged

private void englishCahseeStatusComboBoxChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_englishCahseeStatusComboBoxChanged
    CahseeStatus newStatus;
    if(englishCahseeStatusComboBox.getSelectedIndex() == 0) newStatus = CahseeStatus.PASSED;
    else if(englishCahseeStatusComboBox.getSelectedIndex() == 1) newStatus = CahseeStatus.NOT_TAKEN;
    else newStatus = CahseeStatus.NOT_PASSED;
    ((StudentInformationTableModel)(studentInformationTable.getModel())).
                        getStudentInformation().setEnglishCahseeStatus(newStatus);
}//GEN-LAST:event_englishCahseeStatusComboBoxChanged

private void dateOfBirthTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dateOfBirthTextFieldFocusGained
    dateOfBirthTextField.selectAll();
}//GEN-LAST:event_dateOfBirthTextFieldFocusGained

private void allProgressReportComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allProgressReportComboBoxActionPerformed
                                    
/*
javax.swing.JComboBox specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getCellEditor(rowIndex, columnIndex)).getComponent());
                    //javax.swing.JComboBox specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getCellEditor(0, 1)).getComponent());
                Debug.line("Col, Row: "+columnIndex+","+rowIndex);
                Debug.line("Item Name: "+specificProgressGradeCombo.getSelectedItem().toString());
                
                //if(rowIndex == 0) specificProgressGradeCombo.setEditable(true);
                //else specificProgressGradeCombo.setEditable(false);
*/
    /*
    javax.swing.JComboBox specificProgressGradeCombo;
    for(int row = 0; row < studentInformationTable.getRowCount(); row++)
    {
        specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)studentInformationTable.getCellEditor(row, 1)).getComponent());
        //specificProgressGradeCombo.setSelectedIndex(allProgressReportComboBox.getSelectedIndex());
        //studentInformationTable.repaint();
        
    }*/
    //((StudentInformationTableModel)studentInformationTable.getModel()).setAllProgressBoxes(allProgressReportComboBox.getSelectedIndex());
    
    
    //this.allProgressReportComboBox.setSelectedIndex(2);
    //progressGradeCombo.setSelectedIndex(2);
    
    //studentInformationTable.setModel(new StudentInformationTableModel(studentsInformation.first(),8,this));
    //javax.swing.table.TableColumnModel columnModel = studentInformationTable.getColumnModel();
    //    javax.swing.table.TableColumn firstColumn = columnModel.getColumn(1);
    //    firstColumn.setCellEditor(new javax.swing.DefaultCellEditor(progressGradeCombo));
    
}//GEN-LAST:event_allProgressReportComboBoxActionPerformed


private void addSemestersMenuItemActionPerformed(java.awt.event.ActionEvent evt, int grade, Semester semester)
{
    int lower = ((StudentInformationTableModel)this.studentInformationTable.getModel()).
            getRowLower(grade, semester);
    //Debug.line("In addSemestersActionPerformed lower value:"+lower);
    ((StudentInfo2)(this.studentList.getSelectedValue())).generateClassPlaceholder(grade, semester);
    ((StudentInformationTableModel)this.studentInformationTable.getModel()).insertRow(lower, grade, semester);


    this.generateAddSemestersMenuItems();
}
public void generateAddSemestersMenuItems() 
    {
        this.addSemestersMenu.removeAll();
        this.addSemestersPopupMenu.removeAll();
        javax.swing.JMenuItem item;
        //Wont work
        //boolean [][] onList = new boolean [gradeComboBox.getSelectedIndex()+1][3];
        boolean [][] onList = new boolean [((StudentInfo2)(this.studentList.getSelectedValue())).getClasses().getSize()][3];
        //Wont work
        //boolean [][] onList = new boolean [((StudentInfo2)(this.studentList.getSelectedValue())).getStudentGrade().ordinal()+1][3];
        
        //Debug.line("classes: "+((StudentInfo2)(this.studentList.getSelectedValue())).getClasses().getSize());
        //for(int i = 0; i < ((StudentInfo)(this.studentList.getSelectedValue())).getStudentGrade().ordinal(); i++)
        for(int i = 0; i < ((StudentInfo2)(this.studentList.getSelectedValue())).getClasses().getSize(); i++)
        {
            onList[i][0] = true;
            onList[i][1] = true;
            onList[i][2] = true;
        }
        
        for(int i = 0; i < ((StudentInformationTableModel)(this.studentInformationTable.getModel())).getRowCount(); i++)
        {
            
            String[] gradeAndSemester = ((String)((StudentInformationTableModel)
                    (this.studentInformationTable.getModel())).getValueAt(i, 0)).split("th Grade ");
            
            int grade = Integer.parseInt(gradeAndSemester[0]);
            Semester semester = Semester.valueOf(gradeAndSemester[1]);
            onList[grade-9][semester.ordinal()] = false;
        }
        
        for(int i = 0; i < onList.length; i++)
        {
            if(onList[i][0])
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Fall");
                item.setName((i+9)+"th Grade Fall");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.FALL);
                    }
                });
                this.addSemestersMenu.add(item);
                
                item = new javax.swing.JMenuItem((i+9)+"th Grade Fall");
                item.setName((i+9)+"th Grade Fall");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.FALL);
                    }
                });
                this.addSemestersPopupMenu.add(item);
            }
            
            if(onList[i][1])
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Spring");
                item.setName((i+9)+"th Grade Spring");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SPRING);
                    }
                });
                this.addSemestersMenu.add(item);
                
                item = new javax.swing.JMenuItem((i+9)+"th Grade Spring");
                item.setName((i+9)+"th Grade Spring");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SPRING);
                    }
                });
                this.addSemestersPopupMenu.add(item);

            }
            
            if(onList[i][2])
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Summer");
                item.setName((i+9)+"th Grade Summer");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SUMMER);
                    }
                });
                this.addSemestersMenu.add(item);
                
                item = new javax.swing.JMenuItem((i+9)+"th Grade Summer");
                item.setName((i+9)+"th Grade Summer");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SUMMER);
                    }
                });
                this.addSemestersPopupMenu.add(item);
            }
            
        }
        
        if(this.addSemestersMenu.getItemCount() == 0)
        {
            this.addSemestersMenu.setEnabled(false);
            this.addSemestersPopupMenu.setEnabled(false);
        }
        else
        {
            this.addSemestersMenu.setEnabled(true);
            this.addSemestersPopupMenu.setEnabled(true);
        }
        
        
        /*
        for(int i = 0; i < ((StudentInfo)(this.studentList.getSelectedValue())).getClasses().getSize(); i++)
        {
            if(!((StudentInfo)(this.studentList.getSelectedValue())).getClasses().
                    getFromIndex(i).presentDuring(Semester.FALL))
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Fall");
                item.setName((i+9)+"th Grade Fall");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.FALL);
                    }
                });
                this.addSemesterMenu.add(item);
            }
            
            if(!((StudentInfo)(this.studentList.getSelectedValue())).getClasses().
                    getFromIndex(i).presentDuring(Semester.SPRING))
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Spring");
                item.setName((i+9)+"th Grade Spring");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SPRING);
                    }
                });
                this.addSemesterMenu.add(item);
            }
            
            if(!((StudentInfo)(this.studentList.getSelectedValue())).getClasses().
                    getFromIndex(i).presentDuring(Semester.SUMMER))
            {
                final int temp = i;
                item = new javax.swing.JMenuItem((i+9)+"th Grade Summer");
                item.setName((i+9)+"th Grade Summer");
                item.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addSemestersMenuItemActionPerformed(evt, temp+9, Semester.SUMMER);
                    }
                });
                this.addSemesterMenu.add(item);
            }
            
            
        }*/
        //((StudentInfo)(this.studentList.getSelectedValue())).getClasses()
         
    }
/*
    @Action
    public void showAddStudentDialog() {
        if (addStudentDialog == null) {
            JFrame mainFrame = StudentBrowserApp.getApplication().getMainFrame();
            addStudentDialog = new StudentBrowserAddStudentDialog(mainFrame);
            addStudentDialog.setLocationRelativeTo(mainFrame);
        }
        StudentBrowserApp.getApplication().show(addStudentDialog);
    }
*/
    public JTable getStudentInformationTable() {
        return studentInformationTable;
    }

    @Action
    public void addStudentAction() {
        /*
        //ClassArrayLists classes, String name, int age, StudentGrade grade)
        ClassArrayLists classes = new ClassArrayLists(1);
        ClassList classList = new ClassList();
        classList.add(new ClassInfo(Semester.FALL,"PLACEHOLDER",ClassType.OTHER,new Grade(0.0F)));
        classes.setGradeList(9, classList);
        javax.swing.text.PlainDocument note = new javax.swing.text.PlainDocument();
            try
            {
                note.insertString(0, "new student note", new javax.swing.text.SimpleAttributeSet());
            }
            catch(javax.swing.text.BadLocationException e)
            {
                Debug.line("ERROR: Couldn't create notes, Bad Location(offset).");
                Debug.line(e.getMessage());
                
            }
        StudentInfo newStudent = new StudentInfo(classes,"ZZNew Name",14,StudentGrade.FRESHMAN, note);
        //newStudent.generateClassPlaceholder(9, Semester.FALL);
        studentsInformation.add(newStudent);
        StudentListModel newModel = new StudentListModel(studentsInformation);
        
        //((StudentListModel)(this.studentList.getModel())).
        this.studentList.setModel(newModel);
        
        Debug.line("add student selected name:"+
                ((StudentInfo)(this.studentList.getSelectedValue())).getName());
        */
        //this.studentListingPanel.grabFocus();
        ClassArrayLists classes = new ClassArrayLists(1);
        ClassList classList = new ClassList();
        classList.add(new ClassInfo(Semester.FALL,"PLACEHOLDER",ClassType.PLACEHOLDER,new Grade(0.0F)));
        classes.setGradeList(9, classList);
        
        StudentInfo2 newStudent = new StudentInfo2(classes,"New Name "+this.newStudentNamesAdded,new java.util.GregorianCalendar(1990, 0, 1),StudentGrade.FRESHMAN);
        this.newStudentNamesAdded++;
        //newStudent.generateClassPlaceholder(9, Semester.FALL);
        StudentInfo2 position = (StudentInfo2)this.studentList.getSelectedValue();
        studentsInformation.add(newStudent);
        
        this.studentList.setModel(new StudentListModel(studentsInformation));
        this.gpaTable.setModel(new GPATableModel(studentsInformation));
        //summercolNote
        showSummersCheckBox.setSelected(true);
        saveSummerColumns();
        this.studentList.setSelectedValue(position, true);
        //Make sure the removeStudent menuitems are enabled
        this.removeStudentMenuItem.setEnabled(true);
        this.removeStudentPopupMenuItem.setEnabled(true);
        
        //this.studentLis
        
        //this.studentList.repaint();
        //this.studentList.setSelectedValue(newStudent, true);
    }

    @Action
    public void removeStudentAction() 
    {
        //Blank List is Ok but the information(i.e. name,age fields) won't change
        //if(this.studentList.getLastVisibleIndex() > 0)
        if(studentsInformation.size() != 0)
        {
            int movement = this.studentList.getSelectedIndex();
            StudentInfo2 deleatedInfo = (StudentInfo2)this.studentList.getSelectedValue();
            
            
            //If the last index is selected go one down
            if(this.studentList.getSelectedIndex() == ((StudentListModel)this.studentList.getModel()).getSize()-1)
                movement =((StudentListModel)this.studentList.getModel()).getSize()-2;
                //this.studentList.setSelectedIndex(this.studentList.getSelectedIndex()-1);
            
            
            this.studentsInformation.remove(deleatedInfo);
            this.gpaTable.setModel(new GPATableModel(studentsInformation));
            this.studentList.setSelectedIndex(movement);
            this.studentList.repaint();
        }
    }

    @Action
    public void openStudentsInfoFileAction() 
    {
        javax.swing.JFileChooser openFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        javax.swing.filechooser.FileNameExtensionFilter objectFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("Object Data", "obj");
        openFileDialog.setFileFilter(objectFilter);
        int dialogButtonOption = openFileDialog.showOpenDialog(null);
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File openFile = openFileDialog.getSelectedFile();
            //saveFile.createNewFile()
          
            if(openFile.canRead())
            {
                try
                {
                    java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                            new java.io.FileInputStream(openFile));
                    //java.util.NavigableSet<StudentInfo> newStudentsInfo = (java.util.TreeSet<StudentInfo>)in.readObject();
                    
                    
                    //Object reading
                    java.util.NavigableSet<Object> newStudentsTemp = (java.util.TreeSet<Object>)in.readObject();
                    java.util.NavigableSet<StudentInfo2> newStudentsInfo = new java.util.TreeSet<StudentInfo2>();
                    if(newStudentsTemp.first().getClass() == StudentInfo.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            newStudentsInfo.add(new StudentInfo2((StudentInfo)o));
                        }
                            
                    }
                    else if(newStudentsTemp.first().getClass() == StudentInfo2.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            newStudentsInfo.add((StudentInfo2)o);
                        }
                    }
                    
                    in.close();
                    this.currentFile = openFile;
                    //this.studentInformationTable.setModel(new StudentInformationTableModel(newStudentsInfo.first(),7,this));

                    //The fireTableStructureChanged method didnt work to reset the Table so this is needed
                    this.gpaTable.setModel(new GPATableModel(newStudentsInfo));
                    this.showSummersCheckBox.setSelected(true);
                    this.saveSummerColumns();
                    
                    this.nameTextField.setText(newStudentsInfo.first().getName());
                    //DOBInputMARK
                    dateOfBirthTextField.setText(newStudentsInfo.first().getStringDateOfBirth());
                    studentsInformation = newStudentsInfo;
    
                    ((StudentInformationTableModel)this.studentInformationTable.getModel()).fireTableStructureChanged();
                    this.studentList.setModel(new StudentListModel(studentsInformation));
                    
                }
                catch(Exception e)
                {
                    Debug.line("ERROR: inside openStudentsInfoFileAction.");
                    e.printStackTrace();
                }
            }
        }
    }


    @Action
    public void saveStudentsInfoFileAction() 
    {   try
        {
            //Debug.line("PATH of currentFile:"+currentFile.toString());
            java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream(currentFile));
            out.writeObject(studentsInformation);
            out.close();
        }
        catch(Exception e)
        {
            Debug.line("ERROR: inside saveStudentsInfoFileAction.");
            e.printStackTrace();
        }
        
    }

    @Action
    public void saveAsStudentsInfoFileAction() 
    {
        javax.swing.JFileChooser saveFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        javax.swing.filechooser.FileNameExtensionFilter objectFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("Object Data", "obj");
        saveFileDialog.setFileFilter(objectFilter);
        int dialogButtonOption = saveFileDialog.showSaveDialog(null);
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File saveFile = saveFileDialog.getSelectedFile();
            //saveFile.createNewFile()
            if(!saveFile.getName().contains(".obj"))
                saveFile = new java.io.File(saveFile.getAbsolutePath()+".obj");
            try
            {
                java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                        new java.io.FileOutputStream(saveFile));
                out.writeObject(studentsInformation);
                out.close();
                this.currentFile = saveFile;
                //Debug.line("PATH of saveFile:"+saveFile.toString());
                //Debug.line("PATH of currentFile:"+currentFile.toString());
            }
            catch(Exception e)
            {
                Debug.line("ERROR: inside saveAsStudentsInfoFileAction.");
                e.printStackTrace();
            }
        }
    }

    @Action
    public void createNewStudentsInfoFileAction() 
    {
        java.util.NavigableSet<StudentInfo2> newStudentsInfo = new java.util.TreeSet<StudentInfo2>();
        //this.studentInformationTable.setModel(new StudentInformationTableModel(newStudentsInfo.first(),7,this));
        this.gpaTable.setModel(new GPATableModel(newStudentsInfo));
        //this.nameTextField.setText(newStudentsInfo.first().getName());
        //this.ageTextField.setText(Integer.toString(newStudentsInfo.first().getAge()));
        studentsInformation = newStudentsInfo;
        this.studentList.setModel(new StudentListModel(studentsInformation));
        
        //this.studentsInformation = new TreeSet<StudentInfo>();
    }

    @Action
    public void createStudentsTablesFileAction() 
    {
        javax.swing.JFileChooser generateStudentsTablesFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        generateStudentsTablesFileDialog.setName("Generate Students Tables HTML File Dialog");
        generateStudentsTablesFileDialog.setDialogTitle("Generate HTML File");
        //generateGPAsFileDialog.setApproveButtonText("Generate");
        javax.swing.filechooser.FileNameExtensionFilter htmlFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("HTML File", "html", "htm");
        generateStudentsTablesFileDialog.setFileFilter(htmlFilter);
        
        int dialogButtonOption = generateStudentsTablesFileDialog.showDialog(null, "Generate");
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File tableFile = generateStudentsTablesFileDialog.getSelectedFile();
            //saveFile.createNewFile()
            java.util.regex.Pattern htmlFilePattern = java.util.regex.Pattern.compile("[\\x00-\\x7F]+(.html|.htm)");
            java.util.regex.Matcher htmlFileMatcher = htmlFilePattern.matcher(tableFile.getName());
            if(!htmlFileMatcher.matches())
                tableFile = new java.io.File(tableFile.getAbsolutePath()+".htm");
                
            
            
            try
            {
                tableFile.createNewFile();
                java.io.PrintWriter out = new java.io.PrintWriter(
                        new java.io.FileWriter(tableFile));
                
                
                for(StudentInfo2 s : studentsInformation)
                {
                    out.println("<TABLE BORDER=2>");
                    out.println("<TR>");
                    out.println("<TH>"+s.getName()+"</TH>");
                    out.println("<TH>English</TH>");
                    out.println("<TH>Math</TH>");
                    out.println("<TH>Science</TH>");
                    out.println("<TH>History</TH>");
                    out.println("<TH>Foreign Language</TH>");
                    out.println("<TH>OTHER</TH>");
                    
                    for(int i = 0; i < s.getClasses().getSize(); i++)
                    {
                        if(s.getClasses().getFromIndex(i).presentDuring(Semester.FALL))
                        {
                            out.println("<TR> <TH>"+(i+9)+"th Grade Fall</TH>");
                            StringBuilder cell = new StringBuilder("<TD>");
                            
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.ENGLISH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                           
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.MATH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 0; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+(p+1)+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)                                 
                                cell.append("&nbsp;");                             
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.SCIENCE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.HISTORY))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.FOREIGN_LANGUAGE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.FALL, ClassType.OTHER))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                           
                        }
                        
                        if(s.getClasses().getFromIndex(i).presentDuring(Semester.SPRING))
                        {
                            out.println("<TR> <TH>"+(i+9)+"th Grade Spring</TH>");
                            StringBuilder cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.ENGLISH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                           
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.MATH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.SCIENCE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.HISTORY))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.FOREIGN_LANGUAGE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SPRING, ClassType.OTHER))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                        }
                        
                        if(s.getClasses().getFromIndex(i).presentDuring(Semester.SUMMER))
                        {
                            out.println("<TR> <TH>"+(i+9)+"th Grade Summer</TH>");
                            StringBuilder cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.ENGLISH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                           
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.MATH))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.SCIENCE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.HISTORY))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.FOREIGN_LANGUAGE))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                            
                            cell = new StringBuilder("<TD>");
                            for(ClassInfo ci : s.getClasses().getFromIndex(i).getAll(Semester.SUMMER, ClassType.OTHER))
                            {
                                cell.append(ci.getTitle()+"(F=["+ci.getGrade().letterValue()+"]");
                                int progressReportsLength = ci.getProgressGradeLength();
                                if(progressReportsLength > 0 && printProgressReports)
                                {
                                    
                                    for(int p = 1; p < progressReportsLength; p++)
                                    {
                                        cell.append(" ,"+p+"P=["+ci.getProgressGrade(p).letterValue()+"]");
                                    }
                                        
                                            
                                }
                                cell.append(") ");
                            }
                            if(cell.length() == 4)
                                cell.append("&nbsp;");
                            out.println(cell+"</TD>");
                        }
                    }

                    out.println("</TABLE>");
                }
                
                out.close();
            }
            catch(Exception e)
            {
                Debug.line("ERROR: inside generateGPAsTableFileAction.");
                e.printStackTrace();
            }
            
        }
    }

    @Action
    public void createGPAsTableFileAction() 
    {
        javax.swing.JFileChooser generateGPAsFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        generateGPAsFileDialog.setName("Generate GPAs Table HTML File Dialog");
        generateGPAsFileDialog.setDialogTitle("Generate HTML File");
        //generateGPAsFileDialog.setApproveButtonText("Generate");
        
        javax.swing.filechooser.FileNameExtensionFilter htmlFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("HTML File", "html", "htm");
        generateGPAsFileDialog.setFileFilter(htmlFilter);
        
        int dialogButtonOption = generateGPAsFileDialog.showDialog(null, "Generate");
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            java.io.File tableFile = generateGPAsFileDialog.getSelectedFile();
            //saveFile.createNewFile()
            java.util.regex.Pattern htmlFilePattern = java.util.regex.Pattern.compile("[\\x00-\\x7F]+(.html|.htm)");
            java.util.regex.Matcher htmlFileMatcher = htmlFilePattern.matcher(tableFile.getName());
            if(!htmlFileMatcher.matches())
                tableFile = new java.io.File(tableFile.getAbsolutePath()+".htm");
                
            
            
            try
            {
                tableFile.createNewFile();
                java.io.PrintWriter out = new java.io.PrintWriter(
                        new java.io.FileWriter(tableFile));
                
                out.println("<TABLE BORDER=2>");
                for(int c = 0; c < this.gpaTable.getColumnCount(); c++)
                    out.println("<TH>"+this.gpaTable.getColumnName(c)+"</TH>");
                out.println("</TR>");
                for(int r = 0; r < this.gpaTable.getRowCount(); r++)
                {
                    out.println("<TR> <TH>"+this.gpaTable.getValueAt(r, 0)+"</TH>");
                    for(int c = 1; c < this.gpaTable.getColumnCount(); c++)
                    {
                        out.println("<TD>"+this.gpaTable.getValueAt(r, c)+"</TD>");
                    }
                }
                out.println("</TABLE>");
                
                
                out.close();
            }
            catch(Exception e)
            {
                Debug.line("ERROR: inside generateGPAsTableFileAction.");
                e.printStackTrace();
            }
            
        }
    }

    @Action
    public void loadStudentNames() 
    {
        javax.swing.JFileChooser studentListFileDialog = new javax.swing.JFileChooser(System.getProperty("user.home"));
        studentListFileDialog.setName("Import Student Names File Dialog");
        studentListFileDialog.setDialogTitle("Load List Of Student Names From Text File");
        //generateGPAsFileDialog.setApproveButtonText("Generate");
        
        javax.swing.filechooser.FileNameExtensionFilter txtFilter = 
                new javax.swing.filechooser.FileNameExtensionFilter("TXT File", "txt");
        studentListFileDialog.setFileFilter(txtFilter);
        
        int dialogButtonOption = studentListFileDialog.showDialog(null, "Load");
        if(dialogButtonOption == javax.swing.JFileChooser.APPROVE_OPTION)
        {
        
            /*
            java.io.File openFile = openFileDialog.getSelectedFile();
            //saveFile.createNewFile()
            
            if(openFile.canRead())
            {
                try
                {
                    java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                            new java.io.FileInputStream(openFile));
                    java.util.NavigableSet<StudentInfo> newStudentsInfo = (java.util.TreeSet<StudentInfo>)in.readObject();
                    in.close();
                    this.studentInformationTable.setModel(new StudentInformationTableModel(newStudentsInfo.first(),7,this));
                    this.gpaTable.setModel(new GPATableModel(newStudentsInfo));
                    this.nameTextField.setText(newStudentsInfo.first().getName());
                    this.ageTextField.setText(Integer.toString(newStudentsInfo.first().getAge()));
                    studentsInformation = newStudentsInfo;
                    this.studentList.setModel(new StudentListModel(studentsInformation));
                    
                }
                catch(Exception e)
                {
                    Debug.line("ERROR: inside openStudentsInfoFileAction.");
                    e.printStackTrace();
                }
            }
             */
             
            java.io.File studentNamesFile = studentListFileDialog.getSelectedFile();
            
            if(studentNamesFile.canRead())
            {

            }
                try
                {
                    java.io.BufferedReader in = new java.io.BufferedReader
                            (new java.io.FileReader(studentNamesFile));

                    String name;
                    
                    while ((name = in.readLine()) != null)
                    {
                        ClassArrayLists classes = new ClassArrayLists(1);
                        ClassList classList = new ClassList();
                        classList.add(new ClassInfo(Semester.FALL,"PLACEHOLDER",ClassType.PLACEHOLDER,new Grade(0.0F)));
                        classes.setGradeList(9, classList);
                        
                        this.studentsInformation.add(new StudentInfo2(classes,name,new java.util.GregorianCalendar(1990, 0, 1),StudentGrade.FRESHMAN));
                    }
                    in.close();
                    
                    //Reset the Models
                    this.studentList.setModel(new StudentListModel(studentsInformation));
                    this.gpaTable.setModel(new GPATableModel(studentsInformation));
                    
                    //This code resets the selected student
                    /*
                    this.studentInformationTable.setModel(new StudentInformationTableModel(newStudentsInfo.first(),7,this));
                    this.gpaTable.setModel(new GPATableModel(newStudentsInfo));
                    this.nameTextField.setText(newStudentsInfo.first().getName());
                    this.ageTextField.setText(Integer.toString(newStudentsInfo.first().getAge()));
                    studentsInformation = newStudentsInfo;
                    this.studentList.setModel(new StudentListModel(studentsInformation));
                    */
                    
                }
                catch(Exception e)
                {
                    Debug.line("ERROR: inside loadStudentNamesAction.");
                    e.printStackTrace();
                }
            }

    }

    @Action
    public void showStudentStatisticsDialog() 
    {
        JFrame mainFrame = StudentBrowserApp.getApplication().getMainFrame();
       
        //sends selected student
        //studentStatisticsBox = new StudentBrowserStudentStatisticsDialog(mainFrame, (StudentInfo)this.studentList.getSelectedValue());
        /*
        studentStatisticsBox = new StudentBrowserStudentStatisticsDialog(
            StudentBrowserApp.getApplication().getMainFrame(), this.studentsInformation);
        */
        //sends the whole list
        studentStatisticsBox = new StudentBrowserStudentStatisticsDialog(mainFrame, this.studentsInformation);

        studentStatisticsBox.setLocationRelativeTo(mainFrame);
        StudentBrowserApp.getApplication().show(studentStatisticsBox);

        //This code saves the old StatBox
        /*
        if (studentStatisticsBox == null) {
           JFrame mainFrame = StudentBrowserApp.getApplication().getMainFrame();

           //sends selected student
           //studentStatisticsBox = new StudentBrowserStudentStatisticsDialog(mainFrame, (StudentInfo)this.studentList.getSelectedValue());

           //sends the whole list
           studentStatisticsBox = new StudentBrowserStudentStatisticsDialog(mainFrame, this.studentsInformation);

           studentStatisticsBox.setLocationRelativeTo(mainFrame);
        }
        StudentBrowserApp.getApplication().show(studentStatisticsBox);
        */
    }

    @Action
    public void createYearlyGPAsTableFileAction() 
    {
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    //private javax.swing.JLabel ProgressReportLabel;
    private javax.swing.JMenu addSemestersMenu;
    private javax.swing.JMenu addSemestersPopupMenu;
    private javax.swing.JMenuItem addStudentMenuItem;
    private javax.swing.JMenuItem addStudentPopupMenuItem;
    //private javax.swing.JComboBox allProgressReportComboBox;
    private javax.swing.JLabel averageGPALabel;
    private javax.swing.JLabel averageWeighedGPALabel;
    private javax.swing.JMenuItem createGPAsTableMenuItem;
    private javax.swing.JMenuItem createStudentsTablesMenuItem;
    private javax.swing.JMenuItem createYearlyGPAsTableMenuItem;
    private javax.swing.JLabel dateOfBirthLabel;
    private javax.swing.JTextField dateOfBirthTextField;
    private javax.swing.JMenu editMenu;
    private javax.swing.JComboBox englishCahseeStatusComboBox;
    private javax.swing.JLabel englishCahseeStatusLabel;
    private javax.swing.JComboBox genderComboBox;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JTable gpaTable;
    private javax.swing.JScrollPane gpaTableScrollPane;
    private javax.swing.JComboBox gradeComboBox;
    private javax.swing.JLabel gradeLabel;
    private javax.swing.JPanel gradePointAveragePanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JMenuItem loadStudentNamesMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JComboBox mathCahseeStatusComboBox;
    private javax.swing.JLabel mathCahseeStatusLabel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JPanel notesPanel;
    private javax.swing.JScrollPane notesScrollPane;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem removeStudentMenuItem;
    private javax.swing.JMenuItem removeStudentPopupMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JCheckBox showSummersCheckBox;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JPanel studentInformationPanel;
    private javax.swing.JTable studentInformationTable;
    private javax.swing.JPopupMenu studentInformationTablePopupMenu;
    private javax.swing.JScrollPane studentInformationTableScrollPane;
    private javax.swing.JList studentList;
    private javax.swing.JPopupMenu studentListPopupMenu;
    private javax.swing.JScrollPane studentListScrollPane;
    private javax.swing.JPanel studentListingPanel;
    private javax.swing.JMenuItem studentStatisticsMenuItem;
    private javax.swing.JMenu tableMenu;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private JDialog studentStatisticsBox;
    //private JDialog addStudentDialog;
    private javax.swing.table.TableCellEditor defaultCellEditor;
    private NavigableSet<StudentInfo2> studentsInformation;
    private java.io.File currentFile;
    private int newStudentNamesAdded;
    private javax.swing.JComboBox progressGradeCombo;
    private java.util.ArrayList<javax.swing.table.TableColumn> summerCols = 
            new java.util.ArrayList<javax.swing.table.TableColumn>();
    private boolean printProgressReports = true;
    
    
    
    //TableModel Code
    //new StudentInformationTableModel(studentsInformation.first(),7)
    
}

