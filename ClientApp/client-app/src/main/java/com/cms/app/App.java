package src.main.java.com.cms.app;

import java.awt.EventQueue;

import src.main.java.com.cms.app.views.MainFrame;


public class App 
{
	/**
	 * Launch the client application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
