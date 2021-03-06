package com.aminallogic.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


public class AnimalQueue extends LogicQueue{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Graphics2D onscreen, offscreen = null;
	public static int PieceScale   = 3;
	LinkedList<Piece> myQueue = new LinkedList<Piece>();
	int width      = 300, height     = 50;
	//recX = 40, recY = 200, 
	
    static int recY_space = 60;
	java.awt.Color myColor = Board.BLACK;
	
	AnimalQueue(int x, int y, Graphics2D onS, Graphics2D offS)
	{
		super(x,y,onS,offS);

		this.offscreen = offS;
		this.onscreen  = onS;
    	this.setLocation(x,y);
		this.setSize(new Dimension(width, height));
		this.setLocation(x,y);
	}
	public void addPiece(Piece p)
	{
		myQueue.add(p);
	}
	public void addPiece(Piece p, int ix)
	{
		myQueue.add(ix, p);
	}
	public Optional<Piece> removePiece()
	{
		if (!myQueue.isEmpty())
		{
			return Optional.of(this.myQueue.remove());
		}
		else
		{
			return Optional.empty();
		}
	}
	public Optional<Piece> getTop()
	{
		if (!myQueue.isEmpty())
		{
			return Optional.of(this.myQueue.peek());
		}
		else
		{
			return Optional.empty();
		}
	}
	public void setColor(java.awt.Color c)
	{
		this.myColor = c;
	}
	public void draw()
	{
		onscreen.setColor(myColor);
		onscreen.fill(this);
		final AtomicInteger i = new AtomicInteger(0);
		myQueue.forEach(item->{
			//System.out.println("i ="+ i.get());
			//System.out.println("Painting "+ myStack.elementAt(i));
			int myX = Piece.getRelativeX()+this.x + (i.getAndIncrement()*(item.getRecX_Space()*item.getScale()));
			int myY = Piece.getRelativeY()+this.y;
			item.setLocation(myX, myY);
			item.draw();
			//i.incrementAndGet();
		});
	}
	public Optional<Piece> processMousePress(MouseEvent e)
	{	
	/*
		final Piece tempP;
		myStack.forEach(item->{
			if (item.processMousePress(e))
			{
			    tempP = this.myStack.pop();
			//	return;
			}
		})
		;
	*/	
		return  myQueue.stream()
			.filter(s->s.processMousePress(e)== true)
			.findFirst();                     //Only one item matches 

	/*
		if (this.contains(e.getPoint()) && !myQueue.isEmpty())
		{
			System.out.println("clicked !");
			return Optional.of(this.myQueue.peekFirst());
		}
		else
		{
			return Optional.empty();
		}
	*/
	}
}
