
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services -----------------------------------

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private UtilityService		utilityService;


	// Constructors ------------------------------------

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------------

	public ProfessionalRecord create() {
		HandyWorker principal;
		ProfessionalRecord res;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		res = new ProfessionalRecord();

		return res;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(result);

		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		ProfessionalRecord res;
		Collection<ProfessionalRecord> professionalRecords;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCurriculum());

		Assert.notNull(professionalRecord);

		List<String> atributosAComprobar = new ArrayList<>();
		atributosAComprobar.add(professionalRecord.getCompanyName());
		atributosAComprobar.add(professionalRecord.getRole());
		if (professionalRecord.getComments() != null)
			atributosAComprobar.add(professionalRecord.getComments());
		
		boolean containsSpam = this.utilityService.isSpam(atributosAComprobar);
		if(containsSpam) {
			principal.setIsSuspicious(true);
		}		

		if (professionalRecord.getEndDate() != null)
			Assert.isTrue(professionalRecord.getStartDate().before(professionalRecord.getEndDate()));
		else
			Assert.isTrue(professionalRecord.getStartDate().before(new Date(System.currentTimeMillis())));

		res = this.professionalRecordRepository.saveAndFlush(professionalRecord);
		Assert.notNull(res);

		professionalRecords = principal.getCurriculum().getProfessionalRecords();
		professionalRecords.add(res);
		principal.getCurriculum().setProfessionalRecords(professionalRecords);
		
		return res;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		HandyWorker principal;
		Collection<ProfessionalRecord> professionalRecords;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		professionalRecords = principal.getCurriculum().getProfessionalRecords();
		Assert.isTrue(professionalRecords.contains(professionalRecord));
		professionalRecords.remove(professionalRecord);

		this.professionalRecordRepository.delete(professionalRecord);

		principal.getCurriculum().setProfessionalRecords(professionalRecords);
	}

	// Other business methods -------------------------------

}
