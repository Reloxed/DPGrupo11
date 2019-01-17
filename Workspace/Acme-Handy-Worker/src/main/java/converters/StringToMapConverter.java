package converters;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StringToMapConverter implements
		Converter<String, Map<String, String>> {

	@Override
	public Map<String, String> convert(final String text) {
		Map<String, String> map;
		String value;
		try {
			value = StringUtils.substringBetween(text, "{", "}");
			String[] keyValuePairs = value.split(", ");

			map = new HashMap<>();

			for (String pair : keyValuePairs) {
				String[] entry = pair.split("=");
				map.put(entry[0].trim(), entry[1].trim());
			}
			
		} catch (Throwable oops){
			
			throw new IllegalArgumentException("category.wrongName",oops);
		}
		
		return map;

	}
}
