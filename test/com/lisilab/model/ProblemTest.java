package com.lisilab.model;


import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lisilab.persistence.EMF;

public class ProblemTest {
	
	private static final Logger log = Logger
			.getLogger(ProblemTest.class.getName());
	
	private EntityManager em;
	
	@Before
	public void setUp() throws Exception {
		em = EMF.get().createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		em.close();
	}

	@Test
	public void testPersist() {

		try {


		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}
}
