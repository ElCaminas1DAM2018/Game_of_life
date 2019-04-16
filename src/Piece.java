
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */
public class Piece extends Board {
        
    
    class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent arg0) {
              MainFrame.selectedPiece = form;              
              main.resetPieces();
              setBackground(Color.white);
              selected = true;
        }
    }
    
    private MainFrame main;
    private PredefinedForm form;
    private boolean selected;
    
    public Piece() {
        super();
        setSize(3);
        selected = false;
        addMouseListener(new MyMouseAdapter());
    }
    
    public void setSelected(boolean sel) {
        selected = sel;
    }
    
    public void setMain(MainFrame main) {
        this.main = main;
    }
    
    public void setPredefinedForm(PredefinedForm form) {
        this.form = form;
        switch(form) {
            case POINT:
                setCellAlive(0, 0);
                break;
            case GLIDER:
                setCellAlive(0, 1);
                setCellAlive(1, 2);
                setCellAlive(2, 0);
                setCellAlive(2, 1);
                setCellAlive(2, 2);
                break;
            case BLINKER:
                setCellAlive(0, 0);
                setCellAlive(0, 1);
                setCellAlive(0, 2);
                break;
            case SPACESHIP1:
                setSize(4);
                setCellAlive(0, 0);
                setCellAlive(0, 3);
                setCellAlive(1, 4);
                setCellAlive(2, 0);
                setCellAlive(2, 4);
                setCellAlive(3, 1);
                setCellAlive(3, 2);
                setCellAlive(3, 3);
                setCellAlive(3, 4);
                break;
        }
    }
}
