package nl.plaatsoft.micro.schema;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import nl.plaatsoft.micro.core.Utils;

public class MicroInfoRequestTest {

	/**
	 * Micro inventory publish.
	 */
	@Test
	public void microInfoRequest() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		InfoRequest infoRequest = new InfoRequest();
		infoRequest.setInventory(true);
		infoRequest.setStatus(true);
		
		MicroInfoRequest request = new MicroInfoRequest();
		request.setMeta(meta);
		request.setInfoRequest(infoRequest);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MicroInfoRequest.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter sw = new StringWriter();
			marshaller.marshal(request, sw);

			assertEquals(450, sw.toString().length());
		} catch (Exception e) {

		}
	}
}
