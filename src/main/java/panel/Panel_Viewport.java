package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import application.application;
import renderEngine.DisplayManager;
import renderEngine.Renderer;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_POINT;

public class Panel_Viewport {
	
	private static JPanel panel_Viewport;
//	private DisplayManager dm;
	
	private JButton openButton = new JButton("Open Viewport");
	private JButton restartButton = new JButton("Restart Viewport");
	private JButton closeButton = new JButton("Close Viewport");
	
	private JButton gl_fill_Button = new JButton("GL_FILL");
	private JButton gl_line_Button = new JButton("GL_LINE");
	private JButton gl_point_Button = new JButton("GL_POINT");
	
	public JPanel panel_Viewport() {
		
		panel_Viewport = new JPanel();
		
		JPanel panelBox = new JPanel(new GridLayout(2, 1));
		JPanel inner = new JPanel(new FlowLayout());
		
		inner.setPreferredSize(new Dimension(300, 100));
//		inner.setBackground(Color.WHITE);
		inner.setBounds(300, 0, 0, 0);
		inner.setLayout(new FlowLayout(FlowLayout.CENTER));
		
//		dm = new DisplayManager();
//		dm.startDisplay();
		
		openButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
//		    	dm = new DisplayManager();
//				application.dm.startDisplay();
		    	application.dmt = new DisplayManager();
		    	application.dmt.start();
		    	
		    }
		});
		
		restartButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
//		    	DisplayManager dm = new DisplayManager();
//		    	application.dm.terminateDisplay();
//		    	application.dm.startDisplay();
		    	
		    	application.dmt.interrupt();
		    	application.dmt = new DisplayManager();
		    	application.dmt.start();
		    	
		    }
		});
		
		closeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	
//		    	DisplayManager dm = new DisplayManager();
//		    	application.dm.terminateDisplay();
		    	application.dmt.interrupt();
		    	
		    }
		});
		
		inner.add(openButton);
		inner.add(restartButton);
		inner.add(closeButton);
		
		
		gl_fill_Button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	Renderer.setRendererPolygonMode(GL_FILL);
		    }
		});
		gl_line_Button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	Renderer.setRendererPolygonMode(GL_LINE);
		    }
		});
		gl_point_Button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	Renderer.setRendererPolygonMode(GL_POINT);
		    }
		});

		JPanel inner2 = new JPanel(new FlowLayout());
		inner2.setPreferredSize(new Dimension(300, 0));
//		inner2.setBackground(Color.WHITE);
		inner2.setBounds(300, 0, 0, 0);
		inner.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		inner2.add(gl_fill_Button);
		inner2.add(gl_line_Button);
		inner2.add(gl_point_Button);
		
		panelBox.add(inner);
		panelBox.add(inner2);
		panel_Viewport.add(panelBox);
		
		return panel_Viewport;
	}

}
