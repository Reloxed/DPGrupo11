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
import domain.Actor;
import domain.HandyWorker;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@Transactional
public class MessageBoxServiceTest extends AbstractTest{

	//System under test ------------------------------------------

	@Autowired
	private MessageBoxService messageBoxService;

	@Autowired
	private ActorService actorService;

	//Tests ---------------------------------------------------------

	@Test
	public void testCreate(){
		super.authenticate("handyWorker2");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.create();


		super.unauthenticate();

	}
	@Test
	public void findInBoxActor(){
		super.authenticate("administrator2");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.findInBoxActor(principal);

		super.unauthenticate();
	}

	
	public void testFindOne(){
		Actor principal;
		MessageBox box;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		box = this.messageBoxService.findOne(2377);
		Assert.notNull(box);

	}
	
	public void testFindAll(){
		Collection<MessageBox>boxes;

		boxes = this.messageBoxService.findAll();
		Assert.notNull(boxes);
	}
}
