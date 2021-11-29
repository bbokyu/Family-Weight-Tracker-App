package ui;

import model.Log;
import model.Member;
import model.exceptions.NegativeValueException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.internal.xjc.reader.Ring.add;
import static javax.swing.JOptionPane.showMessageDialog;

// Main class for UI
// Navigate regions using shortcut Command + Option + Period (MAC), Ctrl + Alt + Period (PC)
public class GUI implements ActionListener, ListSelectionListener {

    private Panel firstPanel;
    private Panel secondPanel;

    // Swing
    private JButton addPersonButton;
    private JButton removePersonButton;
    private JButton addWeightButton;
    private JButton checkLogButton;
    private JButton saveButton;
    private JButton loadButton;

    private JFrame mainMenu;

    private JLabel title;

    // Software
    protected ArrayList<Member> family;

    // add member
    private Member member;
    private JFrame addMemberFrame;
    private JButton submitButton;
    private JTextField name;
    private JTextField height;
    private JLabel nameTitle;
    private JLabel heightTitle;
    private JLabel mainTitle;

    // remove member
    private JLabel subTitle;
    private JButton deleteButton;
    private JList<String> namesOfMembers;
    private JFrame deleteMemberFrame;
    private JScrollPane listScroller;
    private DefaultListModel<String> listModel;

    // add weight log
    private JFrame addWeightLogFrame;
    private JTextField weightField;

    // Json
    private static final String JSON_STORE = "./data/family.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // GUI constructor that initializes main menu and JSON
    public GUI()  {


        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        family = new ArrayList<>();

        // example members
        Member ethan = new Member("Ethan", 183);
        Member bryan = new Member("Bryan", 165);
        family.add(ethan);
        family.add(bryan);

        initMain();
        addMenuButtons();
        addToFrameAndPanel();

    }

    // MODIFIES: this
    // EFFECTS: creates main menu frame and first panel and second panel
    private void initMain() {

        mainMenu = new MyFrame();
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setSize(500,700);
        mainMenu.setLayout(null);

        // first panel
        title = new JLabel("Family Weight Tracker");
        firstPanel = new Panel();
        firstPanel.setLayout(null);
        firstPanel.setBackground(Color.decode("#F8EDEB"));
        firstPanel.setBounds(0,0, 500,75);
        firstPanel.add(title);
        title.setFont(new Font("Roboto", Font.PLAIN, 25));
        title.setBounds(120,10,300,50);

        // second panel
        secondPanel = new Panel();
        secondPanel.setBounds(0, 50,500,700);
        secondPanel.setBackground(Color.decode("#FEC89A"));
        secondPanel.setLayout(null);
    }

    // EFFECTS: adds panels and buttons to corresponding component
    private void addToFrameAndPanel() {
        mainMenu.add(firstPanel);
        mainMenu.add(secondPanel);
        secondPanel.add(addPersonButton);
        secondPanel.add(removePersonButton);
        secondPanel.add(addWeightButton);
        secondPanel.add(checkLogButton);
        secondPanel.add(saveButton);
        secondPanel.add(loadButton);
    }

    // EFFECTS: creates buttons for the main menu
    private void addMenuButtons() {
        addPersonButton();
        removePersonButton();
        addWeightButton();
        checkLogButton();
        saveButton();
        loadButton();
    }

    // EFFECTS: creates the load button
    private void loadButton() {
        loadButton = new JButton();
        loadButton.addActionListener(this);
        loadButton.setText("Load Family");
        loadButton.setBounds(150, 425, 200, 50);
    }

    // EFFECTS: creates the save button
    private void saveButton() {
        saveButton = new JButton();
        saveButton.addActionListener(this);
        saveButton.setText("Save Family");
        saveButton.setBounds(150, 350, 200, 50);
    }

    // EFFECTS: creates the Check Log button
    private void checkLogButton() {
        checkLogButton = new JButton();
        checkLogButton.addActionListener(this);
        checkLogButton.setText("Check Member's Log");
        checkLogButton.setBounds(150, 275, 200, 50);
    }

    // EFFECTS: creates the add weight button
    private void addWeightButton() {
        addWeightButton = new JButton();
        addWeightButton.addActionListener(this);
        addWeightButton.setText("Add Weight Log");
        addWeightButton.setBounds(150, 200, 200, 50);
    }

    // EFFECTS: creates the remove person button
    private void removePersonButton() {
        removePersonButton = new JButton();
        removePersonButton.addActionListener(this);
        removePersonButton.setText("Remove Family Member");
        removePersonButton.setBounds(150, 125, 200, 50);
    }

    // EFFECTS: creates the add person button
    private void addPersonButton() {
        addPersonButton = new JButton();
        addPersonButton.addActionListener(this);
        addPersonButton.setText("Add Family Member");
        addPersonButton.setBounds(150, 50, 200, 50);
    }


    // main class that instantiates GUI
    public static void main(String[] args) {
        new GUI();
    }

