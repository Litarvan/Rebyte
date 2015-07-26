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

/**
 * The Rebyte Malformed Bytes Exception
 *
 * <p>
 *    This exception is thrown by an extension, when it
 *    failed to read some bytes.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public class RebyteMalformedBytesException extends Exception {

    /**
     * The Rebyte Malformed Bytes Exception, with the message
     * set as 'Malformed bytes'
     */
    public RebyteMalformedBytesException() {
        super("Malformed bytes");
    }

    /**
     * The Rebyte Malformed Bytes Exception, with the message
     * set as 'Malformed bytes from : ' with then the given string.
     */
    public RebyteMalformedBytesException(String from) {
        super("Malformed bytes from : " + from);
    }

}
