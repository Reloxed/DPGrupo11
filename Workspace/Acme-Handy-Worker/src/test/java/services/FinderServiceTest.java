
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// System under test -------------------------

	@Autowired
	private FinderService		finderService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private WarrantyService		warrantyService;


	//Tests ---------------------------------------

	@Test
	public void testResults() {
		Finder result;
		Finder find;
		HandyWorker principal;

		super.authenticate("handyWorker1");
		
		principal = this.handyWorkerService.findByPrincipal();
		
		find = principal.getFinder();
		
//		System.out.println(find.getFixuptask());
		
		find.setPriceHigh(null);
		find.setPriceLow(null);
		final Calendar startMoment = Calendar.getInstance();
		startMoment.set(2021, 1, 22);
		final Calendar endMoment = Calendar.getInstance();
		endMoment.set(2022, 8, 22);
		find.setStartMoment(null);
		find.setEndMoment(null);
		find.setCategory(null);
		find.setWarranty(null);
		
		result = this.finderService.resultadosFinder(find);
		
//		System.out.println(result.getFixuptask());
		
		super.unauthenticate();
	}

	@Test
	public void TestFindAll() {
		Collection<Finder> result;
		result = this.finderService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void testCreateAndSave() {
		Finder result;
		Finder saved;
		HandyWorker principal;
		super.authenticate("handyWorker2");

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = this.finderService.create();

		result.setSearchMoment(new Date(System.currentTimeMillis() - 1));
		result.setKeyWord("clave");
		saved = this.finderService.save(result);
		Assert.notNull(saved);

		super.unauthenticate();

	}
	@Test
	public void TestCreateAndSaveEmpty() {
		Finder result;
		Finder saved;
		HandyWorker principal;
		super.authenticate("handyWorker2");

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		result = this.finderService.create();

		saved = this.finderService.save(result);
		Assert.notNull(saved);
		super.unauthenticate();
	}

	//	@Test
	//	public void testDelete(){
	//		Finder toDelete;
	//		HandyWorker principal;
	//		int TimeInCache=3600;
	//		 
	//		super.authenticate("handyWorker2");
	//
	//		principal=this.handyWorkerService.findByPrincipal();
	//		Assert.notNull(principal);
	//		
	//		toDelete=this.finderService.findAll().iterator().next();
	//		Assert.notNull(toDelete);
	//		
	//		this.finderService.deleteExpireFinders(TimeInCache);
	//		
	//
	//		super.unauthenticate();
	//
	//
	//	}

	@Test(expected = IllegalArgumentException.class)
	public void TestFindOne() {
		Finder result;

		super.authenticate("handyWorker2");

		result = this.finderService.findOne(-2);
		Assert.notNull(result);

		super.unauthenticate();

	}
}
