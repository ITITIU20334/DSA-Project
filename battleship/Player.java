package battleship;

public class Player {

	private int input_X, input_Y;
	private int input_x_final, input_y_final;

	public Player(int input_X, int input_Y, int input_X_final, int input_y_final) {
		this.input_X = input_X;
		this.input_Y = input_Y;
		this.input_x_final = input_X_final;
		this.input_y_final = input_y_final;
	}

	public int getInput_X() {
		return input_X;
	}
	public int getInput_Y() {
		return input_Y;
	}

	public int getInput_x_final() {
		return input_x_final;
	}

	public int getInput_y_final() {
		return input_y_final;
	}

	public boolean same(int x, int y) {
		if (x <= input_x_final && x >= input_X && y <= input_y_final && y >= input_Y) {
			return true;
		}
		return false;

	}

	public String toString() {
		return input_X + "-" + input_Y + "  " + input_x_final + "-" + input_y_final;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (input_x_final != other.input_x_final)
			return false;
		if (input_X != other.input_X)
			return false;
		if (input_y_final != other.input_y_final)
			return false;
		if (input_Y != other.input_Y)
			return false;
		return true;
	}

}
