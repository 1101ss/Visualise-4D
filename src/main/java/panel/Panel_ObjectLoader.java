package panel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import application.application;
import utils.Object3DLoader;
import utils.Object4DLoader;

public class Panel_ObjectLoader {
	
	private static JPanel panel_ObjectLoader;
	
	private JButton loadButton_predefined = new JButton("Load Shape");
	private JButton loadBotton_file = new JButton("Load 4D File");
	
//	MemoryManager memoryManager = MemoryManager.getInstance();
	
	public JPanel panel_ObjectLoader() {
		
		panel_ObjectLoader = new JPanel();
//		panel_ObjectLoader.setBackground(Color.WHITE);
		panel_ObjectLoader.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// loads a per-defines 4D shape
		loadButton_predefined.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * TODO remove and add dropdown for generic shapes
				 */
				System.out.println("Loading Object4D File ...");
				application.memoryManager.load4DintoMM(Object4DLoader.loadObj4D("src/main/resources/4Dmodels/testShape"));
				Panel_MMObjects.addObjectToPanel();
			}
			});
		
		// Loads a .obj4d file
		loadBotton_file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String chosenFile = null;
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Load Object 4D File");
				
				// opens in current directory
				fileChooser.setCurrentDirectory(new File("."));
				
				// only allows Object4D and Object3D files to be displayed and loaded
				fileChooser.setFileFilter(new FileNameExtensionFilter("Object4D or Object3D", 
						Object4DLoader.OBJECT4D_FILEEXTENSION, Object3DLoader.OBJECT3D_FILEEXTENSION));
				
				if (fileChooser.showOpenDialog(loadBotton_file) == JFileChooser.APPROVE_OPTION) {
//					System.out.println("Opening: " + fileChooser.getSelectedFile().getPath());
					chosenFile = fileChooser.getSelectedFile().getPath().substring(0, 
							fileChooser.getSelectedFile().getPath().lastIndexOf("."));
					}
				
//				System.out.println("Chosen File path: \n" + chosenFile);
				// is cancelled the file choosing then return
				if (chosenFile == null)
					return;
				
				System.out.println("Loading Object4D File ...");
				application.memoryManager.load4DintoMM(Object4DLoader.loadObj4D(chosenFile));
				Panel_MMObjects.addObjectToPanel();
			}
			});
		
		
		panel_ObjectLoader.add(loadButton_predefined);
		panel_ObjectLoader.add(loadBotton_file);
		
		return panel_ObjectLoader;
	}

}
