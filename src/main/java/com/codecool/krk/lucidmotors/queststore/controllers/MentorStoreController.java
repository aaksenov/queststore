package com.codecool.krk.lucidmotors.queststore.controllers;

import com.codecool.krk.lucidmotors.queststore.dao.ArtifactCategoryDao;
import com.codecool.krk.lucidmotors.queststore.dao.ShopArtifactDao;
import com.codecool.krk.lucidmotors.queststore.enums.MentorStoreMenuOptions;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.exceptions.NoSuchIdException;
import com.codecool.krk.lucidmotors.queststore.views.UserInterface;
import com.codecool.krk.lucidmotors.queststore.interfaces.UserController;
import com.codecool.krk.lucidmotors.queststore.models.*;

import java.util.ArrayList;



public class MentorStoreController extends AbstractUserController<Mentor> {

    private final ShopArtifactController shopArtifactController = new ShopArtifactController();


    protected void handleUserRequest(String userChoice) throws DaoException {

        MentorStoreMenuOptions chosenOption = getEnumValue(userChoice);

        switch (chosenOption) {

            case SHOW_AVAILABLE_ARTIFACTS:
                shopArtifactController.showAvailableArtifacts();
                break;

            case ADD_ARTIFACT:
                addArtifact();
                break;

            case UPDATE_ARTIFACT:
                updateArtifact();
                break;

            case ADD_ARTIFACT_CATEGORY:
                addArtifactCategory();
                break;

            case EXIT:
                break;

            case DEFAULT:
                handleNoSuchCommand();
        }
    }

    private MentorStoreMenuOptions getEnumValue(String userChoice) {
        MentorStoreMenuOptions chosenOption;

        try {
            chosenOption = MentorStoreMenuOptions.values()[Integer.parseInt(userChoice)];
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            chosenOption = MentorStoreMenuOptions.DEFAULT;
        }

        return chosenOption;
    }

    protected void showMenu() {
        userInterface.printMentorStoreMenu();
    }

    private void addArtifact() throws DaoException {

        String[] questions = {"Name: ", "Description: ", "Price: "};
        String[] types = {"string", "string", "integer"};

        ArrayList<String> givenValues = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer artifactCategoryId = this.getArtifactCategoryId();
        ArtifactCategory artifactCategory = new ArtifactCategoryDao().getArtifactCategory(artifactCategoryId);
        String name = givenValues.get(0);
        String description = givenValues.get(1);
        Integer price = Integer.parseInt(givenValues.get(2));

        try {
            ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description);
            shopArtifact.save();
            this.userInterface.println("Artifact created.");
        } catch (IllegalArgumentException e) {
            this.userInterface.println(e.getMessage());
        }

        this.userInterface.pause();
    }

    private Integer getArtifactCategoryId() throws DaoException {
        this.printAllArtifactsCategories();
        String[] questions = {"Artifact category id: "};
        String[] types = {"integer"};
        return Integer.parseInt(this.userInterface.inputs.getValidatedInputs(questions, types)
                      .get(0));
    }

    private void printAllArtifactsCategories() throws DaoException {

        ArrayList<ArtifactCategory> artifactCategories = new ArtifactCategoryDao().getAllArtifactCategories();
        this.userInterface.printArtifactsCategories(artifactCategories);
    }

    private void updateArtifact() throws DaoException {
        ShopArtifactDao shopArtifactDao = new ShopArtifactDao();

        this.userInterface.print(shopArtifactDao.getAllArtifacts().iterator());
        ShopArtifact updatedArtifact = shopArtifactDao.getArtifact(this.getArtifactId());

        try {
            if (updatedArtifact != null) {
                updatedArtifact = this.getUpdatedArtifact(updatedArtifact);
                shopArtifactDao.updateArtifact(updatedArtifact);
            } else {
                this.userInterface.println("Update failure: No artifact of such id");
            }
        } catch (NoSuchIdException e) {
            this.userInterface.println("There is no category of such id!");
        }

        this.userInterface.pause();
    }

    private ShopArtifact getUpdatedArtifact(ShopArtifact updatedArtifact) throws DaoException, NoSuchIdException {
        String[] questions = {"Name: ", "Description: ", "Price: "};
        String[] types = {"string", "string", "integer"};
        ArrayList<String> givenValues = this.userInterface.inputs.getValidatedInputs(questions, types);

        Integer artifactCategoryId = this.getArtifactCategoryId();
        ArtifactCategory artifactCategory = new ArtifactCategoryDao().getArtifactCategory(artifactCategoryId);
        if (artifactCategory == null) {
            throw new NoSuchIdException();
        }

        updatedArtifact.setName(givenValues.get(0));
        updatedArtifact.setDescription(givenValues.get(1));
        updatedArtifact.setPrice(Integer.parseInt(givenValues.get(2)));
        updatedArtifact.setArtifactCategory(artifactCategory);
        return updatedArtifact;
    }

    private Integer getArtifactId() {

        String[] question = {"Provide artifact id: "};
        String[] type = {"integer"};

        return Integer.parseInt(userInterface.inputs.getValidatedInputs(question, type).get(0));
    }

    private void addArtifactCategory() throws DaoException {

        String[] question = {"Provide new artifact category name: "};
        String[] type = {"string"};

        String name = userInterface.inputs.getValidatedInputs(question, type).get(0);

        ArtifactCategory artifactCategory = new ArtifactCategory(name);
        new ArtifactCategoryDao().save(artifactCategory);

        this.userInterface.pause();
    }
}
