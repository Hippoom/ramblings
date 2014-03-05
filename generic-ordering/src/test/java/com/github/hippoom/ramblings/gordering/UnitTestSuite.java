package com.github.hippoom.ramblings.gordering;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.hippoom.ramblings.gordering.domain.OrderFullfillingSagaUnitTests;
import com.github.hippoom.ramblings.gordering.domain.OrderUnitTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ OrderUnitTests.class, OrderFullfillingSagaUnitTests.class })
public class UnitTestSuite {

}
