/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author Rafael da Silva Costa & Team
 * 
 *         This class contains static methods responsible for parsing XML to
 *         Java and vice versa.
 */
public class XMLParser
{
	/**
	 * Saves the specified JAXB annotated object to a .xml file.
	 * @param obj The object to be saved to the .xml file.
	 * @param objClass The object's class.
	 * @param xmlFile The .xml file to be written.
	 * @throws JAXBException If an error was encountered while parsing the object.
	 * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
	 */
	public static <K> void saveToFile(K obj, Class<? extends K> objClass, File xmlFile) throws JAXBException, IOException
	{
		// Creating JAXB context and initializing Marshaller
		JAXBContext ctxt = JAXBContext.newInstance(objClass);
		Marshaller mslr = ctxt.createMarshaller();

		// Set to formatted output
		mslr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Writing to XML file
		mslr.marshal(obj, new BufferedWriter(new FileWriter(xmlFile)));

		// Writing to console
		mslr.marshal(obj, System.out);
	}
	
	/**
	 * Loads a Java object from the specified .xml file.
	 * @param objClass The object's class.
	 * @param xmlFile The .xml file to be parsed.
	 * @throws JAXBException If an error was encountered while parsing the object.
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 */
	@SuppressWarnings("unchecked")
	public static <K> K loadFromFile(Class<K> objClass, File xmlFile) throws JAXBException, FileNotFoundException
	{
		// Creating JAXB context and initializing Unmarshaller
		JAXBContext ctxt = JAXBContext.newInstance(objClass);
		Unmarshaller unmslr = ctxt.createUnmarshaller();

		// Reading from XML file
		K obj = (K) unmslr.unmarshal(new BufferedReader(new FileReader(xmlFile)));
		
		return obj;
	}
}