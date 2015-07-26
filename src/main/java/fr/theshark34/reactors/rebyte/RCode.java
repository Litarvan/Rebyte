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
 * The RCode
 *
 * <p>
 *    The RCode is a byte code, that when it is read
 *    by a Rebyte object, run an action.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public abstract class RCode {

    /**
     * Return the byte code that launch the action
     *
     * @return The code
     */
    public abstract byte getCode();

    /**
     * Return the action to launch after read the byte
     *
     * @return The action
     */
    public abstract RCodeAction getAction();

}
