package it.repository.interfaces;

import java.util.List;

import it.entity.RentalRecord;
import it.exception.HistoryNotFoundException;

public interface RentRecordRepositoryInterface {
	public List<RentalRecord> getAllRents();
	public int countRents();
	public int countRentsByUserId(int userId);
	public List<RentalRecord> getActiveRents();
	public List<RentalRecord> getActiveRentsByUserId(int userId);
	public void updateStatusToLend(int bookId);
	public void createRental(RentalRecord rental);
	public void endRental(int bookId, int rentId);
	public void updateRentalStatusOk(int bookId);
	public void updateRentalStatusNotOk(int bookId);
	public void deleteRentalById(int rentId);
	public List<RentalRecord> getBookRecords(int bookId) throws HistoryNotFoundException;
	public List<RentalRecord> getUserRecords(int userId) throws HistoryNotFoundException;
}
