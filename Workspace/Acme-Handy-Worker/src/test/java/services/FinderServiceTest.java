package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Finder;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class FinderServiceTest extends AbstractTest {

	
	// System under test -------------------------
	
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	
	

	
	//Tests ---------------------------------------
	

	@Test
	public void TestFindOne(){
		Finder result;
	
		result=this.finderService.findOne(2418);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2418);
	
		
		
	}
	
	@Test
	public void TestFindAll(){
		Collection<Finder> result;
		result=this.finderService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==2);
		
	}
}
