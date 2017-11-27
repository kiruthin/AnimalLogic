package com.aminallogic.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
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
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
//import javax.swing.

import com.aminallogic.exceptions.InvalidBoardException;

public class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	static String testBoard = "CB1,CR2,LY3,GR4,HB5,CY6,LR7,HY8,CG10,GG11,HR12,GY13,HG14,GB15,LB16";
	
	/*
	 * We will assume that the size of the board also determine the number of types of pieces 
	 * and colors. If the board size is 4 then we have 4 different animals with 4 different colors
	 */
	int BOARD_SIZE   = -1;
    int ANIMAL_TYPES = -1;
	int NUM_COLORS   = -1;

	ArrayList TheBoard [] = null;
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
    private String name = "Draw";

    // for synchronization
    private final Object mouseLock = new Object();
    private final Object keyLock = new Object();

    // current font
    private Font font;

    // the JLabel for drawing
    private JLabel draw;

    // double buffered graphics
    private BufferedImage offscreenImage, onscreenImage;
    private Graphics2D offscreen, onscreen;

    // the frame for drawing to the screen
    private JFrame frame = new JFrame();

    // mouse state
    private boolean isMousePressed = false;
    private double mouseX = 0;
    private double mouseY = 0;

    // keyboard state
    private final LinkedList<Character> keysTyped = new LinkedList<Character>();
    private final TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // event-based listeners
    //private final ArrayList<DrawListener> listeners = new ArrayList<DrawListener>();
    
    //TreeColor
    protected Integer controlColorValue; 
    
	public Board(String StartingSequence, int size) throws Exception
	{
		BOARD_SIZE   = size;
		ANIMAL_TYPES = size;
		NUM_COLORS   = size;
		TheBoard = new ArrayList [BOARD_SIZE];
		this.initializeBoard(StartingSequence);
	}
	public Board(int size) throws Exception
	{
		this(testBoard, size);	
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
		 *   	CB1,CR2,LY3,GR4,HB5,CY6,LR7,HY8,CG10,GG11,HR12,GY13,HG14,GB15,LB16
		 */
		HashMap<String, Integer> validCheck   = new HashMap<String, Integer>(); 
		
		
		List<String> thePieceList = Arrays.asList(StartingSequence.split(","));
//		thePieceList.forEach(item->System.out.print(item+" "));
		thePieceList.forEach(item->{
			System.out.println("PieceCode ="+ item.toUpperCase());
			Piece tempP = new Piece(item.toUpperCase());
		});

		if (frame != null) frame.setVisible(false);
        frame = new JFrame();
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
        draw = new JLabel(icon);

        draw.addMouseListener(this);
        draw.addMouseMotionListener(this);

        frame.setContentPane(draw);
        frame.addKeyListener(this);    // JLabel cannot get keyboard focus
        frame.setResizable(false);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
        frame.setTitle(name);
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);

     //   generalColorValue = getColorParameter(Skin.GENERAL_TEXT_COLOR); 
        //controlColorValue = getColorParameter(Skin.CONTROL_BACKGROUND_COLOR); 
        //trimColorValue = getColorParameter(Skin.TRIM_COLOR); 
        
        this.paintComponent(onscreen);
  
	}
    public void paintComponent (Graphics2D g) {
    	int recX       = 40;
    	int recY       = 40;
    	int recY_space = 60;
        
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(BLACK);
        //g2d.drawRect(20, 20, 400, 100);
        //g2.drawLine(40, 40, 80, 100);
        
        //Color trimColor = new Color(trimColorValue);
       // Color controlColor = new Color(controlColorValue);
        g2d.setColor(YELLOW);
        
        for(int i=0; i<this.BOARD_SIZE; i++)
        {
        	//GradientPaint gragient = new GradientPaint(40, 40, BLACK, 130, 130, YELLOW);
        	//g2d.setPaint(gragient);
        	g2d.fill(new Rectangle2D.Float(recX, recY+(i*recY_space), 300, 50));
        }        
    }

	private JMenuBar createMenuBar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setPenColor() {
		// TODO Auto-generated method stub
		
	}
	private void clear() {
		// TODO Auto-generated method stub
		
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
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouseClicked");
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouseEntered");
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouseExited");
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mousePressed");
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("mouseReleased");
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
	}
}
