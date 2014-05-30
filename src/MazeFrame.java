

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;


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
		initHowPlayScreen();
		initWinScreen();
		initLoseScreen();
//		createPuaseFrame();
		initPauseScreen();
		initCardLayout();
		
		this.setSize(463,390);
		this.setMinimumSize(new Dimension(463,390));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
	}
	
	public void removeTime(int timeRemoved){
		timeCount -= timeRemoved;
		timeLabel.setText(timeCount + " sec");
	}
	
	
	
	public void showWin(){
		if(board != null){
			gameScreen.remove(board);
		}
		layout.show(mainPanel, "win");
		winScreen.requestFocus();
		timer.restart();
		timer.stop();
		winTimeLabel.setText("Your time was " + currentTime + " seconds");
	//	winScoreLabel.setText("Your score was " + maze.getPlayer().getFuel());
		timeCount = 0;
		timeLabel.setText(timeCount + " sec");
		howToPlay.setVisible(false);
		frame.setMinimumSize(new Dimension(463,390));
		frame.pack();
	}
	
	
	public void showLose(){
		if(board != null){
			gameScreen.remove(board);
		}
		layout.show(mainPanel, "lose");
		loseScreen.requestFocus();
		timer.restart();
		timer.stop();
	//	winScoreLabel.setText("Your score was " + maze.getPlayer().getFuel());
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
		mainPanel.add(loseScreen, "lose");
		mainPanel.add(pauseScreen, "pause");
	}
	
	private void initGameScreen(){
		gameScreen = new JPanel();
		gameScreen.setLayout(new BorderLayout());
		JButton retMainMenu = new JButton(goMenu);
		JButton helpButton = new JButton(drawPath);
		scoreLabel = new JLabel("Fuel: " + 0);
		//retMainMenu.setSize(20, 20);
		//helpButton.setSize(20, 20);
		//Timer time = new Tim

		
		timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 18));
		timeLabel.setForeground(Color.YELLOW);
		
		scoreLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 18));
		scoreLabel.setForeground(Color.YELLOW);
		//timeLabel.setPreferredSize(new Dimension(50,20));
//		timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//		timeLabel.setPreferredSize(new Dimension(50,20));
		 ActionListener timerListener = new ActionListener() {
		        public void actionPerformed(ActionEvent actionEvent) {
		        	if (timeCount < 9999){
		        		currentTime =  System.currentTimeMillis() - timeStart;
		        		currentTime = currentTime/1000;
		        		timeLabel.setText(currentTime + " sec");
			        	maze.consumeFuel();
			        	System.out.print(maze.getPlayer().getFuel());
			        	scoreLabel.setText("Fuel: " + maze.getPlayer().getFuel());
		
		        	} else {
		        		timeLabel.setText("way too long");
		        		timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.BOLD, 15));	
		        		timer.stop();
		        	}
		        }
		};
		
		timer = new Timer(50, timerListener);
		    
		    
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
		
		JButton pauseButton = new JButton(showPause);
		gc.gridy = 2;
		gc.weighty = 2;
		gc.ipady = 20;
		gameMenu.add(pauseButton, gc);
		
		gc.gridy = 3;
		gc.weighty = 100;
		gc.ipady = 0;
		gameMenu.add(scoreLabel, gc);
		gc.gridy = 4;
		gc.weighty = 200;
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
		gc.ipady = 50;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridy = 0;
		JLabel mazeLabel = new JLabel("MAZE GAME", SwingConstants.CENTER);
		mazeLabel.setFont(new Font(mazeLabel.getFont().getName(), Font.BOLD, 26));
		mazeLabel.setForeground(Color.YELLOW);
		menuScreen.add(mazeLabel, gc);
		gc.ipadx = 115;
		gc.ipady = 20;
		gc.gridy = 1;
