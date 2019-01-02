
package services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
	private FixUpTaskService			fixUpTaskService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public UtilityService() {
		super();
	}

	// Utility methods ----------------------------------------

	public String generateTicker() {
		String uniqueTicker = null;
		Calendar date;
		String year, month, day, alphaNum, todayDate;
		boolean unique = false;

		date = Calendar.getInstance();
		date.setTime(LocalDate.now().toDate());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH) + 1);
		if (month.length()== 1){
			month = "0" + month;
		}
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		if (day.length()== 1){
			day = "0" + day;
		}

		while (unique == false) {
			alphaNum = this.randomString();
			todayDate = year + month + day;
			uniqueTicker = todayDate + "-" + alphaNum;
			for (final FixUpTask fixUpTask : this.fixUpTaskService.findAll())
				if (fixUpTask.getTicker().equals(uniqueTicker))
					continue;
			for (final Curriculum curriculum : this.curriculumService.findAll())
				if (curriculum.getTicker().equals(uniqueTicker))
					continue;
			unique = true;
		}
		return uniqueTicker;
	}

	public String randomString() {

		final String possibleChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final SecureRandom rnd = new SecureRandom();
		final int length = 6;

		final StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++)
			stringBuilder.append(possibleChars.charAt(rnd.nextInt(possibleChars.length())));
		return stringBuilder.toString();

	}

	public List<String> getCreditCardMakes() {

		final String makes = this.systemConfigurationService.findMySystemConfiguration().getListCreditCardMakes();
		final List<String> listCCMakes = new ArrayList<String>(Arrays.asList(makes.split(",")));
		return listCCMakes;
	}

	public List<String> getNegativeWords() {

		final String makes = this.systemConfigurationService.findMySystemConfiguration().getNegativeWords();
		final List<String> listNegWords = new ArrayList<String>(Arrays.asList(makes.split(" , ")));
		return listNegWords;
	}

	public List<String> getPositiveWords() {

		final String makes = this.systemConfigurationService.findMySystemConfiguration().getPositiveWords();
		final List<String> listPosWords = new ArrayList<String>(Arrays.asList(makes.split(" , ")));
		return listPosWords;
	}

	public List<String> getSpamWords() {

		final String makes = this.systemConfigurationService.findMySystemConfiguration().getSpamWords();
		final List<String> listSpamWords = new ArrayList<String>(Arrays.asList(makes.split(" , ")));
		return listSpamWords;
	}
}
