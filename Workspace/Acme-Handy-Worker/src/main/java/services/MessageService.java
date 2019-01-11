
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------

	@Autowired
	private MessageRepository			messageRepository;

	//Supporting services -------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private MessageBoxService			messageBoxService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Constructors ------------------------------------

	public MessageService() {
		super();
	}

	//CRUD Methods --------------------------------

	public Message create() {
		Message result;
		Actor principal;
		Collection<MessageBox> messageBoxes;
		Collection<Actor> recipients;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messageBoxes = new ArrayList<MessageBox>();
		recipients = new ArrayList<Actor>();

		result = new Message();
		result.setSender(principal);
		result.setMessageBoxes(messageBoxes);
		result.setRecipients(recipients);

		result.setSendMoment(new Date(System.currentTimeMillis() - 1));
		result.setPriority("NEUTRAL");

		return result;
	}

	public Message save(final Message message) {
		Actor principal;
		Message result, saved, copy, principalMessage;
		Collection<MessageBox> inBoxesRecipients;
		Collection<MessageBox> copyOutBoxes;
		Collection<Message> messages;
		Collection<Message> updated, updated2, spamMessages, updatedSpamMessages, spamBoxMessagesPrincipal, updatedSpamPrincipal;
		MessageBox inBox;
		MessageBox outBoxPrincipal;
		Collection<Actor> recipients;
		MessageBox spamBox, spamBoxPrincipal;
		Collection<MessageBox> spamBoxes;
		Collection<MessageBox> spamBoxesPrincipal;

		result = this.create();
		updatedSpamPrincipal = new ArrayList<Message>();
		spamBoxes = new ArrayList<MessageBox>();
		spamBoxMessagesPrincipal = new ArrayList<Message>();
		spamBoxesPrincipal = new ArrayList<MessageBox>();
		messages = new ArrayList<Message>();
		updated2 = new ArrayList<Message>();
		recipients = new ArrayList<Actor>();
		copyOutBoxes = new ArrayList<MessageBox>();
		inBoxesRecipients = new ArrayList<MessageBox>();

		Assert.notNull(message);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(message.getSender());
		Assert.notNull(message.getSendMoment());

		recipients = message.getRecipients();
		Assert.notNull(recipients);

		result.setSubject(message.getSubject());
		result.setBody(message.getBody());
		result.setPriority("NEUTRAL");
		result.setTags(message.getTags());
		result.setRecipients(recipients);

		boolean containsSpam = false;
		final String[] spamWords = this.systemConfigurationService.findMySystemConfiguration().getSpamWords().split(",");
		final String[] subject = message.getSubject().split("(¿¡,.-_/!?) ");
		for (final String word : spamWords) {
			for (final String titleWord : subject)
				if (titleWord.toLowerCase().contains(word.toLowerCase())) {
					containsSpam = true;
					break;
				}
			if (containsSpam) {
				principal.setIsSuspicious(true);
				break;
			}
		}
		if (!containsSpam) {
			final String[] body = message.getBody().split("(¿¡,.-_/!?) ");
			for (final String word : spamWords) {
				for (final String titleWord : body)
					if (titleWord.toLowerCase().contains(word.toLowerCase())) {
						containsSpam = true;
						break;
					}
				if (containsSpam) {
					principal.setIsSuspicious(true);
					break;
				}
			}
		if (message.getTags() != null)
			if (!containsSpam) {
				final String[] tags = message.getTags().split("(¿¡,.-_/!?) ");
				for (final String word : spamWords) {
					for (final String titleWord : tags)
						if (titleWord.toLowerCase().contains(word.toLowerCase())) {
							containsSpam = true;
							break;
						}
					if (containsSpam) {
						principal.setIsSuspicious(true);
						break;
					}
				}
			}
		}

		result.setIsSpam(containsSpam);

		for (final Actor a : recipients)
			if (containsSpam == true) {
				spamBox = this.messageBoxService.findSpamBoxActor(a);
				spamMessages = spamBox.getMessages();
				updatedSpamMessages = new ArrayList<Message>(spamMessages);
				updatedSpamMessages.add(result);
				spamBox.setMessages(updatedSpamMessages);
				Assert.isTrue(!(spamBox.getMessages().size() == 0));
				spamBoxes.add(spamBox);
				result.setMessageBoxes(spamBoxes);
			} else {

				inBox = this.messageBoxService.findInBoxActor(a);
				System.out.println(inBox);
				messages = inBox.getMessages();
				updated = new ArrayList<Message>(messages);
				updated.add(result);
				inBox.setMessages(updated);
				Assert.isTrue(!(inBox.getMessages().size() == 0));
				inBoxesRecipients.add(inBox);
				Assert.notNull(inBoxesRecipients);
				result.setMessageBoxes(inBoxesRecipients);
			}

		saved = this.messageRepository.save(result);
		Assert.notNull(saved);
		System.out.println("Message saved: \n" + saved);
		Assert.isTrue(this.messageRepository.findAll().contains(saved));

		System.out.println("Saved messageBoxes: \n" + saved.getMessageBoxes());

		copy = this.create();
		copy.setSubject(saved.getSubject());
		copy.setBody(saved.getBody());
		copy.setPriority(saved.getPriority());
		copy.setTags(saved.getTags());
		copy.setSender(saved.getSender());
		copy.setRecipients(saved.getRecipients());
		copy.setIsSpam(saved.getIsSpam());

		if (principal.getIsSuspicious() == true) {
			spamBoxPrincipal = this.messageBoxService.findSpamBoxActor(principal);
			Assert.notNull(spamBoxPrincipal);
			spamBoxMessagesPrincipal = spamBoxPrincipal.getMessages();
			Assert.notNull(spamBoxMessagesPrincipal);
			updatedSpamPrincipal = new ArrayList<Message>(spamBoxMessagesPrincipal);
			updatedSpamPrincipal.add(copy);
			spamBoxPrincipal.setMessages(updatedSpamPrincipal);
			spamBoxesPrincipal.add(spamBoxPrincipal);

			copy.setMessageBoxes(spamBoxesPrincipal);
		} else {
			outBoxPrincipal = this.messageBoxService.findOutBoxActor(principal);
			Assert.notNull(outBoxPrincipal);

			final Collection<Message> outBoxMessages = outBoxPrincipal.getMessages();
			Assert.notNull(outBoxMessages);
			outBoxMessages.add(copy);

			updated2 = outBoxPrincipal.getMessages();
			Assert.notNull(updated2);
			updated2.add(copy);
			outBoxPrincipal.setMessages(updated2);

			copyOutBoxes = saved.getMessageBoxes();
			Assert.notNull(copyOutBoxes);
			copyOutBoxes.removeAll(inBoxesRecipients);
			copyOutBoxes.add(outBoxPrincipal);
			copy.setMessageBoxes(copyOutBoxes);
		}

		principalMessage = this.messageRepository.saveAndFlush(copy);

		Assert.isTrue(this.messageRepository.findAll().contains(principalMessage));

		System.out.println("Copy message: \n" + principalMessage);

		return saved;

	}

	public void delete(final Message message) {
		Actor principal;
		Collection<MessageBox> messageBoxesActor;
		Collection<MessageBox> messageBoxesMessage;
		MessageBox trashBox;
		Collection<Message> allMessages;

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		messageBoxesActor = principal.getMessageBoxes();
		Assert.notNull(messageBoxesActor);

		allMessages = new ArrayList<Message>();
		for (final MessageBox mb : messageBoxesActor) {
			final Collection<Message> m = mb.getMessages();
			allMessages.addAll(m);
		}

		Assert.isTrue(allMessages.contains(message));

		messageBoxesMessage = message.getMessageBoxes();
		Assert.notNull(messageBoxesMessage);

		for (final MessageBox mb : messageBoxesMessage)
			if (mb.getIsPredefined() && mb.getName().equals("trsh box"))
				this.messageRepository.delete(message);
			else {
				trashBox = this.messageBoxService.findTrashBoxActor(principal);
				this.move(message, trashBox);
			}

	}

	public Message findOne(final int messageId) {
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	//Other business methods -----------------------------

	public void move(final Message message, final MessageBox destination) {
		Actor principal;
		Collection<MessageBox> origin;
		Collection<Message> allMessagesOrigin;

		Collection<MessageBox> messageBoxesMessage;
		Collection<Message> updatedOriginBox;
		Collection<Message> updatedDestinationBox;
		Message result;

		Assert.notNull(message);
		Assert.notNull(destination);

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(destination.getId() != 0);

		updatedOriginBox = new ArrayList<Message>();
		origin = new ArrayList<MessageBox>();
		allMessagesOrigin = new ArrayList<Message>();
		messageBoxesMessage = new ArrayList<MessageBox>();

		origin = message.getMessageBoxes();
		for (final MessageBox mb : origin) {
			allMessagesOrigin.addAll(mb.getMessages());
			Assert.notNull(allMessagesOrigin);
		}
		Assert.isTrue(allMessagesOrigin.contains(message));
		messageBoxesMessage.add(destination);

		message.setMessageBoxes(messageBoxesMessage);

		result = this.messageRepository.saveAndFlush(message);

		if (allMessagesOrigin.contains(result)) {
			Assert.isTrue(allMessagesOrigin.contains(result));
			allMessagesOrigin.remove(result);

		}
		updatedOriginBox = allMessagesOrigin;
		for (final MessageBox box : origin)
			box.setMessages(updatedOriginBox);

		updatedDestinationBox = destination.getMessages();
		Assert.isTrue(!updatedDestinationBox.contains(result));
		updatedDestinationBox.add(result);

	}

	public void broadcast(final Message m) {
		Administrator principal;
		String subject;
		String body;
		String priority;
		Collection<Actor> recipients;
		Collection<Actor> actorsBroadcast;
		Collection<MessageBox> inBoxRecipients;

		Collection<Actor> sender;
		Collection<MessageBox> outBoxSender;
		Collection<Message> messages;

		boolean isSpam;
		Date sendMoment;

		Message outBoxMessage;

		Assert.notNull(m);

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		subject = m.getSubject();
		body = m.getBody();
		priority = m.getPriority();
		isSpam = false;

		sendMoment = new Date(System.currentTimeMillis() - 1);

		recipients = this.actorService.findAll();
		Assert.notNull(recipients);

		actorsBroadcast = new ArrayList<Actor>();
		inBoxRecipients = new ArrayList<MessageBox>();
		messages = new ArrayList<Message>();

		for (final Actor a : recipients)
			if (!(a instanceof Administrator)) {
				final Message message = this.create();
				message.setSubject(subject);
				message.setBody(body);
				message.setPriority(priority);
				message.setSender(principal);

				actorsBroadcast.add(a);

				message.setIsSpam(isSpam);
				message.setSendMoment(sendMoment);

				final MessageBox inBox = this.messageBoxService.findInBoxActor(a);
				messages = inBox.getMessages();
				messages.add(m);
				inBox.setMessages(messages);
				inBoxRecipients.add(inBox);
				message.setMessageBoxes(inBoxRecipients);
				message.setRecipients(actorsBroadcast);

				this.messageRepository.save(message);
			}

		outBoxMessage = this.create();
		outBoxMessage.setSubject(subject);
		outBoxMessage.setBody(body);
		outBoxMessage.setPriority(priority);
		outBoxMessage.setSender(principal);
		outBoxMessage.setIsSpam(isSpam);
		outBoxMessage.setSendMoment(sendMoment);

		final MessageBox outBox = this.messageBoxService.findOutBoxActor(principal);

		Collection<Message> messagesOutBox = new ArrayList<Message>();
		messagesOutBox = outBox.getMessages();
		messagesOutBox.add(outBoxMessage);
		outBox.setMessages(messagesOutBox);

		outBoxSender = new ArrayList<MessageBox>();
		outBoxSender.add(outBox);
		outBoxMessage.setMessageBoxes(outBoxSender);

		sender = new ArrayList<Actor>();
		sender.add(principal);
		outBoxMessage.setRecipients(sender);

		this.messageRepository.save(outBoxMessage);
	}

	public Message createAndSaveStatus(final Actor actor, final String body, final Date moment) {
		Message result;
		Collection<Actor> recipients;
		Collection<MessageBox> boxes;
		MessageBox outBox;
		MessageBox inBox;
		recipients = new ArrayList<Actor>();
		boxes = new ArrayList<MessageBox>();

		result = new Message();
		result.setSender(actor);
		recipients.add(actor);
		result.setRecipients(recipients);
		result.setSendMoment(moment);
		result.setIsSpam(false);
		outBox = this.messageBoxService.findOutBoxActor(actor);
		inBox = this.messageBoxService.findInBoxActor(actor);
		boxes.add(outBox);
		boxes.add(inBox);
		result.setMessageBoxes(boxes);
		result.setBody(body);
		result.setSubject("Status updated");
		result.setPriority("HIGH");

		this.messageRepository.save(result);

		return result;
	}

}
