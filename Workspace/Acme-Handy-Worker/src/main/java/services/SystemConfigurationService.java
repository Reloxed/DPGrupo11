
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.Administrator;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	// Supporting services

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private ActorService					actorService;


	// Constructors ------------------------------------

	public SystemConfigurationService() {
		super();
	}

	// Simple CRUD Methods

	public SystemConfiguration create() {
		Assert.notNull(this.administratorService.findByPrincipal());

		final SystemConfiguration systemConfiguration = new SystemConfiguration();
		systemConfiguration.setSystemName("Acme-Handy-Worker");
		systemConfiguration.setWelcomeMessageEn("Welcome to Acme Handy Worker!  Price, quality, and trust in a single place");
		systemConfiguration.setWelcomeMessageEs("¡Bienvenidos a Acme Handy Worker!  Precio, calidad y confianza en el mismo sitio");
		systemConfiguration.setBanner("https://irp-cdn.multiscreensite.com/3737b2b6/dms3rep/multi/desktop/4-2000x889.jpg");
		systemConfiguration.setVAT(0.21);
		systemConfiguration.setListCreditCardMakes("VISA,MASTER,DINNERS,AMEX");
		systemConfiguration.setCountryCode("+034");
		systemConfiguration.setTimeResultsCached(1);
		systemConfiguration.setMaxResults(10);
		systemConfiguration.setSpamWords("sex,viagra,cialis,one million,you've been selected,nigeria,sexo,un millon,ha sido seleccionado");
		systemConfiguration.setPositiveWords("good,fantastic,excellent,great,amazing,terrific,beautiful,bueno,fantastico,excelente,genial,increible,asombroso,bonito");
		systemConfiguration.setNegativeWords("not,bad,horrible,average,disaster,no,malo,mediocre,desastre,desastroso");
		return systemConfiguration;
	}

	public Collection<SystemConfiguration> findAll() {
		Assert.notNull(this.administratorService.findByPrincipal());
		Collection<SystemConfiguration> systemConfigurations;

		systemConfigurations = this.systemConfigurationRepository.findAll();

		return systemConfigurations;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		Assert.notNull(this.administratorService.findByPrincipal());
		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		systemConfiguration.setId(this.systemConfigurationRepository.findAll().get(0).getId());

		SystemConfiguration result;
		result = this.systemConfigurationRepository.saveAndFlush(systemConfiguration);
		return result;

	}

	// Other business methods

	public SystemConfiguration findMySystemConfiguration() {
		Assert.notNull(this.actorService.findByPrincipal());
		final SystemConfiguration result;

		result = this.systemConfigurationRepository.findAll().get(0);

		return result;
	}

	public String findSpamWords() {
		final String result;

		result = this.systemConfigurationRepository.findAll().get(0).getSpamWords();

		return result;
	}
}
