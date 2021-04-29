
import DB.dbSQLite;
import LDA.ImportData;
import LDA.LDA;
import Ontology.OntoBuild;
import Ontology.OntoSearch;
import Ultilities.CSV;
import Ultilities.csvread;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;
import extract.BibText;
import extract.Extractor;
import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;
import org.apache.commons.io.FileUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class GUI extends javax.swing.JFrame  {

    JButton go;
    JFileChooser chooser;
    String choosertitle;

    public GUI() {
        initComponents();
        myInitComponents();

    }

    /*public void setTableModel() {
        jTable1.getModel().setValueAt(2, 4, 300);
    }*/

    private void myInitComponents() {

        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    tableDocumentMouseClicked(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        JFrame frame =new JFrame();
        
        
  
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setText("Step 1: \nSelect Input Directory");
        
    
          

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DocClass Interface");
        setBackground(new java.awt.Color(34, 37, 44));
        setMinimumSize(new java.awt.Dimension(610, 610));
        setPreferredSize(new java.awt.Dimension(1390, 660));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(23, 35, 51));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 702));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("DocClass");

        jScrollPane7.setBorder(null);

        jTextArea1.setBackground(new java.awt.Color(23, 35, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setToolTipText("Provide instruction for the application");
        jTextArea1.setBorder(null);
        jScrollPane7.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel1)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 610));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(23, 35, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(23, 35, 51));
        jButton1.setText("Browse file");
        jButton1.setToolTipText("Select the input directory where your pdf files are located.");
        jButton1.setBorder(null);
        jButton1.setMaximumSize(new java.awt.Dimension(81, 25));
        jButton1.setMinimumSize(new java.awt.Dimension(81, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(81, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 37, 120, 30));

        jButton2.setBackground(new java.awt.Color(23, 35, 51));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(23, 35, 51));
        jButton2.setText("Classify");
        jButton2.setToolTipText("Classify pdf files in the selected directory into topics.");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 97, 121, 30));

        SearchField.setToolTipText("Search bar");
        SearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFieldActionPerformed(evt);
            }
        });
        jPanel2.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 321, 856, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(23, 35, 51));
        jLabel2.setText("Search");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 323, -1, -1));

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(400, 432));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "title", "authors", "score"
            }
        ));
        jTable3.setToolTipText("Display search result");
        jScrollPane5.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(200);
            jTable3.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTable3.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        jTabbedPane2.addTab("Search Result", jScrollPane5);

        jPanel2.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 1140, 220));

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(400, 434));

        jScrollPane1.setToolTipText("Display discovered topic");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Topic", "keywords"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(25);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        jTabbedPane1.addTab("Topics", jScrollPane2);

        jScrollPane6.setToolTipText("Display authors with the most papers for each topics.");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Topic", "authors"
            }
        ));
        jScrollPane6.setViewportView(jTable5);

        jTabbedPane1.addTab("Best Authors", jScrollPane6);

        jScrollPane3.setToolTipText("Display Topics distribution per Documents.");
        jScrollPane3.setPreferredSize(new java.awt.Dimension(150, 0));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "filename", "topic1", "distribution1", "topic2", "distribution2"
            }
        ));
        jTable2.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(1200);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(250);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(900);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(250);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(900);
        }

        jTabbedPane1.addTab("Document_Topic", jScrollPane3);

        jPanel2.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 710, 250));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 210, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1140, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            int result;
            //SwingWorker sw1 = new SwingWorker() 

            BibText bib = new BibText();
            ImportData imp = new ImportData();
            Extractor ex = new Extractor();

            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle(choosertitle);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                jTextArea1.setText("Extracting data from pdf files...\n\n"
                        + "This process can be very time consuming\n"
                        + "Please be patient");
                System.out.println("getCurrentDirectory(): "
                        + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : "
                        + chooser.getSelectedFile());

                /*  try {
                pdf.PDFtoText(chooser.getSelectedFile());
                abs.ex_abstract(chooser.getSelectedFile());
                imp.ImportData(chooser.getSelectedFile());
                
                } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                File dir = chooser.getSelectedFile();
                jTextField1.setText(dir.toString());

                SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        bib.Grobidsetup(dir.getAbsolutePath(), null, true);
                        ex.Rename(dir);
                        ex.AbstractFile(dir);
                        // ex.Document(dir);
                        imp.ImportData(dir);
                        return null;
                    }
                };

                /*   bib.Grobidsetup(dir.getAbsolutePath(), null, true);
                try {
                    ex.Rename(dir);
                    ex.AbstractFile(dir);
                    // ex.Document(dir);
                    imp.ImportData(dir);
                } catch (IOException ex1) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex1);
                }*/
                mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("state")) {
                            if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                                jTextArea1.setText("Extracting done \n\nStep 2: \nClick Classify");
                            }
                        }

                    }
                });

                mySwingWorker.execute();

            } else {
                System.out.println("No Selection ");
                jTextArea1.setText("Step 1: Select Input Directory");
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed


    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        jTextArea1.setText("Classifying..");

        LDA lda = new LDA(50);
        CSV csv1 = new CSV();
        OntoBuild build = new OntoBuild();
        OntoSearch build2 = new OntoSearch();
        dbSQLite db = new dbSQLite();

        SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                lda.runLDA(0, 0);

                //convert txt file to csv file
                csv1.CSVconvert("Document_Topic.mallet.txt", "Document_Topic");
                csv1.CSVconvert("Topic_Keyword.mallet.txt", "Topic_Keyword");

                //Create database
                db.createNewDatabase();
                db.DeleteDocument();
                db.createTableDoc();
                db.insertDocumentDir(chooser.getSelectedFile().getAbsolutePath());

                db.DeleteTopics();
                db.createTableTopic();
                db.insertTopicCSV(10, 5);

                db.DeleteDocTopic();
                db.createTableDocTopic();
                db.insertDocTopicCSV(5);
                dbSQLite.createTableSearchResult();

                //Build ontology
                build.Build();

                //Find best authors
                build2.BestAuthor();

                //Display on UI
                displayTopics();
                displayDocTopic();
                displayBesAuthor();

                File dir = new File(chooser.getCurrentDirectory() + "\\bib");
                FileUtils.deleteDirectory(dir);

                return null;
            }
        };


        /*   // Start topic modeling with LDA
        try {
            lda.runLDA(0, 0);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Convert file to csv format
        try {
            csv1.CSVconvert("Document_Topic.mallet.txt", "Document_Topic");
            csv1.CSVconvert("Topic_Keyword.mallet.txt", "Topic_Keyword");
            // csv1.CSVconvert("Document.txt", "Document");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Create table Document
      
        try {
            db.createNewDatabase();
            db.DeleteDocument();
            db.createTableDoc();
            db.insertDocumentDir(chooser.getSelectedFile().getAbsolutePath());
            //db.insertDocumentDir("D:\\Document\\thesis\\paper\\format\\springer");
        } catch (ClassNotFoundException | IOException ex1) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            db.DeleteTopics();
            db.createTableTopic();
            db.insertTopicCSV(10, 5);

            db.DeleteDocTopic();
            db.createTableDocTopic();
            db.insertDocTopicCSV(5);
            dbSQLite.createTableSearchResult();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Build ontology
        try {
            build.Build();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


        //Best Author        
        try {
            build2.BestAuthor();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        try {
            displayTopics();
            displayDocTopic();
            //   displayDocDB();
            displayBesAuthor();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        File dir = new File(chooser.getCurrentDirectory() + "\\bib");

        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        jTextArea1.setText("Display Topics\n"
                                + "Display Document Topic distribution\n"
                                + "Display best authors for each topic\n"
                                + "Classified finished\n"
                                + "Ready for searching !!!");
                    }
                }

            }
        });

        mySwingWorker.execute();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void SearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFieldActionPerformed
        // TODO add your handling code here:

        String SearchStr = SearchField.getText();

        DefaultTableModel tmodel3;
        tmodel3 = (DefaultTableModel) jTable3.getModel();
        tmodel3.getDataVector().removeAllElements();
        tmodel3.fireTableDataChanged();
        jTextArea1.setText("Searching...");

        System.out.println("Entered: " + SearchStr + "!");
        Search search = new Search();

        search.execute();
        /*   OntoSearch Osearch = new OntoSearch();

        HashMap<String, Integer> SearchResult = new HashMap<String, Integer>();


        try {
            SearchResult = Osearch.Search(SearchStr);
            System.out.println("Searching done");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (OWLOntologyStorageException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

       
        try {
            getSearchresult();
            jTable3.getColumnModel().getColumn(3).setWidth(0);
            jTable3.getColumnModel().getColumn(3).setMinWidth(0);
            jTable3.getColumnModel().getColumn(3).setMaxWidth(0);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTextArea1.setText("Display search result\n"
                + "Click on the result to open the pdf file"); */

    }//GEN-LAST:event_SearchFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);

            }

        });

    }

    public class Search extends SwingWorker<Void, String[]> {

        String SearchStr = SearchField.getText();
        HashMap<String, Integer> SearchResult = new HashMap<String, Integer>();

        @Override
        protected Void doInBackground() throws Exception {
            OntoSearch Osearch = new OntoSearch();
            SearchResult = Osearch.Search(SearchStr);
            System.out.println("Searching done");

            getSearchresult();
            jTable3.getColumnModel().getColumn(3).setWidth(0);
            jTable3.getColumnModel().getColumn(3).setMinWidth(0);
            jTable3.getColumnModel().getColumn(3).setMaxWidth(0);
            return null;
        }

        public void done() {

            try {
                int count = getMaxID();
                jTextArea1.setText("Display " + count + " result"
                        + "\n\nClick on the result to\nopen the pdf file");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void getSearchresult() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        String sql = "SELECT SearchResult.title, Document.authors, SearchResult.score, Document.path\n"
                + "FROM Document \n"
                + "INNER JOIN SearchResult ON Document.title = SearchResult.title\n"
                + "ORDER BY SearchResult.score DESC,\n"
                + "         Document.title ASC  ";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(jTable3);
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int getMaxID() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        int count = 0;

        String sql = "SELECT *\n"
                + "FROM SearchResult \n"
                + "WHERE doc_id=(SELECT MAX(doc_id) FROM SearchResult )\n";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            count = rs.getInt("doc_id");
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return count;

    }

    public void displayTopics() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        String sql = "SELECT Topic,keywords FROM Topics ORDER BY Topic ";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(jTable1);

            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayDocTopic() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        String sql = "SELECT filename,topic1,distribution1,topic2,distribution2 FROM DocTopic  ";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(jTable2);

            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayBesAuthor() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        String sql = "SELECT Topic,authors FROM BestAuthor ORDER BY Topic ";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            jTable5.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(jTable5);
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /*    public void displayDocDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        String sql = "SELECT doc_id,title,authors FROM Document  ";
        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            jTable4.setModel(DbUtils.resultSetToTableModel(rs));
             resizeColumnWidth(jTable4);
            // jTable3.getColumnModel().getColumn(0).setPreferredWidth(200);
            //  jTable3.getColumnModel().getColumn(1).setPreferredWidth(40);
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }*/
    public void getSearchresult2(HashMap<String, Integer> SearchResult) throws ClassNotFoundException, SQLException {
        String[] columnsName = {"title", "authors"};

        String[] a = SearchResult.keySet().toArray(new String[0]);

        String sql = "SELECT title, authors FROM Document WHERE title = '" + a[0] + "'";

        Class.forName("org.sqlite.JDBC");

        for (int i = 0; i < a.length; i++) {

            if (i != 0) {
                sql = sql + "or title = '" + a[i] + "'";
                System.out.println(sql);
            }
        }

        String url = "jdbc:sqlite:doc.db";
        try (
                Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
            resizeColumnWidth(jTable3);
            

            rs.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void tableDocumentMouseClicked(java.awt.event.MouseEvent evt) throws SQLException {
        // TODO add your handling code here

        String url = "jdbc:sqlite:doc.db";
        Connection conn = DriverManager.getConnection(url);

        int row = jTable3.getSelectedRow();
        // String tableClick = (myTable.getModel().getValueAt(row, 0).toString());

        String value = (jTable3.getModel().getValueAt(row, 3).toString());
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + value);
        } catch (Exception e) {
            // JOptionPane.showMessageDialog(rootPane, "Error");
            System.out.println(e);
        }
    }
    
    

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 55; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 500) {
                width = 500;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
            //table.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
            
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField SearchField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
