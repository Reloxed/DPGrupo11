
package services;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;

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

	public Complaint create() {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		result = new Complaint();

		result.setTicker(this.generateTicker(result));
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public Complaint save(final Complaint c) {
		Complaint result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(c.getFixUpTask());
		Assert.notNull(c.getDescription());

		result = this.complaintRepository.save(c);

		return result;
	}

	// Other business methods --------------------------------
	public String generateTicker(final Complaint c) {
		final String result;
		Calendar date;
		String year, month, day, alphaNum;

		date = Calendar.getInstance();
		date.setTime(c.getMoment());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH));
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

		alphaNum = this.randomString();
		result = year + month + day + "-" + alphaNum;

		return result;
	}

	public String randomString() {

		final String possibleChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final SecureRandom rnd = new SecureRandom();
		final int length = 6;

		final StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++)
			stringBuilder.append(possibleChars.charAt(rnd.nextInt(possibleChars.length())));
		return stringBuilder.toString();

	}
}
