package nl.plaatsoft.micro.core;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * The Class Utils.
 * 
 * @author wplaat
 */
public class Utils {

	/**
	 * Gets the XML gregorian calendar now.
	 *
	 * @return the XML gregorian calendar now
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
		} catch (Exception e) {

			return null;
		}
	}
		
	/**
	 * To date.
	 *
	 * @param calendar the calendar
	 * @return the date
	 */
	public static Date toDate(XMLGregorianCalendar calendar){
	 if(calendar == null) {
	     return null;
	 }
     return calendar.toGregorianCalendar().getTime();
    }
}
