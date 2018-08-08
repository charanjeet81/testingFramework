package supportLiberaries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		pack("D:\\Users\\charanjeet.singh\\git\\testingFramework\\TestingFramework\\Reports\\TWITTER.TC_03_Login_Twitter\\Aug_8_2018_1_12_13_PM", "D:\\Users\\charanjeet.singh\\git\\testingFramework\\TestingFramework\\Reports\\TWITTER.TC_03_Login_Twitter\\Aug_8_2018_1_12_13_PM.zip");
		//zipFile(new File("D:\\Users\\charanjeet.singh\\git\\testingFramework\\TestingFramework\\Reports\\TWITTER.TC_03_Login_Twitter\\Aug_8_2018_1_12_13_PM"), "D:\\Users\\charanjeet.singh\\git\\testingFramework\\TestingFramework\\Reports\\TWITTER.TC_03_Login_Twitter\\Aug_8_2018_1_12_13_PM", "D:\\Users\\charanjeet.singh\\git\\testingFramework\\TestingFramework\\Reports\\TWITTER.TC_03_Login_Twitter\\Aug_8_2018_1_12_13_PM.zip");
	}

	
	static void zipFile(File fileToZip, String fileName, String zipOut) throws FileNotFoundException  
	{
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		fileWriter = new FileOutputStream(zipOut);
	    zip = new ZipOutputStream(fileWriter);
        if (fileToZip.isHidden()) 
        {
            return;
        }
        if (fileToZip.isDirectory()) 
        {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) 
            {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try 
        {
        	FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zip.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zip.write(bytes, 0, length);
            }
            fis.close();
		} catch (IOException e) 
        {
			//Reporting("Report Compression got failed."+e.getMessage(), Status.FAIL);
		}
    }
	
	public static void pack(String sourceDirPath, String zipFilePath) throws IOException 
	{
	    Path p = Files.createFile(Paths.get(zipFilePath));
	    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) 
	    {
	        Path pp = Paths.get(sourceDirPath);
	        Files.walk(pp)
	          .filter(path -> !Files.isDirectory(path))
	          .forEach(path -> {
	              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
	              try {
	                  zs.putNextEntry(zipEntry);
	                  Files.copy(path, zs);
	                  zs.closeEntry();
	            } catch (IOException e) {
	                System.err.println(e);
	            }
	          });
	    }
	}
}
