package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import application.MemoryManager;

public class Panel_ObjectProperties {
	
	private static MemoryManager memoryManager = MemoryManager.getInstance();
	
	private static JPanel panel_ObjectProperties;
	
	// Object's position in 4D space
	JLabel positionLabel = new JLabel("Position");
	private static float x = 0;
	private static float y = 0;
	private static float z = 0;
	private static float w = 0; // 4-th axis
	// Object's rotational values in 4D space
	JLabel rotationLabel = new JLabel("Rotation");
	private static float xy = 0;
	private static float xz = 0;
	private static float xw = 0;
	private static float yz = 0;
	private static float yw = 0;
	private static float zw = 0;
	
	private static JSpinner spinner_x = positionSpinner();
	private static JSpinner spinner_y = positionSpinner();
	private static JSpinner spinner_z = positionSpinner();
	private static JSpinner spinner_w = positionSpinner();
	
	private static JSpinner spinner_xy = rotationSpinner();
	private static JSpinner spinner_xz = rotationSpinner();
	private static JSpinner spinner_xw = rotationSpinner();
	private static JSpinner spinner_yz = rotationSpinner();
	private static JSpinner spinner_yw = rotationSpinner();
	private static JSpinner spinner_zw = rotationSpinner();
	
	private static JSpinner positionSpinner() {
		
//		SpinnerModel valueModel = new SpinnerNumberModel(0.0f, -10.0f, 10.0f, 0.1f);
		SpinnerModel valueModel = new SpinnerNumberModel(new Float(0.0), new Float(-10.0), new Float(10), new Float(0.05));
		JSpinner spinner = new JSpinner(valueModel);
        spinner.setBounds(0, 0, 80, 30);
        spinner.setPreferredSize(new Dimension(90, 25));
        
        return spinner;
	}
	
	private static JSpinner rotationSpinner() {
		
//		SpinnerModel valueModel = new SpinnerNumberModel(0.0d, -360.0d, 360.0d, 0.5d);
		SpinnerModel valueModel = new SpinnerNumberModel(new Float(0.0), new Float(-360), new Float(360), new Float(1.0));
		JSpinner spinner = new JSpinner(valueModel);
        spinner.setBounds(0, 0, 80, 30);
        spinner.setPreferredSize(new Dimension(90, 25));
        
        return spinner;
	}
	
	public JPanel panel_ObjectProperties() {
		
		panel_ObjectProperties = new JPanel();
		
		panel_ObjectProperties.setPreferredSize(new Dimension(300, 350));
//		panel_ObjectProperties.setBackground(Color.WHITE);
		panel_ObjectProperties.setBounds(300, 350, 0, 0);
		panel_ObjectProperties.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		/**
		 * Position Spinner Functionality
		 */
		spinner_x.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
//				System.out.println("X SPINNER val: " + spinner_x.getValue());
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setX((float) spinner_x.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_y.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setY((float) spinner_y.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_z.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setZ((float) spinner_z.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_w.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setW((float) spinner_w.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		
		/**
		 * Rotation Spinner Functionality
		 */
		spinner_xy.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setXY((float) spinner_xy.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});

		spinner_xz.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setXZ((float) spinner_xz.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_xw.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setXW((float) spinner_xw.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_yz.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setYZ((float) spinner_yz.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_yw.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setYW((float) spinner_yw.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});
		
		spinner_zw.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int objectIndex = Panel_MMObjects.getCurrentlySelected();
				// change the object directly in MM class
				memoryManager.getObject(objectIndex).setZW((float) spinner_zw.getValue());
				memoryManager.MemoryManagerContentsUnRendered();
			}
		});

//		System.out.println(panel_ObjectProperties.getLayout());
		
		JPanel position = new JPanel(new FlowLayout());
//		position.setBackground(Color.WHITE);
		position.setBounds(140, 250, 0, 0);
		position.setPreferredSize(new Dimension(100, 300));
//		panel_ObjectProperties.add(positionLabel);
		position.add(new JLabel("X"));
		position.add(spinner_x);
		position.add(new JLabel("Y"));
		position.add(spinner_y);
		position.add(new JLabel("Z"));
		position.add(spinner_z);
		position.add(new JLabel("W"));
		position.add(spinner_w);
		
		//---------------
		JPanel rotation = new JPanel(new FlowLayout());
//		rotation.setBackground(Color.WHITE);
		rotation.setBounds(140, 250, 0, 0);
		rotation.setPreferredSize(new Dimension(100, 340));
//		panel_ObjectProperties.add(rotationLabel);
		rotation.add(new JLabel("XY"));
		rotation.add(spinner_xy);
		rotation.add(new JLabel("XZ"));
		rotation.add(spinner_xz);
		rotation.add(new JLabel("XW"));
		rotation.add(spinner_xw);
		rotation.add(new JLabel("YZ"));
		rotation.add(spinner_yz);
		rotation.add(new JLabel("YW"));
		rotation.add(spinner_yw);
		rotation.add(new JLabel("ZW"));
		rotation.add(spinner_zw);
		
		
		positionLabel.setPreferredSize(new Dimension(100, 20));
//		positionLabel.setForeground(Color.WHITE);
		rotationLabel.setPreferredSize(new Dimension(100, 20));
//		rotationLabel.setForeground(Color.WHITE);
		
		panel_ObjectProperties.add(positionLabel);
		panel_ObjectProperties.add(rotationLabel);
		panel_ObjectProperties.add(position);
		panel_ObjectProperties.add(rotation);
		
		
		return panel_ObjectProperties;
	}
	
	
	public static void updatePanel_ObjectProperties() {
		int objectIndex = Panel_MMObjects.getCurrentlySelected();
		
		spinner_x.setValue(memoryManager.getObject(objectIndex).getX());
		spinner_y.setValue(memoryManager.getObject(objectIndex).getY());
		spinner_z.setValue(memoryManager.getObject(objectIndex).getZ());
		spinner_w.setValue(memoryManager.getObject(objectIndex).getW());
		spinner_xy.setValue(memoryManager.getObject(objectIndex).getXY());
		spinner_xz.setValue(memoryManager.getObject(objectIndex).getXZ());
		spinner_xw.setValue(memoryManager.getObject(objectIndex).getXW());
		spinner_yz.setValue(memoryManager.getObject(objectIndex).getYZ());
		spinner_yw.setValue(memoryManager.getObject(objectIndex).getYW());
		spinner_zw.setValue(memoryManager.getObject(objectIndex).getZW());
		panel_ObjectProperties.revalidate();
	}
	

}
