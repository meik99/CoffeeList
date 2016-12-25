package rynkbit.tk.coffeelist.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by michael on 11/19/16.
 */

public class Sort {
    public static <T extends Comparable> List<T> bubble(List<T> list) {
        return bubble(list, false);
    }


    public static <T extends Comparable> List<T> bubble(List<T> list, boolean reverse){
        if(list == null || list.size() <= 1)
            return list;

        boolean sorted;

        do{
            sorted = true;
            for(int i = 1; i < list.size(); i++){
                if((reverse == false && list.get(i).compareTo(list.get(i-1)) < 0) ||
                        (reverse == true && list.get(i).compareTo(list.get(i-1)) > 0)){
                    T tmp = list.get(i-1);
                    list.set(i-1, list.get(i));
                    list.set(i, tmp);
                    sorted = false;
                }
            }
        }while (sorted == false);

        return list;
    }


}
