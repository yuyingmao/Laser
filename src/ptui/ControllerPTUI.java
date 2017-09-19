package ptui;

import model.LasersModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represents the controller portion of the plain text UI.
 * It takes the model from the view (LasersPTUI) so that it can perform
 * the operations that are input in the run method.
 *
 * @author Sean Strout @ RIT CS
 * @author Yuying Mao
 * @author Connor Milligan
 */
public class ControllerPTUI{

    /** The UI's connection to the model */
    private LasersModel model;

    /**
     * Construct the PTUI.Create the model and initialize the view.
     * @param model The laser model
     */
    public ControllerPTUI(LasersModel model) {
        this.model = model;
    }

    /**
     * Run the main loop.  This is the entry point for the controller
     * @param inputFile The name of the input command file, if specified
     */
    public void run(String inputFile) throws FileNotFoundException {
        this.model.getSafe().display();
        if(inputFile!=null){
            Scanner input = new Scanner(new File(inputFile));
            while(input.hasNext()){
                String[] commands=input.nextLine().split(" ");
                String output=">";
                for(String c: commands){
                    output+=" "+c;
                }
                System.out.println(output);
                switch (commands[0].charAt(0)){
                    case('a'):
                        if(commands.length==3){
                            this.model.getSafe().add(Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
                        }
                        else{
                            System.out.println("Incorrect coordinates");
                        }
                        break;
                    case('r'):
                        if(commands.length==3){
                            this.model.getSafe().remove(Integer.parseInt(commands[1]),Integer.parseInt(commands[2]));
                        }
                        else{
                            System.out.println("Incorrect coordinates");
                        }
                        break;
                    case('d'):
                        if(commands.length==1){
                            this.model.getSafe().display();
                        }
                        else{
                            System.out.println("Unrecognized command: " + commands[0]);
                        }
                        break;
                    case('h'):
                        if(commands.length==1){
                            this.model.getSafe().help();
                        }
                        else{
                            System.out.println("Unrecognized command: " + commands[0]);
                        }
                        break;
                    case('v'):
                        if(commands.length==1){
                            this.model.getSafe().verify();
                        }
                        else{
                            System.out.println("Unrecognized command: " + commands[0]);
                        }
                        break;
                    case('q'):
                        if(commands.length==1){
                            this.model.getSafe().quit();
                        }
                        else{
                            System.out.println("Unrecognized command: " + commands[0]);
                        }
                        break;
                    default:
                        System.out.println("Unrecognized command: " + commands[0]);
                        break;
                }

            }
        }

        while(true){
            System.out.print("> ");
            Scanner in=new Scanner(System.in);
            String[] next = in.nextLine().split(" ");
            switch (next[0].charAt(0)) {
                case ('a'):
                    if (next.length == 3) {
                        this.model.add(Integer.parseInt(next[1]), Integer.parseInt(next[2]));
                    } else {
                        System.out.println("Incorrect coordinates");
                    }
                    break;
                case ('r'):
                    if (next.length == 3) {
                        this.model.remove(Integer.parseInt(next[1]), Integer.parseInt(next[2]));
                    } else {
                        System.out.println("Incorrect coordinates");
                    }
                    break;
                case ('d'):
                    if (next.length == 1) {
                        this.model.display();
                    } else {
                        System.out.println("Unrecognized command: " + next[0]);
                    }
                    break;
                case ('h'):
                    if (next.length == 1) {
                        this.model.help();
                    } else {
                        System.out.println("Unrecognized command: " + next[0]);
                    }
                    break;
                case ('v'):
                    if (next.length == 1) {
                        this.model.verify();
                    } else {
                        System.out.println("Unrecognized command: " + next[0]);
                    }
                    break;
                case ('q'):
                    if (next.length == 1) {
                        this.model.quit();
                    } else {
                        System.out.println("Unrecognized command: " + next[0]);
                    }
                    break;
                default:
                    System.out.println("Unrecognized command: " + next[0]);
                    break;
            }
        }
    }
}
