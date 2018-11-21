package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageService {

	// Managed repository ---------------------

	@Autowired
	private MessageRepository messageRepository;

	//Supporting services -------------------

	private ActorService actorService;

	//CRUD Methods --------------------------------
	public Message create(){
		Message result;
		Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);{


			result = new Message();
			result.setSender(principal);
			result.setSendMoment(new Date(System.currentTimeMillis()-1));
			
		}
		return result;

		//Other business methods -----------------------------

	}
}