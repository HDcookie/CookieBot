package me.hdcookie.database;

import java.sql.*;

public class Database {

    private Connection connection;

    public void connect() throws SQLException {

        final String PASSWORD = "";
        final String USERNAME = "root";
        final String DATABASE = "cookiebot";
        final int PORT = 3306;
        final String HOST = "localhost";

        connection = DriverManager.getConnection("jdbc:mysql://"
                + HOST + ":" + PORT + "/" + DATABASE +
                "?useSSL=false", USERNAME, PASSWORD);
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

    public void addServerSettings(String serverID, String countID, String guessID, String radioID, String makeSentance, String finishedSentance) throws SQLException {

        if (isConnected()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO settings (GuildID, CountingID, RadioID, GuildMOTD, GuessID, MakeSentenceID, FinishedSentenceID)  VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, serverID);
            ps.setString(2, countID);
            ps.setString(3, radioID); //RadioID
            ps.setString(4, "0"); //MOTD
            ps.setString(5, guessID);
            ps.setString(6, makeSentance);
            ps.setString(7, finishedSentance);
            ps.executeUpdate();

            addGameData(serverID);

        }
    }

    /*
    Add Game Data function creates default values for the games table with the server.  Should only be run once per server, otherwise it will reset data

     */
    public void addGameData(String serverID) throws SQLException {

        if (isConnected()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO games (GuildID, Counting, Guess, CurrentSentence)  VALUES (?, ?, ?, ?)");
            ps.setString(1, serverID);
            ps.setInt(2, 0);
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

}
