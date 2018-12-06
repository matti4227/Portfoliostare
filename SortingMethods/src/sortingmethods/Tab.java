package sortingmethods;

import java.util.Random;

public class Tab {

    private int tab[];

    void initializeTab(int size) { tab = new int[size]; }

    void fillTheTab(int size) {
        Random random = new Random();
        for(int i = 0; i < size; i++)
            tab[i] = random.nextInt(size);
    }

    public int[] getTab() {
        return tab;
    }
}
