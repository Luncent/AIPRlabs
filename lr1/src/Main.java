import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

public class Main extends Frame implements ActionListener{
    Button bex=new Button("Exit");
    Button sea=new Button("Search");
    TextArea txa = new TextArea();
    public  Main()
    {
        super("my window");
        setLayout(null);
        setBackground(new Color(150,200,100));
        setSize(450,250);
        add(bex);
        add(sea);
        add(txa);
        bex.setBounds(110,190,100,20);
        bex.addActionListener(this);
        sea.setBounds(110,165,100,20);
        sea.addActionListener(this);
        txa.setBounds(20,50,300,100);

        this.show();
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==bex) {
            System.exit(0);
        }
        else if (ae.getSource()==sea) {
            String[] keyWords = getKeyWords();

            txa.setText("");

            File targetHtml = findTargetHtml(keyWords);

            if(targetHtml!=null){
                System.out.println(targetHtml);
                showInBrowser(targetHtml.getAbsolutePath());
            }
        }
    }

    public void showInBrowser(String path){
        try {
            URL url = new URL("file:/"+path);
            Desktop.getDesktop().browse(url.toURI());
        }
        catch (Exception ex){
            ex.fillInStackTrace();
        }
    }

    public String[] getKeyWords(){
        String[] words = txa.getText().split(",");
        for (int j=0;j<words.length;j++){
            System.out.println(words[j]);
        }
        return words;
    }

    public File findTargetHtml(String[] keywords){
        int biggestAmount=0;
        File targetHtml=null;
        for (File elem : getHtmlFiles())
        {
            FileInfo fileInfo = test_url(elem,keywords);
            txa.append("\n"+elem+fileInfo.toString());
            //при несовпадении всех слов вообще, этот иф не запустит ничего в браузере
            if(fileInfo.coincidences>biggestAmount){
                biggestAmount=fileInfo.coincidences;
                targetHtml=elem;
            }
        }
        return targetHtml;
    }

    public ArrayList<File> getHtmlFiles(){
        File directory = new File("src/source_html");
        return new ArrayList<File>(Arrays.asList(directory.listFiles()));
    }

    public FileInfo test_url(File elem, String [] keywords)
    {
        FileInfo fileInfo = new FileInfo();
        URL url = null;
        URLConnection con = null;
        int i;

        try {
            String ffele=elem.getAbsolutePath();
            url = new URL("file:/"+ffele.trim());
            con = url.openConnection();
            File file = new File("src/rezult.html");
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedInputStream bis = new BufferedInputStream(
                    con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            String bhtml=""; //file content in byte array

            while ((i = bis.read()) != -1) {
                bos.write(i);
                bhtml+=(char)i;
            }
            bos.flush();
            bis.close();
            String htmlcontent = (bhtml).toLowerCase(); //file content in string
            System.out.println("New url content is: "+htmlcontent);
            for (int j=0;j<keywords.length;j++)
            {
                int keyWordIndex = htmlcontent.indexOf(keywords[j].trim().toLowerCase());
                if(keyWordIndex>=0) {
                    fileInfo.coincidences++;
                    int doubleDotIndex = findDoubleDotIndex(keyWordIndex, htmlcontent);
                    int whiteSpaceIndex = findWhiteSpaceIndex(doubleDotIndex, htmlcontent);
                    fileInfo.sum+=Double.parseDouble(htmlcontent.substring(doubleDotIndex+1,whiteSpaceIndex));
                }
            }
        }
        catch (MalformedInputException malformedInputException)
        {
            malformedInputException.fillInStackTrace();
            return FileInfo.getMockObj();
        }
        catch (IOException ioException)
        {
            ioException.fillInStackTrace();
            return FileInfo.getMockObj();
        }
        catch(Exception e)
        {
            e.fillInStackTrace();
            return FileInfo.getMockObj();
        }
        return fileInfo;
    }

    public int findDoubleDotIndex(int keyWordIndex, String htmlContent){
        String part = htmlContent.substring(keyWordIndex);
        return part.indexOf(':')+keyWordIndex;
    }

    public int findWhiteSpaceIndex(int doubleDotIndex, String htmlContent){
        String part = htmlContent.substring(doubleDotIndex);
        return part.indexOf(' ')+doubleDotIndex;
    }

    public static void main(String[] args)
    {
        new Main();
        /*File file = new File("de");
        file.listFiles()*/
    }
}
