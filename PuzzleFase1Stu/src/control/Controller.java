package control;

import java.awt.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JFileChooser;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import command.Command;
import command.LoadCommand;
import command.MoveCommand;
import command.RandomCommand;
import command.SaveCommand;
import command.SolveCommand;
import model.Model;
import observer.Observer;
import view.BoardView;
import view.PieceView;
import view.PuzzleGUI;


public class Controller extends AbstractController{
	//Variable para recibir del PuzzleGUI la accion realizada
	private String action;
	
	/*No se si es correcto*/
	private BoardView myView;
	private int posX;
	private int posY;
	private Stack<Command> moveCommands;
	private Command solve;
	private Command random;
	private Command save;
	private Command load;
	
	public Controller() {
		moveCommands=new Stack();
		solve=new SolveCommand(this);
		random=new RandomCommand(this);
		save=new SaveCommand(this);
		load=new LoadCommand(this);
		
		//myView=PuzzleGUI.getInstance().getBoardView();
	}
	
	//Ejecutamos todas las acciones con su correspondiente command
	@Override
	public void actionPerformed(ActionEvent act) {
		// TODO Auto-generated method stub			
		this.action = act.getActionCommand();
		System.out.println(	act.getSource().toString());

		switch (action) {
			case "clutter": 
				random.execute();
				break;
				
			case "solve":
				solve.execute();				
				break;
				
			case "load":
				
				/*--------------------Meter en comando---------------------*/
				File f=PuzzleGUI.getInstance().showFileSelector();
				System.out.println("Path: "+f);
				if(f!=null) {
					PuzzleGUI.getInstance().updateBoard(f);
				}
				notifyObserversReset();
				this.myView=PuzzleGUI.getInstance().getBoardView();
				reset();

				//PuzzleGUI.getInstance().getBoardView().update(PuzzleGUI.getInstance().getBoardView().getGraphics());
				System.out.println("Load Image");
				break;
				
			case "saveGame":
				save.execute();
				System.out.println("Save data");
				break;
				
			case "loadGame":
				load.execute();	
				System.out.println("Load data");
				break;
				
			case "info":
				System.out.println("Information");
				break;
				
			default:
				break;
		}
	}

	@Override
	public void notifyObservers(List<Element> pieceList, Element image) {
		//TODO Auto-generated method stub
		for(Observer o:observerList) {
			
			o.loadBoard(pieceList, image);
		}
		
	}
	public void notifyObserversReset() {
		//TODO Auto-generated method stub
		for(Observer o:observerList) {
			
			o.setNewBoard();
		}
		
	}


