package ru.rsreu.queuing_system.shop_api;

import ru.rsreu.queuing_system.exception.client.InsufficientFundsAmountException;
import ru.rsreu.queuing_system.exception.shop.InsufficientProductAmountException;
import ru.rsreu.queuing_system.model.base.Client;
import ru.rsreu.queuing_system.model.base.Product;
import ru.rsreu.queuing_system.model.base.ShopProduct;
import ru.rsreu.queuing_system.exception.client.ClientNotFoundException;
import ru.rsreu.queuing_system.exception.shop.ProductNotFoundException;
import ru.rsreu.queuing_system.repository.ClientRepository;
import ru.rsreu.queuing_system.repository.ShopRepository;

import java.util.Optional;

public class SimpleShopApiImpl extends AbstractShopApi {

    public SimpleShopApiImpl(ClientRepository clientRepository, ShopRepository shopRepository) {
        super(clientRepository, shopRepository);
    }

    @Override
    public void buyProduct(Client client, Product product, int amount)
            throws ClientNotFoundException,
            ProductNotFoundException,
            InsufficientProductAmountException,
            InsufficientFundsAmountException {

        Optional<Client> foundClient = this.findClient(client);
        Optional<ShopProduct> foundProduct = this.findProduct(product);
        double totalCost;

        if (foundProduct.get().getAmount() < amount) {
            throw new InsufficientProductAmountException();
        }

        totalCost = amount * foundProduct.get().getPrice();

        if (foundClient.get().getFundsAmount() < totalCost) {
            throw new InsufficientFundsAmountException();
        }
        synchronized (this.shopRepository) {
            synchronized (this.clientRepository) {
                this.clientRepository.updateFundsAmount(foundClient.get(), totalCost,
                        (fundsAmount, cost) -> fundsAmount - cost);
                this.clientRepository.updateSpentMoney(foundClient.get(), totalCost, Double::sum);
            }
            this.shopRepository.updateProductAmount(foundProduct.get().getProduct(), amount,
                    (oldAmount, reservedAmount) -> oldAmount - reservedAmount);
            this.shopRepository.updateFundsAmount(totalCost);
        }
    }
}
