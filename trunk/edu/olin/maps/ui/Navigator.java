/*
 * Navigator.java
 *
 * Created on November 29, 2007, 2:30 PM
 */

package edu.olin.maps.ui;

import javax.swing.UIManager;

/**
 *
 * @author  tmichon
 */
public class Navigator extends javax.swing.JFrame {
	
	/** Creates new form Navigator */
	public Navigator() {
		initComponents();
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        blueprint1 = new edu.olin.maps.ui.Blueprint();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getContentPane().add(blueprint1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.Toolkit.getDefaultToolkit().setDynamicLayout(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			return;
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Navigator().setVisible(true);
			}
		});
	}
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private edu.olin.maps.ui.Blueprint blueprint1;
    // End of variables declaration//GEN-END:variables
	
}