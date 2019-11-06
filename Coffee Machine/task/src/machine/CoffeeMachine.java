package machine;

import java.util.Scanner;

public class CoffeeMachine {

    private Scanner userInput;

    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int cash;
    private boolean powerOn;


    private CoffeeMachine(){
        this.water = 400;
        this.beans = 120;
        this.milk = 540;
        this.cups = 9;
        this.cash = 550;
        this.powerOn = true;
        this.userInput =new Scanner(System.in);
    }

    private boolean userInteract(){ //All user input handled here
        System.out.print("Write action (buy, fill, take, remaining, exit): ");
        String command = userInput.next();
        switch (command) {
            case "buy":
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                if(userInput.hasNextInt()){
                    int coffeeType = userInput.nextInt();
                    buyCoffee(coffeeType);
                    break;
                }
                break;
            case "fill":
                String[] fillPrompts = new String[]{"ml of water", "ml of milk", "grams of coffee beans", "cups"};
                int[] fillAmounts = new int[4];
                for(int i=0; i <4; i++){
                    System.out.printf("Write how many %s do you want to add: ",fillPrompts[i]);
                    fillAmounts[i] = userInput.nextInt();
                }
                updateSupplies(fillAmounts[0],fillAmounts[1],fillAmounts[2],fillAmounts[3]);
                break;
            case "take":
                System.out.println("I gave you $" + this.cash);
                updateSupplies();
                break;
            case "remaining":
                printStatus();
                break;
            case "exit": //close scanner and set powerOn to false
                userInput.close();
                return false;
        }
        return true; //Continue running userInteract()
    }

    private void printStatus() {
        System.out.printf("%nThe coffee machine has:%n%d of water%n%d of milk%n%d of coffee beans%n%d of disposable cups%n%d of money%n%n",water, milk, beans, cups, cash);
    }

    private void buyCoffee(int coffeeType) {
        int waterReqd = 0;
        int milkReqd = 0;
        int beansReqd = 0;
        int cupsReqd = 1;
        int price = 0;
        switch(coffeeType){
            case 1://espresso
                waterReqd = 250;
                beansReqd = 16;
                price = 4;
                break;
            case 2://latte
                waterReqd= 350;
                milkReqd= 75;
                beansReqd= 20;
                price = 7;
                break;
            case 3://cappuccino
                waterReqd= 200;
                milkReqd= 100;
                beansReqd= 12;
                price = 6;
                break;
        }
        //Guard clauses instead of if else
        if(waterReqd>water) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (milkReqd>milk) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (beansReqd>beans) {
            System.out.println("Sorry, not enough beans!");
            return;
        }
        if (this.cups<1) {
            System.out.println("Sorry, not enough cups!");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        updateSupplies(waterReqd, milkReqd, beansReqd, cupsReqd, price);

    }

    /* updateSupplies() will handle all variable updates and is overloaded to be reused depending on the number of arguments sent */
    private void updateSupplies(int water, int milk, int beans, int cups) {
        this.water += water;
        this.milk += milk;
        this.beans += beans;
        this.cups += cups;
    }
    private void updateSupplies(int water, int milk, int beans, int cups, int cash) {
        this.water -= water;
        this.milk -= milk;
        this.beans -= beans;
        this.cups -= cups;
        this.cash += cash;
    }
    private void updateSupplies() {
        this.cash = 0;
    }

    public static void main(String[] args) {
        CoffeeMachine breakRoomMachine = new CoffeeMachine();
        CoffeeMachine testMachine = new CoffeeMachine();//This object is here to compare variables for testing purposes
       do {
            breakRoomMachine.powerOn = breakRoomMachine.userInteract();
            }  while(breakRoomMachine.powerOn);
       }


}
