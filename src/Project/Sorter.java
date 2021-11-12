package Project;

import Project.Sorts.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Sorter {

    boolean running = true;
    double delta;
    long now, lastTime;

    Display display;
    SortingManager sortingManager;
    EventManager eventManager;

    public Sorter() {
        display = new Display();
        sortingManager = new SortingManager();
        eventManager = new EventManager();
    }

    public void start() {
        delta = 0.0;
        now = 0;
        lastTime = System.currentTimeMillis();
        while (running)
            checkToUpdate();
    }

    void checkToUpdate() {
        now = System.currentTimeMillis();
        delta += (now - lastTime) / Constants.TIME_PER_TICK;
        lastTime = now;
        if (delta >= 1) {
            delta--;
            update();
        }
    }

    void update() {
        display.update();
    }

    class SortingManager implements Runnable {

        SortingManager() {
        }

        @Override
        public void run() {
            startEvents();
        }

        void event(SortingAlgorithm timedSort, SortingAlgorithm renderedSort) {
            Thread sortingThread = new Thread(timedSort);
            sortingThread.start();
            try
            {
                sortingThread.join();
            } catch (Exception ignored) {
            }

            sortingThread = new Thread(renderedSort);
            sortingThread.start();
            try
            {
                sortingThread.join();
            } catch (Exception ignored) {
            }
        }

        void startEvents() {
            int size = Constants.LIST_SIZE;
            event(new InsertionSorter(display, size, true), new InsertionSorter(display, size, false));
            display.addLog("Insertion sort completed", Color.white);
            event(new SelectionSorter(display, size, true), new SelectionSorter(display, size, false));
            display.addLog("Selection sort completed", Color.white);
            event(new BubbleSorter(display, size, true), new BubbleSorter(display, size, false));
            display.addLog("Bubble sort completed", Color.white);
            event(new ShellSorter(display, size, true), new ShellSorter(display, size, false));
            display.addLog("All sorts completed", Color.white);
        }
    }

    class EventManager implements KeyListener {

        EventManager() {
            display.frame.addKeyListener(this);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                sortingManager.startEvents();
            if (e.getKeyCode() == KeyEvent.VK_0)
                System.exit(0);
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
