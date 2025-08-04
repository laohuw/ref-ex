package com.algo.ex.mtls.exmtls;

import org.slf4j.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    private final Logger logger= LoggerFactory.getLogger(ApiController.class);

    @GetMapping("dominions")
    public List<String> getDominions(){
        logger.info("get dominions");
        return List.of("fio","wt");
    }

    @GetMapping("planes")
    public List<String> getPlanes(){
        logger.info( "get planes");
        return List.of("QA","Prod");
    }
}
