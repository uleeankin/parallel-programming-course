package ru.rsreu.queuing_system.service;

import ru.rsreu.queuing_system.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

public class ClientServiceImpl implements ClientService {

    private final List<Client> clients = new ArrayList<>();

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public List<Client> getAllClients() {
        return this.clients;
    }

    @Override
    public Optional<Client> getClient(Client client) {
        return clients.contains(client) ? Optional.of(client) : Optional.empty();
    }

    @Override
    public boolean updateFundsAmount(Client client, double money,
                                     ToDoubleBiFunction<Double, Double> func) {
        if (this.getClient(client).isPresent()) {
            client.setFundsAmount(func.applyAsDouble(client.getFundsAmount(), money));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSpentMoney(Client client, double money,
                                    ToDoubleBiFunction<Double, Double> func) {
        if (this.getClient(client).isPresent()) {
            client.setSpentMoneyAmount(func.applyAsDouble(client.getSpentMoneyAmount(), money));
            return true;
        }
        return false;
    }
}
