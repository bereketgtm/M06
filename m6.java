import java.util.*;

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

class CryptoPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Cryptocurrency");
    }
}

class PaymentContext {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(double amount) {

        if (strategy == null) {
            System.out.println("Payment strategy not selected");
            return;
        }

        strategy.pay(amount);
    }
}

interface Observer {
    void update(String currency, double rate);
}

interface Subject {

    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers(String currency, double rate);
}

class CurrencyExchange implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String currency, double rate) {

        for (Observer observer : observers) {
            observer.update(currency, rate);
        }

    }

    public void setRate(String currency, double rate) {

        System.out.println("\nCurrency updated: " + currency + " = " + rate);

        notifyObservers(currency, rate);
    }

}

class BankObserver implements Observer {

    public void update(String currency, double rate) {
        System.out.println("Bank received update: " + currency + " = " + rate);
    }

}

class InvestorObserver implements Observer {

    public void update(String currency, double rate) {
        System.out.println("Investor received update: " + currency + " = " + rate);
    }

}

class MobileAppObserver implements Observer {

    public void update(String currency, double rate) {
        System.out.println("Mobile app notification: " + currency + " = " + rate);
    }

}

public class DesignPatternsDemo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== STRATEGY PATTERN ===");

        PaymentContext context = new PaymentContext();

        System.out.println("Choose payment method:");
        System.out.println("1 - Credit Card");
        System.out.println("2 - PayPal");
        System.out.println("3 - Crypto");

        int choice = scanner.nextInt();

        if (choice == 1)
            context.setStrategy(new CreditCardPayment());

        else if (choice == 2)
            context.setStrategy(new PayPalPayment());

        else if (choice == 3)
            context.setStrategy(new CryptoPayment());

        else {
            System.out.println("Invalid choice");
            return;
        }

        context.executePayment(100);

        System.out.println("\n=== OBSERVER PATTERN ===");

        CurrencyExchange exchange = new CurrencyExchange();

        Observer bank = new BankObserver();
        Observer investor = new InvestorObserver();
        Observer mobile = new MobileAppObserver();

        exchange.attach(bank);
        exchange.attach(investor);
        exchange.attach(mobile);

        exchange.setRate("USD", 500);
        exchange.setRate("EUR", 550);

    }

}