	public void mouseClicked(MouseEvent e) {		
		posX=e.getX();
		posY=e.getY();
		System.out.println("X: "+posX+" Y: "+posY);
		int pos[]=PuzzleGUI.getInstance().getBoardView().movePiece(posX, posY);
		MoveCommand m=new MoveCommand(this,pos[0],pos[1]);
		this.moveCommands.push(m);
		m.execute();
	}

	
	public BoardView getMyView() {
		return myView;
	}
	

	




public void setMyView(BoardView myView) {
	this.myView = myView;
}

public int getPosY() {
	return posY;
}

public void setPosY(int posY) {
	this.posY = posY;
}

public int getPosX() {
	return posX;
}

public void setPosX(int posX) {
	this.posX = posX;
}

public void reset() {

	solve=new SolveCommand(this);
	random=new RandomCommand(this);
}


public void writeXML() throws IOException{
	
	
	
	Element Model = new Element("Model");
	Document doc = new Document(Model);
	try {
		doc.getRootElement().addContent(new Element("Image").setText(PuzzleGUI.getInstance().getBoardView().getImage().getPath()));

	}catch(Exception e) {
		
	}
	
	Element pieces=new Element("Pieces");
	Element index=new Element("Index").setText(String.valueOf(move.getIndex()));
	
	Element list=new Element("Moves");
	
	
	for(int i[]:move.getMoves()) {
		Element e=new Element("move");
		e.addContent(new Element("Pos0").setText(String.valueOf(i[0])));
		e.addContent(new Element("Pos1").setText(String.valueOf(i[1])));
		list.addContent(e);

	}

	
	for(PieceView p:PuzzleGUI.getInstance().getBoardView().getIconArray()) {
		//System.out.println("id: "+p.getId()+" X: "+p.getIndexRow()+" Y: "+p.getIndexColumn());
		
		Element pieceModel = new Element("pieceModel");

		
		
	
		pieceModel.addContent(new Element("Id").setText(Integer.toString(p.getId())));
		pieceModel.addContent(new Element("X").setText(Integer.toString(p.getIndexRow())));
		pieceModel.addContent(new Element("Y").setText(Integer.toString(p.getIndexColumn())));
		
		pieceModel.addContent(new Element("Size").setText(Integer.toString(p.getImageSize())));
		pieceModel.addContent(new Element("ImagePath").setText(p.getImagePath()));
		
		pieces.addContent(pieceModel);
	}
	
	doc.getRootElement().addContent(pieces);
	doc.getRootElement().addContent(index);
	doc.getRootElement().addContent(list);

	// new XMLOutputter().output(doc, System.out);
	XMLOutputter xmlOutput = new XMLOutputter();

	// display nice 
	xmlOutput.setFormat(Format.getPrettyFormat());
	xmlOutput.output(doc, new FileWriter(System.getProperty("user.dir")+File.separator+"partida.xml"+File.separator));

	System.out.println("File Saved!");
}

public void readXML(File file){
	//Se crea un SAXBuilder para poder parsear el archivo
	SAXBuilder builder = new SAXBuilder();
	try{
        //Se crea el documento a traves del archivo
        Document document = (Document) builder.build(file);

       
        //Obtener la raiz del documento
       Element model = document.getRootElement();
       Element pieces= model.getChild("Pieces");
       Element Image=model.getChild("Image");
        
       Element index=model.getChild("Index");
       
       int aux=Integer.parseInt(index.getText());
       move.setIndex(aux);
       
       Element Moves=model.getChild("Moves");
       java.util.List<Element> list = Moves.getChildren("move");
       java.util.ArrayList<int[]> pos = new ArrayList();	

       
       for(Element e:list) {
    	   int p[]=new int[2];
    	   p[0]=Integer.parseInt(e.getChild("Pos0").getText());
    	   p[1]=Integer.parseInt(e.getChild("Pos1").getText());
    	   
    	   pos.add(p);
       }
       
       move.setMoves(pos);
        java.util.List<Element> pieceList = pieces.getChildren("pieceModel");		        
        
        
        
        for (int i = 0; i < pieceList.size(); i++) {
        	 Element pieceModel = (Element) pieceList.get(i);
		        
		     System.out.println("Id: " + pieceModel.getChildText("Id"));
		     System.out.println("IndexRow: " + pieceModel.getChildText("X"));
		     System.out.println("IndexColumn: " + pieceModel.getChildText("Y"));
		     System.out.println("Size: " + pieceModel.getChildText("Size"));
		     System.out.println("Path: " + pieceModel.getChildText("Image"));
		     
		    // Elements.add(pieces);
		    
   
        }
        notifyObservers(pieceList,Image);
        
        //PuzzleGUI.getInstance().getBoardView().setIconArray(aux);
        //SetCoordinates();
        PuzzleGUI.getInstance().getBoardView().update(PuzzleGUI.getInstance().getBoardView().getGraphics());
        
        
    }catch(IOException io){
    	System.out.println(io.getMessage());
    } catch (JDOMException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
}

private void notifyObservers(ArrayList<Element> elements, Element image) {
	// TODO Auto-generated method stub
	for(Observer o:observerList) {
		o.loadBoard(elements, image);
	}
}

@Override
public void notifyObservers(int blankPos, int movedPos) {
	// TODO Auto-generated method stub
	for(Observer o:observerList) {
		o.update(blankPos, movedPos);
	}
}
public void addCommand(Command c) {
	this.moveCommands.push(c);
	
}

}