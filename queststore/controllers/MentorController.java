package queststore.controllers;

import queststore.models.User;
import queststore.models.School;
import queststore.views.UserInterface;

public class MentorController {
    private UserInterface userInterface = new UserInterface();
    private User user;
    private School school;

    public void startController(User user, School school) {
        this.user = user;
        this.school = school;
        String userChoice = "";

        while (!userChoice.equals("0")) {
            this.userInterface.printMentorMenu();
            userChoice = userInterface.getInput("What do you want to do");
            handleUserRequest(userChoice);
            userInterface.lockActualState();
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

            default:
                handleNoSuchCommand();

        }
    }

    private void addStudent() {

    }

    private void addQuest() {

    }

    private void addQuestCategory() {

    }

    private void updateQuest() {

    }

    private void markBoughtArtifactsAsUsed() {

    }

    private void runMentorStoreController(){

    }

    private void handleNoSuchCommand() {
        userInterface.print("Wrong command!");
    }

}
