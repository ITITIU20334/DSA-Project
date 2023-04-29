import java.util.Scanner;

public class Mothership extends ShipBase{
    Scanner scn = new Scanner(System.in);
    private int ship_x;
    private int ship_y;
    private int ship_lenght;
    private int ship_axis;

    public int getShip_x() {
        return ship_x;
    }
    public int getShip_y() {
        return ship_y;
    }
    public int getShip_lenght() {
        return ship_lenght;
    }
    public int getShip_axis() {
        return ship_axis;
    }

    public void standby(){
        System.out.printf("Enter x: ");
        ship_x = scn.nextInt();
        System.out.printf("Enter y: ");
        ship_y = scn.nextInt();
        ship_lenght = 4;
        System.out.println("Note: 1 for Verticle, 2 for horizontal");
        System.out.printf("Enter ship axis: ");
        ship_axis = scn.nextInt();
        System.out.println("Mothership ready");
        info(ship_x, ship_y, ship_lenght, ship_axis);
    }
}


