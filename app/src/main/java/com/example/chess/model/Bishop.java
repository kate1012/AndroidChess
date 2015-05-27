package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class Bishop extends Piece {
	
	public Bishop(String color) {
		this.color = color;
		this.type = "B";
		this.hasMoved = false;
	}

	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
				
		if (Math.abs(getRow(start)-getRow(end)) == Math.abs(getColumn(start)-getColumn(end))) {
			
			Integer vert_diff = getRow(end).compareTo(getRow(start));
			Integer horiz_diff = getColumn(end).compareTo(getColumn(start));
			
			for (int x = start + horiz_diff + vert_diff*8; x != end; x += horiz_diff + vert_diff*8) {
				if (board.hasPieceAt(x)) {
					return new MoveResponse(false, null);
				}
			}
			
			return new MoveResponse(true, "move");
		}
		
		return new MoveResponse(false, null);
	}

}
