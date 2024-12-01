package view;

import javax.swing.JLabel;


import aggregates.Observer;
import controller.ScrabbleController;

public class InfoLabel extends JLabel implements Observer {

	private String name;
	
	public InfoLabel(ScrabbleController ctrl, String name) {
		ctrl.addObserver(this);
		this.name = name;
	}
	
	@Override
	public void updateInfo(String nStr) {
		this.setText(nStr);
	}
	
	public String getName() {
		return name;
	}

}
