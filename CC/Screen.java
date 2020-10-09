package operative;

/**
 * La classe Screen est utilisée pour gérer l'affichage sur l'acran
 * de la cabine de l'ascenseur ou encore des afficheurs externes à 
 * la colonne de montée.
 * @author benjamindaunar
 * @version 1.0.0
 *
 */
public class Screen {
	
	private String message;		//Message à afficher 
	private int floor;			//Etage auquel se situe l'ascenseur
	
	public Screen(String msg, int floor) {
		message = msg;
		this.floor = floor;
	}
	
	public String getMessage() { return this.message; }
	
	public void setMessage(String msg) { this.message = msg; }
	
	public int getFloor() { return this.floor;}
	
	public void setFloor(int floor) { this.floor = floor; }
	
	/**
	 * Cette méthode permet de transmettre à l'écran le message et
	 * l'étage auquel se situe l'ascenseur afin qu'il puisse les afficher.
	 */
	public void display() { System.out.println(message + "\n" + floor); }
	
}
