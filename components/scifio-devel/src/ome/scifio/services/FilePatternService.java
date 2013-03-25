/*
 * #%L
 * OME SCIFIO package for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2005 - 2013 Open Microscopy Environment:
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

package ome.scifio.services;

import java.io.File;

import org.scijava.service.Service;

import ome.scifio.io.Location;

/**
 * @author Mark Hiner
 *
 */
public interface FilePatternService extends Service {

  /**
   * Identifies the group pattern from a given file within that group.
   * @param path The file path to use as a template for the match.
   */
  String findPattern(String path);

  /**
   * Identifies the group pattern from a given file within that group.
   * @param file The file to use as a template for the match.
   */
  String findPattern(Location file);

  /**
   * Identifies the group pattern from a given file within that group.
   * @param file The file to use as a template for the match.
   */
  String findPattern(File file);

  /**
   * Identifies the group pattern from a given file within that group.
   * @param name The filename to use as a template for the match.
   * @param dir The directory in which to search for matching files.
   */
  String findPattern(String name, String dir);

  /**
   * Identifies the group pattern from a given file within that group.
   * @param name The filename to use as a template for the match.
   * @param dir The directory prefix to use for matching files.
   * @param nameList The names through which to search for matching files.
   */
  String findPattern(String name, String dir, String[] nameList);

  /**
   * Identifies the group pattern from a given file within that group.
   * @param name The filename to use as a template for the match.
   * @param dir The directory prefix to use for matching files.
   * @param nameList The names through which to search for matching files.
   * @param excludeAxes The list of axis types which should be excluded from the
   *  pattern.
   */
  String findPattern(String name, String dir, String[] nameList,
      int[] excludeAxes);

  /**
   * Generate a pattern from a list of file names.
   * The pattern generated will be a regular expression.
   *
   * Currently assumes that all file names are in the same directory.
   */
  String findPattern(String[] names);

  String[] findImagePatterns(String base);

  String[] findImagePatterns(String base, String dir, String[] nameList);

}