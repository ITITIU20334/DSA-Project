package battleship;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import battleship.ui.*;

public class Computer {
	private LinkedList<Position> hitList;
	private Random r;
	private int hit;
	private LinkedList<String> possibility;
	private Position lastShot;
	private String direction;
	private Map plMap;
	private Position firstShot;
	
	private int i = 0;
	private int step = 2;

	public Computer(Map mapOpponent) {
		hitList = new LinkedList<Position>();
		this.plMap = mapOpponent;
		for (int i = 0; i < Map.MAP_LENGHT; i++) {
			for (int j = 0; j < Map.MAP_LENGHT; j++) {
				Position p = new Position(i, j);
				hitList.add(p);
			}
		}
		r = new Random();
		hit = 0;
	}

	public Report myTurn() {

		Report rep = new Report();
		if (hit == 0) {
			boolean shoot = stepMove();
			rep.setP(lastShot);
			rep.setDamage(shoot);
			Player sunk;
			if (shoot) {
				hit++;
				sunk = plMap.sunk(lastShot);
				if (sunk != null) {
					rep.setSunk(true);
					remove_Outline(sunk);
					hit = 0;
					direction = null;
				} else {
					firstShot = lastShot;
					possibility = new LinkedList<String>();
					List_initialize();
				}
			}
			return rep;
		}
		if (hit == 1) {
			boolean shoot = shotAim1();
			Player sunk;
			rep.setP(lastShot);
			rep.setDamage(shoot);
			rep.setSunk(false);
			if (shoot) {
				hit++;
				possibility = null;
				sunk = plMap.sunk(lastShot);
				if (sunk != null) {
					rep.setSunk(true);
					remove_Outline(sunk);
					hit = 0;
					direction = null;
				}
			}
			return rep;
		}
		if (hit >= 2) {
			boolean shoot = shotAim2();
			Player sunk;
			rep.setP(lastShot);
			rep.setDamage(shoot);
			rep.setSunk(false);
			if (shoot) {
				hit++;
				sunk = plMap.sunk(lastShot);
				if (sunk != null) {
					rep.setSunk(true);
					remove_Outline(sunk);
					hit = 0;
					direction = null;
				}
			} else {
				invertDirection();
			}
			return rep;
		}
		return null;// irragiungibile
	}

	private boolean stepMove() {
		Position p = hitList.remove(i);
		lastShot = p;
		boolean shoot = plMap.shot(p);
		if (!shoot) {
			i = i+step;
		}
		if (i > hitList.size()) {
			i = i - hitList.size();
		}
		return shoot;
	}

	private boolean shotAim1() {
		boolean err = true;
		Position p = null;
		do {
			int t = r.nextInt(possibility.size());
			String location = possibility.remove(t);
			p = new Position(firstShot);
			p.move(location.charAt(0));
			direction = location;
			if (!plMap.ocean(p)) {
				hitList.remove(p);
				err = false;
			}
		} while (err);

		lastShot = p;
		return plMap.shot(p);
	}

	private boolean shotAim2() {
		boolean shootable = false;
		Position p = new Position(lastShot);
		do {
			p.move(direction.charAt(0));

			if (p.map_Limit() || plMap.ocean(p)) {
				invertDirection();
			} else {
				if (!plMap.shot(p)) {
					shootable = true;
				}

			}
		} while (!shootable);
		hitList.remove(p);
		lastShot = p;
		return plMap.shot(p);
	}


	private void remove_Outline(Player sunk) {
		int Xin = sunk.getInput_X();
		int Xfin = sunk.getInput_x_final();
		int Yin = sunk.getInput_Y();
		int Yfin = sunk.getInput_y_final();
		if (Xin == Xfin) {
			if (Yin != 0) {
				Position p = new Position(Xin, Yin - 1);
				if (!plMap.ocean(p)) {
					hitList.remove(p);
					plMap.setOcean(p);

				}
			}
			if (Yfin != Map.MAP_LENGHT - 1) {
				Position p = new Position(Xin, Yfin + 1);
				if (!plMap.ocean(p)) {
					hitList.remove(p);
					plMap.setOcean(p);
				}
			}
			if (Xin != 0) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin - 1, Yin + i);
					if (!plMap.ocean(p)) {
						hitList.remove(p);
						plMap.setOcean(p);
					}
				}

			}
			if (Xin != Map.MAP_LENGHT - 1) {
				for (int i = 0; i <= Yfin - Yin; i++) {
					Position p = new Position(Xin + 1, Yin + i);
					if (!plMap.ocean(p)) {
						hitList.remove(p);
						plMap.setOcean(p);
					}
				}
			}
		} else {
			if (Xin != 0) {
				Position p = new Position(Xin - 1, Yin);
				if (!plMap.ocean(p)) {
					hitList.remove(p);
					plMap.setOcean(p);
				}
			}
			if (Xfin != Map.MAP_LENGHT - 1) {
				Position p = new Position(Xfin + 1, Yin);
				if (!plMap.ocean(p)) {
					hitList.remove(p);
					plMap.setOcean(p);
				}
			}
			if (Yin != 0) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin - 1);
					if (!plMap.ocean(p)) {
						hitList.remove(p);
						plMap.setOcean(p);
					}
				}

			}
			if (Yfin != Map.MAP_LENGHT - 1) {
				for (int i = 0; i <= Xfin - Xin; i++) {
					Position p = new Position(Xin + i, Yin + 1);
					if (!plMap.ocean(p)) {
						hitList.remove(p);
						plMap.setOcean(p);
					}
				}
			}
		}
	}

	private void List_initialize() {
		if (lastShot.getCord_X() != 0) {
			possibility.add("N");
		}
		if (lastShot.getCord_X() != Map.MAP_LENGHT - 1) {
			possibility.add("S");
		}
		if (lastShot.getCord_Y() != 0) {
			possibility.add("O");
		}
		if (lastShot.getCord_Y() != Map.MAP_LENGHT - 1) {
			possibility.add("E");
		}
	}

	private void invertDirection() {
		if (direction.equals("N")) {
			direction = "S";
		} else if (direction.equals("S")) {
			direction = "N";
		} else if (direction.equals("E")) {
			direction = "O";
		} else if (direction.equals("O")) {
			direction = "E";
		}
	}

}
