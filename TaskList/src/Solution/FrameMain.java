package Solution;

import util.ArrayUtils;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;


public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonOperation;
    private JTable tableOutput;
    private JScrollPane scrollPaneTableInput;
    private JScrollPane scrollPaneTableOutput;

    private final JFileChooser fileChooserOpen;


    public FrameMain() {
        this.setTitle("FrameMain");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 150, false, true, false, true);
        JTableUtils.initJTableForArray(tableOutput, 190, false, true, false, true);
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

        JTableUtils.writeArrayToJTable(tableInput, new String[]{"Привет", "меня", "зовут", "Николай", "Меньшиков"});

        this.pack();


        buttonLoadInputFromFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    String[] arr = ArrayUtils.readLinesFromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput, arr);
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonOperation.addActionListener(actionEvent -> {
            try {
                String[] matrix = JTableUtils.readStringArrayFromJTable(tableInput);
                assert matrix != null;
                SimpleLinkedList<String> list = SimpleLinkedList.toLinkedList(matrix);
                String[] result = SimpleLinkedList.toString(SimpleLinkedList.longestWord(list));
                JTableUtils.writeArrayToJTable(tableOutput, result);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}
