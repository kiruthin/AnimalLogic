package com.aminallogic.objects;
import com.aminallogic.enums.*;
import com.aminallogic.exceptions.InvalidPieceException;

public class Piece {
/*
 * Class for the pieces on the board.
 * 
 */
	Type myType         = null;
	Color myColor       = null;
	int myBoardPosition = -1;
	
	Piece(Type t, Color c, int pos)
	{
		myType  = t;
		myColor = c;
		myBoardPosition = pos;
	}
	
	Piece(String cb) //
	{
/*
 * 		The input format has to be 3 characters with the format e.g CB1 
 */
		if(cb.length() < 3)
		{
			throw new InvalidPieceException("The coded format is in the wrong format. Length is not equal to 3");
		}
		else
		{
			Color tempC = null;
			Type  tempT = null;
			
			char t = cb.charAt(0);
			char c = cb.charAt(1);
			char p = cb.charAt(2);
			
			switch(t)
			{
				case 'C':
				case 'c':
					tempT = Type.CAMEL;
					break;
				case 'G':
				case 'g':
					tempT = Type.GIRAFFE;	
					break;
				case 'L':
				case 'l':
					tempT = Type.LION;
				case 'H':
				case 'h':
					tempT = Type.HIPPO;
					break;
				default:
					throw new InvalidPieceException("The Type format is unsupported. Valid values are G, C, H and L");
			}
			switch(c)
			{
				case 'B':
				case 'b':
					tempC = Color.BLUE;
					break;
				case 'R':
				case 'r':
					tempC = Color.RED;	
					break;
				case 'Y':
				case 'y':
					tempC = Color.YELLOW;
				case 'G':
				case 'g':
					tempC = Color.GREEN;
					break;
				default:
					throw new InvalidPieceException("The coded format is unsupported. Valid values are B, R, Y and G");
			}
			myType =tempT;
			myColor = tempC;
			myBoardPosition = Integer.parseInt(""+p);
		}
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
	public void draw()
	{
		System.out.println("Draw the piece");
	};
	@Override
	public String toString()
	{
		return "Piece Type: "+this.myType+" Color: "+ this.myColor+"\n";
	}
}
