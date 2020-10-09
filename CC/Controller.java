package operative;

import javax.swing.*;

/**
 * La classe Controller est utilisée par le controle commande de l'ascenseur afin de gérer
 * ses déplacements dans l'immeuble
 * @author benjamindaunar
 * @version 1.0.0
 *
 */
public class Controller{


	private static int UP = 0;
	private static int DOWN = 1;
	
	public boolean emergency_status;		//Etat de l'ascenseur, vrai si en état d'arret d'urgence
	public int NUMBER_OF_FLOORS;			//Nombre d'étages desservis par l'ascenseur
	public int INIT_FLOOR;		//Etage de départ
	/*liste d'attente pour les appels de l'ascenseur 
	 * Ce tableau est fait en deux dimensions afin de distinguer les requetes
	 * pour monter de celles pour descendre.
	 */
	private static int[][] waiting_list;
	
	public Controller(int n_floors, int init) {
		NUMBER_OF_FLOORS = n_floors;
		INIT_FLOOR= init;
		waiting_list = new int[2][NUMBER_OF_FLOORS];
	}
	
	public boolean getEmergencyStatus() { 
		return emergency_status; 
		}

	public void setEmergencyStatus(boolean status) {
		emergency_status = status;
	}
	
	/*
	 * Cette méthode est utilisée pour vérifier si la liste d'attente d'appels à l'ascenseur est vide.
	 */
	public boolean waitingListEmpty() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < waiting_list.length; j++) {
				if (waiting_list[i][j] == 1) { return false; }
			}
		}
		return true;
	}
	
	/**
	 * Cette méthode est utilisée afin de vérifier s'il reste des étages en attente
	 * dans les niveaux supérieurs.
	 * @param position
	 * @return l'étage atuel si aucun autre étage n'est trouvé.
	 */
	public int nextUpFloor(int position) {
		int new_destination = NUMBER_OF_FLOORS;
		while(new_destination * Elevator.COEFF_POS > position) {
			if(waiting_list[UP][new_destination - 1] == 1)
				new_destination -= 1;
		}
		if (new_destination == NUMBER_OF_FLOORS) {
			return position / Elevator.COEFF_POS;
		} else { return new_destination; }
	}
	
	/**
	 * Cette méthode est utilisée afin de vérifier s'il reste des étages en attente
	 * dans les niveaux inférieurs.
	 * @param position
	 * @return,le numéro de l'étage actuel si aucun autre étage n'est trouvé
	 */
	public int nextDownFloor(int position) {
		int new_destination = -1;
		while(new_destination * Elevator.COEFF_POS > position) {
			if(waiting_list[UP][new_destination + 1] == 1)
				new_destination += 1;
		}
		if (new_destination == -1) {
			return position / Elevator.COEFF_POS;
		} else { return new_destination; }
	}
	
	/**
	 * Cette méthode est utilisée afin de vérifier quel est l'étage le plus proche en
	 * attente lorsque plusieurs requêtes arrivent en même temps.
	 * @param position
	 * @return l'étage actuel si aucun autre étage n'est trouvé
	 */
	public int nearFloor(int position) {
		int up = nextUpFloor(position);
		int down = nextDownFloor(position);
		
		if (up - position / Elevator.COEFF_POS >= position / Elevator.COEFF_POS - down) {
			return up;
		} else {
			return down;
		}
	}

	public void setCallUpOn(int floor) {
		waiting_list[UP][floor] = 1;
	}

	public void setCallUpOff(int floor) {
		waiting_list[UP][floor] = 0;
	}

	public void setCallDownOn(int floor) {
		waiting_list[DOWN][floor] = 1;
	}

	public void setCallDownOff(int floor) {
		waiting_list[DOWN][floor] = 0;
	}
	


}
