
public class OceanBattleField{

    public int x;
    public int y;
    private String[][] field;

    //Getter
    public String[][] getField() {
        return field;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //Create battlefield
    public void create(){
        x = 10; // default
        y = 10; // default
        field = new String[x][y];
        System.out.println("Field created");
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                field[i][j] = " - ";
            }
        }
    }

    public void showField(){
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                System.out.printf(field[i][j]);
            }
            System.out.println();
        }
    }
    
    // Place Ship ( 1 for vertical place, 2 for horizontal place )
    public void ship_place(int cord_y, int cord_x, int lenght, int axis){
        ShipBase base = new ShipBase();
        int a = cord_x -1;
        int b = cord_y -1;
        int counter = 0;
        // Verticle place
        if( base.axisCheck(axis) == 1 ){
            for(int i = 0; i < y; i++){
                for(int j = 0; j < x; j++){
                    if((y-1)-(cord_y-1)+1 >= lenght){ // Prevent Array overflow by rotating the ship to suitable position
                        field[b][a] = " x "; 
                        counter++;
                        if(counter < lenght){
                            field[b++][a] = " x ";
                        }
                    }   
                    else{
                        field[b][a] = " x "; 
                        counter++;
                        if(counter < lenght){
                            field[b][a++] = " x ";
                        }
                    }
                         
                }
            }
        }
        // Horizontal place
        else if ( base.axisCheck(axis) == 2){   // Prevent Array overflow by rotating the ship to suitable position
            for(int i = 0; i < y; i++){
                for(int j = 0; j < x; j++){
                    if(((x-1)-(cord_x-1)+1) >= lenght){
                        field[b][a] = " x "; 
                        counter++;
                        if(counter < lenght){
                            field[b][a++] = " x ";
                        }
                    }
                    else{
                        field[b][a] = " x "; 
                        counter++;
                        if(counter < lenght){
                            field[b++][a] = " x ";
                        }
                    } 
                }
            }
        }    
        else if( ((x-1)-(cord_x-1)+1) < lenght && (y-1)-(cord_y-1)+1 < lenght){
            System.out.println("You cannot place ship in here");
            return;
        }
    
        // Axis unknown case ( User enter wrong num )
        else{
            System.out.println("Please enter another value");
            return;
        }
        showField();
    }
}