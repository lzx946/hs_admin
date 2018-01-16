package top.tsama.baseapiserviceweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tsama.baseapiservicedomain.model.TbTest;
import top.tsama.baseapiserviceservices.TestServiceImpl;

import java.util.List;

@RestController
public class TestController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestServiceImpl testServiceImpl;

    @RequestMapping(value = "/")
    public List<TbTest> home() {
        log.info(testServiceImpl.selectAll().get(0).getName());
        log.info(testServiceImpl.selectAll().get(1).getName());
        return testServiceImpl.selectAll();
    }
}
