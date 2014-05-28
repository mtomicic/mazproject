

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MazeFrame extends JFrame{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			new MazeFrame();
		}
		
		});
	}
	
	public MazeFrame() {
		this.setTitle("MAZE GAME");	
		mainPanel = new JPanel();
		this.add(mainPanel);
	
		
		initActions();
		initMenuScreen();
		initGameScreen();
		initDifficultyScreen();
		initCardLayout();
		
		this.setSize(665, 550);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
	}
	
	private void initCardLayout() {
		layout = new CardLayout();
		mainPanel.setLayout(layout);
		mainPanel.add(menuScreen, "mainmenu");
		mainPanel.add(gameScreen, "game");
		mainPanel.add(difficultyScreen, "diff");
	}
	
	private void initGameScreen(){
		gameScreen = new JPanel();
		gameScreen.setLayout(new BorderLayout());
		JButton retMainMenu = new JButton(goMenu);
		JButton helpButton = new JButton(drawPath);
		//retMainMenu.setSize(20, 20);
		//helpButton.setSize(20, 20);
		JPanel gameMenu = new JPanel();
		gameMenu.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gameMenu.setBackground(Color.GRAY);
		
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.PAGE_START;
		gameMenu.add(retMainMenu, gc);
		//gc.ipadx = 45;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0;
		gc.weighty = 1;
		gameMenu.add(helpButton, gc);
		gameScreen.add(gameMenu, BorderLayout.LINE_END);
	}
	
	private void initDifficultyScreen(){
		difficultyScreen = new JPanel();
		
		
		
		difficultyScreen.setBackground(Color.GRAY);
		
		
		
		difficultyScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		JButton easyButton = new JButton(goEasy);
		JButton mediumButton = new JButton(goMedium);
		JButton hardButton = new JButton(goHard);
		difficultyScreen.add(easyButton);
		difficultyScreen.add(mediumButton);
		difficultyScreen.add(hardButton);
	}
	
	private void initMenuScreen() {
		menuScreen = new JPanel();
		menuScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		menuScreen.setBackground(Color.GRAY);
		
		JButton startGame = new JButton(goDifficultySelect);
		
		menuScreen.add(startGame);
		
	}

	private void initActions() {
		goDifficultySelect = new AbstractAction("Play"){
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(mainPanel, "diff");
				difficultyScreen.requestFocusInWindow();
			}
		};
		
		goEasy = new AbstractAction("Easy"){
			@Override
			public void actionPerformed(ActionEvent e) {
				maze = new MazeImp(4,4);
				board = new Board(maze);
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
			}
		};
		
		goMedium = new AbstractAction("Medium"){
			@Override
			public void actionPerformed(ActionEvent e) {
				maze = new MazeImp(20,20);
				board = new Board(maze);
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
			}
		};
		
		goHard = new AbstractAction("Hard"){
			@Override
			public void actionPerformed(ActionEvent e) {
				maze = new MazeImp(25,25);
				board = new Board(maze);
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
			}
		};
		

		goMenu = new AbstractAction("Main Menu"){
			@Override
			public void actionPerformed(ActionEvent e) {
				gameScreen.remove(board);
				layout.show(mainPanel, "mainmenu");
				menuScreen.requestFocus();
			}
		};
		
		drawPath = new AbstractAction("Help"){
			@Override
			public void actionPerformed(ActionEvent e) {
				//board.drawPath();
				gameScreen.getComponent(1).requestFocus();
			}
		};
		
	}
	
	public class boardListener implements MazeListener{

		@Override
		public void playerMoved(EventObject e) {
			board.repaint();
			//System.out.println("player moved");
		}

		@Override
		public void mazeRestarted(EventObject e) {
			board.repaint();
		}

		@Override
		public void playerFinished(EventObject e) {
			goMenu.actionPerformed(null);
			System.out.println("finished");
		}

		@Override
		public void treasureCollected(EventObject e) {
			//
		}
	}
	
	private JPanel mainPanel;
	private JPanel gameScreen;
	private JPanel menuScreen;
	private JPanel difficultyScreen;

	private Action goDifficultySelect;
	private Action goEasy;
	private Action goMedium;
	private Action goHard;
	private Action goMenu;
	private Action drawPath;

	private Board board;
	private MazeImp maze;
	private CardLayout layout;
}
