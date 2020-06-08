package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import renderEngine.DisplayManager;
import sun.awt.IconInfo;

/**
 * The panel class set out to standardise the panels in the in the Educational Application
 * @author Shonam
 *
 */
@SuppressWarnings("serial")
public class Panel extends JPanel {
	
	public String panelTitle = null;
	private static final Font FONT = new Font(null, Font.PLAIN, 12);
	private static final Border BORDER = BorderFactory.createLineBorder(Color.BLUE, 1);
	private static final int TAB_HEIGHT = 24;
	private static Dimension dimension;
	
	private JPanel panel_Content_;
	
	public Panel(String panelTitle) 
	{
		this.panelTitle = panelTitle;
		startPanel();
	}
	
	public Panel(String panelTitle, Dimension dimension) 
	{
		this.dimension = dimension;
		this.panelTitle = panelTitle;
		startPanel();
	}
	
	public Panel(String panelTitle, Dimension dimension, JPanel panelContent) 
	{
		this.panel_Content_ = panelContent;
		this.dimension = dimension;
		this.panelTitle = panelTitle;
		startPanel();
	}
	
	public Panel(String panelTitle, JPanel panelContent) 
	{
		this.panel_Content_ = panelContent;
		this.dimension = dimension;
		this.panelTitle = panelTitle;
		startPanel();
	}
	
	public Panel() 
	{
		startPanel();
	}
	
	
	/**
	 * This is used for the panel title at the top of the JPanel
	 * @param s JLabel String
	 * @param c JLabel string Colour
	 * @return l The JLabel with the string using System default font and in the colour the user choose
	 */
	public JLabel jlabel(String s, Color c)
	{
		JLabel l = new JLabel(s);
		l.setFont(FONT);
		l.setForeground(c);
//		l.setHorizontalAlignment(JLabel.LEFT);
//		l.setVerticalAlignment(JLabel.CENTER);
		l.setBorder(new EmptyBorder(0, 12, 0, 0));
//		l.setBorder(BORDER);
		
		return l;
	}
	
	/**
	 * 
	 * @return iconButton a JButton with aesthetic settings applied and some mouse events
	 */
	public JButton jbutton() 
	{
		// Tab icon and button
		Icon tabIconBlack = new ImageIcon(new ImageIcon("src/main/resources/icons/tripleBar_withAlpha50x50.png", "tab").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		Icon tabIconWhite = new ImageIcon(new ImageIcon("src/main/resources/icons/tripleBar_withAlpha50x50_WHITE.png", "tab").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JButton iconButton = new JButton(tabIconWhite);
		// button set
		iconButton.setMaximumSize(new Dimension(TAB_HEIGHT, TAB_HEIGHT));
		iconButton.setPreferredSize(new Dimension(TAB_HEIGHT, TAB_HEIGHT));
		iconButton.setBackground(Color.DARK_GRAY);
		iconButton.setBorderPainted(false);
		iconButton.setFocusPainted(false);
		iconButton.setRolloverEnabled(true);
		
		
		final JPopupMenu tabPopup = new JPopupMenu();
		tabPopup.setBackground(Color.BLACK);
		tabPopup.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		
		/**
		 * TODO not necessarily needed to implement or keep for now
		 */
//		JMenuItem jmi = new JMenuItem("Close Panel");
//		jmi.setAction(new AbstractAction("Close Panel") 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				// implement closing of panel
//			}
//		});
//		
//		jmi.setBackground(Color.DARK_GRAY);
//		jmi.setForeground(Color.WHITE);
//		jmi.setBorder(null);
//		jmi.setFont(FONT);
//		
//		tabPopup.add(jmi);
//		tabPopup.add(new JMenuItem(new AbstractAction("Close Panel") 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//				// implement closing of panel
//			}
//		}));
		
		iconButton.addMouseListener(new MouseAdapter() 
		{
			public void mouseEntered(MouseEvent e) 
			{
				iconButton.setBackground(Color.LIGHT_GRAY);
				iconButton.setIcon(tabIconBlack);
			}
			public void mouseExited(MouseEvent e) 
			{
				iconButton.setBackground(Color.DARK_GRAY);
				iconButton.setIcon(tabIconWhite);
			}
			public void mousePressed(MouseEvent e)
			{
				tabPopup.show(e.getComponent(), iconButton.getX() + ((int) TAB_HEIGHT / 2), iconButton.getY() + TAB_HEIGHT);
			}
		});
				
		return iconButton;
	}
	
	private void startPanel() 
	{
		
		// overall panel layout
		setDoubleBuffered(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		setPreferredSize(new Dimension(300, 200));
//		setPreferredSize(new Dimension(600, 350));
		if (dimension == null)
			setPreferredSize(new Dimension(300, 350));
		else 
			setPreferredSize(dimension);
		setBackground(Color.DARK_GRAY);
//		setBorder(BORDER);
		
		// JPanel for the top / title
		JPanel titlePanel = new JPanel(true);
		titlePanel.setBackground(Color.DARK_GRAY);
		titlePanel.setBorder(BORDER);
		titlePanel.setBorder(null);
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.add(jbutton());
		titlePanel.add(jlabel(panelTitle, Color.WHITE));
		titlePanel.setPreferredSize(new Dimension(TAB_HEIGHT, TAB_HEIGHT));
		titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, TAB_HEIGHT));
		
		// JPanel for the bottom / contents
		JPanel contentPanel = new JPanel(true);
		contentPanel.setLayout(new GridLayout(1, 1, 0, 0));
		contentPanel.setBackground(null);
		contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
		
		// JPanel for the final delivery of contents
		JPanel contents = new JPanel(true);
//		contents.setBackground(Color.DARK_GRAY);
		
		/**
		 * ADD CONTENT FROM HERE
		 */
//		contents.add();
		if (panel_Content_ != null)
			contents.add(panel_Content_);
		
		
		contentPanel.add(contents);
		
		add(titlePanel);
//		add(jlabel("<CONTENTS>", Color.WHITE));
		add(contentPanel);
		
		setVisible(true);
		
	}
}
