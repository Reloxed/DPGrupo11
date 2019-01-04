
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EmergencyContactRepository;
import domain.EmergencyContact;

@Component
@Transactional
public class StringToEmergencyContactConverter implements Converter<String, EmergencyContact> {

	@Autowired
	EmergencyContactRepository	emergencyContactRepository;


	@Override
	public EmergencyContact convert(final String text) {
		EmergencyContact result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.emergencyContactRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
