package net.glxn.qrgen;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.junit.Test;

import net.glxn.qrgen.image.ImageType;

public class QRCodeTest {

    @Test
    public void shouldGetFileFromTextWithDefaults() throws Exception {
        File file = QRCode.from("Hello World").file();
        Assert.assertNotNull(file);
    }

    @Test
    public void shouldGetCorrectFileEndingOnResultingFileForOwnFileArgument() throws Exception {
        File tempFile = File.createTempFile("test", ".tmp");
        tempFile.deleteOnExit();

        File file = QRCode.from("Hello World").file(tempFile);
        Assert.assertNotNull(file);
        Assert.assertTrue(file.getName().endsWith(".png"));
    }

    @Test
    public void shouldGetSTREAMFromTextWithDefaults() throws Exception {
        ByteArrayOutputStream stream = QRCode.from("Hello World").stream();
        Assert.assertNotNull(stream);
    }

    @Test
    public void shouldHandleLargeString() throws Exception {
        int length = 2950;
        char[] chars = new char[length];
        for(int i = 0; i < length; i++) {
             chars[i] = 'a';
        }
        String text = new String(chars);
        Assert.assertEquals(length, text.length());

        File file = QRCode.from(text).to(ImageType.PNG).file();
        Assert.assertNotNull(file);
    }

    @Test
    public void shouldGetFileFromTextWithImageTypeOverrides() throws Exception {
        File jpg = QRCode.from("Hello World").to(ImageType.JPG).file();
        Assert.assertNotNull(jpg);
        File gif = QRCode.from("Hello World").to(ImageType.GIF).file();
        Assert.assertNotNull(gif);
    }

    @Test
    public void shouldGetStreamFromText() throws Exception {
        ByteArrayOutputStream stream = QRCode.from("Hello World").to(ImageType.PNG).stream();
        Assert.assertNotNull(stream);
        File tempFile = File.createTempFile("test", ".tmp");
        long lengthBefore = tempFile.length();
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        stream.writeTo(fileOutputStream);
        Assert.assertTrue(lengthBefore < tempFile.length());
    }

    @Test
    public void shouldBeAbleToOverrideDimensionsToFile() throws Exception {
        long defaultSize = QRCode.from("Hello World").to(ImageType.PNG).file().length();
        long defaultSize2 = QRCode.from("Hello World").to(ImageType.PNG).file().length();
        File file = QRCode.from("Hello World").to(ImageType.PNG).withSize(250, 250).file();
        Assert.assertNotNull(file);
        Assert.assertTrue(defaultSize == defaultSize2);
        Assert.assertTrue(defaultSize < file.length());
    }
}
