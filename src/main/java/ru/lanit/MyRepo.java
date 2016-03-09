package ru.lanit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.pool.MyDataSource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Service
@Transactional
public class MyRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public RefReputationSource getData() {
        RequestData requestData = MyDataSource.getRequestData();
        log.info("Login for DB: {}", requestData.getLogin());

        Object singleResult = entityManager.createNativeQuery("SELECT now()").getSingleResult();
        log.info("{}", singleResult);

        RefReputationSource refReputationSource = new RefReputationSource();
        refReputationSource.setName(requestData.getLogin());
        refReputationSource.setName(requestData.getLogin() + " " + singleResult);
        return refReputationSource;
    }
}