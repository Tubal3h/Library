package it.repository;

import java.util.List;

import it.entity.RentalRecord;
import it.entity.RentalRecordJoin;

public interface RentRecordRepositoryInterface {
	public List<RentalRecord> getAllRents();
	public int countRents();
	public int countRentsByUserId(int userId);
	public List<RentalRecordJoin> getActiveRents();
	public List<RentalRecordJoin> getActiveRentsByUserId(int userId);
	public void createRental(RentalRecord rental);
	public void endRental(int bookId, int rentId);
}
