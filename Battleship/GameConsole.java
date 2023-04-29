
public class GameConsole {
    ShipBase ship = new ShipBase();
    OceanBattleField field = new OceanBattleField();
    
    public void start(){
        System.out.println("Game Start");
        int counter = 4;
        for(int i = 0; i < counter; i++){
            if(ship.input() == 3){
                Mothership main_ship = new Mothership();
                main_ship.standby();
            }
            else if(ship.input() == 2){
                Destroyer destroyer = new Destroyer();
    
            }
            else if(ship.input() == 1){
                ScoutShip scout = new ScoutShip();
            }
            else{
                System.out.println("Dafuk is this ship ??");
                System.out.println("Choose ship again");
            }
        }
        

        

       


        


        
    }
}
