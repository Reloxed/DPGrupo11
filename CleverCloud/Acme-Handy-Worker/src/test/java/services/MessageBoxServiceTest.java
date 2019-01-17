package services;

import java.util.ArrayList;
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
	public void testSave(){
		super.authenticate("Sponsor2");
		Actor principal;
		MessageBox box,saved;
		Collection<MessageBox>boxes;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		boxes = principal.getMessageBoxes();
		Assert.notNull(boxes);
		
		Assert.isTrue(boxes.size()==4);
		
		box = this.messageBoxService.create();
		Assert.notNull(box);
		box.setName("Test");
		saved = this.messageBoxService.save(box);
		Assert.notNull(saved);
		Assert.notNull(this.messageBoxService.findOne(saved.getId()));
	


		super.unauthenticate();
		
	}
	
	@Test
	public void testDelete(){
		super.authenticate("handyWorker2");
		Actor principal;
		MessageBox toDelete,box;
		Collection<MessageBox>boxes;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		box = this.messageBoxService.create();
		Assert.notNull(box);
		box.setName("Another box");
		toDelete = this.messageBoxService.save(box);
		Assert.notNull(toDelete);
		
		boxes = new ArrayList<MessageBox>();
		boxes = principal.getMessageBoxes();
		
		this.messageBoxService.delete(toDelete);
		
		Assert.isTrue(!boxes.contains(toDelete));
		
	}
	@Test
	public void testFindInBoxActor(){
		super.authenticate("customer1");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.findInBoxActor(principal);

		super.unauthenticate();
	}
	
	@Test
	public void testFindOutBoxActor(){
		super.authenticate("customer1");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.findOutBoxActor(principal);

		super.unauthenticate();
	}
	
	@Test
	public void testFindTrashBoxActor(){
		super.authenticate("customer1");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.findTrashBoxActor(principal);

		super.unauthenticate();
	}
	
	@Test
	public void testFindSpamBoxActor(){
		super.authenticate("customer1");
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		this.messageBoxService.findSpamBoxActor(principal);

		super.unauthenticate();
	}

	
	public void testFindOne(){
		Actor principal;
		MessageBox box;
		Collection<MessageBox> collMB;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		collMB = this.messageBoxService.findAllByPrincipal();
		box = this.messageBoxService.findOne(collMB.iterator().next().getId());
		Assert.notNull(box);

	}
	
	public void testFindAll(){
		Collection<MessageBox>boxes;

		boxes = this.messageBoxService.findAll();
		Assert.notNull(boxes);
	}
}
