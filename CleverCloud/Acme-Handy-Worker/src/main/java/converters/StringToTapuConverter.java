package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TapuRepository;
import domain.Tapu;

@Component
@Transactional
public class StringToTapuConverter implements Converter<String, Tapu> {

	@Autowired
	TapuRepository tapuRepository;

	@Override
	public Tapu convert(final String text) {
		Tapu res;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.tapuRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
