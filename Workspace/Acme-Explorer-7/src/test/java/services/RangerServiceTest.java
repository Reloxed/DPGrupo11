
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
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RangerService	rangerService;


	// Tests
	@Test
	public void testListSuspiciousManagers() {
		Collection<Ranger> result;
		super.authenticate("administrator1");
		result = this.rangerService.findRangersBySuspicious();
		Assert.notNull(result);
		Assert.isTrue(result.size() == 1);
		super.unauthenticate();
	}

}
