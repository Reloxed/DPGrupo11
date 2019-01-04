
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import repositories.MessageRepository;
import repositories.RangerRepository;
import utilities.AbstractTest;
import domain.Actor;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderRepository	folderRepository;

	@Autowired
	private RangerRepository	rangerRepository;


	// Tests
	@Test
	public void testSave() {
		super.authenticate("ranger2");
		Message message, saved;
		Collection<Message> messages;
		Actor recipient;

		recipient = this.actorService.findOne(1144); // Auditor3
		Assert.notNull(recipient);

		message = new Message();
		message.setBody("Hi");
		message.setSubject("sex");
		message.setPriority("HIGH");
		message.setRecipient(recipient);
		
		Assert.isTrue(!this.rangerRepository.findOne(1179).getIsSuspicious()); // Ranger2 is not suspicious
		
		saved = this.messageService.save(message);

		messages = this.messageRepository.findAll();
		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(recipient.getReceivedMessages().size() == 2);  // Auditor3 has two messages. The original message and the copy 
		Assert.isTrue(this.rangerRepository.findOne(1179).getSentMessages().size() == 2); // Ranger2 has two messages. The original message and the copy 

		Assert.isTrue(this.rangerRepository.findOne(1179).getIsSuspicious()); // Ranger2 is now suspicious

		super.authenticate(null);

	}

	@Test
	public void testMove() {
		super.authenticate("ranger2");
		Message message, saved;
		Actor recipient;
		Folder destination;
		Collection<Message> messages;

		recipient = this.actorService.findOne(1236); // Sponsor 3
		Assert.notNull(recipient);

		message = new Message();
		message.setBody("Hi");
		message.setSubject("hello");
		message.setPriority("HIGH");
		message.setRecipient(recipient);

		saved = this.messageService.save(message); // Ranger2 sends a message to Sponsor3

		destination = this.folderRepository.findNotificationFolderActorId(this.actorService.findOne(1236).getId()); // Notification box of Sponsor3 

		try {
			this.messageService.move(saved, destination); // Ranger2 is trying to move the message from the in box to the notification box
		} catch (final RuntimeException e) {
		}               // This action is not permitted

		messages = this.messageRepository.findAll();

		Assert.isTrue(messages.contains(saved));

		// The message is still in the in box ot Sponsor3
		Assert.isTrue(saved.getFolder().getId() != destination.getId());
		Assert.isTrue(!destination.getMessages().contains(saved));

		final Folder originalFolder = this.folderRepository.findInBoxFolderActorId(this.actorService.findOne(1236).getId());
		Assert.isTrue(originalFolder.getMessages().contains(saved));

		super.authenticate(null);

		super.authenticate("sponsor3");

		try {
			this.messageService.move(saved, destination); // Sponsor3 is trying to move the message from the in box to the notification box
		} catch (final RuntimeException e) {
		}

		// The message in now in the notification box of Sponsor3
		Assert.isTrue(saved.getFolder().getId() == destination.getId());
		Assert.isTrue(destination.getMessages().contains(saved));
		Assert.isTrue(!originalFolder.getMessages().contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDelete() {
		super.authenticate("explorer4");  ///id = 1245
		Message message, saved;
		Collection<Message> messages;
		Actor recipient;

		recipient = this.actorService.findOne(1180); //Ranger3
		Assert.notNull(recipient);

		message = new Message();
		message.setBody("Hi");
		message.setSubject("hello");
		message.setPriority("HIGH");
		message.setRecipient(recipient);

		saved = this.messageService.save(message);

		super.authenticate(null);

		super.authenticate("ranger3");  // id = 1180

		messages = this.messageRepository.findAll();

		this.messageService.delete(saved);

		Assert.isTrue(messages.contains(saved));
		Assert.isTrue(recipient.getReceivedMessages().size() == 2);
		Assert.isTrue(!this.folderRepository.findInBoxFolderActorId(this.actorService.findOne(1180).getId()).getMessages().contains(saved));
		Assert.isTrue(this.folderRepository.findTrashFolderActorId(this.actorService.findOne(1180).getId()).getMessages().contains(saved));

		this.messageService.delete(saved);
		messages = this.messageRepository.findAll();

		Assert.isTrue(!messages.contains(saved));

		super.authenticate(null);

	}

}
