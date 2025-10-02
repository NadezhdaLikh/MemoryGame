// Note the count variable is not reset when a new game is pressed

import javax.swing.*; // Библиотека для создания графического интерфейса на языке Java
import java.awt.*; // Abstract Window Toolkit (AWT) — это исходная платформо-независимая оконная библиотека графического интерфейса (Widget toolkit) языка Java
// Виджет — это небольшое графическое приложение, которое выводит информацию на рабочий стол компьютера, экран смартфона/планшета или страницу сайта.
import java.awt.event.*;
// import java.io.*;
import java.util.*;
/*
Contains the collections framework, legacy collection classes, event model, date
and time facilities, internationalization, and miscellaneous utility
classes (a string tokenizer, a random-number generator, and a bit array).
*/

/*
Declare an event handler class and specify that the class either implements an ActionListener interface
or extends a class that implements an ActionListener interface.
*/
public class MemoryGame implements ActionListener { // Публичный класс MemoryGame реализует интерфейс ActionListener.  Класс, связанный с каким-то интерфейсом, должен реализовать все его методы.
    /*
    You implement an action listener to define what should be done when a user performs certain operation.
    An action event occurs, whenever an action is performed by the user.
    Examples: When the user clicks a button, chooses a menu item, presses Enter in a text field.
    The result is that an actionPerformed message is sent to all action listeners that are registered on the relevant component.
    */
    JFrame frame = new JFrame("Memory Game");
    /*
    JFrame is a top-level container that provides a window on the screen.
    A frame is actually a base window on which other components rely, namely the menu bar, panels, labels, text fields, buttons, etc.

    JPanel, a part of the Java Swing package, is a container that can store a group of components.
    The main task of JPanel is to organize components, various layouts can be set
    in JPanel which provide better organization of components, however, it does not have a title bar.
    */
    JPanel field = new JPanel(); // JPanel() creates a new panel with a flow layout
    /*
    In Windows Forms, FlowLayoutPanel control is used to arrange its child controls in a horizontal or vertical flow direction.
    Or in other words, FlowLayoutPanel is a container which is used to organize
    different or same types of controls in it either horizontally or vertically.
    */
    JPanel menu1 = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini_menu2 = new JPanel();
    JPanel main_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct_screen = new JPanel();

    JButton btn[] = new JButton[20]; // Максимальное кол-во уникальных карточек - 10, следовательно, максимальное суммарное кол-во карточек на игровом поле - 20
    JButton start = new JButton("Start"); // It creates a button with the specified text
    JButton finish = new JButton("Exit");

    JButton easy = new JButton("Easy");
    JButton hard = new JButton("Hard");
    JButton instruct = new JButton("Instructions");

    JButton goBack = new JButton("Main Menu");

    Random randomGenerator = new Random(); // Create instance of Random class
    private boolean purgatory = false; // Purgatory = having the quality of cleansing or purifying

    int level = 0;
    int score = 0; // Количество набранных игроком штрафных очков

    String[] board; // Declaration of the String Array without specifying the size
	/*
	A String Array is an Array of a fixed number of String values.
	A String is a sequence of characters. Generally, a string is
	an immutable object, which means the value of the string can not be changed.
	The String Array works similarly to other data types of Array.
	*/

    Boolean shown = true;
    /*
    The Boolean class wraps a value of the primitive type boolean in an object.
    An object of type Boolean contains a single field whose type is boolean.
    */

    int temp1 = 30;
    int temp2 = 30;
    String[] board_values = new String[10]; // Кол-во уникальных значений карточек
    boolean difficulty = true;

    private JLabel menu1_text = new JLabel("Choose game's level of difficulty, enter a number of cards from 1 to 10:");
    private JTextField menu1_input = new JTextField(2); // JTextField(int columns) creates a new empty TextField with the specified number of columns
    private JTextArea instructions = new JTextArea("When the game begins, the screen is filled with pairs of buttons (or cards).\nMemorize their placement.\nAfter you press any button, all cards will be flipped.\nYour goal is to find two matching cards in sequence.\nThe player wins when there are no cards left.\nBe advised, the player receives penalty points for each mismatch.\nGood luck:)\n" +
            "Enter a preferred number of unique cards between 1 and 10,\nselect level of difficulty - easy or hard, then press start.");

