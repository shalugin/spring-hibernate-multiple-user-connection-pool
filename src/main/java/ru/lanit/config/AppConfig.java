package ru.lanit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.IsolationLevelDataSourceRouter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.lanit.pool.MyDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean(destroyMethod = "close")
    public MyDataSource dataSource(Environment env) throws PropertyVetoException {
        MyDataSource ds = new MyDataSource();
//        ds.setMaximumPoolSize(15);
//        ds.setDataSourceClassName(env.getRequiredProperty("hibernate.hikari.dataSourceClassName"));
//        ds.addDataSourceProperty("url", env.getRequiredProperty("spring.datasource.url"));
//        ds.addDataSourceProperty("user", env.getRequiredProperty("spring.datasource.username"));
//        ds.addDataSourceProperty("password", env.getRequiredProperty("spring.datasource.password"));
//        ds.setConnectionInitSql(env.getRequiredProperty("spring.datasource.init-sql"));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("ru.lanit");

        Properties jpaProperties = new Properties();

//        jpaProperties.put("hibernate.default_schema", env.getRequiredProperty("spring.datasource.username"));
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
//            jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
//        }
        jpaProperties.put("hibernate.show_sql", "true");
//        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}