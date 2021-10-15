package ui;

import model.Log;
import model.Member;
import model.exceptions.NegativeValueException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class FamilyWeightTrackerApp {

    private ArrayList<Member> family;
    private Scanner input;
    private LocalDate date;

    // EFFECTS: runs the Family Weight Tracker application
    public FamilyWeightTrackerApp() {
        runApp();
    }

    //some parts of code have been taken from CPSC 210 Teller App Repo below
    // URL: https://github.students.cs.ubc.ca/CPSC210/TellerApp
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


    // EFFECTS: Prompts menu to user
    private void displayMenu() {
        System.out.println("\nHow can I help you today?:");
        System.out.println("\ta -> add new member");
        System.out.println("\tn -> enter today's weight log to chosen member");
        System.out.println("\tc -> check member's weight record and BMI");
        System.out.println("\td -> select member to delete");
        System.out.println("\tq -> quit");
    }


    // EFFECTS: initializes family
    private void init() {
        input = new Scanner(System.in);
        family = new ArrayList<>();
        date = LocalDate.now();
        Member ethan = new Member("Ethan", 183);
        Member bryan = new Member("Bryan", 165);
        family.add(ethan);
        family.add(bryan);
        try {
            ethan.addWeightLog(75.0);
            bryan.addWeightLog(65.0);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }

    }


    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                addMember();
                break;
            case "n":
                addWeight();
                break;
            case "c":
                checkLog();
                break;
            case "d":
                deleteMember();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }


    // EFFECTS: Prints out the selected member's log
    private void checkLog() {

        String selection = "";
        String nameNow;
        Member selectedMem;

        while (!(selection.equals("b"))) {
            System.out.println("Which member's log would you like to check? Please enter their number.");
            selectedMem = selectMember();
            System.out.println("\nDate        Weight");
            printWeightLog(selectedMem);
            System.out.println("\n");
            System.out.println("\nEnter 'b' to return!");
            selection = input.next();
            selection = selection.toLowerCase();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a member to the family
    private void addMember() {

        String selection = "";  // force entry into loop
        String nameNow;
        int height;

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

    // MODIFIES: Member in Family class
    // EFFECTS: adds weight to the chosen member
    private void addWeight() {

        String selection = "";
        Double weight;
        Member selectedMem;


        while (!(selection.equals("b"))) {
            System.out.println("Which member's weight log should we update? \n Please select the number.");
            selectedMem = selectMember();
            System.out.println("What is your weight today? (KG)");
            weight = input.nextDouble();
            try {
                selectedMem.addWeightLog(weight);
            } catch (NegativeValueException e) {
                System.out.println("Cannot add negative weight!!!");
                break;
            }
            System.out.println(weight + " KG has been added to " + selectedMem.getName() + "'s log on " +  date);
            System.out.println("\nEnter 'b' to return!");
            selection = input.next();
            selection = selection.toLowerCase();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes member from the family
    private void deleteMember() {

        String selection = "";
        Member selectedMem;

        while (!(selection.equals("b"))) {
            System.out.println("Which member would you like to delete?");
            selectedMem = selectMember();
            family.remove(selectedMem);
            System.out.println(selectedMem.getName() + " has been removed :(");
            System.out.println("\n");
            System.out.println("\nEnter 'b' to return!");
            selection = input.next();
            selection = selection.toLowerCase();
        }
    }


    // EFFECTS: prints list of member on console
    public void printListOfMember() {
        int index = 1;
        for (Member m: family) {
            System.out.println(index + ". " + m.getName());
            index++;
        }
    }



    // EFFECTS: prints weight log of given member
    public void printWeightLog(Member mem) {
        for (Log log: mem.getWeightLogs()) {
            System.out.println(log.getDate() + "    " + log.getWeight() + " KG");
        }
    }


    // EFFECTS: prompts and returns selected member
    public Member selectMember() {
        int choice;
        Member selectedMem;
        printListOfMember();
        choice = input.nextInt();
        selectedMem = family.get(choice - 1);
        System.out.println("You have chosen: " + selectedMem.getName() + ".");
        return selectedMem;
    }

}
