package com.hibernate.CommunityYouTubePlaylistManager;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CategoryDOA {

	public static void addCategory(SessionFactory factory, Scanner scanner) {
		
		Transaction tx = null;
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
		
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, id);
			
			if (person == null) {
				System.out.println();
	            System.out.println("No person found with ID " + id);
	            return;
	        }
			
			System.out.println();
			System.out.print("Enter Category Name: ");
            String categoryName = scanner.next();
            
			Category category = new Category();
			category.setName(categoryName);
			category.setPerson(person);
			
			session.persist(category);
			tx.commit();
			System.out.println();
	        System.out.println("Transaction committed.");
			
		} catch (Exception e) {
			if (tx != null) {
	            tx.rollback();
	        }
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void deleteCategory(SessionFactory factory, Scanner scanner) {
		
	    Transaction tx = null;

	    System.out.println();
	    System.out.print("Enter Person ID: ");
	    long id = scanner.nextInt();

	    try (Session session = factory.openSession()) {
	        tx = session.beginTransaction();

	        Person person = session.get(Person.class, id);

	        if (person == null) {
	        	System.out.println();
	            System.out.println("No person found with ID " + id);
	            return;
	        }

	        List<Category> categories = (List<Category>) person.getCategories();

	        if (categories == null || categories.isEmpty()) {
	        	System.out.println();
	            System.out.println("No categories found for this person.");
	            return;
	        }

	        for (Category category : categories) {
	        	System.out.println();
	            System.out.println("Category Name: " + category.getName());
	            System.out.println("Do you want to delete this Category?");
	            System.out.println("1. Yes \n2. No");
	            
	            int option = scanner.nextInt();

	            if (option == 1) {
	                session.remove(category);
	                System.out.println();
	                System.out.println("Category '" + category.getName() + "' deleted.");
	            } else if (option == 2) {
	            	System.out.println();
	                System.out.println("Skipped deleting category: " + category.getName());
	            } else {
	            	System.out.println();
	                System.out.println("Invalid option! Skipping category: " + category.getName());
	            }
	        }

	        tx.commit();
	        System.out.println();
	        System.out.println("Transaction committed.");
	        
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error occurred: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
