package battleship.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import battleship.Computer;
import battleship.Map;
import battleship.Player;
import battleship.Position;
import battleship.Report;

public class FrameBattle2Pl implements ActionListener, KeyListener {
	UIMapPanel playerPanel = new UIMapPanel("Player");
	UIMapPanel cpuPanel = new UIMapPanel("Computer");
	JFrame frame = new JFrame("BattleShip");
	JPanel comandPanel = new JPanel();
	Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	UIJPanelBG panel = new UIJPanelBG(
			Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/battleImg.jpg")));
	Report rep;
	Computer cpu;
	Map cpuMap;
	Map playerMap;
	int numNaviPlayer = 10;
	int numNaviCPU = 10;
	StringBuilder sb = new StringBuilder();
	boolean b = true;
	UIStatPanel statPlayer;
	UIStatPanel statCPU;
	JPanel targetPanel = new JPanel(null);
	UIJPanelBG target = new UIJPanelBG(
			Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/images/target.png")));
	ImageIcon wreck = new ImageIcon(getClass().getResource("/res/images/wreck.gif"));
	Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	Timer timer;
	boolean turnoDelCPU;

	public FrameBattle2Pl(LinkedList<int[]> playerShips, LinkedList<int[]> advShips, Map mappa) {
		playerMap = mappa;
		cpu = new Computer(mappa);
		cpuMap = new Map();
		// cpuMap.riempiMappaRandom();
		cpuMap.setAdvShips(advShips);
		frame.setSize(1080, 700);
		frame.setTitle("BattleShip - Pirate Edition");
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.addKeyListener(this);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/icon.png")));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Pannello contenente le navi da eliminare
		statPlayer = new UIStatPanel();
		statCPU = new UIStatPanel();
		statPlayer.setBounds(30, 595, 500, 80);
		statCPU.setBounds(570, 595, 500, 80);
		frame.add(statPlayer);
		frame.add(statCPU);
		// Target Panel
		targetPanel.setBounds(0, 0, 500, 500);
		targetPanel.setOpaque(false);
		playerPanel.sea.add(targetPanel);

		panel.add(playerPanel);
		playerPanel.setBounds(0, 0, UIMapPanel.X, UIMapPanel.Y);
		playerPanel.setOpaque(false);
		panel.add(cpuPanel);
		cpuPanel.setBounds(540, 0, UIMapPanel.X, UIMapPanel.Y);
		panel.add(comandPanel);
		frame.add(panel);
		frame.setResizable(false);
		timer = new Timer(2000, new GestoreTimer());
		turnoDelCPU = false;

		for (int i = 0; i < cpuPanel.bottoni.length; i++) {
			for (int j = 0; j < cpuPanel.bottoni[i].length; j++) {
				cpuPanel.bottoni[i][j].addActionListener(this);
				cpuPanel.bottoni[i][j].setActionCommand("" + i + " " + j);
			}
		}
		for (int[] v : playerShips) {
			playerPanel.disegnaNave(v);
		}

	}

	void setCasella(Report rep, boolean player) {
		int x = rep.getP().getCord_X();
		int y = rep.getP().getCord_Y();
		ImageIcon fire = new ImageIcon(getClass().getResource("/res/images/fireButton.gif"));
		ImageIcon water = new ImageIcon(getClass().getResource("/res/images/grayButton.gif"));
		String cosa;
		if (rep.isDamage())
			cosa = "X";
		else
			cosa = "A";
		UIMapPanel mappanel;
		if (!player) {
			mappanel = playerPanel;
		} else {
			mappanel = cpuPanel;
		}
		if (cosa == "X") {
			mappanel.bottoni[x][y].setIcon(fire);
			mappanel.bottoni[x][y].setEnabled(false);
			mappanel.bottoni[x][y].setDisabledIcon(fire);
			mappanel.bottoni[x][y].setCursor(cursorDefault);
		} else {
			mappanel.bottoni[x][y].setIcon(water);
			mappanel.bottoni[x][y].setEnabled(false);
			mappanel.bottoni[x][y].setDisabledIcon(water);
			mappanel.bottoni[x][y].setCursor(cursorDefault);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (turnoDelCPU)
			return;
		JButton source = (JButton) e.getSource();
		StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());
		Position newP = new Position(x, y);
		boolean colpito = cpuMap.shot(newP);
		Report rep = new Report(newP, colpito, false);
		this.setCasella(rep, true);
		if (colpito) { // continua a giocare il player
			Player naveAffondata = cpuMap.sunk(newP);
			if (naveAffondata != null) {
				numNaviCPU--;
				setAffondato(naveAffondata);
				if (numNaviCPU == 0) {
					Object[] options = { "New Match", "Exit" };
					int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Won!", JLabel.CENTER)),
							"Game Over!", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
							options[1]);
					if (n == 0) {
						FrameManageship restart = new FrameManageship();
						restart.setVisible(true);
						this.frame.setVisible(false);
					} else {
						System.exit(0);
					}
				}
			}
		} else { // tocca al CPU

			if (b) {
				timer.start();
				turnoDelCPU = true;
			}
		}
		frame.requestFocusInWindow();
	}

	private void setAffondato(Position p) {
		LinkedList<String> possibilita = new LinkedList<String>();
		if (p.getCord_X() != 0) {
			possibilita.add("N");
		}
		if (p.getCord_X() != Map.MAP_LENGHT - 1) {
			possibilita.add("S");
		}
		if (p.getCord_Y() != 0) {
			possibilita.add("O");
		}
		if (p.getCord_Y() != Map.MAP_LENGHT - 1) {
			possibilita.add("E");
		}
		String direzione;
		boolean trovato = false;
		Position posAttuale;
		do {
			posAttuale = new Position(p);
			if (possibilita.isEmpty()) {
				deleteShip(1, statPlayer);
				playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setIcon(wreck);
				playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setEnabled(false);
				playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setDisabledIcon(wreck);
				playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setCursor(cursorDefault);
				return;
			}
			direzione = possibilita.removeFirst();
			posAttuale.move(direzione.charAt(0));
			if (playerMap.shot(posAttuale)) {
				trovato = true;
			}
		} while (!trovato);
		int dim = 0;
		posAttuale = new Position(p);
		do {

			playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setIcon(wreck);
			playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setEnabled(false);
			playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setDisabledIcon(wreck);
			playerPanel.bottoni[posAttuale.getCord_X()][posAttuale.getCord_Y()].setCursor(cursorDefault);
			posAttuale.move(direzione.charAt(0));

			dim++;
		} while (posAttuale.getCord_X() >= 0 && posAttuale.getCord_X() <= 9 && posAttuale.getCord_Y() >= 0
				&& posAttuale.getCord_Y() <= 9 && !playerMap.ocean(posAttuale));

		deleteShip(dim, statPlayer);
	}

	private void setAffondato(Player naveAffondata) {
		int dim = 0;
		for (int i = naveAffondata.getInput_X(); i <= naveAffondata.getInput_x_final(); i++) {
			for (int j = naveAffondata.getInput_Y(); j <= naveAffondata.getInput_y_final(); j++) {
				cpuPanel.bottoni[i][j].setIcon(wreck);
				cpuPanel.bottoni[i][j].setEnabled(false);
				cpuPanel.bottoni[i][j].setDisabledIcon(wreck);
				cpuPanel.bottoni[i][j].setCursor(cursorDefault);
				dim++;
			}
		}
		deleteShip(dim, statCPU);
	}

	private void deleteShip(int dim, UIStatPanel panel) {
		switch (dim) {
		case 4:
			panel.ships[0].setEnabled(false);
			break;
		case 3:
			if (!panel.ships[1].isEnabled())
				panel.ships[2].setEnabled(false);
			else
				panel.ships[1].setEnabled(false);
			break;
		case 2:
			if (!panel.ships[3].isEnabled())
				if (!panel.ships[4].isEnabled())
					panel.ships[5].setEnabled(false);
				else
					panel.ships[4].setEnabled(false);
			else
				panel.ships[3].setEnabled(false);
			break;
		case 1:
			if (!panel.ships[6].isEnabled())
				if (!panel.ships[7].isEnabled())
					if (!panel.ships[8].isEnabled())
						panel.ships[9].setEnabled(false);
					else
						panel.ships[8].setEnabled(false);
				else
					panel.ships[7].setEnabled(false);
			else
				panel.ships[6].setEnabled(false);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int tasto = arg0.getKeyCode();
		if (tasto == KeyEvent.VK_ESCAPE) {
			FrameManageship manage = new FrameManageship();
			manage.setVisible(true);
			frame.setVisible(false);
		}

		sb.append(arg0.getKeyChar());
		if (sb.length() == 4) {
			int z = sb.toString().hashCode();
			if (z == 3194657) {
				sb = new StringBuilder();
				b = !b;
			} else {
				String s = sb.substring(1, 4);
				sb = new StringBuilder(s);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public class GestoreTimer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			timer.stop();
			boolean flag;

			Report report = cpu.myTurn();
			disegnaTarget(report.getP().getCord_X() * 50, report.getP().getCord_Y() * 50);
			flag = report.isDamage();
			setCasella(report, false);
			if (report.isSunk()) {
				numNaviPlayer--;
				setAffondato(report.getP());
				if (numNaviPlayer == 0) {
					Object[] options = { "New Match", "Exit" };
					int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Won!", JLabel.CENTER)),
							"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
							options[1]);
					if (n == 0) {
						FrameManageship restart = new FrameManageship();
						restart.setVisible(true);
						frame.setVisible(false);
					} else {
						System.exit(0);
					}
				}
			}

			turnoDelCPU = false;
			if (flag) {
				timer.start();
				turnoDelCPU = true;
			}
			frame.requestFocusInWindow();
		}

	}

	public void disegnaTarget(int i, int j) {
		target.setBounds(j, i, 50, 50);
		target.setVisible(true);
		targetPanel.add(target);
		targetPanel.repaint();
	}
}
