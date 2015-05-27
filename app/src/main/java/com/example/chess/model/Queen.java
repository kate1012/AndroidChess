package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class Queen extends Piece {
	
	public Queen(String color) {
		this.color = color;
		this.type = "Q";
		this.hasMoved = false;
	}

	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		Bishop b = new Bishop(board.getWhoseTurn());
		Rook r = new Rook(board.getWhoseTurn());
		
		if (b.isMoveValid(board, start, end).valid || r.isMoveValid(board, start, end).valid) {
			return new MoveResponse(true, "move");
		}
		
		return new MoveResponse(false, null);
	}

}
