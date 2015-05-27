package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class Knight extends Piece {
	
	public Knight(String color) {
		this.color = color;
		this.type = "N";
		this.hasMoved = false;
	}

	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		
		if (Math.abs(getRow(start)-getRow(end)) + Math.abs((getColumn(start)-getColumn(end))) == 3) {
			return new MoveResponse(true, "move");
		}
		
		return new MoveResponse(false, null);
	}

}
