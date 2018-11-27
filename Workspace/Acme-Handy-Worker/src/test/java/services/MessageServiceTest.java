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

import repositories.HandyWorkerRepository;
import repositories.MessageRepository;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest{
	
	//System under test --------------------------------------------
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
	
	//Tests ---------------------------------------------------------

	@Test
	public void testSave(){
		super.authenticate("handyworker2");
		Message message;
		Message saved;
		Actor recipient1;
		Collection<Message> messages;
		Collection<Actor>recipients;
		
		messages = new ArrayList<Message>();
		
		recipients = new ArrayList<Actor>();
		recipient1 = this.actorService.findOne(2304);
		Assert.notNull(recipient1);
		
		recipients.add(recipient1);
		
		message = this.messageService.create();
		message.setSubject("sex");
		message.setBody("sample");
		message.setPriority("HIGH");
		message.setRecipients(recipients);
		
		//Assert.isTrue(this.handyWorkerRepository.findOne(2422).getIsSuspicious());
		
		
		saved = this.messageRepository.save(message);
		
		messages = this.messageRepository.findAll();
		
		Assert.isTrue(messages.contains(saved));
		//Assert.isTrue(this.handyWorkerRepository.findOne(2422).getIsSuspicious());
		
		super.authenticate(null);
	}
	
}
