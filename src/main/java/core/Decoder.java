package core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import handler.DataHandler;
import handler.Tools;
import handler.Vector;
import entities.Entity;
import entities.Player;
import core.tiles.*;

public class Decoder {
    
    public static int TILE_SIZE = 64;
    
    private Player player;
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    public int mapXPos;
    
    public void decode(Map map, int chapterID) {
        entities = new ArrayList<Entity>();
        String file = "/chapters/chapter" + chapterID + "/map.dll";
        tiles = new ArrayList<Tile>();
        String[] fileData = Tools.getData(file);
        int mapWidth = fileData[0].split(",").length;
        int mapHeight = fileData.length;
        int[] tileData = new int[mapWidth * mapHeight];
        int j = 0;
        for (int i = 0; i < mapHeight; i++) {
            String[] parts = fileData[i].split(",");
            for (String s : parts) {
                tileData[j] = Integer.parseInt(s);
                j++;
            }
        }
        int k = 0;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (tileData[k] != 0) {
                    int i = tileData[k];
                    BufferedImage image = Tools.getImage("/chapters/chapter" + chapterID + "/tiles/" + i + ".png");
                    TILE_SIZE = image.getWidth();
                    switch (i) {
                        case 1:
                            tiles.add(new Floor(new Vector(x * TILE_SIZE, y * TILE_SIZE), chapterID));
                            break;
                        default:
                        	Tile t = new Tile(new Vector(x * TILE_SIZE, y * TILE_SIZE),i, chapterID);
                        	t.setSolid(true);
                        	tiles.add(t);
                    }
                }
                k++;
            }
        }
        
        String[] data = DataHandler.readFile(DataHandler.dir + "//player.dll");
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double health = Double.parseDouble(data[3]);
        double xOffset = Double.parseDouble(data[4]);
        
        player = new Player(map, new Vector(x, y), "/player/player.png", xOffset);
        player.setHealth(health);
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
