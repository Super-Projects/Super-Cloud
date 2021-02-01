package de.z1up.supercloud.core.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class Copier ***REMOVED***

    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException ***REMOVED***
        if (!destinationDirectory.exists()) ***REMOVED***
            destinationDirectory.mkdir();
        ***REMOVED***
        for (String f : sourceDirectory.list()) ***REMOVED***
            copyDirectoryCompatibityMode(new File(sourceDirectory, f), new File(destinationDirectory, f));
        ***REMOVED***
    ***REMOVED***

    public static void copyDirectoryCompatibityMode(File source, File destination) throws IOException ***REMOVED***
        if (source.isDirectory()) ***REMOVED***
            copyDirectory(source, destination);
        ***REMOVED*** else ***REMOVED***
            copyFile(source, destination);
        ***REMOVED***
    ***REMOVED***

    private static void copyFile(File sourceFile, File destinationFile)
            throws IOException ***REMOVED***
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) ***REMOVED***
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) ***REMOVED***
                out.write(buf, 0, length);
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    public static void copyFile(CloudFile from, CloudFolder to) ***REMOVED***

        File file = from.get();
        Path fromPath = file.toPath();

        File toFile = new File(to.get() + "//" + from.get().getName());
        Path toPath = toFile.toPath();

        if(!file.exists()) ***REMOVED***
            return;
        ***REMOVED***

        try ***REMOVED***
            Files.copy(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

    ***REMOVED***

    public static void copyJarFile(JarFile jarFile, File destDir) throws IOException ***REMOVED***
        String fileName = jarFile.getName();
        String fileNameLastPart = fileName.substring(fileName.lastIndexOf(File.separator));
        File destFile = new File(destDir, fileNameLastPart);

        JarOutputStream jos = new JarOutputStream(new FileOutputStream(destFile));
        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) ***REMOVED***
            JarEntry entry = entries.nextElement();
            InputStream is = jarFile.getInputStream(entry);

            //jos.putNextEntry(entry);
            //create a new entry to avoid ZipException: invalid entry compressed size
            jos.putNextEntry(new JarEntry(entry.getName()));
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = is.read(buffer)) != -1) ***REMOVED***
                jos.write(buffer, 0, bytesRead);
            ***REMOVED***
            is.close();
            jos.flush();
            jos.closeEntry();
        ***REMOVED***
        jos.close();
    ***REMOVED***

***REMOVED***
