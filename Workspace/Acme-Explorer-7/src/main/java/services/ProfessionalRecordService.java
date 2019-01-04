
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed Repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services

	@Autowired
	private RangerService					rangerService;


	// Constructors

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<ProfessionalRecord> findByPrincipal() {
		Collection<ProfessionalRecord> result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCV().getProfessionalRecords();

		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord create() {
		ProfessionalRecord result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = new ProfessionalRecord();
		Assert.notNull(result);
		result.setComments(new ArrayList<String>());

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		ProfessionalRecord result;
		Ranger principal;
		List<ProfessionalRecord> professionalRecords;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCV());

		Assert.isTrue(professionalRecord.getEndDate().after(professionalRecord.getStartDate()));

		professionalRecords = principal.getCV().getProfessionalRecords();
		result = this.professionalRecordRepository.save(professionalRecord);
		Assert.notNull(result);
		if (professionalRecord.getId() == 0) {
			professionalRecords.add(result);
			principal.getCV().setProfessionalRecords(professionalRecords);
		}
		return result;

	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Ranger principal;
		List<ProfessionalRecord> listProfessionalRecord;

		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		listProfessionalRecord = principal.getCV().getProfessionalRecords();

		this.professionalRecordRepository.delete(professionalRecord);

		listProfessionalRecord.remove(professionalRecord);

		principal.getCV().setProfessionalRecords(listProfessionalRecord);

	}

	public ProfessionalRecord findOne(final int id) {
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(id);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

}
