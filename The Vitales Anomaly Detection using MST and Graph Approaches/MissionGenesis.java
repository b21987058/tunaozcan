import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList humanList = doc.getElementsByTagName("HumanMolecularData").item(0).getChildNodes();
            NodeList vitalesList = doc.getElementsByTagName("VitalesMolecularData").item(0).getChildNodes();

            List<Molecule> humanMolecules = parseMolecules(humanList);
            List<Molecule> vitalesMolecules = parseMolecules(vitalesList);

            molecularDataHuman = new MolecularData(humanMolecules);
            molecularDataVitales = new MolecularData(vitalesMolecules);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Molecule> parseMolecules(NodeList nodeList) {
        List<Molecule> molecules = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String id = element.getElementsByTagName("ID").item(0).getTextContent();
                int bondStrength = Integer.parseInt(element.getElementsByTagName("BondStrength").item(0).getTextContent());
                NodeList bondedMolecules = element.getElementsByTagName("Bonds").item(0).getChildNodes();
                List<String> bonds = new ArrayList<>();
                for (int j = 0; j < bondedMolecules.getLength(); j++) {
                    bonds.add(bondedMolecules.item(j).getTextContent());
                }
                molecules.add(new Molecule(id, bondStrength, bonds));
            }
        }
        return molecules;
    }
}
