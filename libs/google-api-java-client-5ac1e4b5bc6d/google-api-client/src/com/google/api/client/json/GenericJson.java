/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.json;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

/**
 * Generic JSON data that stores all unknown key name/value pairs.
 * <p>
 * Subclasses can declare fields for known data keys using the {@link Key} annotation. Each field
 * can be of any visibility (private, package private, protected, or public) and must not be static.
 * {@code null} unknown data key names are not allowed, but {@code null} data values are allowed.
 *
 * @since 1.0
 * @author Yaniv Inbar
 */
public class GenericJson extends GenericData implements Cloneable {

  /**
   * JSON factory to use for {@link #toString()}.
   *
   * @since 1.3
   */
  public JsonFactory jsonFactory;

  @Override
  public String toString() {
    return jsonFactory.toString(this);
  }

  @Override
  public GenericJson clone() {
    return (GenericJson) super.clone();
  }
}
