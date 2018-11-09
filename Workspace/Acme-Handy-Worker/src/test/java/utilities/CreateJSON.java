package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;

import utilities.internal.SchemaPrinter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Application;
import domain.Category;
import domain.FixUpTask;
import domain.Money;
import domain.Warranty;

public class CreateJSON {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		final CreateJSON object = new CreateJSON();
		object.run();
	}

	private void run() throws JsonGenerationException, JsonMappingException,
			IOException {
		final ObjectMapper mapper = new ObjectMapper();

		final FixUpTask fix = this.createSample();

		mapper.writeValue(
				new File(
						"C:\\Documents and Settings\\Student\\Desktop\\FixUpTask.json"),
						fix);

		String jsonInString = mapper.writeValueAsString(fix);
		System.out.println(jsonInString);

		jsonInString = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(fix);
		SchemaPrinter.print(jsonInString);

	}

	private FixUpTask createSample() {
				
		//Creacion de categoria, money, application y warranty
		
		Category c = new Category();
		c.setEnglishName("Category");
		c.setSpanishName("Categoría");
		
		Money mnfix = new Money();
		mnfix.setQuantity(200.);
		
		Money mnhandy = new Money();
		mnhandy.setQuantity(180.);
		
		Application ap1 = new Application();
		ap1.setRegisteredMoment(LocalDate.parse("2018-10-30").toDate());
		ap1.setStatus("PENDING");
		ap1.setOfferedPrice(mnhandy);
		ap1.setComment("Comment del handy");
		
		Warranty w = new Warranty();
		w.setTitle("Warranty");
		w.setTerms("Terms");
		w.setIsFinal(false);

		
		// Creacion de fixuptask
		
		Collection<FixUpTask> lsfix = new ArrayList<>();
		
		FixUpTask f = new FixUpTask();

		f.setTicker("301018-1xlz3N");
		f.setPublishedMoment(LocalDate.now().toDate());
		f.setDescription("Desciption");
		f.setAddress("ETSII - Sevilla");
		f.setMaxPrice(mnfix);
		f.setStartMoment(LocalDate.parse("2018-10-29").toDate());
		f.setEndMoment(LocalDate.parse("2018-10-30").toDate());
		f.setCategory(c);
		f.setWarranty(w);
		
		Collection<Application> fixApplications = new ArrayList<>();
		fixApplications.add(ap1);
		
		f.setApplication(fixApplications);
		
		lsfix.add(f);


		return f;

	}
}
