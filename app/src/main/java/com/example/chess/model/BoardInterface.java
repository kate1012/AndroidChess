/**
 * 
 */
package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */
public interface BoardInterface {
	
	/**Add chess piece to board.
	 * @param color Piece's color
	 * @param type Piece's type
	 * @param location Place on board to put piece (a4, b2, etc.)
	 */
	public void addPiece(String color, String type, Integer location);
	
	/**Remove piece from board.
	 * @param location Location of piece to remove
	 */
	public void removePiece(Integer location);
	
	/**Move piece from one place to another on board
	 * @param start_location Current location of piece
	 * @param end_location Destination of piece
	 */
	public void movePiece(Integer start_location, Integer end_location);
	
	/**Checks if a piece exists at the specified location
	 * @param location Location to search for piece
	 * @return Piece at location or null if no piece found
	 */
	
	/**Replace piece with another new piece, used mainly for promotion
	 * @param color
	 * @param type
	 * @param location
	 */
	public void replacePiece(String color, String type, Integer location);
	
	/**
	 * @param location
	 * @return Piece at said location, or null if no piece exists
	 */
	public Piece getPieceAt(Integer location);
	
	/**Switches current player when a turn has ended
	 * 
	 */
	public void switchPlayer();
	
	/**Get board's status
	 * @return current status
	 */
	public String getStatus();
	
	/**Returns player who should make the next move
	 * @return White or Black
	 */
	public String getWhoseTurn();

}
