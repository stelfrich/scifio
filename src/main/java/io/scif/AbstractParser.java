/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2015 Board of Regents of the University of
 * Wisconsin-Madison
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
 * #L%
 */

package io.scif;

import io.scif.config.SCIFIOConfig;
import io.scif.io.RandomAccessInputStream;
import io.scif.util.SCIFIOMetadataTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.scijava.io.DataHandle;
import org.scijava.io.DataHandleInputStream;
import org.scijava.io.DataHandleService;
import org.scijava.io.Location;

/**
 * Abstract superclass of all SCIFIO {@link io.scif.Parser} implementations.
 *
 * @see io.scif.Parser
 * @see io.scif.Metadata
 * @see io.scif.HasFormat
 * @author Mark Hiner 
 * @param <M> - The Metadata type returned by this Parser.
 */
public abstract class AbstractParser<M extends TypedMetadata> extends
	AbstractGroupable implements TypedParser<M>
{

	// -- Fields --

	/** Last Metadata instance parsed by this parser. */
	private M metadata;

	// -- Parser API Methods --

	@Override
	public M parse(final Location fileName) throws IOException, FormatException {
		return parse(fileName, new SCIFIOConfig());
	}

	@Override
	public M parse(final File file) throws IOException, FormatException {
		return parse(file, new SCIFIOConfig());
	}

	@Override
	public M parse(final DataHandleInputStream<Location> stream) throws IOException,
		FormatException
	{
		return parse(stream, new SCIFIOConfig());
	}

	@Override
	public M parse(final Location fileName, final Metadata meta)
		throws IOException, FormatException
	{
		return parse(fileName, meta, new SCIFIOConfig());
	}

	@Override
	public M parse(final File file, final Metadata meta) throws IOException,
		FormatException
	{
		return parse(file, meta, new SCIFIOConfig());
	}

	@Override
	public M parse(final DataHandleInputStream<Location> stream, final Metadata meta)
		throws IOException, FormatException
	{
		return parse(stream, meta, new SCIFIOConfig());
	}

	@Override
	public M getMetadata() {
		return metadata;
	}

	@Override
	public DataHandleInputStream<Location> getSource() {
		final Metadata m = getMetadata();
		if (m == null) return null;
		return m.getSource();
	}

	@Override
	public void updateSource(final Location source) throws IOException {
		metadata.setSource(new DataHandleInputStream<Location>(
				getContext(), source));
	}

	@Override
	public Location[] getUsedFiles() {
		return getUsedFiles(false);
	}

	@Override
	public Location[] getUsedFiles(final boolean noPixels) {
		final List<Location> files = new ArrayList<Location>();
		
		for (int i = 0; i < metadata.getImageCount(); i++) {
			final Location[] s = getImageUsedFiles(i, noPixels);
			if (s != null) {
				for (final Location file : s) {
					if (!files.contains(file)) {
						files.add(file);
					}
				}
			}
		}
		return files.toArray(new Location[files.size()]);
	}

	@Override
	public Location[] getImageUsedFiles(final int imageIndex) {
		return getImageUsedFiles(imageIndex, false);
	}

	@Override
	public Location[]
		getImageUsedFiles(final int imageIndex, final boolean noPixels)
	{
		return noPixels ? null : new Location[] { getMetadata().getSource().
			.getFileName() };
	}

	@Override
	public FileInfo[] getAdvancedUsedFiles(final boolean noPixels) {
		final Location[] files = getUsedFiles(noPixels);
		if (files == null) return null;
		return getFileInfo(files);
	}

	@Override
	public FileInfo[] getAdvancedImageUsedFiles(final int imageIndex,
		final boolean noPixels)
	{
		final Location[] files = getImageUsedFiles(imageIndex, noPixels);
		if (files == null) return null;
		return getFileInfo(files);
	}

	@Override
	public Set<MetadataLevel> getSupportedMetadataLevels() {
		final Set<MetadataLevel> supportedLevels = new HashSet<MetadataLevel>();
		supportedLevels.add(MetadataLevel.ALL);
		supportedLevels.add(MetadataLevel.NO_OVERLAYS);
		supportedLevels.add(MetadataLevel.MINIMUM);
		return supportedLevels;
	}

	// -- TypedParser API Methods --
	@Override
	public M parse(final Location fileName, final M meta) throws IOException,
		FormatException
	{
		return parse(fileName, meta, new SCIFIOConfig());
	}

	@Override
	public M parse(final File file, final M meta) throws IOException,
		FormatException
	{
		return parse(file, meta, new SCIFIOConfig());
	}

	@Override
	public M parse(final DataHandleInputStream<Location> stream, final M meta)
		throws IOException, FormatException
	{
		return parse(stream, meta, new SCIFIOConfig());
	}

	@Override
	public M
		parse(final Location fileName, final M meta, final SCIFIOConfig config)
			throws IOException, FormatException
	{
		DataHandleInputStream<Location> stream = getSource();

		if (stream != null) {
			if (stream.getFileName().equals(fileName)) {
				stream.seek(0);
			}
			else {
				close();
				stream.close();
				stream = null;
			}
		}

		if (stream == null) stream =
			new RandomAccessInputStream(getContext(), fileName);

		return parse(stream, meta, config);
	}

	@Override
	public M parse(final File file, final M meta, final SCIFIOConfig config)
		throws IOException, FormatException
	{
		return parse(file.getPath(), meta);
	}

	@Override
	public M parse(final DataHandleInputStream<Location> stream, final M meta,
		final SCIFIOConfig config) throws IOException, FormatException
	{
		final DataHandleInputStream<Location> in = getSource();

		if (in == null || !in.getFileName().equals(stream.getFileName())) {
			init(stream);

			if (config.parserIsSaveOriginalMetadata()) {
				// TODO store all metadata in OMEXML store..
				// or equivalent function? as per setId.. or handle via
				// annotations
			}
		}

		// TODO relying on Abstract-level API
		meta.setFiltered(config.parserIsFiltered());
		if (meta.getContext() == null) metadata.setContext(getContext());
		meta.setSource(stream);
		meta.setDatasetName(stream.getFileName());

		metadata = meta;
		typedParse(stream, meta, config);

		meta.populateImageMetadata();

		return meta;
	}

	// -- HasSource API Methods --

	@Override
	public void close(final boolean fileOnly) throws IOException {
		if (metadata != null) metadata.close(fileOnly);
	}

	// -- AbstractParser Methods --

	/**
	 * A helper method, called by
	 * {@link #parse(RandomAccessInputStream, TypedMetadata)}. Allows for
	 * boilerplate code to come after parsing, specifically calls to
	 * {@link Metadata#populateImageMetadata()}.
	 * <p>
	 * This method should be implemented to populate any format-specific Metadata.
	 * </p>
	 * <p>
	 * NB: if a Format requires type-specific parsing to occur before the Abstract
	 * layer, Override {@code #parse(Location, TypedMetadata)} instead.
	 * </p>
	 */
	protected abstract void typedParse(DataHandleInputStream<Location> stream, M meta,
		SCIFIOConfig config) throws IOException, FormatException;

	/* Sets the input stream for this parser if provided a new stream */
	private void init(final RandomAccessInputStream stream) throws IOException {

		// Check to see if the stream is already open
		if (getMetadata() != null) {
			final Location[] usedFiles = getUsedFiles();
			for (final Location fileName : usedFiles) {
				if (stream.getFileName().equals(fileName)) return;
			}
		}

		close();
	}

	/* Builds a FileInfo array around the provided array of file names */
	private FileInfo[] getFileInfo(final Location[] fileLocations) {
		final FileInfo[] infos = new FileInfo[fileLocations.length];
		for (int i = 0; i < infos.length; i++) {
			infos[i] = new FileInfo();
			infos[i].location = fileLocations[i];
			infos[i].reader = getFormat().getReaderClass();
			infos[i].usedToInitialize = fileLocations[i].equals();
		}
		return infos;
	}

	@Override
	public M parse(final Location fileName, final SCIFIOConfig config)
		throws IOException, FormatException
	{
		@SuppressWarnings("unchecked")
		final M meta = (M) getFormat().createMetadata();
		return parse(fileName, meta, config);
	}

	@Override
	public M parse(final File file, final SCIFIOConfig config)
		throws IOException, FormatException
	{
		@SuppressWarnings("unchecked")
		final M meta = (M) getFormat().createMetadata();
		return parse(file, meta, config);
	}

	@Override
	public M
		parse(final DataHandleInputStream<Location> stream, final SCIFIOConfig config)
			throws IOException, FormatException
	{
		@SuppressWarnings("unchecked")
		final M meta = (M) getFormat().createMetadata();
		return parse(stream, meta, config);
	}

	@Override
	public M parse(final Location fileName, final Metadata meta,
		final SCIFIOConfig config) throws IOException, FormatException
	{
		return parse(fileName, SCIFIOMetadataTools.<M> castMeta(meta), config);
	}

	@Override
	public M
		parse(final File file, final Metadata meta, final SCIFIOConfig config)
			throws IOException, FormatException
	{
		return parse(file, SCIFIOMetadataTools.<M> castMeta(meta), config);
	}

	@Override
	public M parse(final DataHandleInputStream<Location> stream, final Metadata meta,
		final SCIFIOConfig config) throws IOException, FormatException
	{
		return parse(stream, SCIFIOMetadataTools.<M> castMeta(meta), config);
	}

}
