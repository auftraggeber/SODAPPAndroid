package me.langner.jonas.sodapp.model.parser;

import android.content.Intent;
import me.langner.jonas.sodapp.model.rainbow.GameMap;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import me.langner.jonas.sodapp.model.user.UserData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teilt einen XML-String mit einer bestimmten Struktur in Java-Objekte auf.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public abstract class InformationParser extends SimpleXMLParser {

    /**
     * Übergibt dem Objekt den String und startet das teilen.
     * @param information Der XML-String.
     */
    public InformationParser(String information) {
        super(information);
    }

    /**
     * Wird direkt zum Start aufgerufen (von Superklasse).
     */
    @Override
    public void start() {
        readUserInformation();

        /* nächsten Schritte nur, wenn angemeldet */
        if (UserData.USER_DATA.isLoggedIn()) {
            // angemeldet
            readMaps();
            readOperators();
            readStrats();
        }

        onFinish();
    }

    /**
     * Liest Fehler aus.
     */
    private void readErrors() {
        /* überprüfen, ob struktur stimmt. */
        if (getDocument().getElementsByTagName("error").getLength() <= 0) {
            Node errorInformation = getDocument().getElementsByTagName("user-information").item(0);

            if (errorInformation.getChildNodes().getLength() >= 2) {
                // struktur stimmt - überprüfen, ob fehler der nutzerdaten (UD#...)
                if (errorInformation.getChildNodes().item(1).getTextContent().startsWith("UD#")) {
                    // fehler der nutzerdaten
                    UserData.USER_DATA.setError(errorInformation.getChildNodes().item(0).getTextContent());
                }
            }
        }
    }

    /**
     * Liest aus, ob der Nutzer angemeldet ist.
     */
    private void readUserInformation() {
        if (getDocument().getElementsByTagName("user-information").getLength() <= 0) {
            readErrors();
            return;
        }

        Node userInformation = getDocument().getElementsByTagName("user-information").item(0);

        for (int childIndex = 0; childIndex < userInformation.getChildNodes().getLength(); childIndex++) {
            Node child = userInformation.getChildNodes().item(childIndex);

            switch (child.getNodeName().toLowerCase()) {
                case "status":
                    UserData.USER_DATA.setLoggedIn(child.getTextContent().toLowerCase().equals("logged_in".toLowerCase()));
                    break;
                case "group-name":
                    UserData.USER_DATA.setGroup(child.getTextContent());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *  Liest Maps aus und erstellt die Objekte.
     */
    private void readMaps() {
        if (getDocument().getElementsByTagName("maplist").getLength() <= 0 ||
                getDocument().getElementsByTagName("maplist").item(0).getChildNodes().getLength() <= 0)
            return;

        Node mapList = getDocument().getElementsByTagName("maplist").item(0);

        for (int index = 0; index < mapList.getChildNodes().getLength(); index++) {
            Node map = mapList.getChildNodes().item(index);

            /* Struktur überprüfen */
            if (map.getChildNodes().getLength() == 2) {
                // Struktur ist korrekt: Daten auslesen

                String name = map.getChildNodes().item(0).getTextContent();
                String url = map.getChildNodes().item(1).getTextContent();

                /* Objekt erstellen */
                GameMap.getGameMap(name, url);
            }
        }
    }

    /**
     * Liest Maps aus.
     * Liest Operator aus und erstellt die Objekte.
     */
    private void readOperators() {
        if (getDocument().getElementsByTagName("operatorlist").getLength() <= 0 ||
                getDocument().getElementsByTagName("operatorlist").item(0).getChildNodes().getLength() <= 0)
            return;

        Node operatorList = getDocument().getElementsByTagName("operatorlist").item(0);

        for (int index = 0; index < operatorList.getChildNodes().getLength(); index++) {
            Node operator = operatorList.getChildNodes().item(index);

            /* Struktur überprüfen */
            if (operator.getChildNodes().getLength() == 3) {
                // Struktur ist korrekt: Daten auslesen

                String name = operator.getChildNodes().item(0).getTextContent();
                Operator.Role role = (operator.getChildNodes().item(1).getTextContent().toLowerCase().equals("defender")) ?
                        Operator.Role.DEFENDER : Operator.Role.ATTACKER;
                String url = operator.getChildNodes().item(2).getTextContent();

                /* Objekt erstellen */
                Operator.getOperator(name, url).setRole(role);
            }
        }
    }

    /**
     * Liest alle Strategien.
     */
    private void readStrats() {
        // Fehler ausschließen
        if (getDocument().getElementsByTagName("strat").getLength() <= 0)
            return;

        NodeList strats = getDocument().getElementsByTagName("strat");

        for (int index = 0; index < strats.getLength(); index++) {
            Strat stratObject = null;

            int id = -1;
            String name = null;
            String map = null;
            Operator.Role role = null;
            Map<Operator, List<String>> operators = new HashMap<>();

            Node strat = strats.item(index);
            // Fehler ausschließen
            if (strat.getChildNodes().getLength() <= 0)
                continue;

            for (int i = 0; i < strat.getChildNodes().getLength(); i++) {
                Node child = strat.getChildNodes().item(i);

                switch (child.getNodeName().toLowerCase()) {
                    case "id":
                        id = Integer.parseInt(child.getTextContent());
                        break;
                    case "name":
                        name = child.getTextContent();
                        break;
                    case "map":
                        if (child.getChildNodes().getLength() >= 2)
                            map = child.getChildNodes().item(0).getTextContent();
                        else
                            map = child.getTextContent();
                    case "role":
                        if (child.getTextContent().toLowerCase().equals("defender"))
                            role = Operator.Role.DEFENDER;
                        else
                            role = Operator.Role.ATTACKER;
                        break;
                    default:
                        break;
                }

                /* operator hinzufügen -> etwas komplexer, da listen */
                if (child.getNodeName().equals("operators") && child.getChildNodes().getLength() > 0) {

                    // jeden operator auslesen
                    for (int c = 0; c < child.getChildNodes().getLength(); c++) {
                        Node operatorNode = child.getChildNodes().item(c);

                        if (operatorNode.getNodeName().toLowerCase().equals("operator") &&
                                operatorNode.getChildNodes().getLength() >= 2) {
                            String operatorName = operatorNode.getChildNodes().item(0).getTextContent();
                            List<String> userList = new ArrayList<>();

                            Operator operator = Operator.getOperator(operatorName, null);

                            String[] users = operatorNode.getChildNodes().item(1).getTextContent().split("\n");
                            for (String user : users) {
                                userList.add(user);
                            }

                            /* zwischenspeichern */
                            operators.put(operator, userList);
                        }
                    }

                }
            }

            if (id > 0 && name != null && map != null && role != null) {
                stratObject = Strat.getStrat(id, name, GameMap.getGameMap(map,null), role);

                /* alle operatoren hinzufügen */
                for (Operator operator : operators.keySet()) {
                    stratObject.addOperator(operator, operators.get(operator));
                }
            }
        }
    }
}
