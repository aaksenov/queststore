package com.codecool.krk.lucidmotors.queststore;

import java.sql.SQLException;

import com.codecool.krk.lucidmotors.queststore.controllers.LoginController;
import com.codecool.krk.lucidmotors.queststore.models.School;
import com.codecool.krk.lucidmotors.queststore.exceptions.WrongPasswordException;
import com.codecool.krk.lucidmotors.queststore.dao.DatabaseConnection;

class App {

    public static void main(String[] args) {

        try {
            School school = new School("Codecool");
            new LoginController(school).start();

        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());

        } catch (SQLException ex) {
            System.out.println("Database connection failed!");

        } finally {

            try {
                DatabaseConnection.closeConnection();
            } catch (SQLException ex) {
                System.out.println("Database connection failed!");
            }

        }

    }
}