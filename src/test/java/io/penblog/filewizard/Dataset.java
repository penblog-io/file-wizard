package io.penblog.filewizard;

import io.penblog.filewizard.components.Item;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Dataset {


    public static Map<String, Item> items() {
        Map<String, FileProperty> fileProperties = new HashMap<>();
        fileProperties.put("IMG_001.JPG", new FileProperty(1234));
        fileProperties.put("132618_20191102_132618.jpg", new FileProperty(1234));
        fileProperties.put("132620_20191102_132620.jpg", new FileProperty(5533));
        fileProperties.put("20151123_135458.jpg", new FileProperty(6701));
        fileProperties.put("13052008011.mp4", new FileProperty(73010));
        fileProperties.put("13052008013.mp4", new FileProperty(16934));
        fileProperties.put("Civil War - Part II.docx", new FileProperty(1346));
        fileProperties.put("Civil War.docx", new FileProperty(7));
        fileProperties.put("Financial Statement 2020.xlsx", new FileProperty(17));
        fileProperties.put("Financial Statement 2021.xlsx", new FileProperty(57));
        fileProperties.put("Financial Statement 2022 - copy.xlsx", new FileProperty(71));
        fileProperties.put("IMG_0762.MOV", new FileProperty(2901234));
        fileProperties.put("IMG_1040.PNG", new FileProperty(1368));
        fileProperties.put("IMG_1800.JPG", new FileProperty(7265));
        fileProperties.put("IMG_1856.JPG", new FileProperty(4197));
        fileProperties.put("IMG_2292.JPG", new FileProperty(3477));
        fileProperties.put("IMG_2672.jpg", new FileProperty(1299));
        fileProperties.put("IMG_5127.PNG", new FileProperty(2087));
        fileProperties.put("IMG_7178.PNG", new FileProperty(1088));
        fileProperties.put("IMG_7889.JPG", new FileProperty(3785));
        fileProperties.put("IMG_8774.mov", new FileProperty(222075));
        fileProperties.put("Java (Programming Language)", new FileProperty(107));
        fileProperties.put("Avengers: Endgame.mp4", new FileProperty(4338));
        fileProperties.put("Avengers: Infinity War.mp4", new FileProperty(5123));
        fileProperties.put("Metaverse.pdf", new FileProperty(543));
        fileProperties.put("My wishlist.xlsx", new FileProperty(17));
        fileProperties.put("Notepad", new FileProperty(3));
        fileProperties.put("John Lennon - Imagine.mp3", new FileProperty(4566));
        fileProperties.put("John Lennon - Beautiful Boy.mp3", new FileProperty(6775));
        fileProperties.put("John Lennon - Happy Xmas (War is Over).mp3", new FileProperty(3385));
        fileProperties.put("VID_20161230_151652.mp4", new FileProperty(11754));
        fileProperties.put("VID_20161231_130901.mp4", new FileProperty(12441));
        fileProperties.put("Virtual Reality.pdf", new FileProperty(1200));
        fileProperties.put("WIKIPEDIA.pdf", new FileProperty(976));


        int index = 0;
        String parentPath = "C:\\Parent Folder 01\\";
        Map<String, Item> items = new HashMap<>();
        for (Map.Entry<String, FileProperty> entry : fileProperties.entrySet()) {
            String filename = entry.getKey();
            FileProperty fileProperty = entry.getValue();

            File file = mock(File.class);


            when(file.getAbsolutePath()).thenReturn(parentPath + filename);
            when(file.getName()).thenReturn(filename);

            Item item = new Item(file);
            item.setIndex(index++);

            items.put(filename, item);
        }

        return items;
    }


    record FileProperty(double size) {
    }

}
