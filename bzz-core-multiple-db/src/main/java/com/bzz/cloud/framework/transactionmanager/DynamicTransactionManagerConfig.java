package com.bzz.cloud.framework.transactionmanager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DynamicTransactionManagerConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicTransactionManagerConfig.class);
	
	@Bean(name = "atomikosUserTransaction")
	public UserTransactionImp atomikosUserTransaction() {
		UserTransactionImp atomikosUserTransaction = new UserTransactionImp();
		try {
			atomikosUserTransaction.setTransactionTimeout(300);
			logger.info("[TransactionUtil.begin] set transaction timeout to : " + 300 + " seconds");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return atomikosUserTransaction;
	}
	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public UserTransactionManager atomikosTransactionManager() {
		UserTransactionManager atomikosTransactionManager = new UserTransactionManager();
		atomikosTransactionManager.setForceShutdown(true);
		return atomikosTransactionManager;
	}
	
	@Bean(name = "transactionManager")
	@DependsOn({ "atomikosUserTransaction","atomikosTransactionManager" })
	public JtaTransactionManager transactionManager(UserTransactionManager atomikosTransactionManager,
	                                                UserTransactionImp atomikosUserTransaction) {
		JtaTransactionManager transactionManager = new JtaTransactionManager();
		transactionManager.setTransactionManager(atomikosTransactionManager);
		transactionManager.setUserTransaction(atomikosUserTransaction);
		//transactionManager.setAllowCustomIsolationLevels(true);
		return transactionManager;
	}
}
