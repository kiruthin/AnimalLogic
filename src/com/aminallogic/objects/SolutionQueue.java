package com.aminallogic.objects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;


public class SolutionQueue extends LogicQueue{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public static int PieceScale   = 1;
	private Graphics2D onscreen, offscreen = null;
	Stack<Piece> myStack = new Stack<Piece>();
	int width      = 300, height     = 50;
	//recX = 40, recY = 200, 
	
    static int recY_space = 60;
	java.awt.Color myColor = Board.BLACK;
	
	SolutionQueue(int x, int y, Graphics2D onS, Graphics2D offS)
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
		int myX = Piece.getRelativeX()+this.x + (this.myStack.size()*p.recX_Space);
		int myY = Piece.getRelativeY()+this.y;
		p.setLocation(myX,myY);	
		p.setSize(Piece.recW*SolutionQueue.PieceScale, Piece.recH*SolutionQueue.PieceScale);
		p.setScale(SolutionQueue.PieceScale);
		p.draw();
		//System.out.println("Adding piece to Solution queue" + p);
		myStack.push(p);
	}
	public void addPiece(Piece p, int ix)
	{
		myStack.add(ix, p);
	}
	public Optional<Piece> removePiece()
	{
		if (!myStack.isEmpty())
		{
			return Optional.of(this.myStack.pop());
		}
		else
		{
			return Optional.empty();
		}
	}
	public boolean isValidMove(Piece p)
	{
		if (myStack.isEmpty())
		{
			return true;
		}
		else
		{
			Piece check = myStack.peek();
			
			if (p.getType() == check.getType()) //Same species
			{
				return true;
			}
			else								//Different species
			{
				return (p.getColor().getRGB() == check.getColor().getRGB());
			}
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
		/*
		myStack.forEach(item->{
			//System.out.println("Color ="+ myColor);
			System.out.println(" Drawing Solution Queue "+ item);
			item.draw();
		});
		*/	
		
		final AtomicInteger i = new AtomicInteger(0);
		myStack.forEach(item->{
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
		/*myStack.forEach(item->{
			if (item.processMousePress(e))
			{
				Piece tempP = this.myStack.pop();
				return;
			}
		})
		;*/
		//myStack.stream()
		//.filter(s->s.processMousePress(e)== true)
		//.forEach(Piece::sdraw);
		if (this.contains(e.getPoint()) && !myStack.isEmpty())
		{
			return Optional.of(this.myStack.pop());
		}
		else
		{
			return Optional.empty();
		}
	}
}
