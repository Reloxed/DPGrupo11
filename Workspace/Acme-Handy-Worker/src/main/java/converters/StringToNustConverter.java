package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.NustRepository;


import domain.Nust;


@Component
@Transactional
public class StringToNustConverter implements Converter<String, Nust>{

	@Autowired
	NustRepository nustRepository;
	
	@Override
	public Nust convert(final String text) {
		Nust result;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.nustRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
	
}
