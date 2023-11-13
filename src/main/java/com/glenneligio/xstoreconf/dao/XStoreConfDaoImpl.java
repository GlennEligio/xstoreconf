package com.glenneligio.xstoreconf.dao;

import com.glenneligio.xstoreconf.commands.XStoreConfDiff;
import com.glenneligio.xstoreconf.model.XStoreConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class XStoreConfDaoImpl implements XStoreConfDao {

    private static Logger logger = LoggerFactory.getLogger(XStoreConfDaoImpl.class);
    Class<?> instance = Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    private String hostname, username, password, name, table;
    private int port;

    public XStoreConfDaoImpl(String hostname, String username, String password, String name, int port, String table) throws ClassNotFoundException {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.name = name;
        this.port = port;
        this.table = table;
    }

    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":"+port+"/"+name, username, password);
    }

    @Override
    public List<XStoreConf> getAll() {
        List<XStoreConf> result = new ArrayList<>();
        try {
            createConnection();
            String fetchEntries = "SELECT x.STOREENT_ID , x.NAME , x.VALUE , x.UX_VISIBILITY , x.LANGUAGE_ID  FROM " + table + " x ORDER BY x.STOREENT_ID, x.NAME";
            logger.info(fetchEntries);
            preparedStatement = connection.prepareStatement(fetchEntries);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int storeEntId = resultSet.getInt("STOREENT_ID");
                String name = resultSet.getString("NAME");
                String value = resultSet.getString("VALUE");
                boolean uxVisibility = resultSet.getBoolean("UX_VISIBILITY");
                int langId = resultSet.getInt("LANGUAGE_ID");
                result.add(new XStoreConf(storeEntId, name, value, uxVisibility, langId));
            }

            connection.close();
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            logger.info("Something went wrong.");
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }
}
