package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TonemaRepository;
import domain.Tonema;

@Component
@Transactional
public class StringToTonemaConverter implements Converter<String, Tonema>{

	@Autowired
	TonemaRepository	tonemaRepository;


	@Override
	public Tonema convert(final String text) {
		Tonema result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.tonemaRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
