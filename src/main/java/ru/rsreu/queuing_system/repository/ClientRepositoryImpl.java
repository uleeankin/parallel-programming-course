package ru.rsreu.queuing_system.repository;

import ru.rsreu.queuing_system.model.base.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

public class ClientRepositoryImpl implements ClientRepository {

    private final List<Client> clients = new ArrayList<>();

    @Override
    public void addClient(Client client) {
        synchronized (this.clients) {
            this.clients.add(client);
        }
    }

    @Override
    public List<Client> getAllClients() {
        synchronized (this.clients) {
            return this.clients;
        }
    }

    @Override
    public Optional<Client> getClient(Client client) {
        synchronized (this.clients) {
            return this.clients.contains(client) ? Optional.of(client) : Optional.empty();
        }
    }

    @Override
    public boolean updateFundsAmount(Client client, double money,
                                     ToDoubleBiFunction<Double, Double> func) {

        Optional<Client> foundClient = this.getClient(client);
        if (foundClient.isPresent()) {
            synchronized (foundClient.get()) {
                foundClient.get().setFundsAmount(
                        func.applyAsDouble(
                                foundClient.get().getFundsAmount(), money));
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean updateSpentMoney(Client client, double money,
                                    ToDoubleBiFunction<Double, Double> func) {
        Optional<Client> foundClient = this.getClient(client);
        if (foundClient.isPresent()) {
            synchronized (foundClient.get()) {
                foundClient.get().setSpentMoneyAmount(
                        func.applyAsDouble(
                                foundClient.get().getSpentMoneyAmount(), money));
                return true;
            }
        }
        return false;
    }
}
