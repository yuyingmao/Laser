package model;
import java.io.FileNotFoundException;
import java.util.Observable;

/**
 * The model for LasersPTUI and LasersGUI.
 * @author Yuying Mao
 * @author  Connor Milligan
 */
public class LasersModel extends Observable {

    private SafeModel safe;

    /**
     * The constructor of LasersModel. Create a new SafeModel.
     * @param filename the file name.
     * @throws FileNotFoundException if file not found
     */
    public LasersModel(String filename) throws FileNotFoundException{
        safe=new SafeModel(filename);
    }

    /**
     * A utility method that indicates the model has changed and
     * notifies observers
     */
    private void announceChange() {
        setChanged();
        notifyObservers();
    }

    /**
     * adds a laser at (r, c) and notifies observers.
     * @param r the row number
     * @param c the column number
     */
    public void add(int r,int c){
        this.safe.add(r,c);
        announceChange();
    }

    /**
     * displays the grid.
     */
    public void display(){
        this.safe.display();
    }

    /**
     * displays the help comments.
     */
    public void help(){
        this.safe.help();
        System.out.println(this.getMessage());
    }

    /**
     * quit function.
     */
    public void quit(){
        this.safe.quit();
        announceChange();
    }

    /**
     * removes the laser from (r, c) and notifies observers
     * @param r the row number
     * @param c the column number
     */
    public void remove(int r,int c){
        this.safe.remove(r,c);
        announceChange();
    }

    /**
     * Verify safe correctness and notifies observers.
     */
    public boolean verify(){
        boolean v= this.safe.verify();
        announceChange();
        return v;
    }

    /**
     * resets the SafeModel and notifies observers
     */
    public void reset(){
        this.safe.reset();
        announceChange();
    }

    /**
     * to decide whether should add a laser or remove a laser.
     * if there is a laser at (r,c) already, remove it.
     * else, add a laser at (r,c)
     * @param row the row number
     * @param col the column number
     */
    public void click(int row,int col){
        if(this.safe.getGrid()[row][col].equals("L")){
            remove(row,col);
        }
        else{
            add(row,col);
        }
    }

    /**
     * reset the array that contains the error slot information.
     */
    public void resetErrorSlot(){
        this.safe.resetErrorSlot();
    }

    /**
     * the current safe grid getter function
     * @return the current safe grid
     */
    public String[][] getGrid(){
        return this.safe.getGrid();
    }

    /**
     * the current safe grid setter function and notifies observers.
     * @param grid the grid which the current grid need to be changed to.
     */
    public void setGrid(String[][] grid){
        this.safe.setGrid(grid);
        announceChange();
    }

    /**
     *Getter function. returns the current message.
     * @return the current message
     */
    public String getMessage(){
        return this.safe.getMessage();
    }

    /**
     * Setter function. Sets the message to a given String and notifies observers.
     * @param message the given string.
     */
    public void setMessage(String message){
        this.safe.setMessage(message);
        announceChange();
    }

    /**
     * Getter function. returns the SafeModel.
     * @return the SafeModel.
     */
    public SafeModel getSafe() {
        return safe;
    }

    /**
     * Getter function. returns an integer array that holds the error slot
     * adding from verify method.
     * @return an integer array that holds the error slot
     */
    public Integer[] getErrorSlots() { return this.safe.getErrorSlot();}

    /**
     * Setter function. sets the SafeModel to a given SafeModel and notifies observers.
     * @param safe the given SafeModel
     */
    public void setSafe(SafeModel safe) {
        this.safe = safe;
        announceChange();
    }
}
