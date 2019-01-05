package converters;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MapToStringConverter implements
		Converter<Map<String, String>, String> {

	@Override
	public String convert(final Map<String, String> map) {
		String res = "";
		int i = 0;

		if (map == null)
			res = null;
		else {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (i == 0) {
					res = "{" + entry.getKey() + "=" + entry.getValue() + ";";
					i++;
				} else if (i == map.size() - 1) {
					res += entry.getKey() + "=" + entry.getValue() + "}";
					i++;
				} else {
					res += entry.getKey() + "=" + entry.getValue() + ";";
					i++;
				}
			}
		}
		return res;
	}
}
