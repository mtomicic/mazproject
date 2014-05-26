

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MazeFrame extends JFrame{

	
	public MazeFrame() {
		this.setTitle("MAZE GAME");	
		mainPanel = new JPanel();
		this.add(mainPanel);
	
		
		initActions();
		initMenuScreen();
		initGameScreen();
		initDifficultyScreen();
		initHowPlayScreen();
		initWinScreen();
		initCardLayout();
		
		this.setSize(463,390);
		this.setMinimumSize(new Dimension(463,390));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
	}
	
	public void showWin(){
		if(board != null){
			gameScreen.remove(board);
		}
		layout.show(mainPanel, "win");
		winScreen.requestFocus();
		timer.restart();
		timer.stop();
		winTimeLabel.setText("Your time was " + timeCount + " seconds");
		timeCount = 0;
		timeLabel.setText(timeCount + " sec");
		howToPlay.setVisible(false);
		frame.setMinimumSize(new Dimension(463,390));
		frame.pack();
	}
	
	private void initCardLayout() {
		layout = new CardLayout();
		mainPanel.setLayout(layout);
		mainPanel.add(menuScreen, "mainmenu");
		mainPanel.add(gameScreen, "game");
		mainPanel.add(difficultyScreen, "diff");
		mainPanel.add(howPlayScreen, "howPlay");
		mainPanel.add(winScreen, "win");
	}
	
	private void initGameScreen(){
		gameScreen = new JPanel();
		gameScreen.setLayout(new BorderLayout());
		JButton retMainMenu = new JButton(goMenu);
		JButton helpButton = new JButton(drawPath);

		//retMainMenu.setSize(20, 20);
		//helpButton.setSize(20, 20);
		
		//Timer time = new Tim

		
		timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 22));
		timeLabel.setForeground(Color.YELLOW);
		//timeLabel.setPreferredSize(new Dimension(50,20));
//		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//		timeLabel.setPreferredSize(new Dimension(50,20));
		 ActionListener actionListener = new ActionListener() {
		        public void actionPerformed(ActionEvent actionEvent) {
		        	if (timeCount < 9999){
		        		timeLabel.setText(timeCount + " sec");
			        	timeCount++;
		        	} else {
		        		timeLabel.setText("way too long");
		        		timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 15));	
		        		timer.stop();
		        	}
		        }
		};
		
		timer = new Timer(1000, actionListener);
		    
		    
		JPanel gameMenu = new JPanel();
		gameMenu.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gameMenu.setBackground(Color.GRAY);
		gc.ipady = 20;
//		gc.ipadx = 115;
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
		gc.gridy = 2;
		gc.weighty = 3;
		gameMenu.add(timeLabel,gc);
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
		JButton menuButton = new JButton(goMenu);
