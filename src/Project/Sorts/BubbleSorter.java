package Project.Sorts;

import Project.Display;

import java.awt.*;

public class BubbleSorter extends SortingAlgorithm {

    public BubbleSorter(Display display, int size, boolean timed) {
        super(display, size, "Bubble sort ", timed, new Color(87, 189, 128));
    }

    @Override
    void timedAlgorithm() {
        int j, temp;
        for (int i = 0; i < timedList.size(); i++) {
            for (j = i + 1; j < timedList.size(); j++) {
                if (timedList.get(j) < timedList.get(i)) {
                    temp = timedList.get(i);
                    timedList.set(i, timedList.get(j));
                    timedList.set(j, temp);
                }
            }
        }
    }

    @Override
    void renderAlgorithm() {
        int j, temp;
        for (int i = 0; i < renderList.size(); i++) {
            for (j = i + 1; j < renderList.size(); j++) {
                if (renderList.get(j) < renderList.get(i)) {
                    temp = renderList.get(i);
                    renderList.set(i, renderList.get(j));
                    renderList.set(j, temp);
                }
            }
            try {
                display.setRenderList(displayColor, renderList);
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }
}

