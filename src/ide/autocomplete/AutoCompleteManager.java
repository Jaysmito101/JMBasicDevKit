package ide.autocomplete;

import commons.StaticConstants;
import utils.StringUtils;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AutoCompleteManager {
    private JTextPane textPane;
    private JFrame frame;
    private int lastSpaceIndex, previousCaretIndex;
    private ArrayList<String> predictions, predictionsOld;

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    private JPopupMenu popupMenu;
    private String focusedWord;

    public boolean isAutoCompleteOn() {
        return isAutoCompleteOn;
    }

    public void setAutoCompleteOn(boolean autoCompleteOn) {
        isAutoCompleteOn = autoCompleteOn;
    }

    private boolean isAutoCompleteOn;

    public AutoCompleteManager(JTextPane textPane, JFrame frame) {
        this.textPane = textPane;
        this.frame = frame;
        this.isAutoCompleteOn = true;
        this.focusedWord = "";
        this.lastSpaceIndex = 0;
        this.previousCaretIndex = 0;
        this.predictions = new ArrayList<>();
        this.popupMenu = new JPopupMenu();
        this.popupMenu.setBorder(BorderFactory.createEmptyBorder());
        this.popupMenu.setForeground(new Color(222, 222, 222));
        this.popupMenu.setBackground(new Color(24, 22, 22));
        setKeyBinding(popupMenu, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hide();
                putWord();
            }
        });
    }

    public void applyAutoComplete(KeyEvent event) {
        int currentCaretIndex = textPane.getCaretPosition();
        if (this.previousCaretIndex != currentCaretIndex) {
            String typingWord = getTypingWord(currentCaretIndex);
            ArrayList<String> predictionWords = StringUtils.getMostProbableWord(typingWord, StaticConstants.KEYWORDS);
            ArrayList<String> predictionDataTypes = StringUtils.getMostProbableWord(typingWord, StaticConstants.DATA_TYPES);
            predictionWords = new ArrayList<>(Stream.concat(predictionDataTypes.stream(), predictionWords.stream()).distinct().collect(Collectors.toList()));
            int numberOfPredictionWords = (int) (Math.random() * 4) + 2;
            numberOfPredictionWords = Math.min(numberOfPredictionWords, predictionWords.size());
            predictions.clear();
            for (int i = 0; i < numberOfPredictionWords; i++)
                predictions.add(predictionWords.get(i));
            this.previousCaretIndex = currentCaretIndex;
            showCompletions(event);
        }
    }

    public void showCompletions(KeyEvent event) {
        if (predictions != null) {
            try {
                popupMenu.removeAll();
                for (String prediction : predictions) {
                    JMenuItem item = UIUtils.setPopupMenuItemStyle(new JMenuItem(prediction));
                    item.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent changeEvent) {
                            focusedWord = ((JMenuItem)changeEvent.getSource()).getText();
                        }
                    });
                    popupMenu.add(item);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            focusedWord = ((JMenuItem)actionEvent.getSource()).getText();
                            putWord();
                        }
                    });
                }
                Rectangle2D rectangle = textPane.modelToView2D(textPane.getCaretPosition());
                popupMenu.show(textPane, (int)rectangle.getX(), 30 + (int)rectangle.getY());
                popupMenu.updateUI();
                if (predictions.size() > 0)
                    popupMenu.setVisible(true);
                else
                    popupMenu.setVisible(false);
                frame.requestFocusInWindow();
                textPane.requestFocusInWindow();
            } catch (Exception ex) {
                popupMenu.setVisible(false);
            }
        }
        predictionsOld = predictions;
    }

    private String getTypingWord(int currentCaretIndex) {
        try {
            int start = Utilities.getWordStart(textPane, currentCaretIndex);
            int end = Utilities.getWordEnd(textPane, currentCaretIndex);
            return textPane.getText(start, end - start);
        } catch (Exception ex) {
        }
        return "";
    }

    public boolean isVisible(){
        return popupMenu.isVisible();
    }

    public boolean isFocused(){
        return popupMenu.hasFocus();
    }

    public void hide(){
        popupMenu.setVisible(false);
        frame.requestFocus();
        frame.requestFocusInWindow();
        textPane.requestFocusInWindow();
    }

    public void setFocus() {
        popupMenu.requestFocusInWindow();
    }

    public void putWord() {
        if (!isAutoCompleteOn)
            return;
        try {
            String word = focusedWord;
            int currentCaretIndex = textPane.getCaretPosition();
            int start = Utilities.getWordStart(textPane, currentCaretIndex);
            int end = Utilities.getWordEnd(textPane, currentCaretIndex);
            String random = StringUtils.generateRandomString(35);
            textPane.getStyledDocument().remove(start, end-start);
            textPane.getStyledDocument().insertString(start, random, null);
            textPane.setText(textPane.getText().replace(random, word));
            textPane.setCaretPosition(currentCaretIndex);
            hide();
        }catch (Exception ex){

        }
    }

    private void setKeyBinding(JComponent listItem, int keyCode, AbstractAction action) {
        int modifier = 0;
        switch (keyCode) {
            case KeyEvent.VK_CONTROL:
                modifier = InputEvent.CTRL_DOWN_MASK;
                break;
            case KeyEvent.VK_SHIFT:
                modifier = InputEvent.SHIFT_DOWN_MASK;
                break;
            case KeyEvent.VK_ALT:
                modifier = InputEvent.ALT_DOWN_MASK;
                break;
        }

        listItem.getInputMap().put(KeyStroke.getKeyStroke(keyCode, modifier), keyCode);
        listItem.getActionMap().put(keyCode, action);
    }
}
