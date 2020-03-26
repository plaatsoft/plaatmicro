package nl.plaatsoft.micro.schema;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import nl.plaatsoft.micro.core.Utils;

/**
 * The Class MicroInventorySubscribeTest.
 * 
 * @author wplaat
 */
public class MicroInventorySubscribeTest {
	
	/**
	 * Micro Inventory Subscribe.
	 */
	@Test
	 public void microInventorySubscribe() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());
		
		InventorySubscribe InventorySubscribe = new InventorySubscribe();
		InventorySubscribe.setName("inventory1");
		InventorySubscribe.setDescription("inventory1 description");
		InventorySubscribe.setSource("source1");
				
		MicroInventorySubscribe subscribe = new  MicroInventorySubscribe();
		subscribe.setMeta(meta);
		subscribe.setInventorySubscribe(InventorySubscribe);
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MicroInventorySubscribe.class);
			Marshaller marshaller = jaxbContext.createMarshaller();			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			
			StringWriter sw = new StringWriter();
			marshaller.marshal(subscribe, sw);
					
			assertEquals(535, sw.toString().length());
		} catch (Exception e) {
			
		}
	}
}
