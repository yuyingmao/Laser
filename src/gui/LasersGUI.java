package gui;

import backtracking.Backtracker;
import backtracking.Configuration;
import backtracking.SafeConfig;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import model.*;

/**
 * The main class that implements the JavaFX UI.   This class represents
 * the view/controller portion of the UI.  It is connected to the model
 * and receives updates from it.
 *
 * @author Sean Strout @ RIT CS
 * @author Yuying Mao
 * @author Connor Milligan
 */
public class LasersGUI extends Application implements Observer {

    /** The UI's connection to the model */
    private LasersModel model;
    private Label top;
    private HashMap<String,Button> inputs;
    private BorderPane pane;
    private SafeConfig safeConfig;
    private boolean reload;
    private boolean verify;
    private Stage stage;
    private Backtracker bt;

    @Override
    public void init() throws Exception {
        // the init method is run before start.  the file name is extracted
        // here and then the model is created.
        try {
            Parameters params = getParameters();
            String filename = params.getRaw().get(0);
            this.model = new LasersModel(filename);
            this.safeConfig=new SafeConfig(filename);
            this.inputs=new HashMap<>();
            this.top=new Label();
            this.pane=new BorderPane();
            this.reload=false;
            this.verify=false;
            this.bt = new Backtracker(false);
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
            System.exit(-1);
        }
        this.model.addObserver(this);
    }

    /**
     * A private utility function for setting the background of a button to
     * an image in the resources subdirectory.
     *
     * @param button the button control
     * @param bgImgName the name of the image file
     */
    private void setButtonBackground(Button button, String bgImgName) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image( getClass().getResource("resources/" + bgImgName).toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane.setStyle("-fx-background-color: CYAN;");

        //set the top a label that tells user what is going on.
        this.top.setText(this.model.getMessage());
        top.setPadding(new Insets(10,0,0,60));
        pane.setTop(top);

        //set the center a GridPane which represents the safe grid.
        pane.setCenter(makeCenter());

        //set the right a VBox which contains the load, solve, hint, restart and
        // check buttons.
        pane.setRight(makeRight());

        pane.autosize();

        Scene scene=new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lasers");

        this.stage=primaryStage;
        stage.setResizable(false);
        stage.show();
    }

    /**
     * create a GridPane that contains buttons which can represent an empty place, a laser,
     * a beam or a pillar, and return it.
     * @return the GridPane that contains buttons and also is the center of the BorderPane.
     */
    public GridPane makeCenter(){
        GridPane center=new GridPane();
        String[][] grid=this.model.getGrid();
        for(int row=0;row<grid.length;row++){
            for(int col=0;col<grid[0].length;col++){
                final int r=row;
                final int c=col;
                String s=r+","+c;
                switch (grid[row][col]){
                    case("X"):
                        Button anyNum = new Button();
                        setButtonBackground(anyNum,"pillarX.png");
                        anyNum.setMinSize(40,40);
                        this.inputs.put(s,anyNum);
                        center.add(anyNum,col,row);
                        anyNum.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("0"):
                        Button p0 = new Button();
                        Image p0Img = new Image(getClass().getResourceAsStream("resources/pillar0.png"));
                        ImageView p0Icon = new ImageView(p0Img);
                        p0.setGraphic(p0Icon);
                        setButtonBackground(p0,"white.png");
                        p0.setMinSize(40,40);
                        this.inputs.put(s,p0);
                        center.add(p0,col,row);
                        p0.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("1"):
                        Button p1 = new Button();
                        Image p1Img = new Image(getClass().getResourceAsStream("resources/pillar1.png"));
                        ImageView p1Icon = new ImageView(p1Img);
                        p1.setGraphic(p1Icon);
                        setButtonBackground(p1,"white.png");
                        p1.setMinSize(40,40);
                        this.inputs.put(s,p1);
                        center.add(p1,col,row);
                        p1.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("2"):
                        Button p2 = new Button();
                        Image p2Img = new Image(getClass().getResourceAsStream("resources/pillar2.png"));
                        ImageView p2Icon = new ImageView(p2Img);
                        p2.setGraphic(p2Icon);
                        setButtonBackground(p2,"white.png");
                        p2.setMinSize(40,40);
                        this.inputs.put(s,p2);
                        center.add(p2,col,row);
                        p2.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("3"):
                        Button p3 = new Button();
                        Image p3Img = new Image(getClass().getResourceAsStream("resources/pillar3.png"));
                        ImageView p3Icon = new ImageView(p3Img);
                        p3.setGraphic(p3Icon);
                        setButtonBackground(p3,"white.png");
                        p3.setMinSize(40,40);
                        this.inputs.put(s,p3);
                        center.add(p3,col,row);
                        p3.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("4"):
                        Button p4 = new Button();
                        Image p4Img = new Image(getClass().getResourceAsStream("resources/pillar4.png"));
                        ImageView p4Icon = new ImageView(p4Img);
                        p4.setGraphic(p4Icon);
                        setButtonBackground(p4,"white.png");
                        p4.setMinSize(40,40);
                        this.inputs.put(s,p4);
                        center.add(p4,col,row);
                        p4.setOnAction(event -> this.model.add(r,c));
                        break;
                    case("."):
                        Button empty = new Button();
                        Image empImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                        ImageView empIcon = new ImageView(empImg);
                        empty.setGraphic(empIcon);
                        setButtonBackground(empty,"white.png");
                        empty.setMinSize(40,40);
                        this.inputs.put(s,empty);
                        center.add(empty,col,row);
                        empty.setOnAction(event -> this.model.click(r,c));
                        break;
                }
            }
        }
        center.setPadding(new Insets(10,10,10,10));
        center.setVgap(5);
        center.setHgap(5);
        return center;
    }

