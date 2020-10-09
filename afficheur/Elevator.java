import javax.swing.SwingUtilities;

public class Elevator {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){ //instanciation d'une fenêtre calculatrice
                ElevatorWindow window = new ElevatorWindow();
                window.setVisible(true);
            }
        });
    }
}
