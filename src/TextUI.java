

import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    public void displayMsg(String msg){
        System.out.println(msg);
    }

    public boolean promptBinary(String msg){
        String input = promptText(msg);
        if(input.equalsIgnoreCase("Login")){
            return false;
        }
        else if(input.equalsIgnoreCase("Create user")){
            return true;
        }
        displayMsg("You typed a wrong input, try again");
        return promptBinary(msg);
    }

    public int promptNumeric(String msg) {
        System.out.println(msg);              // Stille brugeren et spørgsmål
        String input = scan.nextLine();
        int number;
        // Give brugere et sted at placere sit svar og vente på svaret
        try {
            number = Integer.parseInt(input);
        }
        catch(NumberFormatException e){
            displayMsg("Please type a number");
            number = promptNumeric(msg);
        }
        return number;
    }

    public String promptText(String msg){
        System.out.println(msg);//Stille brugeren et spørgsmål
        String input = scan.nextLine();
        return input;
    }

    public ArrayList<String> promptChoice(ArrayList<String> options, int limit, String msg){
        ArrayList<String> choices = new ArrayList<String>();  //Lave en beholder til at gemme brugerens valg
        int count = 1;
        while(choices.size() < limit){             //tjekke om brugeren skal vælge flere drinks
            String choice = promptText(count+":");
            choices.add(choice);
            count++;
        }
        return choices;
    }
    //public void displayList(ArrayList<String> options, String msg){
    public void displayList(ArrayList<String> options, String msg){
        System.out.println("*******");
        System.out.println(msg);
        System.out.println("*******");


        for (String option : options) {
            displayMsg("- "+option); //andre attributer kan tilføjes, hvis ønsket

        }
        System.out.println("*******\n");
    }

}