    /**
     * create a VBox that contains buttons which are check, hint, solve, reset and load,
     * and return it.
     * @return the VBox that contains buttons and also is the right of the BorderPane.
     */
    public VBox makeRight(){
        VBox buttons=new VBox(5);
        Button check=new Button("Check");
        Button hint=new Button("Hint");
        Button solve=new Button("Solve");
        Button restart=new Button("Restart");
        Button load=new Button("Load");
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(20, 10, 10, 10));
        buttons.getChildren().addAll(restart,load,hint,solve,check);

        //when click check button
        check.setOnAction(event -> {
            verify=true;
            this.model.verify();
        });

        //when click restart button
        restart.setOnAction(event -> this.model.reset());

        //when click hint button
        hint.setOnAction(event -> {
            safeConfig.setGrid(this.model.getGrid());
            List<Configuration> sol = this.bt.solveWithPath(safeConfig);
            if(this.model.verify()){
                this.model.setMessage(this.model.getMessage());
            }
            else{
                if(sol.size() != 0){
                    SafeConfig s=(SafeConfig)sol.get(1);
                    this.model.setGrid(s.getGrid());
                    this.model.setMessage("Hint: Lasers add at ("+s.getCurrRow()+", "+s.getCurrCol()+")");
                } else{
                    this.model.setMessage("Has No Solution!");
                }
            }
        });

        //when click solve button
        solve.setOnAction(event -> {
            Optional<Configuration> sol = bt.solve(safeConfig);
            if(sol.isPresent()){
                SafeConfig s=(SafeConfig)sol.get();
                this.model.setGrid(s.getGrid());
                this.model.setMessage("Solved!");
            }
            else{
                this.model.setMessage("Has No Solution!");
            }
        });

        //when click load button
        load.setOnAction(event -> {
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Open Test File");
            File path = fileChooser.showOpenDialog(null);
            if(path!=null){
                String file=path.getAbsolutePath();
                try{
                    reload=true;
                    SafeModel safe=new SafeModel(file);
                    safeConfig=new SafeConfig(file);
                    this.model.setSafe(safe);
                }catch(FileNotFoundException e){
                    e.getMessage();
                }
            }
        });

