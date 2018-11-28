package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Application;

import domain.Customer;

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
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private WarrantyService warrantyService;
	
	@Autowired
	private ApplicationService applicationService;
	
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
	@Test
	public void testCreateAndSave(){
		FixUpTask result;
		Customer principal;
		FixUpTask saved;
		
		super.authenticate("customer2");
		
		principal=this.customerService.findByPrincipal();
		Assert.notNull(principal);
	
		
		result=this.fixUpTaskService.create();
		result.setTicker(this.utilityService.generateTicker());
		result.setPublishedMoment(new Date(System.currentTimeMillis()-1));
		result.setDescription("descripcion");
		result.setAddress("Mairena");
		result.setStartMoment(new Date(System.currentTimeMillis()-1));
		result.setEndMoment(new Date(203984203402L));
		result.setCategory(this.categoryService.findOne(2391));
		result.setWarranty(this.warrantyService.findOne(2415));
		result.setApplications(this.applicationService.findAll());
		
		saved=this.fixUpTaskService.save(result);
		Assert.notNull(saved);
		
		
		super.unauthenticate();
	
		
		
	}
	
	
	@Test
	public void Delete1(){
		Customer principal;
		FixUpTask result;
		
		super.authenticate("customer2");
		principal=this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		result=this.fixUpTaskService.findOne(2428);
		result.setApplications(new ArrayList<Application>());
		if(result.getApplications().isEmpty()){
			this.fixUpTaskService.delete(result);
		}
		
		
		super.unauthenticate();
		
	}
	
	
	@Test
	public void testDelete2(){
		FixUpTask result;
		Customer principal;
		Collection <FixUpTask> fixs;
		
		super.authenticate("customer2");
		
		principal=this.customerService.findByPrincipal();
		Assert.notNull(principal);
		
		result=this.fixUpTaskService.findOne(2428);
		
		try{
			this.fixUpTaskService.delete(result);
		}catch(final RuntimeException e){	
		}
		
		fixs=this.fixUpTaskService.findAll();
		
		Assert.isTrue(fixs.contains(result));

		
		super.unauthenticate();
		
		
	}

}
