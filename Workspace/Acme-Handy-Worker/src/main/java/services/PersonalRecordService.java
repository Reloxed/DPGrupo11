package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalRecord;

import repositories.PersonalRecordRepository;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private PersonalRecordRepository personalRecordRepository;

	// Supporting services -----------------------------------

	/* NONE REQUIRED */

	// Simple CRUD methods -----------------------------------

	public PersonalRecord create() {
		return new PersonalRecord();
	}
}
