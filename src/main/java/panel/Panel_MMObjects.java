package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import application.MemoryManager;

public class Panel_MMObjects {
	
	public static MemoryManager memoryManager = MemoryManager.getInstance();
	
	private static JPanel panel_MMObjects;
	
	private static List<JRadioButton> JRButtonArray = new ArrayList<JRadioButton>();
	private static ButtonGroup buttonGroup = new ButtonGroup();
	
	
	public JPanel panel_MMObjects() {
		panel_MMObjects = new JPanel();
		
		panel_MMObjects.setPreferredSize(new Dimension(300, 600));
//		panel_MMObjects.setBackground(Color.WHITE);
//		panel_MMObjects.setBounds(300, 350, 0, 0);
		panel_MMObjects.setLayout(new BoxLayout(panel_MMObjects, BoxLayout.Y_AXIS));
		
		
		return panel_MMObjects;
	}
	//Panel_ObjectProperties.updatePanel_ObjectProperties();
	public static void addObjectToPanel() {
		// creates a new button for the JRButtonArray
		JRadioButton jrButton = new JRadioButton("Object4D: " + JRButtonArray.size());
		jrButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel_ObjectProperties.updatePanel_ObjectProperties();
			}
		});
		JRButtonArray.add(jrButton);
		
		// adds the JRButtonArray button to the button groud to the panel
		buttonGroup.add(JRButtonArray.get(JRButtonArray.size() - 1));
		panel_MMObjects.add(JRButtonArray.get(JRButtonArray.size() - 1));
		
		updatePanel_MMObjects();
	}
	
	public static void updatePanel_MMObjects() {
		panel_MMObjects.revalidate();
	}
	
	public static int getCurrentlySelected() {
		for (int i = 0; i < JRButtonArray.size(); i++) {
			if (JRButtonArray.get(i).isSelected())
				return i;
		}
		return -1;
	}
	
	
//	public JRadioButton radioButton(String name) {
//		JRadioButton returnButton = new JRadioButton(name);
//		
//		return returnButton;
//	}

}
