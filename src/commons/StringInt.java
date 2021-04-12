package commons;

import javax.swing.text.*;

public class StringInt {
    private int n, style;
    private String str;

    public int getint() {
        return style;
    }

    public void setint(int style) {
        this.style = style;
    }

    public StringInt(String str, int n, int style) {
        this.n = n;
        this.str = str;
        this.style = style;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "StringInt{" +
                "Integer=" + n +
                ", String='" + str + '\'' +
                '}';
    }
}
