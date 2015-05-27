package com.example.chess.model;


import com.example.chess.control.MoveProcessor;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class King extends Piece {
	
	public King(String color) {
		this.color = color;
		this.type = "K";
		this.hasMoved = false;
	}

	@Override
	public MoveResponse isMoveValid(Board board, Integer start, Integer end) {
		
		if (Math.abs(getRow(start)-getRow(end)) < 2 && Math.abs(getColumn(start)-getColumn(end)) < 2) {
			return new MoveResponse(true, "move");
		}
		
		//castling
		int horiz_diff = end.compareTo(start);
		
		if (end == start + 2 || end == start - 2) {
			if (!this.hasMoved) {
				
				for (int loc = start + horiz_diff; loc != end; loc += horiz_diff) {
					if (board.hasPieceAt(loc) || !MoveProcessor.obeysCheck(board, start, end)) {
						return new MoveResponse(false, null);
					}
				}
				
				//check that rook hasn't moved
				if (horiz_diff == 1) {
					if (board.hasPieceAt(end + 1)) {
						if (board.getPieceAt(end + 1).getColor().equals(board.getWhoseTurn()) && !board.getPieceAt(end + 1).hasMoved) {
							return new MoveResponse(true, "castle");
						}
						return new MoveResponse(false, null);
					}
				} else {
					if (board.getPieceAt(end - 2).getColor().equals(board.getWhoseTurn()) && !board.getPieceAt(end - 2).hasMoved) {
						return new MoveResponse(true, "castle");
					}
					return new MoveResponse(false, null);
				}
			}
		}
		return new MoveResponse(false, null);
	}

}
