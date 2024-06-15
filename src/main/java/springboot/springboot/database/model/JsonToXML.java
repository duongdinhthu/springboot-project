package springboot.springboot.database.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;

public class JsonToXML {
    public  void jsonToXml(String name) throws Exception {
        // Đọc file JSON
        String jsonFile = name +".json";
        String jsonString = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(jsonFile)));

        // Chuyển đổi JSON thành XML
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonString);

        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                if (element.isJsonObject()) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    Document xmlDocument = convertJsonToXml(jsonObject);

                    // Ghi ra file XML
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    DOMSource source = new DOMSource(xmlDocument);
                    File xmlFile = new File(name+".xml");
                    FileWriter writer = new FileWriter(xmlFile, true); // Nối vào cuối file
                    StreamResult result = new StreamResult(writer);
                    transformer.transform(source, result);

                    System.out.println("Chuyển đổi thành công. File XML đã được tạo.");
                }
            }
        }
    }

    private static Document convertJsonToXml(JsonObject jsonObject) throws ParserConfigurationException {
        Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element rootElement = xmlDocument.createElement("data");
        xmlDocument.appendChild(rootElement);

        for (String key : jsonObject.keySet()) {
            Element element = xmlDocument.createElement(key);
            element.appendChild(xmlDocument.createTextNode(jsonObject.get(key).getAsString()));
            rootElement.appendChild(element);
        }

        return xmlDocument;
    }
}