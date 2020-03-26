package nl.plaatsoft.micro.schema;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import nl.plaatsoft.micro.core.Utils;

/**
 * The Class MicroStatusPublishTest.
 * 
 * @author wplaat
 */
public class MicroStatusPublishTest {
	
	/**
	 * Micro status publish.
	 */
	@Test
	 public void microStatusPublish() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());
		
		StatusPublish statusPublish = new StatusPublish();
		statusPublish.setDt(Utils.getXMLGregorianCalendarNow());
		statusPublish.setInventoryId(UUID.randomUUID().toString());
		statusPublish.setState(1);
		
		MicroStatusPublish publish = new  MicroStatusPublish();
		publish.setMeta(meta);
		publish.getStatusPublish().add(statusPublish);
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MicroStatusPublish.class);
			Marshaller marshaller = jaxbContext.createMarshaller();			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			
			StringWriter sw = new StringWriter();
			marshaller.marshal(publish, sw);
					
			assertEquals(536, sw.toString().length());
		} catch (Exception e) {
			
		}
	}
}
