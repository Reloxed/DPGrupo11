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

import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import repositories.MessageBoxRepository;
import repositories.MessageRepository;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test --------------------------------------------
	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageBoxService messageBoxService;

	@Autowired
	private MessageBoxRepository messageBoxRepository;

	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private CustomerRepository customerRepository;

	// Tests ---------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker2");
		Actor principal;
		Message result;
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		result = new Message();
		result.setSubject("Entrega");
		result.setBody("Mañana es");
		result.setPriority("HIGH");
		result.setTags("no spam");
		result.setIsSpam(false);

		result = this.messageService.create();

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("customer1");
		Message message,saved;
		Collection<Message> messages;
		Actor recipient;

		recipient = this.actorService.findOne(6376); //referee1
		Assert.notNull(recipient);

		message= new Message();
		message.setBody("test");
		message.setSubject("test");
		message.setPriority("LOW");
		message.setRecipient(recipient);

		saved = this.messageService.save(message);
		
		System.out.println(saved.getMessageBoxes().size());
		
		Assert.isTrue(saved.getMessageBoxes().size()==2);
		
		messages = this.messageRepository.findAll();
		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(!this.messageBoxRepository.findOutBoxActorId(6368).getMessages().isEmpty());
		Assert.isTrue(this.messageBoxRepository.findInBoxActorId(6376).getMessages().contains(saved));
		System.out.println(this.messageBoxRepository.findOutBoxActorId(6386).getMessages());
		System.out.println(this.messageBoxRepository.findOutBoxActorId(6368));
		

		super.unauthenticate();
	}

	@Test
	public void testSaveSpam() {
		super.authenticate("customer1");
		Message message,saved;
		Collection<Message> messages;
		Actor recipient;

		recipient = this.actorService.findOne(6376); //referee1
		Assert.notNull(recipient);

		message= new Message();
		message.setBody("sex");
		message.setSubject("test");
		message.setPriority("LOW");
		message.setRecipient(recipient);

		saved = this.messageService.save(message);

		messages = this.messageRepository.findAll();
		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(this.messageBoxRepository.findOutBoxActorId(6368).getMessages().contains(saved));
		Assert.isTrue(this.messageBoxRepository.findSpamBoxActorId(6376).getMessages().contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testMove() {
		super.authenticate("customer2");
		Message message, saved;
		Actor recipient;
		MessageBox destination,origin;
		Collection<Message> messages;

		recipient = this.actorService.findOne(6380); // sponsor1
		Assert.notNull(recipient);

		message = new Message();
		message.setBody("HOALAA");
		message.setSubject("hadshfskl");
		message.setPriority("NEUTRAL");
		message.setRecipient(recipient);

		saved = this.messageService.save(message);
		destination = this.messageBoxRepository.findSpamBoxActorId(this.actorService.findOne(6380).getId());
		Assert.notNull(destination);

		origin = this.messageBoxRepository.findInBoxActorId(this.actorService.findOne(6380).getId());
		Assert.notNull(origin);
		
		messages = this.messageRepository.findAll();

		Assert.isTrue(messages.contains(saved));
		
		
		Assert.isTrue(!destination.getMessages().contains(saved));
		Assert.isTrue(origin.getMessages().contains(saved));

		super.authenticate(null);

		super.authenticate("sponsor1");


		this.messageService.move(saved, destination);

		
		Assert.isTrue(destination.getMessages().contains(saved));
		Assert.isTrue(!origin.getMessages().contains(saved));

		super.authenticate(null);

	}


	@Test
	public void testDelete() {
		super.authenticate("handyWorker1");
		Message message,saved;
		Collection<Message> messages,messagesAfter;
		Actor recipient;
		
		recipient = this.actorService.findOne(6368);//customer1
		Assert.notNull(recipient);

		message = new Message();
		message.setBody("ellele");
		message.setSubject("ldlldl");
		message.setPriority("LOW");
		message.setRecipient(recipient);

		saved = this.messageService.save(message);
		
		Assert.isTrue(this.messageBoxRepository.findInBoxActorId(recipient.getId()).getMessages().contains(saved));
		Assert.isTrue(this.messageBoxRepository.findOutBoxActorId(this.actorService.findOne(6372).getId()).getMessages().contains(saved));

		super.authenticate(null);
		
		

		super.authenticate("customer1");


		
		
		
		this.messageService.delete(saved);

		
		Assert.isTrue(this.messageBoxRepository.findTrashBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved));
		Assert.isTrue(!this.messageBoxRepository.findInBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved));
		
		this.messageService.delete(saved);

		Assert.isTrue((!this.messageBoxRepository.findInBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved)) &&
					(!this.messageBoxRepository.findOutBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved)) && 
					(!this.messageBoxRepository.findTrashBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved))&&
					(!this.messageBoxRepository.findSpamBoxActorId(this.actorService.findOne(6368).getId()).getMessages().contains(saved)));
		

		super.authenticate(null);
		
		super.authenticate("handyWorker1");
		
		this.messageService.delete(saved);
		
		Assert.isTrue(!(this.messageBoxRepository.findOutBoxActorId(this.actorService.findOne(6372).getId()).getMessages().contains(saved)));
		Assert.isTrue((this.messageBoxRepository.findTrashBoxActorId(this.actorService.findOne(6372).getId()).getMessages().contains(saved)));
		
		
		this.messageService.delete(saved);
		
		messages = this.messageRepository.findAll();
		Assert.notNull(messages);
		Assert.isTrue(!(messages.contains(saved)));
	}

	

}

