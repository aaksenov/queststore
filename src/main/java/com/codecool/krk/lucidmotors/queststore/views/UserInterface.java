package com.codecool.krk.lucidmotors.queststore.views;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import com.codecool.krk.lucidmotors.queststore.dao.FileLoader;
import com.codecool.krk.lucidmotors.queststore.models.BoughtArtifact;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;
import com.codecool.krk.lucidmotors.queststore.models.Student;

public class UserInterface {
    private Scanner in = new Scanner(System.in);
    private FileLoader dataLoader = new FileLoader();
    public Inputs inputs = new Inputs();

    public void println(String text) {
        System.out.println(text);
    }

    public void newLine() {
        System.out.println();
    }

    public void clearWindow() {
        for(int i = 0; i < 50; i ++) {
            System.out.println();
        }
    }

    public void lockActualState() {
        System.out.println();
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    public void printMentorMenu() {
        String fileName = "csv/mentorMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printMentorStoreMenu() {
        String fileName = "csv/mentorStoreMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printStudentMenu() {
        String fileName = "csv/studentMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printStudentStoreMenu() {
        String fileName = "csv/studentStoreMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printManagerMenu() {
        String fileName = "csv/managerMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printExperienceLevelsMenu() {
        String fileName = "csv/experienceLevelsMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    private void printMenu(List<String> data) {
        clearWindow();
        printAllRecordsInMenuStyle(data);
    }

    private void printAllRecordsInMenuStyle(List<String> data) {
        String title = data.get(0);

        System.out.println(title);

        for(int i = 1; i < data.size(); i++) {
            String index = (Integer.toString(i % (data.size() - 1)));
            String row = data.get(i);

            System.out.println("\t" + "(" + index + ") " + row);
        }

        System.out.println();
    }

    public void printStoreArtifacts(ArrayList<ShopArtifact> allArtifacts) {
        for(ShopArtifact shopArtifact : allArtifacts) {
            System.out.println(shopArtifact);
        }

    }

    public void printBoughtArtifacts(Student student, ArrayList<BoughtArtifact> studentArtifacts) {
        this.println("Owned artifacts:");
        for(BoughtArtifact boughtArtifact : studentArtifacts) {
            System.out.println(boughtArtifact);
        }
    }


}
