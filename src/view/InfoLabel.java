package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;


import aggregates.Observer;
import controller.ScrabbleController;

public class InfoLabel extends JLabel implements Observer {

	private String name;
	
	public InfoLabel(ScrabbleController ctrl, String name) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ctrl.addObserver(this);
		this.name = name;
	}
	
	@Override
	public void updateInfo(String nStr) {
		this.setText("<html><body style='width: 180px'>" + nStr + "</body></html>");
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension size = super.getPreferredSize();
		size.width = 250;
		size.height = 100;
		return size;
	}

}
