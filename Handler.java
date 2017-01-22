import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Kneehaul on 5/9/2016.
 */
public class Handler {

    LinkedList<Piece> pieces = new LinkedList<Piece>();

    public void tick() {
        for(int i = 0; i < pieces.size(); i++) {
            Piece temp = pieces.get(i);
            temp.tick();
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < pieces.size(); i++) {
            Piece temp = pieces.get(i);
            temp.render(g);
        }
    }

    public void add(Piece p) {
        pieces.add(p);
    }

    void remove(int x, int y) {
        for(int i = 0; i < this.pieces.size(); i++) {
            Piece temp = this.pieces.get(i);
            if(temp.x == x && temp.y == y) {
                pieces.remove(i);
            }
        }
    }

    Piece get(Color c) {
        Piece p =  p = this.pieces.get(0);

        for (int i = this.pieces.size() - 1; i > 0 ; i--) {
            Piece temp = this.pieces.get(i);
            if (temp.getColor() == c) {
                p = temp;

            }
        }


        return p;
    }


}
