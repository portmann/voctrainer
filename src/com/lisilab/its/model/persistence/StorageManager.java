package com.lisilab.its.model.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.lisilab.its.model.Student;
import com.lisilab.persistence.EMF;

public class StorageManager {

	private static final Logger log = Logger.getLogger(StorageManager.class
			.getName());

	private EntityManager em = EMF.get().createEntityManager();

	

	public void store(Object entity) {

		try {
			em.getTransaction().begin();
			em.persist(entity);
		} finally {
			em.getTransaction().commit();
		}

	}

	public void storeUser(Student student) {

		try {

			em.getTransaction().begin();

			em.persist(student);
			log.info("persisted student " + student.getUserName());

		} finally {
			em.getTransaction().commit();
		}
	}

	public Student loadUser(String userName) {

		Student result = null;

		try {

			em.getTransaction().begin();

			Query query = em.createQuery("SELECT s FROM "
					+ Student.class.getName()
					+ " s WHERE s.userName = :userName");

			query.setParameter("userName", userName);

			@SuppressWarnings("unchecked")
			List<Student> results = query.getResultList();

			if (results != null && results.size() > 0) {
				result = results.get(0);
			}
		} finally {
			em.getTransaction().commit();
		}

		return result;
	}

}
