package nl.plaatsoft.micro.schema;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import nl.plaatsoft.micro.core.Utils;

/**
 * The Class MicroStatusSubscribeTest.
 * 
 * @author wplaat
 */
public class MicroStatusSubscribeTest {

	/**
	 * Micro status Subscribe.
	 */
	@Test
	public void microStatusSubscribe() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		StatusSubscribe statusSubscribe = new StatusSubscribe();
		statusSubscribe.setName("status1");
		statusSubscribe.setDescription("status1 description");
		statusSubscribe.setSource("source1");

		MicroStatusSubscribe subscribe = new MicroStatusSubscribe();
		subscribe.setMeta(meta);
		subscribe.setStatusSubscribe(statusSubscribe);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MicroStatusSubscribe.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter sw = new StringWriter();
			marshaller.marshal(subscribe, sw);

			assertEquals(517, sw.toString().length());
		} catch (Exception e) {

		}
	}
}
