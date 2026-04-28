package it.repository.interfaces;

import java.util.List;

import it.dto.response.BookRecordsJoinDtoResponse;
import it.entity.RentalRecord;
import it.entity.RentalRecordJoin;
import it.exception.HistoryNotFoundException;

public interface RentRecordRepositoryInterface {
	public List<RentalRecord> getAllRents();
	public int countRents();
	public int countRentsByUserId(int userId);
	public List<RentalRecordJoin> getActiveRents();
	public List<RentalRecordJoin> getActiveRentsByUserId(int userId);
	public void updateStatusToLend(int bookId);
	public void createRental(RentalRecord rental);
	public void endRental(int bookId, int rentId);
	public void updateRentalStatusOk(int bookId);
	public void updateRentalStatusNotOk(int bookId);
	public void deleteRentalById(int rentId);
	public List<BookRecordsJoinDtoResponse> getBookRecords(int bookId) throws HistoryNotFoundException;
	public List<BookRecordsJoinDtoResponse> getUserRecords(int userId) throws HistoryNotFoundException;
}
