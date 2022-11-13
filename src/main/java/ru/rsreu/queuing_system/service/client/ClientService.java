package ru.rsreu.queuing_system.service.client;

import ru.rsreu.queuing_system.model.Client;
import ru.rsreu.queuing_system.service.client.exception.ClientNotFoundException;

public interface ClientService {

    void putMoney(Client client) throws ClientNotFoundException;

    double takeMoney(Client client, double money) throws ClientNotFoundException;

    double getSpentMoney(Client client) throws ClientNotFoundException;
}
