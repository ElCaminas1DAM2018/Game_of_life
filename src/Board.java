
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */
public class Board extends JPanel {
        
    private boolean[][] board;
    private boolean[][] boardTemp;
    private int numCols;  
    private int numRows;
    private Timer timer;
    private int deltaTime;
    public float fillRatio = 0.0f;
    private JLabel totalRatioLabel;
    
    public Board() {
        super();
        
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tick();
            }
        });
        setSize(MainFrame.INITIAL_SIZE);
        
        deltaTime = 300;
                   
        clearBoard();
                  
    }
    
    public void clearBoard() {
        board = new boolean[numRows][numCols]; 
        boardTemp = new boolean[numRows][numCols];
        for(int row = 0; row<numRows; row++) {
            for (int col = 0; col<numCols; col++) {
                board[row][col] = false;
                boardTemp[row][col] = false;
            }
        }
        
        timer.stop();
        repaint();
    }
    
    
    public void initGame() {    
        repaint();
        timer.start();   
    }
    
    
    private void tick() {
        for(int row = 0; row <numRows; row ++) {
            for(int col = 0; col<numCols; col++) {
                applyRules(row,col);
            }
        }
        for (int i = 0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                board[i][j] = boardTemp[i][j];
            }
        } 
        totalRatioLabel.setText("Total ratio: " + String.format("%.2f", getNumAliveCells() / 
                (float)(numRows * numCols)));
        repaint();
        Toolkit.getDefaultToolkit().sync(); 
    }
    
    private void applyRules(int row, int col) {
        // First rule
        int numNeighbours = getNumNeighbours(row, col);
        if (board[row][col]) {
            if (numNeighbours < 2) {
                boardTemp[row][col] = false;
            }
        }
        // Second rule
        if (board[row][col]) {
           if (numNeighbours == 2 || numNeighbours == 3) {
               boardTemp[row][col] = true;
           } 
        }
        //Third rule
        if (board[row][col]) {
            if (numNeighbours>3) {
                boardTemp[row][col] = false;
            }
        }
        //Forth rule
        if (!board[row][col]) {
            if (numNeighbours == 3) {
                boardTemp[row][col] = true;
            }
        }
               
        
    }
    
    private int getNumAliveCells() {
        int counter = 0;
        for (int row = 0; row < numRows; row ++) {
            for (int col = 0; col < numCols; col ++) {
                if (board[row][col]) {
                    counter ++;
                }
            }
        }
        return counter;
    }
    
    private int getNumNeighbours(int row, int col) {
        int counter = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i <= numRows-1 && j>=0 && j<= numCols-1) {
                    if (row == i && col == j) {
                        
                    } else {                       
                        if (board[i][j]) {                        
                            counter ++;
                        }
                    }
                }
            }
        }       
        return counter;
    }
    
    public void fillRandomly(float ratio) {
        int numCells = (int) (numCols * numRows * ratio);
        int count = 0;
        Random random = new Random();
        while(count < numCells) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            if (!board[row][col]) {
                board[row][col] = true;
                count ++;
            }
        }   
        timer.stop();
        repaint();
    }
    
    private int getSquareWidth() {
        return getWidth() / numCols;
    }
    
    private int getSquareHeight() {
        return getHeight() / numRows;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                drawSquare(g, row, col, getSquareWidth(), getSquareHeight());
            }
        }       
    }
    
    public void drawSquare(Graphics g, int row, int col, 
                                    int squareWidth, int squareHeight) {
        int x = col * squareWidth;
        int y = row * squareHeight;
        if (board[row][col]) {
            Color color = new Color(102, 102, 204);
            g.setColor(color);
            g.fillRect(x + 1, y + 1, squareWidth - 2,
                squareHeight - 2);
            g.setColor(color.brighter());
            g.drawLine(x, y + squareHeight - 1, x, y);
            g.drawLine(x, y, x + squareWidth - 1, y);
            g.setColor(color.darker());
            g.drawLine(x + 1, y + squareHeight - 1,
                x + squareWidth - 1, y + squareHeight - 1);
            g.drawLine(x + squareWidth - 1,
                y + squareHeight - 1,
                x + squareWidth - 1, y + 1);
        } else {
            g.setColor(Color.GRAY);
            g.drawRect(x, y, squareWidth, squareHeight);
        }
        
    }

    public void paintCell(int x, int y) {
        int row = y / getSquareHeight();
        int col = x / getSquareWidth();
        if (board[row][col]) {
            board[row][col] = false;
        } else {
            board[row][col] = true;
        }
        repaint();
    }

    public void setTtotalRatioLabel(JLabel jLabelTotalRatio) {
        totalRatioLabel = jLabelTotalRatio;
    }

    public void setSpeed(int speed) {
        timer.setDelay(2000 - speed * 20);
    }
    
    public void setSize(int size) {
        numRows = size;
        numCols = size * 4 / 3;
        clearBoard();
    }
    
    
}
