package com.lisilab.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Keep a single instance because creating the factory is expensive.
 * 
 * @author jost
 * 
 */
public final class EMF {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EMF() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}