        return buttons;
    }

    /**
     * helper function for load button. when load a new file, reset the GridPane, the center
     * of the BorderPane, and return it.
     * @param grid the current grid that comes from the new file which needs to be shown in
     *             the stage
     * @return the new GridPane
     */
    public GridPane loadNewFile(String[][] grid){
        GridPane newGrid = new GridPane();
        this.inputs=new HashMap<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                final int r = row;
                final int c = col;
                String s = r + "," + c;
                switch (grid[row][col]) {
                    case ("X"):
                        Button anyNum = new Button();
                        setButtonBackground(anyNum, "pillarX.png");
                        anyNum.setMinSize(40, 40);
                        this.inputs.put(s, anyNum);
                        newGrid.add(anyNum, col, row);
                        anyNum.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("0"):
                        Button p0 = new Button();
                        Image p0Img = new Image(getClass().getResourceAsStream("resources/pillar0.png"));
                        ImageView p0Icon = new ImageView(p0Img);
                        p0.setGraphic(p0Icon);
                        setButtonBackground(p0, "white.png");
                        p0.setMinSize(40, 40);
                        this.inputs.put(s, p0);
                        newGrid.add(p0, col, row);
                        p0.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("1"):
                        Button p1 = new Button();
                        Image p1Img = new Image(getClass().getResourceAsStream("resources/pillar1.png"));
                        ImageView p1Icon = new ImageView(p1Img);
                        p1.setGraphic(p1Icon);
                        setButtonBackground(p1, "white.png");
                        p1.setMinSize(40, 40);
                        this.inputs.put(s, p1);
                        newGrid.add(p1, col, row);
                        p1.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("2"):
                        Button p2 = new Button();
                        Image p2Img = new Image(getClass().getResourceAsStream("resources/pillar2.png"));
                        ImageView p2Icon = new ImageView(p2Img);
                        p2.setGraphic(p2Icon);
                        setButtonBackground(p2, "white.png");
                        p2.setMinSize(40, 40);
                        this.inputs.put(s, p2);
                        newGrid.add(p2, col, row);
                        p2.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("3"):
                        Button p3 = new Button();
                        Image p3Img = new Image(getClass().getResourceAsStream("resources/pillar3.png"));
                        ImageView p3Icon = new ImageView(p3Img);
                        p3.setGraphic(p3Icon);
                        setButtonBackground(p3, "white.png");
                        p3.setMinSize(40, 40);
                        this.inputs.put(s, p3);
                        newGrid.add(p3, col, row);
                        p3.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("4"):
                        Button p4 = new Button();
                        Image p4Img = new Image(getClass().getResourceAsStream("resources/pillar4.png"));
                        ImageView p4Icon = new ImageView(p4Img);
                        p4.setGraphic(p4Icon);
                        setButtonBackground(p4, "white.png");
                        p4.setMinSize(40, 40);
                        this.inputs.put(s, p4);
                        newGrid.add(p4, col, row);
                        p4.setOnAction(event -> this.model.add(r, c));
                        break;
                    case ("."):
                        Button empty = new Button();
                        Image empImg = new Image(getClass().getResourceAsStream("resources/white.png"));
                        ImageView empIcon = new ImageView(empImg);
                        empty.setGraphic(empIcon);
                        setButtonBackground(empty, "white.png");
                        empty.setMinSize(40, 40);
                        this.inputs.put(s, empty);
                        newGrid.add(empty, col, row);
                        empty.setOnAction(event -> this.model.click(r, c));
                        break;
                }
            }
        }
        newGrid.setPadding(new Insets(10,10,10,10));
        newGrid.setVgap(5);
        newGrid.setHgap(5);
        this.bt=new Backtracker(false);
        return newGrid;
    }

    /**
     * update helper function. when the user click on any button inside the GridPane, the
     * GridPane changes, for example, adds or removes a laser.
     * @param grid the current grid that needs to be shown in the stage
     */
    public void change(String[][] grid){
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                String s = row + "," + col;
                switch (grid[row][col]) {
                    case ("X"):
                        setButtonBackground(this.inputs.get(s), "pillarX.png");
                        break;
                    case ("0"):
                        Image p0Img = new Image(getClass().getResourceAsStream("resources/pillar0.png"));
                        ImageView p0Icon = new ImageView(p0Img);
                        this.inputs.get(s).setGraphic(p0Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                    case ("1"):
                        Image p1Img = new Image(getClass().getResourceAsStream("resources/pillar1.png"));
                        ImageView p1Icon = new ImageView(p1Img);
                        this.inputs.get(s).setGraphic(p1Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                    case ("2"):
                        Image p2Img = new Image(getClass().getResourceAsStream("resources/pillar2.png"));
                        ImageView p2Icon = new ImageView(p2Img);
                        this.inputs.get(s).setGraphic(p2Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                    case ("3"):
                        Image p3Img = new Image(getClass().getResourceAsStream("resources/pillar3.png"));
                        ImageView p3Icon = new ImageView(p3Img);
                        this.inputs.get(s).setGraphic(p3Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                    case ("4"):
                        Image p4Img = new Image(getClass().getResourceAsStream("resources/pillar4.png"));
                        ImageView p4Icon = new ImageView(p4Img);
                        this.inputs.get(s).setGraphic(p4Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                    case ("L"):
                        Image laserImg = new Image(getClass().getResourceAsStream("resources/laser.png"));
                        ImageView laserIcon = new ImageView(laserImg);
                        this.inputs.get(s).setGraphic(laserIcon);
                        setButtonBackground(this.inputs.get(s), "yellow.png");
                        break;
                    case ("*"):
                        Image bImg = new Image(getClass().getResourceAsStream("resources/beam.png"));
                        ImageView bIcon = new ImageView(bImg);
                        this.inputs.get(s).setGraphic(bIcon);
                        break;
                    case ("."):
                        Image Img = new Image(getClass().getResourceAsStream("resources/white.png"));
                        ImageView Icon = new ImageView(Img);
                        this.inputs.get(s).setGraphic(Icon);
                        setButtonBackground(this.inputs.get(s), "white.png");
                        break;
                }
            }
        }
    }

    /**
     * helper function for check button. when verify the current grid, change the error slot's
     * background color to red if there is one. if the error slot is an Empty slot, its graph
     * needs to change to red too; otherwise, just change its background color.
     * @param grid the current grid that needs to be shown in the stage
     */
    public void showOutError(String[][] grid){
        Integer[] errorSlots=this.model.getErrorSlots();
        if(errorSlots[0]!=null){
            int row=errorSlots[0];
            int col=errorSlots[1];
            String s=row+","+col;
            if(grid[row][col].equals(".")){
                Image Img = new Image(getClass().getResourceAsStream("resources/red.png"));
                ImageView Icon = new ImageView(Img);
                this.inputs.get(s).setGraphic(Icon);
            }
            setButtonBackground(this.inputs.get(s),"red.png");
        }
        this.model.resetErrorSlot();
    }

    @Override
    public void update(Observable o, Object arg) {

        //change the label content that tells user what is going on.
        this.top.setText(this.model.getMessage());

        String[][] grid=this.model.getGrid();

        //if user click the load button
        if(reload) {
            this.pane.setCenter(loadNewFile(grid));
            this.stage.sizeToScene();
            reload = false;
        }

        //if user click the check button
        else if(verify){
            showOutError(grid);
            verify=false;
        }

        else{
            change(grid);
        }

    }
}
