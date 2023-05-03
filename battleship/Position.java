package battleship;

public class Position {
	private int cord_X,cord_Y;

	public int getCord_X() {
		return cord_X;
	}

	public int getCord_Y() {
		return cord_Y;
	}

	public Position(int cord_X, int cord_Y) {
		this.cord_X = cord_X;
		this.cord_Y = cord_Y;
	}

	public Position(Position p){
		this.cord_X = p.cord_X;
		this.cord_Y = p.cord_Y;
	}
	
	public void move(char location){
		switch(location){
			case 'N':
				cord_X--;
				break;
			case 'S':
				cord_X++;
				break;
			case 'O':
				cord_Y--;
				break;
			case 'E':
				cord_Y++;
				break;
		}
	}
	
	public String toString(){
		char Y=(char)(cord_Y+65);
		return ""+(cord_X+1)+" "+Y;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (cord_X != other.cord_X)
			return false;
		if (cord_Y != other.cord_Y)
			return false;
		return true;
	}
	
	public boolean map_Limit(){
		if(cord_X>=Map.MAP_LENGHT||cord_Y>=Map.MAP_LENGHT||cord_X<0||cord_Y<0)
			return true;
		return false;
	}
	
	
}
