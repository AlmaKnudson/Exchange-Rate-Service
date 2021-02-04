package com.example.demo.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Creating a bare bones xml parser. Normally, I would use a dedicated library like Jackson for this instead
 * of working directly with the DOM.
 */
@Service
public class Parser implements DownloaderChannel {

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    private File xmlFile;
    private final Map<String, Double> euroCurrencyMap;

    public Parser() {
        euroCurrencyMap = new HashMap<>();
        scheduleDailyExchangeRatesDownload();
    }

    public Map<String, Double> getEuroCurrencyMap() {
        return this.euroCurrencyMap;
    }

    private void parseXmlToEuroCurrencyMap() {
        try {
            Document document = getDocument();
            NodeList nodes = getNodeListFromDocument(document);
            buildCurrencyMap(euroCurrencyMap, nodes);
        } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException exception) {
            //Handle exceptions at another time.
            logger.error("An error occurred while parsing the xml file.", exception);
        } finally {
            logger.info(String.format("Euro Currency map: %s", euroCurrencyMap.toString()));
        }
    }

    private void buildCurrencyMap(Map<String, Double> euroCurrencyMap, NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element element = (Element) nodes.item(i);
            String currency = element.getAttribute("currency");
            Double exchangeRate = Double.parseDouble(element.getAttribute("rate"));
            euroCurrencyMap.put(currency, exchangeRate);
        }
    }

    private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(xmlFile);
        document.getDocumentElement().normalize(); //optional, but probably a good idea to normalize the document
        return document;
    }

    private void scheduleDailyExchangeRatesDownload() {
        ScheduledExecutorService session = Executors.newSingleThreadScheduledExecutor();
        XmlFileDownloader xmlFileDownloader = new XmlFileDownloader();
        xmlFileDownloader.addObserver(this);//Listen for fileDownloader triggers
        session.scheduleAtFixedRate(xmlFileDownloader, 0, 1, TimeUnit.DAYS);
    }

    private NodeList getNodeListFromDocument(Document document) throws XPathExpressionException {
        //I prefer to use xPaths
        XPath xPath = XPathFactory.newInstance().newXPath();
        return (NodeList) xPath.evaluate("/Envelope/Cube/Cube/Cube",
                document, XPathConstants.NODESET);
    }

    @Override
    public void downloadFinished(File file) {
        this.xmlFile = file;
        parseXmlToEuroCurrencyMap();
    }
}
