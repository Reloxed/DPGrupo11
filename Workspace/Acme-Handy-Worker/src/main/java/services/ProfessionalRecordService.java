package services;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------------------

	public ProfessionalRecord create() {
		HandyWorker principal;
		ProfessionalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new ProfessionalRecord();

		return res;
	}

	public ProfessionalRecord findOne(int professionalRecordId) {
		ProfessionalRecord res;
		res = this.professionalRecordRepository.findOne(professionalRecordId);
		return res;
	}

	public ProfessionalRecord save(ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		ProfessionalRecord res;
		Collection<ProfessionalRecord> professionalRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.notNull(professionalRecord);

		Assert.isTrue(!professionalRecord.getCompanyName().isEmpty());
		Assert.isTrue(!professionalRecord.getRole().isEmpty());
		Assert.isTrue(professionalRecord.getStartDate().before(
				LocalDate.now().toDate()));

		professionalRecords = principal.getCurriculum()
				.getProfessionalRecords();

		res = this.professionalRecordRepository.save(professionalRecord);
		Assert.notNull(res);
		if (professionalRecord.getId() == 0) {
			professionalRecords.add(res);
			principal.getCurriculum().setProfessionalRecords(
					professionalRecords);
		}

		return res;
	}

	public void delete(ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		Collection<ProfessionalRecord> professionalRecords;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		professionalRecords = principal.getCurriculum()
				.getProfessionalRecords();

		this.professionalRecordRepository.delete(professionalRecord);

		professionalRecords.remove(professionalRecord);

		principal.getCurriculum().setProfessionalRecords(professionalRecords);
	}

	// Other business methods -------------------------------

	public Collection<ProfessionalRecord> findByPrincipal() {
		HandyWorker principal;
		Collection<ProfessionalRecord> res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = principal.getCurriculum().getProfessionalRecords();
		Assert.notNull(res);

		return res;
	}
}
