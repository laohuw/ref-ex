package com.algo.pilot.ex_observ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("service")
public class ServerService {
    Logger logger= LoggerFactory.getLogger(Main.class);
    @GetMapping("planes")
    public List<String> getPlanes(){
        logger.info("get planes");
        List<String> planes = List.of("QA", "Prod");
        return planes;
    }
}
