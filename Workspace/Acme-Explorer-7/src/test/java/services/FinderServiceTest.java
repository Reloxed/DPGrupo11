
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	//	@Autowired
	//	private ExplorerRepository	explorerRepository;

	//	@Autowired
	//	private FinderService		finderService;

	// Tests

	// TRIP 1 startDate = "2018/05/31" price = 750
	// TRIP 2 startDate = "2018/08/01" price = 500
	// TRIP 3 startDate = "2018/04/24" price = 375
	// TRIP 4 startDate = "2017/09/22" price = 775
	// TRIP 5 startDate = "2018/12/11" price = 300

	/*
	 * 
	 * @Test
	 * public void testSearch1() {
	 * super.authenticate("explorer1");
	 * Collection<Trip> trips;
	 * Explorer explorer1;
	 * Finder finder;
	 * 
	 * explorer1 = this.explorerRepository.findOne(1242); // <- este es explorer 1
	 * finder = explorer1.getFinder(); // <- el finder de explorer 1 (devuelve todos los viajes)
	 * 
	 * finder.setDateMin(new Date(1522540800000L)); // Sunday, April 1, 2018 12:00:00 AM
	 * 
	 * explorer1.setFinder(finder);
	 * this.finderService.save(finder);
	 * 
	 * trips = this.finderService.search();
	 * Assert.notNull(trips);
	 * Assert.isTrue(trips.size() == 5); // It should return all trips except Trip4
	 * 
	 * super.authenticate(null);
	 * }
	 * 
	 * @Test
	 * public void testSearch2() {
	 * super.authenticate("explorer1");
	 * Collection<Trip> trips;
	 * Explorer explorer1;
	 * Finder finder;
	 * 
	 * explorer1 = this.explorerRepository.findOne(1242); // <- este es explorer 1
	 * finder = explorer1.getFinder(); // <- el finder de explorer 1 (devuelve todos los viajes)
	 * 
	 * finder.setDateMin(new Date(1522540800000L)); // Sunday, April 1, 2018 12:00:00 AM
	 * 
	 * finder.setPriceMin(300.);
	 * finder.setPriceMax(500.);
	 * 
	 * explorer1.setFinder(finder);
	 * this.finderService.save(finder);
	 * 
	 * trips = this.finderService.search();
	 * Assert.notNull(trips);
	 * Assert.isTrue(trips.size() == 3); // It should return all trips except Trip4 and Trip1
	 * 
	 * super.authenticate(null);
	 * }
	 * 
	 * @Test
	 * public void testSearch3() {
	 * super.authenticate("explorer1");
	 * Collection<Trip> trips;
	 * Explorer explorer1;
	 * Finder finder;
	 * 
	 * explorer1 = this.explorerRepository.findOne(1242); // <- este es explorer 1
	 * finder = explorer1.getFinder(); // <- el finder de explorer 1 (devuelve todos los viajes)
	 * 
	 * finder.setKeyword("HCV"); // The ticker of Trip5 contains "HCV"
	 * 
	 * explorer1.setFinder(finder);
	 * this.finderService.save(finder);
	 * 
	 * trips = this.finderService.search();
	 * Assert.notNull(trips);
	 * Assert.isTrue(trips.size() == 1);
	 * 
	 * super.authenticate(null);
	 * }
	 */
}
