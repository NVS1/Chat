package Client;

import java.util.*;

public class JsonRooms {
    private final List<String> rooms;

    public JsonRooms(Map<String, Room> roomMap) {
        Set<String> strings = roomMap.keySet();
        rooms = new ArrayList<>(strings);
    }
    public List<String> getList() {
        return Collections.unmodifiableList(rooms);
    }
}
