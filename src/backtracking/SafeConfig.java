package backtracking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import model.LasersModel;

/**
 * The class represents a single configuration of a safe.  It is
 * used by the backtracker to generate successors, check for
 * validity, and eventually find the goal.
 *
 * This class is given to you here, but it will undoubtedly need to
 * communicate with the model.  You are free to move it into the model
 * package and/or incorporate it into another class.
 *
 * @author Sean Strout @ RIT CS
 * @author Yuying Mao
 * @author Connor Milligan
 */
public class SafeConfig implements Configuration {
    public final static String EMPTY=".";
    public final static String ANYNUM="X";
    public final static String LASER="L";
    public final static String BEAM="*";

    private String[][] grid;
    private Integer[] size;
    private int currRow;
    private int currCol;


    /**
     * constructor. read a file and create a safe grid.
     * @param filename file name
     * @throws FileNotFoundException if file does not exist
     */
    public SafeConfig(String filename) throws FileNotFoundException{
        Scanner file = new Scanner(new File(filename));
        this.size = new Integer[2];
        this.size[0] = file.nextInt();
        this.size[1] = file.nextInt();
        file.nextLine();
        this.grid=new String[this.size[0]][this.size[1]];
        for(int row=0;row<this.size[0];row++){
            String[] next=file.nextLine().split(" ");
            for(int col=0;col<this.size[1];col++){
                this.grid[row][col]=next[col];
            }
        }
        file.close();
        this.currRow = 0;
        this.currCol = -1;
    }

    /**
     * Getter function. return the safe grid.
     * @return the safe grid
     */
    public String[][] getGrid(){
        return this.grid;
    }

    /**
     * Getter function. return the current row number
     * @return the current row number
     */
    public int getCurrRow() {
        return currRow;
    }

    /**
     * Getter function. return the current column number
     * @return the current column number
     */
    public int getCurrCol() {
        return currCol;
    }

    /**
     * Setter function. set the safe grid to a given grid.
     * @param grid the given grid.
     */
    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    /**
     * Copy constructor.  Takes a config, other, and makes a full "deep" copy
     * of its instance data.
     * @param other the config to copy
     */
    public SafeConfig(SafeConfig other){
        this.grid = new String[other.size[0]][other.size[1]];
        this.size = new Integer[2];

        for(int row = 0; row < other.size[0]; row++){
            System.arraycopy(other.grid[row], 0, this.grid[row], 0, other.size[0]);
        }
        this.size[0] = other.size[0];
        this.size[1] = other.size[1];
        this.currRow = other.currRow;
        this.currCol = other.currCol;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        Collection<Configuration> config = new ArrayList<>();
        this.currCol += 1;
        int col = this.currCol;
        if(col == this.size[1]){
            col = 0;
            this.currCol = 0;
            this.currRow += 1;
        }
        int row = this.currRow;

        if (this.grid[row][col].equals(String.valueOf(EMPTY))) {
            SafeConfig child1 = new SafeConfig(this);
            child1.add(row, col);
            SafeConfig child2 = new SafeConfig(this);
            child2.grid[row][col] = String.valueOf(EMPTY);
            config.add(child1);
            config.add(child2);
            return config;
        } else {
            SafeConfig child3 = new SafeConfig(this);
            config.add(child3);
            return config;
        }
    }

    @Override
    public boolean isValid() {
        if(this.grid[this.currRow][this.currCol].equals(String.valueOf(LASER))){
            if(!verifyLaser(this.currRow, this.currCol)){
                return false;
            }
        }
        if(this.currRow == this.size[0] - 1 && this.currCol == this.size[1] - 1){
            return this.verify();
        }

        return true;
    }

