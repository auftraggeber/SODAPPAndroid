package me.langner.jonas.sodapp.model.rainbow;

import java.util.*;

/**
 * Behandelt Operatoren aus R6.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class Operator {

    /**
     * Rollen, die ein Operator haben kann.
     */
    public enum Role {
        DEFENDER, ATTACKER;
    }

    private static final java.util.Map<String, Operator> operators = new HashMap<>();

    /**
     * Gibt die Instanz eines Operators zurück. Verhindert, dass ein Operator doppelt erstellt werden kann.
     * @param name Der Name des Operators.
     * @param imageUrl Die Url zum Bild des Operators.
     * @return Die gefundenen Instanz.
     */
    public static Operator getOperator(String name, String imageUrl) {
        if (operators.containsKey(name) && operators.get(name) != null)
            return operators.get(name);

        return new Operator(name, imageUrl);
    }

    public static Collection<Operator> getOperators() {
        return Collections.unmodifiableCollection(operators.values());
    }

    private String name, imageUrl;
    private Role role;

    /**
     * Erstellt einen neuen Operator.
     * @param name Der Name des Operators.
     * @param imageUrl Die Url zum Bild des Operators.
     */
    private Operator(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl.replace("ä","a");

        Operator.operators.put(name, this);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasRole(Role role) {
        if (role != null && this.role != null) {
            return this.role.equals(role);
        }

        return (role == null && this.role == null);
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
