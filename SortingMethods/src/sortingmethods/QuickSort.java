package sortingmethods;

public class QuickSort extends SortingAlgorithm {

    private static QuickSort instance = new QuickSort();

    private QuickSort() {}

    public static QuickSort getInstance() { return instance; }

    private int low;
    private int high;

    @Override
    void initializeTab(Tab tabToSort) {
        int[] temp = tabToSort.getTab();
        tab = new int[temp.length];
        for(int i = 0; i < tab.length; i++)
            tab[i] = temp[i];
        low = 0;
        high = tabToSort.getTab().length - 1;
    }

    @Override
    public void sort(String ile) {

        howMany = ile;
        long startTime = System.nanoTime();
        quickSort(tab, low, high);
        long endTime = System.nanoTime();
        if(ile == "one")
            sortingDuration = (endTime - startTime);
        if(ile == "ten")
            sortingDuration += (endTime - startTime);
    }

    public void quickSort(int tabToSort[], int low, int high) {

        if (low < high)
        {
            int pi = partition(tabToSort, low, high);
            quickSort(tabToSort, low, pi-1);
            quickSort(tabToSort, pi+1, high);
        }
    }

    int partition(int arr[], int low, int high) {

        int pivot = arr[high];
        int i = (low-1);

        for (int j=low; j<high; j++)
        {
            if (arr[j] <= pivot)
            {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
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
