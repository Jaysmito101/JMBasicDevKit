package ide.styling;

import commons.StaticConstants;
import commons.StringInt;
import utils.StringUtils;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JMBasicStylingDocument extends DefaultStyledDocument {
    private static final long serialVersionUID = 1L;
    private Style defaultStyle;
    private Style keywordStyle, datatypeStyle, numberStyle, stringStyle;

    public JMBasicStylingDocument() {
        StyleContext styleContext = new StyleContext();
        defaultStyle = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        keywordStyle = styleContext.addStyle("ConstantWidth", null);
        datatypeStyle = styleContext.addStyle("ConstantWidth", null);
        numberStyle = styleContext.addStyle("ConstantWidth", null);
        stringStyle = styleContext.addStyle("ConstantWidth", null);
        StyleConstants.setForeground(keywordStyle, new Color(177, 66, 5));
        StyleConstants.setForeground(datatypeStyle, new Color(255, 162, 0));
        StyleConstants.setForeground(numberStyle, new Color(10, 163, 212));
        StyleConstants.setForeground(stringStyle, new Color(12, 132, 3));
    }


    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, StringUtils.processIdeCode(str), a);
        refreshDocument();
    }

    public void remove (int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        refreshDocument();
    }

    private synchronized void refreshDocument() throws BadLocationException {
        String text = getText(0, getLength());
        final List<StringInt> list = processWords(text);
        setCharacterAttributes(0, text.length(), defaultStyle, true);
        for(StringInt word : list) {
            int p0 = word.getN();
            switch (word.getint()){
                case 1:{
                    setCharacterAttributes(p0, word.getStr().length(), keywordStyle, true);
                    break;
                }
                case 2:{
                    setCharacterAttributes(p0, word.getStr().length(), datatypeStyle, true);
                    break;
                }
                case 3:{
                    setCharacterAttributes(p0, word.getStr().length(), numberStyle, true);
                    break;
                }
                case 4:{
                    setCharacterAttributes(p0, word.getStr().length(), stringStyle, true);
                    break;
                }
            }
        }
    }

    private static  List<StringInt> processWords(String content) {
        content += " ";
        List<StringInt> hiliteWords = new ArrayList<StringInt>();
        int lastWhitespacePosition = 0;
        String word = "";
        char[] data = content.toCharArray();

        for(int index=0; index < data.length; index++) {
            char ch = data[index];
            if(!(Character.isLetter(ch) || Character.isDigit(ch) || ch == '_' || ch == '.' || ch == '\"')) {
                lastWhitespacePosition = index;
                if(word.length() > 0) {

                    //JUST FOR NOW!
                    word = word.toUpperCase();

                    if(isReservedWord(word)) {
                        hiliteWords.add(new StringInt(word,(lastWhitespacePosition - word.length()),  1) );
                    }
                    else if (isDataType(word)){
                        hiliteWords.add(new StringInt(word,(lastWhitespacePosition - word.length()), 2));
                    }
                    else if(isNumeric(word)){
                        hiliteWords.add(new StringInt(word,(lastWhitespacePosition - word.length()), 3));
                    }
                    else if(isString(word)){
                        hiliteWords.add(new StringInt(word,(lastWhitespacePosition - word.length()), 4));
                    }
                    word="";
                }
            }
            else {
                word += ch;
            }
        }
        return hiliteWords;
    }

    private static boolean isReservedWord(String word) {
        return StaticConstants.KEYWORDS_LIST.contains(word);
    }

    private static boolean isDataType(String word){
        return StaticConstants.DATA_TYPES_LIST.contains(word);
    }

    private static boolean isNumeric(String word){
        try{
            Double.parseDouble(word);
        }catch (NumberFormatException nmfe){
            return false;
        }
        return true;
    }

    private static boolean isString(String word){
        return (word.charAt(0)=='\"' && word.charAt(word.length()-1)=='\"') || word.equals("TRUE") || word.equals("FALSE");
    }
}

