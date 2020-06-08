package application;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panel.Panel;
import panel.Panel_MMObjects;
import panel.Panel_ObjectLoader;
import panel.Panel_ObjectProperties;
import panel.Panel_Viewport;
import renderEngine.DisplayManager;

/**
 * The Main method class for setting up and initialising the Educational Application
 * @author Shonam
 *
 */
public class application {
	
	private static final String WINDOW_TITLE = "Educational Application";
	private static JFrame window;
	
	public static MemoryManager memoryManager = MemoryManager.getInstance();
	
//	public static DisplayManager dm;
	public static Thread dmt;
	
	// Sets up and initialises the application window
	private JFrame startApplication()
	{
		System.out.println("Starting Application");
		// JFrame initialisations
		window = new JFrame();
		// Close on exit
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.getContentPane().setLayout(new FlowLayout());
		window.setTitle(WINDOW_TITLE);
		window.getContentPane().setBackground(Color.LIGHT_GRAY);
//		window.setPreferredSize(new Dimension(720, 480));
//		window.setPreferredSize(new Dimension(1280, 750));
		window.setPreferredSize(new Dimension(640, 800));
		window.pack();
		
		// set position on screen by the OS
//		window.setLocationByPlatform(true);
		// set position to the centre of the screen
//		window.setLocationRelativeTo(null);
		// set position somewhere onscreen
		window.setLocation(1080, 100);
		
		// general but not final layout
		JPanel mainPanel = new JPanel(new BorderLayout(2, 0));
		JPanel rightPanel = new JPanel(new BorderLayout(2, 1));
		rightPanel.add(new Panel("Object Loader", new Dimension(300, 140), new Panel_ObjectLoader().panel_ObjectLoader()), BorderLayout.PAGE_START);
		rightPanel.add(new Panel("Object Properties", new Dimension(300, 400), new Panel_ObjectProperties().panel_ObjectProperties()), BorderLayout.PAGE_END);
		mainPanel.add(rightPanel, BorderLayout.LINE_END);
		
		rightPanel.add(new Panel("Viewport", new Dimension(300, 200), new Panel_Viewport().panel_Viewport()), BorderLayout.CENTER);
		
		mainPanel.add(new Panel("Scene Objects", new Panel_MMObjects().panel_MMObjects()));
//		mainPanel.add(new panel("Viewport", new Dimension(960, 650)), BorderLayout.CENTER);
		window.add(mainPanel);
		
		// previous
//		window.add(new panel("testPanel 1"));
//		window.add(new panel("testPanel 2"));
		
		/*////////////////////////////////
		 
		// Button to Add new Panel
		JButton addButton = new JButton("Add New Panel");
		addButton.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        window.add(new panel("new Panel"));
		        window.revalidate();
		    }
		});
		window.add(b);
		////////////////////////////////*/
		
		// set the window to visible
		window.setVisible(true);
		// returns the JFrame window
		return window;
	}
	
	// Main
	public static void main(String[] args) 
	{
		new application().startApplication();
		
//		MemoryManager memoryManager = MemoryManager.getInstance();
		
		
//		DisplayManager dm = new DisplayManager();
//		dm = new DisplayManager();
//		dm.startDisplay();
		
		dmt = new DisplayManager();
		dmt.start();
		
//		Thread t = new Thread("main_viewport");
//		System.out.println("Starting {" + t.getName() + " } Thread");
//		t.start();
	}


}
