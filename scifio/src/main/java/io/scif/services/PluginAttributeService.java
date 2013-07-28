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

package io.scif.services;

import io.scif.SCIFIOPlugin;

import java.util.List;
import java.util.Map;

import org.scijava.plugin.PluginInfo;
import org.scijava.service.Service;

/**
 * Service for finding plugins with particular attribute values.
 * <p>
 * In each method signature, plugins will be returned if they are annotated with
 * {@link org.scijava.plugin.Attr}'s matching all key,value pairs in
 * {@code andPairs}, and at least one of any key, value pairs in {@code orPairs}
 * .
 * </p>
 * <p>
 * NB: attributes are assumed to be classes, and "matching" is equivalent to
 * passing an "isAssignableFrom" test. So it is possible to have multiple
 * "matches", in the case of both specific and general attribute types.
 * Typically you should set plugins with specific parameters to have higher
 * priority than those with general parameters, so they are checked first.
 * </p>
 * 
 * @author Mark Hiner
 */
public interface PluginAttributeService extends Service {

	/**
	 * As {@link org.scijava.plugin.PluginService#createInstancesOfType(Class)}
	 * but with key,value pair parameters to allow for filtering based on
	 * {@code Attr} annotation. Returns the first possible match.
	 */
	<PT extends SCIFIOPlugin> PT createInstance(Class<PT> type,
		Map<String, String> andPairs, Map<String, String> orPairs);

	/**
	 * As {@link org.scijava.plugin.PluginService#createInstancesOfType(Class)}
	 * but with key,value pair parameters to allow for filtering based on
	 * {@code Attr} annotation. Returns the first possible match if exact ==
	 * false, or the first specific match if exact == true.
	 */
	<PT extends SCIFIOPlugin> PT createInstance(Class<PT> type,
		Map<String, String> andPairs, Map<String, String> orPairs, boolean exact);

	/**
	 * As {@link org.scijava.plugin.PluginService#getPlugin(Class)} but with
	 * key,value pair parameters to allow for filtering based on {@code Attr}
	 * annotation. Returns the first possible match if exact == false, or the
	 * first specific match if exact == true.
	 */
	<PT extends SCIFIOPlugin> PluginInfo<PT> getPlugin(Class<PT> type,
		Map<String, String> andPairs, Map<String, String> orPairs, boolean exact);

	/**
	 * As {@link org.scijava.plugin.PluginService#getPluginsOfType(Class)} but
	 * with key,value pair parameters to allow for filtering based on {@code Attr}
	 * annotation. Returns all possible match if exact == false, or all specific
	 * matches if exact == true.
	 */
	<PT extends SCIFIOPlugin> List<PluginInfo<PT>> getPluginsOfType(
		Class<PT> type, Map<String, String> andPairs, Map<String, String> orPairs,
		boolean exact);
}