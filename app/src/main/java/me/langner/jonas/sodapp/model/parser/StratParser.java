package me.langner.jonas.sodapp.model.parser;

import android.content.Intent;
import me.langner.jonas.sodapp.model.rainbow.Operator;
import me.langner.jonas.sodapp.model.rainbow.Strat;
import org.w3c.dom.Node;

/**
 * Behandelt die Rückgaben über Strats.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public abstract class StratParser extends SimpleXMLParser {

    /**
     * Erstellt ein neues Lese-Objekt.
     * @param xml Der XML-String.
     */
    public StratParser(String xml) {
        super(xml);

        System.out.println(xml);
    }

    @Override
    public void start() {
        readInformation();

        onFinish();
    }

    /**
     * Liest Informationen der Strategien aus.
     */
    private void readInformation() {
        if (getDocument().getElementsByTagName("strat-information").getLength() <= 0 ||getDocument().getElementsByTagName("strat").getLength() <= 0)
            return;

        for (int index = 0; index < getDocument().getElementsByTagName("strat").getLength(); index++) {
            Node stratNode = getDocument().getElementsByTagName("strat").item(index);
            Strat strat = null;

            /* Alle Informationen auslesen */
            for (int i = 0; i < stratNode.getChildNodes().getLength(); i++) {
                Node child = stratNode.getChildNodes().item(i);

                switch (child.getNodeName()) {
                    case "id":
                        try {
                            if (Strat.stratExists(Integer.parseInt(child.getTextContent()))) {
                                strat = Strat.getStrat(Integer.parseInt(child.getTextContent()));
                            }
                        }
                        catch (RuntimeException ex) {}
                        break;
                    case "user-operator":

                        if (strat != null) {
                            Operator operator = Operator.getOperator(child.getTextContent(), null);

                            if (operator.getImageUrl() != null) {
                                strat.setUserOperator(operator);
                            }
                        }
                        break;
                }
            }
        }
    }
}
