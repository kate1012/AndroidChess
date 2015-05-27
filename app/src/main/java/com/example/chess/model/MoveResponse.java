package com.example.chess.model;

/**
 * @author Ben Green & Kate Sussman
 *
 */

public class MoveResponse {
	
	public boolean valid;
	public String special;
	
	//special should be one of: move, capture, castle
	
	public MoveResponse(boolean valid, String special) {
		this.valid = valid;
		this.special = special;
	}

}