//		gc.weighty = 1;
		JButton startGame = new JButton(goDifficultySelect);
		menuScreen.add(startGame, gc);
		
		JButton howPlay = new JButton(goHowPlay);
		gc.gridy = 2;
		gc.ipadx = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		menuScreen.add(howPlay, gc);
		
		JButton exitButton = new JButton(exitGame);
		gc.gridy = 3;
		gc.ipadx = 0;
		menuScreen.add(exitButton, gc);
	}
	
	private void initWinScreen(){
		winScreen = new JPanel();
		winScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		winScreen.setBackground(Color.GRAY);
		JLabel winLabel = new JLabel("YOU WIN!!", SwingConstants.CENTER);
		winLabel.setFont(new Font(winLabel.getFont().getName(), Font.BOLD, 30));
		winLabel.setForeground(Color.YELLOW);
		winTimeLabel = new JLabel("Your time was " + currentTime + " seconds", SwingConstants.CENTER);
		winTimeLabel.setForeground(Color.YELLOW);
		//winScoreLabel = new JLabel("Your score was " + 0, SwingConstants.CENTER);
		//winScoreLabel .setForeground(Color.YELLOW);
		timeCount = 0;
		gc.ipadx = 78;
		gc.ipady = 20;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 0;
//		gc.weighty = 1;
		winScreen.add(winLabel, gc);
		gc.gridy = 1;
		gc.ipady = 0;
		winScreen.add(winTimeLabel, gc);
		gc.gridy = 2;
		gc.ipady = 20;
		//winScreen.add(winScoreLabel, gc);
		gc.gridy = 3;
		gc.ipady = 20;
		JButton playAgain = new JButton(goDifficultySelect);
		playAgain.setText("Play Again");
		winScreen.add(playAgain, gc);
		
		JButton menuButton = new JButton(goMenu);
		gc.gridy = 4;
		gc.ipadx = 76;
		winScreen.add(menuButton, gc);
	}
	
	
	private void initLoseScreen(){
		loseScreen = new JPanel();
		loseScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		loseScreen.setBackground(Color.GRAY);
		JLabel loseLabel = new JLabel("YOU LOSE!!", SwingConstants.CENTER);
		loseLabel.setFont(new Font(loseLabel.getFont().getName(), Font.BOLD, 30));
		loseLabel.setForeground(Color.YELLOW);
		JLabel loseLabelFuel = new JLabel("You Ran Out Of Fuel", SwingConstants.CENTER);
		loseLabelFuel.setForeground(Color.YELLOW);
		//winScoreLabel = new JLabel("Your score was " + 0, SwingConstants.CENTER);
		//winScoreLabel .setForeground(Color.YELLOW);
		gc.ipadx = 78;
		gc.ipady = 20;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 0;
//		gc.weighty = 1;
		loseScreen.add(loseLabel, gc);
		gc.gridy = 1;
		gc.ipady = 0;
		loseScreen.add(loseLabelFuel, gc);
		gc.gridy = 2;
		gc.ipady = 20;
		//winScreen.add(winScoreLabel, gc);
		gc.gridy = 3;
		gc.ipady = 20;
		JButton playAgain = new JButton(goDifficultySelect);
		playAgain.setText("Play Again");
		loseScreen.add(playAgain, gc);
		
		JButton menuButton = new JButton(goMenu);
		gc.gridy = 4;
		gc.ipadx = 76;
		loseScreen.add(menuButton, gc);
		
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
		
		howToPlay.setText("You are an explorer who is trying to escape a maze you are stuck in."
				+ " In order to escape you must make your way from the top left corner to the bottom"
				+ " right corner. However you must keep an eye on your fuel which is depleting whilst"
				+ " you are trying to escape. But all is not lost as there is often fuel nearby which"
				+ " can be used to replenish your supply. \nYour character is controlled by either using"
				+ " the up, down, left and right arrow keys or alternatively you can use 'W' to go up, "
				+ "'S' to go down 'A' to go left and 'D' to go right. There are three different difficulties"
				+ " that you can select from:\n- Easy is the easiest of them all and is the smallest maze."
				+ "\n- Medium is like Easy but has a lerger maze which is harder to get through.\n- Hard is"
				+ " obviously, Hard olny shows a little bit of the maze and how much is shown depends on how "
				+ "much fuel you have in your possession. More fuel means you can see more of the maze."
				+ "\nThroughout all of your maze navigation you will be timed. This time will be displayed at "
				+ "the end of the game so that you will know how fast you completed the maze.");
		
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

	
	private void initPauseScreen(){
		pauseScreen = new JPanel();
		pauseScreen.setLayout(new GridBagLayout());
		GridBagConstraints gc =  new GridBagConstraints();
		gc.anchor = GridBagConstraints.CENTER;
		pauseScreen.setBackground(Color.GRAY);
		pauseTimeLabel = new JLabel("Your time so far is " + currentTime + " seconds", SwingConstants.CENTER);
		pauseTimeLabel.setForeground(Color.YELLOW);
		//winScoreLabel = new JLabel("Your score was " + 0, SwingConstants.CENTER);
		//winScoreLabel .setForeground(Color.YELLOW);
		timeCount = 0;
		gc.ipadx = 78;
		gc.ipady = 20;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridy = 0;
		JLabel pauseLabel = new JLabel("Paused", SwingConstants.CENTER);
		pauseLabel.setForeground(Color.YELLOW);
		pauseLabel.setFont(new Font(pauseLabel.getFont().getName(), Font.BOLD, 30));
		pauseScreen.add(pauseLabel, gc);
//		gc.weighty = 1;
		gc.gridy = 1;
		gc.ipady = 0;
		pauseScreen.add(pauseTimeLabel, gc);
		gc.gridy = 2;
		gc.ipady = 20;
		//winScreen.add(winScoreLabel, gc);
		gc.gridy = 3;
		gc.ipady = 20;
		JButton resume = new JButton(resumeGame);
		gc.ipadx = 90;
		pauseScreen.add(resume, gc);
		
		JButton menuButton = new JButton(goMenu);
		gc.gridy = 4;
		gc.ipadx = 76;
		pauseScreen.add(menuButton, gc);
	}
	
	private void initActions() {
		goDifficultySelect = new AbstractAction("Play"){
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(mainPanel, "diff");
				scoreLabel.setText("Fuel: " + 0);
				difficultyScreen.requestFocusInWindow();
			}
		};
		
		goEasy = new AbstractAction("Easy"){
			

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
				timeStart = System.currentTimeMillis();
				maze = new MazeImp(15,15);
				board = new Board(maze);
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(board.getPixelWidth() + 100,board.getPixelHeight() + 20));
				//frame.setSize(new Dimension(463,390));
				frame.pack();
			}
		};
		
		goMedium = new AbstractAction("Medium"){
	

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
				timeStart = System.currentTimeMillis();
				maze = new MazeImp(25,25);
				board = new Board(maze);
				//board.activateSurvival();
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(board.getPixelWidth() + 100,board.getPixelHeight() + 20));
				//frame.setSize(new Dimension(463,390));
				frame.pack();
			}
		};
		
		goHard = new AbstractAction("Hard"){
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
				timeStart = System.currentTimeMillis();
				maze = new MazeImp(25,25);
				//maze.randomEnd(new ManhattenHeuristic());
				maze.setPlayer(new Player(0,0,4));
				//maze.addLoops(10);
				board = new Board(maze);
				board.activateSurvival();
				maze.addMazeListener(new boardListener());
				gameScreen.add(board, BorderLayout.CENTER);
				gameScreen.setBackground(Color.GRAY);
				layout.show(mainPanel, "game");
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(board.getPixelWidth() + 100,board.getPixelHeight()+ 20));
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
				scoreLabel.setText("Fuel: " + 0);
				howToPlay.setVisible(false);
				frame.setSize(463,390);
				frame.setMinimumSize(new Dimension(463,390));
				//frame.setMinimumSize(new Dimension(board.getPixelWidth() + 100,board.getPixelHeight()));
				frame.pack();
			}
		};
		
		drawPath = new AbstractAction("Help"){
			@Override
			public void actionPerformed(ActionEvent e) {
				//board.drawPath();
				gameScreen.getComponent(1).requestFocus();
//				timeCount +=20;
//				timeLabel.setText(timeCount + " sec");
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
		
		exitGame = new AbstractAction("Exit") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		
		showPause = new AbstractAction("Pause") {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setMinimumSize(new Dimension(463,390));
				layout.show(mainPanel, "pause");
				pauseTimeLabel.setText("Your time so far is " + currentTime + " seconds");
				pauseScreen.requestFocus();
                timer.stop();
                frame.pack();
//                frame.setVisible(false);
			}
		};
		
		
		resumeGame = new AbstractAction("Resume"){
			@Override
			public void actionPerformed(ActionEvent e){
				layout.show(mainPanel, "game");
				timer.start();
				menuScreen.requestFocusInWindow();
				gameScreen.getComponent(1).requestFocus();
				frame.setMinimumSize(new Dimension(board.getPixelWidth() + 100,board.getPixelHeight()+ 20));
				frame.pack();
//				frame.setVisible(true);
			}
		};
	}
	
	
	
