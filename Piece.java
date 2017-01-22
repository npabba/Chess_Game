import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kneehaul on 5/9/2016.
 */

public abstract class Piece {
    int x, y;
    protected Color color;

    public Piece(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public abstract void tick();

    public void render(Graphics g, String path) {
        Image piece = null;
        try {
            piece = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        g.drawImage(piece, (this.x * Game.WIDTH_SCALE) + 25,
                (this.y * Game.HEIGHT_SCALE) + 25,
                null);
    }

    public abstract void render(Graphics g);
    public abstract ArrayList<Posn> potentialMoves(Handler handler);
    //TODO public abstract void move(String dir);

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return this.color;
    }

    public Color oppColor() {
        if (this.getColor() == Color.Black) {
            return Color.White;
        } else {
            return Color.Black;
        }
    }

    public Posn furthestUp(Handler handler) {
        int upX = this.x;
        int upY = this.y - 1;

        while(upY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == upX && temp.y == upY) {
                    // check friend or foe, then gives final move;
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    else {
                        return new Posn(temp.x, temp.y + 1);
                    }
                }
             }

            upY = upY - 1;

        }

        return new Posn(this.x, 0);
    }

    public Posn furthestDown(Handler handler) {
        int downX = this.x;
        int downY = this.y + 1;

        while(downY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == downX && temp.y == downY) {
                    // check friend or foe, then gives final move;
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    else {
                        return new Posn(temp.x, temp.y - 1);
                    }
                }
            }
            downY = downY + 1;
        }

        return new Posn(this.x, 7);
    }

    public Posn furthestLeft(Handler handler) {
        int leftX = this.x - 1;
        int leftY = this.y;

        while(leftX > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == leftX && temp.y == leftY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    else {
                        return new Posn(temp.x + 1, temp.y);
                    }
                }
            }
            leftX = leftX - 1;
        }

        return new Posn(0, this.y);
    }

    public Posn furthestRight(Handler handler) {
        int rightX = this.x + 1;
        int rightY = this.y;

        while(rightX < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == rightX && temp.y == rightY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    else {
                        return new Posn(temp.x - 1, temp.y);
                    }
                }
            }
            rightX = rightX + 1;
        }

        return new Posn(7, this.y);
    }

    Posn furthestUpLeft(Handler handler) {
        int upLeftX = this.x - 1;
        int upLeftY = this.y - 1;

        while(upLeftX > -1 && upLeftY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == upLeftX && temp.y == upLeftY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    return new Posn(upLeftX + 1, upLeftY + 1);
                }
            }
            upLeftX = upLeftX - 1;
            upLeftY = upLeftY - 1;
        }

        return new Posn(upLeftX + 1, upLeftY + 1);
    }

    Posn furthestUpRight(Handler handler) {
        int upRightX = this.x + 1;
        int upRightY = this.y - 1;

        while(upRightX < 8 && upRightY > - 1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == upRightX && temp.y == upRightY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    return new Posn(upRightX - 1, upRightY + 1);
                }
            }
            upRightX = upRightX + 1;
            upRightY = upRightY - 1;

        }
        return new Posn(upRightX - 1, upRightY + 1);
    }

    Posn furthestDownLeft(Handler handler) {
        int downLeftX = this.x - 1;
        int downLeftY = this.y + 1;

        while(downLeftX > -1 && downLeftY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == downLeftX && temp.y == downLeftY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    return new Posn(downLeftX + 1, downLeftY - 1);
                }
            }
            downLeftX = downLeftX - 1;
            downLeftY = downLeftY + 1;
        }
        return new Posn(downLeftX + 1, downLeftY - 1);
    }


    Posn furthestDownRight(Handler handler) {
        int downRightX = this.x + 1;
        int downRightY = this.y + 1;

        while(downRightX < 8 && downRightY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == downRightX && temp.y == downRightY) {
                    if(temp.getColor() == this.oppColor()) {
                        return new Posn(temp.x, temp.y);
                    }
                    return new Posn(downRightX - 1, downRightY - 1);
                }
            }
            downRightX++;
            downRightY++;
        }
        return new Posn(downRightX - 1, downRightY - 1);
    }

}

class Posn {
    int x;
    int y;

