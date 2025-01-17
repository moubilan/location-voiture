package com.location.voiture.services;

import com.location.voiture.models.Client;
import com.location.voiture.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Integer id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Integer id, Client clientDetails) {
        return clientRepository.findById(id).map(client -> {
            client.setEmail(clientDetails.getEmail());
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setPassword(clientDetails.getPassword());
            client.setRole(clientDetails.getRole());
            client.setTelephone(clientDetails.getTelephone());
            client.setAdresse(clientDetails.getAdresse());
            client.setPermisConduireValide(clientDetails.isPermisConduireValide());
            return clientRepository.save(client);
        }).orElseThrow(() -> new EntityNotFoundException("Client not found with id " + id));
    }

    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}

