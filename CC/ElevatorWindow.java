package operative;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.*;
import java.awt.event.*;

public class ElevatorWindow extends JFrame implements ActionListener {

    private int height = 700;
    private int width = 1050;
    private int n = 10; // Nombre d'étages ascenseur

    private JLabel label;
    private JLabel[] etages;
    private JLabel[] invisible;
    private JButton[] buttons;
    private JButton[] appelsBut;
    private JButton buttonAU;
    private Color c1= new Color(255,65,0);
    private Color c2= new Color(58,213,254);
    private Color c3= new Color(233,162,38);
    private Border b0 = new LineBorder(Color.LIGHT_GRAY, 0);
    private Border b1 = new LineBorder(c1, 3);
    private Border b2 = new LineBorder(c2, 3);
    private Border b3 = new LineBorder(c3, 3);
    private boolean boolAU = false; //Arrêt d'urgence
    private JSlider slider = new JSlider(JSlider.VERTICAL, 0, n, 0);
    private int tempsMontee = 500; //Temps en seconde entre chaque étage
    private int indice=0;




    public ElevatorWindow() {
        super();

        build();
    }

    //Création de l'IGT
    private void build() {
        setTitle("Ascenseur");
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(buildContentPane());
    }


    //CREATION DES PANELS ------------------------------------------------------
    private JPanel buildContentPane() {

        //Panel fenêtre entière
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setLayout(new BorderLayout());

        //Partie appel utilisateurs exterieurs
        JPanel panelG = new JPanel();
        panelG.setPreferredSize(new Dimension(170,height));
        panelG.setBackground(new Color(163,149,144));
        panel.add(panelG, BorderLayout.WEST);

        //Panel partie de droite
        JPanel panelD = new JPanel();
        panelD.setPreferredSize(new Dimension(410,height));
        panel.add(panelD, BorderLayout.EAST);
        panelD.setBackground(new Color(163,149,144));

        //Partie ascenseur centrale
        JPanel panelC = new JPanel();
        panelC.setPreferredSize(new Dimension(height,height));
        panelC.setBackground(Color.LIGHT_GRAY);
        panel.add(panelC, BorderLayout.CENTER);

        //Panel écran
        JPanel panelE = new JPanel();
        panelE.setPreferredSize(new Dimension(410, 130));
        panelE.setBackground(Color.BLACK);
        panelD.add(panelE, BorderLayout.NORTH);

        //Panel Boutons
        JPanel panelB = new JPanel();
        panelB.setBackground(new Color(163,149,144));
        panelB.setPreferredSize(new Dimension(410, height));
        panelD.add(panelB, BorderLayout.CENTER);


        //Texte à l'écran
        label = new JLabel("0");
        label.setForeground(c2);
        label.setFont(new Font("Verdana", Font.PLAIN, 40));
        panelE.add(label);


        //SLIDER ---------------------------------------------------------------
        //JSlider slider = new JSlider(JSlider.VERTICAL, 0, n, 0);
        slider.setBackground(Color.LIGHT_GRAY);
        slider.setPreferredSize(new Dimension(width/2,height-65));
        slider.setPaintTicks(false);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        //slider.setEnabled(false);
        panelC.add(slider, BorderLayout.CENTER);


        //Numérotation des niveaux sur le slider
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i=0;i<=n;i++){
            labels.put(i, new JLabel(String.valueOf(i)));
            labels.get(i).setFont(new Font("Verdana", Font.PLAIN, 32));
        }
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);


        //BOUTONS --------------------------------------------------------------

        //Boutons des appels
        etages = new JLabel[n+1];
        invisible = new JLabel [n];
        buttons = new JButton[n+1];
        appelsBut = new JButton[(n+1)*2];
        for (int i=0; i<=n; i++){

            //Numéros d'étages
            if(i!=0) {
                invisible[i-1]=new JLabel(String.valueOf(1));
                invisible[i-1].setForeground(new Color(163,149,144));
                invisible[i-1].setFont(new Font("Verdana", Font.PLAIN, 30));
                panelG.add(invisible[i-1]);
            }
            etages[i] = new JLabel(String.valueOf(n-i));
            etages[i].setForeground(new Color(50,50,50));
            etages[i].setFont(new Font("Verdana", Font.PLAIN, 33));
            panelG.add(etages[i]);

            //Boutons d'appels montée
            if(i!=0)appelsBut[i] = new JButton("↑");
            else {
                appelsBut[i]= new JButton ("");
                appelsBut[i].setEnabled(false);
            }
            appelsBut[i].setBackground(Color.LIGHT_GRAY);
            appelsBut[i].addActionListener(new appelNiveau());
            appelsBut[i].setPreferredSize(new Dimension(50,50));
            panelG.add(appelsBut[i]);

            //Boutons d'appels déscente
            if(i!=n) appelsBut[i+n+1] = new JButton("↓");
            else {
                appelsBut[i+n+1]= new JButton ("");
                appelsBut[i+n+1].setEnabled(false);
            }
            appelsBut[i+n+1].setBackground(Color.LIGHT_GRAY);
            appelsBut[i+n+1].addActionListener(new appelNiveau());
            appelsBut[i+n+1].setPreferredSize(new Dimension(50,50));
            panelG.add(appelsBut[i+n+1]);

        }


        //Boutons des niveaux
        buttons = new JButton[n+1];
        for (int i=0; i<=n; i++){
            buttons[i] = new JButton(String.valueOf(n-i));
            buttons[i].addActionListener(new requeteNiveau());
            buttons[i].setPreferredSize(new Dimension(100,100));
            buttons[i].setBackground(Color.LIGHT_GRAY);
            buttons[i].setFont(new Font("Verdana", Font.PLAIN, 30));
            panelB.add(buttons[i]);
        }

        //Bouton Arrêt d'urgence
        buttonAU = new JButton("⚠");
        buttonAU.setFont(new Font("Verdana", Font.PLAIN, 40));
        buttonAU.addActionListener(new arretUrgence());
        buttonAU.setPreferredSize(new Dimension(100,100));
        buttonAU.setBackground(Color.LIGHT_GRAY);
        panelB.add(buttonAU);




        return panel;
    }



    //RECEPTION ACTIONS --------------------------------------------------------

    //Reçoit les appels
    public class appelNiveau implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (boolAU == false) { //Blocage des actions si état d'arrêt d'urgence
                Object source = e.getSource();
                int i=2*n+1;
                while (source != appelsBut[i]) i--;
                if((i<=n && appelsBut[i+n+1].getBorder() != b3) ||
                        (i>n && appelsBut[i-n-1].getBorder() != b3))
                    if(appelsBut[i].getBorder() != b3) appelsBut[i].setBorder(b3);
            }
        }
    }


    //Reçoit les clics d'arret d'urgence
    public class arretUrgence implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(boolAU == true) {
                label.setText("0");
                label.setForeground(c2);
                buttonAU.setBorder(b0);
                boolAU=false;
                slider.setValue(0);
            }
            else {
                label.setText("Arrêt d'urgence");
                label.setForeground(c1);
                for (int i=0; i<=n; i++) { //Désactivation des boutons
                    buttons[i].setBorder(b0);
                    appelsBut[i].setBorder(b0);
                    appelsBut[i+n+1].setBorder(b0);
                }
                buttonAU.setBorder(b1);
                boolAU = true;
            }
        }
    }

    //Reçoit les requêtes de niveaux
    public class requeteNiveau implements ActionListener {
        private int i;
        public void actionPerformed(ActionEvent e) {
            if (boolAU == false) { //Blocage des actions si état d'arrêt d'urgence
                Object source = e.getSource();
                i=n;
                while (source != buttons[i]) i--;
                if(buttons[i].getBorder() != b2){
                    buttons[i].setBorder(b2);

                    Timer minuteur = new Timer();
                    TimerTask monter = new TimerTask(){
                        public void run(){
                            slider.setValue(slider.getValue()+1);
                            label.setForeground(c2);
                            label.setText(String.valueOf(slider.getValue()));
                            if(slider.getValue()==(n-i)){
                                minuteur.cancel();
                                minuteur.purge();
                                buttons[i].setBorder(b0);
                            }
                        }
                    };
                    TimerTask descendre = new TimerTask(){
                        public void run(){
                            slider.setValue(slider.getValue()-1);
                            label.setForeground(c2);
                            label.setText(String.valueOf(slider.getValue()));
                            if(slider.getValue()==(n-i)){
                                minuteur.cancel();
                                minuteur.purge();
                                buttons[i].setBorder(b0);
                            }
                        }
                    };

                    slider.setValue(Main.elevator.getPosition());
                    System.out.println(Main.elevator.getPosition());
                    if(slider.getValue()<(n-i)) {
                        //minuteur.schedule(monter, tempsMontee, tempsMontee);
                        Main.elevator.moveUp(i,Main.controller);
                        System.out.println(i);
                    }
                    else if (slider.getValue()>(n-i)){
                        //minuteur.schedule(descendre, tempsMontee, tempsMontee);
                        Main.elevator.moveDown(i,Main.controller);
                    }

                }
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {}
}