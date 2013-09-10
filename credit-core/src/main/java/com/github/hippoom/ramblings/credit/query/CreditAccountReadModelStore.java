package com.github.hippoom.ramblings.credit.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CreditAccountReadModelStore {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void store(CreditAccountReadModel account) {
		entityManager.persist(account);
	}

	public CreditAccountReadModel findBy(Long id) {
		return entityManager.find(CreditAccountReadModel.class, id);
	}

}
