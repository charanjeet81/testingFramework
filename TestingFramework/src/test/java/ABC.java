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
		/*try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String auditDescription=null;
		String userName = "AppUser";
		String password = "Pa$$w0rd1";
		String url = "jdbc:jtds:sqlserver://CARZ1DB769.corp.cox.com:1499";
		String tCID=null;
		Connection con;

		try {
			con = DriverManager.getConnection(url, userName, password);
			Statement s1 = con.createStatement();
			ResultSet rs1 = s1.executeQuery("select * from workflow.cox.troublecalldisposition where workorderno='9913521'");
			if(rs1!=null)
			{
				int count = 0;
				while (rs1.next())
				{
					tCID = rs1.getString("TCID");
					System.out.println(tCID);
					count++;
				}
			}
			Statement s2 = con.createStatement();
			ResultSet rs2 = s2.executeQuery("select * from workflow.framework.audit where workitemid ='"+tCID+"'");
			if(rs2!=null)
			{

				while (rs2.next())
				{
					auditDescription= rs2.getString("Event");	
					System.out.println(auditDescription);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
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
