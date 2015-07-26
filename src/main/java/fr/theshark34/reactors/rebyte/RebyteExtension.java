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

import java.util.ArrayList;

/**
 * The Rebyte Extension
 *
 * <p>
 *    The Rebyte extension adds some features to Rebyte.
 * </p>
 *
 * @version 1.0.0-BETA
 * @author TheShark34
 */
public abstract class RebyteExtension {

    /**
     * The RCodes of the extension
     */
    private ArrayList<RCode> codes = new ArrayList<RCode>();

    /**
     * The method executed when the extension is loaded.
     *
     * @param rebyte
     *            The {@link Rebyte} where the extension was added.
     */
    public abstract void onLoad(Rebyte rebyte);

    /**
     * Add an action to execute when the given code is read
     *
     * @param code
     *            The code that need to be read to launch the action
     * @param action
     *            The action to launch after read the code
     */
    public void on(final byte code, final RCodeAction action) {
        codes.add(new RCode() {
            @Override
            public byte getCode() {
                return code;
            }

            @Override
            public RCodeAction getAction() {
                return action;
            }
        });
    }

    /**
     * Add an RCode (an action to execute when a code is read)
     *
     * @param rCode
     *            The RCode to add
     */
    public void addCode(RCode rCode) {
        codes.add(rCode);
    }

    /**
     * Return the list of the extension codes
     *
     * @return The extension codes
     */
    public ArrayList<RCode> getCodes() {
        return codes;
    }
}
