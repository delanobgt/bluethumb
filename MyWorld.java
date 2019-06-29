import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    private boolean[][] path;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(525, 525, 1); 

        loadMap();
        prepare();
    }

    public boolean isWalkable(int r, int c) {
        return path[r][c];
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Greenfoot.setSpeed(57);
    }
    
    private void loadMap() {
        // read map information from map.txt
        List<String> lst = new ArrayList<String>();
        InputStream is = getClass().getClassLoader().getResourceAsStream("texts/map.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            if (is != null) {
                String str;
                while ((str = br.readLine()) != null) {   
                    str = str.trim();
                    if (str.length() > 0)
                        lst.add(str);
                }                
            }
        } catch (Throwable ignore) {
        } finally {
            try { is.close(); } catch (Throwable ignore) {}
        }
        
        // find maximum row size and column size
        int R = lst.size(), C = 0;
        for (String s : lst) {
            C = Math.max(C, s.length());
        }
        
        // populate grid with information from map.txt
        char[][] grid = new char[R][C];
        for (int r = 0; r < R; r++) {
            Arrays.fill(grid[r], ' ');
            for (int c = 0; c < lst.get(r).length(); c++) {
                grid[r][c] = lst.get(r).charAt(c);
            }
        }
        
        // construct map from grid
        int[] dr = {-1, 0, 1, 0};
        int[] dc = { 0, 1, 0,-1};
        path = new boolean[R*35][C*35];
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == '#') {
                    Wall0 wall0 = new Wall0();
                    addObject(wall0, 35*c+17, 35*r+17);
                    
                    for (int i = 0; i < 4; i++) {
                        int iR = r+dr[i];
                        int iC = c+dc[i];
                        if (0 <= iR && iR < R && 0 <= iC && iC < C && grid[iR][iC] == '#') {
                            if (i == 0) {
                                Wall1 wall1 = new Wall1();
                                addObject(wall1, 35*c+17, 35*r+17);
                            } else if (i == 1) {
                                Wall2 wall2 = new Wall2();
                                addObject(wall2, 35*c+17, 35*r+17);                                
                            } else if (i == 2) {
                                Wall3 wall3 = new Wall3();
                                addObject(wall3, 35*c+17, 35*r+17);                                
                            } else if (i == 3) {
                                Wall4 wall4 = new Wall4();
                                addObject(wall4, 35*c+17, 35*r+17);                            
                            }
                        }
                    }
                } else {
                    if (grid[r][c] == 'C') {
                        Pacman pacman = new Pacman(35*r+17, 35*c+17);
                        addObject(pacman, 35*c+17, 35*r+17);
                    }
                    for (int i = 0; i < 4; i++) {
                        int iR = r+dr[i];
                        int iC = c+dc[i];
                        if (0 <= iR && iR < R && 0 <= iC && iC < C && grid[iR][iC] != '#') {
                            int curR = 35*r+17, curC = 35*c+17;
                            for (int j = 0; j <= 17; j++) {
                                path[curR][curC] = true;
                                curR += dr[i];
                                curC += dc[i];
                            }
                        }
                    }
                }
            }
        }
    }
}