//		easyButton.setSize(100, 50);
//		mediumButton.setSize(100, 50);
//		hardButton.setSize(100, 50);
		JLabel difficultyLabel = new JLabel("Choose Difficulty");
		difficultyLabel.setForeground(Color.YELLOW);
		difficultyLabel.setFont(new Font(difficultyLabel.getFont().getName(), Font.BOLD, 22));
		gc.anchor = GridBagConstraints.NORTH;
		gc.ipady = 50;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		difficultyScreen.add(difficultyLabel, gc);
		gc.ipady = 20;
		gc.weighty = 0;
		gc.gridy = 1;
		difficultyScreen.add(easyButton, gc);
		gc.weighty = 0;
		gc.gridy = 2;
		difficultyScreen.add(mediumButton, gc);
		gc.weighty = 0;
		gc.gridy = 3;
		difficultyScreen.add(hardButton, gc);
		gc.gridy = 4;
		difficultyScreen.add(menuButton, gc);
	}
	
	private void initMenuScreen() {
		menuScreen = new JPanel();
		menuScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		menuScreen.setBackground(Color.GRAY);
		gc.ipady = 20;
		gc.ipadx = 115;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 0;
//		gc.weighty = 1;
		JButton startGame = new JButton(goDifficultySelect);
		menuScreen.add(startGame, gc);
		
		JButton howPlay = new JButton(goHowPlay);
		gc.gridy = 1;
		gc.ipadx = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		menuScreen.add(howPlay, gc);
		
	}
	
	private void initWinScreen(){
		winScreen = new JPanel();
		winScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		winScreen.setBackground(Color.GRAY);
		JLabel winLabel = new JLabel("YOU WIN!!", SwingConstants.CENTER);
		winLabel.setForeground(Color.YELLOW);
		winTimeLabel = new JLabel("Your time was " + timeCount + " seconds", SwingConstants.CENTER);
		winTimeLabel.setForeground(Color.YELLOW);
		timeCount = 0;
		gc.ipady = 20;
		gc.ipadx = 78;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 0;
//		gc.weighty = 1;
		winScreen.add(winLabel, gc);
		gc.gridy = 1;
		winScreen.add(winTimeLabel, gc);
		gc.gridy = 2;
		JButton playAgain = new JButton(goDifficultySelect);
		playAgain.setText("Play Again");
		winScreen.add(playAgain, gc);
		
		JButton menuButton = new JButton(goMenu);
		gc.gridy = 3;
		gc.ipadx = 76;
		winScreen.add(menuButton, gc);
	}
	
	private void initHowPlayScreen(){
		howPlayScreen = new JPanel();
		howPlayScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		howPlayScreen.setBackground(Color.GRAY);
		howToPlay = new JTextArea();
		howToPlay.setBackground(null);
		howToPlay.setForeground(Color.YELLOW);
		howToPlay.setLineWrap(true);
		howToPlay.setEditable(false);
		howToPlay.setPreferredSize(new Dimension(400,300));
		howToPlay.setWrapStyleWord(true);
		howToPlay.setHighlighter(null);
		howToPlay.setText("ENTER TEXT HERE");
		howToPlay.setVisible(false);
		howPlayScreen.add(howToPlay, gc);
		gc.gridy = 0;
		JButton retMainMenu = new JButton(goMenu);
		gc.gridy = 1;
		gc.ipady = 20;
		gc.anchor = GridBagConstraints.SOUTH;
		gc.ipadx = 78;
		howPlayScreen.add(retMainMenu, gc);
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
				timer.start();
				maze = new MazeImp(15,15);
				board = new Board(maze);
				gameScreen.add(board, BorderLayout.CENTER);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(463,390));
				frame.setSize(new Dimension(463,390));
				frame.pack();
			}
		};
		
		goMedium = new AbstractAction("Medium"){
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
				maze = new MazeImp(20,20);
				board = new Board(maze);
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(463,390));
				frame.setSize(new Dimension(463,390));
				frame.pack();
			}
		};
		
		goHard = new AbstractAction("Hard"){
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
				maze = new MazeImp(25,25);
				board = new Board(maze);
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(463,390));
				frame.pack();
			}
		};
		

		goMenu = new AbstractAction("Main Menu"){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(board != null){
					gameScreen.remove(board);
				}
				layout.show(mainPanel, "mainmenu");
				menuScreen.requestFocus();
				timeCount = 0;
				timer.restart();
				timer.stop();
				timeLabel.setText(timeCount + " sec");
				howToPlay.setVisible(false);
				frame.setMinimumSize(new Dimension(463,390));
				frame.pack();
			}
		};
		
		drawPath = new AbstractAction("Help"){
			@Override
			public void actionPerformed(ActionEvent e) {
				//board.drawPath();
				gameScreen.getComponent(1).requestFocus();
				timeCount +=20;
				timeLabel.setText(timeCount + " sec");
				frame.pack();
			}
			
			
		};
		
		
		goHowPlay = new AbstractAction("How To Play"){
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(mainPanel, "howPlay");
				howPlayScreen.requestFocus();
				howToPlay.setVisible(true);
				frame.setMinimumSize(new Dimension(463,390));
				frame.pack();
			}
		};
	}
	private JPanel mainPanel;
	private JPanel gameScreen;
	private JPanel menuScreen;
	private JPanel difficultyScreen;
	private JPanel howPlayScreen;
	private JPanel winScreen;

	private Action goDifficultySelect;
	private Action goEasy;
	private Action goMedium;
	private Action goHard;
	private Action goMenu;
	private Action drawPath;
	private Action goHowPlay;

	private Board board;
	private MazeImp maze;
	private CardLayout layout;
	private JFrame frame = this;
	
	private int timeCount = 0;
	private final JLabel timeLabel = new JLabel(timeCount + " sec", SwingConstants.RIGHT);
	private Timer timer;
	
	private JTextArea howToPlay;
	private JLabel winTimeLabel;
}
