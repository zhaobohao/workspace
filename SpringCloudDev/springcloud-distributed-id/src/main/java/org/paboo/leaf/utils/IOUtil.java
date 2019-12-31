/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.paboo.leaf.utils;

import java.io.*;
import java.nio.charset.Charset;

import java.nio.charset.StandardCharsets;

/**
 * @author Leonard Woo
 */
public class IOUtil {
    /**
     * Get file stream
     *
     * @param filepath File path
     * @return File input stream
     * @throws FileNotFoundException File not found
     */
    public static InputStream getStream(String filepath)
            throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(filepath));
    }

    /**
     * Get file stream
     *
     * @param file File object
     * @return File input stream
     * @throws FileNotFoundException File not found
     */
    public static InputStream getStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * Load string to reader
     *
     * @param str String
     * @return Reader
     */
    public static BufferedReader loadString(String str) {
        return new BufferedReader(new StringReader(str));
    }

    /**
     * Load inputstream to reader
     *
     * @param in Inputstream
     * @return Reader
     */
    public static BufferedReader loadStream(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

    /**
     * Load inputstram to reader with charset
     *
     * @param in      Inputstream
     * @param charset Charest see{@link StandardCharsets}
     * @return Reader
     */
    public static BufferedReader loadStream(InputStream in, Charset charset) {
        return new BufferedReader(new InputStreamReader(in, charset));
    }

    public static String readerToString(BufferedReader reader) throws IOException {
        String str = "";
        while (reader.ready()) {
            str += reader.readLine();
        }
        return str;
    }

    public static Writer writeFile(String pathname) throws IOException {
        return new FileWriter(pathname);
    }

    public static Writer writeFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

    /**
     * Output to file
     *
     * @param b    bytes
     * @param file File
     * @throws IOException I/O exception
     */
    public static void toFile(byte[] b, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        os.write(b);
        os.close();
    }

    /**
     * Copy inputstream to outputstream
     *
     * @param is InputStream
     * @param os OutputStream
     * @throws IOException I/O exception
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        int t;
        while ((t = is.read()) != -1)
            os.write(t);
        os.flush();
    }
}
