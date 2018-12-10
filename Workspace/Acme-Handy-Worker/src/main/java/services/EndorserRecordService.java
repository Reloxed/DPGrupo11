
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	//Managed repository-----------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	//Supporting services ----------
	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Constructor ----------------------------------------------------

	public EndorserRecordService() {
		super();
	}

	//Simple CRUD methods-------
	public EndorserRecord create() {
		EndorserRecord result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new EndorserRecord();
		Assert.notNull(result);

		return result;

	}
	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;
		result = this.endorserRecordRepository.findAll();
		return result;

	}

	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(endorserRecordId);
		//Assert.isNull(result);
		return result;

	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {

		Assert.notNull(endorserRecord);
		EndorserRecord result;
		HandyWorker principal;
		Collection<EndorserRecord> endorsersRecord;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.notNull(endorserRecord.getFullName());
		Assert.notNull(endorserRecord.getEmail());
		Assert.notNull(endorserRecord.getLinkedinLink());

		Assert.isTrue(endorserRecord.getLinkedinLink().startsWith("https://www.linkedin.com/"));

		Assert.notNull(endorserRecord.getPhoneNumber());
		endorsersRecord = principal.getCurriculum().getEndorserRecords();
		result = this.endorserRecordRepository.saveAndFlush(endorserRecord);
		Assert.notNull(result);

		endorsersRecord.add(result);
		principal.getCurriculum().setEndorserRecords(endorsersRecord);

		return result;

	}

	public void delete(final EndorserRecord endorserRecord) {

		HandyWorker principal;
		Collection<EndorserRecord> collectionEndorserRecords;

		Assert.notNull(endorserRecord);
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		collectionEndorserRecords = principal.getCurriculum().getEndorserRecords();

		collectionEndorserRecords.remove(endorserRecord);

		this.endorserRecordRepository.delete(endorserRecord);

		principal.getCurriculum().setEndorserRecords(collectionEndorserRecords);

	}

	//Other business methods--------

	public Collection<EndorserRecord> findByPrincipal() {
		HandyWorker principal;
		Collection<EndorserRecord> res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = principal.getCurriculum().getEndorserRecords();
		Assert.notNull(res);

		return res;
	}

}
