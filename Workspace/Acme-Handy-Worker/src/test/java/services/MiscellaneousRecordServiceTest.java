package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class MiscellaneousRecordServiceTest {

	//System under test ----------------------
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Tests ---------------------------------------
	@Test
	public void testCreateAndSave(){
		MiscellaneousRecord miscellaneousRecord,saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		
		miscellaneousRecord = miscellaneousRecordService.create();
		saved = miscellaneousRecordService.save(miscellaneousRecord);
		miscellaneousRecords = miscellaneousRecordService.findAll();
		Assert.isTrue(miscellaneousRecords.contains(saved));
		
		
	}
}
