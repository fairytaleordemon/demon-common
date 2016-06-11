package com.yuanbosu.common.xml;

public class JaxbUtils {

//	public static String convertToXml(Object obj) throws JAXBException {
//		return convertToXml(obj, "UTF-8");
//	}
//
//	public static String convertToXml(Object obj, String encoding) throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
//		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
//		marshaller.setProperty("jaxb.encoding", encoding);
//
//		StringWriter writer = new StringWriter();
//		marshaller.marshal(obj, writer);
//		return writer.toString();
//	}
//
//	public static <T> T converyToJavaBean(String xml, Class<T> c) throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance(new Class[] { c });
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		return unmarshaller.unmarshal(new StringReader(xml));
//	}
}
