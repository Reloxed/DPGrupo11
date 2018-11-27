package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Endorsement;


import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class EndorsementServiceTest extends AbstractTest {
	
	// System under test -------------------------
	
	@Autowired
	private EndorsementService endorsementService;
	
	

	
	//Tests ---------------------------------------
	
	@Test
	public void TestFindOne(){
		Endorsement result;
		result=this.endorsementService.findOne(2425);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2425);
		
	}
	
	@Test
	public void TestFindAll(){
		Collection<Endorsement>  result;
		result=this.endorsementService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==3);
	}
	

}