    //region Add Member
    // creates UI for adding member
    private void addMember() {

        addMemberFrame = new JFrame();
        mainTitle = new JLabel("Create Member");
        mainTitle.setBounds(200,50,100,50);

        addMemberFrame.setTitle("Create and add member");
        addMemberFrame.setSize(500,500);
        addMemberFrame.setResizable(false);
        addMemberFrame.setLayout(null);
        addMemberFrame.setVisible(true);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new AddMemberListener());
        submitButton.setBounds(200,350,100,50);

        addNameTextField();
        addHeightTextField();

        addMemberFrame.add(mainTitle);
        addMemberFrame.add(nameTitle);
        addMemberFrame.add(heightTitle);
        addMemberFrame.add(submitButton);
        addMemberFrame.add(name);
        addMemberFrame.add(height);

    }

    private void addHeightTextField() {
        heightTitle = new JLabel("Height(CM): ");
        heightTitle.setBounds(70,200,100,50);
        height = new JTextField();
        height.setBounds(150, 200,200,50);

    }

    private void addNameTextField() {
        nameTitle = new JLabel("Name: ");
        nameTitle.setBounds(100,100,100,50);
        name = new JTextField();
        name.setBounds(150, 100,200,50);
    }


    class AddMemberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            family.add(createMember(name.getText(), height.getText()));
            addMemberFrame.setVisible(false);
        }
    }

    private Member createMember(String text, String heightText) {
        int temp = Integer.parseInt(heightText);
        showMessageDialog(null, text + " has been added to the family!");
        return member = new Member(text, temp);
    }

    //endregion


    //region Remove Member
    // MODIFIES: this
    // EFFECTS: creates UI for removing member
    public void removeMember() {

        deleteMemberFrame = new JFrame();

        subTitle = new JLabel("Select Member to delete :(");
        subTitle.setBounds(150,50,200,50);

        deleteMemberFrame.setTitle("Create and add member");
        deleteMemberFrame.setSize(500,500);
        deleteMemberFrame.setResizable(false);
        deleteMemberFrame.setLayout(null);
        deleteMemberFrame.setVisible(true);


        List<String> listOfNames = getFamilyNames();
        listModel = new DefaultListModel<>();
        for (String str : listOfNames) {
            listModel.addElement(str);
        }

        JScrollPane listScroller = initList();


        deleteButton = new JButton("Delete Member");
        deleteButton.setBounds(175,350,125,50);
        deleteButton.addActionListener(new DeleteMemberListener());


        deleteMemberFrame.add(deleteButton);
        deleteMemberFrame.add(listScroller);
        deleteMemberFrame.add(subTitle);

    }

    private JScrollPane initList() {
        namesOfMembers = new JList<>(listModel); //data has type Object[]
        namesOfMembers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        namesOfMembers.setLayoutOrientation(JList.VERTICAL);
        namesOfMembers.setVisibleRowCount(4);
        namesOfMembers.addListSelectionListener(this);
        namesOfMembers.setBounds(150,100,200,150);
        listScroller = new JScrollPane(namesOfMembers);
        listScroller.setBounds(175,100,125,150);
        return listScroller;
    }

    // part of code modelled from:
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#ListDemo
    // action listener class for delete member
    class DeleteMemberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = namesOfMembers.getSelectedIndex();
            listModel.remove(index);
            Member tempMember = family.get(index);
            family.remove(index);
            showMessageDialog(null, tempMember.getName() + " has been removed from the family");
            deleteMemberFrame.setVisible(false);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                deleteButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                namesOfMembers.setSelectedIndex(index);
                namesOfMembers.ensureIndexIsVisible(index);
            }
        }
    }

    //endregion



    //region Add Weight Log
    // MODIFIES: this
    // EFFECTS: creates UI for adding weight to member
    private void addWeightToMember() {

        addWeightLogFrame = new JFrame();

        subTitle = new JLabel("Select Member and add today's weight:");
        subTitle.setBounds(100,50,250,50);

        addWeightLogFrame.setTitle("Create and add member");
        addWeightLogFrame.setSize(500,500);
        addWeightLogFrame.setResizable(false);
        addWeightLogFrame.setLayout(null);
        addWeightLogFrame.setVisible(true);

        List<String> listOfNames = getFamilyNames();
        listModel = new DefaultListModel<>();
        for (String str : listOfNames) {
            listModel.addElement(str);
        }

        JScrollPane listScroller = initList();


        JButton addWeightLogButton = new JButton("Add Weight");
        addWeightLogButton.addActionListener(this);
        addWeightLogButton.setBounds(175,350,125,50);
        addWeightLogButton.addActionListener(new AddWeightLogListener());


        weightField = new JTextField();
        weightField.setBounds(200, 300,100,25);
        JLabel weightFieldLabel = new JLabel("Enter Weight (KG): ");
        weightFieldLabel.setBounds(75,300,159,25);

        addToWeightLogFrame(listScroller, addWeightLogButton, weightFieldLabel);

    }

    // EFFECTS: add components to add weight log frame
    private void addToWeightLogFrame(JScrollPane listScroller, JButton addWeightLogButton, JLabel weightFieldLabel) {
        addWeightLogFrame.add(weightFieldLabel);
        addWeightLogFrame.add(addWeightLogButton);
        addWeightLogFrame.add(listScroller);
        addWeightLogFrame.add(subTitle);
        addWeightLogFrame.add(weightField);
    }

    // part of code modelled from:
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#ListDemo
    //  action listener for
    class AddWeightLogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = namesOfMembers.getSelectedIndex();

            Double weightFieldNum = Double.valueOf(weightField.getText());

            Member tempMem = family.get(index);
            try {
                tempMem.addWeightLog(weightFieldNum);
            } catch (NegativeValueException ex) {
                ex.printStackTrace();
            }

            showMessageDialog(null, "Added Weight Log of " + weightFieldNum + " KG to "
                    + tempMem.getName() + "!");

            int size = listModel.getSize();

            if (size == 0) {
                addWeightButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                namesOfMembers.setSelectedIndex(index);
                namesOfMembers.ensureIndexIsVisible(index);
            }
        }

    }
    //endregion


    //region Check Log
    // MODIFIES: this
    // EFFECTS: creates UI for checking a members weight log
    private void checkLog() {

        JFrame checkLogFrame = new JFrame();

        subTitle = new JLabel("Select Member View Log");
        subTitle.setBounds(150,50,200,50);

        checkLogFrame.setTitle("Create and add member");
        checkLogFrame.setSize(500,500);
        checkLogFrame.setResizable(false);
        checkLogFrame.setLayout(null);
        checkLogFrame.setVisible(true);

        List<String> listOfNames = getFamilyNames();
        listModel = new DefaultListModel<String>();
        for (String str : listOfNames) {
            listModel.addElement(str);
        }

        JScrollPane listScroller = initList();

        deleteButton = new JButton("Check Log");
        deleteButton.setBounds(175,350,125,50);
        deleteButton.addActionListener(new CheckLogListener());

        checkLogFrame.add(deleteButton);
        checkLogFrame.add(listScroller);
        checkLogFrame.add(subTitle);
    }

    // part of code modelled from:
    // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html#ListDemo
    // action listener class check log
    class CheckLogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            int index = namesOfMembers.getSelectedIndex();
            Member tempMem = family.get(index);
            viewMemberLog(tempMem);
            int size = listModel.getSize();

            if (size == 0) {
                checkLogButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                namesOfMembers.setSelectedIndex(index);
                namesOfMembers.ensureIndexIsVisible(index);
            }
        }
    }







    // EFFECTS: creates table UI for given member
    private void viewMemberLog(Member m) {

        JFrame memberLogFrame = new JFrame();

        subTitle = new JLabel("Select Member View Log");
        subTitle.setBounds(150,50,200,50);

        memberLogFrame.setTitle("Weight Log");
        memberLogFrame.setSize(400,450);
        memberLogFrame.setResizable(false);
        memberLogFrame.setLayout(null);
        memberLogFrame.setVisible(true);

        String[] columnNames = {
                "Date",
                "Weight (KG)"};

        Object[][] data = createTableData(m);

        final JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(75,40,250,300);

        memberLogFrame.add(scrollPane);
    }

    // EFFECTS: creates 2D array of data from given member
    private Object[][] createTableData(Member m) {
        List<List<String>> arr = new ArrayList<>();

        for (Log l : m.getWeightLogs()) {
            List<String> row = new ArrayList<>();
            row.add(l.getDate());
            row.add(l.getWeight().toString());
            arr.add(row);
        }

        Object[][] data = new Object[arr.size()][2];
        int index = 0;
        for (List<String> stuff : arr) {
            data[index][0] = stuff.get(0);
            data[index][1] = stuff.get(1);
            index++;
        }
        return data;
    }
    //endregion

    //region Load Family
    private void loadFamily() {
        try {
            ArrayList<Member> fam = jsonReader.read();
            this.family = fam;
            showMessageDialog(null, "Loaded family from " + JSON_STORE);
        } catch (IOException e) {
            showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
        }
    }
    //endregion

    //region Save Family
    private void saveFamily() {
        try {
            jsonWriter.open();
            jsonWriter.write(family);
            jsonWriter.close();
            showMessageDialog(null, "Saved all members of family to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }
    //endregion


    // EFFECTS: returns an arraylist of all members' name in the family
    public ArrayList<String> getFamilyNames() {
        ArrayList<String> temp = new ArrayList<>();
        for (Member m: family) {
            temp.add(m.getName());
        }
        return temp;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPersonButton) {
            addMember();
        }
        if (e.getSource() == removePersonButton) {
            removeMember();
        }
        if (e.getSource() == addWeightButton) {
            addWeightToMember();
        }
        if (e.getSource() == checkLogButton) {
            checkLog();
        }
        if (e.getSource() == saveButton) {
            saveFamily();
        }
        if (e.getSource() == loadButton) {
            loadFamily();
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


}



