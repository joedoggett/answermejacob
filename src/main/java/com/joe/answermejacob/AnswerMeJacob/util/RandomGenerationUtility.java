package com.joe.answermejacob.AnswerMeJacob.util;

import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RandomGenerationUtility
{

    public String randomCharacterGeneration(int numberOfCharacters)
    {
        final Integer maximum = 122;
        final Integer minimum = 48;
        final List<Integer> notValid = Arrays.asList(58,59,60,61,62,63,64,91,92,93,94,95,96);
        return IntStream.iterate(1, n -> n + 1)
                .map(n -> java.util.concurrent.ThreadLocalRandom.current().nextInt(minimum, maximum + 1))
                .filter(i -> !notValid.contains(i))
                .mapToObj(i -> Character.toString( (char) i ))
                .limit(numberOfCharacters)
                .collect(Collectors.joining());
    }


}
