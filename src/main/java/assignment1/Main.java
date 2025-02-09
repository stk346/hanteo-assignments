package assignment1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TreeStructure tree = new TreeStructure();
        TreeInitializer.init(tree);
        
        UserInterface ui = new UserInterface(new Scanner(System.in), tree);
        ui.start();
    }
}
