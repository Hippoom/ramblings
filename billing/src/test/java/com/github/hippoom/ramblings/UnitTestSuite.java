package com.github.hippoom.ramblings;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.hippoom.ramblings.billing.query.ReceiptViewSynchronizerUnitTests;
import com.github.hippoom.ramblings.ordering.commandhandling.OrderCommandHandlerUnitTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ OrderCommandHandlerUnitTests.class,
		ReceiptViewSynchronizerUnitTests.class })
public class UnitTestSuite {

}
