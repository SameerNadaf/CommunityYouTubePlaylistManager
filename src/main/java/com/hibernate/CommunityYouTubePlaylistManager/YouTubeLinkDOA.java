package com.hibernate.CommunityYouTubePlaylistManager;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class YouTubeLinkDOA {

	public static void addYouTubeLink(SessionFactory factory, Scanner scanner) {
		
		Transaction tx = null;
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
		
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, id);
			List<Category> categories = (List<Category>) person.getCategories();
			
			
			if (categories == null) {
				System.out.println();
	            System.out.println("No Category found with Person ID " + id);
	            return;
	        }
			
			for (Category category : categories) {
	        	System.out.println();
	        	System.out.println("Category Name: " + category.getName());
	            System.out.println("Do you want to add new youtube link in this Category?");
	            System.out.println("1. Yes \n2. No");
	            
	            int option = scanner.nextInt();

	            if (option == 1) {
	            	System.out.println();
	            	System.out.print("Enter YouTube URL: ");
	                String ytUrl = scanner.next();
	                System.out.print("Enter Video Title: ");
	                String title = scanner.next();
	                System.out.print("Enter Video status: ");
	                String status = scanner.next();
	                System.out.print("Enter rating (1-10): ");
	                int rating = scanner.nextInt();
	                
	                YouTubeLinks link = new YouTubeLinks();
	                link.setUrl(ytUrl);
	                link.setTitle(title);
	                link.setStatus(status);
	                link.setRating(rating);
	                link.setCategory(category);
	                
	                session.persist(link);
	                
	            } else if (option == 2) {
	            	System.out.println();
	                System.out.println("Skipped adding new link: " + category.getName());
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

	public static void deleteYouTubeLink(SessionFactory factory, Scanner scanner) {

		Transaction tx = null;
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
		
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, id);
			List<Category> categories = (List<Category>) person.getCategories();
			
			
			if (categories == null) {
				System.out.println();
	            System.out.println("No Category found with Person ID " + id);
	            return;
	        }
			
			for (Category category : categories) {
	        	System.out.println();
	        	System.out.println("Category Name: " + category.getName());
	            System.out.println("Do you want to browse this Category?");
	            System.out.println("1. Yes \n2. No");
	            
	            int option = scanner.nextInt();

	            if (option == 1) {
	                for (YouTubeLinks link : category.getYtLinks()) {
	                	System.out.println();
	    	        	System.out.println("Title: " + link.getTitle());
	    	            System.out.println("Do you want to delete this video?");
	    	            System.out.println("1. Yes \n2. No");
	    	            
	    	            int opt = scanner.nextInt();
	    	            
	    	            if (opt == 1) {
	    	                session.remove(link);
	    	                System.out.println();
	    	                System.out.println("Video '" + link.getTitle() + "' deleted.");
	    	            } else if (opt == 2) {
	    	            	System.out.println();
	    	                System.out.println("Skipped deleting video: " + link.getTitle());
	    	            } else {
	    	            	System.out.println();
	    	                System.out.println("Invalid option! Skipping video: " + link.getTitle());
	    	            }
	    	            
	                }
	            } else if (option == 2) {
	            	System.out.println();
	                System.out.println("Skipped category: " + category.getName());
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
