import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

/**
 * Created by Kneehaul on 5/9/2016.
 */
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int WIDTH_SCALE = WIDTH / 8;
    public static final int HEIGHT_SCALE = HEIGHT / 8;
    public static Color turn;


    private Thread thread;
    private boolean running = false;

    private Handler handler;
    public static Piece selected;


    public Game() {
        handler = new Handler();
        this.setPieces();
        new Window(WIDTH + 5, HEIGHT + 25, "Chess Game", this);
        Game.turn = Color.White;
        this.selected = handler.pieces.get(0);
        MouseHandler mouseHandler = new MouseHandler(this.handler);
        addMouseListener(mouseHandler);
    }

    void setPieces() {
        // set pawns
        for(int i = 0; i < 8; i++) {
            this.handler.add(new Pawn(i, 1, Color.White));
            this.handler.add(new Pawn(i, 6, Color.Black));
        }
        // rooks
        this.handler.add(new Rook(0, 0, Color.White));
        this.handler.add(new Rook(7, 0, Color.White));
        this.handler.add(new Rook(0, 7, Color.Black));
        this.handler.add(new Rook(7, 7, Color.Black));

        // horses
        this.handler.add(new Horse(1, 0, Color.White));
        this.handler.add(new Horse(6, 0, Color.White));
        this.handler.add(new Horse(1, 7, Color.Black));
        this.handler.add(new Horse(6, 7, Color.Black));

        // bishops
        this.handler.add(new Bishop(2, 0, Color.White));
        this.handler.add(new Bishop(5, 0, Color.White));
        this.handler.add(new Bishop(2, 7, Color.Black));
        this.handler.add(new Bishop(5, 7, Color.Black));

        // kings
        this.handler.add(new King(3, 0, Color.White));
        this.handler.add(new King(4, 7, Color.Black));

        // queens
        this.handler.add(new Queen(4, 0, Color.White));
        this.handler.add(new Queen(3, 7, Color.Black));

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        this.running = true;

    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 69.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer+= 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();

    }

    private void renderSelected(Graphics g) {
        int x = this.selected.x * this.WIDTH_SCALE;
        int y = this.selected.y * this.HEIGHT_SCALE;
        Image selection = null;
                try {
                    selection = ImageIO.read(new File("images/selected.png"));
                }
                catch(IOException e) {
                    System.out.print(e.getMessage());
                }
        g.drawImage(selection, x, y, null);

        Image target = null;
        try {
            target = ImageIO.read(new File("images/target.png"));
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

        for(Posn p : this.selected.potentialMoves(this.handler)) {
            int posX = p.x * this.WIDTH_SCALE;
            int posY = p.y * this.HEIGHT_SCALE;
            g.drawImage(target, posX + 50, posY + 50, null);
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Image background = null;
        try {
            background = ImageIO.read(new File("images/chessboard.png"));
        }
        catch(IOException e) {
            System.out.print(e.getMessage());
        }
        g.drawImage(background, 0, 0, this);

        this.handler.render(g);
        this.renderSelected(g);


        g.dispose();
        bs.show();
    }



    public static void main(String args[]) {
        new Game();
    }
}
