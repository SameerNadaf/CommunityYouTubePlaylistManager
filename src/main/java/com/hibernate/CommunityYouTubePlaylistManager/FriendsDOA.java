package com.hibernate.CommunityYouTubePlaylistManager;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class FriendsDOA {
	
	public static void addFriend(SessionFactory factory, Scanner scanner) {
		
		System.out.println();
		System.out.print("Enter Your ID: ");
        long uid = scanner.nextInt();
        
        System.out.println();
		System.out.print("Enter Friend ID: ");
        long fid = scanner.nextInt();
        
        Transaction tx = null;
        
		try (Session session = factory.openSession()) {
			
			tx = session.beginTransaction();
			
			Person person = (Person) session.get(Person.class, uid);
			Person friend = (Person) session.get(Person.class, fid);
                        
            if (person != null && friend != null) {
            	person.getFriends().add(friend);
            	session.merge(person);
            	tx.commit();
            	System.out.println("Transaction committed.");
            }
            else if (person == null){
	    		System.out.println();
	    		System.out.println("No person found with ID " + uid);
	    	}
            else if (friend == null){
	    		System.out.println();
	    		System.out.println("No person found with ID " + fid);
	    	}
            else {
            	System.out.println();
	    		System.out.println("No persons found with ID " + uid + " and " + fid);
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

	public static void viewFriends(SessionFactory factory, Scanner scanner) {
		
		 System.out.println();
		 System.out.print("Enter Person ID: ");
		 long id = scanner.nextLong();

		 try (Session session = factory.openSession()) {
			 
		     Person person = session.get(Person.class, id);

		     if (person != null) {
	
		         if (person.getFriends().isEmpty()) {
		             System.out.println("No friends found.");
		         } 
		         else 
		         {
		        	 System.out.println();
			         System.out.println("Friends of Person ID: " + id);
			         
		        	 for (Person friend : person.getFriends()) { 
		                 System.out.println();
		                 System.out.println("-----------------------------------------");
		                 System.out.println("Friend ID: " + friend.getId());
		                 System.out.println("Friend Name: " + friend.getName());
		                 System.out.println("Friend Email: " + friend.getEmail());

		                 for (Category category : friend.getCategories()) {
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
		        }
		    } 
		    else 
		    {
		        System.out.println();
		        System.out.println("No person found with ID " + id);
		    }
		    System.out.println();
		} catch (Exception e) {
		    System.out.println("Error occurred: " + e.getMessage());
		    e.printStackTrace();
		}
	}

	public static void removeFriend(SessionFactory factory, Scanner scanner) {
		
		System.out.println();
	    System.out.print("Enter Your ID: ");
	    long uid = scanner.nextLong();

	    System.out.println();
	    System.out.print("Enter Friend's ID to Remove: ");
	    long fid = scanner.nextLong();

	    Transaction tx = null;

	    try (Session session = factory.openSession()) {
	        tx = session.beginTransaction();

	        Person person = session.get(Person.class, uid);
	        Person friend = session.get(Person.class, fid);

	        if (person != null && friend != null) {
	            if (person.getFriends().contains(friend)) {
	                person.getFriends().remove(friend);
	                friend.getFriends().remove(person);

	                session.merge(person);
	                session.merge(friend);

	                tx.commit();
	                System.out.println("Friend removed successfully!");
	            } else {
	                System.out.println("This person is not in your friends list.");
	            }
	        } else {
	            if (person == null) {
	                System.out.println("No person found with ID " + uid);
	            }
	            if (friend == null) {
	                System.out.println("No person found with ID " + fid);
	            }
	        }
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error occurred: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
}
