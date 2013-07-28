/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2013 Open Microscopy Environment:
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 *   - University of Dundee
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package io.scif.io;

import io.scif.SCIFIO;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.scijava.Context;

/**
 * RandomAccessOutputStream provides methods for writing to files and byte
 * arrays.
 */
public class RandomAccessOutputStream extends OutputStream implements
	DataOutput
{

	// -- Fields --

	private final IRandomAccess outputFile;

	// -- Constructor --

	/**
	 * Constructs a random access stream around the given file.
	 * 
	 * @param file Filename to open the stream for.
	 * @throws IOException If there is a problem opening the file.
	 */
	public RandomAccessOutputStream(final Context context, final String file)
		throws IOException
	{
		final SCIFIO scifio = new SCIFIO(context);
		outputFile = scifio.location().getHandle(file, true);
	}

	/**
	 * Constructs a random access stream around the given handle.
	 * 
	 * @param handle Handle to open the stream for.
	 */
	public RandomAccessOutputStream(final IRandomAccess handle) {
		outputFile = handle;
	}

	// -- RandomAccessOutputStream API methods --

	/** Seeks to the given offset within the stream. */
	public void seek(final long pos) throws IOException {
		outputFile.seek(pos);
	}

	/** Returns the current offset within the stream. */
	public long getFilePointer() throws IOException {
		return outputFile.getFilePointer();
	}

	/** Returns the length of the file. */
	public long length() throws IOException {
		return outputFile.length();
	}

	/** Advances the current offset by the given number of bytes. */
	public void skipBytes(final int skip) throws IOException {
		outputFile.seek(outputFile.getFilePointer() + skip);
	}

	/** Sets the endianness of the stream. */
	public void order(final boolean little) {
		outputFile
			.setOrder(little ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
	}

	/** Gets the endianness of the stream. */
	public boolean isLittleEndian() {
		return outputFile.getOrder() == ByteOrder.LITTLE_ENDIAN;
	}

	/** Writes the given string followed by a newline character. */
	public void writeLine(final String s) throws IOException {
		writeBytes(s);
		writeBytes("\n");
	}

	// -- DataOutput API methods --

	/* @see java.io.DataOutput#write(byte[]) */
	@Override
	public void write(final byte[] b) throws IOException {
		outputFile.write(b);
	}

	/* @see java.io.DataOutput#write(byte[], int, int) */
	@Override
	public void write(final byte[] b, final int off, final int len)
		throws IOException
	{
		outputFile.write(b, off, len);
	}

	/**
	 * Writes bytes to the stream from the given buffer.
	 * 
	 * @param b Source buffer to read data from.
	 * @throws IOException If there is an error writing to the stream.
	 */
	public void write(final ByteBuffer b) throws IOException {
		outputFile.write(b);
	}

	/**
	 * @param b Source buffer to read data from.
	 * @param off Offset within the buffer to start reading from.
	 * @param len Number of bytes to read.
	 * @throws IOException If there is an error writing to the stream.
	 */
	public void write(final ByteBuffer b, final int off, final int len)
		throws IOException
	{
		outputFile.write(b, off, len);
	}

	/* @see java.io.DataOutput#write(int) */
	@Override
	public void write(final int b) throws IOException {
		outputFile.write(b);
	}

	/* @see java.io.DataOutput#writeBoolean(boolean) */
	public void writeBoolean(final boolean v) throws IOException {
		outputFile.writeBoolean(v);
	}

	/* @see java.io.DataOutput#writeByte(int) */
	public void writeByte(final int v) throws IOException {
		outputFile.writeByte(v);
	}

	/* @see java.io.DataOutput#writeBytes(String) */
	public void writeBytes(final String s) throws IOException {
		outputFile.writeBytes(s);
	}

	/* @see java.io.DataOutput#writeChar(int) */
	public void writeChar(final int v) throws IOException {
		outputFile.writeChar(v);
	}

	/* @see java.io.DataOutput#writeChars(String) */
	public void writeChars(final String s) throws IOException {
		outputFile.writeChars(s);
	}

	/* @see java.io.DataOutput#writeDouble(double) */
	public void writeDouble(final double v) throws IOException {
		outputFile.writeDouble(v);
	}

	/* @see java.io.DataOutput#writeFloat(float) */
	public void writeFloat(final float v) throws IOException {
		outputFile.writeFloat(v);
	}

	/* @see java.io.DataOutput#writeInt(int) */
	public void writeInt(final int v) throws IOException {
		outputFile.writeInt(v);
	}

	/* @see java.io.DataOutput#writeLong(long) */
	public void writeLong(final long v) throws IOException {
		outputFile.writeLong(v);
	}

	/* @see java.io.DataOutput#writeShort(int) */
	public void writeShort(final int v) throws IOException {
		outputFile.writeShort(v);
	}

	/* @see java.io.DataOutput#writeUTF(String) */
	public void writeUTF(final String str) throws IOException {
		outputFile.writeUTF(str);
	}

	// -- OutputStream API methods --

	/* @see java.io.OutputStream#close() */
	@Override
	public void close() throws IOException {
		outputFile.close();
	}

	/* @see java.io.OutputStream#flush() */
	@Override
	public void flush() throws IOException {}

}