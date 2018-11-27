package services;

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
		result = this.miscellaneousRecordService.findOne(2327);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2327);
		
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
		MiscellaneousRecord result;
		MiscellaneousRecord saved;
		HandyWorker principal;
		super.authenticate("handyworker1");
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.miscellaneousRecordService.create();
		result.setTitle("Sample");
		saved = this.miscellaneousRecordService.save(result);
		Assert.notNull(saved);
		Assert.isTrue(principal.getCurriculum().getMiscellaneousRecords().contains(saved));
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		MiscellaneousRecord toDelete;
		Collection<MiscellaneousRecord> listMiscellaneousRecord;
		HandyWorker principal;
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		super.authenticate("handyworker2");
		toDelete = this.miscellaneousRecordService.findOne(2328);
		listMiscellaneousRecord = principal.getCurriculum().getMiscellaneousRecords();
		Assert.isTrue(!listMiscellaneousRecord.contains(toDelete));
		super.unauthenticate();
		
	}
}
