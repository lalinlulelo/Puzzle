package command;

import control.Controller;
import view.PuzzleGUI;

public class RandomCommand implements Command {

	private Controller controller;
	public RandomCommand(Controller c) {
		controller=c;
	}
	
	
	@Override
	public void undoCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redoCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		int random=(int) (Math.random()*10);
		
		
		int[]pos = new int[2];
		
		for(int i=0;i<10;i++) {
			
			pos[0]=(int)(Math.random()*(controller.getRowNum()*controller.getColNum()));
			pos[1]=(int)(Math.random()*(controller.getRowNum()*controller.getColNum()));
			
			System.out.println(pos[0]+" "+pos[1]);
			
			controller.getCommandMove().Random(pos[0],pos[1]);
		}
				
	}

}
