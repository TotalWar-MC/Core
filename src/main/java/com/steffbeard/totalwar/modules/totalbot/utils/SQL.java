package com.steffbeard.totalwar.modules.totalbot.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.dv8tion.jda.api.entities.Guild;

public class SQL {

    private static Connection connection;
    private static String password = Statics.PASSWORD;

    public static void connect(){
        if(!isConnected()){
            try{

                String host = Statics.HOST;
                String port = Statics.PORT;
                String database = Statics.DATABASE;
                String username = Statics.USERNAME;
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
                System.out.println("> SQL connected.");

            } catch (SQLException e) {
                System.out.println("> SQL connection failed!");
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected(){
        return (connection != null);
    }


    public static boolean ifGuildExists(Guild guild){
        try {
            if(connection.isClosed())
                connect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM guild WHERE serverid = ?");
            ps.setString(1, String.valueOf(guild.getIdLong()));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ex){
        	ex.printStackTrace();
        }
        return  false;
    }

    public static void updateValue(Guild guild, String type, String value){
        try{
            if(connection.isClosed())
                connect();
            if(!ifGuildExists(guild))
                createServer(guild);
            PreparedStatement ps = connection.prepareStatement("UPDATE guild SET " + type + " = '" + value + "' WHERE serverid = " + guild.getId());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static void createServer(Guild guild){
        try{
            if(connection.isClosed())
                connect();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `guild`(`serverid`,  `music`, `joinchannel`, `logchannel`, `prefix`, `msg`, `autorole`, `joinmessage`) VALUES (?, '0', '0', '0', '_', '1', '0', 'Welcome %user% on %guild%')");
            ps.setString(1, String.valueOf(guild.getIdLong()));
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String getValue(Guild guild, String type){
        try{
            if(connection.isClosed())
                connect();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM guild WHERE `serverid` = ?");
            ps.setString(1, guild.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getString(type);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}