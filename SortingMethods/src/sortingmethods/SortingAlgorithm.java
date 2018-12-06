package sortingmethods;

public abstract class SortingAlgorithm {
    protected int tab[];
    protected String howMany;
    protected long sortingDuration;
    abstract void initializeTab(Tab tabToSort);
    public abstract void sort(String ile);
    public abstract String getSortingDuration();
}
