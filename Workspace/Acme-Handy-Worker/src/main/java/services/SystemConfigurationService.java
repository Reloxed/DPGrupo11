
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	//Managed repository

	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;


	//Supporting services

	//Simple CRUD Methods

	public SystemConfiguration create() {
		return new SystemConfiguration();
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> systemConfigurations;

		systemConfigurations = this.systemConfigurationRepository.findAll();

		return systemConfigurations;
	}

	public SystemConfiguration findMySystemConfiguration() {
		final SystemConfiguration result;

		result = this.systemConfigurationRepository.findAll().get(0);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);

		SystemConfiguration result;
		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.notNull(this.systemConfigurationRepository.findOne(systemConfiguration.getId()));
		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	//Other business methods

}
