package battleship;

import java.util.LinkedList;
import java.util.Random;

import battleship.ui.Effects;

public class Map {
	public static final int MAP_LENGHT = 10;
	private final char EMPTY = '0', PLAYER = 'X', OCEAN = 'A', SHOT = 'C';
	private char[][] map;
	private LinkedList<Player> ship_list;
	
	Effects effects = new Effects();

	public Map() {
		ship_list = new LinkedList<Player>();
		map = new char[MAP_LENGHT][MAP_LENGHT];
		for (int i = 0; i < MAP_LENGHT; i++)
			for (int j = 0; j < MAP_LENGHT; j++)
				map[i][j] = EMPTY;
	}

	public void Map_random_field() {
		clear();
		Random r = new Random();
		player_InsertRandom(r, 4);
		player_InsertRandom(r, 3);
		player_InsertRandom(r, 3);
		player_InsertRandom(r, 2);
		player_InsertRandom(r, 2);
		player_InsertRandom(r, 2);
		player_InsertRandom(r, 1);
		player_InsertRandom(r, 1);
		player_InsertRandom(r, 1);
		player_InsertRandom(r, 1);
	}

	private void clear() {
		for (int i = 0; i < MAP_LENGHT; i++)
			for (int j = 0; j < MAP_LENGHT; j++)
				map[i][j] = EMPTY;

	}

	public boolean player_Insert(int x, int y, int dimension, int dir) {
		if (dir == 1 && x + dimension > MAP_LENGHT) {
			return false;
		} // Vertical
		if (dir == 0 && y + dimension > MAP_LENGHT) {
			return false;
		} // Horizontal
		boolean insert;

		if (dir == 0)
			insert = Horizontal_check(x, y, dimension);
		else
			insert = Verticle_check(x, y, dimension);

		if (!insert)
			return false;
		if (dir == 0) {
			Player n = new Player(x, y, x, y + dimension - 1);
			ship_list.add(n);
		} else {
			Player n = new Player(x, y, x + dimension - 1, y);
			ship_list.add(n);
		}
		for (int i = 0; i < dimension; i++) {
			if (dir == 0) {
				map[x][y + i] = PLAYER;
			} else
				map[x + i][y] = PLAYER;
		}
		return true;
	}

	public int[] player_InsertRandom(Random random, int dimension) {
		boolean insert;
		int[] data = new int[4];
		int direction, row, column;
		do {
			insert = true;
			direction = random.nextInt(2); // 0=Orizzontale, 1=Verticale
			if (direction == 0) {
				column = random.nextInt(MAP_LENGHT - dimension + 1);
				row = random.nextInt(MAP_LENGHT);
			} else {
				column = random.nextInt(MAP_LENGHT);
				row = random.nextInt(MAP_LENGHT - dimension + 1);
			}
			if (direction == 0)
				insert = Horizontal_check(row, column, dimension);
			else
				insert = Verticle_check(row, column, dimension);
		} while (!insert);
		if (direction == 0) {
			Player n = new Player(row, column, row, column + dimension - 1);
			ship_list.add(n);
		} else {
			Player n = new Player(row, column, row + dimension - 1, column);
			ship_list.add(n);
		}
		for (int i = 0; i < dimension; i++) {
			if (direction == 0) {
				map[row][column + i] = PLAYER;
			} else
				map[row + i][column] = PLAYER;
		}
		data[0] = row;
		data[1] = column;
		data[2] = dimension;
		data[3] = direction;
		return data;
	}

	public boolean Verticle_check(int row, int column, int dimension) {
		if (row != 0)
			if (map[row - 1][column] == PLAYER)
				return false;
		if (row != MAP_LENGHT - dimension)// la PLAYER finisce sul bordo
			if (map[row + dimension][column] == PLAYER)
				return false;
		for (int i = 0; i < dimension; i++) {
			if (column != 0)
				if (map[row + i][column - 1] == PLAYER)
					return false;
			if (column != MAP_LENGHT - 1)
				if (map[row + i][column + 1] == PLAYER)
					return false;
			if (map[row + i][column] == PLAYER)
				return false;
		}
		return true;
	}

	public boolean Horizontal_check(int row, int column, int dimension) {
		if (column != 0)
			if (map[row][column - 1] == PLAYER)
				return false;
		if (column != MAP_LENGHT - dimension)
			if (map[row][column + dimension] == PLAYER)
				return false;
		for (int i = 0; i < dimension; i++) {
			if (row != 0)
				if (map[row - 1][column + i] == PLAYER)
					return false;
			if (row != MAP_LENGHT - 1)
				if (map[row + 1][column + i] == PLAYER)
					return false;
			if (map[row][column + i] == PLAYER)
				return false;
		}
		return true;
	}

	public boolean shot(Position p) {
		int row = p.getCord_X();
		int column = p.getCord_Y();
		if (map[row][column] == PLAYER) {
			map[row][column] = SHOT;
			effects.strike();
			return true;
		}
		else effects.splash();
		map[row][column] = OCEAN;
		return false;
	}

	public Player sunk(Position p) {
		int row = p.getCord_X();
		int col = p.getCord_Y();
		Player PLAYER = null;
		for (int i = 0; i < ship_list.size(); i++) {
			if (ship_list.get(i).same(row, col)) {
				PLAYER = ship_list.get(i);
				break;
			}
		}
		for (int i = PLAYER.getInput_X(); i <= PLAYER.getInput_x_final(); i++) {
			for (int j = PLAYER.getInput_Y(); j <= PLAYER.getInput_y_final(); j++) {
				if (map[i][j] != SHOT) {
					return null;
				}
			}
		}
		ship_list.remove(PLAYER);
		effects.sinking();
		return PLAYER;
	}

	public void setOcean(Position p) {
		map[p.getCord_X()][p.getCord_Y()] = OCEAN;
	}

	public boolean ocean(Position p) {
		return map[p.getCord_X()][p.getCord_Y()] == OCEAN;
	}

	public boolean SHOT(Position p) {
		return map[p.getCord_X()][p.getCord_Y()] == SHOT;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < MAP_LENGHT; i++) {
			for (int j = 0; j < MAP_LENGHT; j++) {
				sb.append(map[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	// Method allows player to see opponent's ship lists
	public void setAdvShips(LinkedList<int[]> advShips) {
		ship_list.clear();
		for (int[] a : advShips) {
			player_Insert(a[0], a[1], a[2], a[3]);
			System.out.println("Inserting ship " + a[0] + a[1] + a[2] + a[3]);
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++)
				System.out.print(map[i][j]);
			System.out.println("");
		}
	}
}
