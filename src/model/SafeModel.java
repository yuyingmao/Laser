package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Scanner;

/**
 * The safe model for LasersModel that contains the information that LasersModel needs.
 * @author Connor Milligan
 * @author Yuying Mao
 */

public class SafeModel extends Observable {
    public final static String HORI_DIVIDE = "-";
    public final static String VERT_DIVIDE = "|";

    public final static String EMPTY=".";
    public final static String ANYNUM="X";
    public final static String LASER="L";
    public final static String BEAM="*";
    private String[][] initGrid;
    private String[][] grid;
    private Integer[] size;
    private String initM;
    private String message;
    private Integer[] errorSlot;

    /**
     * Constructor. Read the file and initialize the grid.
     * @param filename the file name.
     * @throws FileNotFoundException if file not found
     */
    public SafeModel(String filename) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filename));
        this.size = new Integer[2];
        this.size[0] = file.nextInt();
        this.size[1] = file.nextInt();
        this.errorSlot=new Integer[2];
        file.nextLine();
        this.initGrid=new String[this.size[0]][this.size[1]];
        this.grid=new String[this.size[0]][this.size[1]];
        for(int row=0;row<this.size[0];row++){
            String[] next=file.nextLine().split(" ");
            for(int col=0;col<this.size[1];col++){
                this.initGrid[row][col]=next[col];
            }
        }
        file.close();
        for(int row=0;row<this.size[0];row++){
            for(int col=0;col<this.size[1];col++){
                this.grid[row][col]=this.initGrid[row][col];
            }
        }
        this.initM=filename+" is loaded";
        this.message=this.initM;
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
            } else if(!this.grid[row][c].equals(LASER)
                    && !this.grid[row][c].equals(BEAM)){
                break;
            }
        }

        // South
        for(int row = r; row < this.size[0]; row++){
            if(this.grid[row][c].equals(EMPTY)){
                this.grid[row][c] = BEAM;
            } else if(!this.grid[row][c].equals(LASER)
                    && !this.grid[row][c].equals(BEAM)){
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
            this.message="Laser added at: ("+r+", "+c+")";
        }
        else{
            this.message="Error adding laser at: (" +r+", "+c+")";
        }
    }

    /**
     * Display the grid.
     */
    public void display(){
        String result="  ";
        for(int j=0;j<this.size[1];j++){
            if(j>9){
                result+=Integer.toString(j%10)+" ";
            }
            else{
                result+=j+" ";
            }
        }
        result+="\n  ";
        for(int j=0; j<2*this.size[1]-1;j++){
            result+=HORI_DIVIDE;
        }
        result+="\n";

        for(int i=0;i<this.size[0];i++){
            if(i>9){
                result+=Integer.toString(i/10);
            }
            else{
                result+=i;
            }
            result+=VERT_DIVIDE;
            for(int j=0;j<this.size[1];j++){
                result+=this.grid[i][j]+" ";
            }
            result+="\n";
        }
        result = result.substring(0, result.length() - 2);
        System.out.println(result);
    }

    /**
     * Print the help message.
     */
    public void help(){
        this.message="a|add r c: Add laser to (r,c)\n"
                +"d|display: Display safe\n"
                +"h|help: Print this help message\n"
                +"q|quit: Exit program\n"
                +"r|remove r c: Remove laser from (r,c)\n"
                +"v|verify: Verify safe correctness";
    }

    /**
     * Exit program.
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * Remove laser from (r, c)
     * @param r the row number of the slot.
     * @param c the column number of the slot.
     */
    public void remove(int r,int c){
        if(r <= size[0] - 1 && c <= size[1] - 1 && this.grid[r][c].equals(LASER) && r>=0 && c>=0){
            removeLaser(r, c);
            this.message="Laser removed at: ("+r+", "+c+")";
        }
        else{
            this.message="Error removing laser at: ("+r+", "+c+")";
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
                this.message="Error verifying at: ("
                        + String.valueOf(row) + ", " + String.valueOf(col) + ")";
                this.errorSlot[0]=row;
                this.errorSlot[1]=col;
                return false;

            }
            else if (this.grid[row][col].equals(LASER)){
                if (!verifyLaser(row, col)){
                    this.message="Error verifying at: ("
                            + String.valueOf(row) + ", " + String.valueOf(col) + ")";
                    this.errorSlot[0]=row;
                    this.errorSlot[1]=col;
                    return false;
                }

            }
            else if (!this.grid[row][col].equals(BEAM)) {
                if (!verifyColumn(row, col)){
                    this.message="Error verifying at: ("
                            + String.valueOf(row) + ", " + String.valueOf(col) + ")";
                    this.errorSlot[0]=row;
                    this.errorSlot[1]=col;
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
        this.message="Safe is fully verified!";
        return true;
    }

    /**
     * resets the grid and the message
     */
    public void reset(){
        for(int row=0;row<this.size[0];row++){
            for(int col=0;col<this.size[1];col++){
                this.grid[row][col]=this.initGrid[row][col];
            }
        }
        resetErrorSlot();
        this.message=this.initM;
    }

    /**
     * the current grid Getter function.
     * @return the current grid.
     */
    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }

    /**
     * the current message Getter function.
     * @return the current message
     */
    public String getMessage() {
        return message;
    }

    /**
     * the current message Setter function.
     * @param message the given message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * the current errorSlot Getter function.
     * @return the current errorSlot.
     */
    public Integer[] getErrorSlot(){ return this.errorSlot; }

    /**
     * resets the errorSlot array. Sets it to empty.
     */
    public void resetErrorSlot(){
        this.errorSlot=new Integer[2];
    }
}
