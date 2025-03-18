package it.dmsoft.flowmanager.agent.api.synch;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dmsoft.flowmanager.common.model.SynchData;

@Service("synchService")
public class SynchService {

	@Value("${flowmanager.agent.config.data.file}")
	private String dataFile;

	public SynchData execute(final SynchData synchData) {
		ObjectMapper om = new ObjectMapper();

		try {
			File file = new File(dataFile);
			om.writeValue(file, synchData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return synchData;
	}

}