    public Posn(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Pawn extends Piece {

    boolean firstMove;

    public Pawn(int x, int y, Color color) {
        super(x, y, color);
        firstMove = true;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackpawn.png");
        } else {
            this.render(g, "images/whitepawn.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        if(this.getColor() == Color.Black) {
            moves.add(this.furthestUp(handler));
            moves.add(this.upRight(handler));
            moves.add(this.upLeft(handler));
//            if(this.firstMove) {
//                moves.add(this.furthestUp2(handler));
//            }
        }
        else {
            moves.add(this.furthestDown(handler));
            moves.add(this.downRight(handler));
            moves.add(this.downLeft(handler));
//            if(this.firstMove) {
//                moves.add(this.furthestDown2(handler));
//            }

        }

        return moves;
    }


    Posn downLeft(Handler handler) {
        int downLeftX = this.x - 1;
        int downLeftY = this.y + 1;
        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == downLeftX && temp.y == downLeftY && temp.getColor()
                    == this.oppColor()) {
                return new Posn(downLeftX, downLeftY);
            }
        }
        return new Posn(this.x, this.y);
    }

    Posn downRight(Handler handler) {
        int downRightX = this.x + 1;
        int downRightY = this.y + 1;
        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == downRightX && temp.y == downRightY && temp.getColor()
                    == this.oppColor()) {
                return new Posn(downRightX, downRightY);
            }
        }
        return new Posn(this.x, this.y);
    }

    Posn upLeft(Handler handler) {
        // up left
        int upLeftX = this.x - 1;
        int upLeftY = this.y - 1;
        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == upLeftX && temp.y == upLeftY && temp.getColor() ==
                    this.oppColor()) {
                return new Posn(upLeftX, upLeftY);
            }
        }
        return new Posn(this.x, this.y);
    }

    Posn upRight(Handler handler) {
        // up right
        int upRightX = this.x + 1;
        int upRightY = this.y - 1;
        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == upRightX && temp.y == upRightY && temp.getColor() ==
                    this.oppColor()) {
                return new Posn(upRightX, upRightY);
            }
        }
        return new Posn(this.x, this.y);
    }


    public Posn furthestUp(Handler handler) {
        // only used by black pawns

        int upX = this.x;
        int upY = this.y - 1;


            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == upX && temp.y == upY) {

                    return new Posn(temp.x, temp.y + 1);
                }

            }

        return new Posn(upX, upY);
    }

    public Posn furthestUp2(Handler handler) {
        // only used by black pawns

        int upX = this.x;
        int upY = this.y - 2;


        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == upX && temp.y == upY) {

                return new Posn(temp.x, temp.y + 1);
            }

        }

        return new Posn(upX, upY);
    }


    public Posn furthestDown(Handler handler) {
        // only used by black pawns
        int downX = this.x;
        int downY = this.y + 1;


            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == downX && temp.y == downY) {
                    return new Posn(temp.x, temp.y - 1);
                }
            }

        return new Posn(downX, downY);
    }

    public Posn furthestDown2(Handler handler) {
        // only used by black pawns
        int downX = this.x;
        int downY = this.y + 2;


        for(int i = 0; i < handler.pieces.size(); i++) {
            Piece temp = handler.pieces.get(i);
            if(temp.x == downX && temp.y == downY) {
                return new Posn(temp.x, temp.y - 1);
            }
        }

        return new Posn(downX, downY);
    }
}

class Rook extends Piece {

    public Rook(int x, int y, Color color) {
        super(x, y, color);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackrook.png");
        } else {
            this.render(g, "images/whiterook.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        moves.add(this.furthestUp(handler));
        moves.add(this.furthestDown(handler));
        moves.add(this.furthestLeft(handler));
        moves.add(this.furthestRight(handler));
        return moves;
    }
}

class Bishop extends Piece {

    public Bishop(int x, int y, Color color) {
        super(x, y, color);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackbishop.png");
        } else {
            this.render(g, "images/whitebishop.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        moves.add(this.furthestUpLeft(handler));
        moves.add(this.furthestUpRight(handler));
        moves.add(this.furthestDownLeft(handler));
        moves.add(this.furthestDownRight(handler));

        return moves;
    }

}

class Horse extends Piece {

