package org.example.ex2.Controller;

import org.example.ex2.Models.Client;
import org.example.ex2.Models.Movie;
import org.example.ex2.Repository.IRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;



public class Controller {
    public final IRepository<Client> clientRepo;
    public final IRepository<Movie> movieRepo;

    public Controller(IRepository<Client> clientRepo, IRepository<Movie> movieRepo) {
        this.clientRepo = clientRepo;
        this.movieRepo = movieRepo;
    }

    public void addClient(Client client) {
        if (client.getName().isEmpty()){
            throw new IllegalArgumentException("Client name cannot be empty");
        }

        clientRepo.create(client);

    }

    public List<Client> getClients() {
        return clientRepo.getAll();
    }


    public void listAllClients() {
        List<Client> clients = getClients();

        if(clients.isEmpty()){
            System.out.println("No clients found");
        }
        else{
            System.out.println("Clients found");
            for(Client client : clients){
                System.out.println(client);
            }
        }
    }

    public void deleteClient(Client client) {
        if (client == null){
            throw new IllegalArgumentException("Client cannot be null");
        }

        try {
            Client exists = clientRepo.read(client.getId());
            if (exists != null){
                clientRepo.delete(client.getId());
            }
        }catch (Exception ex){}
    }

    public void updateClient(Client client) {
        Client exists = clientRepo.read(client.getId());
        if (exists == null){
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientRepo.update(client.getId(), client);
    }

    public Client getClient(int id) {
        return clientRepo.read(id);
    }

    public void addMovie(Movie movie) {
        if (movie.getTitle().isEmpty()){
            throw new IllegalArgumentException("Movie title cannot be empty");
        }
        if (movie.getGenre().isEmpty()){
            throw new IllegalArgumentException("Movie genre cannot be empty");
        }
        if (movie.getPrice() < 0){
            throw new IllegalArgumentException("Movie price cannot be negative");
        }
        movieRepo.create(movie);
    }

    public List<Movie> getMovies() {
        return movieRepo.getAll();
    }


    public void listAllMovies() {
        List<Movie> pList = getMovies();

        if(pList.isEmpty()) {
            System.out.println("No product found");
        }
        else {
            System.out.println("Movie list:");
            for(Movie movie : pList) {
                System.out.println(movie);
            }
        }
    }


    public void deleteMovie(Movie product) {
        if (product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        try {
            Movie exists = movieRepo.read(product.getId());
            if (exists != null){
                movieRepo.delete(product.getId());
            }
        }catch (Exception ex){}

    }


    public void updateMovie(Movie product) {
        Movie exists = movieRepo.read(product.getId());
        if (exists == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        movieRepo.update(product.getId(), product);
    }

    public Movie getMovie(int id) {
        return movieRepo.read(id);
    }

    public List<Movie> filterMoviesByGenre(String genre) {
        List<Movie> mList = new ArrayList<>(getMovies());

        Iterator<Movie> it = mList.iterator();
        while(it.hasNext()){
            Movie movie = it.next();
            if (!movie.getGenre().equals(genre)){
                it.remove();
            }
        }

        if(mList.isEmpty()){
            System.out.println("No movies found");
        }
        return mList;
    }

    public List<Client> sortClientsByDirectorMovies(String director) {
        return clientRepo.getAll()
                .stream()
                .filter(c -> c.getMovies()
                        .stream()
                        .anyMatch(m -> m.getDirector().equals(director)))
                .collect(Collectors.toList());
    }


    public List<Movie> getSortedMoviesForClient(int clientId, boolean ascending) {
        Client client = clientRepo.read(clientId);

        if (client == null) {
            throw new IllegalArgumentException("Client with the given ID does not exist.");
        }

        return client.getMovies()
                .stream()
                .sorted(ascending ? Comparator.comparing(Movie::getPrice) : Comparator.comparing(Movie::getPrice).reversed())
                .collect(Collectors.toList());
    }
}



