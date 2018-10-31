package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Application;
import domain.Category;
import domain.FixUpTask;
import domain.Money;
import domain.Status;
import domain.Warranty;

public class SerializeJSON {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		final SerializeJSON object = new SerializeJSON();
		object.run();
	}

	private void run() throws JsonGenerationException, JsonMappingException,
			IOException {
		final ObjectMapper mapper = new ObjectMapper();

		final FixUpTask f = this.createSample();

		mapper.writeValue(
				new File(
						"C:\\Documents and Settings\\Student\\Desktop\\FixUpTask.json"),
				f);

		String jsonInString = mapper.writeValueAsString(f);
		System.out.println(jsonInString);

		jsonInString = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(f);
		System.out.println(jsonInString);

	}

	private FixUpTask createSample() {
		final FixUpTask f = new FixUpTask();

		f.setTicker("301018-1xlz3N");
		f.setPublishedTime(LocalDate.now().toDate());
		f.setDescription("Desciption");
		f.setAddress("ETSII - Sevilla");

		Money mn = new Money();
		mn.setQuantity(200.);

		f.setMaxPrice(mn);

		f.setStartMoment(LocalDate.parse("2018-10-29").toDate());
		f.setEndMoment(LocalDate.parse("2018-10-30").toDate());

		Category c = new Category();
		c.setEnglishName("Category");
		c.setSpanishName("Categoría");

		f.setCategory(c);

		Warranty w = new Warranty();
		w.setTitle("Warranty");
		w.setTerms("Terms");
		w.setIsFinal(false);

		f.setWarranty(w);

		Collection<Application> ls = new ArrayList<>();

		Application ap0 = new Application();
		ap0.setRegisteredTime(LocalDate.parse("2018-10-29").toDate());
		ap0.setStatus(Status.PENDING);
		ap0.setOfferedPrice(mn);
		ap0.setComment("Comment");

		Application ap1 = new Application();
		ap1.setRegisteredTime(LocalDate.parse("2018-10-30").toDate());
		ap1.setStatus(Status.PENDING);
		ap1.setOfferedPrice(mn);
		ap1.setComment("Comment");

		ls.add(ap0);
		ls.add(ap1);

		f.setApplication(ls);

		return f;

	}
}
