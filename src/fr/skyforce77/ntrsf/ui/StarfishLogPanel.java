package fr.skyforce77.ntrsf.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class StarfishLogPanel extends JPanel {

	private static final long serialVersionUID = 6325683364711672L;
	
	private List<String> history;
	private int historyIndex = -1;
	
	private JTextArea area;
	private JTextField field;
	
	public StarfishLogPanel() {
		history = new LinkedList<String>();
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		area = new JTextArea();
		area.setEditable(false);
		NTRLogger.setArea(area);
		
		field = new JTextField();
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		field.setMinimumSize(new Dimension(0, 40));
		field.addActionListener(new CommandListener(this));
		field.addKeyListener(new HistoryListener(this));
		
		add(new JScrollPane(area));
		add(field);
	}
	
	public void command(String command) {
		NTRLogger.println("> "+command);
		history.add(command);
		historyIndex = history.size();
		updateHistory();
		
		String[] parts = command.split(" ");
		command = parts[0];
		String[] args = new String[parts.length-1];
		System.arraycopy(parts, 1, args, 0, args.length);
		Starfish.getPluginManager().callCommand(command, args);
	}
	
	public void up() {
		if(historyIndex-1 >= 0) {
			historyIndex-=1;
			updateHistory();
		}
	}
	
	public void down() {
		if(historyIndex+1 <= history.size()) {
			historyIndex+=1;
			updateHistory();
		}
	}
	
	public void updateHistory() {
		if(historyIndex == history.size()) {
			field.setText("");
		} else if(historyIndex >= 0 && historyIndex < history.size()) {
			field.setText(history.get(historyIndex));
		}
	}
	
	private class HistoryListener implements KeyListener {
		
		private StarfishLogPanel logPanel;
		
		public HistoryListener(StarfishLogPanel logPanel) {
			this.logPanel = logPanel;
		}
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				logPanel.up();
			} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				logPanel.down();
			}
		}
	}
	
	private class CommandListener implements ActionListener {
		
		private StarfishLogPanel logPanel;
		
		public CommandListener(StarfishLogPanel logPanel) {
			this.logPanel = logPanel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JTextField field = (JTextField)arg0.getSource();
			logPanel.command(field.getText());
			field.setText("");
		}
		
	}

}
