package com.github.hippoom.ramblings.ordercqrs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.hippoom.ramblings.ordercqrs.domain.BalanceUnitTests;
import com.github.hippoom.ramblings.ordercqrs.domain.OrderUnitTests;
import com.github.hippoom.ramblings.ordercqrs.persistence.DailyReadModelDaoPersistenceTests;
import com.github.hippoom.ramblings.ordercqrs.persistence.DetailReadModelDaoPersistenceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ OrderUnitTests.class, BalanceUnitTests.class,
		DetailReadModelDaoPersistenceTests.class,
		DailyReadModelDaoPersistenceTests.class })
public class PrecommitTestSuite {

}
