package com.github.hippoom.ramblings.airswitch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AirReservationUnitTests.class, AirTicketUnitTests.class,
		MakeReservationSagaUnitTests.class })
public class CommitTestSuite {

}
