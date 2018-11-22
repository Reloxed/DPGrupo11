package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Service
@Transactional
public class UserAccountService {

	// Managed Repository
	
	@Autowired
	private UserAccountRepository userAccountRepository;
		
		
	// Supporting Services

		
		
	// Simple CRUD Methods
	
	
	
	// Other business methods
	
//	public UserAccount getUserAccountByCreditCard (CreditCard creditCard) {
//		UserAccount userAccount = new UserAccount(userAccountRepository);
//	}
	
	
}
