package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoaderUtils {
    private LoaderUtils(){}

    public static boolean loadJars(){
        if (1==1)
            return true;
        try {
            File file = File.createTempFile(StringUtils.generateRandomString(10), ".jar");
            OutputStream os = new FileOutputStream(file);
            os.write(ClassLoader.getSystemClassLoader().getResourceAsStream("/res/JMBASIC.jar").readAllBytes());
            os.close();
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")};
            URLClassLoader classLoader = URLClassLoader.newInstance(urls);
            while (entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                if(jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")){
                    continue;
                }
                String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                className = className.replace("/", ".");
                Class c = classLoader.loadClass(className);
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}
