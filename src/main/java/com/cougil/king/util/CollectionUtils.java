package com.cougil.king.util;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This class contains static methods to operate and work with collections
 */
public class CollectionUtils {

    /**
     * Returns a {@link SortedSet} that contains the keys included in the map ordered in
     * descending order with less than or equal a maximum number of elements
     * @param map The map
     * @param MAX_VALUES maximum number of elements to return
     * @param <K> Key type of the map
     * @param <V> Value type of the map
     * @return an ordered set for the specified parameters
     */
    public static <K,V> SortedSet<K> keySetReverseOrder(Map<K, V> map, @SuppressWarnings("SameParameterValue") final int MAX_VALUES) {
        SortedSet<K> sortedSet = new ConcurrentSkipListSet<K>(Collections.reverseOrder());
        List<K> list = CollectionUtils.reversedListKeySet(map.keySet());
        for (int i=0,j=0; i<MAX_VALUES && j<list.size();j++) {
            K key = list.get(i);
            if (sortedSet.add(key)) i++;
        }
        return sortedSet;
    }

    /**
     * Returns a synchronized {@link List} that contains the {@link java.util.Set} specified in descending order
     * @param keyset Original set
     * @param <K> type of the elements of the Set
     * @return list of ordered key sets
     */
    public static <K> List<K> reversedListKeySet(Set<K> keyset) {
        List<K> list= Collections.synchronizedList(new ArrayList<K>(keyset));
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}
