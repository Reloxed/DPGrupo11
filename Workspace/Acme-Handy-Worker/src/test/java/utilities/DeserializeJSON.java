package utilities;

import java.io.File;
import java.io.IOException;

import utilities.internal.SchemaPrinter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.FixUpTask;

public class DeserializeJSON {

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		final DeserializeJSON object = new DeserializeJSON();
		object.run();
	}

	private void run() throws JsonParseException, JsonMappingException,
			IOException {
		final ObjectMapper mapper = new ObjectMapper();

		final FixUpTask f = mapper
				.readValue(
						new File(
								"C:\\Documents and Settings\\Student\\Desktop\\FixUpTask.json"),
						FixUpTask.class);

		SchemaPrinter.print(f);
	}
}
