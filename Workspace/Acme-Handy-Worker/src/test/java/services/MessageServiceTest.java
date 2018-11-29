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
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
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
		Message message, saved;
		Actor principal;
		Actor recipient1;
		Actor recipient2;
		Collection<Message> messagesInBox;
		
		MessageBox inboxes;
		//Collection<Message> messagesOutBox;
		Collection<Message> messages;
		Collection<Actor> recipients;
		MessageBox outBoxPrincipal;
		Collection<MessageBox> inBoxRecipients;
		//Collection<MessageBox>boxes;
		MessageBox inBoxRecipient1;
		MessageBox inBoxRecipient2;
		MessageBox trashBoxRecipient1;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		//boxes = new ArrayList<MessageBox>();
		recipients = new ArrayList<Actor>();
		messagesInBox = new ArrayList<Message>();
		inBoxRecipients = new ArrayList<MessageBox>();
		messages = new ArrayList<Message>();

		recipient1 = this.handyWorkerRepository.findOne(2453);
		Assert.notNull(recipient1);
		recipient2 = this.customerRepository.findOne(2335);
		Assert.notNull(recipient2);

		recipients.add(recipient1);
		recipients.add(recipient2);
		Assert.notNull(recipients);

		message = this.messageService.create();
		Assert.notNull(message);
		message.setSubject("sample");
		message.setBody("My body sample");
		message.setPriority("LOW");
		message.setTags("no spam");
		message.setIsSpam(false);
		inBoxRecipient1 = this.messageBoxService.findInBoxActor(recipient1);
		inBoxRecipient2 = this.messageBoxService.findInBoxActor(recipient2);
		inBoxRecipients.add(inBoxRecipient1);
		inBoxRecipients.add(inBoxRecipient2);
		Assert.notNull(inBoxRecipients);
		message.setRecipients(recipients);
		message.setMessageBoxes(inBoxRecipients);
		outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);

		saved = this.messageService.save(message);
		Assert.notNull(saved);
		Assert.isTrue(principal.getIsSuspicious()==false);
		messages = this.messageService.findAll();

		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(principal.getIsSuspicious()==false);
		Assert.isTrue(saved.getIsSpam()==false);
		trashBoxRecipient1 = this.messageBoxService.findTrashBoxActor(recipient1);
		//Assert.isTrue(trashBoxRecipient1.getMessages().contains(saved));

		Assert.isTrue(inBoxRecipient1.getMessages().size()==1 && inBoxRecipient1.getMessages().contains(saved));
		Assert.isTrue(inBoxRecipient2.getMessages().size()==1 && inBoxRecipient2.getMessages().contains(saved));
		outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBoxPrincipal);
		
		Assert.isTrue(outBoxPrincipal.getMessages().size()==2);
		System.out.println("============Tests===============");
		System.out.println("Recipients: \n" + saved.getRecipients());
		System.out.println("Inboxes recipients: \n" + saved.getMessageBoxes());
		System.out.println("Messages inBoxR1: \n"+ inBoxRecipient1.getMessages());
		System.out.println("Messages inBoxR2: \n"+ inBoxRecipient2.getMessages());
		System.out.println("Messages outBox princial: \n" + outBoxPrincipal.getMessages());
		System.out.println(this.messageService.findOne(saved.getId()));
		System.out.println(this.messageService.findOne(saved.getId()+1));
		
		System.out.println("Message saved: \n"+saved);
		for(MessageBox mb: inBoxRecipients){
			System.out.println("Messages inBox: \n"+mb.getMessages());
		}
		super.unauthenticate();

	}
	
	@Test
	public void testSaveSpam(){
		super.authenticate("handyWorker2");
		Message message,saved;
		Actor principal;
		Actor recipient1;
		Collection<Actor>recipients;
		MessageBox trashRecipient1;
		Collection<MessageBox>trashBoxRecipients;
		Collection<Message>messages;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		recipients = new ArrayList<Actor>();
		messages = new ArrayList<Message>();
		trashBoxRecipients = new ArrayList<MessageBox>();
		recipient1 = this.customerRepository.findOne(2335);
		Assert.notNull(recipient1);
		
		recipients.add(recipient1);
		Assert.notNull(recipients);
		
		message = this.messageService.create();
		Assert.notNull(message);
		message.setSubject("sex");
		message.setBody("My body sample");
		message.setPriority("LOW");
		message.setTags("no spam");
		message.setIsSpam(false);
		
		trashRecipient1 = this.messageBoxService.findTrashBoxActor(recipient1);
		Assert.notNull(trashRecipient1);
		trashBoxRecipients.add(trashRecipient1);
		message.setRecipients(recipients);
		message.setMessageBoxes(trashBoxRecipients);
		
		saved = this.messageService.save(message);
		
		messages = this.messageService.findAll();
		Assert.notNull(messages);
		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(principal.getIsSuspicious());
		Assert.isTrue(trashRecipient1.getMessages().contains(saved));
		Assert.isTrue(trashRecipient1.getMessages().size()==1);
	}

	/*
	 * outBoxPrincipal = this.messageBoxService.findInBoxActor(principal);
	 * Assert.notNull(outBoxPrincipal);
	 * 
	 * for(Actor a: recipients){ MessageBox inBox =
	 * this.messageBoxService.findInBoxActor(a); inBoxRecipients.add(inBox);
	 * 
	 * } for(MessageBox mb : inBoxRecipients){
	 * messagesInBox.addAll(mb.getMessages()); } messagesOutBox =
	 * outBoxPrincipal.getMessages();
	 * 
	 * Assert.notNull(messagesOutBox); Assert.notNull(inBoxRecipients);
	 * 
	 * Assert.isTrue(principal.getMessageBoxes().contains(outBoxPrincipal));
	 * Assert
	 * .isTrue(recipient1.getMessageBoxes().contains(inBoxRecipients));
	 * Assert
	 * .isTrue(recipient2.getMessageBoxes().contains(inBoxRecipients));
	 * Assert.isTrue(messagesInBox.contains(message));
	 */
	@Test
	public void move(){
		super.authenticate("Sponsor2");
		Message toMove,saved;
		Actor recipient,principal;
		MessageBox destination;
		Collection<Message>allMessages;
		Collection<Actor>recipients;
		Collection<MessageBox>boxes;
		Collection<MessageBox>boxesAftterMove;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		boxes = new ArrayList<MessageBox>();
		boxesAftterMove = new ArrayList<MessageBox>();
		toMove = this.messageService.findOne(2406);
		Assert.notNull(toMove);
		
		destination = this.messageBoxService.findTrashBoxActor(principal);
		boxes = toMove.getMessageBoxes();
		
		System.out.println(boxes);
		for(MessageBox box : boxes){
			System.out.println("Messages before move: \n" + box.getMessages());
		}
		this.messageService.move(toMove, destination);
		boxesAftterMove = toMove.getMessageBoxes();
		System.out.println("Boxes after move: \n" + boxesAftterMove);
		for(MessageBox box : boxesAftterMove){
			System.out.println("Messages after move: \n" + box.getMessages());
			Assert.isTrue(box.getMessages().contains(toMove));
		}
		
		for(MessageBox box : boxes){
			Assert.isTrue(!(box.getMessages().contains(toMove)), "Message was moved");
		}
		
		super.unauthenticate();
		
		
	}
		/*recipients = new ArrayList<Actor>();
		boxesPrincipal = new ArrayList<MessageBox>();
		origin = new ArrayList<MessageBox>();
		
		recipient = this.actorService.findOne(2335);
		Assert.notNull(recipient);
		recipients.add(recipient);
		System.out.println("Message's recipients: \n"+recipients);
		
		result = this.messageService.create();
		result.setSubject("Move");
		result.setBody("Move");
		result.setPriority("LOW");
		result.setRecipients(recipients);
		result.setTags("lel");
		result.setIsSpam(false);
		boxesPrincipal = this.messageBoxService.findAllByPrincipal();
		result.setMessageBoxes(boxesPrincipal);
		saved = this.messageService.save(result);
		Assert.notNull(saved);
		
		destination = this.messageBoxService.findTrashBoxActor(recipient);
		Assert.notNull(destination);
		
		this.messageService.move(saved, destination);
		
		allMessages = this.messageService.findAll();
		
		Assert.isTrue(allMessages.contains(saved));
		
		Assert.isTrue(destination.getMessages().contains(saved));
		
	*/

	@Test
	public void testDelete(){
		super.authenticate("sponsor2");
		Message toDelete;
		Actor principal;
		MessageBox inBoxPrincipal;
		MessageBox trashBoxPrincipal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		inBoxPrincipal = this.messageBoxService.findInBoxActor(principal);
		Assert.notNull(inBoxPrincipal);
		
		trashBoxPrincipal = this.messageBoxService.findTrashBoxActor(principal);
		Assert.notNull(trashBoxPrincipal);
		
		toDelete = this.messageService.findOne(2406);
		Assert.notNull(toDelete);
		
		this.messageService.delete(toDelete);
		
		Assert.isTrue(!(inBoxPrincipal.getMessages().contains(toDelete)));
		Assert.isTrue(trashBoxPrincipal.getMessages().contains(toDelete));
		
		super.unauthenticate();
	}
	
	@Test
	public void testBroadcast(){
		super.authenticate("admin2");
		Message result;
		MessageBox inBox1;
		MessageBox inBox2;
		MessageBox outBoxAdmin;
		Administrator principal;
		
		
		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);
		result = this.messageService.findOne(2406);
		Assert.notNull(result);
		
		
		this.messageService.broadcast(result);
		
		outBoxAdmin = this.messageBoxRepository.findOutBoxActorId(2354);
		System.out.println(outBoxAdmin.getMessages());
		System.out.println(result);
		inBox1 = this.messageBoxRepository.findInBoxActorId(2453);
		System.out.println(inBox1.getMessages());
		
		Assert.isTrue(inBox1.getMessages().contains(result));
		super.unauthenticate();
		
	}


}
