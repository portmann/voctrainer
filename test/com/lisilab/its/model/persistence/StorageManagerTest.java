package com.lisilab.its.model.persistence;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lisilab.its.model.Student;
import com.lisilab.persistence.EMF;

public class StorageManagerTest {

	private static final Logger log = Logger.getLogger(StorageManagerTest.class
			.getName());

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
	public void test() {

		try {

			em.getTransaction().begin();
			Student joe = new Student();
			Student peter = new Student();

			joe.setUserName("John Doe");
			peter.setUserName("Peter");

			em.persist(peter);
			log.info("persisted student " + peter.getUserName());

			em.getTransaction().commit();
			em.getTransaction().begin();

			em.persist(joe);
			log.info("persisted student " + joe.getUserName());

			em.getTransaction().commit();

			Query query = em.createQuery("SELECT s FROM "
					+ Student.class.getName() + " s WHERE s.userId = :userId");

			query.setParameter("userId", "John Doe");

			@SuppressWarnings("unchecked")
			List<Student> results = query.getResultList();

			assertEquals(1, results.size());
			assertEquals("John Doe", results.get(0).getUserName());

		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	@Test
	public void storeStudentTest() {

		StorageManager utils = new StorageManager();

		Student student = new Student();

		student.setUserName("John Doe");
		student.setPasswordHash("pw".hashCode());

		utils.storeUser(student);

		student = utils.loadUser("John Doe");

		assertTrue(student != null);
		assertEquals(student.getPasswordHash(), "pw".hashCode());
		assertEquals(student.getUserName(), "John Doe");

	}

}
