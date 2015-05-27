package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class Rook extends Piece {
	
	public Rook(String color) {
		this.color = color;
		this.type = "R";
		this.hasMoved = false;
	}

	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		
		//vertical
		if (Math.abs(end-start) % 8 == 0) {
			
			int direction = end.compareTo(start);
			for (int x = start + direction * 8; x != end; x += direction * 8) {
				if (board.hasPieceAt(x)) {
					return new MoveResponse(false, null);
				}
			}
			
			return new MoveResponse(true, "move");
			
		}
		
		//horizontal
		if (Math.abs(end-start) < 8 && (start-1) / 8 == (end-1) / 8) {
			
			int direction = end.compareTo(start);
			for (int x = start + direction; x != end; x += direction) {
				if (board.hasPieceAt(x)) {
					return new MoveResponse(false, null);
				}
			}
			
			return new MoveResponse(true, "move");
			
		}
		
		return new MoveResponse(false, null);
	}

}
