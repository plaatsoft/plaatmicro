package nl.plaatsoft.micro.schema;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import nl.plaatsoft.micro.core.Utils;

/**
 * The Class MicroInventoryPublishTest.
 *  
 * @author wplaat
 */
public class MicroInventoryPublishTest {

	/**
	 * Micro inventory publish.
	 */
	@Test
	public void microInventoryPublish() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		InventoryPublish inventoryPublish = new InventoryPublish();
		inventoryPublish.setInventoryId(UUID.randomUUID().toString());
		inventoryPublish.setName("inventory1");
		inventoryPublish.setDescription("inventory1 description");
		
		MicroInventoryPublish publish = new MicroInventoryPublish();
		publish.setMeta(meta);
		publish.getInventoryPublish().add(inventoryPublish);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MicroInventoryPublish.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter sw = new StringWriter();
			marshaller.marshal(publish, sw);

			assertEquals(566, sw.toString().length());
		} catch (Exception e) {

		}
	}
}
