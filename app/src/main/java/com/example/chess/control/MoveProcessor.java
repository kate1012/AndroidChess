/**
 * 
 */
package com.example.chess.control;


import com.example.chess.model.Board;
import com.example.chess.model.MoveResponse;
import com.example.chess.model.Piece;

import java.util.StringTokenizer;

/**
 * @author Ben Green & Kate Sussman
 *
 */
public class MoveProcessor {

	public static boolean processMove(Board board, Integer start, Integer end, String option) {

        System.out.println("processing move " + start + " to " + end);
		
		//verify that a piece exists on the specified space that belongs to the player
		Piece piece = board.getPieceAt(start);
		
		//check that the piece exists
		if (piece == null) {
			System.err.println("tried to move an empty square");
			return false;
		
		//check that the piece belongs to the current player
		} else if (!piece.getColor().equals(board.getWhoseTurn())) {
			System.err.println("tried to move an enemy piece");
			return false;
		}
		
		//make sure piece isn't trying to 'capture' a friend
		if (board.hasPieceAt(end)) {
			if (board.getPieceAt(end).getColor().equals(board.getWhoseTurn())) {
				System.err.println("tried to capture a friend");
				return false;
			}
		}
		
		MoveResponse response = piece.isMoveValid(board, start, end);
	
		
		//analyze move further with helper method
		if (!response.valid) {
			System.err.println("move violates piece rules");
			return false;
		}
		
		//make sure move doesn't violate check rules
		if (!obeysCheck(board, start, end)) {
			System.err.println("move results in self-check");
			return false;
		}
		
		
		//handle move
		
		Board.draw = false;
		
		switch(response.special) {
		
		case("castle"):
			board.movePiece(start, end);
			if (start > end) {
				board.movePiece(end - 2, end + 1);
			} else {
				board.movePiece(end + 1, end - 1);
			}
			break;
		
		case("promotion"):
			board.movePiece(start, end);

			if (option != null) {
                if ("RNBQ".indexOf(option.charAt(0)) != -1) {
                    board.replacePiece(board.getWhoseTurn(), option, end);
                    break;
                }
            }
            board.replacePiece(board.getWhoseTurn(), "Q", end);
			break;

        case("en passant"):
            board.movePiece(start,end);
            if (board.getWhoseTurn().equals("w")) {
                board.removePiece(end - 8);
            } else {
                board.removePiece(end + 8);
            }
            break;
		
		default:
			board.movePiece(start, end);
            System.out.println("successful move from " + start + " to " + end);
			break;
		}
		
		//check if player wants to draw
		if(option != null && option.equals("draw?"))
			Board.draw = true;
		return true;
		
	}
	
	
	public static boolean obeysCheck(Board board, Integer start, Integer end) {
		Board alt_board = board.duplicate();
		alt_board.movePiece(start, end);
		
		//find enemy pieces and check all their moves
		//TODO not brute force this
		for (int loc = 1; loc < 65; loc++) {
			if (alt_board.hasPieceAt(loc)) {
				if (!alt_board.getPieceAt(loc).getColor().equals(alt_board.getWhoseTurn())) {
					for (int target = 1; target < 65; target++) {
						if (alt_board.hasPieceAt(target)) {
							Piece piece = alt_board.getPieceAt(target);
							if (piece.getColor().concat(piece.getType()).equals(board.getWhoseTurn().concat("K"))) {
								if (alt_board.getPieceAt(loc).isMoveValid(alt_board, loc, target).valid) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

    public static Integer[] findMove(Board board) {
        for (int loc = 1; loc < 65; loc++) {
            if (board.hasPieceAt(loc)) {
                if (board.getPieceAt(loc).getColor().equals(board.getWhoseTurn())) {
                    for (int target = 1; target < 65; target++) {
                        if (board.getPieceAt(loc).isMoveValid(board, loc, target).valid) {
                            if (obeysCheck(board, loc, target)) {
                                if (board.hasPieceAt(target)) {
                                    if (!board.getPieceAt(target).getColor().equals(board.getWhoseTurn())) {
                                        return new Integer[]{loc, target};
                                    }
                                } else {
                                    return new Integer[]{loc, target};
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }



    /**checks for checkmate, stalemate, check
	 * @param board Board to inspect
	 * @return MoveResponse.valid will be true if player has a valid move
	 */
	public static MoveResponse checkMate(Board board) {
		
		boolean in_check = false;
		boolean has_valid_move = false;
		int king_loc = board.findKing(board.getWhoseTurn());
		
		//identify check on player's king
		for (int loc = 1; loc < 65; loc++) {
			if (board.hasPieceAt(loc)) {
				//iterate through enemy piece and look for a valid attack
				if (!board.getPieceAt(loc).getColor().equals(board.getWhoseTurn())) {
					if (board.getPieceAt(loc).isMoveValid(board, loc, king_loc).valid) {
						in_check = true;
					}
				}
			}
		}
		
		//check if player has a valid move by iterating through pieces and checking moves
		for (int loc = 1; loc < 65; loc++) {
			if (board.hasPieceAt(loc)) {
				if (board.getPieceAt(loc).getColor().equals(board.getWhoseTurn())) {
					for (int target = 1; target < 65; target++) {
						if (board.getPieceAt(loc).isMoveValid(board, loc, target).valid) {
							if (obeysCheck(board, loc, target)) {
								if (board.hasPieceAt(target)) {
									if (!board.getPieceAt(target).getColor().equals(board.getWhoseTurn())) {
										has_valid_move = true;
									}
								} else {

									has_valid_move = true;
								}
								
							}
						}
					}
				}
			}
		}
		
		
		if (!has_valid_move) {
			
			if (in_check) {
				return new MoveResponse(false, "checkmate");
			} else {
				return new MoveResponse(false, "stalemate");
			}
			
		} else {
		
			if (in_check) {
				return new MoveResponse(true, "check");
			} else {
				return new MoveResponse(true, "normal");
			}
		}
		
	}

}
