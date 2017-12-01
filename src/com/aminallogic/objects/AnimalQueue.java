package com.aminallogic.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Stack;


public class AnimalQueue extends Rectangle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Graphics2D onscreen, offscreen = null;
	Stack<Piece> myStack = new Stack<Piece>();
	int width      = 300, height     = 50;
	
	static int recX = 40, recY = 200, recY_space = 60;
	java.awt.Color myColor = Board.BLACK;
	
	AnimalQueue(int x, int y, Graphics2D onS, Graphics2D offS)
	{
		this.offscreen = offS;
		this.onscreen  = onS;
    	this.setLocation(x,y);
		this.setSize(new Dimension(width, height));
	}
	public void addPiece(Piece p)
	{
		myStack.push(p);
	}
	public void addPiece(Piece p, int ix)
	{
		myStack.add(ix, p);
	}
	public Piece removePiece()
	{
		return (Piece)myStack.pop();
	}
	public void setColor(java.awt.Color c)
	{
		this.myColor = c;
	}
	public void draw()
	{
		onscreen.setColor(myColor);
		onscreen.fill(this);
		for (int i=0; i<this.myStack.size(); i++)
		{
			//System.out.println("Color ="+ myColor);
			//System.out.println("Painting "+ myStack.elementAt(i));
			myStack.elementAt(i).draw();
		}
	}
	public void processMousePress(MouseEvent e)
	{	
		myStack.forEach(item->{item.processMousePress(e);});
		draw();
	}
}
