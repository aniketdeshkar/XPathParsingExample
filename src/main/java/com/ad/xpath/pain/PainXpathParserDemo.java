package com.ad.xpath.pain;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class PainXpathParserDemo {

	public static void main(String[] args) {

		try {
			String xmlPath = "src/pain.001.001.03.xml";
			String xsdPath = "src/pain.001.001.03.xsd";

			File inputFile = new File(xmlPath);
			boolean validateXMLSchema = PainUtility.validateXMLSchema(xsdPath, xmlPath);

			if (validateXMLSchema) {
				// Create XPathFactory object
				XPathFactory xpathFactory = XPathFactory.newInstance();
				// Create XPath object
				XPath xpath = xpathFactory.newXPath();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
