
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

	@Autowired
	private UtilityService				utilityService;


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
		Assert.notNull(result);
		return result;

	}

	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.notNull(result);
		return result;

	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		EndorserRecord result;
		HandyWorker principal;
		Collection<EndorserRecord> endorserRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.notNull(endorserRecord.getFullName());
		Assert.notNull(endorserRecord.getEmail());
		Assert.notNull(endorserRecord.getLinkedinLink());

		Assert.isTrue(endorserRecord.getLinkedinLink().toLowerCase().startsWith("https://www.linkedin.com/") || endorserRecord.getLinkedinLink().toLowerCase().startsWith("http://www.linkedin.com"));

		Assert.notNull(endorserRecord.getPhoneNumber());

		final List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(endorserRecord.getFullName());
		if (endorserRecord.getComments() != null)
			atributosAComprobar.add(endorserRecord.getComments());

		final boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if (containsSpam)
			principal.setIsSuspicious(true);

		result = this.endorserRecordRepository.save(endorserRecord);
		Assert.notNull(result);

		if (endorserRecord.getId() == 0) {
			endorserRecords = principal.getCurriculum().getEndorserRecords();
			endorserRecords.add(result);
			principal.getCurriculum().setEndorserRecords(endorserRecords);
		}
		return result;

	}

	public void delete(final EndorserRecord endorserRecord) {
		HandyWorker principal;
		Collection<EndorserRecord> collectionEndorserRecords;

		Assert.notNull(endorserRecord);
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		collectionEndorserRecords = principal.getCurriculum().getEndorserRecords();
		Assert.isTrue(collectionEndorserRecords.contains(endorserRecord));

		collectionEndorserRecords.remove(endorserRecord);
		principal.getCurriculum().setEndorserRecords(collectionEndorserRecords);

		this.endorserRecordRepository.delete(endorserRecord);
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
