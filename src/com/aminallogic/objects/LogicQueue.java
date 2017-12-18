package com.aminallogic.objects;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Optional;

public abstract class LogicQueue extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
    static int recY_space = 60;
	java.awt.Color myColor = Board.BLACK;
	
	LogicQueue(int x, int y, Graphics2D onS, Graphics2D offS)
	{
		this.setLocation(x,y);
		this.setSize(new Dimension(width, height));
		this.setLocation(x,y);
	}
	abstract public void addPiece(Piece p);
	abstract public void addPiece(Piece p, int ix);
	abstract public Optional <Piece> removePiece();
	abstract public void setColor(java.awt.Color c);
	abstract public void draw();

}
