package com.github.hippoom.ramblings.billing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.hippoom.ramblings.billing.commandhandling.OrderCommandHandlerUnitTests;
import com.github.hippoom.ramblings.billing.commandhandling.PaymentCommandHandlerUnitTests;
import com.github.hippoom.ramblings.billing.query.ReceiptViewSynchronizerUnitTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PaymentCommandHandlerUnitTests.class,
		OrderCommandHandlerUnitTests.class,
		ReceiptViewSynchronizerUnitTests.class })
public class UnitTestSuite {

}
