package Project.Sorts;

import Project.Display;

import java.awt.*;

public class SelectionSorter extends SortingAlgorithm {

    public SelectionSorter(Display display, int size, boolean timed) {
        super(display, size, "Selection sort ", timed, new Color(199, 147, 95));
    }

    @Override
    void timedAlgorithm() {
        int min, temp;
        for (int index = 0; index < timedList.size(); index++) {
            min = index;
            for (int scan = index + 1; scan < timedList.size(); scan++) {
                if (timedList.get(scan) < timedList.get(min)) {
                    min = scan;
                }
            }
            temp = timedList.get(min);
            timedList.set(min, timedList.get(index));
            timedList.set(index, temp);
        }
    }

    @Override
    void renderAlgorithm() {
        int min, temp;
        for (int index = 0; index < renderList.size(); index++) {
            min = index;
            for (int scan = index + 1; scan < renderList.size(); scan++) {
                if (renderList.get(scan) < renderList.get(min)) {
                    min = scan;
                }
            }
            temp = renderList.get(min);
            renderList.set(min, renderList.get(index));
            renderList.set(index, temp);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }
}
