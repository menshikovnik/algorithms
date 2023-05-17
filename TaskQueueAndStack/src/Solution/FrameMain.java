package Solution;

import util.ArrayUtils;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Stack;


public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonOperationJavaDeque;
    private JTable tableOutput;
    private JScrollPane scrollPaneTableInput;
    private JScrollPane scrollPaneTableOutput;
    private JButton button1;
    private JTextField textField1;

    private final JFileChooser fileChooserOpen;


    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 50, false, true, false, true);
        JTableUtils.initJTableForArray(tableOutput, 50, false, true, false, true);
        //tableOutput.setEnabled(false);
        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);
        scrollPaneTableInput.setPreferredSize(new Dimension(-1, 90));
        scrollPaneTableOutput.setPreferredSize(new Dimension(-1, 90));

        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);

        JMenuBar menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        JMenu menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        JTableUtils.writeArrayToJTable(tableInput, new int[]{7, 5, 3, 1, 4, 6 ,2});

        this.pack();


        buttonLoadInputFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[] arr = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonOperationJavaDeque.addActionListener(actionEvent -> {
            try {
                int[] matrix = JTableUtils.readIntArrayFromJTable(tableInput);
                assert matrix != null;
                Stack<Integer> stack = new Stack<>();
                for (int value : matrix){
                    stack.add(value);
                }
                int[] resultArr = new int[stack.size()];
                int i = 0;
                for (int value : Solution.RestoreTheOriginalSequence(stack)){
                    resultArr[i] = value;
                    i++;
                }
                JTableUtils.writeArrayToJTable(tableOutput, resultArr);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        button1.addActionListener(actionEvent -> {
            try {
                int[] matrix = JTableUtils.readIntArrayFromJTable(tableInput);
                assert matrix != null;
                Stack<Integer> stack = new Stack<>();
                for (int value : matrix){
                    stack.add(value);
                }
                int[] resultArr = new int[stack.size()];
                int i = 0;
                for (int value : MyDeque.RestoreTheOriginalSequenceWithMyDeque(stack)){
                    resultArr[i] = value;
                    i++;
                }
                JTableUtils.writeArrayToJTable(tableOutput, resultArr);
            } catch (Exception e){
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}
