package com.aminalLogic;
import com.aminallogic.objects.Board;

public class PlayLogic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{
			Board b = new Board(4);
			System.out.println(b);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
