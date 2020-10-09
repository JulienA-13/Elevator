package operative;

public class Elevator {
	
	/* 
	 * arret, monter et descendre constituent les différents sens de déplacement
	 * de l'ascenseur
	 */
	static int STOP = 0, UP = 1, DOWN = -1;
	
	static int COEFF_POS = 1;
	static int DIST_ARRET = 0;
	
	public static int MAX_SPEED = 2;		//Vitesse maximale atteinte par l'ascenseur
	public static int MAX_WEIGHT = 1000;	//Poids maximal autorisé en charge

	private int position;				//Position en temps réel de l'ascenseur
	private int direction;				//Sens de déplacement de l'ascenseur
	private int speed;					//Vitesse en temps réel de l'ascenseur
	private boolean emergency_stop;		//Permet d'identifier la mise en arrêt d'urgence si vrai.
	
	
	/**
	 * Constructeur de la classe ascenseur utilisé pour initialiser un ascenseur 
	 * en fonction de son étage de départ.
	 * @param init_floor
	 */
	public Elevator(int init_floor) {
		position = init_floor * COEFF_POS;
		direction = STOP;
		speed = 0;
		emergency_stop = false;
	}
	
	public int getPosition() { return position; }
	
	public void setPosition(int position) { this.position = position; }
	
	public int getDirection() { return this.direction; }
	
	public void setDirection(int direction) { this.direction = direction; }
	
	public int getMaxWeight() { return MAX_WEIGHT; }

	public int getSpeed() { return speed; }

	public void setSpeed(int speed) { this.speed = speed; }
	
	public int getCoeffPos() { return COEFF_POS; }
	
	public int getDistArret() { return DIST_ARRET; }

	public boolean getEmergency_stop() {
		return emergency_stop;
	}

	public void setEmergency_stop(boolean emergency_stop) {
		this.emergency_stop = emergency_stop;
	}
	
	/**
	 * Cette methode est utilisée par l'appelant pour que la cabine monte
	 * jusqu'à l'étage indiqué en paramètre.Une fois arrivé, enlève l'étage 
	 * atteint de la liste d'attente du controle commande.
	 * @param dest
	 */
	public void moveUp(int floor, Controller controller) {
		int dest = COEFF_POS * floor;
		while (!emergency_stop) {
			if (position < dest - DIST_ARRET) {
				if (speed < MAX_SPEED) {
					speed++;
				}
			}
			if (position < dest) {
				if (speed > 1) {
					speed--;
				}
			}
			if (position >= dest) {
				speed = 0;
				controller.setCallUpOff(floor);
				break;
			}
			position += speed;
			System.out.println(position);
		}
	}
	
	/**
	 * Cette methode est utilisée par l'appelant pour que la cabine descende
	 * jusqu'à l'étage indiqué en paramètre. Une fois arrivé, enlève l'étage 
	 * atteint de la liste d'attente du controle commande.
	 * @param dest
	 * @param controller
	 */
	public void moveDown(int floor, Controller controller) {
		int dest = COEFF_POS * floor;
		while (!emergency_stop) {
			if (position > dest + DIST_ARRET) {
				if (speed < MAX_SPEED) {
					speed++;
				}
			}
			if (position > dest) {
				if (speed > 1) {
					speed--;
				}
			}
			if (position <= dest) {
				speed = 0;
				controller.setCallDownOff(floor);
				break;
			}
			position -= speed;

		}
	}
	
	/**
	 * Cette méthode est utilisée par l'appelant afin d'activer la mise en arret
	 * d'urgence de la cabine.
	 */
	public void emergencyStopOn() { 
		emergency_stop = true; 
		speed = 0; 					//  Arret de l'ascenseur quoiqu'il arrive.
	}
	
	/**
	 * Cette méthode est utlisée par l'appelant pour remettre en état normal de 
	 * marche l'ascenseur lorsque emergencyStopOn() a été appelé.
	 */
	public void emergencyStopOff(Controller controller) {
		emergency_stop = false;
		controller.setEmergencyStatus(false);
		this.moveDown(0, controller);			//  L'ascenseur redescend une fois le retour à la normale
	}
	
}
