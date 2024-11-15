package com.hibernate.CommunityYouTubePlaylistManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PersonDOA {

	public static void addPerson(SessionFactory factory, Scanner scanner) {
		
		Transaction tx = null;
		
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			System.out.println();
			System.out.print("Enter Person Name: ");
	        String personName = scanner.next();
	        System.out.print("Enter Person Email: ");
	        String personEmail = scanner.next();
	        
	        Person person = new Person();
	        person.setName(personName);
	        person.setEmail(personEmail);
	        
	        System.out.println();
	        System.out.print("How many categories you want to add: ");
	        int categoryCount = scanner.nextInt();
	        List<Category> categories = new ArrayList<>();
	        
	        for (int i = 0; i < categoryCount; i++) {
	            System.out.print("Enter Category Name: ");
	            String categoryName = scanner.next();

	            Category category = new Category();
	            category.setName(categoryName);
	            category.setPerson(person);

	            System.out.println();
	            System.out.print("How many YouTube links for this category you want to add: ");
	            int linkCount = scanner.nextInt();
	            List<YouTubeLinks> ytLinks = new ArrayList<>();

	            for (int j = 0; j < linkCount; j++) {
	                System.out.print("Enter YouTube URL: ");
	                String ytUrl = scanner.next();
	                System.out.print("Enter Video Title: ");
	                String title = scanner.next();
	                System.out.print("Enter Video status: ");
	                String status = scanner.next();
	                System.out.print("Enter rating (1-10): ");
	                int rating = scanner.nextInt();

	                YouTubeLinks ytLink = new YouTubeLinks();
	                ytLink.setUrl(ytUrl);
	                ytLink.setTitle(title);
	                ytLink.setStatus(status);
	                ytLink.setRating(rating);
	                ytLink.setCategory(category);

	                ytLinks.add(ytLink);
	            }

	            category.setYtLinks(ytLinks);
	            categories.add(category);
	        }

	        person.setCategories(categories);

	        session.persist(person);
	        tx.commit();
	        
	        System.out.println("Transaction committed.");	        
	        System.out.println();
	        
		} catch (Exception e) {
			if (tx != null) {
	            tx.rollback();
	        }
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	public static void viewAllPersons(SessionFactory factory) {
		
		try (Session session = factory.openSession()) {
			String hql = "FROM Person p ORDER BY p.id";
            Query<Person> query = session.createQuery(hql, Person.class);
            List<Person> persons = query.getResultList();
            
            System.out.println();
            System.out.println("Persons");

            for (Person person : persons) {
            	System.out.println("-----------------------------------------");
            	System.out.println("Person ID: " + person.getId());
                System.out.println("Person Name: " + person.getName());
                System.out.println("Person Email: " + person.getEmail());

                for (Category category : person.getCategories()) {
                	System.out.println();
                	System.out.println("  Category ID: " + category.getId());
                    System.out.println("  Category Name: " + category.getName());

                    for (YouTubeLinks ytLink : category.getYtLinks()) {
                    	System.out.println();
                    	System.out.println("    YouTube ID: " + ytLink.getId());
                        System.out.println("    YouTube URL: " + ytLink.getUrl());
                        System.out.println("    YouTube Title: " + ytLink.getTitle());
                        System.out.println("    YouTube Status: " + ytLink.getStatus());
                        System.out.println("    YouTube Rating: " + ytLink.getRating());
                    }
                }
                System.out.println("-----------------------------------------");
                System.out.println();
            }
		} catch (Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void viewPerson(SessionFactory factory, Scanner scanner) {
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
        
		try (Session session = factory.openSession()) {
			
			Person person = (Person) session.get(Person.class, id);
                        
            if (person != null) {
            	System.out.println();
            	System.out.println("-----------------------------------------");
            	System.out.println("Person ID: " + person.getId());
                System.out.println("Person Name: " + person.getName());
                System.out.println("Person Email: " + person.getEmail());

                for (Category category : person.getCategories()) {
                	System.out.println();
                	System.out.println("  Category ID: " + category.getId());
                    System.out.println("  Category Name: " + category.getName());

                    for (YouTubeLinks ytLink : category.getYtLinks()) {
                    	System.out.println();
                    	System.out.println("    YouTube ID: " + ytLink.getId());
                        System.out.println("    YouTube URL: " + ytLink.getUrl());
                        System.out.println("    YouTube Title: " + ytLink.getTitle());
                        System.out.println("    YouTube Status: " + ytLink.getStatus());
                        System.out.println("    YouTube Rating: " + ytLink.getRating());
                    }
                }
                System.out.println("-----------------------------------------");
            }
            else {
	    		System.out.println();
	    		System.out.println("No person found with ID " + id);
	    	}
            System.out.println();
		} catch (Exception e) {
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	public static void updatePerson(SessionFactory factory, Scanner scanner) {
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
        
        Transaction tx = null;
        
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, id);
                        
            if (person != null) {
            	System.out.println();
            	System.out.print("Enter Person Name: ");
                String name = scanner.next();
                System.out.print("Enter Person Email: ");
                String mail = scanner.next();
            	person.setName(name);
            	person.setEmail(mail);
            	session.merge(person);
            	tx.commit();
            	System.out.println("Transaction committed.");
            }
            else {
	    		System.out.println();
	    		System.out.println("No person found with ID " + id);
	    	}
            System.out.println();
		} catch (Exception e) {
			if (tx != null) {
	            tx.rollback();
	        }
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

	public static void deletePerson(SessionFactory factory, Scanner scanner) {
		
		System.out.println();
		System.out.print("Enter Person ID: ");
        long id = scanner.nextInt();
        
        Transaction tx = null;
        
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, id);
                        
            if (person != null) {
            	session.remove(person);
            	tx.commit();
            	System.out.println("Transaction committed.");
            }
            else {
	    		System.out.println();
	    		System.out.println("No person found with ID " + id);
	    	}
            System.out.println();
		} catch (Exception e) {
			if (tx != null) {
	            tx.rollback();
	        }
			System.out.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
			
	}

}