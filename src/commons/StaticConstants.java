package commons;

import java.util.ArrayList;
import java.util.Arrays;

public class StaticConstants {
    public static final String LICENSE = String.format("\nMIT License\n\nCopyright (c) 2021 Jaysmitio101\n\nPermission is hereby granted, free of charge, to any person obtaining a copy\nof this software and associated documentation files (the \"Software\"), to deal\nin the Software without restriction, including without limitation the rights\nto use, copy, modify, merge, publish, distribute, sublicense, and/or sell\ncopies of the Software, and to permit persons to whom the Software is\nfurnished to do so, subject to the following conditions:\n\nThe above copyright notice and this permission notice shall be included in all\ncopies or substantial portions of the Software.\n\nTHE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\nIMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\nFITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\nAUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\nLIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\nOUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\nSOFTWARE.");
    public static final String WEBSITE_URL = "";

    private StaticConstants(){}

    public static final String[] DATA_TYPES = {"INT", "DOUBLE", "STRING", "BOOLEAN"};
    public static final String[] KEYWORDS = {"REM", "DECLARE", "DECL", "UNDECLARE", "UNDEC", "UNDECALL", "SET", "MOV", "JUMP", "GOTO", "EXIT", "ADD", "SUB", "MULTIPLY", "MUL", "IMUL", "MULT", "DIVIDE", "DIV", "IDIV", "DIVI", "MODULUS", "MOD", "IMOD", "MODULO", "CONVERTTYPE", "CVT", "CONV", "WHATTYPE", "WHT", "GETTYPE", "PRINT", "DISP", "DISPLAY", "PRINTLN", "DISPLN", "DISPLAYLN", "COMPARE", "CMP", "COMPARETO", "JUMPZ", "JMZ", "JUMPN", "JMN", "JUMPP", "JMP", "INCREMENT", "INC", "DECREMENT", "DEC", "INPUT", "CLOSESTDIN", "VERSION", "AUTHOR", "LABEL", "LOAD"};
    public static final ArrayList<String> KEYWORDS_LIST = new ArrayList<String>(Arrays.asList(KEYWORDS));
    public static final ArrayList<String> DATA_TYPES_LIST = new ArrayList<String>(Arrays.asList(DATA_TYPES));
    public static final String DEFAULT_IDE_TITLE = "JMBASIC IDE -- Jaysmito Mukherjee";
}
