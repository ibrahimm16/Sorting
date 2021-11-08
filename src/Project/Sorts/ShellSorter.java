package Project.Sorts;

import Project.Display;

import java.awt.*;

public class ShellSorter extends SortingAlgorithm {

    public ShellSorter(Display display, int size, boolean timed) {
        super(display, size, "Shell sort ", timed, new Color(97, 26, 36));
    }

    @Override
    void timedAlgorithm() {
        for (int interval = timedList.size() / 2; interval > 0; interval /= 2) {
            for (int i = interval; i < timedList.size(); i++) {
                int temp = timedList.get(i);
                int j;
                for (j = i; j >= interval && timedList.get(j - interval) > temp; j -= interval)
                    timedList.set(j, timedList.get(j - interval));
                timedList.set(j, temp);
            }
        }
    }

    @Override
    void renderAlgorithm() {
        for (int interval = renderList.size() / 2; interval > 0; interval /= 2) {
            for (int i = interval; i < renderList.size(); i++) {
                int temp = renderList.get(i);
                int j;
                for (j = i; j >= interval && renderList.get(j - interval) > temp; j -= interval)
                    renderList.set(j, renderList.get(j - interval));
                renderList.set(j, temp);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}