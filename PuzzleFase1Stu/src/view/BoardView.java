package view;

import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Clase que representa la vista del tablero
 * @author Miguel Ã�ngel
 * @version 1.0
 */
public class BoardView extends JPanel implements Observer {
    public static final int imageWidth= 96;
    public static final int imageHeight= 96;
    private ArrayList<PieceView> iconArray = null;
    private static boolean offseted=false;
    
    public BoardView(int rowNum, int columnNum,int imageSize, String[] imageList){
        super();
      
       
        iconArray=new ArrayList<PieceView>();
            
       
       
        for(int i=0;i<rowNum*columnNum;i++) {
        	/*
        	if(i%columnNum==0) {
        		drawnRowIndex+=30;
        		drawnColumnIndex=75;
        	}else {
        		drawnColumnIndex+=30;

        	}
        	
        	*/
        	
        	
        		PieceView p=new PieceView( i,i%rowNum,i/rowNum,imageSize,imageList[i]);
        		SetCoordinates(p);
        		
        		iconArray.add(p);
        		       		
        		
        }

    }
    
    private void SetCoordinates(PieceView p) {
    	
    
    	int drawnRow;
    	int drawnColumn;
    	
    	 drawnRow=p.getIndexRow()*30;
    	 drawnColumn=p.getIndexColumn()*30;

    	
    	 
    	 p.setDrawnColumnIndex(drawnColumn);
    	 p.setDrawnRowIndex(drawnRow);
	
    	   	
    	
    }
    

    public BoardView(int rowNum, int columnNum, int imageSize, File imageFile){
        super();
    }

    //redimensionamos la imagen para 96*96
    private BufferedImage resizeImage(File fileImage){
        BufferedImage resizedImage = null;

        return(resizedImage);
    }

    //dividimos la imagen en el nÃºmero
    private BufferedImage[] splitImage(BufferedImage image){
        //Divisor de imÃ¡genes
        BufferedImage images[] = null;
        return(images);
    }

    public void update(int blankPos, int movedPos){
    	
    }

    public void update(Graphics g){
        paint(g);
    }

    public void paint(Graphics g){

    	int rowOff=0;
    	int colOff=0;
    	
    	if(!offseted) {
    		rowOff=(this.getWidth()-this.imageWidth)/2;
    		colOff=(this.getHeight()-this.imageHeight)/2;
    		offseted=true;
    	}
    	
        for(PieceView iconImage:iconArray){
        	
        	
        	iconImage.setDrawnRowIndex(iconImage.getDrawnRowIndex()+rowOff);
            iconImage.setDrawnColumnIndex(iconImage.getDrawnColumnIndex()+colOff);           
        	
        	
            g.drawImage(iconImage.getImage(), iconImage.getDrawnRowIndex(), iconImage.getDrawnColumnIndex(), iconImage.getImageSize(), iconImage.getImageSize(), this);
            
        }
    }

    //Dado una posicion X e Y localizar una pieza
    private int locatePiece(int posX,int posY){
        return(-1);
    }

    /**
     * Mueve la pieza y devuelve las coordenadas en un array de dos posiciones
     * donde: la primera posicion representa la posicion actual de la pieza blanca
     * y la segunda posicion representa la posicion actual de la pieza a mover.
     * @param posX posicion X del puntero
     * @param posY posicion Y del puntero.
     * @return Array de dos posiciones: posicion actual de la pieza blanca y posicion
     * actual de la pieza que tiene que ser movida.
     */
    public int[] movePiece(int posX,int posY){
    	//Array de 2 posiciones para devolver
    	int[] move = new int[4];
    	
    	//Pieza blanca
    	
    	
    	//En primera posicion pieza blanca y segunda pieza posX y posY
    	move = new int[]{0,0,0,0};
    	
        return(move);
    }

}
