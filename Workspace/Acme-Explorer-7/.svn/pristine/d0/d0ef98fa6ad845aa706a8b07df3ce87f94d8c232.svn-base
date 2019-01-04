
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.Ranger;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services

	@Autowired
	private RangerService				rangerService;


	// Constructors

	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<EndorserRecord> findByPrincipal() {
		Collection<EndorserRecord> result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCV().getEndorserRecord();

		Assert.notNull(result);

		return result;
	}

	public EndorserRecord create() {
		EndorserRecord result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = new EndorserRecord();
		result.setComments(new ArrayList<String>());
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		EndorserRecord result;
		Ranger principal;
		List<EndorserRecord> endorsersRecords;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCV());

		endorsersRecords = principal.getCV().getEndorserRecord();
		result = this.endorserRecordRepository.save(endorserRecord);
		Assert.notNull(result);
		if (endorserRecord.getId() == 0) {
			endorsersRecords.add(result);
			principal.getCV().setEndorserRecord(endorsersRecords);
		}
		return result;

	}

	public void delete(final EndorserRecord endorserRecord) {
		Ranger principal;
		List<EndorserRecord> listEndorserRecord;

		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		listEndorserRecord = principal.getCV().getEndorserRecord();

		this.endorserRecordRepository.delete(endorserRecord);
		listEndorserRecord.remove(endorserRecord);

		principal.getCV().setEndorserRecord(listEndorserRecord);

	}

	public EndorserRecord findOne(final int id) {
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(id);
		return result;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;
		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

}
