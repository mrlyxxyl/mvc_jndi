package com.yx.controller;

import com.yx.service.PersonService;
import com.yx.utils.GenResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "person")
public class PersonController {

    @Resource
    private PersonService personService;

    private static Logger log = Logger.getLogger(PersonController.class);

    /**
     * 根据serverName获取数据源 然后获取数据
     *
     * @param serverName
     * @return
     */
    @RequestMapping(value = "queryAllPersons")
    @ResponseBody
    public Map<String, Object> queryAllPersons(String serverName) {
        try {
            List<Map<String, Object>> persons = personService.queryPersons(serverName);
            if (persons.size() > 0) {
                return GenResult.SUCCESS.genResult(persons);
            } else {
                return GenResult.NO_DATA.genResult();
            }
        } catch (Exception e) {
            log.error(e, e);
            return GenResult.FAILED.genResult();
        }
    }
}
