package com.aminallogic.objects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

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
	private Color myColor = Color.PINK;
	
	public static int relativeX = 10, relativeY = 10;
	public static int      recH = 30,     recW = 30;
	public int       recX_Space = 4*relativeX;
	
	public static boolean moving       = false;
	public static Piece   selectedPiece= null;
	
	Piece(Type t, Color c, int pos, Graphics2D onS, Graphics2D offS)
	{
		super();
		myType  = t;
		myColor = c;
		myBoardPosition = pos;
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
					break;
				case "G":
				case "g":
					tempT = Type.GIRAFFE;	
					break;
				case "L":
				case "l":
					tempT = Type.LION;
				case "H":
				case "h":
					tempT = Type.HIPPO;
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
		}
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
	@Override
	public String toString()
	{
		return "Piece Type: "+this.myType+" Color: "+ this.myColor+" Position: "+getBoardPostiion()+" "+((getBoardPostiion()-1)/4)
				+" "+ this.getLocation()+"\n";
	}
	public void draw()
	{
		int myX = relativeX+getMyQueue().x + (this.getQueuePostiion()*this.recX_Space);
		int myY = relativeY+getMyQueue().y;
		
		this.setLocation(myX,myY);
		
		this.setSize(recW, recH);
		onscreen.setColor(myColor);
		onscreen.fill(this);
		//System.out.println("relativeX ="+relativeX +" relativeY ="+relativeY +" Point ="+getMyQueue().x);
		//System.out.println("P: x="+ myX+" y ="+myY);
	}
	public void processMousePress(MouseEvent e)
	{
		if (this.contains(e.getPoint()))
		{
			Piece.moving = true;
			System.out.println("Clicked a piece!!"+ toString());
			Piece.selectedPiece = this;
		}
	}
}
