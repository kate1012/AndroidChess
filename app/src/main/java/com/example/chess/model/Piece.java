package com.example.chess.model;

import java.io.Serializable;

/**Interface for chess pieces
 * @author Ben Green & Kate Sussman
 *
 */
public abstract class Piece implements Serializable{
	
	protected String type;
	protected String color;
	protected boolean hasMoved;
    private static final long serialVersionUID = 0L;


    /**Getter for the piece's type (rook, knight, etc.)
	 * @return Piece's type
	 */
	public String getType() {
		return type;
	}
	
	/**Getter for piece's color (black, white)
	 * @return Piece's color
	 */
	public String getColor() {
		return color;
	}

	public void markMoved() {
		hasMoved = true;
	}

	/**Check if move is valid for piece (not including check rules)
	 * @return MoveResponse.valid plus a special message for castling/en passant if necessary
	 */
	public abstract MoveResponse isMoveValid(Board board, Integer start, Integer end);
	
	public static Integer getRow(int location) {
		if (location % 8 == 0) {
			return location / 8;
		} else {
			return ((location - (location % 8)) / 8) + 1;
		}
	}
	
	public static Integer getColumn(int location) {
		if (location % 8 == 0) {
			return 8;
		} else {
			return location % 8;
		}
	}


}
