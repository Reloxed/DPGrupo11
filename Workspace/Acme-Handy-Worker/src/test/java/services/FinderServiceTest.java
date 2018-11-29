package services;

import java.util.Collection;
import java.util.Date;

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
	public void TestFindAll(){
		Collection<Finder> result;
		result=this.finderService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==2);

	}

	@Test
	public void testCreateAndSave(){
		Finder result;
		Finder saved;
		HandyWorker principal;
		super.authenticate("handyWorker2");

		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result=this.finderService.create();

		result.setSearchMoment(new Date(System.currentTimeMillis()-1));
		result.setKeyWord("clave");
		saved=this.finderService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

	}
	@Test
	public void TestCreateAndSaveEmpty(){
		Finder result;
		Finder saved;
		HandyWorker principal;
		super.authenticate("handyWorker2");

		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result=this.finderService.create();

		saved=this.finderService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}

	@Test
	public void testDelete(){
		Finder toDelete;
		HandyWorker principal;
		int TimeInCache=3600;
		 
		super.authenticate("handyWorker2");

		principal=this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		
		toDelete=this.finderService.findAll().iterator().next();
		Assert.notNull(toDelete);
		
		this.finderService.deleteExpireFinders(TimeInCache);
		

		super.unauthenticate();


	}


	@Test(expected = IllegalArgumentException.class)
	public void TestFindOne(){
		Finder result;

		super.authenticate("handyWorker2");


		result=this.finderService.findOne(218);
		Assert.notNull(result);

		super.unauthenticate();



	}
}
