package operative;

import javax.swing.*;

public class Main {

    public static Controller controller = new Controller(11, 0);
    public static Elevator elevator = new Elevator(controller.INIT_FLOOR);
    public static Sensor[] sensors = new Sensor[controller.NUMBER_OF_FLOORS];

    public static void main(String[] args) {
        if (controller.NUMBER_OF_FLOORS > controller.INIT_FLOOR && controller.INIT_FLOOR >= 0) {

            for (int i = 0; i < controller.NUMBER_OF_FLOORS; i++) {
                sensors[i] = new Sensor(i);
            }
            Screen screen = new Screen("Bienvenue", controller.INIT_FLOOR);

            int position, direction, destination;

            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    ElevatorWindow window = new ElevatorWindow();
                    window.setVisible(true); /* Affichage de l'IGT */
                }
            });

            /* temps que l'ascenseur n'est pas en position d'arret d'urgence
             * Main Loop
             */
            while (!controller.emergency_status) {

                position = elevator.getPosition();
                direction = elevator.getDirection();
                destination = position;

                /* Si la liste d'attente n'est pas vide */
                if(!controller.waitingListEmpty()) {

                    /* Choix de la prochaine destination  en fonction du sens de
                     * déplacement actuel de l'ascenseur, ou de la destination la plus
                     * proche lorsque ce dernier était à l'arrêt.
                     */
                    if(direction == Elevator.UP) {
                        destination = controller.nextUpFloor(position);
                    } else if(direction == Elevator.DOWN) {
                        destination = controller.nextDownFloor(position);
                    } else {
                        destination = controller.nearFloor(position);
                    }

                    /*
                     * Comportement une fois la nouvelle destination de l'ascenseur
                     * sélectionnée.
                     */
                    if (destination * Elevator.COEFF_POS > position) {
                        elevator.moveUp(destination, controller);
                        if(controller.nextUpFloor(destination) == destination) {
                            if (controller.nextDownFloor(destination) == destination) {
                                elevator.setDirection(Elevator.STOP);
                            } else {
                                elevator.setDirection(Elevator.DOWN);
                            }
                        }
                    }
                    if (destination * Elevator.COEFF_POS < position) {
                        elevator.moveDown(destination, controller);
                        if(controller.nextDownFloor(destination) == destination) {
                            if (controller.nextUpFloor(destination) == destination) {
                                elevator.setDirection(Elevator.STOP);
                            } else {
                                elevator.setDirection(Elevator.UP);
                            }
                        }
                    }

                    /*
                     * Affichage à l'écran.
                     */
                    screen.setFloor(position / Elevator.COEFF_POS);
                    screen.display();
                }
            }
            if(controller.emergency_status) {
                System.out.println("Emergency state: please call a technician");
            } else {
                System.out.println("Fatal error: got out of main loop");
            }
        } else {
            System.out.println("System fatal main bug detected : System not working");
            System.out.println("Static variables wrongly initialized for the main");
        }




    }
}
