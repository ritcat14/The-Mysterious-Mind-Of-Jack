package core;

import java.util.ArrayList;

import handler.Tools;
import handler.Vector;
import entities.Entity;
import entities.Player;

public class Decoder {
    
    private final int TILE_SIZE = 32;
    
    private Player player;
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    
    public void decode(int chapterID){
        entities = new ArrayList<Entity>();
        String file = "/chapters/chapter" + chapterID + "/map.gme";
        tiles = new ArrayList<Tile>();
        String[] fileData = Tools.getData(file);
        int mapWidth = fileData[0].split(",").length;
        int[] tileData = new int[mapWidth * 10];
        int j = 0;
        for (int i = 0; i < 10; i++) {
            String[] parts = fileData[i].split(",");
            for (String s : parts) {
                tileData[j] = Integer.parseInt(s);
                j++;
            }
        }
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < 10; y++) {
                if (tileData[x + y * TILE_SIZE] != 0) tiles.add(new Tile(new Vector(x * TILE_SIZE, y * TILE_SIZE), tileData[x + y * TILE_SIZE], chapterID));
            }
        }
        player = new Player(new Vector(40, 6 * 32), "/player.png");
    }
    
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
    
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    
    public Player getPlayer() {
        return player;
    }
    
}
