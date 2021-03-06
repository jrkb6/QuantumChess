import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Piece {
    private boolean superPosBol;
    private Color color;
    private boolean taken;
    protected int posX;
    protected int posY;
    private int superPosX;
    private int superPosY;
    private boolean superPosAllowed; //if the user observes it there will be no more super pos
    private BufferedImage fullImage;
    private BufferedImage superPosImage;
    protected int player;
    protected ArrayList<Point> positions;
    private int iterator;

    public Piece(int initialX, int initialY, String fullPicName, String halfPicName,int sizex, int sizey, Color color, int player){
        superPosBol = false;
        this.color = color;
        taken = false;
        posX = initialX;
        posY = initialY;
        superPosAllowed = true;

        fullImage = new BufferedImage(sizex,sizey,BufferedImage.TYPE_INT_ARGB_PRE);
        superPosImage = new BufferedImage(sizex,sizey,BufferedImage.TYPE_INT_ARGB_PRE);
        fullImage = loadPicture(fullPicName);
        superPosImage = loadPicture(halfPicName);

        this.player = player;
        positions = new ArrayList<Point>();
        iterator = 0;
    }

    public Piece(Piece copy){
        superPosAllowed = true;
        taken = false;
        superPosBol = false;
        this.color = copy.color;
        posX = copy.posX;
        posY = copy.posY;
        fullImage = copy.getFullImage();
        superPosImage = copy.getSuperPosImage();
        player = copy.player;
        positions =new ArrayList<Point>();
        iterator = 0;
    }

    public boolean validPos(int x, int y){
        Point tmp;
        while (hasNext()){
            tmp = getNext();
            if((tmp != null) && (x == tmp.getX()) && (y == tmp.getY() )){
                return true;
            }
        }
        return false;
    }

    public abstract void move(int x, int y);
    protected void calculatePossibleMoves(){
        positions = new ArrayList<Point>();
    }


    private BufferedImage loadPicture(String fileName){
        try{
            return ImageIO.read(new File(fileName));
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void updatePiecePos(int x, int y) {
        posY = y;
        posX = x;
        calculatePossibleMoves();

    }

    public void updatePieceSuperPos(int x, int y){
        superPosY = y;
        superPosX = x;
    }

    public int getPosx(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public int getSuperPosX(){
        return  superPosX;
    }

    public int getSuperPosY(){
        return superPosY;
    }

    public BufferedImage getFullImage() {
        return fullImage;
    }

    public BufferedImage getSuperPosImage() {
        return fullImage;
    }

    public boolean hasNext(){
        if (iterator < positions.size()){
            return true;
        }else{
            iterator = 0;
            return false;
        }
    }

    public Point getNext() {
        while(positions.get(iterator) == null){
            iterator++;
        }
        return positions.get(iterator++);
    }

    public void continueUntilNull(){
        while (hasNext() && (positions.get(iterator) != null)){
            iterator++;
        }
    }


}