    public MemoryGame() {
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(main_screen);
        frame.getContentPane().setBackground(Color.yellow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application

		/*
		A border layout lays out a container, arranging and resizing its components to fit in five regions: north, south, east, west, and center.
		Each region may contain no more than one component, and is identified by a corresponding constant: NORTH, SOUTH, EAST, WEST, and CENTER.
		When adding a component to a container with a border layout, use one of these five constants.

		Менеджер расположения (layout manager) определяет, каким образом на форме будут располагаться компоненты.
		Независимо от платформы, виртуальной машины, разрешения и размеров экрана менеджер расположения гарантирует, что компоненты
		будут иметь предпочтительный или близкий к нему размер и располагаться в том порядке, который был указан
		программистом при создании программы.
		*/

        main_screen.setLayout(new BorderLayout());
        menu1.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini_menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
		/*
		The FlowLayout class provides a very simple layout manager that is used, by default, by the JPanel objects.
		The FlowLayout class puts components in a row, sized at their preferred size.
		If the horizontal space in the container is too small to put all the components in one row, the FlowLayout class uses multiple rows.
		If the container is wider than necessary for a row of components, the row is, by default, centered horizontally within the container.
		*/
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER)); // This value indicates that each row of components should be centered.

        main_screen.add(menu1, BorderLayout.NORTH);
        main_screen.add(menu2, BorderLayout.CENTER);
        main_screen.add(menu3, BorderLayout.SOUTH);
        // menu1_text.setForeground(new Color(64, 20,82));
        menu1_input.setForeground(Color.RED);
        menu1.add(menu1_text);
        menu1.add(menu1_input);
        menu2.add(mini_menu2, BorderLayout.CENTER);
        mini_menu2.add(easy, BorderLayout.NORTH);
        mini_menu2.add(hard, BorderLayout.NORTH);
        mini_menu2.add(instruct, BorderLayout.SOUTH);

        start.addActionListener(this);
        start.setEnabled(true);
        menu3.add(start);

        finish.addActionListener(this);
        finish.setEnabled(true);
        menu3.add(finish);

        easy.addActionListener(this);
        easy.setEnabled(true);

        hard.addActionListener(this);
        hard.setEnabled(true);

        instruct.addActionListener(this);
        instruct.setEnabled(true);

