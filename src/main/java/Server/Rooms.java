package Server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Rooms {
    private final Gson gson = new GsonBuilder().create();
    private static final Rooms ROOMS = new Rooms();
    private final Map<String, Room> rooms = new HashMap<>();

    private Rooms() {
       Room globalRoom = new Room("global");
       rooms.put(globalRoom.getName(), globalRoom);
    }
    public synchronized static Rooms getInstance (){
        return ROOMS;
    }
    public synchronized Room getRoom (String name){
        return rooms.get(name);
    }

    public synchronized Map<String, Room> getRooms() {
        return rooms;
    }
    public synchronized boolean isPresent (String name){
        return rooms.containsKey(name);
    }

    public synchronized Room getGlobalRoom (){
        return rooms.get("global");
    }
    public synchronized void addRoom (Room room){
        rooms.put(room.getName(), room);
    }
    public synchronized void removeRoom(String name){
        rooms.remove(name);
    }
    public synchronized String toJSON(int n, String roomName) {
        return gson.toJson(new JsonMessages(rooms.get(roomName).getMsgList(), n));
    }
    public synchronized String toJSONRooms (){
        return gson.toJson(new JsonRooms(rooms));
    }
}
