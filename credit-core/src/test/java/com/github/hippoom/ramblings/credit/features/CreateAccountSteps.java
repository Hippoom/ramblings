package com.github.hippoom.ramblings.credit.features;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.hippoom.ramblings.credit.domain.model.creditaccount.CreateCreditAccountCommand;
import com.github.hippoom.ramblings.credit.query.CreditAccountReadModelStore;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateAccountSteps {

	private Long accountId;
	private Date effectiveStart;
	private Date effectiveEnd;
	private int balance;

	@Autowired
	private CommandGateway gateway;

	@Autowired
	private CreditAccountReadModelStore readModel;

	@When("^I specify account id as '(\\d+)'$")
	public void I_specify_account_id_as_(Long arg1) throws Throwable {
		this.accountId = arg1;
	}

	@When("^I specify effective date range between '(.*)' and '(.*)'$")
	public void I_specify_effective_date_range_between_and_(String arg1,
			String arg2) throws Throwable {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.effectiveStart = sdf.parse(arg1);
		this.effectiveEnd = sdf.parse(arg2);
	}

	@When("^I specify initial balance as '(\\d+)'$")
	public void I_specify_initial_balance_as_(int arg1) throws Throwable {
		this.balance = arg1;
	}

	@Then("^a credit account is created$")
	public void a_credit_account_is_created() throws Throwable {
		gateway.send(new CreateCreditAccountCommand(accountId, balance,
				effectiveStart, effectiveEnd));
		
		System.err.println(readModel.findBy(accountId));
	}

}
