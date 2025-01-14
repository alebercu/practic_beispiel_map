package org.example.ex2;

import org.example.ex2.Controller.Controller;
import org.example.ex2.Models.Client;
import org.example.ex2.Models.Movie;
import org.example.ex2.Repository.IRepository;
import org.example.ex2.Repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        IRepository<Client> clientRepo = new Repository<>();
        IRepository<Movie> movieRepo = new Repository<>();

        // Initialize the Controller
        Controller controller = new Controller(clientRepo, movieRepo);

        Movie movie1 = new Movie("Ale B", 1, "Rapunzel", "children", 60);
        Movie movie2 = new Movie("Ale B", 2, "Dynasty", "horror", 80);
        Movie movie3 = new Movie("Ana B", 3, "Spider Man", "action", 50);
        Movie movie4 = new Movie("Andra B", 4, "It", "horror", 65);

        controller.addMovie(movie1);
        controller.addMovie(movie2);
        controller.addMovie(movie3);
        controller.addMovie(movie4);

        Client client1 = new Client(1, "Victor", new ArrayList<>(List.of(movie1, movie3)));
        Client client2 = new Client(2, "Darius", new ArrayList<>(List.of(movie3, movie4)));
        Client client3 = new Client(3, "Rici", new ArrayList<>(List.of(movie1)));


        controller.addClient(client1);
        controller.addClient(client2);
        controller.addClient(client3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. List all clients");
            System.out.println("2. Add a client");
            System.out.println("3. Update a client");
            System.out.println("4. Delete a client");
            System.out.println("5. List all movies");
            System.out.println("6. Add a movie");
            System.out.println("7. Update a movie");
            System.out.println("8. Delete a movie");
            System.out.println("9. Filter Movies by genre");
            System.out.println("10. Show clients who have movies from a specific director");
            System.out.println("11. View movies ordered for a client");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    controller.listAllClients();
                    break;
                case 2:
                    System.out.print("Enter client name: ");
                    String clientName = scanner.nextLine();
                    Client newClient = new Client(0, clientName, List.of());
                    controller.addClient(newClient);
                    System.out.println("Client added.");
                    break;
                case 3:
                    System.out.print("Enter client ID to update: ");
                    int updateClientId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Client clientToUpdate = controller.getClient(updateClientId);
                    if (clientToUpdate != null) {
                        System.out.print("Enter new name: ");
                        clientToUpdate.setName(scanner.nextLine());
                        System.out.println("Client updated.");
                    } else {
                        System.out.println("Client not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter client ID to delete: ");
                    int deleteClientId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Client clientToDelete = controller.getClient(deleteClientId);
                    if (clientToDelete != null) {
                        controller.deleteClient(clientToDelete);
                        System.out.println("Client deleted.");
                    } else {
                        System.out.println("Client not found.");
                    }
                    break;
                case 5:
                    controller.listAllMovies();
                    break;
                case 6:
                    System.out.print("Enter movie Id: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter movie title: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter movie director: ");
                    String directorName = scanner.nextLine();
                    System.out.print("Enter movie genere: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter movie price: ");
                    int productPrice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Movie newMovie = new Movie(directorName,productId,productName,genre,productPrice);
                    controller.addMovie(newMovie);
                    System.out.println("Movie added.");
                    break;

                case 7:
                    System.out.print("Enter movie ID to update: ");
                    int updateProductId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Movie productToUpdate = controller.getMovie(updateProductId);
                    if (productToUpdate != null) {
                        System.out.print("Enter new name: ");
                        productToUpdate.setTitle(scanner.nextLine());
                        System.out.print("Enter new price: ");
                        productToUpdate.setPrice(scanner.nextInt());
                        scanner.nextLine(); // Consume newline
                        controller.updateMovie(productToUpdate);
                        System.out.println("Movie updated.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 8:
                    System.out.print("Enter movie ID to delete: ");
                    int deleteProductId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Movie productToDelete = controller.getMovie(deleteProductId);
                    if (productToDelete != null) {
                        controller.deleteMovie(productToDelete);
                        System.out.println("Product deleted.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 9:
                    System.out.print("Enter genre to filter by: ");
                    String g = scanner.nextLine();
                    controller.filterMoviesByGenre(g).forEach(System.out::println);
                    break;
                case 10:
                    System.out.print("Enter the director to see customers who bought films from that director: ");
                    String d = scanner.nextLine();
                    List<Client> clientsWhoBoughtFilm = controller.sortClientsByDirectorMovies(d);
                    if (clientsWhoBoughtFilm.isEmpty()) {
                        System.out.println("No clients found who bought films from " + d);
                    } else {
                        System.out.println("Customers who bought films from " + d + " are:");
                        clientsWhoBoughtFilm.forEach(System.out::println);
                    }
                    break;

                case 11:
                    displaySortedProductsForClient(scanner, controller);
                    break;

                case 12:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        }

    public static void displaySortedProductsForClient(Scanner scanner, Controller controller) {
        System.out.print("Enter the client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        System.out.print("Sort mode (asc/desc): ");
        String sortMode = scanner.nextLine().trim().toLowerCase();

        boolean ascending = sortMode.equals("asc");

        try {
            List<Movie> sortedMovies = controller.getSortedMoviesForClient(clientId, ascending);
            if (sortedMovies.isEmpty()) {
                System.out.println("The client has no movies.");
            } else {
                System.out.println("Sorted Movies:");
                sortedMovies.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    }


