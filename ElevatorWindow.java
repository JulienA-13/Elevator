import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;

public class ElevatorWindow extends JFrame implements ActionListener {

    //création des variables d'intérêt
    private int height = 700;
    private int width = 1240;
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



    public ElevatorWindow() {
        super();

        build();
    }

    //Création de la fenêtre
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
        panelG.setPreferredSize(new Dimension((width-height)/3-20,height));
        panelG.setBackground(new Color(163,149,144));
        panel.add(panelG, BorderLayout.WEST);

        //Panel partie de droite
        JPanel panelD = new JPanel();
        panelD.setPreferredSize(new Dimension(width/2,height));
        panel.add(panelD, BorderLayout.EAST);
        panelD.setBackground(new Color(163,149,144));

        //Partie ascenseur centrale
        JPanel panelC = new JPanel();
        panelC.setPreferredSize(new Dimension(height,height));
        panelC.setBackground(Color.LIGHT_GRAY);
        panel.add(panelC, BorderLayout.CENTER);

        //Panel écran
        JPanel panelE = new JPanel();
        panelE.setPreferredSize(new Dimension(2*(width-height)/3, 130));
        panelE.setBackground(Color.BLACK);
        panelD.add(panelE, BorderLayout.NORTH);

        //Panel Boutons
        JPanel panelB = new JPanel();
        panelB.setBackground(new Color(163,149,144));
        panelB.setPreferredSize(new Dimension(2*(width-height)/3, height));
        panelD.add(panelB, BorderLayout.CENTER);


        //Slider
        JSlider slider = new JSlider(JSlider.VERTICAL, 0, n, 0);
        slider.setBackground(Color.LIGHT_GRAY);
        slider.setPreferredSize(new Dimension(width/2,height-65));
        slider.setPaintTicks(false);

        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        //slider.setEnabled(false);

        panelC.add(slider, BorderLayout.CENTER);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();

        for (int i=0;i<11;i++){
            labels.put(i, new JLabel(String.valueOf(i)));
            labels.get(i).setFont(new Font("Verdana", Font.PLAIN, 32));
        }

        slider.setLabelTable(labels);

        slider.setPaintLabels(true);


        //Texte à l'écran
        label = new JLabel("0");
        label.setForeground(c2);
        label.setFont(new Font("Verdana", Font.PLAIN, 40));
        panelE.add(label);

        //BOUTONS --------------------------------------------------------------

        //Boutons des appels
        etages = new JLabel[n+1];
        invisible = new JLabel [n];
        buttons = new JButton[n+1];
        appelsBut = new JButton[(n+1)*2];
        for (int i=0; i<=n; i++){
            /*buttons[i] = new JButton(String.valueOf(n-i));
            buttons[i].addActionListener(new appelNiveau());
            buttons[i].setPreferredSize(new Dimension(50,50));
            buttons[i].setBackground(Color.LIGHT_GRAY);
            panelG.add(buttons[i]);*/

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


            appelsBut[i] = new JButton("↑");
            appelsBut[i].addActionListener(new appelNiveau());
            appelsBut[i].setPreferredSize(new Dimension(50,50));
            appelsBut[i].setBackground(Color.LIGHT_GRAY);
            panelG.add(appelsBut[i]);


            appelsBut[i+n+1] = new JButton("↓");
            appelsBut[i+n+1].addActionListener(new appelNiveau());
            appelsBut[i+n+1].setPreferredSize(new Dimension(50,50));
            appelsBut[i+n+1].setBackground(Color.LIGHT_GRAY);
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

        buttons[0].addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("hello");
                Monter monter = new Monter(label,slider,panel,buttons, buttons[0]);
                monter.start();

            }
        }));

        buttons[5].addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int ind = slider.getValue();
                int pos = Integer.parseInt(buttons[5].getText());
                if(ind < pos ) {
                    Monter monter = new Monter(label, slider, panel, buttons, buttons[5]);
                    monter.start();
                } else {
                    Descendre desc = new Descendre(label,slider,panel,buttons, buttons[5]);
                    desc.start();
                }

            }
        }));

        buttons[10].addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("hello");
                Descendre desc = new Descendre(label,slider,panel,buttons, buttons[10]);
                desc.start();

            }
        }));


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
      public void actionPerformed(ActionEvent e) {
        if (boolAU == false) { //Blocage des actions si état d'arrêt d'urgence
          Object source = e.getSource();
          int i=n;
          while (source != buttons[i]) i--;
          label.setText(String.valueOf(n-i));
          label.setForeground(c2);
          if(buttons[i].getBorder() != b2) buttons[i].setBorder(b2);
        }



      }
  }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}

class Monter extends Thread {

    private final JPanel panel5;
    private final JSlider slider;
    private final JLabel area;
    private final JButton[]  buttons;
    private final JButton button;

    public Monter(JLabel area, JSlider slider, JPanel panel5, JButton [] buttons, JButton button) {
        this.area= area;
        this.slider = slider;
        this.panel5 = panel5;
        this.buttons = buttons;
        this.button = button;

    }
    public void run() {
        int pos = Integer.parseInt(button.getText());
        int ind = slider.getValue();
        for (int i = ind; i<pos;i++) {

            area.setText("");
            String entry = area.getText() + buttons[10-slider.getValue()].getText();
            area.setText(entry);
            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            slider.setValue((slider.getValue() + 1));
            area.setText("");
            String entry2 = area.getText() + buttons[10-slider.getValue()].getText();
            area.setText(entry2);
            panel5.repaint();
            panel5.revalidate();
            System.out.println(slider.getValue());
        }

    }

}

class Descendre extends Thread {

    private final JPanel panel5;
    private final JSlider slider;
    private final JLabel area;
    private final JButton[]  buttons;
    private final JButton button;

    public Descendre(JLabel area, JSlider slider, JPanel panel5, JButton [] buttons, JButton button) {
        this.area= area;
        this.slider = slider;
        this.panel5 = panel5;
        this.buttons = buttons;
        this.button = button;

    }
    public void run() {
        int pos = Integer.parseInt(button.getText());
        int ind = slider.getValue();
        for (int i= pos; i<ind;i++) {

            area.setText("");
            String entry = area.getText() + buttons[10-slider.getValue()].getText();
            area.setText(entry);
            try {
                this.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            slider.setValue((slider.getValue() - 1)); //faire une fct qui fait ça pour synchronize
            area.setText("");
            String entry2 = area.getText() + buttons[10-slider.getValue()].getText();
            area.setText(entry2);
            panel5.repaint();
            panel5.revalidate();
            System.out.println(slider.getValue());

        }

    }
}