package queststore.controllers;

import java.util.ArrayList;

import queststore.models.User;
import queststore.models.School;
import queststore.models.SchoolClass;
import queststore.models.Student;
import queststore.models.Mentor;

import queststore.views.UserInterface;

import queststore.exceptions.LoginInUseException;


public class MentorController {

    private UserInterface userInterface = new UserInterface();
    private Mentor user;
    private School school;

    public void startController(User user, School school) {

        this.user = (Mentor) user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {
            this.userInterface.printMentorMenu();
            userChoice = userInterface.inputs.getInput("What do you want to do: ");
            handleUserRequest(userChoice);

            school.save();
        }
    }

    private void handleUserRequest(String userChoice) {

        switch(userChoice) {
            case "1":
                addStudent();
                break;

            case "2":
                addQuest();
                break;

            case "3":
                addQuestCategory();
                break;

            case "4":
                updateQuest();
                break;

            case "5":
                markBoughtArtifactsAsUsed();
                break;

            case "6":
                runMentorStoreController();

            case "0":
                break;

            default:
                handleNoSuchCommand();
        }
    }

    private void addStudent() {
        String[] questions = {"Name: ", "Login: ", "Password: ", "Email: "};
        String[] expectedTypes = {"String", "String", "String", "String"};

        ArrayList<String> basicUserData = userInterface.inputs.getValidatedInputs(questions, expectedTypes);
        String name = basicUserData.get(0);
        String login = basicUserData.get(1);
        String password = basicUserData.get(2);
        String email = basicUserData.get(3);
        SchoolClass choosenClass = chooseProperClass();

        try {
            this.school.addUser(new Student(name, login, password, email, choosenClass));
        } catch (LoginInUseException e) {
            userInterface.println(e.getMessage());
        }

        this.userInterface.lockActualState();
    }

    private SchoolClass chooseProperClass() {
        ArrayList<SchoolClass> allClasses = this.school.getAllClasses();
        int userChoice;

        do {
            showAvailableClasses(allClasses);
            userChoice = getUserChoice() - 1;

        } while (userChoice > (allClasses.size() - 1) || userChoice < 0);

        return allClasses.get(userChoice);
    }

    private Integer getUserChoice() {
        String[] questions = {"Please choose class: "};
        String[] expectedTypes = {"integer"};
        ArrayList<String> userInput = userInterface.inputs.getValidatedInputs(questions, expectedTypes);

        return Integer.parseInt(userInput.get(0));
    }

    private void showAvailableClasses(ArrayList<SchoolClass> allClasses) {
        userInterface.println("");

        for (int i = 0; i < allClasses.size(); i++) {
            String index = Integer.toString(i+1);
            System.out.println(index + ". " + allClasses.get(i));
        }

        userInterface.println("");
    }

    private void addQuest() {
        String[] questions = {"Name: ", "Quest category: ", "Description: ", "Value: "};
        String[] types = {"string", "string", "string", "integer"};
        this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.lockActualState();
    }

    private void addQuestCategory() {
        String[] questions = {"Name: "};
        String[] types = {"string"};
        this.userInterface.inputs.getValidatedInputs(questions, types);


        this.userInterface.lockActualState();
    }

    private void updateQuest() {
        Integer id = this.getQuestId();
        String[] questions = {"new name: ", "new quest category: ", "new description: ", "new value: "};
        String[] types = {"string", "string", "string", "integer"};
        this.userInterface.inputs.getValidatedInputs(questions, types);

        this.userInterface.lockActualState();
    }

    private Integer getQuestId() {
        String[] question = {"Provide quest id: "};
        String[] type = {"integer"};

        Integer id = Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
        return id;
    }

    private void markBoughtArtifactsAsUsed() {
        String mockArtifactsList = "id - owner - name - status\n" +
                "1 - Maciej Nowak - Sanctuary - used\n" +
                "2 - Paweł Polakiewicz - Teleport - not used";
        this.userInterface.println(mockArtifactsList);
        String[] question = {"id: "};
        String[] type = {"integer"};
        if(this.userInterface.inputs.getValidatedInputs(question, type).get(0).equals("1")) {
            this.userInterface.println("Artifact mark as used!");
        } else {
            this.userInterface.println("Artifact already used!");
        }


        this.userInterface.lockActualState();
    }

    private void runMentorStoreController() {
        new MentorStoreController().startController(this.user, this.school);
    }

    private void handleNoSuchCommand() {
        userInterface.println("Wrong command!");

        this.userInterface.lockActualState();
    }

}
