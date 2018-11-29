package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import utilities.AbstractTest;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest{

	//System under test ----------------------
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Tests ---------------------------------------
	
	@Test
	public void testFindOne(){
		MiscellaneousRecord result;
		result = this.miscellaneousRecordService.findOne(2364);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2364);
		
	}
	@Test
	public void testFindAll(){
		Collection<MiscellaneousRecord>result;
		result = this.miscellaneousRecordService.findAll();
		Assert.notNull(result);
		Assert.isTrue(result.size()==2);
	}
	@Test
	public void testCreateAndSave(){
		super.authenticate("handyWorker2");
		MiscellaneousRecord result;
		MiscellaneousRecord saved;
		HandyWorker principal;
		Collection<MiscellaneousRecord>miscellaneousRecords;
		miscellaneousRecords = new ArrayList<MiscellaneousRecord>();
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.miscellaneousRecordService.create();
		Assert.notNull(result);
		miscellaneousRecords = principal.getCurriculum().getMiscellaneousRecords();
		result.setTitle("test");
		saved = this.miscellaneousRecordService.save(result);
		this.miscellaneousRecordService.findOne(saved.getId());
		Assert.notNull(saved);
		Assert.isTrue(miscellaneousRecords.contains(saved));
		
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		super.authenticate("handyWorker2");
		MiscellaneousRecord toDelete;
		Collection<MiscellaneousRecord> listMiscellaneousRecord;
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		toDelete = this.miscellaneousRecordService.findOne(2364);
		this.miscellaneousRecordService.delete(toDelete);
		listMiscellaneousRecord = principal.getCurriculum().getMiscellaneousRecords();
		Assert.isTrue(!listMiscellaneousRecord.contains(toDelete));
		super.unauthenticate();
		
	}
}
