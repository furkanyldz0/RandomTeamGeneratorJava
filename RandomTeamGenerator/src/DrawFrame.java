import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


public class DrawFrame extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JButton addButton, drawButton;
	private JTextField inputField;
	private DefaultListModel<String> inputListModel;
	private JList<String> inputList;
	private JLabel inputCountLabel, drawnValueLabel, label1;
	private JMenuBar menuBar;
	private JMenu modeMenu, optionsMenu;
	private JMenuItem aboutItem;
	private JRadioButtonMenuItem teamGeneratorModeItem, drawModeItem;
	
	DrawFrame(){
		
		initializeDrawPanel();
		initializeMenuBar();
		
		this.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setResizable(false);
		this.setTitle("Team Generator");
		this.pack();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 2;
		int y = (screenSize.height - this.getHeight() - 100) / 2;
		this.setLocation(x,y); //screen position
		
		this.setVisible(true);
		}
	
	public void initializeDrawPanel(){
		
		inputListModel = TeamGenerator.getInputListModel();
		inputList = new JList<>(inputListModel);
		JScrollPane inputPane = new JScrollPane(inputList);
		
		inputPane.setPreferredSize(new Dimension(170, 250));
		
		inputCountLabel = new JLabel(String.valueOf(inputListModel.size()));
		label1 = new JLabel("Insert value");
		drawnValueLabel = new JLabel("       ");
		
		inputField = new JTextField("Type here");
		inputField.setColumns(6);
		CustomFocusListener listener = new CustomFocusListener(); //for esthetics purposes
		listener.setFocusListener(inputField, inputField.getText());
		
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setFocusable(false);
		
		drawButton = new JButton("Draw");
		drawButton.addActionListener(this);
		drawButton.setFocusable(false);
		
		@SuppressWarnings("unused") //Keybinding for delete button
		Action deleteAction = new DeleteAction();
		inputList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0), "deleteAction");
		inputList.getActionMap().put("deleteAction", new DeleteAction());
		
		@SuppressWarnings("unused") //Keybinding for enter button
		Action addAction = new addAction();
		inputField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "addAction");
		inputField.getActionMap().put("addAction", new addAction());
		
		mainPanel = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelCenterSubCount = new JPanel();
		JPanel panelNorth = new JPanel();
		JPanel panelNorthSub1 = new JPanel();
		JPanel panelNorthSub2 = new JPanel();
		JPanel panelSouth = new JPanel();
		JPanel panelSouthSub1 = new JPanel();
		JPanel panelSouthSub2 = new JPanel();
		
		mainPanel.setLayout(new BorderLayout(0,10));
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
		panelCenterSubCount.setLayout(new FlowLayout(FlowLayout.CENTER));
//		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.PAGE_AXIS));
		panelNorthSub1.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelNorthSub2.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.PAGE_AXIS));
		panelSouthSub1.setLayout(new FlowLayout());
		panelSouthSub2.setLayout(new FlowLayout());
		
		panelCenterSubCount.add(inputCountLabel);
		panelCenter.add(inputPane);
		panelCenter.add(panelCenterSubCount);
		
		panelNorthSub1.add(label1);
		panelNorthSub2.add(drawnValueLabel);
		panelNorth.add(panelNorthSub1);
		panelNorth.add(panelNorthSub2);
		
		panelSouthSub1.add(inputField);
		panelSouthSub1.add(addButton);
		panelSouthSub2.add(drawButton);
		panelSouth.add(panelSouthSub1);
		panelSouth.add(panelSouthSub2);
		
		mainPanel.add(panelNorth, BorderLayout.NORTH);
		mainPanel.add(panelCenter, BorderLayout.CENTER);
		mainPanel.add(panelSouth, BorderLayout.SOUTH);
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,50,10,50));
		
	}
	
	
	public void initializeMenuBar() {
		
		menuBar = new JMenuBar();
		modeMenu = new JMenu("Mode");
		optionsMenu = new JMenu("Options");
		teamGeneratorModeItem = new JRadioButtonMenuItem("Team Generator");
		drawModeItem = new JRadioButtonMenuItem("Draw mode");
		aboutItem = new JMenuItem("About");
		
		ButtonGroup menuGroup = new ButtonGroup();
		menuGroup.add(teamGeneratorModeItem);
		menuGroup.add(drawModeItem);
		menuGroup.setSelected(drawModeItem.getModel(), true);
		
		teamGeneratorModeItem.addActionListener(this);
		drawModeItem.addActionListener(this);
		aboutItem.addActionListener(this);
		
		modeMenu.add(teamGeneratorModeItem);
		modeMenu.add(drawModeItem);
		optionsMenu.add(aboutItem);
		menuBar.add(modeMenu);
		menuBar.add(optionsMenu);
		this.setJMenuBar(menuBar);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton) {
			addElement();
		}
		if(e.getSource() == drawButton) {
			drawElement();
		}
		if(e.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(null, "You can press;\nENTER for adding,\nDELETE for removing values."
					+ "\nGithub profile: github.com/furkanyldz0","About", JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getSource() == teamGeneratorModeItem) {
			this.dispose();
			TeamGenerator teamGeneratorFrame = new TeamGenerator();
		}
	}
	
	private void addElement() {
		String input = inputField.getText();
		if(!input.equals("Type here") && !(input.isBlank())) {
			inputListModel.addElement(input);
			inputCountLabel.setText(String.valueOf(inputListModel.size()));
		}
	}
	
	private void drawElement() {
		int inputSize = inputListModel.size();
		
		if(inputSize == 0)
			System.out.println("List is empty!");
		
		else {
			Random rand = new Random();
			int index = rand.nextInt(inputSize);
			String drawnValue = inputListModel.get(index);
			drawnValueLabel.setText(drawnValue);
			label1.setText("Drawn Value: ");
		}
		
	}
	
	private void removeElement() {
		int selectedIndex = inputList.getSelectedIndex();
		
		if(selectedIndex != -1) {
			inputListModel.remove(selectedIndex);
			inputCountLabel.setText(String.valueOf(inputListModel.size()));
		}
		else
			System.out.println("Item is not selected!");
	}
	
	
	private class DeleteAction extends AbstractAction{ /**
		 * 
		 */
		private static final long serialVersionUID = -3952914188649142696L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			removeElement();
			
		}
	}
	
	private class addAction extends AbstractAction{ 

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			addElement();
			
		}
	}
	
}
