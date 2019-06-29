import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pacman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pacman extends Actor
{
    private int curOrient = 1, nxtOrient = -1;
    private int rPos;
    private int cPos;
    
    public Pacman(int rPos, int cPos) {
        this.rPos = rPos;
        this.cPos = cPos;
    }
    
    /**
     * Act - do whatever the Pacman wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.isKeyDown("up")) {
            nxtOrient = 0;
        } else if (Greenfoot.isKeyDown("down")) {
            nxtOrient = 2;
        } else if (Greenfoot.isKeyDown("left")) {
            nxtOrient = 3;
        } else if (Greenfoot.isKeyDown("right")) {
            nxtOrient = 1;
        }
        
        int[] dr = {-1, 0, 1, 0};
        int[] dc = { 0, 1, 0,-1};
        
        MyWorld myWorld = (MyWorld)getWorld();
        
        if (nxtOrient != -1) {
            int nxtR = rPos + dr[nxtOrient];
            int nxtC = cPos + dc[nxtOrient];
            if (myWorld.isWalkable(nxtR, nxtC)) {
                curOrient = nxtOrient;
                nxtOrient = -1;
            }
        }
        
        int nxtR = rPos + dr[curOrient];
        int nxtC = cPos + dc[curOrient];
        if (myWorld.isWalkable(nxtR, nxtC)) {
            this.rPos = nxtR;
            this.cPos = nxtC;
        }
        
        // System.out.println(this.rPos + ", " + this.cPos);
        setLocation(this.cPos, this.rPos);
    }    
}
