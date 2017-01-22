import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Kneehaul on 5/15/2016.
 */
public class MouseHandler extends MouseAdapter {

    Handler handler;



    public MouseHandler(Handler handler) {
        this.handler = handler;
    }


    public void mousePressed(MouseEvent e) {
        int x = e.getX() / Game.WIDTH_SCALE;
        int y = e.getY() / Game.HEIGHT_SCALE;

        ArrayList<Posn> moves = Game.selected.potentialMoves(handler);
        ArrayList<Posn> movesRemoved = new ArrayList<Posn>();

        for (int i = 0; i < moves.size(); i++) {
            Posn move = moves.get(i);
            if(!(move.x == Game.selected.x && move.y == Game.selected.y)) {
                movesRemoved.add(move);
            }
        }




        for(Posn move : movesRemoved) {

            if(x == move.x && y == move.y && Game.selected.getColor() == Game
                    .turn) {
                handler.remove(move.x, move.y);
                Game.selected.x = move.x;
                Game.selected.y = move.y;
                Game.turn = Game.selected.oppColor();
                Game.selected = handler.get(Game.turn);

            }
        }

        System.out.println();

        for(Piece piece : handler.pieces) {
            if(piece.x == x && piece.y == y && piece.getColor() == Game.turn) {
                Game.selected = piece;
            }
        }


    }
}
