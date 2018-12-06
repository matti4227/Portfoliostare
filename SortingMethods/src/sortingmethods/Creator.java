package sortingmethods;

import java.util.ArrayList;
import java.util.List;

public class Creator {
    private List<SortingAlgorithm> observers = new ArrayList<>();
    Tab tab1;
    Tab tab2;

    public Creator() { tab1 = new Tab(); tab2 = new Tab(); }

    void initializeTab(int tabSize, String which) {
        if(which == "first") tab1.initializeTab(tabSize);
        if(which == "second") tab2.initializeTab(tabSize);
    }

    void fillTheTab(int tabSize, String which) {
        if(which == "first") tab1.fillTheTab(tabSize);
        if(which == "second") tab2.fillTheTab(tabSize);
    }

    void nullTheTab(String which) {
        if(which == "first") tab1 = new Tab();
        if(which == "second") tab2 = new Tab();
    }

    void addAlgorithm(SortingAlgorithm algorithm) {
        observers.add(algorithm);
    }

    void removeAlgorithm(SortingAlgorithm algorithm) {
        observers.remove(algorithm);
    }

    void notifyObservers(String howMany, String which) {

        if(howMany == "one") {
            if(which == "first")
                for(SortingAlgorithm ob: observers) {
                    ob.initializeTab(tab1);
                    ob.sort(howMany);
                }
            if(which == "second")
                for(SortingAlgorithm ob: observers) {
                    ob.initializeTab(tab2);
                    ob.sort(howMany);
                }
        }
        if(howMany == "ten") {
            if(which == "first")
                for(int i = 0; i < 10; i++)
                    for(SortingAlgorithm ob: observers) {
                        ob.initializeTab(tab1);
                        ob.sort(howMany);
                    }
            if(which == "second")
                for(int i = 0; i < 10; i++)
                    for(SortingAlgorithm ob: observers) {
                        ob.initializeTab(tab2);
                        ob.sort(howMany);
                    }
        }
    }

    String showTab(String which) {
        StringBuilder sb = new StringBuilder();
        int []tempTab = (which == "first" ? tab1.getTab() : tab2.getTab());
        for(int t : tempTab){
            sb.append(tempTab[t]);
            sb.append(" ");
        }

        return sb.toString();
    }
}
