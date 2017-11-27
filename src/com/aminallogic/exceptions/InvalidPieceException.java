package com.aminallogic.exceptions;

public class InvalidPieceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidPieceException()
	{
		super();
	}
	public InvalidPieceException(String message)
	{
		super(message);
	}
}