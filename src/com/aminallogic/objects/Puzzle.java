package com.aminallogic.objects;

import com.aminallogic.enums.Level;

public class Puzzle {
	private static int PUZZLE_NUM = 0;
	private int num = 0 ;
	private int mylevel = 0;
	Level difficulty = null;
	Puzzle (String s, int lvl)
	{
		num = PUZZLE_NUM++;
		setMylevel(lvl);
		switch(lvl)
		{
			case 1-2:
				difficulty = Level.EASY;
				break;
			case 3:
				difficulty = Level.MEDIUM;
				break;
			case 4:
				difficulty = Level.HARD;
				break;
			case 5:
				difficulty = Level.PRO;
				break;
			default:
				break;
		}
	}
	public String toString()
	{
		String returnMe = "";
		returnMe = "Puzzle #"+ num +" "+ difficulty;
		return returnMe;
	}
	public int getMylevel() {
		return mylevel;
	}
	public void setMylevel(int mylevel) {
		this.mylevel = mylevel;
	}
}
