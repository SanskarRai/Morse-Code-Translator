import javax.sound.sampled.LineUnavailableException;
import  javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MorseCodeTranslatorGUI extends JFrame implements KeyListener {

    private MorseCodeController morseCodeController;

    //textInputArea - user input (text to be translated t morse code)
    //morseCodeArea - translated text into more code area
    private JTextArea textInputArea, morseCodeArea;

    public MorseCodeTranslatorGUI() {
        //add text to the title bar
        super("Morse Code Translator");

        //sets the size of the frame to be 540x760 pixels
        setSize(new Dimension(540, 760));

        //prevents GUI from being resized
        setResizable(false);

        //setting layout to null allows us to manually position and set the size of components in our GUI
        setLayout(null);

        //exits program when closing the GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // changes the colour of the background
        getContentPane().setBackground(Color.decode("#264653"));

        // place the HUI in the middle of the screen
        setLocationRelativeTo(null);

        morseCodeController = new MorseCodeController();
        addGuiComponents();
    }
    private void addGuiComponents(){
        //title label
        JLabel titleLabel = new JLabel("Morse Code Translator");

        //changes the font size for the label and the font weight
        titleLabel.setFont(new Font("Dialog", Font.BOLD,32));

        //changes the font colour of the text to white
        titleLabel.setForeground(Color.WHITE);

        // centers text (relative to it container's width)
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //sets the x, y position and the width and height dimensions
        // to make sure that the title aligns to the centre of our GUI, we need it the same width
        titleLabel.setBounds(0,0,540,100);

        //text input
        JLabel textInputLabel = new JLabel("Text: ");
        textInputLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        textInputLabel.setBounds(20, 100,200,30);
        textInputLabel.setForeground(Color.WHITE);

        textInputArea = new JTextArea();
        textInputArea.setFont(new Font("Dialog", Font.PLAIN, 18));

        //makes it so that we are listening to key pressing whenever we are typing in this text area
        textInputArea.addKeyListener(this);

        // simulates a padding of 10px around the text area
        textInputArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //makes it so that the word wrap around the next line after reaching the end of the text area
        textInputArea.setLineWrap(true);

        //adds scrolling ability to input txt
        JScrollPane textInputScroll = new JScrollPane(textInputArea);
        textInputScroll.setBounds(20,132,484,236);

        //morse code input
        JLabel morseCodeInputLabel = new JLabel("Morse Code");
        morseCodeInputLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        morseCodeInputLabel.setForeground(Color.WHITE);
        morseCodeInputLabel.setBounds(20,390,200,30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        morseCodeArea.setEditable(false);
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);
        morseCodeArea.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JScrollPane morseInputScroll = new JScrollPane(morseCodeArea);
        morseInputScroll.setBounds(20,430,484,236);

        //play sound button
        JButton playSoundButton = new JButton("Play Button");
        playSoundButton.setBounds(210,680,100, 30);
        playSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundButton.setEnabled(false);

                Thread playMorseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            morseCodeController.playSound(morseCodeMessage);
                        }catch (LineUnavailableException lineUnavailableException) {
                            lineUnavailableException.printStackTrace();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }finally{
                            playSoundButton.setEnabled(true);
                        }
                    }
                });
                playMorseCodeThread.start();
            }
        });
        //add to GUI
        add(titleLabel);
        add(textInputLabel);
        add(textInputScroll);
        add(morseCodeInputLabel);
        add(morseInputScroll);
        add(playSoundButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            String inputText = textInputArea.getText();
            morseCodeArea.setText(morseCodeController.translateToMorse(inputText));
        }
    }
}
