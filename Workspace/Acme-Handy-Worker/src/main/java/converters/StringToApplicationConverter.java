
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdministratorRepository;
import domain.Administrator;

@Component
@Transactional
public class StringToApplicationConverter implements Converter<String, Administrator> {

	@Autowired
	AdministratorRepository	applicationRepository;


	@Override
	public Administrator convert(final String text) {
		Administrator result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.applicationRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
