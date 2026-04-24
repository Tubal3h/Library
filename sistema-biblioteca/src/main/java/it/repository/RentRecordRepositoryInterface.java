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
<<<<<<< HEAD
	public void updateStatus(int bookId);
=======
	public void updateStatusToLend(int bookId);
>>>>>>> 5088af4f7c092c17706c09e9dbd8f35149c5cbbb
	public void createRental(RentalRecord rental);
	public void endRental(int bookId, int rentId);
	public void updateRentalStatusOk(int bookId);
	public void updateRentalStatusNotOk(int bookId);
	public void deleteRentalById(int rentId);
}
