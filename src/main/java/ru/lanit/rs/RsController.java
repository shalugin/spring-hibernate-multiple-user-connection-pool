package ru.lanit.rs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.lanit.MyRepo;
import ru.lanit.RefReputationSource;
import ru.lanit.RequestData;
import ru.lanit.pool.MyDataSource;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("resources/test")
@Slf4j
public class RsController extends HttpServlet {

    @EJB
    private MyRepo myRepo;

    @RequestMapping(method = GET)
    @ResponseBody
    public String getIt() {
        long l = System.currentTimeMillis();
        l = l % 500;
        RequestData requestData = new RequestData("u" + l, "u" + l);
        MyDataSource.setRequestData(requestData);
        log.info("Login for database: {}", requestData.getLogin());

        RefReputationSource data = myRepo.getData();
        MyDataSource.clearRequestData();
        return data.getName();
    }
}