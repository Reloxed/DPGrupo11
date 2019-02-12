package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Nust;




@Component
@Transactional
public class NustToStringConverter implements Converter<Nust, String>{

	
	@Override
	public String convert(final Nust nust) {
		String result;

		if (nust == null)
			result = null;
		else
			result = String.valueOf(nust.getId());
		return result;
	}
	
	
}
