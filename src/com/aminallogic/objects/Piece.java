package com.aminallogic.objects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aminallogic.enums.*;
import com.aminallogic.exceptions.InvalidPieceException;

public class Piece extends Rectangle{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*
 * Class for the pieces on the board.
 * 
 */
	private Graphics2D offscreen, onscreen;
	Type myType           = null;
	AnimalQueue myQueue   = null;
	int myBoardPosition   = -1;
	int myQueuePosition   = -1;
	private java.awt.Color myColor = Color.PINK;
	private boolean iammoving = false;
	
	BufferedImage img = null;

	public static int relativeX = 10, relativeY = 10;
	public static int      recH = 10,     recW = 10;
	private int Scale           = 3;
	public int       recX_Space = relativeX+3;
	
	private int imgW =0, imgH = 0, imgScale =1;
	
	public static boolean moving       = false;
	public static Piece   selectedPiece= null;
	
	Piece(Type t, Color c, int pos, Graphics2D onS, Graphics2D offS)
	{
		super();
		myType  = t;
		myColor = c;
		myBoardPosition = pos;
		initPiece();
	}
	
	Piece(String cb, Graphics2D onS, Graphics2D offS) //
	{
/*
 * 		The input format has to be 3 characters with the format e.g CB1 
 */
		super();
		this.offscreen = offS;
		this.onscreen  = onS;
		if(cb.length() < 3)
		{
			throw new InvalidPieceException("The coded format is in the wrong format. Length is less than 3");
		}
		else
		{
			Type  tempT = null;
			
			String t = cb.substring(0, 1);
			String c = cb.substring(1, 2);
			String p = cb.substring(2, cb.length());
			
			switch(t)
			{
				case "C":
				case "c":
					tempT = Type.CAMEL;
					try 
					{
					    img = ImageIO.read(new File("/home/knjenga/Desktop/camel.png"));
				        imgW = img.getWidth(null)+150;
				        imgH = img.getHeight(null)+175;
				        imgScale = 50;
					} 
					catch (IOException e) {
						System.out.print(e);
					}
					break;
				case "G":
				case "g":
					tempT = Type.GIRAFFE;	
					try 
					{
					    img = ImageIO.read(new File("/home/knjenga/Desktop/giraffe.png"));
				
				        imgW = img.getWidth(null);
				        imgH = (img.getHeight(null));
				        imgScale = 43;
					} 
					catch (IOException e) {
						System.out.print(e);
					}
					break;
				case "L":
				case "l":
					tempT = Type.LION;
					try 
					{
					    img = ImageIO.read(new File("/home/knjenga/Desktop/lion.png"));
				
				        imgW = img.getWidth(null);
				        imgH = (img.getHeight(null));
				        imgScale = 100;
					} 
					catch (IOException e) {
						System.out.print(e);
					}

					break;
				case "H":
				case "h":
					tempT = Type.HIPPO;
					try 
					{
					    img = ImageIO.read(new File("/home/knjenga/Desktop/hippo.png"));
				
				        imgW = img.getWidth(null);
				        imgH = (img.getHeight(null))+200;
				        imgScale = 70;
					} 
					catch (IOException e) {
						System.out.print(e);
					}
					break;
				default:
					throw new InvalidPieceException("The Type format is unsupported. Valid values are G, C, H and L");
			}
			switch(c)
			{
				case "B":
				case "b":	
					myColor = Color.BLUE;
					break;
				case "R":
				case "r":
					myColor = Color.RED;	
					break;
				case "Y":
				case "y":
					myColor = Color.YELLOW;
					break;
				case "G":
				case "g":
					myColor = Color.GREEN;
					break;
				default:
					throw new InvalidPieceException("The coded format is unsupported. Valid values are B, R, Y and G");
			}
			myType =tempT;
			myBoardPosition = Integer.parseInt(""+p);
			initPiece();
		}
	}

    private BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                
                if (pixels[0] == 255 && pixels[1] == 255 && pixels[2] == 255)
                {
                	pixels[0] = 0;
                	pixels[1] = 0;
                	pixels[2] = 0;
                }
                else
                {
                	pixels[0] = myColor.getRed();
                	pixels[1] = myColor.getGreen();
                	pixels[2] = myColor.getBlue();
                }
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
	public void initPiece()
	{
		this.setSize(recW*Scale, recH*Scale);
		if(img != null)
	    img = colorImage(img);
	}
	public void setMyQueue(AnimalQueue aq)
	{
		this.myQueue = aq;
	}
	public AnimalQueue getMyQueue()
	{
		return myQueue;
	}
	public Type getType()
	{
		return this.myType;
	};
	public Color getColor()
	{
		return this.myColor;
	};
	public int getBoardPostiion()
	{
		return this.myBoardPosition;
	};
	public int getQueuePostiion()
	{
		return this.myQueuePosition;
	};
	public void setQueuePostiion(int qp)
	{
		this.myQueuePosition = qp;
	};
	public void setScale(int s)
	{
		this.Scale = s;
	}
	public int getScale()
	{
		return this.Scale;
	}
	@Override
	public String toString()
	{
		return "Piece Type: "+this.myType+" Color: "+ this.myColor+" Position: "+getBoardPostiion()+" "+((getBoardPostiion()-1)/4)
				+" "+ this.getLocation()+"\n";
	}
	public void draw()
	{
		if(!IsMoving())
		{
	//		int myX = relativeX+getMyQueue().x + (this.getQueuePostiion()*this.recX_Space);
	//		int myY = relativeY+getMyQueue().y;
	//		this.setLocation(myX,myY);		
		}
		//BufferedImageOp op = null;
		//onscreen.drawImage(img, op, this.recW, this.recH);
        
		
		//onscreen.setColor(myColor);
		//onscreen.fill(this);
		this.setSize(Piece.recW*Scale, Piece.recH*Scale);
        onscreen.drawImage(img, x+1, y, (imgW/imgScale)*Scale, (imgH/imgScale)*Scale, null);
	}
	public void setMoving(boolean t)
	{
		this.iammoving = t;
	}
	public boolean IsMoving()
	{
		return this.iammoving;
	}
	public boolean processMousePress(MouseEvent e)
	{
		if (this.contains(e.getPoint()))
		{
			this.setMoving(true);
			System.out.println("Clicked a piece!!"+ toString());
			Piece.selectedPiece = this;
			Piece.moving = true;
		}
		else
		{
			this.setMoving(false);
		}
		return IsMoving();
	}

	public static int getRelativeX() {
		return relativeX;
	}

	public static void setRelativeX(int relativeX) {
		Piece.relativeX = relativeX;
	}

	public static int getRelativeY() {
		return relativeY;
	}

	public static void setRelativeY(int relativeY) {
		Piece.relativeY = relativeY;
	}

	public int getRecX_Space() {
		return recX_Space;
	}

	public void setRecX_Space(int recX_Space) {
		this.recX_Space = recX_Space;
	}
	
}
