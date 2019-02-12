package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private SystemConfigurationRepository systemConfigurationRepository;

	// Supporting services

	@Autowired
	private AdministratorService administratorService;

	// Constructors ------------------------------------

	public SystemConfigurationService() {
		super();
	}

	// Simple CRUD Methods

	public SystemConfiguration create() {
		Assert.notNull(this.administratorService.findByPrincipal());

		final Map<String, String> wellMap = new HashMap<>();
		wellMap.put(
				"Español",
				"¡Bienvenidos a Acme Handy Worker!  Precio, calidad y confianza en el mismo sitio");
		wellMap.put("English",
				"Welcome to Acme Handy Worker!  Price, quality, and trust in a single place");

		final SystemConfiguration systemConfiguration = new SystemConfiguration();
		systemConfiguration.setSystemName("Acme-Handy-Worker");
		systemConfiguration.setWelcomeMessage(wellMap);
		systemConfiguration
				.setBanner("https://irp-cdn.multiscreensite.com/3737b2b6/dms3rep/multi/desktop/4-2000x889.jpg");
		systemConfiguration.setVAT(0.21);
		systemConfiguration.setListCreditCardMakes("VISA,MASTER,DINNERS,AMEX");
		systemConfiguration.setCountryCode("+034");
		systemConfiguration.setTimeResultsCached(1);
		systemConfiguration.setMaxResults(10);
		systemConfiguration
				.setSpamWords("sex,viagra,cialis,one million,you've been selected,nigeria,sexo,un millon,un millón,ha sido seleccionado");
		systemConfiguration
				.setPositiveWords("good,fantastic,excellent,great,amazing,terrific,beautiful,bueno,fantastico,fantástico,excelente,genial,"
						+ "increíble,increible,asombroso,bonito");
		systemConfiguration
				.setNegativeWords("not,bad,horrible,average,disaster,no,malo,mediocre,desastre,desastroso");
		return systemConfiguration;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		SystemConfiguration result;

		result = this.systemConfigurationRepository
				.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration save(
			final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Administrator principal;

		principal = this.administratorService.findByPrincipal();
		Assert.notNull(principal);

		systemConfiguration.setId(this.systemConfigurationRepository.findAll()
				.get(0).getId());

		SystemConfiguration result;
		result = this.systemConfigurationRepository
				.saveAndFlush(systemConfiguration);
		return result;

	}

	// Other business methods

	public SystemConfiguration findMySystemConfiguration() {
		final SystemConfiguration result;

		result = this.systemConfigurationRepository.findSystemConf();

		return result;
	}

	public String findMyBanner() {

		String result;

		result = this.findMySystemConfiguration().getBanner();

		return result;
	}

	public String findSpamWords() {
		final String result;

		result = this.findMySystemConfiguration().getSpamWords();

		return result;
	}

	public List<String> findSupportedLanguages() {
		final Map<String, String> aux;
		final List<String> result = new ArrayList<>();

		aux = this.findMySystemConfiguration().getWelcomeMessage();
		result.addAll(aux.keySet());

		return result;
	}

	public Double findVAT() {
		final Double result;

		result = this.findMySystemConfiguration().getVAT();

		return result;
	}
	
	public Map<String, String> findWelcomeMessage() {
		final Map<String, String> result;

		result = this.findMySystemConfiguration().getWelcomeMessage();

		return result;
	}

}