//	public static void createPuaseFrame() {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//            	pauseFrame = new JFrame("Pause Menu");
//                pauseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                pauseFrame.setMinimumSize(new Dimension(363,290));
//                pauseFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
////                try {
////                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////                } catch (Exception e) {
////                   e.printStackTrace();
////                }
//                JPanel panel = new JPanel();
//                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//                panel.setOpaque(true);
//                JTextArea textArea = new JTextArea(15, 30);
//                textArea.setWrapStyleWord(true);
//                textArea.setEditable(false);
//                textArea.setFont(Font.getFont(Font.SANS_SERIF));
//                JScrollPane scroller = new JScrollPane(textArea);
//                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//                JPanel inputpanel = new JPanel();
//                inputpanel.setLayout(new FlowLayout());
//                JTextField input = new JTextField(20);
//                JButton button = new JButton("Enter");
//                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
//                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//                panel.add(scroller);
//                JButton resumeButton = new JButton(resumeGame);
//                inputpanel.add(input);
//                inputpanel.add(button);
//                inputpanel.add(resumeButton);
//                panel.add(inputpanel);
//                pauseFrame.getContentPane().add(BorderLayout.CENTER, panel);
//                pauseFrame.pack();
//                pauseFrame.setLocationByPlatform(true);
//                pauseFrame.setVisible(false);
//                pauseFrame.setResizable(false);
//                input.requestFocus();
//                pauseFrame.setLocationRelativeTo(null);
//            }
//        });
//    }
	
	
	public class boardListener implements MazeListener{

		@Override
		public void playerMoved(EventObject e) {
			board.repaint();
		}

		@Override
		public void mazeRestarted(EventObject e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void playerFinished(EventObject e) {
			// TODO Auto-generated method stub
			showWin();
		}

		@Override
		public void treasureCollected(EventObject e) {
			// TODO Auto-generated method stub
			//removeTime(2);
			scoreLabel.setText("Fuel: " + maze.getPlayer().getFuel());
		}

		@Override
		public void fuelConsumed(EventObject e) {
			if(maze.getPlayer().getFuel() > 0){
				board.repaint();
			} else {
				timer.stop();
				showLose();
			}
		}
		
	}
	
	private JPanel mainPanel;
	private JPanel gameScreen;
	private JPanel menuScreen;
	private JPanel difficultyScreen;
	private JPanel howPlayScreen;
	private JPanel winScreen;
	private JPanel loseScreen;
	private JPanel pauseScreen;

	private Action goDifficultySelect;
	private Action goEasy;
	private Action goMedium;
	private Action goHard;
	private Action goMenu;
	private Action drawPath;
	private Action goHowPlay;
	private Action exitGame;
	private Action showPause;
	private static Action resumeGame;

	private Board board;
	private MazeImp maze;
	private CardLayout layout;
	private JFrame frame = this;
	
	private int timeCount = 0;
	private final JLabel timeLabel = new JLabel(timeCount + " sec", SwingConstants.RIGHT);
	private JLabel scoreLabel;
	private static Timer timer;
	private long timeStart;
	long currentTime;
	
	private JTextArea howToPlay;
	private JLabel winTimeLabel;
	private JLabel winScoreLabel;
	
//	private static JFrame pauseFrame; 
	private JLabel pauseTimeLabel;
	
}
