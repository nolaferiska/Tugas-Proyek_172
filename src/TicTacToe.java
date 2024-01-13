import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class TicTacToe extends JFrame implements ItemListener, ActionListener {
    int i, j, ii, jj, x, y, yesnull;
    int a[][] = {{10, 1, 2, 3, 11}, {10, 1, 4, 7, 11}, {10, 1, 5, 9, 11}, {10, 2, 5, 8, 11},
            {10, 3, 5, 7, 11}, {10, 3, 6, 9, 11}, {10, 4, 5, 6, 11}, {10, 7, 8, 9, 11}};
    int a1[][] = {{10, 1, 2, 3, 11}, {10, 1, 4, 7, 11}, {10, 1, 5, 9, 11}, {10, 2, 5, 8, 11},
            {10, 3, 5, 7, 11}, {10, 3, 6, 9, 11}, {10, 4, 5, 6, 11}, {10, 7, 8, 9, 11}};

    boolean state, type, set;

    String player1 = "<html><font size='7' color='#FF5733'>X</font></html>"; // Warna oranye tua
    String player2 = "<html><font size='7' color='#33A1DE'>O</font></html>"; // Warna biru langit

    Checkbox c1, c2;
    JLabel l1, l2;
    JButton b[] = new JButton[9];
    JButton reset;

    int player1Score = 0;
    int player2Score = 0;

    JLabel player1Label;
    JLabel player2Label;

    public void showButton() {
        x = 10;
        y = 10;
        j = 0;
        state = true;

        for (i = 0; i <= 8; i++, x += 100, j++) {
            b[i] = new JButton();
            if (j == 3) {
                j = 0;
                y += 100;
                x = 10;
            }
            b[i].setBounds(x, y, 100, 100);
            add(b[i]);
            b[i].addActionListener(this);
            b[i].setText(null);
            state = !state;
        }

        reset = new JButton("RESET");
        reset.setBounds(100, 350, 100, 50);
        add(reset);
        reset.addActionListener(this);

        player1Label = new JLabel("Player X Score: 0");
        player1Label.setBounds(10, 300, 150, 40);
        player1Label.setForeground(new Color(255, 87, 51)); // Warna oranye tua
        add(player1Label);

        player2Label = new JLabel("Player O Score: 0");
        player2Label.setBounds(160, 300, 150, 40);
        player2Label.setForeground(new Color(51, 161, 222)); // Warna biru langit
        add(player2Label);
    }

    public void highlightWinnerButtons(int index1, int index2, int index3) {
        Border thinBlueBorder = BorderFactory.createLineBorder(new Color(0, 102, 204), 3); // Warna biru muda
        b[index1].setBorder(thinBlueBorder);
        b[index2].setBorder(thinBlueBorder);
        b[index3].setBorder(thinBlueBorder);
    }

    public void check(int num1) {
        for (ii = 0; ii <= 7; ii++) {
            for (jj = 1; jj <= 3; jj++) {
                if (a[ii][jj] == num1) {
                    a[ii][4] = 11;
                }
            }
        }
    }

    public void complogic(int num) {
        for (i = 0; i <= 7; i++) {
            for (j = 1; j <= 3; j++) {
                if (a[i][j] == num) {
                    a[i][0] = 11;
                    a[i][4] = 10;
                }
            }
        }
        for (i = 0; i <= 7; i++) {
            set = true;
            if (a[i][4] == 10) {
                int count = 0;
                for (j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getText() != null) {
                        count++;
                    } else {
                        yesnull = a[i][j];
                    }
                }
                if (count == 2) {
                    b[yesnull - 1].setText(player2);
                    this.check(yesnull);
                    set = false;
                    break;
                }
            } else if (a[i][0] == 10) {
                for (j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getText() == null) {
                        b[(a[i][j] - 1)].setText(player2);
                        this.check(a[i][j]);
                        set = false;
                        break;
                    }
                }
                if (set == false)
                    break;
            }

            if (set == false)
                break;
        }
    }

    TicTacToe() {
        super("Tic Tac Toe by Nola Feriska");

        // Setting warna latar belakang
        getContentPane().setBackground(new Color(255, 253, 230)); // Warna kuning muda

        CheckboxGroup cbg = new CheckboxGroup();
        c1 = new Checkbox("vs Computer", cbg, false);
        c2 = new Checkbox("vs Friend", cbg, false);
        c1.setBounds(120, 80, 100, 40);
        c2.setBounds(120, 150, 100, 40);
        c1.setFont(new Font("Arial", Font.PLAIN, 14));
        c2.setFont(new Font("Arial", Font.PLAIN, 14));
        add(c1);
        add(c2);
        c1.addItemListener(this);
        c2.addItemListener(this);

        state = true;
        type = true;
        set = true;

        setLayout(null);
        setSize(330, 450);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void itemStateChanged(ItemEvent e) {
        if (c1.getState()) {
            type = false;
        } else if (c2.getState()) {
            type = true;
        }
        remove(c1);
        remove(c2);
        repaint(0, 0, 330, 450);
        showButton();
    }

    public void actionPerformed(ActionEvent e) {
        if (type) {
            if (e.getSource() == reset) {
                resetGame();
            } else {
                for (i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i]) {
                        if (b[i].getText() == null) {
                            if (state) {
                                b[i].setText(player2);
                                state = false;
                            } else {
                                b[i].setText(player1);
                                state = true;
                            }
                        }
                    }
                }
            }
            checkDraw();
            checkWinner();
        } else {
            if (e.getSource() == reset) {
                resetGame();
            } else {
                for (i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i]) {
                        if (b[i].getText() == null) {
                            b[i].setText(player1);
                            if (b[4].getText() == null) {
                                b[4].setText(player2);
                                this.check(5);
                            } else {
                                this.complogic(i);
                            }
                        }
                    }
                }
            }
            checkDraw();
            checkWinner();
        }
    }

    public void checkDraw() {
        boolean isDraw = true;
        for (i = 0; i < 9; i++) {
            if (b[i].getText() == null) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            JOptionPane.showMessageDialog(TicTacToe.this, "DRAW! Click reset", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    public void checkWinner() {
        for (i = 0; i <= 7; i++) {
            String text1 = b[(a[i][1] - 1)].getText();
            String text2 = b[(a[i][2] - 1)].getText();
            String text3 = b[(a[i][3] - 1)].getText();
            if (text1 != null && text1.equals(text2) && text2.equals(text3)) {
                if (text1.equals(player1)) {
                    highlightWinnerButtons(a[i][1] - 1, a[i][2] - 1, a[i][3] - 1);
                    player1Score++;
                    updateScores();
                    JOptionPane.showMessageDialog(TicTacToe.this, "Player X wins! Click reset", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                } else if (text1.equals(player2)) {
                    highlightWinnerButtons(a[i][1] - 1, a[i][2] - 1, a[i][3] - 1);
                    player2Score++;
                    updateScores();
                    JOptionPane.showMessageDialog(TicTacToe.this, "Player O wins! Click reset", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                }
                break;
            }
        }
    }

    public void resetGame() {
        for (i = 0; i <= 8; i++) {
            b[i].setText(null);
            b[i].setBorder(null);
        }
        for (i = 0; i <= 7; i++)
            for (j = 0; j <= 4; j++)
                a[i][j] = a1[i][j];

        updateScores();
    }

    public void updateScores() {
        player1Label.setText("Player X Score: " + player1Score);
        player2Label.setText("Player O Score: " + player2Score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                     | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
            new TicTacToe();
        });
    }
}

