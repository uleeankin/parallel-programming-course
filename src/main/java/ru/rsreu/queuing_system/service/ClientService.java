package ru.rsreu.queuing_system.service;

import ru.rsreu.queuing_system.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

public interface ClientService {

    void addClient(Client client);

    List<Client> getAllClients();

    Optional<Client> getClient(Client client);

    boolean updateFundsAmount(Client client, double money,
                              ToDoubleBiFunction<Double, Double> func);

    boolean updateSpentMoney(Client client, double money,
                             ToDoubleBiFunction<Double, Double> func);
}
