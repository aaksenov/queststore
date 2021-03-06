package com.codecool.krk.lucidmotors.queststore.dao;

import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.models.ArtifactCategory;
import com.codecool.krk.lucidmotors.queststore.models.ShopArtifact;

import java.math.BigInteger;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShopArtifactDao {

    private static ShopArtifactDao dao = null;
    private final Connection connection;
    private PreparedStatement stmt = null;
    private ArtifactCategoryDao artifactCategoryDao = ArtifactCategoryDao.getDao();

    private ShopArtifactDao() throws DaoException {

        this.connection = DatabaseConnection.getConnection();
    }

    public static ShopArtifactDao getDao() throws DaoException {

        if (dao == null) {

            synchronized (ShopArtifactDao.class) {

                if (dao == null) {
                    dao = new ShopArtifactDao();
                }
            }
        }

        return dao;
    }

    public ShopArtifact getArtifact(Integer id) throws DaoException {

        ShopArtifact shopArtifact = null;
        String sqlQuery = "SELECT * FROM shop_artifacts WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                BigInteger price = new BigInteger(result.getString("price"));
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");

                ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(categoryId);

                shopArtifact = new ShopArtifact(name, price, artifactCategory, description, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return shopArtifact;
    }

    public void updateArtifact(ShopArtifact shopArtifact) throws DaoException {

        String name = shopArtifact.getName();
        BigInteger price = shopArtifact.getPrice();
        Integer categoryId = shopArtifact.getArtifactCategory().getId();
        String description = shopArtifact.getDescription();
        Integer artifactId = shopArtifact.getId();

        String sqlQuery = "UPDATE shop_artifacts "
                + "SET name = ?, price = ?, category_id = ?, description = ? "
                + "WHERE id = ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, price.toString());
            stmt.setInt(3, categoryId);
            stmt.setString(4, description);
            stmt.setInt(5, artifactId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public List<ShopArtifact> getAllArtifacts() throws DaoException {

        List<ShopArtifact> shopArtifacts = new ArrayList<>();
        String sqlQuery = "SELECT * FROM shop_artifacts;";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                BigInteger price = new BigInteger(result.getString("price"));
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer id = result.getInt("id");

                ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(categoryId);

                ShopArtifact shopArtifact = new ShopArtifact(name, price, artifactCategory, description, id);
                shopArtifacts.add(shopArtifact);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return shopArtifacts;
    }

    public void save(ShopArtifact shopArtifact) throws DaoException {

        String name = shopArtifact.getName();
        BigInteger price = shopArtifact.getPrice();
        Integer categoryId = shopArtifact.getArtifactCategory().getId();
        String description = shopArtifact.getDescription();

        String sqlQuery = "INSERT INTO shop_artifacts "
                + "(name, price, category_id, description) "
                + "VALUES (?, ?, ?, ?);";

        try {
            stmt = connection.prepareStatement(sqlQuery);

            stmt.setString(1, name);
            stmt.setString(2, price.toString());
            stmt.setInt(3, categoryId);
            stmt.setString(4, description);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

    }

    public ShopArtifact getArtifactByName(String name) throws DaoException {
        String sqlQuery = "SELECT * FROM shop_artifacts WHERE name LIKE ?;";

        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String foundName = result.getString("name");
                BigInteger price = new BigInteger(result.getString("price"));
                Integer categoryId = result.getInt("category_id");
                String description = result.getString("description");
                Integer id = result.getInt("id");

                ArtifactCategory artifactCategory = artifactCategoryDao.getArtifactCategory(categoryId);

                return new ShopArtifact(foundName, price, artifactCategory, description, id);
            }

            result.close();
            stmt.close();
        } catch (SQLException e) {
            throw new DaoException(this.getClass().getName() + " class caused a problem!");
        }

        return null;
    }
}