    public Horse(int x, int y, Color color) {
        super(x, y, color);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackhorse.png");
        } else {
            this.render(g, "images/whitehorse.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        moves.add(this.move1(handler));
        moves.add(this.move2(handler));
        moves.add(this.move3(handler));
        moves.add(this.move4(handler));
        moves.add(this.move5(handler));
        moves.add(this.move6(handler));
        moves.add(this.move7(handler));
        moves.add(this.move8(handler));

        return moves;
    }
    /*
            81
           7  2
           6  3
            54

     */

    Posn move8(Handler handler) {
        int moveX = this.x - 1;
        int moveY = this.y - 2;
        boolean okMove = true;
        if(moveX >  - 1 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }


    Posn move7(Handler handler) {
        int moveX = this.x - 2;
        int moveY = this.y - 1;
        boolean okMove = true;
        if(moveX >  - 1 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move6(Handler handler) {
        int moveX = this.x - 2;
        int moveY = this.y + 1;
        boolean okMove = true;
        if(moveX >  - 1 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move5(Handler handler) {
        int moveX = this.x - 1;
        int moveY = this.y + 2;
        boolean okMove = true;
        if(moveX >  - 1 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move4(Handler handler) {
        int moveX = this.x + 1;
        int moveY = this.y + 2;
        boolean okMove = true;
        if(moveX <  8 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move3(Handler handler) {
        int moveX = this.x + 2;
        int moveY = this.y + 1;
        boolean okMove = true;
        if(moveX <  8 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move2(Handler handler) {
        int moveX = this.x + 2;
        int moveY = this.y - 1;
        boolean okMove = true;
        if(moveX >  - 1 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn move1(Handler handler) {
        int moveX = this.x + 1;
        int moveY = this.y - 2;
        boolean okMove = true;
        if(moveX >  - 1 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;

                }
            }
        }
        else {
            okMove = false;
        }
        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

}

class King extends Piece {

    public King(int x, int y, Color color) {
        super(x, y, color);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackking.png");
        } else {
            this.render(g, "images/whiteking.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        moves.add(this.moveUp(handler));
        moves.add(this.moveDown(handler));
        moves.add(this.moveLeft(handler));
        moves.add(this.moveRight(handler));
        moves.add(this.moveUpLeft(handler));
        moves.add(this.moveUpRight(handler));
        moves.add(this.moveDownLeft(handler));
        moves.add(this.moveDownRight(handler));

        return moves;
    }

    Posn moveUp(Handler handler) {
        int moveX = this.x;
        int moveY = this.y - 1;
        boolean okMove = true;
        if(moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveDown(Handler handler) {
        int moveX = this.x;
        int moveY = this.y + 1;
        boolean okMove = true;
        if(moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveLeft(Handler handler) {
        int moveX = this.x - 1;
        int moveY = this.y;
        boolean okMove = true;
        if(moveX > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveRight(Handler handler) {
        int moveX = this.x + 1;
        int moveY = this.y;
        boolean okMove = true;
        if(moveX < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveUpLeft(Handler handler) {
        int moveX = this.x - 1;
        int moveY = this.y - 1;
        boolean okMove = true;
        if(moveX > -1 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveUpRight(Handler handler) {
        int moveX = this.x + 1;
        int moveY = this.y - 1;
        boolean okMove = true;
        if(moveX < 8 && moveY > -1) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveDownLeft(Handler handler) {
        int moveX = this.x - 1;
        int moveY = this.y + 1;
        boolean okMove = true;
        if(moveX > -1 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }

    Posn moveDownRight(Handler handler) {
        int moveX = this.x + 1;
        int moveY = this.y + 1;
        boolean okMove = true;
        if(moveX < 8 && moveY < 8) {
            for(int i = 0; i < handler.pieces.size(); i++) {
                Piece temp = handler.pieces.get(i);
                if(temp.x == moveX && temp.y == moveY && temp.getColor() ==
                        this.getColor()) {
                    okMove = false;
                }
            }
        }
        else {
            okMove = false;
        }

        if(okMove) {
            return new Posn(moveX, moveY);
        }
        else {
            return new Posn(this.x, this.y);
        }
    }


}

class Queen extends Piece {

    public Queen(int x, int y, Color color) {
        super(x, y, color);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (this.color == Color.Black) {
            this.render(g, "images/blackqueen.png");
        } else {
            this.render(g, "images/whitequeen.png");
        }
    }

    public ArrayList<Posn> potentialMoves(Handler handler) {
        ArrayList<Posn> moves = new ArrayList<Posn>();
        moves.add(this.furthestUp(handler));
        moves.add(this.furthestDown(handler));
        moves.add(this.furthestLeft(handler));
        moves.add(this.furthestRight(handler));
        moves.add(this.furthestUpLeft(handler));
        moves.add(this.furthestUpRight(handler));
        moves.add(this.furthestDownLeft(handler));
        moves.add(this.furthestDownRight(handler));



        return moves;
    }
}





