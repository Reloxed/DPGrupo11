package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.EndorserRecord;
import domain.FixUpTask;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
	})

@Transactional
public class FixUpTaskServiceTest extends AbstractTest{
	
	// System under test -------------------------
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	
	

	
	//Tests ---------------------------------------
	
	
	@Test
	public void TestFindOne(){
		FixUpTask result;
		result=this.fixUpTaskService.findOne(2427);
		Assert.notNull(result);
		Assert.isTrue(result.getId()==2427);
		
		
	}
	
	@Test
	public void TestFindAll(){

		Collection<FixUpTask>  result;
		result=this.fixUpTaskService.findAll();
		Assert.isTrue(!result.isEmpty());
		Assert.isTrue(result.size()==3);
		
		
	}

}
