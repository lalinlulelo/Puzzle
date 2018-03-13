package command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import control.Controller;
import view.PuzzleGUI;

public class MoveCommand implements Command{

	private List<int[]> list=new ArrayList();
	private Controller controller;
	private int index=0;
	
	
	public MoveCommand(Controller c) {
	this.controller=c;	
	}
	
	@Override
	public void undoCommand() {
		
		
			// TODO Auto-generated method stub
			while(index>0) {
				
				int pos[]=list.get(index-1);
				System.err.println(index);
				index--;
				if(pos==null) {
					return;
				}
				
				controller.notifyObservers(pos[1],pos[0]);
				
			}
		}
		
		
	

	@Override
	public void redoCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void execute() {
		
		
		
		
		
		int[] pos=PuzzleGUI.getInstance().getBoardView().movePiece(controller.getPosX(), controller.getPosY());


		
		if(pos==null) {
			return;
		}
		
		try {
			list.set(index,pos);
		}catch (Exception e) {
			list.add(index,pos);

		}
		index++;
		
		controller.notifyObservers(pos[0],pos[1]);
	}

	public int getIndex() {
		return index;
	}

	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	

	

}
