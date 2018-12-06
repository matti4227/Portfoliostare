package sortingmethods;

import java.util.Arrays;

public class JavaSort extends SortingAlgorithm {

    private static JavaSort instance = new JavaSort();

    private JavaSort() {}

    public static JavaSort getInstance() { return instance; }

    public JavaSort(Tab tabToSort) {
        initializeTab(tabToSort);
    }

    @Override
    void initializeTab(Tab tabToSort) {
        int[] temp = tabToSort.getTab();
        tab = new int[temp.length];
        for(int i = 0; i < tab.length; i++)
            tab[i] = temp[i];
    }

    @Override
    public void sort(String ile) {

        howMany = ile;
        long startTime = System.nanoTime();
        Arrays.sort(tab);
        long endTime = System.nanoTime();
        if(ile == "one")
            sortingDuration = (endTime - startTime);
        if(ile == "ten")
            sortingDuration += (endTime - startTime);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String getSortingDuration() {
        StringBuilder sb = new StringBuilder();
        sb.append(howMany == "one" ? sortingDuration : sortingDuration/10);
        sb.append(" ns");
        sortingDuration = 0;
        return String.valueOf(sb);
    }
}
