package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {

    public final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Value("${server.port}")
    private int port;
    @GetMapping
    public int getPort(){
        return port;
    }

    @GetMapping("/sum-experiments")
    public void sum(){
        //option 1
        long startTime = System.currentTimeMillis();
        int sum1 = IntStream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .sum();
        long endTime = System.currentTimeMillis();

        logger.info("Experiment 1 - streams with sum. " +
                "Sum = " + sum1 + ". " +
                " Total calculator time = " + (endTime - startTime));

        //option 2
        startTime = System.currentTimeMillis();
        int sum2 = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        endTime = System.currentTimeMillis();
        logger.info("Experiment 2 - streams with reduce. " +
                "Sum = " + sum2 + ". " +
                " Total calculator time = " + (endTime - startTime));

        //option 3
        startTime = System.currentTimeMillis();
        int sum3 = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        endTime = System.currentTimeMillis();
        logger.info("Experiment 3 - parallel streams. " +
                "Sum = " + sum3 + ". " +
                " Total calculator time = " + (endTime - startTime));

        //option 4
        startTime = System.currentTimeMillis();
        int sum4 = 0;
        for (int i = 0; i <= 1_000_000; i++) {
            sum4 += i;
        }
        endTime = System.currentTimeMillis();
        logger.info("Experiment 4 - for cycle. " +
                "Sum = " + sum4 + ". " +
                " Total calculator time = " + (endTime - startTime));
    }
}
