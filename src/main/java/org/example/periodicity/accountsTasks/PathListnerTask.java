package org.example.periodicity.accountsTasks;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathListnerTask {
        public static File[] fileListing (String path)
        {
            Path realpath = Paths.get(path);
            File[] files = realpath.toFile().listFiles();
            return files;
        }

}
