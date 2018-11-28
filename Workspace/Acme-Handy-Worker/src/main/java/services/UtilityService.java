
package services;

import java.security.SecureRandom;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Curriculum;
import domain.FixUpTask;

@Service
@Transactional
public class UtilityService {

	// Supporting Services ------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CurriculumService	curriculumService;
	
	// Utility methods ----------------------------------------
	
	public String generateTicker() {
		String uniqueTicker = null;
		Calendar date;
		String year, month, day, alphaNum;
		boolean unique = false;

		date = Calendar.getInstance();
		date.setTime(LocalDate.now().toDate());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH) + 1);
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));

		while(unique == false){
			alphaNum = this.randomString();
			uniqueTicker = year + month + day + "-" + alphaNum;
			for(FixUpTask fixUpTask : fixUpTaskService.findAll()) {
				if(fixUpTask.getTicker().equals(uniqueTicker))
					continue;
			}
			for(Curriculum curriculum : curriculumService.findAll()){
				if(curriculum.getTicker().equals(uniqueTicker))
					continue;
			}
			unique = true;
		}	
		return uniqueTicker;
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
