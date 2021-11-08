package Project;

import java.util.LinkedList;

public class ListGenerator {

    public static LinkedList<Integer> generateList(int size) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < size; i++)
            list.add((int)((Math.random() * 351.0) + 50));
        return list;
    }
}
