package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tonema;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class TonemaServiceTest extends AbstractTest{
	
	// System under test ------------------------------------------------------

	@Autowired
	private TonemaService tonemaService;

	// Tests ------------------------------------------------------------------
	
	@Test
	public void testFindAll(){
		Collection<Tonema> collTonema;

		collTonema = this.tonemaService.findAll();
		Assert.notNull(collTonema);
		Assert.notEmpty(collTonema);

	}
	
	@Test
	public void testFindOne(){
		Collection<Tonema> collTonema;
		Tonema result;

		collTonema = this.tonemaService.findAll();
		result = this.tonemaService.findOne(collTonema.iterator().next().getId());
		Assert.notNull(result);

	}


}
