package me.langner.jonas.sodapp.model.rainbow;

import android.util.Property;

import java.util.*;

/**
 * Behandelt Strategien, die auf der Website registriert wurden.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class Strat {

    private static final Map<Integer, Strat> strats = new HashMap<>();

    /**
     * Gibt die Instanz einer Strat zurück. Achtet darauf, dass kein Objekt doppelt existiert.
     * @param id Die ID der Strat.
     * @param name Der Name der Strat.
     * @param map Die Map der Strat.
     * @param role Die {@link me.langner.jonas.sodapp.model.rainbow.Operator.Role} der Strat.
     * @param userOperator Der Operator, der vom Nutzer gespielt wird
     * @return Die gefundene oder neu erstellte Strat.
     */
    public static Strat getStrat(int id, String name, GameMap map, Operator.Role role, Operator userOperator) {
        if (stratExists(id))
            return strats.get(id);

        return new Strat(id, name, map, role, userOperator);
    }

    public static Strat getStrat(int id) {
        if (stratExists(id))
            return strats.get(id);

        return null;
    }

    public static boolean stratExists(int id) {
        return strats.containsKey(id) && strats.get(id) != null;
    }

    public static Collection<Strat> getStrats() {
        return Collections.unmodifiableCollection(strats.values());
    }

    private int id;
    private String name;
    private GameMap map;
    private Operator.Role role;
    private Operator userOperator;
    private Map<Operator, List<String>> operators = new HashMap<>();

    /**
     * Erstellt eine neue Strat.
     * @param id Die zugehörige ID.
     * @param name Der Name der Strat.
     * @param map Die Map der Strat.
     * @param userOperator Der Operator, der vom Nutzer gespielt wird
     * @param role Die {@link me.langner.jonas.sodapp.model.rainbow.Operator.Role} der Strat.
     */
    private Strat(int id, String name, GameMap map, Operator.Role role, Operator userOperator) {
        this.id = id;
        this.name = name;
        this.map = map;
        this.role = role;
        this.userOperator = userOperator;

        strats.put(id, this);
        map.addStrat(this);
    }

    /**
     * Fügt der Strat einen Operator hinzu.
     * @param operator Der Operator.
     * @param users Die Nutzer, die den Operator spielen.
     * @return Gibt an, ob der Operator hinzugefügt werden konnte.
     */
    public boolean addOperator(Operator operator, List<String> users) {
        if (operator.hasRole(null))
            operator.setRole(this.role);

        if (!operators.containsKey(operator) && operator.hasRole(this.role))
            operators.put(operator, users);

        return operators.containsKey(operator);
    }

    public boolean removeOperator(Operator operator) {
        while (operators.containsKey(operator))
            operators.remove(operator);

        return !operators.containsKey(operator);
    }

    public void addUser(Operator operator, String userName) {
        if (!operators.containsKey(operator))
            return;

        List<String> users = getOperatorUsers(operator);

        if (!users.contains(userName))
            users.add(userName);

        operators.put(operator, users);
    }

    public void removeUser(Operator operator, String userName) {
        if (!operators.containsKey(operator))
            return;

        List<String> users = getOperatorUsers(operator);

        while (users.contains(userName))
            users.remove(userName);

        operators.put(operator, users);
    }

    public void setUserOperator(Operator userOperator) {
        this.userOperator = userOperator;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GameMap getMap() {
        return map;
    }

    public Operator.Role getRole() {
        return role;
    }

    public Set<Operator> getOperators() {
        return Collections.unmodifiableSet(operators.keySet());
    }

    public List<String> getOperatorUsers(Operator operator) {
        if (operators.containsKey(operator)) {
            return operators.get(operator);
        }

        return new ArrayList<>();
    }

    public Operator getUserOperator() {
        return userOperator;
    }
}
