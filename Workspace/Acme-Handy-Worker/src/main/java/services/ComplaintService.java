
package services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ComplaintRepository;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	// Managed repository ------------------------------------
	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services -----------------------------------
	@Autowired
	private CustomerService		customerService;


	// CRUD methods -----------------------------------------

	/*
	 * public Complaint create() {
	 * final Complaint result;
	 * final Customer customer;
	 * 
	 * customer = this.customerService.
	 * 
	 * }
	 */

	// Other business methods --------------------------------
	public String generateTicker(final Complaint c) {
		final String result;
		Calendar date;
		String year;
		final String month, day;

		date = Calendar.getInstance();
		date.setTime(c.getMoment());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH));
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

		//TODO: faltan los caracteres alphanumericos aleatorios
		result = null;

		return result;
	}
}