        // frame.pack(); // The pack method sizes the frame so that all its contents are at or above their preferred sizes
        frame.setVisible(true); // To present the frame on the screen
    }

    public void setUpGame(int x, Boolean difficulty) {
        level = x;
        clearMain();

        board = new String[2 * x];
        for (int i = 0; i < x * 2; i++) {
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(225, 225, 225)); // Цвет карточек
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]); // Размещаем карточки на игровом поле
        }

        String[] difEasy = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] difHard = {"square", "circle", "rectangle", "heart", "star", "oval", "hexagon", "triangle", "trapezoid", "rhombus"};
        // difHard: квадрат, круг, прямоугольник, сердечко, звездочка, овал, шестиугольник, треугольник, трапеция, ромб
        if (difficulty) board_values = difHard; // If difficulty is true, make the game harder and use difHard
        else board_values = difEasy;

        for (int i = 0; i < x; i++) {
            for (int z = 0; z < 2; z++) {
                while (true) {
                    int y = randomGenerator.nextInt(x * 2);
                    if (board[y] == null) {
                        btn[y].setText(board_values[i]);
                        board[y] = board_values[i];
                        break;
                    }
                }
            }
        }
        createBoard();
    }

    public void hideField(int x) { // This sets all the boxes blank
        for (int i = 0; i < x * 2; i++) {
            btn[i].setText("");
        }
        shown = false;
    }

    public void switchSpot(int i) { // This will switch the current spot to either blank or what it should have
        if (!Objects.equals(board[i], "done")) { // When a match is correctly chosen, it will no longer switch when pressed
            if (Objects.equals(btn[i].getText(), "")) {
                btn[i].setText(board[i]);
            } else {
                btn[i].setText("");
            }
        }
    }

    public void showSpot(int i) {
        btn[i].setText(board[i]);
    }

    public void showField(int x, String a[]) { // This shows all the symbols on the field
        for (int i = 0; i < x * 2; i++) {
            btn[i].setText(a[i]);
        }
        shown = true;
    }

    void waitABit() { // This was an attempt at fixing the glitch I told you about
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
    }

    public boolean checkWin() { // Checks if every spot is labeled as done
        for (int i = 0; i < level * 2; i++) {
            if (!Objects.equals(board[i], "done"))
                return false;
        }
        winner();
        return true;
    }

    public void winner() {
        main_screen.remove(field);
        end_screen.setLayout(new BorderLayout());
        main_screen.add(end_screen, BorderLayout.CENTER);
        JLabel congrats_text = new JLabel("Congratulations, you won! The number of your penalty points is " + score);
        end_screen.add(congrats_text);
        // goBack.setPreferredSize(new Dimension(40, 40));
        end_screen.add(goBack, BorderLayout.SOUTH);
        goBack.setEnabled(true);
        goBack.addActionListener(this);
    }

    public void goToMainScreen() {
        new MemoryGame();
    }

    public void createBoard() { // This is just GUI stuff to show the board
        field.setLayout(new BorderLayout());
        main_screen.add(field, BorderLayout.CENTER);
        field.setLayout(new GridLayout(5, 4, 2, 2));
        /*
        Creates a grid layout with the specified number of rows and columns.
        In addition, the horizontal and vertical gaps are set to the specified values.
        Horizontal gaps are places between each of columns.
        Vertical gaps are placed between each of the rows.
        */
        field.setBackground(Color.DARK_GRAY); // Цвет игрового поля
        field.requestFocus();
    }

    public void clearMain() { // Clears the main menu, so I can add the board or instructions
        main_screen.remove(menu1);
        main_screen.remove(menu3);
        main_screen.remove(menu2);

        main_screen.revalidate();
        main_screen.repaint();
    }

    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource(); // getSource() returns the object on which the Event initially occurred
        if (purgatory) {
            switchSpot(temp1);
            switchSpot(temp2);
            score++;
            temp1 = level * 2;
            temp2 = 30;
            purgatory = false;
        }
        if (source == start) {
            try {
                level = Integer.parseInt(menu1_input.getText());
            } catch (Exception e) {
                level = 1;
            }
            setUpGame(level, difficulty); // Level is between 1 and 10, difficulty is true or false
        }
        if (source == finish) { // Quits
            System.exit(0);
        }
        if (source == instruct) { // This shows the instruction screen
            clearMain();

            main_screen.add(instruct_screen, BorderLayout.NORTH);

            JPanel one = new JPanel();
            one.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel two = new JPanel();
            two.setLayout(new FlowLayout(FlowLayout.CENTER));
            instruct_screen.setLayout(new BorderLayout());
            instruct_screen.add(one, BorderLayout.NORTH);
            instruct_screen.add(two, BorderLayout.SOUTH);

            one.add(instructions);
            two.add(goBack);
            goBack.addActionListener(this);
            goBack.setEnabled(true);
        }
        if (source == goBack) { // Back to the main screen
            frame.dispose();
            goToMainScreen();
        }
        if (source == easy) { // If easy is clicked, it turns blue and hard remains black
            difficulty = false;
            easy.setForeground(Color.RED);
            hard.setForeground(Color.BLACK);
        } else if (source == hard) {
            difficulty = true;
            hard.setForeground(Color.RED);
            easy.setForeground(Color.BLACK);
        }

        for (int i = 0; i < level * 2; i++) { // Gameplay when a button is pressed
            if (source == btn[i]) {
                if (shown) {
                    hideField(level); // If first time, hides field
                } else { // Otherwise play
                    switchSpot(i);
                    if (temp1 >= level * 2) {
                        temp1 = i;
                    } else {
                        if ((!Objects.equals(board[temp1], board[i])) || (temp1 == i)) {
                            temp2 = i;
                            purgatory = true;
                        } else {
                            board[i] = "done";
                            board[temp1] = "done";
                            checkWin();
                            temp1 = level * 2;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new MemoryGame(); // Calling the class constructor
    }
}
	
