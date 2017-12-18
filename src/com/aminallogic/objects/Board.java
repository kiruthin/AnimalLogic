package com.aminallogic.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.geom.Rectangle2D;
//import javax.swing.

import com.aminallogic.exceptions.InvalidBoardException;

public class Board extends JFrame implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	static String testBoard1  = "GG1,GY2,GB3,GR4,LG5,LY6,LB7,LR8,HG9,HY10,HB11,HR12,CG13,CY14,CB15,CR16";
	static String testBoard2  = "HY1,HR2,HB3,HG4,GB5,GG6,GY7,GR8,CG9,CY10,CR11,CB12,LR13,LG14,LB15,LY16";
	static String testBoard3  = "CB1,CR2,LY3,GR4,HB5,CY6,LR7,HY8,LG9,CG10,GG11,HR12,GY13,HG14,GB15,LB16";
	
	/*
	 * We will assume that the size of the board also determine the number of types of pieces 
	 * and colors. If the board size is 4 then we have 4 different animals with 4 different colors
	 */
	int BOARD_SIZE   = -1;
    int ANIMAL_TYPES = -1;
	int NUM_COLORS   = -1;

	AnimalQueue TheBoard [] = null;
//*
	

    /**
     *  The color black.
     */
    public static final Color BLACK = Color.BLACK;

    /**
     *  The color blue.
     */
    public static final Color BLUE = Color.BLUE;

    /**
     *  The color cyan.
     */
    public static final Color CYAN = Color.CYAN;

    /**
     *  The color dark gray.
     */
    public static final Color DARK_GRAY = Color.DARK_GRAY;

    /**
     *  The color gray.
     */
    public static final Color GRAY = Color.GRAY;

    /**
     *  The color green.
     */
    public static final Color GREEN  = Color.GREEN;

    /**
     *  The color light gray.
     */
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;

    /**
     *  The color magenta.
     */
    public static final Color MAGENTA = Color.MAGENTA;

    /**
     *  The color orange.
     */
    public static final Color ORANGE = Color.ORANGE;

    /**
     *  The color pink.
     */
    public static final Color PINK = Color.PINK;

    /**
     *  The color red.
     */
    public static final Color RED = Color.RED;

    /**
     *  The color white.
     */
    public static final Color WHITE = Color.WHITE;

    /**
     *  The color yellow.
     */
    public static final Color YELLOW = Color.YELLOW;

    /**
     * Shade of blue used in Introduction to Programming in Java.
     * It is Pantone 300U. The RGB values are approximately (9, 90, 166).
     */
    public static final Color BOOK_BLUE = new Color(9, 90, 166);

    /**
     * Shade of light blue used in Introduction to Programming in Java.
     * The RGB values are approximately (103, 198, 243).
     */
    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);
    
    /**
     * Shade of red used in <em>Algorithms, 4th edition</em>.
     * It is Pantone 1805U. The RGB values are approximately (150, 35, 31).
     */
    public static final Color BOOK_RED = new Color(150, 35, 31);

    /**
     * Shade of orange used in Princeton's identity.
     * It is PMS 158. The RGB values are approximately (245, 128, 37).
     */
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);
    
    public static final Color TRANSPARENT =      new Color(255, 255, 255, 0);

    // default colors
    private static final Color DEFAULT_PEN_COLOR   = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;

    // boundary of drawing canvas, 0% border
    private static final double BORDER = 0.0;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;

    // default canvas size is SIZE-by-SIZE
    private static final int DEFAULT_SIZE = 512;

    // default pen radius
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // default font
    private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    // current pen color
    private Color penColor;

    // canvas size
    private int width  = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;

    // current pen radius
    private double penRadius;

    // show we draw immediately or wait until next show?
    private boolean defer = false;

    private double xmin, ymin, xmax, ymax;

    // name of window
    private String name = "Animal Logic Game";

    // for synchronization
    private final Object mouseLock = new Object();
    private final Object keyLock = new Object();

    // current font
    private Font font;

    // the JLabel for drawing
    private JLabel drawLabel;

    // double buffered graphics
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;

    // the frame for drawing to the screen
   // private JFrame frame = new JFrame();

    // mouse state
    private boolean isMousePressed = false;
    private double mouseX = 0;
    private double mouseY = 0;

    // keyboard state
    private final LinkedList<Character> keysTyped = new LinkedList<Character>();
    private final TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // event-based listeners
    //private final ArrayList<DrawListener> listeners = new ArrayList<DrawListener>();
    SolutionQueue sq = null;
    
    JButton resetButton = null;
    JButton undoButton = null;
 

	private JComboBox c = new JComboBox();
	private JButton   b = new JButton("Select");
	private JTextField t = new JTextField();
	int count            =0;

	public Board(String StartingSequence, int size) throws Exception
	{
		BOARD_SIZE   = size;
		ANIMAL_TYPES = size;
		NUM_COLORS   = size;
		TheBoard = new AnimalQueue [BOARD_SIZE];
		
		this.initPanel();
		this.initializeBoard(StartingSequence);
	}
	public Board(int size) throws Exception
	{
		this(testBoard2, size);	
	}
	public void initializeBoard(String StartingSequence) throws InvalidBoardException
	{
		/*
		 *  You can have different starting points for the board. 
		 *  
		 *  For starting sequence we will use the following notation to represent
		 *  the pieces on the board:
		 *  
		 *  Type Notation:
		 *   	L = Lion
		 *   	G = Giraffe
		 *   	C = Camel 
		 *   	H = Hippo
		 *  
		 *  Color Notation:
		 *  	B = Blue
		 *  	R = Red
		 *  	G = Green
		 *  	Y = Yellow
		 *  
		 *  
		 *   Followed by the position on the board. A board is 4 x 4. 
		 *   
		 *   [type][color][position]
		 *   
		 *   For example:
		 *   
		 *   	CB1,CR2,LY3,GR4,HB5,CY6,LR7,HY8,LG9,CG10,GG11,HR12,GY13,HG14,GB15,LB16
		 */
		
		sq = new SolutionQueue(
				40, 
				10, 
				onscreen, offscreen);
		
		List<String> thePieceList = Arrays.asList(StartingSequence.split(","));
		thePieceList.forEach(item->{
			//System.out.println("PieceCode ="+ item.toUpperCase());
			AnimalQueue aq  = null;
			Piece tempP = new Piece(item.toUpperCase(), onscreen, offscreen);
			int rowCol = ((tempP.getBoardPostiion()-1)/this.BOARD_SIZE);
			int queP   = ((tempP.getBoardPostiion()-1)%this.BOARD_SIZE);
			
			if(this.TheBoard[rowCol] == null)
			{
				aq = new AnimalQueue(
							40, 
							200+(rowCol*AnimalQueue.recY_space), 
							onscreen, offscreen);
				this.TheBoard[rowCol] = aq;	
			}
			else
			{
				aq = this.TheBoard[rowCol];
			}
			aq.addPiece(tempP, (tempP.getBoardPostiion()-1)%this.BOARD_SIZE);
			tempP.setMyQueue(aq);
			tempP.setQueuePostiion(queP);
			
			//System.out.println(tempP);
		});
        this.paintComponent(onscreen);
	}
	/*
	 * Initialize the Panel for the board.
	 */
	public void initPanel()
	{
		this.setVisible(false);
		//this.setLayout(new BorderLayout());
        
		//*Layout setup
		
		GroupLayout layout = new GroupLayout(this);
		//this.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);		
				
		//frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen  = onscreenImage.createGraphics();
        setXscale();
        setXscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        
        // frame stuff
        ImageIcon icon = new ImageIcon(onscreenImage);
        drawLabel = new JLabel(icon);

        drawLabel.addMouseListener(this);
        drawLabel.addMouseMotionListener(this);
               
        //Create buttons
        resetButton = new JButton();
        undoButton  = new JButton();
        
        undoButton.setVisible(true);
        undoButton.setEnabled(true);
        undoButton.setName("Undo");
        undoButton.setText("Undo");
        undoButton.setSize(75, 30);
        undoButton.setPreferredSize(new Dimension(75,30));
        
        resetButton.setVisible(true);
        resetButton.setEnabled(true);
        resetButton.setName("Reset");
        resetButton.setText("Reset");
    	resetButton.setSize(75, 30);
    	resetButton.setPreferredSize(new Dimension(75,30));
    	
        //this.add(resetButton, BorderLayout.NORTH);
        //this.add(undoButton, BorderLayout.NORTH);
      
      //  JPanel mainPanel = new JPanel(new BorderLayout());

      //  JPanel northPanel = new JPanel();
      //  northPanel.add(resetButton);
      //  northPanel.add(undoButton);
      //  mainPanel.add(northPanel, BorderLayout.NORTH);
      //  mainPanel.add(drawLabel, BorderLayout.CENTER);
        

     //   generalColorValue = getColorParameter(Skin.GENERAL_TEXT_COLOR); 
        //controlColorValue = getColorParameter(Skin.CONTROL_BACKGROUND_COLOR); 
 
		//trimColorValue = getColorParameter(Skin.TRIM_COLOR); 
	    
		initComboBox();
		//this.setContentPane(drawLabel); <---------------------------
        this.add(drawLabel, BorderLayout.CENTER);
        
        this.addKeyListener(this);    // JLabel cannot get keyboard focus
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
        this.setTitle(name);
       // frame.setJMenuBar(createMenuBar());
        this.pack();
        this.requestFocusInWindow();
        this.setVisible(true);
 
	}
	public void initComboBox()
	{
		String[] description = { "Ebullient", "Obtuse", "Recalcitrant",
			      "Brilliant", "Somnescent", "Timorous", "Florid", "Putrescent" };

		for (int i = 0; i < 4; i++)
			      c.addItem(description[count++]);
		t.setEditable(false);
		b.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        if (count < description.length)
			        {
			          c.addItem(description[count++]);
			        }
			  }
		});
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    System.out.println("index: " + c.getSelectedIndex() + "   "
			            + ((JComboBox) e.getSource()).getSelectedItem());
			    
			    t.setText("index: " + c.getSelectedIndex() + "   "
			            + ((JComboBox) e.getSource()).getSelectedItem());
			      }
		});
		//	    Container cp = getContentPane();
		//	    cp.setLayout(new FlowLayout());
	//	this.add(t, BorderLayout.NORTH);
		this.add(c, BorderLayout.NORTH);
	//	this.add(b, BorderLayout.NORTH);
	}
    public void paintComponent (Graphics2D g) {
    	Graphics2D g2d = (Graphics2D) g;
    	super.paintComponents(g2d);
        //Arrays.setAll(TheBoard, i -> supplier.paint());
        //TheBoard.forEach(item->System.out.print(item+" "));
    	draw();
    }
	private void setPenColor() {
		// TODO Auto-generated method stub	
	}
	private void clear() {
		// TODO Auto-generated method stub
		System.out.println("Calling clear");
	}
	private void setFont() {
		// TODO Auto-generated method stub
	}
	private void setPenRadius() {
		// TODO Auto-generated method stub
	}
	private void setXscale() {
		// TODO Auto-generated method stub
	}
	public void draw()
	{
        onscreen.setColor(DEFAULT_CLEAR_COLOR);
        onscreen.fillRect(0, 0, width, height);
		sq.draw();        
		for(int i=0; i<this.BOARD_SIZE; i++)
        {
        	this.TheBoard[i].draw();
        }
		
    	undoButton.setSize(75, 30);
    	undoButton.setLocation(
    			(drawLabel.getWidth()-40+drawLabel.getY())+drawLabel.getLocationOnScreen().x,
    			40+drawLabel.getLocationOnScreen().y);
    	
        //undoButton.setLocation((drawLabel.getWidth()-undoButton.getX()+drawLabel.getY()),40);

    	resetButton.setSize(75, 30);
    	resetButton.setLocation(
    			undoButton.getX(),
    			undoButton.getY()+undoButton.getHeight()+5);
    	
    	this.validate();
        this.repaint();

	}
	@Override
	public String toString()
	{
		String returnMe = "";
		for (int i=0; i<this.TheBoard.length; i++) 
		{
			returnMe += printRowBorder();
		}
		returnMe += printRowBorder();//because of extra line
		return returnMe;
	}
	public String printRowBorder()
	{
		String returnMe ="";
		for(int i=0; i<BOARD_SIZE*BOARD_SIZE; i++)
		{
			returnMe +="=";
		}
		return returnMe+"\n";
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	/*
		if(Piece.moving)
		{
			Piece.selectedPiece.setLocation(e.getPoint());
			draw();
			this.validate();
			this.repaint();
			System.out.println("Mouse dragged" + Piece.selectedPiece);
		}
	*/	
	}
	@Override
	public void mouseMoved(MouseEvent e) {

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("mouseClicked");
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("mouseEntered");
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("mouseExited");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Optional<Piece> p = null;
		
		p = sq.processMousePress(e);
		p.ifPresent(item->{
			item.setScale(AnimalQueue.PieceScale);
			item.getMyQueue().addPiece(item, 0);
			item.draw();
		//	sq.draw();
			});
		for(int i=0; i<this.BOARD_SIZE; i++)
		{
			AnimalQueue aq = this.TheBoard[i];
			if(aq.contains(e.getPoint()))
			{
				aq.getTop().ifPresent(item->{
					if(sq.isValidMove(item))
					{
						aq.removePiece()
						.ifPresent(item2->{
							System.out.println(item2);
							item2.setScale(SolutionQueue.PieceScale);
							sq.addPiece(item2);
							item2.draw();
							});
					}
					else
					{	
						System.out.println("Not a valie move");
					}
				});
				/*
				p = aq.processMousePress(e);
				p.ifPresent(item->{
		//			System.out.println("\t aq contents ="+ aq.myQueue);
					Piece tempP = aq.removePiece();
					tempP.setScale(SolutionQueue.PieceScale);
					sq.addPiece(tempP);
		//			System.out.println("\t Peek  ="+ aq.removePiece());
		//			System.out.println("\t PieceCode removed  ="+ tempP);
		//			System.out.println("\t aq contents ="+ aq.myQueue);
					}
				);
				*/
				break;
			}
		}
		draw();
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(Piece.selectedPiece != null)
		{
			Piece.moving = false;
			Piece.selectedPiece.setMoving(false);
			draw();
			repaint();
			//System.out.println("mouseReleased");
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("keyPressed");
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("keyReleased");
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("keyTyped");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("actionPerformed");
		this.validate();
		this.repaint();
	}
}
