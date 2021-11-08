package Project.Sorts;

import Project.Constants;
import Project.Display;
import Project.ListGenerator;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public abstract class SortingAlgorithm implements Runnable {

    Display display;
    String type, sortType;
    LinkedList<Integer> timedList, renderList;
    LinkedHashMap<String, Color> logs;
    String log;
    int logId;
    Color displayColor;

    public SortingAlgorithm(Display display, int size, String sortType, boolean timed, Color displayColor) {
        this.display = display;
        this.sortType = sortType;
        logs = display.getLogs();
        this.displayColor = displayColor;
        if (!display.getColors().contains(displayColor))
            display.addColor(displayColor);
        if (timed) {
            type = Constants.TIMED;
            timedList = ListGenerator.generateList(size);
            initTimed();
        } else {
            type = Constants.RENDERED;
            renderList = calculateAveragedList(ListGenerator.generateList(size));
            initRendered();
        }
    }

    void initTimed() {
        log = sortType + "timer status: unsorted";
        display.addLog(sortType + "timer list size: " + timedList.size() + " elements", displayColor);
        display.addLog(log, displayColor);
        logId = display.getLogs().size()-1;
    }

    void initRendered() {
        boolean added = false;
        while (!added) {
            try {
                display.addRenderList(renderList, displayColor);
                added = true;
            } catch (ConcurrentModificationException e) {
            }
        }
        log = sortType + "visualizer status: unsorted";
        display.addLog(log, displayColor);
        logId = display.getLogs().size()-1;
    }

    LinkedList<Integer> calculateAveragedList(LinkedList<Integer> list) {
        LinkedList<Integer> returnList = new LinkedList<>();
        double columnSize = (list.size() / 400.0);
        int columns = (int) (list.size() / columnSize);
        for (int columnsChecked = 0; columnsChecked < columns; columnsChecked++) {
            double average = 0;
            for (int i = (int) (columnsChecked * columnSize); i < ((int) ((columnsChecked * columnSize)) + columnSize); i++) {
                average += list.get(i);
            }
            average /= columnSize;
            returnList.add((int) average);
        }
        return returnList;
    }

    @Override
    public void run() {
        switch (type) {
            case Constants.TIMED:
                timedSort();
                break;
            case Constants.RENDERED:
                renderedSort();
                break;
        }
    }

    public void timedSort() {
        String logToReplace = log;
        log = sortType + "timer status: in progress";
        logs.put(log, logs.remove(logToReplace));

        String timeLog = sortType + "timer status: ";
        double begin = System.nanoTime();

        timedAlgorithm();

        double nanosecondsElapsed = System.nanoTime() - begin;
        double seconds = (nanosecondsElapsed / (Math.pow(10.0, 9.0))) - (3.0 * (Math.pow(10.0, -7.0)));
        timeLog += "completed (" + seconds + " seconds)";
        logs.put(timeLog, logs.remove(log));
    }

    public void renderedSort() {
        String oldLog = log;
        log = sortType + "visualization status: in progress";
        logs.put(log, logs.remove(oldLog));

        renderAlgorithm();

        oldLog = log;
        log = sortType + "visualizer status: completed";
        logs.put(log, logs.remove(oldLog));

    }

    void timedAlgorithm() {

    }

    void renderAlgorithm() {

    }

    public LinkedList<Integer> getRenderList() {
        return renderList;
    }

    public LinkedList<Integer> getTimedList() {
        return timedList;
    }
}
