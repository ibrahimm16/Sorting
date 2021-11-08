package Project.Sorts;

import Project.Constants;
import Project.Display;

import java.awt.*;

public class InsertionSorter extends SortingAlgorithm {

    public InsertionSorter(Display display, int size, boolean timed) {
        super(display, size, "Insertion sort ", timed, new Color(122, 47, 214));
    }

    @Override
    void timedAlgorithm() {
        for (int i = 1; i < timedList.size(); i++) {
            Integer key = timedList.get(i);
            int j = i - 1;
            while (j >= 0 && timedList.get(j) > key) {
                timedList.set(j + 1, timedList.get(j));
                j--;
            }
            timedList.set(j + 1, key);
        }
    }

    @Override
    void renderAlgorithm() {
        for (int i = 1; i < renderList.size(); i++) {
            Integer key = renderList.get(i);
            int j = i - 1;
            while (j >= 0 && renderList.get(j) > key) {
                renderList.set(j + 1, renderList.get(j));
                j--;
            }
            renderList.set(j + 1, key);
            try {
                Thread.sleep((long) Constants.TIME_PER_TICK);
            } catch (InterruptedException e) {
            }
        }
    }
}
