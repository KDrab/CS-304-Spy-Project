import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.Database;
import gui.Homepage;
import gui.LoginDialog;

public class Main {
    public static void main(String[] args) {
    	// create database
    	Database db = new Database("ora_q0b9", "a48197123");
    	Homepage frame = new Homepage("Game of Politics", db);
    }
}