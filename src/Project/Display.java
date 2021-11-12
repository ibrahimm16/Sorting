package Project;

import Project.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Display {

    JFrame frame;
    Canvas canvas;
    LinkedHashMap<String, Color> logs;

    LinkedHashMap<Color, LinkedList<Integer>> renderLists;
    LinkedList<Color> colors = new LinkedList<>();



    Renderer renderer;

    public Display() {
        Dimension d = new Dimension(Constants.WIDTH, Constants.HEIGHT);
        frame = new JFrame();
        frame.setSize(d);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocation(10, 10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas();
        canvas.setSize(d);
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        logs = new LinkedHashMap<>();
        renderLists = new LinkedHashMap<>();
        renderer = new Renderer();
    }

    public void update() {
        renderer.render();
    }

    public void clear() {
        logs = new LinkedHashMap<>();
        renderLists = new LinkedHashMap<>();
    }

    public void addLog(String log, Color color) {
        logs.put(log, color);
    }

    public void replaceLog(String logToRemove, String newLog) {
        Color logColor = logs.get(logToRemove);
        logs.remove(logToRemove);
        logs.put(newLog, logColor);
    }

    public void addColor(Color c) {
        if (!colors.contains(c))
            colors.add(c);
    }

    public void setRenderList(Color key, LinkedList<Integer> updatedList) {
        renderLists.put(key, updatedList);
    }

    public LinkedHashMap<String, Color> getLogs() {
        return logs;
    }

    public LinkedList<Color> getColors() {
        return colors;
    }

    class Renderer {
        Graphics2D g;
        BufferStrategy bufferStrategy;

        void render() {
            bufferStrategy = canvas.getBufferStrategy();
            if (bufferStrategy == null) {
                canvas.createBufferStrategy(3);
                return;
            }
            g = (Graphics2D) bufferStrategy.getDrawGraphics();
            g.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);




            // Attempts to render the interface and lists
            // Catches a concurrent modification exception
            // attempts to re-render and returns when the recursion comes back to this call
            try {
                renderInterface();
                renderLists();
            } catch (ConcurrentModificationException e) {
                g.dispose();
                render();
                return;
            }



            // renders the interface

            bufferStrategy.show();
            g.dispose();
        }

        void renderLists() {
            for (int index = 0; index < renderLists.size(); index++) {
                g.setColor(colors.get(index));
                LinkedList<Integer> renderList = renderLists.get(colors.get(index));

                if (index == 0) {
                    for (int x = 10; x < renderList.size() + 10; x++)
                        g.drawLine(x, 10, x, 10 + renderList.get(x - 10));
                } else if (index == 1) {
                    for (int x = 430; x < renderList.size() + 430; x++)
                        g.drawLine(x, 10, x, 10 + renderList.get(x - 430));
                } else if (index == 2) {
                    for (int x = 10; x < renderList.size() + 10; x++)
                        g.drawLine(x, 850, x, 850 - renderList.get(x - 10));
                } else if (index == 3) {
                    for (int x = 430; x < renderList.size() + 430; x++)
                        g.drawLine(x, 850, x, 850 - renderList.get(x - 430));
                }
            }
        }

        void renderInterface() {
            // background/borders
            g.setColor(Color.black);
            g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
            g.setColor(Color.white);
            g.fillRect(0, 0, Constants.WIDTH, 2);
            g.fillRect(0, Constants.HEIGHT - 2, Constants.WIDTH, 2);
            g.fillRect(0, 0, 2, Constants.HEIGHT);
            g.fillRect(Constants.WIDTH - 2, 0, 2, Constants.HEIGHT);
            g.fillRect(840, 0, 2, Constants.HEIGHT);
            g.fillRect(0, 429, 840, 2);
            g.fillRect(419, 0, 2, Constants.HEIGHT);

            g.drawString("Press enter to begin timed and visualized sorts", 850, 825);
            g.drawString("Press 0 to exit", 850, 845);

            // render logs
            int y = 20;
            for (String log : logs.keySet()) {
                g.setColor(logs.get(log));
                g.drawString(log, 850, y);
                y += 20;
            }
        }
    }
}
