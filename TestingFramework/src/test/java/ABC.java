import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.jna.platform.FileUtils;




public class ABC 
{
	public static void main(String[] args) throws IOException
	{
	//	pack("D:\\workD\\TestingFramework\\Reports\\MODULE_1.TC_01_Login\\Jun_19_2018_1_04_09_PM", "D:\\workD\\TestingFramework\\Reports\\MODULE_1.TC_01_Login\\Jun_19_2018_1_04_09_PM\\TCLogin.zip");
		String sourceFile = "D:\\workD\\TestingFramework\\Reports\\MODULE_1.TC_01_Login\\Jun_19_2018_1_04_09_PM";
        FileOutputStream fos = new FileOutputStream("TC_01_Login.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        org.apache.commons.io.FileUtils.copyFileToDirectory(new File("D:\\workD\\TestingFramework\\TC_01_Login.zip"), new File("D:\\workD\\TestingFramework\\Reports\\MODULE_1.TC_01_Login\\Jun_19_2018_1_04_09_PM"));
        org.apache.commons.io.FileUtils.deleteQuietly(new File("D:\\workD\\TestingFramework\\TC_01_Login.zip"));
        
		//ZipUtil.pack(new File("D:\\reports\\january\\"), new File("D:\\reports\\january.zip"));
		
	}
	
	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException 
	{
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
	
	public static void pack(String sourceDirPath, String zipFilePath) throws IOException 
	{
	    Path p = Files.createFile(Paths.get(zipFilePath));
	    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
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
