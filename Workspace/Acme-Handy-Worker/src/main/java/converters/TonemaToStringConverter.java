package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tonema;

@Component
@Transactional
public class TonemaToStringConverter implements Converter<Tonema, String>{
	
	@Override
	public String convert(final Tonema tonema) {
		String result;

		if (tonema == null)
			result = null;
		else
			result = String.valueOf(tonema.getId());
		return result;
	}

}
