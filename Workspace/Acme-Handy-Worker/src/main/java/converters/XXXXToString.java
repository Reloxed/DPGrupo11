package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.XXXX;

@Component
@Transactional
public class XXXXToString implements Converter<XXXX, String> {

	@Override
	public String convert(final XXXX xxxx) {
		String result;

		if (xxxx == null)
			result = null;
		else
			result = String.valueOf(xxxx.getId());
		return result;
	}
}
