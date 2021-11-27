package ui;

import model.Member;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

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
    private JList namesOfMembers;
    private JFrame deleteMemberFrame;
    private JScrollPane listScroller;
    private DefaultListModel listModel;

    public GUI()  {

        family = new ArrayList<>();

        Member ethan = new Member("Ethan", 183);
        Member bryan = new Member("Bryan", 165);
        family.add(ethan);
        family.add(bryan);


        initMain();
        addMenuButtons();
        addToFrameAndPanel();

    }

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

    private void addMenuButtons() {
        addPersonButton();
        removePersonButton();
        addWeightButton();
        checkLogButton();
        saveButton();
        loadButton();
    }

    private void loadButton() {
        loadButton = new JButton();
        loadButton.addActionListener(this);
        loadButton.setText("Load Family");
        loadButton.setBounds(150, 425, 200, 50);
    }

    private void saveButton() {
        saveButton = new JButton();
        saveButton.addActionListener(this);
        saveButton.setText("Save Family");
        saveButton.setBounds(150, 350, 200, 50);
    }

    private void checkLogButton() {
        checkLogButton = new JButton();
        checkLogButton.addActionListener(this);
        checkLogButton.setText("Check Member's Log");
        checkLogButton.setBounds(150, 275, 200, 50);
    }

    private void addWeightButton() {
        addWeightButton = new JButton();
        addWeightButton.addActionListener(this);
        addWeightButton.setText("Add Weight Log");
        addWeightButton.setBounds(150, 200, 200, 50);
    }

    private void removePersonButton() {
        removePersonButton = new JButton();
        removePersonButton.addActionListener(this);
        removePersonButton.setText("Remove Family Member");
        removePersonButton.setBounds(150, 125, 200, 50);
    }

    private void addPersonButton() {
        addPersonButton = new JButton();
        addPersonButton.addActionListener(this);
        addPersonButton.setText("Add Family Member");
        addPersonButton.setBounds(150, 50, 200, 50);
    }


    public static void main(String[] args) {
        new GUI();
    }

    // creates new member and adds it to family _________________________________________________________

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

    public ArrayList<String> getFamilyNames() {
        ArrayList<String> temp = new ArrayList<>();
        for (Member m: family) {
            temp.add(m.getName());
        }
        return temp;
    }


    // ______________________ remove member

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
        listModel = new DefaultListModel();
        for (String str : listOfNames) {
            listModel.addElement(str);
        }

        JScrollPane listScroller = initList();


        deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(this);
        deleteButton.setBounds(175,350,125,50);
        deleteButton.addActionListener(new DeleteMemberListener());


        deleteMemberFrame.add(deleteButton);
        deleteMemberFrame.add(listScroller);
        deleteMemberFrame.add(subTitle);

    }

    private JScrollPane initList() {
        namesOfMembers = new JList(listModel); //data has type Object[]
        namesOfMembers.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        namesOfMembers.setLayoutOrientation(JList.VERTICAL);
        namesOfMembers.setVisibleRowCount(4);
        namesOfMembers.addListSelectionListener(this);
        namesOfMembers.setBounds(150,100,200,200);
        listScroller = new JScrollPane(namesOfMembers);
        listScroller.setBounds(175,100,125,200);
        return listScroller;
    }




    private Member createMember(String text, String heightText) {
        int temp = Integer.parseInt(heightText);
        showMessageDialog(null, text + " has been added to the family!");
        return member = new Member(text, temp);
    }

    //_________Add weight log


    private void addWeightToMember() {

    }





    class AddMemberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            family.add(createMember(name.getText(), height.getText()));
            addMemberFrame.setVisible(false);
        }

    }

    class DeleteMemberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = namesOfMembers.getSelectedIndex();
            listModel.remove(index);
            System.out.println(index);
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

    }



    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (namesOfMembers.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                deleteButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                deleteButton.setEnabled(true);
            }
        }
    }


}



