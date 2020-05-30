import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class GUI extends JPanel{

    /**
     * WORKING ELEMENTS:
     *
     * [x] Read existing table elements from tasks.txt
     * [x] Create new incomplete elements and add to table dynamically
     * [x] All necessary methods
     *
     * ELEMENTS TO ADD:
     *
     * [] Editing task name dynamically
     * [] Changing task status dynamically
     * [] Get rid of link column
     * [] Go to link in chrome
     * [] Make it look nice
     * [] Figure out whole spacing issue
     *
     */
    public static void main(String[] args) throws ParseException, IOException {
        createGUI();
    }

    public GUI() throws ParseException, IOException {
        //Task one = new Task("yep",1,"11/10/2001","www.google.com");
        //Task two = new Task("mhm",0,"11/10/2001","www.google.com");
        //Task three = new Task("oh ye",0,"11/10/2001","www.google.com");

        Task[] tsk = load();
        Table main = new Table(tsk);

        String[] columnNames = {"Task", "Status", "Due Date","Link"};

        String[] stats = {"Incomplete","Done"};
        Object[][] incdata = new Object[main.getincNum()][4];
        Object[][] compdata = new Object[main.getcompNum()][4];

        Task[] inc =  main.getincTasks(); // throw incomplete tasks into table1
        for(int i=0; i < inc.length;i++){
            incdata[i] = new Object[]{inc[i].getTask(), stats[inc[i].getStatus()], inc[i].getDate(), inc[i].getLink()};
        }

        Task[] comp = main.getcompTasks();
        for(int i=0; i < comp.length;i++){
            compdata[i] = new Object[]{comp[i].getTask(), stats[comp[i].getStatus()], comp[i].getDate(), comp[i].getLink()};
        }

        JTable inctable = new JTable(incdata, columnNames);
        inctable.setPreferredScrollableViewportSize(new Dimension(800, 70/4 * main.getincNum()));
        inctable.setFillsViewportHeight(true);

        JTable comptable = new JTable(compdata, columnNames);
        comptable.setPreferredScrollableViewportSize(new Dimension(800, 70/4 * main.getcompNum()));
        comptable.setFillsViewportHeight(true);

        TableColumn column1 = null;
        TableColumn column2 = null;
        for (int i = 0; i < 4; i++) {
            column1 = inctable.getColumnModel().getColumn(i);
            column2 = comptable.getColumnModel().getColumn(i);
            if (i == 2|| i == 0) {
                column1.setPreferredWidth(500);
                column2.setPreferredWidth(500);//third column is bigger
            } else {
                column1.setPreferredWidth(250);
                column2.setPreferredWidth(250);
            }
        }
        JScrollPane scrollPane = new JScrollPane(inctable);
        JScrollPane scrollPane2 = new JScrollPane(comptable);

        //Add the scroll pane to this panel.
        add(scrollPane);
        add(scrollPane2);
    }

    private static Task[] load() throws IOException, ParseException { //loading previously made tasks
        File file = new File("/Users/kayacelebi/Projects/Organizer/data/tasks.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        ArrayList<Task> loaded = new ArrayList<>();
        while((st = br.readLine()) != null){
            String[] text = st.split("\\s+");
            if(text.length==4) {
                String link = text[3];
                String date = text[2];
                int status = Integer.parseInt(text[1]);
                String taskname = text[0].replace("_"," ");
                Task t = new Task(taskname, status, date, link, 1);
                loaded.add(t);
            }

        }
        Task[] fin= new Task[loaded.size()];
        for(int i=0; i<fin.length;i++)
            fin[i] = loaded.get(i);
        return fin;
    }

    public static void createGUI() throws ParseException, IOException {

        JFrame frame = new JFrame("Task Organizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);

        //Create and set up the content pane.
        GUI newContentPane = new GUI();
        newContentPane.setOpaque(false); //content panes must be opaque
        frame.setContentPane(newContentPane);

        JTextField newtask = new JTextField();
        JTextField date = new JTextField();
        JTextField url = new JTextField();

        newtask.setText("Task Name");
        date.setText("Date");
        url.setText("Link");

        newtask.setBounds(300,300,150,40);
        date.setBounds(300,400,150,40);
        url.setBounds(300,500,150,40);

        JButton b = new JButton("Submit");
        b.setBounds(300,350,100,30);
        b.addActionListener(f -> {
            String txt1 = newtask.getText();
            String txt2 = date.getText();
            String txt3 = url.getText();
            System.out.println(txt1);
            System.out.println(txt2);
            System.out.println(txt3);

            if(txt1.length()!= 0 && txt2.length()!=0 && txt3.length() != 0) {
                Task t = null;
                try {
                    t = new Task(txt1, 0, txt2, txt3, 0);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //changed = true;
                frame.setVisible(false);
                try {
                    createGUI();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Display the window.
        //frame.pack();
        frame.add(newtask);
        frame.add(date);
        frame.add(url);
        frame.add(b);
        frame.setVisible(true);
    }

}
