package me.langner.jonas.sodapp.model.rainbow;

import java.util.*;

/**
 * Behandelt die Maps aus R6.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class GameMap {

    private static final java.util.Map<String, GameMap> maps = new HashMap<>();

    /**
     * Liefert die Instanz einer Map zurück. Verhindert, dass eine Map doppelt existiert.
     * @param name Name der Map.
     * @param imageUrl Link zum Bild der Map.
     * @return Die Instanz der Map, die gefunden wurde.
     */
    public static GameMap getGameMap(String name, String imageUrl) {
        if (maps.containsKey(name) && maps.get(name) != null)
            return maps.get(name);

        return new GameMap(name, imageUrl);
    }

    public static Collection<GameMap> getMaps() {
        return Collections.unmodifiableCollection(maps.values());
    }

    private String name, imageUrl;
    private List<Strat> strats = new ArrayList<>();

    /**
     * Erstellt eine neue Map.
     * @param name Name der Map.
     * @param imageUrl Link zum Bild der Map.
     */
    private GameMap(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;

        GameMap.maps.put(name, this);
    }

    public boolean addStrat(Strat strat) {
        if (!strats.contains(strat))
            strats.add(strat);

        return strats.contains(strat);
    }

    public List<Strat> getStrats() {
        return Collections.unmodifiableList(strats);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
