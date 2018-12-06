package sortingmethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.ItemEvent.SELECTED;

public class Form extends JFrame implements Runnable {
    private JPanel jPanel;
    private JButton stworzTab2;
    private JTextField tablica2;
    private JButton stworzTab1;
    private JTextField tablica1;
    private JLabel jLabel1;
    private JButton infoOProgramieButton;
    private JLabel jLabel2;
    private JButton sortuj1;
    private JButton sortuj2;
    private JLabel bubble1;
    private JLabel quick1;
    private JLabel java1;
    private JCheckBox bubbleCheck;
    private JCheckBox quickCheck;
    private JCheckBox javaCheck;
    private JButton sortuj1_10;
    private JLabel bubble1_10;
    private JLabel quick1_10;
    private JLabel java1_10;
    private JButton wyczyśćWynikiButton;
    private JLabel info;
    private JCheckBox pokazCheck;
    private JLabel infoTablica1;
    private JLabel infoTablica2;
    private JLabel bubble2;
    private JLabel quick2;
    private JLabel java2;
    private JButton sortuj2_10;
    private JLabel bubble2_10;
    private JLabel quick2_10;
    private JLabel java2_10;
    private JButton wyczyscTab1;
    private JButton wyczyscTab2;
    private JLabel t1;
    private JLabel t2;

