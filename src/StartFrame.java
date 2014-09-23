import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.*;


import java.io.*;

@SuppressWarnings("serial")
public class StartFrame extends JFrame{

	int value1;
	int value2;
	private String[] choicesAvailable = {"Easy","Medium","Hard"};
	JComboBox choiceBox = new JComboBox(choicesAvailable);
	String choice;
	JButton startButton = new JButton("Start");;
	JPanel panel = new JPanel();
	static MazeGen.Maze gen;
	private JTextField x_param;
	private JTextField y_param;
	BackgroundSound sound = new BackgroundSound();
	
	public StartFrame(){
		super("3D-Maze");
	}

	public void StartAll(){
		try{
			JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("textures/moria.jpg"))));
			panel.add(background, BorderLayout.PAGE_END);
		}
		catch (IOException e) {
            e.printStackTrace();
		}
		setLayout(new BorderLayout());
		setSize(400, 550);
		setLocation(825, 425);
		setResizable(false);
		choiceBox.setSelectedItem(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(choiceBox, BorderLayout.CENTER);
		panel.add(startButton, BorderLayout.SOUTH);
		x_param = new JTextField(2);		
		y_param = new JTextField(2);		
		panel.add(x_param);
		JLabel by = new JLabel("X");
		by.setForeground(Color.white);
		panel.add(by);
		panel.add(y_param);	
		panel.setBackground(Color.black);
		add(panel);

		startButton.addActionListener(new startListener());
		getRootPane().setDefaultButton(startButton);
		setVisible(true);
	}
	

	public class startListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			Point param = null;
			if(choiceBox.getSelectedIndex() == -1){
				String x = x_param.getText();
				String y = y_param.getText();
				if(x.equals("") | y.equals("")){
					param = new Point (10, 10);										
				}
				else{
					param = customDifficulty(x, y);	
				}
			}
			else{
				choice = choicesAvailable[choiceBox.getSelectedIndex()];
				param = getDifficulty(choice);				
			}
			setVisible(false);
			MazeGUI maze = new MazeGUI();
			gen = MazeGen.generateMaze(param.x,param.y, maze.pane);       
			maze.setVisible(true);				
			maze.pane.buildAll(gen.width,gen.height, gen);
			for(int[] meh : gen.arr)
			{
				for(int i : meh)
				{
					if(i == 0)
						System.out.print("# ");
					else if(i == 1)
						System.out.print("  ");
					else if(i == 2)
						System.out.print("E ");
					else if(i == 3)
						System.out.print("S ");
					else if(i == 4)
						System.out.print("X ");
					else
						System.out.print((char)((int)'A' - i - 1)+" ");
				}
				System.out.println();
			}
			sound.play(new File("soundtrack.wav"));
		}		
	}

	public Point getDifficulty(String s){
		if (s.equals("Easy")) {
			return new Point(5,5);
		}
		if (s.equals("Medium")){
			return new Point(10,10);
		}
		if (s.equals("Hard")){
			return new Point(20,20);
		}
		return new Point(10,10);
	}
	
	public Point customDifficulty(String x, String y){
		int newx = Integer.parseInt(x);
		int newy = Integer.parseInt(y);
		return new Point(newx, newy);
	}
	
}