    @Override
    public boolean isGoal() {
        if(!(this.currRow == this.size[0] - 1)  || !(this.currCol == this.size[1] - 1)){
            return false;
        }
        if(this.verify()){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Add laser helper function
     * @param r integer representing the lasers row position
     * @param c integer representing the lasers column position
     */
    public void shootLaser(int r, int c){
        // North
        for(int row = r; row >= 0; row--){
            if(this.grid[row][c].equals(EMPTY)){
                this.grid[row][c] = BEAM;
            } else if(!this.grid[row][c].equals(LASER) && !this.grid[row][c].equals(BEAM)){
                break;
            }
        }

        // South
        for(int row = r; row < this.size[0]; row++){
            if(this.grid[row][c].equals(EMPTY)){
                this.grid[row][c] = BEAM;
            } else if(!this.grid[row][c].equals(LASER) && !this.grid[row][c].equals(BEAM)){
                break;
            }
        }

        // East
        for(int col = c; col < this.size[1]; col++){
            if(this.grid[r][col].equals(EMPTY)){
                this.grid[r][col] = BEAM;
            } else if(!this.grid[r][col].equals(LASER) && !this.grid[r][col].equals(BEAM)){
                break;
            }
        }

        // West
        for(int col = c; col >= 0; col--){
            if(this.grid[r][col].equals(EMPTY)){
                this.grid[r][col] = BEAM;
            } else if(!this.grid[r][col].equals(LASER) && !this.grid[r][col].equals(BEAM)){
                break;
            }
        }
    }


    /**
     * Remove laser helper function
     * @param r integer representing the lasers row position
     * @param c integer representing the lasers column position
     */
    public void removeLaser(int r, int c) {
        // North
        for (int row = r; row >= 0; row--) {
            if (this.grid[row][c].equals(BEAM)) {
                this.grid[row][c] = EMPTY;
            } else if (!this.grid[row][c].equals(LASER)) {
                break;
            }
        }

        // South
        for (int row = r; row < this.size[0]; row++) {
            if (this.grid[row][c].equals(BEAM)) {
                this.grid[row][c] = EMPTY;
            } else if (!this.grid[row][c].equals(LASER)) {
                break;
            }
        }

        // East
        for (int col = c; col < this.size[1]; col++) {
            if (this.grid[r][col].equals(BEAM)) {
                this.grid[r][col] = EMPTY;
            } else if (!this.grid[r][col].equals(LASER)) {
                break;
            }
        }

        // West
        for (int col = c; col >= 0; col--) {
            if (this.grid[r][col].equals(BEAM)) {
                this.grid[r][col] = EMPTY;
            } else if (!this.grid[r][col].equals(LASER)) {
                break;
            }
        }

        // re-deploy any lasers that crossed the removed laser
        this.grid[r][c] = EMPTY;
        int col = 0;
        int row = 0;
        int arraySize = this.size[0] * this.size[1];
        while(arraySize > 0){
            if(this.grid[row][col].equals(LASER)){
                shootLaser(row, col);
            }
            col++;
            if(col > this.size[1] - 1){
                col = 0;
                row ++;
            }
            arraySize--;
        }
    }


    /**
     * Verify laser helper function
     * @param r integer representing the lasers row position
     * @param c integer representing the lasers column position
     */
    public boolean verifyLaser(int r, int c){

        // North
        for(int row = r - 1; row >= 0; row--){
            if(this.grid[row][c].equals(LASER)){
                return false;
            } else if(!this.grid[row][c].equals(LASER) && !this.grid[row][c].equals(BEAM)){
                break;
            }
        }

        // South
        for(int row = r + 1; row < this.size[0]; row++){
            if(this.grid[row][c].equals(LASER)){
                return false;
            } else if(!this.grid[row][c].equals(LASER) && !this.grid[row][c].equals(BEAM)){
                break;
            }
        }

        // East
        for(int col = c + 1; col < this.size[1]; col++){
            if(this.grid[r][col].equals(LASER)){
                return false;
            } else if(!this.grid[r][col].equals(LASER) && !this.grid[r][col].equals(BEAM)){
                break;
            }
        }

        // West
        for(int col = c - 1; col >= 0; col--){
            if(this.grid[r][col].equals(LASER)){
                return false;
            } else if(!this.grid[r][col].equals(LASER) && !this.grid[r][col].equals(BEAM)){
                break;
            }
        }
        return true;
    }


    /**
     * Verify laser helper function
     * @param r integer representing the lasers row position
     * @param c integer representing the lasers column position
     */
    public boolean verifyColumn(int r, int c){
        if(this.grid[r][c].equals(ANYNUM)){
            return true;
        }

        int value = Integer.parseInt(this.grid[r][c]);
        int numLasers = 0;

        //North
        if(r > 0) {
            if (this.grid[r - 1][c].equals(LASER)) {
                numLasers++;
            }
        }

        //South
        if(r < this.size[0] - 1){
            if (this.grid[r + 1][c].equals(LASER)) {
                numLasers++;
            }
        }

        //East
        if(c > 0){
            if (this.grid[r][c - 1].equals(LASER)) {
                numLasers++;
            }
        }

        //West
        if(c < this.size[1] - 1){
            if (this.grid[r][c + 1].equals(LASER)) {
                numLasers++;
            }
        }

        if(value == numLasers){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Add laser to slot(r,c)
     * @param r the row number of the slot
     * @param c the column number of the slot
     */
    public void add(int r,int c){
        if(r <= this.size[0] - 1 && c <= this.size[1] - 1
                && r>=0 && c>=0
                && (this.grid[r][c].equals(EMPTY) || this.grid[r][c].equals(BEAM))){
            this.grid[r][c]=LASER;
            shootLaser(r, c);
        }
        else{
        }
    }

    /**
     * Verify safe correctness.
     * @return boolean representing the validity of the safe configuration
     */
    public boolean verify() {
        int col = 0;
        int row = 0;
        int arraySize = this.size[0] * this.size[1];

        while (arraySize > 0) {
            if (this.grid[row][col].equals(EMPTY)) {
                return false;

            }
            else if (this.grid[row][col].equals(LASER)){
                if (!verifyLaser(row, col)){
                    return false;
                }

            }
            else if (!this.grid[row][col].equals(BEAM)) {
                if (!verifyColumn(row, col)){
                    return false;
                }
            }

            col++;
            if (col > this.size[1] - 1) {
                col = 0;
                row++;
            }
            arraySize--;

        }
        return true;
    }
}
