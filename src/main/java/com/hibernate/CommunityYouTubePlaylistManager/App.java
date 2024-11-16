package com.hibernate.CommunityYouTubePlaylistManager;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App
{
	private static SessionFactory factory;
	
    public static void main( String[] args )
    {
    	try {
       	 	Configuration con = new Configuration().configure().addAnnotatedClass(Person.class)
       	 			.addAnnotatedClass(Category.class).addAnnotatedClass(YouTubeLinks.class);
            ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
            factory = con.buildSessionFactory(reg); 
            
            Scanner scanner = new Scanner(System.in);    
            
            while (true) {
            	System.out.println();
            	System.out.println("----- Welcome to Community YouTube Playlist Manager -----");
            	System.out.println();
            	System.out.println("1. Manage Persons");
                System.out.println("2. Manage Categories");
                System.out.println("3. Manage YouTube Links");
                System.out.println("4. Manage Friends");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                    	managePersons(factory, scanner);
                        break;
                    case 2:
                    	manageCategory(factory, scanner);
                        break;
                    case 3:
                    	manageYouTubeLinks(factory, scanner);
                        break;
                    case 4:
                    	manageFriends(factory, scanner);
                        break;
                    case 5:
                    	exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option! Try again.");
                        break;
                }
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }

	private static void managePersons(SessionFactory factory, Scanner scanner) {
		while (true) {          	
        	System.out.println();
        	System.out.println("1. Add person");
            System.out.println("2. View All persons");
            System.out.println("3. View Specific person");
            System.out.println("4. Update person");
            System.out.println("5. Delete person");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    PersonDOA.addPerson(factory, scanner);
                    break;
                case 2:
                	PersonDOA.viewAllPersons(factory);
                    break;
                case 3:
                	PersonDOA.viewPerson(factory, scanner);
                    break;
                case 4:
                	PersonDOA.updatePerson(factory, scanner);
                    break;
                case 5:
                	PersonDOA.deletePerson(factory, scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
                    break;
            }
        }
	}
	
	private static void manageCategory(SessionFactory factory, Scanner scanner) {
		while (true) {          	
        	System.out.println();
        	System.out.println("1. Add Category");
            System.out.println("2. Delete Category");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    CategoryDOA.addCategory(factory, scanner);
                    break;
                case 2:
                	CategoryDOA.deleteCategory(factory, scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
                    break;
            }
        }
	}
	
	private static void manageYouTubeLinks(SessionFactory factory, Scanner scanner) {
		while (true) {          	
        	System.out.println();
        	System.out.println("1. Add YouTubeLink");
            System.out.println("2. Delete YouTubeLink");
            System.out.println("3. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    YouTubeLinkDOA.addYouTubeLink(factory, scanner);
                    break;
                case 2:
                	YouTubeLinkDOA.deleteYouTubeLink(factory, scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
                    break;
            }
        }
	}
	
	private static void manageFriends(SessionFactory factory2, Scanner scanner) {
		while (true) {          	
        	System.out.println();
        	System.out.println("1. Add friend");
            System.out.println("2. View my friends");
            System.out.println("3. Remove friend");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    FriendsDOA.addFriend(factory, scanner);
                    break;
                case 2:
                	FriendsDOA.viewFriends(factory, scanner);
                    break;
                case 3:
                	FriendsDOA.removeFriend(factory, scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
                    break;
            }
        }
	}	
	
	private static void exit() throws InterruptedException {
		System.out.println();
		System.out.print("Exiting");
        int i = 5;
        while (i!=0) {
            System.out.print(".");
            Thread.sleep(300);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Community YouTube Playlist Manager.");
	}	
	
}
