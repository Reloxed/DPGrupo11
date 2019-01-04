
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EmergencyContact;

@Component
@Transactional
public class EmergencyContactToStringConverter implements Converter<EmergencyContact, String> {

	@Override
	public String convert(final EmergencyContact emergencyContact) {
		String result;

		if (emergencyContact == null)
			result = null;
		else
			result = String.valueOf(emergencyContact.getId());

		return result;
	}

}
