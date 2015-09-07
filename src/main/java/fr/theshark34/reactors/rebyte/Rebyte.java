/*
 * Copyright 2015 TheShark34
 *
 * This file is part of Rebyte.

 * Rebyte is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Rebyte is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Rebyte.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.theshark34.reactors.rebyte;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Rebyte
 *
 * <p>
 *    Rebyte read a file byte per byte, and if the byte
 *    is registered as an RCode by one of the extension,
 *    it execute the corresponding action.
 * </p>
 *
 * <p>
 *    It can read all bytes before one given (getUntil(byte) method),
 *    escape the next character (escapeNext() method) and skip until a
 *    given byte (waitFor(byte) method).
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class Rebyte {

    /**
     * The Rebyte version
     */
    public static final String VERSION = "1.0.0-BETA";


    /**
     * The current reading file
     */
    private File currentFile;

    /**
     * The list of extensions
     */
    private ArrayList<RebyteExtension> extensions = new ArrayList<RebyteExtension>();

    /**
     * The bytes of the file
     */
    private byte[] bytes;

    /**
     * The current index of the reading byte
     */
    private int index;

    /**
     * Empty constructor, you will need to do setCurrentFile/setBytes
     * to select the file to read.
     */
    public Rebyte() {
    }

    /**
     * Constructor with a file to read (but you can change it
     * with the setCurrentFile method).
     *
     * @param fileToRead
     *            The file to read
     */
    public Rebyte(File fileToRead) throws IOException {
        // Checking if the file to read is not null
        if(fileToRead == null)
            throw new IllegalArgumentException("fileToRead == null");

        this.currentFile = fileToRead;
        this.bytes = read(currentFile);
    }

    /**
     * Constructor with somes bytes to read (but you can change it
     * with the setBytes method).
     *
     * @param bytesToRead
     *            The bytes to read
     */
    public Rebyte(byte[] bytesToRead) {
        // Checking if the bytes to read is not null
        if(bytesToRead == null)
            throw new IllegalArgumentException("bytesToRead == null");

        this.bytes = bytesToRead;
    }

    /**
     * Starts rebyte =)
     *
     * @throws IOException
     *            If the reader has thrown one
     */
    public void start() throws IOException, RebyteMalformedBytesException {
        // Checking if the current file to read is not null
        if(this.currentFile == null)
            throw new IllegalStateException("currentFile == null");

        // Getting the current byte array
        if(this.bytes == null)
            throw new IllegalStateException("bytes == null");

        // For each byte
        while(index < bytes.length) {
            // Getting the byte
            byte b = getNext();

            // Executing the specific actions
            for (RebyteExtension ext : extensions)
                for (RCode code : ext.getCodes())
                    if (code.getCode() == b)
                        code.getAction().onCodeRead(this, code.getCode());
        }
    }

    /**
     * Load an extension, and add it
     *
     * @param ext
     *            The extension to load and add
     */
    public void loadExt(RebyteExtension ext) {
        // Checking if the extension is not null
        if(ext == null)
            throw new IllegalArgumentException("ext == null");

        // Loading it
        ext.onLoad(this);

        // Then adding it
        extensions.add(ext);
    }

    /**
     * Set the file to read
     *
     * @param currentFile
     *            The new file to read
     */
    public void setCurrentFile(File currentFile) throws IOException {
        // Checking if the file to read is not null
        if(currentFile == null)
            throw new IllegalArgumentException("currentFile == null");

        this.currentFile = currentFile;
        this.bytes = read(currentFile);
    }

    /**
     * Return the current file that is read
     *
     * @return The current file
     */
    public File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * Skip all bytes until one given
     *
     * @param byteToWait
     *            The byte to wait for
     */
    public void waitUntil(byte byteToWait) {
        // Checking the bytes
        if(bytes == null)
            throw new IllegalStateException("You need to give a file or some bytes to read");

        // Waiting until the given byte
        byte currentByte = 0;
        do {
            currentByte = getNext();
        } while(currentByte != byteToWait);
    }

    /**
     * Skip a given number of bytes
     *
     * @param bytesToSkip
     *            The number of byte to skip
     */
    public void waitFor(long bytesToSkip) {
        // Checking the bytes
        if(bytes == null)
            throw new IllegalStateException("You need to give a file or some bytes to read");

        // Skipping a given number of byte
        for(int i = 0; i < bytesToSkip; i++)
            getNext();
    }

    /**
     * Read a given number of byte
     *
     * @param numberOfByteToRead
     *            The number of byte to read
     */
    public byte[] readFor(long numberOfByteToRead) {
        // Checking the bytes
        if(bytes == null)
            throw new IllegalStateException("You need to give a file or some bytes to read");

        // Creating an array list of bytes
        ArrayList<Byte> readBytes = new ArrayList<Byte>();

        // Getting the given number of byte
        for(int readNumber = 0; readNumber < numberOfByteToRead; readNumber++)
            readBytes.add(getNext());

        // Returning the list as an array list
        return toArray(readBytes);
    }

    /**
     * Read until a given byte
     *
     * @param byteToWait
     *            The byte to wait for
     */
    public byte[] readUntil(byte byteToWait) {
        // Checking the bytes
        if(bytes == null)
            throw new IllegalStateException("You need to give a file or some bytes to read");

        // Creating an array list of bytes
        ArrayList<Byte> readBytes = new ArrayList<Byte>();

        // Reading until the given byte
        byte currentByte = 0;
        do {
            currentByte = getNext();

            if(currentByte != byteToWait)
                readBytes.add(currentByte);
        } while(currentByte != byteToWait);

        // Returning the list as an array list
        return toArray(readBytes);
    }

    /**
     * Converts a list of Byte to an array of byte
     *
     * @param list
     *            The list of byte to convert
     * @return An array of byte, corresponding to the given list
     */
    public byte[] toArray(ArrayList<Byte> list) {
        // Creating an array with the same size as the list
        byte[] array = new byte[list.size()];

        // Transfering
        for(int i = 0; i < list.size(); i++)
            array[i] = list.get(i);

        // Returning the created array
        return array;
    }

    /**
     * Read all the bytes of a file
     *
     * @param file
     *            The bytes of the file
     *
     * @throws IOException
     *            If it failed
     * @return The bytes of the file
     */
    public byte[] read(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;

        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1)
                return buffer;
        } finally {
            try {
                if ( ios != null )
                    ios.close();
            } catch ( IOException e) {
            }
        }

        return buffer;
    }

    /**
     * Read one byte
     *
     * @return The next byte
     */
    public byte getNext() {
        byte next = bytes[index];
        this.index++;

        return next;
    }

    /**
     * Set the bytes to read
     *
     * @param bytes
     *            The bytes to read
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Return the current reading bytes
     *
     * @return The reading bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Set the index of the current reading byte of
     * the byte array
     *
     * @param index
     *             The new index (need to be < than getBytes().length)
     */
    public void setReadingIndex(int index) {
        if(index >= bytes.length)
            throw new IllegalArgumentException("index >= bytes.length");

        this.index = index;
    }

    /**
     * Return the index of the current reading byte
     * of the byte array
     *
     * @return The current reading index
     */
    public int getReadingIndex() {
        return this.index;
    }

}