    Form(){
        add(jPanel);
        setSize(900, 370);
        setTitle("Program sortujący tablice");
        jLabel1.setText("<HTML>Poniżej wstaw wartości dla wielkości tablic,<BR>które mają zostać stworzone. (Nie przesadzaj.)<BR>Wartości zostaną do niej losowo wygenerowane.</HTML>");
        jLabel2.setText("<HTML>Wyniki sortowań dla:</HTML>");
        info.setText("<HTML>Poniższe na własną <BR>odpowiedzialność.<HTML>");
        t1.setFont(new Font("Courier", Font.BOLD, 14));
        t2.setFont(new Font("Courier", Font.BOLD, 14));

        creator = new Creator();
        bubbleSort = BubbleSort.getInstance();
        quickSort = QuickSort.getInstance();
        javaSort = JavaSort.getInstance();

        sortuj1.addActionListener(e -> {
            thread1 = new Thread(this::run);
            thread1.start();
            sort1 = true;
        });
        sortuj2.addActionListener(e -> {
            thread2 = new Thread(this::run);
            thread2.start();
            sort2 = true;
        });

        sortuj1_10.addActionListener(e -> {
            thread101 = new Thread(this::run);
            thread101.start();
            sort101 = true;
        });
        sortuj2_10.addActionListener(e -> {
            thread102 = new Thread(this::run);
            thread102.start();
            sort102 = true;
        });

        stworzTab1.addActionListener(e -> createTable(tablica1, infoTablica1, "first"));
        stworzTab2.addActionListener(e -> createTable(tablica2, infoTablica2, "second"));

        bubbleCheck.addItemListener(e -> {
            if(e.getStateChange() == SELECTED) {
                creator.addAlgorithm(bubbleSort);
                bubbleFlag = true;
            }
            else {
                creator.removeAlgorithm(bubbleSort);
                bubbleFlag = false;
            }
        });
        quickCheck.addItemListener(e -> {
            if(e.getStateChange() == SELECTED) {
                creator.addAlgorithm(quickSort);
                quickFlag = true;
            }
            else {
                creator.removeAlgorithm(quickSort);
                quickFlag = false;
            }
        });
        javaCheck.addItemListener(e -> {
            if(e.getStateChange() == SELECTED) {
                creator.addAlgorithm(javaSort);
                javaFlag = true;
            }
            else {
                creator.removeAlgorithm(javaSort);
                javaFlag = false;
            }
        });

        pokazCheck.addItemListener(e -> numbers = (e.getStateChange() == SELECTED));

        wyczyśćWynikiButton.addActionListener(e -> {
            bubble1.setText("");
            quick1.setText("");
            java1.setText("");
            bubble1_10.setText("");
            quick1_10.setText("");
            java1_10.setText("");
            bubble2.setText("");
            quick2.setText("");
            java2.setText("");
            bubble2_10.setText("");
            quick2_10.setText("");
            java2_10.setText("");
        });
        wyczyscTab1.addActionListener(e -> {
            if(creator.tab1 != null){
                creator.nullTheTab("first");
                table1 = false;
                infoTablica1.setText("");
            }
        });
        wyczyscTab2.addActionListener(e -> {
            if(creator.tab2 != null){
                creator.nullTheTab("second");
                table2 = false;
                infoTablica2.setText("");
            }
        });

        infoOProgramieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "<HTML>Program sortuje tablicy i podaje czas sortowań w nanosekundach.<BR><BR>Użyto wzorców projektowych:<BR>-Observer<BR>-Singleton<BR>-Delegate</HTML>");
            }
        });//////////////////////////////////////////////TODO: wstawić informacje o programie
    }

    private void createTable(JTextField tablica, JLabel infotablica, String which) {
        try {
            String text = tablica.getText();
            if(!text.equals("")){
                if(checkNumber(text)){
                    int tabSize = Integer.parseInt(text);
                    if(tabSize <= 200000000){
                        if(tabSize != 0){
                            creator.initializeTab(tabSize, which);
                            creator.fillTheTab(tabSize, which);
                            infotablica.setText("<HTML>Tablica <BR><HTML>" + tabSize + "<HTML> - elementowa<HTML>");
                            if(which == "first")
                                table1 = true;
                            if(which == "second")
                                table2 = true;
                            try {
                                if(numbers)
                                    JOptionPane.showMessageDialog(null, creator.showTab("first"));
                            } catch (OutOfMemoryError oome) {
                                JOptionPane.showMessageDialog(null, "Przesadziłeś. Nie pokażę Ci tego.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Podaj coś, co nie jest zerem");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Podana liczba wykracza poza przyjęty, górny zakres (do 200 000 000)");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "To nie jest liczba całkowita, bądź wykracza poza dolny zakres");
                }
            } else {
                JOptionPane.showMessageDialog(null, "A może coś wpisz");
            }
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Podana liczba wykracza poza przyjęty, górny zakres (0 - 200 000 000)");
        }
    }
    private void sortTable(JLabel bubble, JLabel quick, JLabel java, boolean table, String which, String howMany) throws InterruptedException {
        try {
            if(table)
                creator.notifyObservers(howMany, which);
        } catch (StackOverflowError soe) {
            JOptionPane.showMessageDialog(null, "Stack Overflow. Spróbuj ponownie.");
        }
        if(bubbleFlag && table) {
            bubble.setText(bubbleSort.getSortingDuration());
            Thread.sleep(300);
        }
        else {
            bubble.setText("");
        }
        if(quickFlag && table) {
            quick.setText(quickSort.getSortingDuration());
            Thread.sleep(300);
        }
        else {
            quick.setText("");
        }
        if(javaFlag && table) {
            java.setText(javaSort.getSortingDuration());
            Thread.sleep(300);
        }
        else
            java.setText("");

        try {
            if(numbers)
                JOptionPane.showMessageDialog(null, creator.showTab(which));
        } catch(OutOfMemoryError oome) {
            JOptionPane.showMessageDialog(null, "Przesadziłeś. Nie pokażę Ci tego.");
        }

    }
    private boolean checkNumber(String input){
        boolean flag = true;
        int i = 0;

        while(i < input.length() && flag == true){
            flag = false;
            for(int j = 48; j <= 57; j++) {
                if ((int) input.charAt(i) == j) {
                    flag = true;
                }
            }
            if(flag == false)
                return false;
            i++;
        }
        return true;
    }

    private Creator creator;
    private SortingAlgorithm bubbleSort;
    private SortingAlgorithm quickSort;
    private SortingAlgorithm javaSort;
    private boolean bubbleFlag;
    private boolean quickFlag;
    private boolean javaFlag;
    private boolean table1 = false;
    private boolean table2 = false;
    private boolean numbers;
    private boolean sort1 = false;
    private boolean sort2 = false;
    private boolean sort101 = false;
    private boolean sort102 = false;
    Thread thread1;
    Thread thread2;
    Thread thread101;
    Thread thread102;

    @Override
    public void run() {
        synchronized (this) {
            if(sort1 == true){
                try {
                    Thread.sleep(100);
                    sortTable(bubble1, quick1, java1, table1, "first", "one");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sort1 = false;
            }
            if(sort101 == true){
                try {
                    Thread.sleep(100);
                    sortTable(bubble1_10, quick1_10, java1_10, table1, "first", "ten");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sort101 = false;
            }
            if(sort2 == true){
                try {
                    Thread.sleep(100);
                    sortTable(bubble2, quick2, java2, table2, "second", "one");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sort2 = false;
            }
            if(sort102 == true){
                try {
                    Thread.sleep(100);
                    sortTable(bubble2_10, quick2_10, java2_10, table2, "second", "ten");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sort102 = false;
            }
        }
    }
}
