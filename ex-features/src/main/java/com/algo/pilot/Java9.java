package com.algo.pilot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java9 {

    public void collectionsUtil(){
        List<String> list = List.of("one", "two", "three");
        Set<String> set = Set.of("one", "two", "three");
        Map<String, String> map = Map.of("foo", "one", "bar", "two");

        System.out.println(list);
        System.out.println(set);
        System.out.println(map);
    }

    public void streamUtil(){
        Stream<String> stream=Stream.iterate("", s -> s+"s")
                .takeWhile(s -> s.length() <10);
        List<String> strList=stream.toList();
        System.out.println(strList);

        Stream.generate(new Random()::nextInt)
                .limit(10).forEach(System.out::println);
    }

    public void optionalUtil(){
        Optional<String> user=Optional.of("test_user");
        user.ifPresentOrElse(System.out::println, ()->System.out.println("not set"));
        user=Optional.empty();
        user.ifPresentOrElse(System.out::println, ()->System.out.println("not set"));
    }


}
