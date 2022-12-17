package me.hdcookie.database;

import java.sql.*;
import java.util.HashMap;

public class Database {

    private Connection connection;
    HashMap<String, String> config = Config.configHashMap;
    private ConfigManager configManager;

    public Database(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void connect() throws SQLException {

        final String PASSWORD = configManager.getConfigValue("password").toString().replace("\"", "");
        final String USERNAME = configManager.getConfigValue("username").toString().replace("\"", "");
        final String DATABASE = configManager.getConfigValue("database").toString().replace("\"", "");
        final int PORT = Integer.parseInt(configManager.getConfigValue("port").toString());
        final String HOST = configManager.getConfigValue("host").toString().replace("\"", "");

        connection = DriverManager.getConnection("jdbc:mysql://"
                + HOST + ":" + PORT + "/" + DATABASE +
                "?useSSL=false&autoReconnect=true", USERNAME, PASSWORD);
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void addServerSettings(String serverID) throws SQLException {

        if (isConnected()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO settings (GuildID, CountingID, RadioID, GuildMOTD, GuessID, MakeSentenceID, FinishedSentenceID, TruthOrDareID)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, serverID);
            ps.setString(2, "countID");
            ps.setString(3, "radioID"); //RadioID
            ps.setString(4, "0"); //MOTD
            ps.setString(5, "guessID");
            ps.setString(6, "makeSentance");
            ps.setString(7, "finishedSentance");
            ps.setString(8, "truthOrDare");
            ps.executeUpdate();
        }
    }

    /*
    Add Game Data function creates default values for the games table with the server.  Should only be run once per server, otherwise it will reset data

     */
    public void addGameData(String serverID) throws SQLException {

        if (isConnected()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO games (GuildID, Counting, Guess, CurrentSentence)  VALUES (?, ?, ?, ?)");
            ps.setString(1, serverID);
            ps.setInt(2, 1);
            ps.setInt(3, 0);
            ps.setString(4, "");
            ps.executeUpdate();
        }
    }


    //Getters for channel ID's -----------------------------------------------------------------------------------------------
    public String getCountingID(String serverID) throws SQLException {


        PreparedStatement ps = connection.prepareStatement("SELECT CountingID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("CountingID");
    }

    public String getGuessingID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT GuessID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("GuessID");
    }

    public String getMakeSentenceID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT MakeSentenceID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("MakeSentenceID");
    }

    public String getFinishedSentencesID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT FinishedSentenceID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("FinishedSentenceID");
    }

    public String getRadioID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT RadioID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        try {
            return rs.getString("RadioID");
        } catch (SQLException e) {
            return "0";
        }
    }

    public String getTruthOrDareID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT TruthOrDareID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        try {
            return rs.getString("TruthOrDareID");
        } catch (SQLException e) {
            return "0";
        }
    }

//Setters for channel ID's -----------------------------------------------------------------------------------------------

    public void setRadio(String channelID, String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET RadioID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setCountingID(String channelID, String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET CountingID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setGuessingID(String channelID, String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET GuessID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setMakeSentenceID(String channelID, String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET MakeSentenceID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setFinishedSentencesID(String channelID, String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET FinishedSentenceID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setTruthOrDareID(String channelID, String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET TruthOrDareID = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    //Getters for game data -----------------------------------------------------------------------------------------------

    public int getCounting(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT Counting FROM games WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Counting");
    }

    public int getGuessing(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT Guess FROM games WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Guess");
    }

    public String getCurrentSentence(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT CurrentSentence FROM games WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("CurrentSentence");
    }

    //Setters for game data -----------------------------------------------------------------------------------------------

    public void setCounting(String serverID, int counting) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE games SET Counting = ? WHERE GuildID = ?");
        ps.setInt(1, counting);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setGuessing(String serverID, int guessing) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE games SET Guess = ? WHERE GuildID = ?");
        ps.setInt(1, guessing);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setCurrentSentence(String serverID, String sentence) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE games SET CurrentSentence = ? WHERE GuildID = ?");
        ps.setString(1, sentence);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    //Getters for Truth or Dare data -----------------------------------------------------------------------------------------------

    public String getTruthOrDare(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT TruthOrDare FROM tod WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("TruthOrDare");
    }

    public String getAskerID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT AskerID FROM tod WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("AskerID");
    }

    public String getAnswererID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT AnswererID FROM tod WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("AnswererID");
    }

    public String getQuestion(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT Question FROM tod WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Question");
    }

    //Setters for Truth or Dare data -----------------------------------------------------------------------------------------------

    public void addTruthOrDare(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO tod (GuildID, AskerID, AnswererID, Question, TruthOrDare) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, serverID);
        ps.setString(2, "0");
        ps.setString(3, "0");
        ps.setString(4, "none");
        ps.setString(5, "none");
        ps.executeUpdate();
    }

    //statement checking if the server is already in the tod table

    public boolean todExists(String serverID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM tod WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }


    public void setTruthOrDare(String serverID, String truthOrDare) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE tod SET TruthOrDare = ? WHERE GuildID = ?");
        ps.setString(1, truthOrDare);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setAskerID(String serverID, String askerID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE tod SET AskerID = ? WHERE GuildID = ?");
        ps.setString(1, askerID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setAnswererID(String serverID, String answererID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE tod SET AnswererID = ? WHERE GuildID = ?");
        ps.setString(1, answererID);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    public void setQuestion(String serverID, String question) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE tod SET Question = ? WHERE GuildID = ?");
        ps.setString(1, question);
        ps.setString(2, serverID);
        ps.executeUpdate();
    }

    //QOTD channel data -----------------------------------------------------------------------------------------------

    //method that returns a hashmap of all servers with a qotd channel set, and the channel id
    public HashMap<String, String> getQOTDChannels() throws SQLException {
        HashMap<String, String> qotdChannels = new HashMap<>();

        // Define the SQL query to select the GuildID and QOTDId columns from the settings table
        String query = "SELECT GuildID, QOTDId FROM settings WHERE QOTDId IS NOT NULL";

        // Create a prepared statement for the query
        PreparedStatement pstmt = connection.prepareStatement(query);

        // Execute the query and get the result set
        ResultSet resultSet = pstmt.executeQuery();

        // Iterate through the result set and add each GuildID and QOTDId to the hashmap
        while (resultSet.next()) {
            String guildId = resultSet.getString("GuildID");
            String channelId = resultSet.getString("QOTDId");
            if(guildId != null && channelId != null) {
                if(channelId.length() > 0 && !channelId.equals("")) {
                    qotdChannels.put(guildId, channelId);
                }

            }

        }
        System.out.println(qotdChannels + "from getQOTDChannels");
        return qotdChannels;
    }
    public void setQOTDID(String serverID, String channelID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE settings SET QOTDId = ? WHERE GuildID = ?");
        ps.setString(1, channelID);
        ps.setString(2, serverID);
        ps.executeUpdate();

    }

    public void getQOTDID(String serverID) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT ChannelID FROM settings WHERE GuildID = ?");
        ps.setString(1, serverID);
        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString("ChannelID"));
    }
}
