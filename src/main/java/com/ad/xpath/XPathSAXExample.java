package com.ad.xpath;

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

public class XPathSAXExample {

	public static void main(String args[]) throws Exception {
		XPathSAXExample xpsexample = new XPathSAXExample();
		xpsexample.runApp("src/main/resources/inventory.xml");
	}

	/**
	 * Run the application
	 */

	public void runApp(String filename) throws Exception {

		/////////////////////////////////////////////
		// The following initialization code is specific to Saxon
		// Please refer to SaxonHE documentation for details
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
		// End Saxon specific code
		/////////////////////////////////////////////

		XPathExpression findComputers = xpExpression.compile("count(//computer)");

		Number countResults = (Number) findComputers.evaluate(treeInfo, XPathConstants.NUMBER);
		System.out.println("1. There are " + countResults + " computers in the inventory.");
		outputSeparator();

		// Get a list of the serial numbers
		// The following expression gets a set of nodes that have a serialno attribute,
		// then extracts the serial numbers from the attribute and finally creates a
		// list of nodes that contain the serial numbers.
		XPathExpression findSerialNos = xpExpression.compile("//computer[@serialno]/@serialno");

		List resultNodeList = (List) findSerialNos.evaluate(inputSrc, XPathConstants.NODESET);
		if (resultNodeList != null) {
			int count = resultNodeList.size();
			System.out.println("2. There are " + count + " serial numbers:");

			// Go through each node in the list and display the serial number.
			for (int i = 0; i < count; i++) {
				NodeInfo cNode = (NodeInfo) resultNodeList.get(i);
				String name = cNode.getStringValue();
				System.out.println("Serial Number:" + name);
			}
		}
		outputSeparator();

		// Finish when the user enters "."
		System.out.println("Finished.");
	}

	// Helper method to pretty up the output
	public static void outputSeparator() {
		System.out.println("=+=+=+=+=+=+=+=+");
	}

}