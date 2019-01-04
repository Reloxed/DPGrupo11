
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services

	@Autowired
	private RangerService					rangerService;


	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public Collection<MiscellaneousRecord> findByPrincipal() {
		Collection<MiscellaneousRecord> result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = principal.getCV().getMiscellaneousRecord();

		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;
		Ranger principal;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		result = new MiscellaneousRecord();
		Assert.notNull(result);

		result.setComments(new ArrayList<String>());

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		MiscellaneousRecord result;
		Ranger principal;
		List<MiscellaneousRecord> miscellaneousRecords;

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(principal.getCV());

		miscellaneousRecords = principal.getCV().getMiscellaneousRecord();
		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);
		if (miscellaneousRecord.getId() == 0) {
			miscellaneousRecords.add(result);
			principal.getCV().setMiscellaneousRecord(miscellaneousRecords);
		}
		return result;

	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Ranger principal;
		List<MiscellaneousRecord> listMiscellaneousRecord;

		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		principal = this.rangerService.findByPrincipal();
		Assert.notNull(principal);

		listMiscellaneousRecord = principal.getCV().getMiscellaneousRecord();
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		listMiscellaneousRecord.remove(miscellaneousRecord);

		principal.getCV().setMiscellaneousRecord(listMiscellaneousRecord);

	}

	public MiscellaneousRecord findOne(final int id) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(id);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods

}
