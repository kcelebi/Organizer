import java.lang.reflect.Array;
import java.util.ArrayList;

public class Table {

    private int incnumItems;
    private int compnumItems;
    private Task[] inctasks;
    private Task[] comptasks;

    public Table(Task[] arr){
        inctasks = arr;
        checkStatus();
    }

    public Task[] getincTasks(){
        return inctasks;
    }

    public Task[] getcompTasks(){
        return comptasks;
    }

    public int getincNum(){
        return incnumItems;
    }

    public int getcompNum(){
        return compnumItems;
    }

    public void checkStatus(){  //move any comp tasks to list
        ArrayList<Task> inc = new ArrayList<>();
        ArrayList<Task> comp = new ArrayList<>();
        for(Task t: inctasks){
            if(t.getStatus() == 1){
                comp.add(t);
            }
            else{
                inc.add(t);
            }
        }
        Task[] temp = new Task[inc.size()];
        Task[] temp2 = new Task[comp.size()];
        for(int i=0; i < temp.length; i++)
            temp[i] = inc.get(i);
        for(int i=0; i < temp2.length; i++)
            temp2[i] = comp.get(i);
        incnumItems = temp.length;
        compnumItems = temp2.length;
        inctasks =temp;
        comptasks = temp2;
    }

}
