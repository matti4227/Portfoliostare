package sortingmethods;

public class BubbleSort extends SortingAlgorithm {

    private static BubbleSort instance = new BubbleSort();

    private BubbleSort() {}

    public static BubbleSort getInstance() { return instance; }

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
        int n = tab.length;
        int temp;

        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(tab[j-1] > tab[j]){
                    temp = tab[j-1];
                    tab[j-1] = tab[j];
                    tab[j] = temp;
                }
            }
        }
        long endTime = System.nanoTime();
        if(howMany == "one")
            sortingDuration = (endTime - startTime);
        if(howMany == "ten")
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
