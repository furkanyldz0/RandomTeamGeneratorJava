import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class TeamGenerator extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L; //i don't know what that is 
	private JPanel mainPanel;
	private JTextField team1Count, team2Count, inputField;
	private JButton addButton, generateButton;
	private JList<String> inputList, team1, team2;
	private DefaultListModel<String> team1Model, team2Model;
	private static final DefaultListModel<String> INPUT_LIST_MODEL = new DefaultListModel<>();;
	private JLabel inputCountLabel, vsLabel;
	private JRadioButton defaultMode, customMode;
	private int team1Size, team2Size, totalTeamSize;
	private JMenuBar menuBar;
	private JMenu modeMenu, optionsMenu;
	private JMenuItem aboutItem;
	private JRadioButtonMenuItem teamGeneratorModeItem, drawModeItem;

	TeamGenerator(){
		
		initializeMainPanel();
		initializeMenuBar();
		
		this.add(mainPanel);
//		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Team Generator");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 2;
		int y = (screenSize.height - this.getHeight() - 100) / 2;
		this.setLocation(x,y);
		
		this.setVisible(true);
	}
	
	public void initializeMainPanel() {
		
		inputList = new JList<>(INPUT_LIST_MODEL);
		JScrollPane inputPane = new JScrollPane(inputList);
		
		team1Model = new DefaultListModel<>();
		team1 = new JList<>(team1Model);
		JScrollPane team1Pane = new JScrollPane(team1);
		
		team2Model = new DefaultListModel<>();
		team2 = new JList<>(team2Model);
		JScrollPane team2Pane = new JScrollPane(team2);
		
		inputPane.setPreferredSize(new Dimension(120, 200));
		team1Pane.setPreferredSize(new Dimension(120,300)); 
		team2Pane.setPreferredSize(new Dimension(120,300));
		
		team1Count = new JTextField("Count");
		team2Count = new JTextField("Count");
		inputField = new JTextField("Type here"); 
		team1Count.setColumns(3);
		team2Count.setColumns(3);
		inputField.setColumns(6);
		
		CustomFocusListener listener1 = new CustomFocusListener(); //for esthetics purposes
		listener1.setFocusListener(team1Count, team1Count.getText());
		CustomFocusListener listener2 = new CustomFocusListener();
		listener2.setFocusListener(team2Count, team2Count.getText());
		CustomFocusListener listener3 = new CustomFocusListener();
		listener3.setFocusListener(inputField, inputField.getText());
		
		@SuppressWarnings("unused") //Keybinding for enter button
		Action enterAction = new EnterAction();
		inputField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enterAction");
		inputField.getActionMap().put("enterAction", new EnterAction());
		
		@SuppressWarnings("unused") //Keybinding for delete button
		Action deleteAction = new DeleteAction();
		inputList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0), "deleteAction");
		inputList.getActionMap().put("deleteAction", new DeleteAction()); 
		
		inputCountLabel = new JLabel(String.valueOf(INPUT_LIST_MODEL.size()));
		vsLabel = new JLabel("vs");
		
		defaultMode = new JRadioButton("Default");
		defaultMode.addActionListener(this);
		defaultMode.setFocusable(false);
		
		customMode = new JRadioButton("Custom");
		customMode.addActionListener(this);
		customMode.setFocusable(false);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(defaultMode);
		buttonGroup.add(customMode);
		
		buttonGroup.setSelected(defaultMode.getModel(), true);
		setCountFieldsVisible(false);
		
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		addButton.setFocusable(false);
		generateButton = new JButton("Generate");
		generateButton.addActionListener(this);
		generateButton.setFocusable(false);
		
		mainPanel = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelCenterSub1 = new JPanel();
		JPanel panelCenterSub2 = new JPanel();
		JPanel panelSouth = new JPanel();
		JPanel panelSouthSub1 = new JPanel();
		JPanel panelSouthSub2 = new JPanel();
		JPanel panelCenterSubCount = new JPanel();
		
		mainPanel.setLayout(new BorderLayout(20,10));
		panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
		panelCenterSubCount.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCenterSub1.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCenterSub2.setLayout(new BoxLayout(panelCenterSub2, BoxLayout.LINE_AXIS));
		panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.PAGE_AXIS));
		panelSouthSub1.setLayout(new FlowLayout());
		panelSouthSub2.setLayout(new FlowLayout());
		
		panelCenter.add(inputPane);

		panelCenterSubCount.add(inputCountLabel);
		panelCenterSub1.add(team1Count);
		panelCenterSub1.add(vsLabel);
		panelCenterSub1.add(team2Count);
		panelCenterSub2.add(defaultMode);
		panelCenterSub2.add(customMode);
		
		panelSouthSub1.add(inputField);
		panelSouthSub1.add(addButton);
		panelSouthSub2.add(generateButton);
		panelSouth.add(panelSouthSub1);
		panelSouth.add(panelSouthSub2);
		
		panelCenter.add(panelCenterSubCount);
		panelCenter.add(panelCenterSub1);
		panelCenter.add(panelCenterSub2);
		
		mainPanel.add(team1Pane, BorderLayout.WEST);
		mainPanel.add(team2Pane, BorderLayout.EAST);
		mainPanel.add(panelCenter, BorderLayout.CENTER);
		mainPanel.add(panelSouth, BorderLayout.SOUTH);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,10,5));
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
		menuGroup.setSelected(teamGeneratorModeItem.getModel(), true);
		
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
			addElementToList();
		}
		
		if(e.getSource() == generateButton) {
			
			if(defaultMode.isSelected()) {
				generateEqualTeams();
			}
			else if(customMode.isSelected()) {
				generateTeamsSafely();
			}
			
		}
		
		if(e.getSource() == defaultMode) {
			setCountFieldsVisible(false);
		}	
		if(e.getSource() == customMode) {
			setCountFieldsVisible(true);
		}
		if(e.getSource() == aboutItem) {
			JOptionPane.showMessageDialog(null, "You can press;\nENTER for adding,\nDELETE for removing values."
					+ "\nGithub profile: github.com/furkanyldz0", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getSource() == drawModeItem) {
			this.dispose();
			DrawFrame drawFrame = new DrawFrame();
		}
	}
	
	private void addElementToList() {
		
		String input = inputField.getText();
		if(!input.equals("Type here") && !(input.isBlank())) {
			INPUT_LIST_MODEL.addElement(input);
			inputCountLabel.setText(String.valueOf(INPUT_LIST_MODEL.size()));
		}
	}
	
	private void generateEqualTeams() {
		int inputSize = INPUT_LIST_MODEL.getSize();
		
		if(inputSize == 0) {
			System.out.println("List is empty!");
		}
		
		else {
			Random rand = new Random();
			int randomNumber = rand.nextInt(2);
			
			team1Model.clear();
			team2Model.clear();
			int midIndex= inputSize / 2;
			
			List<Object> inputs = Arrays.asList(INPUT_LIST_MODEL.toArray());
			Collections.shuffle(inputs);
			
			if(inputSize % 2 == 0 || randomNumber == 0) {
				
				for(int i = 0; i < midIndex; i++) 
					team1Model.addElement((String)inputs.get(i)); //String.valueOf(inputs.get(i))
				
				for(int i = midIndex; i < inputSize; i++) 
					team2Model.addElement((String)inputs.get(i)); 
				
			}
			else if((inputSize % 2 != 0) && randomNumber == 1) {
				
				for(int i = 0; i < midIndex + 1; i++) 
					team1Model.addElement((String)inputs.get(i)); 
				
				for(int i = midIndex + 1; i < inputSize; i++) 
					team2Model.addElement((String)inputs.get(i)); 
				
			}
			
			
		}
		
	}
	
	private void generateTeamsSafely() {
		int inputSize = INPUT_LIST_MODEL.size();
		
		if(inputSize == 0) {
			System.out.println("List is empty!");
		}
		
		else if(isNumeric(team1Count.getText()) && isNumeric(team2Count.getText())) {
			team1Size = Integer.parseInt(team1Count.getText());
			team2Size = Integer.parseInt(team2Count.getText());
			totalTeamSize = team1Size + team2Size;
			
			if(inputSize != totalTeamSize) {
//				System.out.println("Kutucuklardaki değerlerin toplam eleman sayısına eşit olduğundan emin olun!");
				JOptionPane.showMessageDialog(null, "Total element count is not equal to sum of fields!", "Hata", JOptionPane.WARNING_MESSAGE);
			}
				
			else
				generateTeams();
			
		}
		else {
//			System.out.println("Kutucuklara uygun değerler girdiğinizden emin olun!");
			JOptionPane.showMessageDialog(null, "Make sure that values in fields are valid."
					, "Error", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	private void generateTeams() { 
		team1Model.clear();
		team2Model.clear();
		
		List<Object> inputs = Arrays.asList(INPUT_LIST_MODEL.toArray());
		Collections.shuffle(inputs);
		
		for(int i = 0; i < team1Size; i++) {
			team1Model.addElement((String)inputs.get(i)); //String.valueOf(inputs.get(i))
		}
		
		for(int i = team1Size; i < totalTeamSize; i++) {
			team2Model.addElement((String)inputs.get(i)); //String.valueOf(inputs.get(i))
		}

	}
	
	
	public boolean isNumeric(String str) {
		boolean check = false;
		
		if(str.matches("[0-9]+")) 
			check = true;
		
		return check;
	}
	
	
	private void removeElement() {
		int selectedIndex = inputList.getSelectedIndex();
		
		if(selectedIndex != -1) {
			INPUT_LIST_MODEL.remove(selectedIndex);
			inputCountLabel.setText(String.valueOf(INPUT_LIST_MODEL.size()));
		}
		else
			System.out.println("Item is not selected!");
	}
	
	private void setCountFieldsVisible(boolean check) {
		if(check) {
			team1Count.setEnabled(true);
			team2Count.setEnabled(true);
			vsLabel.setEnabled(true);
		}
		else {
			team1Count.setEnabled(false);
			team2Count.setEnabled(false);
			vsLabel.setEnabled(false);
		}
		
	}
	
	public static DefaultListModel<String> getInputListModel(){
		return INPUT_LIST_MODEL;
	}

	
	//inner class
	private class DeleteAction extends AbstractAction{ /**
		 * 
		 */
		private static final long serialVersionUID = -3952914188649142696L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			removeElement();
			
		}
	}
	
	private class EnterAction extends AbstractAction{ /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		
		@Override
		public void actionPerformed(ActionEvent e) {
			addElementToList();
			
		}
	}
	
}


