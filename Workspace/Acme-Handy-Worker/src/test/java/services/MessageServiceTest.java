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
import repositories.MessageRepository;
import utilities.AbstractTest;
import domain.Actor;
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
	private SystemConfigurationService systemConfigurationService;

	@Autowired
	private MessageBoxService messageBoxService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

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
		super.authenticate("handyworker2");
		Message message, saved;
		Actor principal;
		Actor recipient1;
		Actor recipient2;
		Collection<Message> messagesInBox;
		Collection<Message> messagesOutBox;
		Collection<Message> messages;
		Collection<Actor> recipients;
		MessageBox outBoxPrincipal;
		Collection<MessageBox> inBoxRecipients;
		Collection<MessageBox>boxes;
		MessageBox inBoxRecipient1;
		MessageBox inBoxRecipient2;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		
		boxes = new ArrayList<MessageBox>();
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
		message.setSubject("Test");
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
		saved = this.messageRepository.saveAndFlush(message);
		Assert.notNull(saved);
		
		messages = this.messageService.findAll();
		
		Assert.isTrue(messages.contains(saved));
	
		
		/*for(Actor a : saved.getRecipients()){
			MessageBox inBox = this.messageBoxService.findInBoxActor(a);
			Assert.isTrue(inBox.getMessages().contains(saved));
		}
		
		outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);
		Assert.notNull(outBoxPrincipal);
		
		Assert.isTrue(outBoxPrincipal.getMessages().contains(saved));
		/*
		
		
		
		
		 */
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

		super.unauthenticate();

	}
	
	

}
