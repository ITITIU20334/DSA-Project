import java.util.Scanner;
public class ShipBase {
    private int x;
    private int y;
    private int shipLenght;
    private int axis;
    OceanBattleField field = new OceanBattleField();
    Scanner scn = new Scanner(System.in);
    //Getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getAxis() {
        return axis;
    }
    public int getShipLenght() {
        return shipLenght;
    }

    public void info(int cord_x, int cord_y, int lenght, int s_axis){
        x = cord_x;
        y = cord_y;
        shipLenght = lenght;
        axis = s_axis;
        field.create();
        field.ship_place(y, x, shipLenght, axis);
    }
    // Check ship axis
    public int axisCheck(int axis_check){
        axis = axis_check;
        if( axis == 1 ){ 
            return 1; // vertical place
        }
        else if ( axis == 2 ){
            return 2; // horizontal place
        }
        else{
            return 0;
        }
    }
    public int input(){

        System.out.println("Note: 1 for Scout Ship, 2 for Destroyer, 3 for Mothership");
        System.out.printf("Choose ship: ");
        int input = scn.nextInt();
        if(input  == 3){
            System.out.println("Mothership stanby");

            return 3;   
        }
        else if (input == 2){
            System.out.println("Destroyer stanby");

            return 2;
        }
        else if(input == 1){
            System.out.println("Scout Ship Deploy");

            return 1;
        }
        else{

            return 0;
        }
    }


    
    
}
