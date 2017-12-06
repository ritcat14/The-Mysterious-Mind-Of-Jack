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
    
    private int TILE_SIZE = 64;
    
    private Player player;
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    
    public void decode(Map map, int chapterID){
    	System.out.println(chapterID);
        entities = new ArrayList<Entity>();
        String file = "/chapters/chapter" + chapterID + "/map.gme";
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
                    }
                }
                k++;
            }
        }
        
        String[] data = DataHandler.readFile(DataHandler.dir + "//player.gme");
        double x = Double.parseDouble(data[0]);
        double y = Double.parseDouble(data[1]);
        int health = Integer.parseInt(data[2]);
        int xScroll = Integer.parseInt(data[3]);
        int yScroll = Integer.parseInt(data[4]);
        
        player = new Player(map, new Vector(x, y), "/player.png");
        player.setScroll(xScroll, yScroll);
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