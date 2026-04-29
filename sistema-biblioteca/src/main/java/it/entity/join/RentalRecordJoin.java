package it.entity.join;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;

import it.entity.Edition;

/**
 * Entità che rappresenta un record di noleggio nel sistema.
 */
public class RentalRecordJoin {

	private int rentalId;
	private Edition editionJoin;
	private LocalDate rentalDate;
	private LocalDate rentalExpired;
	private LocalDate rentalEnded;
	
	public RentalRecordJoin() {
		
	}
	
    public RentalRecordJoin(
		int rentalId,
		Edition editionJoin,
		LocalDate rentalDate,
		LocalDate rentalExpired,
		LocalDate rentalEnded
	) {
		this.rentalId = rentalId;
		this.editionJoin = editionJoin;				
		this.rentalDate = rentalDate;
		this.rentalExpired = rentalExpired;
		this.rentalEnded = rentalEnded;
	}




	public int getRentalId() {
		return rentalId;
	}

	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}

	public Edition getEditionJoin() {
		return editionJoin;
	}

	public void setEditionJoin(Edition editionJoin) {
		this.editionJoin = editionJoin;
	}

	public LocalDate getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}

	public LocalDate getRentalExpired() {
		return rentalExpired;
	}

	public void setRentalExpired(LocalDate rentalExpired) {
		this.rentalExpired = rentalExpired;
	}

	public LocalDate getRentalEnded() {
		return rentalEnded;
	}

	public void setRentalEnded(LocalDate rentalEnded) {
		this.rentalEnded = rentalEnded;
	}

	@Override
	public String toString() {
		return "RentalRecordJoin [rentalId=" + rentalId + ", editionJoin=" + editionJoin + ", rentalDate="
				+ rentalDate + ", rentalExpired=" + rentalExpired + ", rentalEnded=" + rentalEnded + "]";
	}

	
}

