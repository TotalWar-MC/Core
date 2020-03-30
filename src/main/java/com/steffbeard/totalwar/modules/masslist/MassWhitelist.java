package com.steffbeard.totalwar.modules.masslist;

import com.github.games647.craftapi.UUIDAdapter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Connection;

import dev.siris.module.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public final class MassWhitelist extends Module {
    
	FileConfiguration config = this.getConfig();
    //Database Variables
    final String dbusername = config.getString("database_username"); //Database Username
    final String dbpassword = config.getString("database_password"); //Database Password
    final String dbhost = config.getString("database_host"); //Database Host
    final String dbport = config.getString("database_port"); //Database Port
    final String dbname = config.getString("database_name"); //Database Name
    final String discordStaff = config.getString("discord_staff"); //Discord Staff Channel Webhook URL
    final String discordPlayers = config.getString("discord_players"); //Discord Players Channel Webhook URL
    final String serverIP = config.getString("server_ip"); //Minecraft Server IP
    final String logoURL = config.getString("logo_url"); //Logo URL to show on webhooks
    final String url = "jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname + ""; //Final Connection String

    //Connection vars
    public Connection connection; //This is the variable we will use to connect to database

    @Override
    public void onEnable() {
        // Plugin startup logic
        //Generate config.yml and set defaults
        config.addDefault("debug", false);
        config.addDefault("database_username", "");
        config.addDefault("database_password", "");
        config.addDefault("database_host", "");
        config.addDefault("database_port", "3306");
        config.addDefault("database_name", "");
        config.addDefault("discord_staff", "");
        config.addDefault("discord_players", "");
        config.addDefault("server_ip", "");
        config.addDefault("logo_url", "");
        config.options().copyDefaults(true);
        saveConfig();
        System.out.println("MassWhitelist by konsyr11");
        System.out.println("Total War Version");
        System.out.println(" ");
        try { //We use a try catch to avoid errors, hopefully we don't get any.
            Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("FATAL: The JDBC Driver is not available! Contact AcmePlugins for more details!");
            System.err.println("FATAL: Plugin is not functional!!");
            return;
        }
        try { //Another try catch to get any SQL errors (for example connections errors)
            connection = (Connection) DriverManager.getConnection(url, dbusername, dbpassword);
            //with the method getConnection() from DriverManager, we're trying to set
            //the connection's url, username, password to the variables we made earlier and
            //trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { //catching errors)
            System.err.println("FATAL: Cannot connect to the database! Have you set credentials in config.yml?");
            e.printStackTrace(); //prints out SQLException errors to the console (if any)
            System.err.println("FATAL: Plugin is not functional!!");
        }

        if (config.getBoolean("debug")) {
            System.out.println("Debugging mode is Enabled, displaying connection info!");
            System.out.println("Supplied host: " + dbhost);
            System.out.println("Supplied port: " + dbport);
            System.out.println("Supplied database name: " + dbname);
            System.out.println("Supplied username: " + dbusername);
            System.out.println("Supplied password: Hidden, please check config.yml");
            System.out.println("Discord staff webhook: " + discordStaff);
            System.out.println("Discord players webhook: " + discordPlayers);
            System.out.println(" ");
        }
        System.out.println("Checking tables and creating if not exist...");

        String create_applications = "CREATE TABLE IF NOT EXISTS masswl_applications(id INT NOT NULL AUTO_INCREMENT , PRIMARY KEY (id), username varchar(64), discord varchar(64), application_time timestamp, paragraph varchar(2047), accepted enum('yes', 'no'), status enum('processed', 'pending'));";
        // prepare the statement to be executed
        try {
            PreparedStatement stmt = connection.prepareStatement(create_applications);
            // I use executeUpdate() to update the databases table.
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String create_users = "CREATE TABLE IF NOT EXISTS masswl_users(id INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id), username VARCHAR(50) NOT NULL UNIQUE, password varchar(255) NOT NULL, created_at timestamp);";
        // prepare the statement to be executed
        try {
            PreparedStatement stmt = connection.prepareStatement(create_users);
            // I use executeUpdate() to update the databases table.
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Reading database...");
        System.out.println("Checking for new whitelists...");
        String sql = "SELECT * FROM masswl_applications WHERE accepted = 'yes' AND status = 'pending'";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet results = null;
        try {
            results = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (!results.next()) {
                System.out.println("No new accepted players.");
            } else {
                System.out.println("There are new accepted players! Run /masswl to whitelist them!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("The Plugin is now started.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabling MassWhitelist...");
        System.out.println("Closing all Database connections...");
        try { //using a try catch to catch connection errors (like wrong sql password...)
            if (connection != null && !connection.isClosed()) { //checking if connection isn't null to
                //avoid receiving a nullpointer
                connection.close(); //closing the connection field variable.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Disconnected from Database!");
        System.out.println("Plugin Disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("readwl")) {
            //Creating a Statement object
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //Retrieving the data
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("SELECT count(*) FROM masswl_applications WHERE accepted = 'yes' AND status = 'pending'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("masswhitelist.readwl")) {
                    try {
                        player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " There are currently " + ChatColor.YELLOW + rs.getInt("count(*)") + ChatColor.GREEN + " accepted players to be whitelisted. Run /masswl to whitelist them!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "] " + ChatColor.DARK_RED + "You don't have the permission to run this command!");
                }
            } else {
                try {
                    System.out.println("[MassWhitelist] There are currently " + rs.getInt("count(*)") + " accepted players to be whitelisted. Run /masswl to whitelist them!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if (command.getName().equals("masswl")) {
        TemmieWebhook playerNotify = new TemmieWebhook(discordPlayers);
            int wlarraylength = 0; //Initiate variable for array size.
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("masswhitelist.masswl")) {
                    player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " Starting MassWhitelist process...");
                    ArrayList<Object> getPlayers;
                    {
                        ArrayList<Object> players = new ArrayList<Object>();
                        try {
                            Statement stmt = connection.createStatement();
                            ResultSet result = stmt.executeQuery("SELECT * FROM masswl_applications WHERE accepted = 'yes' AND status = 'pending'");
                            while (result.next()) {

                                players.add(result.getString(2));

                            }
                        } catch (SQLException e) {
                            player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "] " + ChatColor.GREEN +  e.getMessage());
                        }
                        player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " The following players will be whitelisted: " + players);
                        while (players.size() > 0) {
                            String wlplayer = String.valueOf(players.get(0));
                            player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " Whitelisting player: " + wlplayer);
                            UUID wluuid = UUIDAdapter.generateOfflineId(wlplayer);
                            player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " UUID of player is: " + wluuid);
                            Bukkit.getServer().getOfflinePlayer(wlplayer).setWhitelisted(true);
                            try {
                                Statement stmt = connection.createStatement();
                                stmt.executeUpdate("UPDATE masswl_applications SET status='processed' WHERE username = '" + wlplayer + "'");
                            } catch (SQLException e) {
                                player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " There was an error while running MassWhitelist process!");
                                player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "] " + ChatColor.GREEN + e.getMessage());
                            }

                            players.remove(wlplayer);
                            player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " > " + wlplayer + " has been whitelisted!");
                        }
                        Bukkit.getServer().reloadWhitelist();
                        player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "]" + ChatColor.GREEN + " MassWhitelist process is now completed!");
                    }
//                DiscordMessage dm = new DiscordMessage("Total War Whitelist", "@everyone whitelist applications have been processed! If you were accepted, you should be able to connect to the server using IP `" + serverIP + "`. Ready to conquer?", logoURL);
//                playerNotify.sendMessage(dm);

                } else {
                    player.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + "MassWhitelist" + ChatColor.RED + "] " + ChatColor.DARK_RED + "You don't have the permission to run this command!");
                }
            } else {
                System.out.println("Starting MassWhitelist process...");
                ArrayList<Object> getPlayers;
                {
                    ArrayList<Object> players = new ArrayList<Object>();
                    try {
                        Statement stmt = connection.createStatement();
                        ResultSet result = stmt.executeQuery("SELECT * FROM masswl_applications WHERE accepted = 'yes' AND status = 'pending'");
                        while (result.next()) {

                            players.add(result.getString(2));

                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("The following players will be whitelisted: " + players);
                    while (players.size() > 0) {
                        String wlplayer = String.valueOf(players.get(0));
                        System.out.println("Whitelisting player: " + wlplayer);
                        UUID wluuid = UUIDAdapter.generateOfflineId(wlplayer);
                        System.out.println("UUID of player is: " + wluuid);
                        Bukkit.getServer().getOfflinePlayer(wlplayer).setWhitelisted(true);
                        try {
                            Statement stmt = connection.createStatement();
                            stmt.executeUpdate("UPDATE masswl_applications SET status='processed' WHERE username = '" + wlplayer + "'");
                        } catch (SQLException e) {
                            System.out.println("There was an error while running MassWhitelist process!");
                            System.out.println(e.getMessage());
                        }

                        players.remove(wlplayer);
                        System.out.println("> " + wlplayer + " has been whitelisted!");
                    }
                    Bukkit.getServer().reloadWhitelist();
                    System.out.println("MassWhitelist process is now completed!");
                }
//            DiscordMessage dm = new DiscordMessage("Total War Whitelist", "@everyone whitelist applications have been processed! If you were accepted, you should be able to connect to the server using IP `" + serverIP + "`. Ready to conquer?", logoURL);
//            playerNotify.sendMessage(dm);
            }

        }

        return(false);

    }

}
