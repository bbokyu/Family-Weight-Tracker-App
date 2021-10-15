package ui;

import model.Log;
import model.Member;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;


public class FamilyWeightTrackerApp {

    private ArrayList<Member> family;
    private Scanner input;
    private Date date;

    // EFFECTS: runs the Family Weight Tracker application
    public FamilyWeightTrackerApp() {
        runApp();
    }

    //some parts of code have been taken from CPSC 210 Teller App
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();
        System.out.println(date);
        System.out.println("\nWelcome to Family Weight Tracker Program!");
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }

        }

        System.out.println("\nSee ya later alligator!");
    }


    private void displayMenu() {
        System.out.println("\nHow can I help you today?:");
        System.out.println("\ta -> add new member");
        System.out.println("\tn -> add weight to chosen member");
        System.out.println("\tc -> check member's weight record and BMI");
        System.out.println("\td -> select member to delete");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        family = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addMember();
        } else if (command.equals("n")) {
            addWeight();
        } else if (command.equals("c")) {
            System.out.println("producing recording");
        } else if (command.equals("d")) {
            System.out.println("deleting member");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a member to the family
    private void addMember() {

        String selection = "";  // force entry into loop
        String nameNow = "";
        Integer height;

        while (!(selection.equals("b"))) {
            System.out.println("What will be the new member's name?");
            nameNow = input.next();
            System.out.println("What will be the height? (centimeters)");
            height = input.nextInt();
            Member temp = new Member(nameNow, height);
            family.add(temp);
            System.out.println(nameNow + " has been added to the family!");
            System.out.println("\nEnter 'b' to return!");
            selection = input.next();
            selection = selection.toLowerCase();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds weight to the chosen member
    private void addWeight() {

        String selection = "";  // force entry into loop
        Integer choice;
        Integer index = 1;
        Double weight = 0.0;
        Member selectedMem;


        while (!(selection.equals("b"))) {
            System.out.println("Which member's weight log should we update? \n Please select the number.");
            for (Member m: family) {
                System.out.println(index + ". " + m.getName());
                index++;
            }
            choice = input.nextInt();
            selectedMem = family.get(choice - 1);
            System.out.println("You have chosen: " + selectedMem.getName() + ".");
            System.out.println("What is your weight today? (KG)");
            weight = input.nextDouble();
            selectedMem.addWeightLog(weight);
            System.out.println(weight + " KG has been added to " + selectedMem.getName() + "'s log!");
            System.out.println("\nEnter 'b' to return!");
            selection = input.next();
            selection = selection.toLowerCase();
        }
    }





}
