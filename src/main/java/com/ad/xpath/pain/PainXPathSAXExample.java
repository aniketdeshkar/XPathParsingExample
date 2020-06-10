package com.ad.xpath.pain;

import net.sf.saxon.Configuration;
import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.xml.sax.InputSource;

import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.*;
import java.io.File;
import java.util.List;

/**
 * Class XPathSAXExample - Parses the Inventory.xml file and uses the JAXP XPath
 * API to evaluate XPath expressions.
 */

public class PainXPathSAXExample {

	public static void main(String args[]) throws Exception {
		PainXPathSAXExample xpsexample = new PainXPathSAXExample();
		xpsexample.runApp("src/main/resources/pain.001.001.03.xml");
	}

	/**
	 * Run the application
	 */

	public void runApp(String filename) throws Exception {

		// The following initialization code is specific to Saxon Please refer to SaxonHE documentation for details
		System.setProperty("javax.xml.xpath.XPathFactory:" + NamespaceConstant.OBJECT_MODEL_SAXON,
				"net.sf.saxon.xpath.XPathFactoryImpl");

		XPathFactory xpFactory = XPathFactory.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
		XPath xpExpression = xpFactory.newXPath();
		System.err.println("Loaded XPath Provider " + xpExpression.getClass().getName());

		// Build the source document.
		InputSource inputSrc = new InputSource(new File(filename).toURL().toString());
		SAXSource saxSrc = new SAXSource(inputSrc);
		Configuration config = ((XPathFactoryImpl) xpFactory).getConfiguration();
		TreeInfo treeInfo = config.buildDocumentTree(saxSrc);

		XPathExpression findSerialNos = xpExpression.compile("//CstmrCdtTrfInitn/PmtInf/Dbtr/Nm");

		String msgId = (String) findSerialNos.evaluate(inputSrc, XPathConstants.STRING);
		System.out.println("-----------");
		System.out.println(msgId);
		System.out.println("-----------");
	}

}