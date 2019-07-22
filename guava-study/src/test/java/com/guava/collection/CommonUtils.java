package com.guava.collection;

import com.google.common.collect.*;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.*;

@Log4j2
public class CommonUtils {
    @Test
    public void listsTest() {
        List<String> strList = Lists.newArrayList();
        List<String> linkedStrlist = Lists.newLinkedList();
        Lists.reverse(strList);
        Lists.partition(linkedStrlist, 2);
    }

    @Test
    public void setsTest() {
        Set<String> strSet = Sets.newHashSet();
        Set<String> treeStrset = Sets.newTreeSet();
        strSet.stream();
        Sets.filter(strSet, s -> s.equals("1"));
        Sets.difference(strSet, treeStrset);
    }


    @Test
    public void mapsTest() {
        Maps.newHashMap();
        Maps.newLinkedHashMap();
        Maps.newTreeMap();
        Maps.newConcurrentMap();

//        Maps.filterEntries();

    }

    @Test
    public static void collection2Test() {
        List<String> list = Lists.newArrayList("hello", "world", "javab", "c", "someone");
        Collection<List<String>> collection = Collections2.orderedPermutations(list);
        System.out.println(collection.size());
        for (List<String> temp : collection) {
            System.out.println(temp);
        }

        System.out.println("==============使用比较器=================");
        Collection<List<String>> collection5 = Collections2.orderedPermutations(list, new SimpleCom());
        System.out.println(collection5.size());
        for (List<String> temp : collection5) {
            System.out.println(temp);
        }

        System.out.println();
        System.out.println();
        Collection<List<String>> collection2 = Collections2.permutations(list);
        for (List<String> temp : collection2) {
            System.out.println(temp);
        }

        Collection<String> collection3 = Collections2.filter(list, input -> input.length() >= 5 ? true : false);
        System.out.println();
        for (String temp : collection3) {
            System.out.println(temp);
        }

        Collection<String> collection4 = Collections2.transform(collection3, input -> input.toUpperCase());
        System.out.println();
        for (String temp : collection4) {
            System.out.println(temp);
        }
        Collections.sort(list);
    }


    @Test
    public void multiSetsTest() {
        Multiset<String> m=HashMultiset.create();
    }

}

class SimpleCom implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int a1 = o1.length();
        int a2 = o2.length();
        if (a1 != a2) {
            return a2 - a1;
        }
        if (a1 == a2) {
            char[] arr1 = o1.toCharArray();
            char[] arr2 = o2.toCharArray();
            for (int i = 0; i < a1; i++) {
                if (arr1[i] > arr2[i]) {
                    return -1;
                }
                if (arr1[i] < arr2[i]) {
                    return 1;
                }
            }
        }
        return 0;
    }

}
