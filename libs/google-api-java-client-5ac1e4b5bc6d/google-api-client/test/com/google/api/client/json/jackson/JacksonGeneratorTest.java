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

package com.google.api.client.json.jackson;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.testing.json.AbstractJsonGeneratorTest;

/**
 * Tests {@link JacksonGenerator}.
 *
 * @author Yaniv Inbar
 */
public class JacksonGeneratorTest extends AbstractJsonGeneratorTest {

  public JacksonGeneratorTest(String name) {
    super(name);
  }

  @Override
  protected JsonFactory newFactory() {
    return new JacksonFactory();
  }
}
