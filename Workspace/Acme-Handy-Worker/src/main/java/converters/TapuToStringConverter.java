package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tapu;

@Component
@Transactional
public class TapuToStringConverter implements Converter<Tapu, String> {

	@Override
	public String convert(final Tapu tapu) {
		String result;

		if (tapu == null)
			result = null;
		else
			result = String.valueOf(tapu.getId());
		return result;
	}
}
