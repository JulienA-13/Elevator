package operative;

/**
 * La classe Sensor est utilisée pour identifier les capteurs.
 * @author benjamindaunar
 * @version 1.0.0
 *
 */
public class Sensor {
	
	private int floor;		//Etage où se situe le capteur
	private int weight;		//Poids en temps réel de la cabine
	
	/**
	 * Constructeur de la classe sensor initialisant son attribut d'identification d'étage à 
	 * floor.
	 * @param floor
	 */
	public Sensor (int floor) {
		this.floor = floor;		//  Pour l'exemple nous avons prédéfini une masse 
		weight = 320;			//  représentative d'un foyer français moyen composé
	}							//  d'un couple et deux enfants.
	
	
	public int getFloor() { return this.floor; }
	
	public void setFloor(int floor) { this.floor = floor; }
	
	public int getWeight() { return weight; }
	
	public void setWeight(int weight) { this.weight = weight; }
	
	/**
	 * Cette méthode est utilisée par le bouton d'arrêt d'urgence afin de transmettre la requête
	 * au contrôle commande controller.
	 * @param controller
	 */
	public void emergency_signal (Controller controller) {
		controller.setEmergencyStatus(true);
	}
	
	/**
	 * Cette méthode est utilisée par le bouton d'appel en de l'ascenseur pour monter.
	 * @param controller
	 */
	public void sendUpSignal(Controller controller) {
		controller.setCallUpOn(this.floor);
	}
	
	/**
	 * Cette méthode est utilisée par le bouton d'appel de l'ascenseur pour descendre.
	 * @param controller
	 */
	public void sendDownSignal(Controller controller) {
		controller.setCallDownOn(this.floor);
	}
	
	/**
	 * Cette méthode est utilisée par le panneau d'appel de la cabine permettant de 
	 * choisir l'étage où l'on souhaite se rendre.
	 * @param controller
	 * @param dest_floor
	 * @param elevator
	 */
	public void sendDestination(Controller controller, int dest_floor, Elevator elevator) {
		int position = elevator.getPosition();
		int floor = dest_floor * elevator.getCoeffPos();
		if (floor > position) {
			controller.setCallUpOn(this.floor);
		}
		else {
			controller.setCallDownOn(this.floor);
		}
	}
	
	/**
	 * Cette méthose est utilisée pour vérifier que l'ascenseur ne dépasse pas son
	 * poids maximal autorisé.
	 * @param elevator
	 * @return Vrai si l'ascenseur dépasse son poids maximal autorisé en charge.
	 */
	public boolean isTooHeavy(Elevator elevator) {
		int max = elevator.getMaxWeight();
		return (this.weight > max);
	}
}
